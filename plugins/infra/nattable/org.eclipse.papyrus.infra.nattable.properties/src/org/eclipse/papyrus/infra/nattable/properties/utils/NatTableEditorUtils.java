/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) - thanhliem.phan@all4tec.net - Bug 520188
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.utils;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * Utility class for NatTable Editor.
 * 
 * @since 4.0
 */
public class NatTableEditorUtils {

	/**
	 * @return The current nattable editor if it is available or <code>null</code> if not found
	 */
	public static NatTableEditor getCurrentNatTableEditor() {
		NatTableEditor result = null;

		final IEditorPart currentEditor = EditorHelper.getCurrentEditor();
		if (currentEditor instanceof IMultiPageEditorPart && null != ((IMultiPageEditorPart) currentEditor).getActiveEditor()) {
			result = ((IMultiPageEditorPart) currentEditor).getActiveEditor().getAdapter(NatTableEditor.class);
		} else if (currentEditor instanceof IAdaptable) {
			result = ((IAdaptable) currentEditor).getAdapter(NatTableEditor.class);
		}

		return result;
	}

	/**
	 * @return The current nattable model manager if it is available or <code>null</code> if not found
	 */
	public static INattableModelManager getCurrentNatTableModelManager() {
		NatTableEditor nattableEditor = getCurrentNatTableEditor();
		INattableModelManager currentNattableManager = null;

		if (null != nattableEditor) {
			currentNattableManager = (INattableModelManager) nattableEditor.getAdapter(INattableModelManager.class);
		}

		return currentNattableManager;
	}
}
