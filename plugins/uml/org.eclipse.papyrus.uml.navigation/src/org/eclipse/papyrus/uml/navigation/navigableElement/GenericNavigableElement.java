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
 *   Shuai Li (CEA LIST) shuai.li@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.navigation.navigableElement;

import java.util.Collections;

import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.navigation.service.ExtendedNavigableElement;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.papyrus.infra.widgets.util.NavigationTarget;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Navigates from a NamedElementdElement to its NamedElement declaration
 * 
 */
public class GenericNavigableElement implements ExtendedNavigableElement {

	protected Element element;

	/**
	 *
	 * @param element
	 *            The NamedElement to navigate to. May be null.
	 */
	public GenericNavigableElement(Element element) {
		this.element = element;
	}

	public String getLabel() {
		String label = "Go to element" + getElementLabel() + "...";
		return label;
	}

	public String getDescription() {
		return "Go to the element " + getElementLabel();
	}

	protected String getElementLabel() {
		if (element == null) {
			return " (Undefined)";
		} else if (element instanceof NamedElement) {
			if (((NamedElement) element).getName() == null || ((NamedElement) element).getName().isEmpty()) {
				return " (Unnamed)"; // Often happens for Associations, as their name is derived in the UI
			} else {
				return " (" + UMLLabelInternationalization.getInstance().getLabel((NamedElement) element) + ")";
			}
		} else {
			return " (Unnamed)";
		}
	}

	@Deprecated
	public void navigate(IRevealSemanticElement navigationContext) {
		if (!isEnabled()) {
			return;
		}

		navigationContext.revealSemanticElement(Collections.singletonList(element));
	}

	public Image getImage() {
		if (element == null) {
			return null;
		}

		try {
			return ServiceUtilsForEObject.getInstance().getServiceRegistry(element).getService(LabelProviderService.class).getLabelProvider().getImage(element);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Enabled when the element is defined
	 */
	public boolean isEnabled() {
		return element != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean navigate(NavigationTarget navigationContext) {
		if (!isEnabled()) {
			return false;
		}
		return navigationContext.revealElement(element);
	}

	/**
	 * Returns the element (UML Element) of the NamedElementdNavigableElement
	 * @return element
	 */
	public Element getElement() {
		return this.element;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigableElement#getSemanticElement()
	 *
	 * @return
	 */
	public Object getSemanticElement() {
		return getElement();
	}
}
