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

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;


/**
 * Utilities for working with undoable operations.
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.util.OperationUtils} API, instead.
 */
@Deprecated
public class OperationUtils {

	/**
	 * Not instantiable by clients.
	 */
	private OperationUtils() {
		super();
	}

	public static boolean anyDirtying(IUndoableOperation[] undoHistory) {
		return org.eclipse.papyrus.infra.emf.gmf.util.OperationUtils.anyDirtying(undoHistory);
	}

	/**
	 * Queries whether an operation is non-dirtying. The only known non-dirtying operations, currently, are those that wrap a {@link AbstractCommand.NonDirtying}.
	 *
	 * @param operation
	 *            an undoable operation
	 *
	 * @return whether it is a non-dirtying operation
	 */
	public static boolean isNonDirtying(IUndoableOperation operation) {
		return org.eclipse.papyrus.infra.emf.gmf.util.OperationUtils.isNonDirtying(operation);
	}

	/**
	 * Obtains the singular EMF {@link Command} that is wrapped by an {@code operation}, if it is a command wrapper of some kind.
	 *
	 * @param operation
	 *            an operation
	 *
	 * @return the {@link Command} that it wraps, or {@code null} if it does not wrap a singular EMF command
	 */
	public static Command unwrap(IUndoableOperation operation) {
		return org.eclipse.papyrus.infra.emf.gmf.util.OperationUtils.unwrap(operation);
	}

	public static boolean isDirty(IUndoableOperation[] undoHistory, IUndoableOperation[] redoHistory, IUndoableOperation savepoint) {
		return org.eclipse.papyrus.infra.emf.gmf.util.OperationUtils.isDirty(undoHistory, redoHistory, savepoint);
	}
}
