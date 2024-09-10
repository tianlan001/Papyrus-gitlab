/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeAddedEvent;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.IModelingAssistantModelProvider;
import org.eclipse.uml2.common.util.CacheAdapter;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * Registry of modeling assistant models contributed on our extension point.
 */
public class ModelingAssistantModelRegistry implements IProviderChangeListener {

	private static final String EXT_POINT = "modelProviders"; //$NON-NLS-1$

	private static final String E_PROVIDER = "modelProvider"; //$NON-NLS-1$

	private static final String A_URI = "uri"; //$NON-NLS-1$

	private static final String A_CLASS = "class"; //$NON-NLS-1$

	private static final ModelingAssistantModelRegistry INSTANCE = new ModelingAssistantModelRegistry();

	private final ResourceSet resourceSet = new ResourceSetImpl();

	private final Multimap<IModelingAssistantModelProvider, IModelingAssistantProvider> providers = HashMultimap.create();

	private final Multimap<IModelingAssistantModelProvider, Resource> models = HashMultimap.create();

	private final Map<URI, IModelingAssistantModelProvider> workspaceModels = Maps.newHashMap();

	private final CopyOnWriteArrayList<IProviderChangeListener> listeners = new CopyOnWriteArrayList<>();

	private ModelingAssistantModelRegistry() {
		super();

		// Attach a CacheAdapter for performance
		resourceSet.eAdapters().add(new CacheAdapter()); // bug 541590 [CDO] - change is not required here

		// Load plug-in models
		new ExtensionLoader().readRegistry();

		// And workspace models
		for (URI next : AssistantPlugin.preferences.getWorkspaceAssistantModels()) {
			addWorkspaceModel(next);
		}

		// And listen for workspace changes
		ResourcesPlugin.getWorkspace().addResourceChangeListener(createWorkspaceListener(), IResourceChangeEvent.POST_CHANGE);

		// And for element-type registry changes
		ElementTypeRegistry.getInstance().addElementTypeRegistryListener(createElementTypeRegistryListener());
	}

	public static ModelingAssistantModelRegistry getInstance() {
		return INSTANCE;
	}

	public Iterable<IModelingAssistantProvider> getModelingAssistantProviders() {
		return Collections.unmodifiableCollection(providers.values());
	}

	public void loadModels(IModelingAssistantModelProvider modelProvider) {
		// Track the resources that it adds
		final List<Resource> resources = resourceSet.getResources();
		final int priors = resources.size();

		providers.putAll(modelProvider, modelProvider.loadProviders(resourceSet));
		models.putAll(modelProvider, resources.subList(priors, resources.size()));
	}

	public void unloadModels(IModelingAssistantModelProvider modelProvider) {
		for (Resource next : models.get(modelProvider)) {
			next.unload();
			resourceSet.getResources().remove(next);
			next.eAdapters().clear();
		}

		models.removeAll(modelProvider);
		providers.removeAll(modelProvider);
	}

	public void registerWorkspaceAssistantModel(URI uri) {
		if (!workspaceModels.containsKey(uri) && addWorkspaceModel(uri)) {
			AssistantPlugin.preferences.addWorkspaceAssistantModel(uri);
		}
	}

	public void deregisterWorkspaceAssistantModel(URI uri) {
		if (removeWorkspaceModel(uri)) {
			AssistantPlugin.preferences.removeWorkspaceAssistantModel(uri);
		}
	}

	public boolean isRegistered(URI assistantModelURI) {
		Resource resource = resourceSet.getResource(assistantModelURI, false);
		return (resource != null) && resource.isLoaded();
	}

	private boolean addWorkspaceModel(URI uri) {
		boolean result = resourceSet.getURIConverter().exists(uri, null);
		if (result) {
			IModelingAssistantModelProvider provider = new DefaultModelingAssistantModelProvider(uri);
			workspaceModels.put(uri, provider);
			loadModels(provider);
		}

		return result;
	}

	private boolean removeWorkspaceModel(URI uri) {
		IModelingAssistantModelProvider provider = workspaceModels.remove(uri);
		if (provider != null) {
			unloadModels(provider);
		}

		return provider != null;
	}

	void reloadWorkspaceModel(URI oldURI, URI newURI) {
		if (oldURI != null) {
			removeWorkspaceModel(oldURI);
		}
		if (newURI != null) {
			addWorkspaceModel(newURI);
		}
	}

	//
	// Listener protocol
	//

	@Override
	public void providerChanged(ProviderChangeEvent event) {
		// Forward
		if (!listeners.isEmpty()) {
			for (IProviderChangeListener next : listeners) {
				try {
					// Forward this event as is because the only listener I should have is our own delegating provider
					// which handles this correctly
					next.providerChanged(event);
				} catch (Exception e) {
					AssistantPlugin.log.error("Uncaught exception in provider change listener", e); //$NON-NLS-1$
				}
			}
		}
	}

	public void addProviderChangeListener(IProviderChangeListener listener) {
		listeners.addIfAbsent(listener);
	}

	public void removeProviderChangeListener(IProviderChangeListener listener) {
		listeners.remove(listener);
	}

	private IResourceChangeListener createWorkspaceListener() {
		return new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {

						@Override
						public boolean visit(IResourceDelta delta) throws CoreException {
							IResource resource = delta.getResource();
							if (resource.getType() == IResource.FILE) {
								// Handle changed file.
								URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);

								// Do we care about it?
								if (AssistantPlugin.preferences.isWorkspaceAssistantModel(uri)) {
									switch (delta.getKind()) {
									case IResourceDelta.CHANGED:
										// Only interested if the contents (may) have changed
										if ((delta.getFlags() & (IResourceDelta.CONTENT | IResourceDelta.SYNC)) != 0) {
											reloadWorkspaceModel(uri, uri);
										}
										break;
									case IResourceDelta.REMOVED:
										if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
											// It was moved
											IPath movedToPath = delta.getMovedToPath();
											if (movedToPath != null) {
												URI newURI = URI.createPlatformResourceURI(movedToPath.toString(), true);
												deregisterWorkspaceAssistantModel(uri);
												registerWorkspaceAssistantModel(newURI);
											}
										} else if (resource.getProject().isAccessible()) {
											// It was deleted
											deregisterWorkspaceAssistantModel(uri);
										} else {
											// The project was closed. Don't forget about this model in case it returns later
											reloadWorkspaceModel(uri, null);
										}
										break;
									case IResourceDelta.ADDED:
										if ((delta.getFlags() & IResourceDelta.MOVED_FROM) == 0) {
											// It was not moved from some other location but has been restored to us
											reloadWorkspaceModel(null, uri);
										}
										break;
									}
								}
							}

							return true;
						}
					});
				} catch (CoreException e) {
					AssistantPlugin.log.error(e);
				}
			}
		};
	}

	private IElementTypeRegistryListener createElementTypeRegistryListener() {
		return new IElementTypeRegistryListener() {

			@Override
			public void elementTypeAdded(ElementTypeAddedEvent elementTypeAddedEvent) {
				// Forget everything we think we know about element types and recompute
				CacheAdapter.getCacheAdapter(resourceSet).clear(); // bug 541590 [CDO] - change is not required here
			}
		};
	}

	//
	// Nested types
	//

	private class ExtensionLoader extends RegistryReader {
		private final Map<IConfigurationElement, IModelingAssistantModelProvider> configured = Maps.newHashMap();

		ExtensionLoader() {
			super(Platform.getExtensionRegistry(), AssistantPlugin.getPlugin().getSymbolicName(), EXT_POINT);
		}

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			boolean result = true;

			switch (element.getName()) {
			case E_PROVIDER:
				if (add) {
					loadModels(element);
				} else {
					unloadModels(element);
				}
				break;
			default:
				result = false;
				break;
			}

			return result;
		}

		private void loadModels(IConfigurationElement element) {
			IModelingAssistantModelProvider provider = null;

			String modelURIString = element.getAttribute(A_URI);
			if (modelURIString != null) {
				URI resourceURI = URI.createURI(modelURIString);
				if (resourceURI.isRelative()) {
					resourceURI = resourceURI.resolve(URI.createPlatformPluginURI(element.getContributor().getName() + "/", true)); //$NON-NLS-1$
				}
				provider = new DefaultModelingAssistantModelProvider(resourceURI);
			} else {
				String className = element.getAttribute(A_CLASS);
				if (className == null) {
					// We do prefer the URI over the class
					logMissingAttribute(element, A_URI);
				} else {
					try {
						provider = (IModelingAssistantModelProvider) element.createExecutableExtension(A_CLASS);
					} catch (CoreException e) {
						AssistantPlugin.log.error("Failed to instantiate modeling assistant model provider extension", e); //$NON-NLS-1$
					}
				}
			}

			if (provider != null) {
				configured.put(element, provider);
				ModelingAssistantModelRegistry.this.loadModels(provider);
			}
		}

		private void unloadModels(IConfigurationElement element) {
			IModelingAssistantModelProvider provider = configured.remove(element);
			if (provider != null) {
				ModelingAssistantModelRegistry.this.unloadModels(provider);
			}
		}
	}
}
