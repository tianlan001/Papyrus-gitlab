/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.IObservable;


/**
 * An interface for listening "Commit" events on Editors
 * This is used to implement transactions when using some editors (Especially {@link MultipleValueEditor}s)
 * When using a CommitListener, the {@link IObservable} should not directly execute
 * commands when its methods are called, but instead wait for a commit event.
 *
 * @author Camille Letavernier
 */
public interface ICommitListener {

	/**
	 * Indicates that the implementer should apply the list of operations
	 * received since the last commit
	 *
	 * @param editor
	 *            The editor that sent the commit event
	 */
	public void commit(AbstractEditor editor);
}
