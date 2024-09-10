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

package org.eclipse.papyrus.infra.sync.service;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.sync.Activator;
import org.eclipse.papyrus.infra.sync.internal.SyncService;

import com.google.common.base.Function;

/**
 * A sync action that cascades the evaluation of triggers to related objects.
 */
public class CascadeTriggers implements ISyncAction {

	private final ISyncService service = SyncService.getCurrent();

	private Function<Object, ? extends Iterable<?>> cascadeFunction;

	public CascadeTriggers() {
		this(null);
	}

	/**
	 * Initializes me with a cascade function.
	 */
	public CascadeTriggers(Function<Object, ? extends Iterable<?>> cascadeFunction) {
		super();

		this.cascadeFunction = cascadeFunction;
	}

	/**
	 * Assigns a function that I use to compute the objects to which to cascade the
	 * evaluation of synchronization triggers.
	 */
	public void setCascadeFunction(Function<Object, ? extends Iterable<?>> cascadeFunction) {
		this.cascadeFunction = cascadeFunction;
	}

	/**
	 * Evaluates sync triggers on the objects related to the given triggered {@code object} by my
	 * {@linkplain #setCascadeFunction(Function) cascade function}.
	 */
	@Override
	public IStatus perform(ISyncService syncService, Object object) {
		IStatus result = Status.OK_STATUS;

		for (Object next : cascade(object)) {
			IStatus nextResult = service.evaluateTriggers(next);
			if ((nextResult != null) && !nextResult.isOK()) {
				if (result.isOK()) {
					result = nextResult;
				} else if (result.isMultiStatus()) {
					((MultiStatus) result).merge(nextResult);
				} else {
					result = new MultiStatus(Activator.PLUGIN_ID, 0, new IStatus[] { result, nextResult }, "Multiple sync trigger cascade problems occurred.", null);
				}
			}
		}

		return result;
	}

	/**
	 * Obtains the objects on which to evaluate cascaded sync triggers, based on the given {@code triggered} object.
	 * 
	 * @throws IllegalStateException
	 *             if I have no {@linkplain #setCascadeFunction(Function) cascade function} with which
	 *             to compute the cascaded triggers
	 */
	protected Iterable<?> cascade(Object triggered) {
		if (cascadeFunction == null) {
			throw new IllegalStateException("no cascade function"); //$NON-NLS-1$
		}

		return cascadeFunction.apply(triggered);
	}
}
