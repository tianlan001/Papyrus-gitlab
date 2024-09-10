/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.utils;

import static org.eclipse.papyrus.infra.emf.utils.ResourceUtils.createWorkspaceAwareURIConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.osgi.container.ModuleContainer;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.pde.core.plugin.IPluginAttribute;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.namespace.IdentityNamespace;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.FrameworkWiring;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Optional support for a PDE Target platform, to discover the plug-ins against
 * which workspace projects (if any) are overlaid.
 */
abstract class PlatformHelper {

	private static final String ECORE_URI_MAPPING_EXTENSION_POINT = "org.eclipse.emf.ecore.uri_mapping"; //$NON-NLS-1$
	private static final String E_EXTENSION = "extension"; //$NON-NLS-1$
	private static final String A_POINT = "point"; //$NON-NLS-1$
	private static final String E_MAPPING = "mapping"; //$NON-NLS-1$
	private static final String A_SOURCE = "source"; //$NON-NLS-1$
	private static final String A_TARGET = "target"; //$NON-NLS-1$

	static final PlatformHelper INSTANCE;

	static {
		PlatformHelper instance;

		try {
			instance = new PDEHelper();
		} catch (Exception e) {
			// PDE is not available
			instance = new InstallHelper();
		}

		INSTANCE = instance;
	}


	/**
	 * Get the IDs of all bundles available in the target platform, whether that be the
	 * PDE Target Platform (in case PDE is installed) or else the host installation.
	 *
	 * @return the platform bundle IDs
	 */
	abstract Collection<String> getPlatformBundleIDs();

	/**
	 * Return the {@code org.eclipse.emf.ecore.uri_mapping} extension declarations in the given {@code project}.
	 *
	 * @param project
	 *            a project in the workspace
	 * @return its declared URI mappings
	 */
	abstract Map<String, String> getLocalUriMappings(IProject project);

	/**
	 * Create a package registry that, if possible, includes also packages registered by plug-ing projects in the workspace.
	 *
	 * @return a new workspace-aware (as much as possible) package registry
	 */
	EPackage.Registry createWorkspacePackageRegistry() {
		return new EPackageRegistryImpl(EPackage.Registry.INSTANCE);
	}

	//
	// Nested types
	//

	/**
	 * The install helper instance gets all resolved (ready/available) bundles in the current installation.
	 */
	private static final class InstallHelper extends PlatformHelper {

		private static final int AVAILABLE = Bundle.ACTIVE | Bundle.RESOLVED | Bundle.STARTING;

		@Override
		Collection<String> getPlatformBundleIDs() {
			Collection<String> result = new HashSet<>();

			FrameworkWiring wiring = Platform.getBundle(Constants.SYSTEM_BUNDLE_SYMBOLICNAME).adapt(FrameworkWiring.class);
			Collection<BundleCapability> bundleIdentities = wiring.findProviders(ModuleContainer
					.createRequirement(IdentityNamespace.IDENTITY_NAMESPACE, Collections.emptyMap(), Collections.emptyMap()));
			for (BundleCapability next : bundleIdentities) {
				Bundle bundle = next.getRevision().getBundle();
				if ((bundle.getState() & AVAILABLE) != 0) {
					result.add(bundle.getSymbolicName());
				}
			}

			return result;
		}

		@Override
		Map<String, String> getLocalUriMappings(IProject project) {
			IFile pluginXML = project.getFile("plugin.xml"); //$NON-NLS-1$
			if (!pluginXML.isAccessible()) {
				return Map.of();
			}

			Map<String, String> result = new HashMap<>();

			try (InputStream input = pluginXML.getContents()) {
				SAXParserFactory.newInstance().newSAXParser().parse(input, new DefaultHandler2() {
					private boolean inMappings;

					@Override
					public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
						// plugin.xml does not use namespaces, so the parser provides empty local names and the local names in qName
						if (E_EXTENSION.equals(qName) && ECORE_URI_MAPPING_EXTENSION_POINT.equals(attributes.getValue(A_POINT))) {
							inMappings = true;
						} else if (inMappings && E_MAPPING.equals(qName)) {
							String source = attributes.getValue(A_SOURCE);
							String target = attributes.getValue(A_TARGET);
							if (source != null && !source.isBlank() && target != null && !target.isBlank()) {
								result.put(source, target);
							}
						}
					}

					@Override
					public void endElement(String uri, String localName, String qName) throws SAXException {
						if (E_EXTENSION.equals(qName)) {
							inMappings = false;
						}
					}
				});
			} catch (SAXException | IOException | ParserConfigurationException | CoreException e) {
				Activator.log.error("Failed to parse plugin.xml in project" + project.getName(), e); //$NON-NLS-1$
			}

			return result;
		}

	}

	/**
	 * The PDE helper instance gets all active bundles in the current PDE target platform.
	 */
	private static final class PDEHelper extends PlatformHelper {

		private static final String GENERATED_PACKAGE_EXTPOINT = "org.eclipse.emf.ecore.generated_package"; //$NON-NLS-1$
		private static final String DYNAMIC_PACKAGE_EXTPOINT = "org.eclipse.emf.ecore.dynamic_package"; //$NON-NLS-1$
		private static final String PACKAGE_EXTELEM = "package"; //$NON-NLS-1$
		private static final String RESOURCE_EXTELEM = "resource"; //$NON-NLS-1$
		private static final String URI_EXTATT = "uri"; //$NON-NLS-1$
		private static final String GEN_MODEL_EXTATT = "genModel"; //$NON-NLS-1$
		private static final String LOCATION_EXTATT = "location"; //$NON-NLS-1$

		private static final String GEN_MODEL = "GenModel"; //$NON-NLS-1$
		private static final String GEN_PACKAGES = "genPackages"; //$NON-NLS-1$
		private static final String ECORE_PACKAGE = "ecorePackage"; //$NON-NLS-1$

		@Override
		Collection<String> getPlatformBundleIDs() {
			IPluginModelBase[] pluginModels = PluginRegistry.getActiveModels();
			Collection<String> result = new HashSet<>();

			for (IPluginModelBase next : pluginModels) {
				if (next.getBundleDescription() != null) {
					result.add(next.getBundleDescription().getSymbolicName());
				}
			}

			return result;
		}

		@Override
		Map<String, String> getLocalUriMappings(IProject project) {
			HashMap<String, String> localMappings = new HashMap<>();
			final IPluginModelBase model = PluginRegistry.findModel(project.getName());
			if (model == null) {
				// No mappings if no plugin model
				return localMappings;
			}

			for (IPluginExtension extension : model.getExtensions().getExtensions()) {
				if (!Objects.equals(extension.getPoint(), ECORE_URI_MAPPING_EXTENSION_POINT)) {
					continue;
				}
				Arrays.stream(extension.getChildren())
						.filter(IPluginElement.class::isInstance).map(IPluginElement.class::cast)
						.filter(element -> Objects.equals(E_MAPPING, element.getName()))
						.forEach(element -> mapURI(localMappings, element));
			}

			return localMappings;
		}

		private void mapURI(Map<String, String> mappings, IPluginElement element) {
			String source = getAttribute(element, A_SOURCE);
			String target = getAttribute(element, A_TARGET);
			if (source != null && target != null) {
				mappings.put(source, target);
			}
		}

		@Override
		EPackage.Registry createWorkspacePackageRegistry() {
			ResourceSet resourceSet = new ResourceSetImpl();

			// Ensure that platform:/plugin URIs in package/profile registrations resolve
			// into the workspace where applicable (bug 573888)
			resourceSet.setURIConverter(createWorkspaceAwareURIConverter());

			EPackage.Registry result = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);

			Map<String, URI> models = new HashMap<>();

			for (IPluginModelBase model : PluginRegistry.getWorkspaceModels()) {
				for (IPluginExtension extension : model.getExtensions().getExtensions()) {
					switch (extension.getPoint()) {
					case GENERATED_PACKAGE_EXTPOINT:
						Arrays.stream(extension.getChildren())
								.filter(IPluginElement.class::isInstance).map(IPluginElement.class::cast)
								.filter(element -> Objects.equals(PACKAGE_EXTELEM, element.getName()))
								.forEach(element -> mapPackageRegistration(models, element, URI_EXTATT, GEN_MODEL_EXTATT,
										genmodel -> getURI(model, genmodel, false)));
						break;
					case DYNAMIC_PACKAGE_EXTPOINT:
						Arrays.stream(extension.getChildren())
								.filter(IPluginElement.class::isInstance).map(IPluginElement.class::cast)
								.filter(element -> Objects.equals(RESOURCE_EXTELEM, element.getName()))
								.forEach(element -> mapPackageRegistration(models, element, URI_EXTATT, LOCATION_EXTATT,
										location -> getURI(model, location, true)));
						break;
					}
				}
			}

			models.forEach((uri, modelURI) -> {
				if (!EPackage.Registry.INSTANCE.containsKey(uri)) {
					result.put(uri, createEPackageDescriptor(resourceSet, modelURI));
				}
			});

			return result;
		}

		private void mapPackageRegistration(Map<String, URI> models, IPluginElement element, String nsURIAttribute, String modelAttribute, Function<String, URI> uriFunction) {
			String nsURI = getAttribute(element, nsURIAttribute);
			String modelLocation = getAttribute(element, modelAttribute);
			URI modelURI = null;

			if (modelLocation != null && nsURI != null) {
				modelURI = uriFunction.apply(modelLocation);
			}

			if (modelURI != null) {
				models.put(nsURI, modelURI);
			}
		}

		private String getAttribute(IPluginElement element, String name) {
			IPluginAttribute attribute = element.getAttribute(name);
			return attribute == null ? null : attribute.getValue();
		}

		private URI getURI(IPluginModelBase plugin, String path, boolean withFragment) {
			URI result;

			try {
				result = URI.createURI(path, true);
				if (!result.isRelative()) {
					// Nothing to resolve against the workspace project location
					return result;
				}
			} catch (Exception e) {
				// This can be expected for some inputs that manifestly are not absolute URIs
			}

			while (!path.isEmpty() && (path.startsWith("/") || path.startsWith("\\"))) { //$NON-NLS-1$//$NON-NLS-2$
				path = path.substring(1);
			}

			String fragment = null;
			if (withFragment) {
				// Don't encode this as part of the path!
				int hash = path.lastIndexOf('#');
				if (hash >= 0) {
					fragment = path.substring(hash + 1);
					path = path.substring(0, hash);
				}
			}

			path = String.format("%s/%s", plugin.getPluginBase().getId(), path); //$NON-NLS-1$
			result = URI.createPlatformResourceURI(path, true);

			if (fragment != null) {
				result = result.appendFragment(fragment);
			}

			return result;
		}

		private EPackage.Descriptor createEPackageDescriptor(ResourceSet resourceSet, URI modelURI) {
			return new EPackage.Descriptor() {

				@Override
				public EPackage getEPackage() {
					return loadEPackage(resourceSet, modelURI);
				}

				@Override
				public EFactory getEFactory() {
					EPackage ePackage = getEPackage();
					return ePackage == null ? null : ePackage.getEFactoryInstance();
				}
			};
		}

		private EPackage loadEPackage(ResourceSet resourceSet, URI modelURI) {
			EPackage result = null;

			try {
				if (modelURI.hasFragment()) {
					// It's an explicit reference to an EPackage
					EObject object = resourceSet.getEObject(modelURI, true);
					if (object instanceof EPackage) {
						result = (EPackage) object;
					}
				} else {
					Resource resource = resourceSet.getResource(modelURI, true);
					if (resource != null && resource.isLoaded() && !resource.getContents().isEmpty()) {
						EObject object = resource.getContents().get(0);
						if (object instanceof EPackage) {
							result = (EPackage) object;
						} else if (GEN_MODEL.equals(object.eClass().getName()) && object.eClass().getEStructuralFeature(GEN_PACKAGES) != null) {
							EStructuralFeature genPackagesRef = object.eClass().getEStructuralFeature(GEN_PACKAGES);
							@SuppressWarnings("unchecked")
							EObject genPackage = ((EList<? extends EObject>) object.eGet(genPackagesRef)).get(0);
							EStructuralFeature ecorePackageRef = genPackage.eClass().getEStructuralFeature(ECORE_PACKAGE);
							result = (EPackage) genPackage.eGet(ecorePackageRef);
						}
					}
				}

				if (result != null) {
					// Make it appear to be registered
					result.eResource().setURI(URI.createURI(result.getNsURI()));
				}
			} catch (Exception e) {
				// Cannot load the resource? It's not a package, then
				Activator.log.error("Failed to load Ecore package from workspace.", e); //$NON-NLS-1$
			}

			return result;
		}

	}

}
