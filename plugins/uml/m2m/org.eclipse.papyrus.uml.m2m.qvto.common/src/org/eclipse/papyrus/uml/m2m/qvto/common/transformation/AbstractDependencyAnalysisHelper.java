/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST)  - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping;



public abstract class AbstractDependencyAnalysisHelper implements IDependencyAnalysisHelper {

	protected final ThreadConfig config;

	protected final Set<String> softwareExtensions;

	protected final String softwareProfileExtension;

	// ResourceSet used to load and explore Static Libraries
	// protected final ResourceSet localResourceSet = new ThreadSafeResourceSet();
	protected final ResourceSet localResourceSet = new ResourceSetImpl();

	// Store the broken URIs without trying to resolve them. We don't have enough information to resolve them during the first phase of the model import
	// The Key is the resource URI, the value is the Set of each individual EObject Fragment (We need the EObject fragments to find potential matches)
	protected final Map<URI, Set<String>> brokenUris = new HashMap<URI, Set<String>>();

	protected final Set<URI> brokenProfiles = new HashSet<URI>();

	public AbstractDependencyAnalysisHelper(ThreadConfig config, Set<String> softwareExtensions, String softwareProfileExtension) {
		this.config = config;
		this.softwareExtensions = softwareExtensions;
		this.softwareProfileExtension = softwareProfileExtension;

		configureResourceSet();
	}

	protected void configureResourceSet() {
		localResourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_ATTACHMENT, true);
		localResourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, true);
		localResourceSet.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		localResourceSet.getLoadOptions().put(XMLResource.OPTION_USE_PACKAGE_NS_URI_AS_LOCATION, Boolean.FALSE);
	}

	protected void unloadResourceSet() {
		EMFHelper.unload(localResourceSet);
	}

	public void computeURIMappings(Collection<Resource> sourceModels) {
		for (Resource sourceModel : sourceModels) {
			doComputeURIMappings(sourceModel);
		}
	}

	public synchronized void resolveAllMappings(Map<URI, URI> urisToReplace, Map<URI, URI> profileUrisToReplace) {
		if (config instanceof AdvancedConfig) {
			if (((AdvancedConfig) config).getMappingParameters() == null) {
				((AdvancedConfig) config).setMappingParameters(MigrationParametersFactory.eINSTANCE.createMappingParameters());
			}

			try {
				for (Entry<URI, Set<String>> resourceToRepair : brokenUris.entrySet()) {

					// Already known mapping
					if (urisToReplace.containsKey(resourceToRepair.getKey())) {
						continue;
					}
					findMatch(resourceToRepair.getKey(), resourceToRepair.getValue());
				}

				for (URI profileDefinition : brokenProfiles) {

					// Already known mapping
					if (profileUrisToReplace.containsKey(profileDefinition.trimFragment().trimQuery())) {
						continue;
					}
					findMatch(profileDefinition);
				}
			} finally {
				unloadResourceSet();
				brokenUris.clear();
				brokenProfiles.clear();
			}
		}
	}

	protected void findMatch(URI resourceURI, Set<String> fragments) {
		if (config instanceof AdvancedConfig) {
			URIMapping mapping = null;

			for (String fragment : fragments) {
				URI eObjectURI = resourceURI.appendFragment(fragment);
				mapping = findExistingMapping(eObjectURI, localResourceSet);

				if (mapping != null) {
					break;
				}
			}

			if (mapping == null) {
				mapping = MigrationParametersFactory.eINSTANCE.createURIMapping();
				mapping.setSourceURI(resourceURI.toString());
				mapping.setTargetURI(mapping.getSourceURI());
			}

			((AdvancedConfig) config).getMappingParameters().getUriMappings().add(mapping);
		}
	}

	protected void findMatch(URI profileDefinitionURI) {
		if (this.config instanceof AdvancedConfig) {
			URIMapping match = findExistingProfileMapping(profileDefinitionURI, localResourceSet);

			if (match == null) {
				match = MigrationParametersFactory.eINSTANCE.createURIMapping();
				match.setSourceURI(profileDefinitionURI.trimFragment().trimQuery().toString());
				match.setTargetURI(match.getSourceURI());
			}

			((AdvancedConfig) config).getMappingParameters().getProfileUriMappings().add(match);
		}
	}

	protected void doComputeURIMappings(Resource sourceModel) {
		doComputeProfileURIMappings(sourceModel);

		TreeIterator<EObject> resourceContents = sourceModel.getAllContents();
		ResourceSet resourceSet = sourceModel.getResourceSet();

		while (resourceContents.hasNext()) {
			EObject next = resourceContents.next();
			for (EReference reference : next.eClass().getEAllReferences()) {
				if (reference.isContainer() || reference.isContainment() || reference.isDerived() || reference.isTransient()) {
					continue;
				}

				Object value = next.eGet(reference, false);
				if (value instanceof EObject) {
					handleURIMapping((EObject) value, resourceSet);
				} else if (value instanceof Collection<?>) {
					for (Object element : (Collection<?>) value) {
						if (element instanceof EObject) {
							handleURIMapping((EObject) element, resourceSet);
						}
					}
				}
			}
		}
	}

	protected void doComputeProfileURIMappings(Resource sourceModel) {
		ResourceSet resourceSet = sourceModel.getResourceSet();

		for (EObject rootObject : sourceModel.getContents()) {
			if (isInvalidStereotypeApplication(rootObject)) {
				handleProfileURIMapping(rootObject, resourceSet);
			}
		}
	}

	protected abstract boolean isInvalidStereotypeApplication(EObject eObject);

	protected boolean isSoftwareModelElement(EObject eObject) {
		return isSoftwareModelElement(EcoreUtil.getURI(eObject));
	}

	protected boolean isSoftwareModelElement(URI objectURI) {
		String fileExtension = objectURI.fileExtension();
		return softwareExtensions.contains(fileExtension) || softwareProfileExtension.equals(fileExtension);
	}


	protected synchronized Set<String> getFragments(URI resourceURI) {
		if (!brokenUris.containsKey(resourceURI)) {
			brokenUris.put(resourceURI, new HashSet<String>());
		}

		return brokenUris.get(resourceURI);
	}

	protected synchronized void handleBrokenReference(EObject proxy) {
		URI proxyURI = EcoreUtil.getURI(proxy);
		URI resourceURI = proxyURI.trimFragment().trimQuery();

		String fragment = proxyURI.fragment();
		Set<String> fragments = getFragments(resourceURI);
		fragments.add(fragment);
	}

	protected synchronized void addBrokenProfileDefinition(URI packageURI) {
		brokenProfiles.add(packageURI);
	}


	protected void handleProfileURIMapping(EObject stereotypeApplication, ResourceSet resourceSet) {

		EPackage profileDefinition = stereotypeApplication.eClass().getEPackage();
		URI packageURI = EcoreUtil.getURI(profileDefinition);
		if (packageURI.trimFragment().isEmpty()) {
			packageURI = URI.createURI(profileDefinition.getNsURI());
		}
		addBrokenProfileDefinition(packageURI);

	}

	protected void handleURIMapping(EObject eObject, ResourceSet resourceSet) {

		if (isSoftwareModelElement(eObject)) {
			handleBrokenReference(eObject);
			return;
		}

		if (eObject.eIsProxy()) {
			eObject = EcoreUtil.resolve(eObject, resourceSet);
			if (eObject.eIsProxy()) {
				handleBrokenReference(eObject);
				return;
			}
		}

	}

	protected URIMapping findExistingProfileMapping(URI profileDefinitionURI, ResourceSet resourceSet) {
		throw new UnsupportedOperationException(); // TODO I don't know how to implements it for Rpy Import
	}

	protected boolean isPathFragment(URI proxyURI) {
		String uriFragment = proxyURI.fragment();

		return uriFragment != null && uriFragment.charAt(0) == '/';
	}

	protected URIMapping findExistingMapping(URI proxyURI, ResourceSet resourceSet) {
		throw new UnsupportedOperationException();// TODO required for RSA, but I don't know yet how to implements if for Rpy Import
	}

	/** Propagates the URI Mappings to all duplicates */
	public void propagateURIMappings(List<URIMapping> allMappings, MappingParameters result) {
		for (URIMapping mapping : allMappings) {
			for (URIMapping uriMapping : result.getUriMappings()) {
				if (uriMapping.getSourceURI().equals(mapping.getSourceURI())) {
					uriMapping.setTargetURI(mapping.getTargetURI());
				}
			}

			for (URIMapping profileURIMapping : result.getProfileUriMappings()) {
				if (profileURIMapping.getSourceURI().equals(mapping.getSourceURI())) {
					profileURIMapping.setTargetURI(mapping.getTargetURI());
				}
			}
		}
	}

	public List<URIMapping> flattenURIMappings(MappingParameters result) {
		List<URIMapping> allMappings = new LinkedList<URIMapping>();
		allMappings.addAll(result.getUriMappings());
		allMappings.addAll(result.getProfileUriMappings());

		removeDuplicates(allMappings);

		return allMappings;
	}

	/**
	 * Remove duplicate mappings. Mappings are duplicate if they have the same SourceURI.
	 * Less specific mappings will be discarded (Usually, the ones with the same Source and Target URI)
	 */
	protected void removeDuplicates(List<URIMapping> allMappings) {
		List<URIMapping> mappingsCopy = new LinkedList<URIMapping>(allMappings);

		for (URIMapping mapping : mappingsCopy) {
			for (URIMapping m : allMappings) {
				if (m == mapping) {
					continue;
				}

				// This is a duplicate
				if (mapping.getSourceURI().equals(m.getSourceURI())) {
					// If both mappings are still present, remove one of them
					if (allMappings.contains(mapping) && allMappings.contains(m)) {
						URIMapping mappingToRemove = findLessSpecificMapping(mapping, m);

						allMappings.remove(mappingToRemove);
						break;
					}
				}
			}
		}
	}

	/**
	 * If 2 mappings have the same sourceURI but different targetURI, returns the less pertinent one
	 * (Usually, the one with the same Source and Target)
	 *
	 * @param mapping1
	 * @param mapping2
	 * @return
	 */
	protected URIMapping findLessSpecificMapping(URIMapping mapping1, URIMapping mapping2) {
		if (!isUsefulMapping(mapping1)) {
			return mapping1;
		}

		return mapping2;
	}

	protected boolean isUsefulMapping(URIMapping mapping) {
		if (mapping.getTargetURI() == null || "".equals(mapping.getTargetURI()) || mapping.getTargetURI().equals(mapping.getSourceURI())) {
			return false;
		}

		return true;
	}
}
