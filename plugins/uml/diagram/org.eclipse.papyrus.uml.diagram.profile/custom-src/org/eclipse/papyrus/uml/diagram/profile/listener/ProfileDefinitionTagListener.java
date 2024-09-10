/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.profile.listener;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.decoration.DecorationService;
import org.eclipse.papyrus.uml.tools.Activator;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.profile.definition.IPapyrusVersionConstants;
import org.eclipse.papyrus.uml.tools.profile.definition.PapyrusDefinitionAnnotation;
import org.eclipse.papyrus.uml.tools.service.IProfileDefinitionService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * The purpose of this listener is to tag modifications which doesn't appear in Profile Definition.
 *
 * @author Gabriel Pascual
 * @since 3.1
 */
public class ProfileDefinitionTagListener implements IPapyrusListener {

	/** The Constant MODEL_SET_SERVICE_ERROR. */
	private static final String MODEL_SET_SERVICE_ERROR = "Failed to get Model Set."; //$NON-NLS-1$

	/** The Constant DECORATION_ACTION_ERROR. */
	private static final String DECORATION_ACTION_ERROR = "Failed to decorate undefined Profile."; //$NON-NLS-1$

	/** The Constant UNDECORATION_ACTION_ERROR. */
	private static final String UNDECORATION_ACTION_ERROR = "Failed to remove decoration on Profile."; //$NON-NLS-1$

	/** The Constant PAPYRUS_SERVICE_ERROR. */
	private static final String ROOT_MODEL_ACCESS_ERROR = "Failed to retrieve root element of UML resource"; //$NON-NLS-1$



	/**
	 * Instantiates a new profile definition tag listener.
	 */
	public ProfileDefinitionTagListener() {

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	public void notifyChanged(final Notification notification) {

		// Filter notification
		int eventType = notification.getEventType();
		Object notifier = notification.getNotifier();

		if (Notification.RESOLVE != eventType && Notification.REMOVING_ADAPTER != eventType) {

			// Case : an element is modified
			if (notifier instanceof Element) {

				Resource resource = ((Element) notifier).eResource();
				if (resource instanceof UMLResource) {
					EObject rootElement = getRootModel((EObject) notifier);

					if (rootElement instanceof Profile) {
						// The notification concerns an element of Profile model
						EAnnotation umlAnnotation = ((Profile) rootElement).getEAnnotation(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
						if (null != umlAnnotation) {
							if (!isAlreadyTag(umlAnnotation)) {
								umlAnnotation.getEAnnotations().add(PapyrusDefinitionAnnotation.UNDEFINED_ANNOTATION.convertToEAnnotation());
								decorateProfile((Profile) rootElement);

							}
						}

					}
				}
			} else if (Notification.ADD == eventType && notifier instanceof EAnnotation && notification.getNewValue() instanceof EAnnotation) {

				// Case: an Undefined Annotation is added
				EAnnotation umlAnnotation = (EAnnotation) notifier;
				EAnnotation undefinedAnnotation = (EAnnotation) notification.getNewValue();

				if (isUndefinedProfileTagAnnotation(umlAnnotation, undefinedAnnotation)) {

					EObject rootElement = getRootModel(umlAnnotation);
					if (rootElement instanceof Profile) {
						decorateProfile((Profile) rootElement);
					}

				}



			} else if (Notification.REMOVE == eventType && notifier instanceof EAnnotation && notification.getOldValue() instanceof EAnnotation) {

				// Case : an undefined annotation is removed
				EAnnotation undefinedAnnotation = (EAnnotation) notification.getOldValue();
				EAnnotation umlAnnotation = (EAnnotation) notifier;

				if (isUndefinedProfileTagAnnotation(umlAnnotation, undefinedAnnotation)) {

					EObject rootElement = getRootModel(umlAnnotation);
					if (rootElement instanceof Profile) {
						undecorateProfile((Profile) rootElement);
					}
				}

			}

		}



	}

	/**
	 * Checks if is undefined profile tag annotation.
	 *
	 * @param umlAnnotation
	 *            the uml annotation
	 * @param undefinedAnnotation
	 *            the undefined annotation
	 * @return true, if is undefined profile tag annotation
	 */
	private boolean isUndefinedProfileTagAnnotation(final EAnnotation umlAnnotation, final EAnnotation undefinedAnnotation) {
		Resource resource = umlAnnotation.eResource();
		PapyrusDefinitionAnnotation parsedAnnotation = PapyrusDefinitionAnnotation.parseEAnnotation(undefinedAnnotation);
		return IPapyrusVersionConstants.PAPYRUS_EANNOTATION_SOURCE.equals(undefinedAnnotation.getSource()) && PapyrusDefinitionAnnotation.UNDEFINED_ANNOTATION.equals(parsedAnnotation) && resource instanceof UMLResource;
	}

	/**
	 * Gets the root model.
	 *
	 * @param element
	 *            the element
	 * @return the root model
	 */
	private EObject getRootModel(final EObject element) {
		EObject rootElement = null;
		try {
			ModelSet modelSet = ServiceUtilsForEObject.getInstance().getModelSet(element);
			UmlModel umlModel = (UmlModel) modelSet.getModel(UmlModel.MODEL_ID);
			rootElement = umlModel.lookupRoot();
		} catch (ServiceException exception) {
			Activator.log.error(MODEL_SET_SERVICE_ERROR, exception);
		} catch (NotFoundException exception) {
			Activator.log.error(ROOT_MODEL_ACCESS_ERROR, exception);
		}
		return rootElement;
	}

	/**
	 * Undecorate profile.
	 *
	 * @param profile
	 *            the profile
	 */
	private void undecorateProfile(final Profile profile) {
		try {
			DecorationService decorationService = ServiceUtilsForEObject.getInstance().getService(DecorationService.class, profile);
			IProfileDefinitionService definitionService = ServiceUtilsForEObject.getInstance().getService(IProfileDefinitionService.class, profile);
			decorationService.removeDecoration(definitionService.getMarker(profile).toString());
			definitionService.disposeMarker(profile);
		} catch (ServiceException exception) {
			Activator.log.error(UNDECORATION_ACTION_ERROR, exception);
		}
	}

	/**
	 * Decorate profile.
	 *
	 * @param profile
	 *            the profile
	 */
	private void decorateProfile(Profile profile) {
		try {
			ServiceUtilsForEObject serviceUtils = ServiceUtilsForEObject.getInstance();
			DecorationService decorationService = serviceUtils.getService(DecorationService.class, profile);
			IProfileDefinitionService definitionService = serviceUtils.getService(IProfileDefinitionService.class, profile);
			decorationService.addDecoration(definitionService.getMarker(profile), profile);
		} catch (ServiceException exception) {
			Activator.log.error(DECORATION_ACTION_ERROR, exception);
		}
	}


	/**
	 * Checks if Profile is already tag.
	 *
	 * @param umlAnnotation
	 *            the uml annotation
	 * @return true, if is already tag
	 */
	private boolean isAlreadyTag(final EAnnotation umlAnnotation) {
		boolean alreadyTag = !umlAnnotation.getEAnnotations().isEmpty();

		if (alreadyTag) {
			EAnnotation firstAnnotation = umlAnnotation.getEAnnotation(IPapyrusVersionConstants.PAPYRUS_EANNOTATION_SOURCE);
			alreadyTag = null != firstAnnotation;
		}

		return alreadyTag;
	}

}
