/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.service.types.command.StereotypePropertyReferenceDestroyCommand;
import org.eclipse.papyrus.uml.types.core.commands.StereotypePropertyReferenceEdgeUtil;

/**
 * This helper provides the command to remove an annotated element from a comment.
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceDestroyEditHelperAdvice extends AbstractReferenceDeleteRelationshipEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkSourceEdgeToFindConnectorToDestroy()
	 */
	@Override
	protected boolean checkSourceEdgeToFindConnectorToDestroy() {
		return false;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkTargetEdgeToFindConnectorToDestroy()
	 */
	@Override
	protected boolean checkTargetEdgeToFindConnectorToDestroy() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#getFeatureElementTypeToEReferenceMap()
	 */
	@Override
	protected Map<String, EReference> getFeatureElementTypeToEReferenceMap() {
		return Collections.singletonMap("org.eclipse.papyrus.umldi.StereotypePropertyReferenceEdge", null);//$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterDestroyReferenceCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest)
	 */
	@Override
	protected ICommand getAfterDestroyReferenceCommand(final DestroyReferenceRequest request) {
		if (StereotypePropertyReferenceEdgeUtil.isStereotypePropertyReferenceEdge(request.getParameters().get(RequestParameterConstants.AFFECTED_VIEW))) {
			return new StereotypePropertyReferenceDestroyCommand(request);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#findConnectorsToDestroy(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest)
	 */
	@Override
	protected List<Connector> findConnectorsToDestroy(final DestroyReferenceRequest request) {
		if (StereotypePropertyReferenceEdgeUtil.isStereotypePropertyReferenceEdge(request.getParameters().get(RequestParameterConstants.AFFECTED_VIEW))) {
			List<Connector> connector = new ArrayList<Connector>();
			Object parameter = request.getParameter(RequestParameterConstants.AFFECTED_VIEW);
			if (parameter instanceof Connector) {
				connector.add((Connector) parameter);
			}
			return connector;
		} else {
			return super.findConnectorsToDestroy(request);
		}
	}

}
