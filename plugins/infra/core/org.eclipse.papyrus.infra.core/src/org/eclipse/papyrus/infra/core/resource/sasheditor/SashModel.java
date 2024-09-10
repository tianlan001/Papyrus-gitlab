/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Rewrite the sash model - store in the plugin's PreferenceStore (Bug 429239)
 *  Christian W. Damus (CEA) - bugs 429242, 436468
 * 	Christian W. Damus - bugs 434983, 469188, 485220, 461709
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource.sasheditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionPreferences;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.resource.EMFLogicalModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;

import com.google.common.base.Objects;
import com.google.common.io.Resources;

/**
 * <p>
 * Model for the sash system.
 * </p>
 * <p>
 * It may be stored in the *.di file (Legacy mode) or in a *.sash file in the user
 * preference space (~workspace/.metadata/.plugins/org.eclipse.papyrus.infra.core/)
 * </p>
 * <p>
 * The following properties are observable via Java Beans {@linkplain #addPropertyChangeListener(String, PropertyChangeListener) listeners}:
 * </p>
 * <ul>
 * <li>{@link #isLegacyMode() legacyMode}</li>
 * <li>{@link #getPrivateResourceURI() privateResourceURI}</li>
 * <li>{@link #getSharedResourceURI() sharedResourceURI}</li>
 * </ul>
 *
 * @author Cedric Dumoulin
 * @author Camille Letavernier
 *
 */
public class SashModel extends EMFLogicalModel implements IModel {

	/**
	 * @since 2.0
	 */
	public static final String PROPERTY_PRIVATE_RESOURCE_URI = "privateResourceURI"; //$NON-NLS-1$
	/**
	 * @since 2.0
	 */
	public static final String PROPERTY_SHARED_RESOURCE_URI = "sharedResourceURI"; //$NON-NLS-1$
	/**
	 * @since 2.0
	 */
	public static final String PROPERTY_LEGACY_MODE = "legacyMode"; //$NON-NLS-1$

	private final PropertyChangeSupport bean = new PropertyChangeSupport(this);

	private SashModelProviderManager providerManager;

	private Adapter sashModelStorageAdapter;

	private volatile Boolean legacyMode;

	/**
	 * File extension.
	 *
	 * @deprecated Use {@link DiModel#MODEL_FILE_EXTENSION} instead. The SashModel has been moved to a separate file
	 */
	@Deprecated
	public static final String MODEL_FILE_EXTENSION = "di"; //$NON-NLS-1$

	/**
	 * File extension for the Sash model
	 */
	public static final String SASH_MODEL_FILE_EXTENSION = "sash"; //$NON-NLS-1$

	/**
	 * Model ID.
	 */
	public static final String MODEL_ID = "org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public SashModel() {
		super();

		sashModelStorageAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (getResources().contains(msg.getNotifier())) {
					switch (msg.getFeatureID(Resource.class)) {
					case Resource.RESOURCE__CONTENTS:
						invalidateLegacyMode();
						break;
					}
				}
			}
		};
	}

	/**
	 * Get the file extension used for this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		if (isLegacy((resourceURI == null) ? resourceURI : resourceURI.trimFileExtension())) {
			return DiModel.MODEL_FILE_EXTENSION;
		} else {
			return SASH_MODEL_FILE_EXTENSION;
		}
	}

	@Override
	public void init(ModelSet modelSet) {
		super.init(modelSet);

		this.providerManager = new SashModelProviderManager(modelSet);
	}

	@Override
	public void unload() {
		if (providerManager != null) {
			providerManager.dispose();
			providerManager = null;
		}

		getResources().forEach(res -> res.eAdapters().remove(sashModelStorageAdapter));

		super.unload();
	}

	@Override
	protected boolean isRelatedResource(Resource resource) {
		boolean result = false;

		if (resource != null) {
			// We only handle the main Sash resource. Imported *.sash are not relevant
			if (resource == getResource()) {
				result = true;
			} else {
				// We can only calculate these related URIs if the ModelSet is initialized
				result = resource.getURI().equals(getPrivateResourceURI()) || resource.getURI().equals(getSharedResourceURI());
			}
		}

		return result;
	}

	@Override
	protected void configureResource(Resource resourceToConfigure) {
		super.configureResource(resourceToConfigure);

		if (resourceToConfigure != null) {
			resourceToConfigure.eAdapters().add(sashModelStorageAdapter);
		}
	}

	/**
	 * Get the identifier used to register this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		return MODEL_ID;
	}

	@Override
	public void loadModel(URI uriWithoutExtension) {

		URI sashModelURI = getSashModelURI(uriWithoutExtension);

		this.resourceURI = sashModelURI;

		try {
			super.loadModel(sashModelURI.trimFileExtension());
		} catch (Exception ex) {
			// Unload it and try again by creating it
			Resource resource = getModelManager().getResource(sashModelURI, false);
			if (resource != null) {
				resource.unload();
				resource.getResourceSet().getResources().remove(resource);
			}

			// This will create the private sash model if that's what we need
			createModel(uriWithoutExtension);
		}

		if (resource == null) {
			// This will create the private sash model if that's what we need
			createModel(uriWithoutExtension);
		}
	}

	@Override
	public void createModel(URI uriWithoutExtension) {
		if (!isLegacy(uriWithoutExtension)) {
			// http://eclip.se/461709
			// Initialize the workspace-private sash model from our template
			// to avoid resolving all proxies in the ProxyModificationTrackingAdapter
			URI sashURI = getSashModelStoreURI(uriWithoutExtension);
			try {
				intantiateTemplate(sashURI);
				loadModel(uriWithoutExtension);
			} catch (IOException e) {
				// Fall back to the default (pre-461709) strategy
				Activator.log.error("Failed to initialize workspace private sash model", e); //$NON-NLS-1$
				super.createModel(sashURI.trimFileExtension());
			}
		} else {
			super.createModel(uriWithoutExtension);
		}
	}

	/**
	 * Instantiates the sash resource template on the given URI.
	 * 
	 * @param sashResourceURI
	 *            the new sash resource URI
	 * 
	 * @throws IOException
	 *             on failure to copy the template to this URI for any reason
	 */
	void intantiateTemplate(URI sashResourceURI) throws IOException {
		try (OutputStream output = getModelManager().getURIConverter().createOutputStream(sashResourceURI)) {
			String path = "templates/model.sash"; //$NON-NLS-1$
			URL template = Activator.getDefault().getBundle().getEntry(path);
			if (template != null) {
				Resources.copy(template, output);
			} else {
				throw new IOException("Sash template URL is null for: " + path);
			}
		}
	}

	@Override
	public void setModelURI(URI uriWithoutExtension) {
		URI oldPrivateURI = getPrivateResourceURI();
		URI oldSharedURI = getSharedResourceURI();

		URI newURI;
		if ((resourceURI != null) && isLegacy(resourceURI.trimFileExtension())) {
			newURI = getLegacyURI(uriWithoutExtension);
		} else {
			newURI = getSashModelStoreURI(uriWithoutExtension);
		}

		super.setModelURI(newURI.trimFileExtension());

		bean.firePropertyChange(PROPERTY_PRIVATE_RESOURCE_URI, oldPrivateURI, getPrivateResourceURI());
		bean.firePropertyChange(PROPERTY_SHARED_RESOURCE_URI, oldSharedURI, getSharedResourceURI());
	}

	protected boolean isLegacy(URI uriWithoutExtension) {
		if (uriWithoutExtension == null) {
			return false;
		}
		
		URI legacy = getLegacyURI(uriWithoutExtension);
		URI sash = getSashModelURI(uriWithoutExtension);
		
		return Objects.equal(legacy, sash);
	}

	/**
	 * Returns the sash model URI (With file extension)
	 *
	 * It may be either the Legacy URI (platform:/resource/model/model.di)
	 * or the 1.0.0 URI (file:/~workspace/.metadata/.plugins/org.eclipse.papyrus.infra.core/model/model.sash)
	 *
	 * @param uriWithoutExtension
	 * @return
	 *
	 */
	protected URI getSashModelURI(URI uriWithoutExtension) {
		URI legacyURI = getLegacyURI(uriWithoutExtension);

		// If the DI model exists and contains a SashWindowsMngr, this is a legacy model
		// (don't test the DI model on the file system, in case the DI model is not saved yet)
		try {
			Resource diResource = getModelManager().getResource(legacyURI, false);
			if (diResource != null && DiUtils.lookupSashWindowsMngr(diResource) != null) {
				return legacyURI;
			}
		} catch (Exception ex) {
			// Temporary workaround: the DI file may exist and be empty
			// (DiModel is currently disabled and doesn't properly init the di file)
			// Log the error and continue
			Activator.log.error(ex);
		}
		return getSashModelStoreURI(uriWithoutExtension);
	}

	protected URI getLegacyURI(URI uriWithoutExtension) {
		return uriWithoutExtension.appendFileExtension(DiModel.MODEL_FILE_EXTENSION);
	}

	protected URI getSashModelStoreURI(URI uriWithoutExtension) {
		URI fullURI = uriWithoutExtension.appendFileExtension(SASH_MODEL_FILE_EXTENSION);
		return providerManager.getSashModelProvider(fullURI).getSashModelURI(fullURI);
	}

	@Override
	protected Map<Object, Object> getSaveOptions() {
		Map<Object, Object> saveOptions = super.getSaveOptions();

		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.FALSE);
		saveOptions.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.FALSE);
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

		return saveOptions;
	}

	/**
	 * @since 2.0
	 */
	public boolean isLegacyMode() {
		if (legacyMode == null) {
			legacyMode = false; // Assume not

			// Does the shared DI resource contain the sash layout?
			URI sharedURI = getSharedResourceURI();
			if (sharedURI != null) {
				for (Resource next : getResources()) {
					if (sharedURI.equals(next.getURI())) {
						legacyMode = DiUtils.lookupSashWindowsMngr(next) != null;
						break;
					}
				}
			}
		}

		return legacyMode;
	}

	void invalidateLegacyMode() {
		boolean oldValue = isLegacyMode();

		legacyMode = null;

		boolean newValue = isLegacyMode();

		if (oldValue != newValue) {
			bean.firePropertyChange(PROPERTY_LEGACY_MODE, oldValue, newValue);
		}
	}

	/**
	 * Gets the URI of the sash-model resource in the user private area, irrespective
	 * of whether that actually is the resource that currently stores the sash model.
	 * 
	 * @return the private sash-model resource URI
	 * @since 2.0
	 */
	public URI getPrivateResourceURI() {
		URI modelURI = (getModelManager() == null) ? null : getModelManager().getURIWithoutExtension();
		return (modelURI == null) ? null : getSashModelStoreURI(modelURI);
	}

	/**
	 * Gets the URI of the sash-model resource in the shared (collocated with the user model)
	 * area, irrespective of whether that actually is the resource that currently stores the
	 * sash model.
	 * 
	 * @return the shared sash-model resource URI
	 * @since 2.0
	 */
	public URI getSharedResourceURI() {
		URI modelURI = (getModelManager() == null) ? null : getModelManager().getURIWithoutExtension();
		return (modelURI == null) ? null : modelURI.appendFileExtension(DiModel.MODEL_FILE_EXTENSION);
	}

	@Override
	protected boolean isRootElement(EObject object) {
		return super.isRootElement(object) && 
				(object instanceof SashWindowsMngr || 
				 object instanceof ArchitectureDescriptionPreferences);
	}

	@Override
	protected boolean isSupportedRoot(EObject object) {
		return DiPackage.Literals.SASH_WINDOWS_MNGR.isInstance(object) ||
			  ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION_PREFERENCES.isInstance(object);
	}

	//
	// Bean API
	//

	/**
	 * @since 2.0
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		bean.addPropertyChangeListener(listener);
	}

	/**
	 * @since 2.0
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		bean.removePropertyChangeListener(listener);
	}

	/**
	 * @since 2.0
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		bean.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @since 2.0
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		bean.removePropertyChangeListener(propertyName, listener);
	}
}
