/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.uml2.uml.TemplateParameter;

/**
 * this a abstract editpart use to add listeners
 */
public class AbstractTemplateParameterEditPart extends UMLCompartmentEditPart {

	protected static final String LISTEN_OWNED_PARAM = "ListenOwnedParam"; //$NON-NLS-1$

	public AbstractTemplateParameterEditPart(EObject model) {
		super(model);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		super.activate();
		addOwnedParamListeners();
	}

	/**
	 * to listen parameters
	 */
	protected void addOwnedParamListeners() {
		EObject ownedParam = ((TemplateParameter) resolveSemanticElement()).getOwnedParameteredElement();
		if (ownedParam != null) {
			addListenerFilter(LISTEN_OWNED_PARAM, this, ownedParam);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		removeOwnedParamListeners();
		super.deactivate();
	}

	/**
	 * to stop listening parameters
	 */
	protected void removeOwnedParamListeners() {
		removeListenerFilter(LISTEN_OWNED_PARAM);
	}
}
