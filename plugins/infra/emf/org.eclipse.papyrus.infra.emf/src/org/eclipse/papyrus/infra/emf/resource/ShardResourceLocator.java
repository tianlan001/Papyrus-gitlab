/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.resource;

import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.getSemanticModelFileExtensions;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.emf.Activator;

/**
 * A {@link ResourceLocator} that can be used with any {@link ResourceSet}
 * to ensure that when a shard resource is demand-loaded by proxy resolution,
 * it is loaded from the top down to ensure that dependencies such as profile
 * applications in UML models are ensured before loading the shard.
 * 
 * @since 2.1
 */
public class ShardResourceLocator extends ResourceLocator {

	private final Set<Resource> inDemandLoadHelper = new HashSet<>();

	private final Supplier<? extends ICrossReferenceIndex> index;

	private final Set<String> semanticModelExtensions;

	/**
	 * Installs me in the given resource set. I use the best available
	 * {@link ICrossReferenceIndex} for resolution of shard relationships.
	 *
	 * @param resourceSet
	 *            the resource set for which I shall provide
	 */
	public ShardResourceLocator(ResourceSetImpl resourceSet) {
		this(resourceSet, new ICrossReferenceIndex.Delegator(resourceSet));
	}

	/**
	 * Installs me in the given resource set with a particular {@code index}.
	 *
	 * @param resourceSet
	 *            the resource set for which I shall provide
	 * @param index
	 *            the index to use for resolving shard relationships
	 */
	public ShardResourceLocator(ResourceSetImpl resourceSet, ICrossReferenceIndex index) {
		this(resourceSet, () -> index);
	}

	/**
	 * Installs me in the given resource set with a dynamic {@code index} supplier.
	 *
	 * @param resourceSet
	 *            the resource set for which I shall provide
	 * @param index
	 *            a dynamic supplier of the index to use for resolving shard relationships
	 */
	public ShardResourceLocator(ResourceSetImpl resourceSet, Supplier<? extends ICrossReferenceIndex> index) {
		super(resourceSet);

		this.index = index;
		this.semanticModelExtensions = getSemanticModelFileExtensions(resourceSet);
	}

	/**
	 * Handles shard resources by loading their roots first and the chain(s) of resources
	 * all the way down to the shard.
	 */
	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		if (loadOnDemand && uri.isPlatformResource()
				&& semanticModelExtensions.contains(uri.fileExtension())) {

			// Is it already loaded? This saves blocking on the cross-reference index
			Resource existing = getResource(uri, false);
			if ((existing == null) || !existing.isLoaded()) {
				// Do our peculiar process
				handleShard(uri);
			}
		}

		return basicGetResource(uri, loadOnDemand);
	}

	/**
	 * Handles the case of demand-loading of a shard by loading it from the root resource
	 * on down.
	 * 
	 * @param uri
	 *            the URI of a resource that may be a shard
	 */
	protected void handleShard(URI uri) {
		try {
			Set<URI> parents = index.get().getParents(uri);

			if (!parents.isEmpty()) {
				// Load from the root resource down
				parents.stream()
						.filter(this::notLoaded)
						.forEach(r -> loadParentResource(r, uri));
			}
		} catch (CoreException e) {
			Activator.log.log(e.getStatus());
		}
	}

	protected boolean notLoaded(URI uri) {
		Resource resource = resourceSet.getResource(uri, false);
		return (resource == null) || !resource.isLoaded();
	}

	protected void loadParentResource(URI parentURI, URI shardURI) {
		// This operates recursively on the demand-load helper
		Resource parent = resourceSet.getResource(parentURI, true);
		Resource shard = resourceSet.getResource(shardURI, true);

		// Unlock the shardresource, now
		inDemandLoadHelper.remove(shard);

		// Scan for the cross-resource containment
		URI normalizedShardURI = normalize(shardURI);
		for (TreeIterator<EObject> iter = EcoreUtil.getAllProperContents(parent, false); iter.hasNext();) {
			EObject next = iter.next();
			if (next.eIsProxy()) {
				// Must always only compare normalized URIs to determine 'same resource'
				URI proxyURI = normalize(((InternalEObject) next).eProxyURI());
				if (proxyURI.trimFragment().equals(normalizedShardURI)) {
					// This is our parent object
					EObject parentObject = next.eContainer();

					// Resolve the reference
					EReference containment = next.eContainmentFeature();
					if (!containment.isMany()) {
						// Easy case
						parentObject.eGet(containment, true);
					} else {
						InternalEList<?> list = (InternalEList<?>) parentObject.eGet(containment);
						int index = list.basicIndexOf(next);
						if (index >= 0) {
							// Resolve it
							list.get(index);
						}
					}
					break;
				}
			}
		}
	}

	protected URI normalize(URI uri) {
		return resourceSet.getURIConverter().normalize(uri);
	}

}
