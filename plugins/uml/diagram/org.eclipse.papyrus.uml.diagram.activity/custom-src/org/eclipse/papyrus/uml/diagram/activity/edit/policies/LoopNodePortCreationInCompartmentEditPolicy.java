/*****************************************************************************
 * Copyright (c) 2011 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Collection;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;

import com.google.common.collect.Lists;

/**
 * Aim to redirect pin and edge creation to parent element
 * WARNING : NOT WORKING FOR NOW
 */
public class LoopNodePortCreationInCompartmentEditPolicy extends PinAndEdgeCreationInCompartmentEditPolicy {

	private static Collection<IElementType> elements = null;

	@Override
	protected Collection<IElementType> getElementHandleByParent() {
		if (elements == null) {
			elements = Lists.newArrayList(UMLElementTypes.InputPin_LoopNodeVariableInputShape);
		}
		return elements;
	}
}
