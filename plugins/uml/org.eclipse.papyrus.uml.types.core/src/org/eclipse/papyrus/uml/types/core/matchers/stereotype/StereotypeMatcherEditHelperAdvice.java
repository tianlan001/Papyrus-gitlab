/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.types.core.Activator;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * An advice that applies the stereotype(s) specified by a stereotype application matcher.
 */
public class StereotypeMatcherEditHelperAdvice extends AbstractEditHelperAdvice {


	protected StereotypeApplicationMatcherConfiguration configuration;


	public StereotypeMatcherEditHelperAdvice(StereotypeApplicationMatcherConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		CreateElementRequest createElement = null;

		if (request instanceof CreateElementRequest) {
			createElement = (CreateElementRequest) request;
		} else if (request instanceof GetEditContextRequest) {
			GetEditContextRequest getEditContext = (GetEditContextRequest) request;
			IEditCommandRequest editRequest = getEditContext.getEditCommandRequest();
			if (editRequest instanceof CreateElementRequest) {
				createElement = (CreateElementRequest) editRequest;
			}
		}

		if (createElement != null) {
			if (configuration == null) {
				return false;
			}

			if (!(createElement.getContainer() instanceof Element)) {
				return false;
			}

			Element container = (Element) createElement.getContainer();
			Package nearestPackage = container.getNearestPackage();
			if (nearestPackage == null) {
				return false;
			}

			String profileURI = configuration.getProfileUri();
			if (profileURI != null && !StereotypeApplicationMatcher.isProfileApplied(container, profileURI)) {
				// If the profile is not applied, then we cannot apply the stereotypes
				return false;
			}

			if (configuration.getStereotypesQualifiedNames().isEmpty()
					|| !configuration.getStereotypesQualifiedNames().stream().allMatch(sqn -> isApplicableInContext(sqn, nearestPackage))) {
				// Some stereotype is unavailable in the context of this package
				return false;
			}
		}

		return true;
	}

	/**
	 * Is a stereotype applicable in the given package {@code context}? This is the case if the given name
	 * is a qualified name, and the profile is applied in the {@code context}, and that profile has a
	 * stereotype of the given name.
	 *
	 * @param stereotypeQualifiedName
	 *            the stereotype qualified name
	 * @param context
	 *            the package in which context created elements would be configured (stereotypes applied) by this advice
	 * @return whether the given stereotype is applicable in this {@code context}
	 */
	private boolean isApplicableInContext(String stereotypeQualifiedName, Package context) {
		int sep = stereotypeQualifiedName == null ? -1 : stereotypeQualifiedName.lastIndexOf(NamedElement.SEPARATOR);
		if (sep < 0) {
			// Not a qualified name
			return false;
		}

		String profileQualifiedName = stereotypeQualifiedName.substring(0, sep);
		String stereotypeName = stereotypeQualifiedName.substring(sep + NamedElement.SEPARATOR.length());
		Profile profile = context.getAppliedProfile(profileQualifiedName, true);
		return profile != null && profile.getOwnedStereotype(stereotypeName) != null;
	}

	@Override
	public void configureRequest(final IEditCommandRequest request) {
		if (!(request instanceof ConfigureRequest)) {
			return;
		}

		// Use the last stereotype for the name to set, similar to the ApplyStereotypeAdvice
		List<String> stereotypeQNames = configuration.getStereotypesQualifiedNames();
		if (!stereotypeQNames.isEmpty()) {
			request.setParameter(RequestParameterConstants.BASE_NAME_TO_SET,
					NamedElementUtil.getNameFromQualifiedName(stereotypeQNames.get(stereotypeQNames.size() - 1)));
		}
	}

	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand result = null;

		if (configuration == null) {
			return result;
		}

		EObject elementToConfigure = request.getElementToConfigure();
		if (!(elementToConfigure instanceof Element)) {
			return result;
		}

		TransactionalEditingDomain editingDomain = request.getEditingDomain();
		if (editingDomain == null) {
			return result;
		}

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(elementToConfigure);
		if (service == null) {
			Activator.log.error(NLS.bind("Cannot get edit service from element: {0}.", elementToConfigure), null); //$NON-NLS-1$
			return result;
		}

		for (String next : configuration.getStereotypesQualifiedNames()) {
			Stereotype stereotype = ((Element) elementToConfigure).getApplicableStereotype(next);
			if (stereotype != null) {
				ICommand applyStereotypeCommand = service.getEditCommand(new ApplyStereotypeRequest((Element) elementToConfigure, stereotype, editingDomain));

				if (result == null) {
					result = applyStereotypeCommand;
				} else {
					result = result.compose(applyStereotypeCommand);
				}
			}

		}

		if (result != null) {
			result = result.reduce();
		}

		return result;
	}


}
