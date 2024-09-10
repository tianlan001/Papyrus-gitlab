/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.editor.welcome.internal;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.TransactionHelper;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeFactory;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;

/**
 * A manager of the default {@link Welcome} model for the workspace.
 */
public class WelcomeModelManager {
	private final URI welcomeURI = URI.createURI("papyrus.welcome:dynamic"); //$NON-NLS-1$

	private final Path welcomePath;

	private final Map<ModelSet, WelcomeLocator> resourceSets = new HashMap<>();
	private final Map<ModelSet, Consumer<Welcome>> welcomeChangedHandlers = new HashMap<>();

	public WelcomeModelManager(Path stateLocation) {
		super();

		welcomePath = stateLocation.resolve("welcome.xmi").toAbsolutePath(); //$NON-NLS-1$
	}

	public URI getWelcomeURI() {
		return welcomeURI;
	}

	public boolean welcomeModelExists() {
		return Files.exists(welcomePath);
	}

	public Welcome getWelcome(ResourceSet resourceSet) {
		Resource resource = getWelcomeResource(resourceSet);
		return (resource == null) ? null : getWelcome(resource);
	}

	public Resource getWelcomeResource(ResourceSet resourceSet) {
		return resourceSet.getResource(getWelcomeURI(), true);
	}

	public void connect(ModelSet resourceSet) {
		resourceSets.put(resourceSet, new WelcomeLocator(resourceSet));
	}

	public void disconnect(ModelSet resourceSet) {
		welcomeChangedHandlers.remove(resourceSet);

		WelcomeLocator locator = resourceSets.remove(resourceSet);
		if (locator != null) {
			locator.dispose();
		}
	}

	/**
	 * Adds a handler to be invoked whenever the default {@link Welcome} model changes.
	 * 
	 * @param ownerModelSet
	 *            the model set in which the default welcome model is used
	 * 
	 * @param welcomeChangedHandler
	 *            a handler that reacts to the changed welcome model
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code ownerModelSet} is not currently
	 *             {@linkplain #connect(ModelSet) connected} to the model manager
	 */
	public void onWelcomeChanged(ModelSet ownerModelSet, Consumer<? super Welcome> welcomeChangedHandler) {
		if (!resourceSets.containsKey(ownerModelSet)) {
			throw new IllegalArgumentException("ownerModelSet is not connected"); //$NON-NLS-1$
		}

		// Chain handlers, with a base handler that validates the incoming Welcome instance
		welcomeChangedHandlers.put(ownerModelSet,
				getWelcomeChangedHandler(ownerModelSet).andThen(welcomeChangedHandler));
	}

	private Consumer<Welcome> getWelcomeChangedHandler(ResourceSet resourceSet) {
		return welcomeChangedHandlers.getOrDefault(resourceSet, Objects::requireNonNull);
	}

	public void createDefaultWelcomeResource(Welcome welcome) throws IOException {
		Resource welcomeResource = new XMIResourceImpl(getWelcomeURI());
		welcomeResource.getContents().add(EcoreUtil.copy(welcome));

		try (OutputStream output = Files.newOutputStream(welcomePath, WRITE, CREATE)) {
			welcomeResource.save(output, null);

			notifyDefaultChanged();
		} catch (IOException e) {
			// Make sure that any partial file is cleaned up
			Files.deleteIfExists(welcomePath);
			throw e;
		} finally {
			welcomeResource.unload();
			welcomeResource.eAdapters().clear();
		}
	}

	public void deleteDefaultWelcomeResource() throws IOException {
		Files.deleteIfExists(welcomePath);

		notifyDefaultChanged();
	}

	private void notifyDefaultChanged() {
		resourceSets.keySet().stream()
				.map(this::getWelcomeResource)
				// This notifies the adapter, which re-loads the resource and notifies clients
				.forEach(Resource::unload);
	}

	static Welcome getWelcome(Resource resource) {
		return (Welcome) EcoreUtil.getObjectByType(resource.getContents(), WelcomePackage.Literals.WELCOME);
	}

	/**
	 * Make an on old object become something new by morphing into its likeness.
	 * 
	 * @param oldObject
	 *            the old object, which identity we need to keep
	 * @param newObject
	 *            what the old object should be transformed into to look like
	 * 
	 * @return the {@code oldObject}
	 */
	static Welcome become(Welcome oldObject, Welcome newObject) {
		if (oldObject == newObject) {
			return oldObject;
		}

		// Re-initialize the old object
		oldObject.eClass().getEAllStructuralFeatures().stream()
				.filter(EStructuralFeature::isChangeable)
				.forEach(f -> oldObject.eUnset(f));

		// And repopuplate it from the new state
		@SuppressWarnings("serial")
		EcoreUtil.Copier copier = new EcoreUtil.Copier(true, true) {
			@Override
			protected EObject createCopy(EObject eObject) {
				// We already have this "copy"
				return (eObject == newObject) ? oldObject : super.createCopy(eObject);
			}
		};

		// Replace the new object with the old
		EcoreUtil.replace(newObject, oldObject);

		return (Welcome) copier.copy(newObject);
	}

	//
	// Nested types
	//

	private class WelcomeLocator extends ResourceLocator {
		private final Resource welcomeResource;

		WelcomeLocator(ModelSet modelSet) {
			super(modelSet);

			welcomeResource = new XMIResourceImpl(getWelcomeURI()) {
				@Override
				public ResourceSet getResourceSet() {
					// Yes, this is a violation of the opposite constraint
					return modelSet;
				}

				@Override
				public NotificationChain basicSetResourceSet(ResourceSet resourceSet, NotificationChain notifications) {
					throw new UnsupportedOperationException("setResourceSet");
				}
			};

			welcomeResource.eAdapters().add(new DefaultWelcomeAdapter());
		}

		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			Resource result;

			if (uri.equals(welcomeResource.getURI())) {
				// Always implicitly load this one
				result = getWelcomeResource();
			} else {
				result = basicGetResource(uri, loadOnDemand);
			}

			return result;
		}

		@Override
		protected void demandLoadHelper(Resource resource) {
			if (getWelcomeURI().equals(resource.getURI())) {
				Welcome welcome = null;

				if (welcomeModelExists()) {
					try {
						try (InputStream input = Files.newInputStream(welcomePath, StandardOpenOption.READ)) {
							welcomeResource.load(input, null);
							welcome = WelcomeModelManager.getWelcome(welcomeResource);
						}
					} catch (IOException e) {
						Activator.log.error("Failed to load default welcome page layout", e); //$NON-NLS-1$
						welcomeResource.getContents().clear(); // In case of partial load
					}
				}

				if (welcome == null) {
					welcome = WelcomeFactory.eINSTANCE.createWelcome();
					welcomeResource.getContents().add(welcome);
				}
			} else {
				super.demandLoadHelper(resource);
			}
		}

		@Override
		public void dispose() {
			super.dispose();

			// Ensure that our adapter is no longer listening to re-load the resource
			welcomeResource.eAdapters().clear();
			welcomeResource.unload();
		}

		Resource getWelcomeResource() {
			if (!welcomeResource.isLoaded()) {
				demandLoadHelper(welcomeResource);
			}
			return welcomeResource;
		}
	}

	private class DefaultWelcomeAdapter extends AdapterImpl {
		private Welcome realWelcome;

		@Override
		public void unsetTarget(Notifier oldTarget) {
			super.unsetTarget(oldTarget);
			realWelcome = null;
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getNotifier() instanceof Resource) {
				switch (msg.getFeatureID(Resource.class)) {
				case Resource.RESOURCE__CONTENTS:
					switch (msg.getEventType()) {
					case Notification.REMOVE:
						if (msg.getOldValue() instanceof Welcome) {
							realWelcome = (Welcome) msg.getOldValue();
						}
						break;
					case Notification.REMOVE_MANY:
						realWelcome = (Welcome) EcoreUtil.getObjectByType((Collection<?>) msg.getOldValue(), WelcomePackage.Literals.WELCOME);
						break;
					}
					break;
				case Resource.RESOURCE__IS_LOADED:
					if (msg.getOldBooleanValue() && !msg.getNewBooleanValue()) {
						ResourceSet rset = ((Resource) msg.getNotifier()).getResourceSet();

						// The resource was unloaded. Re-load it and push
						// the new Welcome object into the Welcome Page if
						// necessary. Be sure to maintain the old welcome's identity
						// for integrity of the welcome page reference
						try {
							TransactionHelper.run(TransactionUtil.getEditingDomain(rset), () -> {
								Welcome newWelcome = getWelcome(rset);
								become(realWelcome, newWelcome);

								// And it was unloaded, so it has a proxy URI that it shouldn't
								((InternalEObject) realWelcome).eSetProxyURI(null);
							});
						} catch (InterruptedException | RollbackException e) {
							Activator.log.error("Failed to re-load the default welcome model.", e); //$NON-NLS-1$
						}

						// Notify the client
						getWelcomeChangedHandler(rset).accept(realWelcome);

						realWelcome = null; // Don't need to hang onto this any longer
					}
					break;
				}
			}
		}
	}

}
