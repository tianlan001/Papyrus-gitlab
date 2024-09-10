/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 434983
 *  Christian W. Damus - bug 469188
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource.sasheditor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionPreferences;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelUtils;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;

/**
 * Set of utility methods linked to Trace for ControlMode
 *
 * @author cedric dumoulin
 *
 */
public class SashModelUtils {

	/**
	 * Gets the SashModel for the currently selected editor. <br>
	 * Warning: This method is designed to be call from ui.handlers. It is not
	 * designed to be call from Editors. This method can return null if called
	 * during the MultiEditor initialization.
	 *
	 * @see ServiceUtilsForActionHandlers.getInstance().getModelSet()
	 *
	 * @return The {@link SashModel} of the current editor, or null if not
	 *         found.
	 * @deprecated Use {@link #getSashModel(ModelSet)} or {@link #getSashModel(ServicesRegistry)} instead
	 */
	@Deprecated
	public static SashModel getSashModel() {

		try {
			return (SashModel) ServiceUtils.getInstance().getModelSet(null).getModel(SashModel.MODEL_ID);
		} catch (ServiceException e) {
			return null;
		}
	}

	/**
	 * Gets the SashModel for the currently selected editor. <br>
	 * Warning: This method is designed to be call from ui.handlers. It is not
	 * designed to be call from Editors. This method can return null if called
	 * during the MultiEditor initialization.
	 *
	 * @see ServiceUtilsForActionHandlers.getInstance().getModelSet()
	 *
	 * @return The {@link SashModel} of the current editor, or null if not
	 *         found.
	 * @throws ServiceException
	 *             If an error occurs while getting or starting the service.
	 * @deprecated Use {@link #getSashModelChecked(ServicesRegistry)} instead
	 */
	@Deprecated
	public static SashModel getSashModelChecked() throws ServiceException {

		return (SashModel) ServiceUtils.getInstance().getModelSet(null).getModel(SashModel.MODEL_ID);
	}

	/**
	 * Gets the SashModel from the {@link ModelSet}. <br>
	 *
	 * @param modelsManager
	 *            The modelManager containing the requested model.
	 *
	 * @return The {@link SashModel} registered in modelManager, or null if not
	 *         found.
	 */
	public static SashModel getSashModel(ModelSet modelsManager) {

		return (SashModel) modelsManager.getModel(SashModel.MODEL_ID);
	}

	/**
	 * Gets the SashModel from the {@link ModelSet}. <br>
	 *
	 * @param ServicesRegistry
	 *            The servie registry under which the ModelSet is registered.
	 *
	 * @return The {@link SashModel} registered in modelManager, or null if not
	 *         found.
	 */
	public static SashModel getSashModel(ServicesRegistry servicesRegistry) {

		try {
			return (SashModel) ModelUtils.getModelSetChecked(servicesRegistry).getModel(SashModel.MODEL_ID);
		} catch (ServiceException e) {
			return null;
		}
	}

	/**
	 * Gets the SashModel from the {@link ModelSet}. <br>
	 *
	 * @param ServicesRegistry
	 *            The servie registry under which the ModelSet is registered.
	 *
	 * @return The {@link SashModel} registered in modelManager, or null if not
	 *         found.
	 * @throws ServiceException
	 *             If the service can't be returned.
	 */
	public static SashModel getSashModelChecked(ServicesRegistry servicesRegistry) throws ServiceException {

		return (SashModel) ModelUtils.getModelSetChecked(servicesRegistry).getModel(SashModel.MODEL_ID);
	}

	/**
	 * Retrieve the uri of the initial di opened
	 *
	 * @param modelSet
	 * @return
	 */
	public static URI getInitialURI(ModelSet modelSet) {
		return SashModelUtils.getSashModel(modelSet).getResourceURI();
	}

	/**
	 * Returns the Sash Resource (.di or *.sash) associated to the model set. May be null.
	 *
	 * @param modelSet
	 * @return
	 * @since 3.0
	 */
	public static Resource getSashResource(ModelSet modelSet) {
		SashModel model = getSashModel(modelSet);
		if (model != null) {
			return model.getResource();
		}
		return null;
	}

	/**
	 * @since 2.0
	 */
	public static SashWindowsMngr getSashWindowsMngr(ModelSet modelSet) {
		SashWindowsMngr result = null;

		Resource resource = getSashResource(modelSet);
		if (resource != null) {
			result = (SashWindowsMngr) EcoreUtil.getObjectByType(resource.getContents(), DiPackage.Literals.SASH_WINDOWS_MNGR);
		}

		return result;
	}

	/**
	 * Gets an architecture description preferences element if available in the given model set
	 * 
	 * @param modelSet
	 *            the given model set
	 * @return an architecture description preferences (can be null)
	 * @since 3.0
	 */
	public static ArchitectureDescriptionPreferences getArchitectureDescriptionPreferences(ModelSet modelSet) {
		Resource resource = getSashResource(modelSet);
		if (resource != null) {
			return (ArchitectureDescriptionPreferences) EcoreUtil.getObjectByType(
					resource.getContents(), ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION_PREFERENCES);
		}
		return null;
	}

	/**
	 * Gets an architecture description preferences element if available in the given model set
	 * 
	 * @param modelSet
	 *            the given model set
	 * @return an architecture description preferences (can be null)
	 * @since 3.0
	 */
	public static ArchitectureDescriptionPreferences getOrAddArchitectureDescriptionPreferences(ModelSet modelSet) {
		Resource resource = getSashResource(modelSet);
		if (resource != null) {
			ArchitectureDescriptionPreferences preferences = (ArchitectureDescriptionPreferences) EcoreUtil.getObjectByType(resource.getContents(),
					ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION_PREFERENCES);
			if (preferences == null) {
				preferences = ArchitectureFactory.eINSTANCE.createArchitectureDescriptionPreferences();
				resource.getContents().add(preferences);
			}
			return preferences;
		}
		return null;
	}

}
