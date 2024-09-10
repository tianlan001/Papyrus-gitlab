/*****************************************************************************
 * Copyright (c) 2010 LIFL & CEA LIST.
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
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.lifecycleevents;

import org.eclipse.ui.part.FileEditorInput;

/**
 * Interface implemented by classes wishing to be notified of the inputChanged
 * event after a call to {@link ISaveAndDirtyService#doSaveAs()}.
 *
 * @author cedric dumoulin
 * @since 1.2
 *
 */
public interface IEditorInputChangedListener {

	/**
	 *
	 * @param fileEditorInput
	 *            The new value of EditorInput
	 */
	public void editorInputChanged(FileEditorInput fileEditorInput);

	/**
	 * Called when the value of the isDirty() flag has changed.
	 */
	public void isDirtyChanged();

}
