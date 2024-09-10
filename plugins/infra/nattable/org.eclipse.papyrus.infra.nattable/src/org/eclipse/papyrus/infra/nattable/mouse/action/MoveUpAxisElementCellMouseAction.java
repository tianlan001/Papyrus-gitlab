/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.mouse.action;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

/**
 * The action to move up the axis element
 *
 * @since 6.7
 */
public class MoveUpAxisElementCellMouseAction extends AbstractMoveAxisCellMouseAction {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.mouse.action.AbstractMoveAxisCellMouseAction#doMove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, int)
	 *
	 * @param toMove
	 * @param parent
	 * @param feature
	 * @param currentIndex
	 */
	@Override
	void doMove(final EObject toMove, final EObject parent, final EStructuralFeature feature, final int currentIndex) {
		TransactionalEditingDomain d = TransactionUtil.getEditingDomain(toMove);
		MoveCommand move = new MoveCommand(d, parent, feature, toMove, currentIndex - 1);
		d.getCommandStack().execute(move);
	}

}
