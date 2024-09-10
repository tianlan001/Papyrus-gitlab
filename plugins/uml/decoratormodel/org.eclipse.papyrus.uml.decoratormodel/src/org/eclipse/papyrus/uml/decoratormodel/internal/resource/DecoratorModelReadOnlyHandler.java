/*****************************************************************************
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.decoratormodel.internal.resource;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.AbstractReadOnlyHandler;
import org.eclipse.papyrus.infra.core.resource.IReadOnlyHandler2;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ReadOnlyAxis;
import org.eclipse.papyrus.infra.tools.util.Suppliers2;
import org.eclipse.papyrus.uml.decoratormodel.helper.DecoratorModelUtils;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

/**
 * Read-only handler for profile application resources used by the current model.
 */
public class DecoratorModelReadOnlyHandler extends AbstractReadOnlyHandler {

	public DecoratorModelReadOnlyHandler(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	public Optional<Boolean> anyReadOnly(Set<ReadOnlyAxis> axes, URI[] uris) {
		// mine is a discretionary read-only policy
		if (axes.contains(ReadOnlyAxis.DISCRETION)) {
			EditingDomain domain = getEditingDomain();
			ResourceSet rset = (domain == null) ? null : domain.getResourceSet();
			if (rset instanceof ModelSet) {
				List<URI> decoratorModels = Lists.newArrayListWithCapacity(uris.length);

				// Eventually available mapping of {decorator model => user model}.
				// In the very unlikely case that the index actually isn't ready, yet,
				// this will result in failure to override a lower-priority policy's
				// read-only result with our not-read-only result. The consequence is
				// resources temporarily being thought to be read-only that aren't, which
				// may manifest itself to the user as a confusing pop-up. But, by that
				// time, probably it won't anyways because we will have recomputed
				// read-only state again by then
				Supplier<SetMultimap<URI, URI>> userModelsByDecorator = Suppliers2.eventualSupplier(
						DecoratorModelIndex.getInstance().getUserModelsByDecoratorAsync(), ImmutableSetMultimap.<URI, URI> of());

				for (int i = 0; i < uris.length; i++) {
					Resource resource = rset.getResource(uris[i].trimFragment(), false);
					if ((resource != null) && resource.isLoaded() && DecoratorModelUtils.isDecoratorModel(resource)) {
						decoratorModels.add(resource.getURI());
					} else if (userModelsByDecorator.get().containsKey(uris[i].trimFragment())) {
						// Go to the index, if we have it, yet
						decoratorModels.add(uris[i]);
					}
				}

				Set<URI> userModels = Sets.newHashSet();
				for (URI next : decoratorModels) {
					Resource resource = rset.getResource(next, false);
					if ((resource != null) && resource.isLoaded()) {
						userModels.addAll(DecoratorModelUtils.getUserModelResources(resource));
					} else {
						// Go to the index, if we have it, yet
						userModels.addAll(userModelsByDecorator.get().get(next));
					}
				}

				// We only overrule the read-only state of decorator models
				if (!decoratorModels.isEmpty()) {
					if (userModels.isEmpty()) {
						// An empty profile applications resource is not read-only; it's waiting for us to add stuff to it!
						return Optional.of(false);
					} else {
						// This resource's read-only status is the same as the resources for which it provides profile applications
						IReadOnlyHandler2 manager = ((ModelSet) rset).getReadOnlyHandler();
						if (manager != null) {
							return manager.anyReadOnly(ReadOnlyAxis.discretionAxes(), Iterables.toArray(userModels, URI.class));
						}
					}
				} else {
					// Maybe we're looking at the user model referenced by a decorator that was loaded as the model
					// set's main resource?
					ModelSet mset = (ModelSet) rset;
					for (int i = 0; i < uris.length; i++) {
						Resource resource = mset.getResource(uris[i], false);
						if ((resource != null) && resource.isLoaded()) {
							EObject firstRoot = Iterables.getFirst(resource.getContents(), null);
							if (firstRoot != null) {
								Resource root = EcoreUtil.getRootContainer(firstRoot).eResource();
								if (root != null) {
									resource = root;
								}
							}

							Set<URI> loadedDecorators = DecoratorModelUtils.getLoadedDecoratorModels(resource);
							boolean isPrincipalUserModel = false;
							if (!loadedDecorators.isEmpty()) {
								for (URI next : loadedDecorators) {
									isPrincipalUserModel = next.trimFileExtension().equals(mset.getURIWithoutExtension());
									if (isPrincipalUserModel) {
										break;
									}
								}
							}

							if (isPrincipalUserModel) {
								// The user model that we're editing via the decorator is discretionarily editable
								// because it is essentially the "main" resource being edited
								return Optional.of(false);
							}
						}
					}
				}
			}
		}

		return Optional.absent();
	}

	@Override
	public Optional<Boolean> makeWritable(Set<ReadOnlyAxis> axes, URI[] uris) {
		// I never make anything writable; my job is to make sure that resources don't appear read-only when they shouldn't
		return Optional.absent();
	}

}
