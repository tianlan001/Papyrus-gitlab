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
package org.eclipse.papyrus.infra.nattable.common.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.papyrus.infra.nattable.common.editor.AbstractEMFNattableEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * @author Vincent Lorenzo
 *         This handler allows to the user to recreate the table in the editor. It is useful in case of broken table after a user action
 */
public class ReloadNattableWidgetInEditorHandler extends AbstractHandler {

	/**
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractEMFNattableEditor nattableEditor = getNattableEditor(event);
		if (nattableEditor != null) {
			nattableEditor.reloadNattableModelManager();
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			AbstractEMFNattableEditor editor = getNattableEditor(evaluationContext);
			setBaseEnabled(editor != null);
		} else {
			setBaseEnabled(false);
		}
	}

	/**
	 * 
	 * @param event
	 * 
	 * @return
	 * 		the nattable editor or <code>null</code> if not found
	 */
	private AbstractEMFNattableEditor getNattableEditor(Object event) {
		IEditorPart editor = null;
		AbstractEMFNattableEditor nattableEditor = null;
		if (event instanceof IEvaluationContext) {
			Object part =  HandlerUtil.getVariable(event, "activePart"); //$NON-NLS-1$
			if(part instanceof IEditorPart){
				editor = (IEditorPart) part;
			}
		} else if (event instanceof ExecutionEvent) {
			editor = HandlerUtil.getActiveEditor((ExecutionEvent) event);
		}

		if (editor != null) {
			if (editor instanceof AbstractEMFNattableEditor) {
				nattableEditor = (AbstractEMFNattableEditor) editor;
			} else {
				nattableEditor = editor.getAdapter(AbstractEMFNattableEditor.class);
			}
		}
		return nattableEditor;
	}

}
