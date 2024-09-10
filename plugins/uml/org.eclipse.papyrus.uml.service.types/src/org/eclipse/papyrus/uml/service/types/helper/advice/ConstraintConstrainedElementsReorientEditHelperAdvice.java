/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.command.ConstraintConstrainedElementsReorientCommand;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;

public class ConstraintConstrainedElementsReorientEditHelperAdvice extends AbstractFeatureRelationshipReorientEditHelperAdvice {

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractFeatureRelationshipReorientEditHelperAdvice#elementTypeIDToSpecialize()
	 *
	 * @return
	 */
	@Override
	protected String elementTypeIDToSpecialize() {
		return UMLElementTypes.CONSTRAINT_CONSTRAINEDELEMENTS.getId();
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractFeatureRelationshipReorientEditHelperAdvice#getReorientCommand()
	 *
	 * @return
	 */
	@Override
	protected ICommand getFeatureRelationshipReorientCommand(ReorientReferenceRelationshipRequest request) {
		return new ConstraintConstrainedElementsReorientCommand(request);
	}


}
