/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * An advice that tweaks certain requests to provide hints (if necessary) to the read-only advice.
 */
public class UMLReadOnlyHintsAdvice extends AbstractEditHelperAdvice {

	public UMLReadOnlyHintsAdvice() {
		super();
	}

	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		EClass toBeCreated = request.getElementType().getEClass();

		if (UMLPackage.Literals.EXTENSION.isSuperTypeOf(toBeCreated)) {
			handleExtensionRequest(request);
		} else if (UMLPackage.Literals.ASSOCIATION.isSuperTypeOf(toBeCreated)) {
			handleAssociationRequest(request);
		} else if (UMLPackage.Literals.CONNECTOR.isSuperTypeOf(toBeCreated)) {
			handleConnectorRequest(request);
		}

		// We only tweak the request for the benefit of the read-only advice
		return null;
	}

	protected void handleExtensionRequest(CreateRelationshipRequest request) {
		// Metaclasses are never altered by Extensions because the ExtensionEnd is always owned by the Extension
		Class metaclass = TypeUtils.as(request.getSource(), Class.class);
		if ((metaclass != null) && metaclass.isMetaclass() && !request.getParameters().containsKey(RequestParameterConstants.AFFECTS_SOURCE)) {
			request.setParameter(RequestParameterConstants.AFFECTS_SOURCE, false);
		}

		// Could draw it either way
		metaclass = TypeUtils.as(request.getTarget(), Class.class);
		if ((metaclass != null) && metaclass.isMetaclass() && !request.getParameters().containsKey(RequestParameterConstants.AFFECTS_TARGET)) {
			request.setParameter(RequestParameterConstants.AFFECTS_TARGET, false);
		}
	}

	protected void handleAssociationRequest(CreateRelationshipRequest request) {
		Type source = TypeUtils.as(request.getSource(), Type.class);
		if ((source != null) && !request.getParameters().containsKey(RequestParameterConstants.AFFECTS_SOURCE)) {
			request.setParameter(RequestParameterConstants.AFFECTS_SOURCE, source instanceof AttributeOwner);
		}

		Type target = TypeUtils.as(request.getTarget(), Type.class);
		if ((target != null) && !request.getParameters().containsKey(RequestParameterConstants.AFFECTS_TARGET)) {
			request.setParameter(RequestParameterConstants.AFFECTS_TARGET, target instanceof AttributeOwner);
		}
	}

	protected void handleConnectorRequest(CreateRelationshipRequest request) {
		// Connector creation never modifies the connected elements
		request.setParameter(RequestParameterConstants.AFFECTS_SOURCE, false);
		request.setParameter(RequestParameterConstants.AFFECTS_TARGET, false);
	}
}
