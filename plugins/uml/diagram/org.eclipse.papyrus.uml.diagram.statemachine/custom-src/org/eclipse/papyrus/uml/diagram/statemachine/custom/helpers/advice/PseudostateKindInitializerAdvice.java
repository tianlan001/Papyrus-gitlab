/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.advice;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;


/**
 * @since 3.1
 */
public class PseudostateKindInitializerAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		if (false == request.getElementToConfigure() instanceof Pseudostate) {
			return null;
		}
		Pseudostate pseudo = (Pseudostate) request.getElementToConfigure();

		IElementType type = request.getTypeToConfigure();
		if (type == null) {
			return null;
		}

		PseudostateKind kind = getPseudostateKindForType(type);
		if (kind == null) {
			return null;
		}
		return new SetPseudostateKindCommand(kind, pseudo, request);
	}

	protected PseudostateKind getPseudostateKindForType(IElementType type) {
		List<IElementType> superTypes = Arrays.asList(type.getAllSuperTypes());
		if (UMLElementTypes.Pseudostate_InitialShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_InitialShape)) {
			return PseudostateKind.INITIAL_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_JoinShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_JoinShape)) {
			return PseudostateKind.JOIN_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_ForkShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_ForkShape)) {
			return PseudostateKind.FORK_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_ChoiceShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_ChoiceShape)) {
			return PseudostateKind.CHOICE_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_JunctionShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_JunctionShape)) {
			return PseudostateKind.JUNCTION_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_ShallowHistoryShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_ShallowHistoryShape)) {
			return PseudostateKind.SHALLOW_HISTORY_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_DeepHistoryShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_DeepHistoryShape)) {
			return PseudostateKind.DEEP_HISTORY_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_TerminateShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_TerminateShape)) {
			return PseudostateKind.TERMINATE_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_EntryPointShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_EntryPointShape)) {
			return PseudostateKind.ENTRY_POINT_LITERAL;
		}
		if (UMLElementTypes.Pseudostate_ExitPointShape.equals(type) || superTypes.contains(UMLElementTypes.Pseudostate_ExitPointShape)) {
			return PseudostateKind.EXIT_POINT_LITERAL;
		}
		return null;
	}

	private static class SetPseudostateKindCommand extends ConfigureElementCommand {

		private final PseudostateKind myKind;

		protected SetPseudostateKindCommand(PseudostateKind kind, EObject elementToEdit, ConfigureRequest request) {
			super(request);
			myKind = kind;
		}

		@Override
		public boolean canExecute() {
			if (false == getElementToEdit() instanceof Pseudostate) {
				return false;
			}
			if (myKind == null) {
				return false;
			}
			return super.canExecute();
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			if (!canExecute()) {
				return CommandResult.newErrorCommandResult("Command can't be executed");
			}
			Pseudostate pseudo = (Pseudostate) getElementToEdit();
			pseudo.setKind(myKind);

			// we have to reset initialized name because it depends on kind
			// reset old name
			String oldName = pseudo.getName();
			pseudo.setName(null);

			String initializedName = NamedElementUtil.getDefaultNameWithIncrement(pseudo, pseudo.eContainer().eContents());
			initializedName = initializedName != null ? initializedName : oldName;
			pseudo.setName(initializedName);
			return CommandResult.newOKCommandResult();
		}
	}
}