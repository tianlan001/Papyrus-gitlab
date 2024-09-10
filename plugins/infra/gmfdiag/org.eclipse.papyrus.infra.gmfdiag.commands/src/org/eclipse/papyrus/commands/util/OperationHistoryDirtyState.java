/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.commands.util;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying;


/**
 * A utility class for tracking the dirty state of an operation history. It works in the
 * same fashion as the EMF {@link BasicCommandStack}, accounting for operations that {@linkplain INonDirtying do not dirty} the editor.
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.util.OperationHistoryDirtyState} API, instead.
 */
@Deprecated
public class OperationHistoryDirtyState extends org.eclipse.papyrus.infra.emf.gmf.util.OperationHistoryDirtyState.Delegator {

	private OperationHistoryDirtyState(IUndoContext context, IOperationHistory history) {
		super(context, history);
	}

	private OperationHistoryDirtyState(org.eclipse.papyrus.infra.emf.gmf.util.OperationHistoryDirtyState delegate) {
		super(delegate);
	}

	/**
	 * Obtains a new operation history dirty-state tracker. Every result of this call must eventually be {@linkplain #dispose() disposed},
	 * even if it is actually the same instance as returned by an earlier call, because instances are reference-counted.
	 */
	public static OperationHistoryDirtyState newInstance(IUndoContext context, IOperationHistory history) {
		return adapt(getInstance(context, history, OperationHistoryDirtyState::new));
	}

	private static OperationHistoryDirtyState adapt(org.eclipse.papyrus.infra.emf.gmf.util.OperationHistoryDirtyState state) {
		OperationHistoryDirtyState result;

		if (state instanceof OperationHistoryDirtyState) {
			result = (OperationHistoryDirtyState) state;
		} else {
			result = new OperationHistoryDirtyState(state);
		}

		return result;
	}

}
