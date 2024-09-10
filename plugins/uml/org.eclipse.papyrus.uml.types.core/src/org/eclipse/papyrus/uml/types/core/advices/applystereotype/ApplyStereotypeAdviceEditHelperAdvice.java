/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 530026
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.advices.applystereotype;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.types.core.Activator;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * advice for the {@link ApplyStereotypeAdviceConfiguration}
 */
public class ApplyStereotypeAdviceEditHelperAdvice extends AbstractEditHelperAdvice {


	protected ApplyStereotypeAdviceConfiguration configuration;


	public ApplyStereotypeAdviceEditHelperAdvice(ApplyStereotypeAdviceConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			if (configuration == null) {
				return false;
			}
			if (!(((CreateElementRequest) request).getContainer() instanceof Element)) {
				return false;
			}
			Element container = (Element) ((CreateElementRequest) request).getContainer();
			Package nearestPackage = container.getNearestPackage();
			if (nearestPackage == null) {
				return false;
			}
			List<Profile> appliedProfiles = nearestPackage.getAllAppliedProfiles();
			if (appliedProfiles == null) {
				// no profiles applied, no stereotype can be applied
				return false;
			}
			List<String> appliedProfileNames = new ArrayList<String>();
			for (Profile profile : appliedProfiles) {
				appliedProfileNames.add(profile.getQualifiedName());
			}
			for (StereotypeToApply stereotypeToApply : configuration.getStereotypesToApply()) {
				List<String> requiredProfiles = stereotypeToApply.getRequiredProfiles();
				if (requiredProfiles != null) {
					for (String requiredProfile : requiredProfiles) {
						if (!appliedProfileNames.contains(requiredProfile)) {
							return false;
						}
					}
				}
			}
			return true;
		}

		return true;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#configureRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 */
	@Override
	public void configureRequest(final IEditCommandRequest request) {
		super.configureRequest(request);
		// we go through this method 5 times by stereotyped elements (tested with SysML Block):
		// 4 time with a GetEditContextRequest and 1 time with a ConfigureRequest
		if (false == request instanceof ConfigureRequest) {
			return;
		}

		final List<StereotypeToApply> stereotypes = this.configuration.getStereotypesToApply();
		// we take the last stereotype for the name, to preserving the previous implementation
		// we assume that the naming stereotype is applicable to the future created object
		for (int i = stereotypes.size() - 1; i >= 0; i--) {
			final StereotypeToApply current = stereotypes.get(i);
			if (current.isUpdateName()) {
				request.setParameter(RequestParameterConstants.BASE_NAME_TO_SET, NamedElementUtil.getNameFromQualifiedName(current.getStereotypeQualifiedName()));
				break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		if (configuration == null) {
			return null;
		}
		ICommand resultCommand = null;
		// retrieve eobject
		EObject elementToConfigure = request.getElementToConfigure();
		if (!(elementToConfigure instanceof Element)) {
			return null;
		}

		TransactionalEditingDomain editingDomain = request.getEditingDomain();
		if (editingDomain == null) {
			return null;
		}
		// retrieve edit service to get features from configure command
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(elementToConfigure);
		if (service == null) {
			Activator.log.error(NLS.bind("Impossible to get edit service from element: {0}.", elementToConfigure), null); //$NON-NLS-1$
			return null;
		}

		// for each stereotype, apply stereotype and apply values
		for (StereotypeToApply stereotypeToApply : configuration.getStereotypesToApply()) {
			Stereotype stereotype = ((Element) elementToConfigure).getApplicableStereotype(stereotypeToApply.getStereotypeQualifiedName());

			if (stereotype != null) {

				ICommand applyStereotypeCommand = service.getEditCommand(new ApplyStereotypeRequest((Element) elementToConfigure, stereotype, editingDomain));

				if (resultCommand == null) {
					resultCommand = applyStereotypeCommand;
				} else {
					resultCommand = resultCommand.compose(applyStereotypeCommand);
				}

				// naming is now managed by configuring the request

				// Set values
				for (FeatureToSet featureToSet : stereotypeToApply.getFeaturesToSet()) {
					// retrieve feature value
					ICommand command = getSetStereotypeFeatureValueCommand((Element) elementToConfigure, stereotype, featureToSet.getFeatureName(), featureToSet.getValue(), service, request);
					if (command != null) {
						if (resultCommand == null) {
							resultCommand = command;
						} else {
							resultCommand = resultCommand.compose(command);
						}
					}
				}
			}

		}

		if (resultCommand != null) {
			return resultCommand.reduce();
		}
		return super.getAfterConfigureCommand(request);
	}

	/**
	 * @param elementToConfigure
	 *            the eobject to configure
	 * @param name
	 *            the name of the feature to set
	 * @param value
	 *            the new value of the feature
	 */
	protected ICommand getSetStereotypeFeatureValueCommand(Element elementToConfigure, Stereotype stereotype, String name, FeatureValue featureValue, IElementEditService service, ConfigureRequest configureRequest) {
		if (name == null) {
			Activator.log.debug("No feature name has been set."); //$NON-NLS-1$
			return null;
		}
		if (elementToConfigure.eClass() == null) {
			Activator.log.error(NLS.bind("Impossible to find EClass from EObject: {0}.", elementToConfigure), null); //$NON-NLS-1$
			return null;
		}

		if (configureRequest.getEditingDomain() == null) {
			return null;
		}

		// retrieve structural feature for the element to configure
		TypedElement typedElement = (TypedElement) stereotype.getMember(name, true, UMLPackage.eINSTANCE.getTypedElement());
		if (typedElement == null) {
			Activator.log.error(NLS.bind("Impossible to find feature {0} for eobject {1}.", name, elementToConfigure), null); //$NON-NLS-1$
			return null;
		}
		Object value = getStereotypeValue(elementToConfigure, stereotype, typedElement.getType(), featureValue);

		return service.getEditCommand(new SetStereotypeValueRequest(configureRequest.getEditingDomain(), stereotype, elementToConfigure, name, value));
	}


	/**
	 * @param elementToConfigure
	 * @param stereotype
	 * @param feature
	 * @param featureValue
	 * @return
	 */
	protected Object getStereotypeValue(Element elementToConfigure, Stereotype stereotype, Type type, FeatureValue featureValue) {
		return StereotypeFeatureValueUtils.getValue(elementToConfigure, stereotype, type, featureValue);
	}
}
