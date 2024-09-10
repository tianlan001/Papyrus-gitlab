/*
 * Copyright (c) 2014 CEA and others.
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
 *
 */
package org.eclipse.papyrus.infra.ui.editor.reload;


/**
 * An adapter protocol for objects that can provide {@code context}s to be included in the
 * re-load state of dependent parts in an {@link EditorReloadEvent}, for the purpose of
 * restoring the state of those objects after re-load has completed.
 * 
 * @since 1.2
 */
public interface IReloadContextProvider {

	/**
	 * Creates an opaque token from which the receiver can be {@linkplain #restore(Object) restored} after the editor has reloaded.
	 *
	 * @return an opaque editor re-load context, or {@code null} if none is needed on this occasion (for example because the receiver
	 *         is in its default state)
	 */
	Object createReloadContext();

	/**
	 * Reloads the receiver's state from a token previously {@linkplain #createReloadContext() provided}.
	 *
	 * @param reloadContext
	 *            the opaque re-load context token
	 */
	void restore(Object reloadContext);
}
