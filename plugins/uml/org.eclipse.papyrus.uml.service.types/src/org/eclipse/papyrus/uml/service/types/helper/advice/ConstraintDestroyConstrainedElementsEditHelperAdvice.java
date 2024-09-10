/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo - bug 493317 
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * This class manages the destruction of the link representing the ConstrainedElement when one of them is removed from the feature
 *
 */
public class ConstraintDestroyConstrainedElementsEditHelperAdvice extends AbstractReferenceDeleteRelationshipEditHelperAdvice {

	@Override
	protected ICommand getBeforeSetCommand(SetRequest request) {
		// TODO, here we must manage the case where a set request is sent instead of a destroy reference request, like done for Comment#annotatedElement by the property view.
		// Currently, this is the class org.eclipse.papyrus.uml.diagram.sequence.SequenceViewDependentsAdvice which intercept the set request
		return super.getBeforeSetCommand(request);
	}


	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#getFeatureElementTypeToEReferenceMap()
	 *
	 * @return
	 */
	@Override
	protected Map<String, EReference> getFeatureElementTypeToEReferenceMap() {
		return Collections.singletonMap(UMLElementTypes.CONSTRAINT_CONSTRAINEDELEMENTS.getId(), UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
	}


	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkSourceEdgeToFindConnectorToDestroy()
	 *
	 * @return
	 */
	@Override
	protected boolean checkSourceEdgeToFindConnectorToDestroy() {
		return true;
	}


	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkTargetEdgeToFindConnectorToDestroy()
	 *
	 * @return
	 */
	@Override
	protected boolean checkTargetEdgeToFindConnectorToDestroy() {
		return false;
	}
}