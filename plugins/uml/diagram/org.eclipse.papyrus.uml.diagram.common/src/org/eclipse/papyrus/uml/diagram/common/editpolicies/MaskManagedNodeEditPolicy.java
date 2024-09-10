/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - Update getUMLElement()
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.common.helper.PortLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.PropertyLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * Specific edit policy for to allow nodes to have mash managed that can be consulted by delegated mask managed.
 */
public class MaskManagedNodeEditPolicy extends AbstractMaskManagedEditPolicy {

	/**
	 * Creates a new PropertyLabelEditPolicy
	 */
	public MaskManagedNodeEditPolicy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAdditionalListeners() {
		super.addAdditionalListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getDefaultDisplayValue() {
		if (getUMLElement() instanceof Port) {
			return ICustomAppearance.DEFAULT_UML_PORT;
		}
		return ICustomAppearance.DEFAULT_UML_PROPERTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		if (getUMLElement() instanceof Port) {
			return PortLabelHelper.getInstance().getMasks();
		}

		return PropertyLabelHelper.getInstance().getMasks();
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public Property getUMLElement() {
		EObject element = super.getUMLElement();
		if (element instanceof Property) {
			return (Property) element;
		}
		return null;
	}

	@Override
	public void refreshDisplay() {
		// do nothing
		// there are label that are will be refreshed

	}


}
