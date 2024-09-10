/**
 * Copyright (c) 2014 CEA LIST.
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
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.advice;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomRegionPreDeleteCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.uml2.uml.Region;

/**
 * <pre>
 * This HelperAdvice resizes other regions of a state-machine or composite state, if a region gets deleted
 * </pre>
 *
 * @since 3.1
 */
public class DeleteRegionHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getBeforeDestroyElementCommand(DestroyElementRequest request) {
		EObject destructee = request.getElementToDestroy();

		Set<View> viewsToDestroy = new HashSet<>();

		if (destructee instanceof Region) {
			viewsToDestroy = CrossReferencerUtil.getCrossReferencingViews(destructee, PackageEditPart.MODEL_ID);
		}

		// return the command to destroy all these views
		if (!viewsToDestroy.isEmpty()) {
			ICommand result = null;
			for (View view : viewsToDestroy) {
				result = CompositeCommand.compose(result,
						new CustomRegionPreDeleteCommand(request.getEditingDomain(), view));
			}
			return result;
		}
		return null;
	}
}
