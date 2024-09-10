/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 429242
 *  Christian W. Damus - bug 496299
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource.sasheditor;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescription;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionPreferences;
import org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource;
import org.eclipse.papyrus.infra.core.resource.IEMFModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;

/**
 * @author dumoulin
 *
 */
public class DiModel extends AbstractModelWithSharedResource<EObject> implements IModel {

	/**
	 * File extension used for notation.
	 */
	public static final String MODEL_FILE_EXTENSION = "di"; //$NON-NLS-1$

	/**
	 * File extension used for notation.
	 */
	public static final String DI_FILE_EXTENSION = MODEL_FILE_EXTENSION;

	/**
	 * Sash Model ID.
	 *
	 * @deprecated Use {@link SashModel#MODEL_ID} instead
	 */
	@Deprecated
	public static final String MODEL_ID = "org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel"; //$NON-NLS-1$

	/**
	 * The ID of the DI Model
	 */
	public static final String DI_MODEL_ID = "org.eclipse.papyrus.infra.core.resource.DiModel"; //$NON-NLS-1$


	public DiModel() {
		super(ModelKind.master);
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
		return DI_FILE_EXTENSION;
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
		return DI_MODEL_ID;
	}

	@Override
	public void loadModel(URI uriWithoutExtension) {
		// It is a common use case that this resource does not (and will not) exist
		if (exists(uriWithoutExtension)) {
			try {
				super.loadModel(uriWithoutExtension);
			} catch (Exception ex) {
				createModel(uriWithoutExtension);
			}
		}

		if (resource == null) {
			createModel(uriWithoutExtension);
		}
	}


	@Override
	protected Map<Object, Object> getSaveOptions() {
		Map<Object, Object> saveOptions = super.getSaveOptions();

		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.FALSE);
		saveOptions.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.FALSE);

		return saveOptions;
	}

	@Override
	protected boolean isModelRoot(EObject object) {
		return object instanceof SashWindowsMngr ||
				object instanceof ArchitectureDescription ||
				object instanceof ArchitectureDescriptionPreferences;
	}

	@Override
	public void setModelURI(URI uriWithoutExtension) {
		for (Resource resource : getResources()) {
			if (isControlled(resource)) {
				URI newBaseUri = uriWithoutExtension.trimSegments(1).appendSegment(resource.getURI().lastSegment());
				resource.setURI(newBaseUri);
			}
		}
		super.setModelURI(uriWithoutExtension);

	}

	// Prevent infinite loop from 2 models delegating to each other.
	private boolean checkingControlState = false;

	/**
	 * Di resources are controlled if their base element is controlled
	 * In case the Di resource is empty, we should look at the associated resources and see if one of them is controlled.
	 */
	@Override
	public boolean isControlled(Resource resource) {
		if (checkingControlState) {
			return false;
		}

		try {
			checkingControlState = true;

			for (Resource resourceInModelSet : modelSet.getResources()) {
				if (resource.getURI().trimFileExtension().equals(resourceInModelSet.getURI().trimFileExtension()) && !isRelatedResource(resourceInModelSet)) {
					if (!resourceInModelSet.getContents().isEmpty()) {
						EObject eObject = resourceInModelSet.getContents().get(0);
						IModel iModel = modelSet.getModelFor(eObject);
						if (iModel instanceof IEMFModel) {
							if (((IEMFModel) iModel).isControlled(resourceInModelSet)) {
								return true;
							}
						}
					}
				}
			}
		} finally {
			checkingControlState = false;
		}
		return false;
	}


}
