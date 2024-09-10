/*****************************************************************************
 * Copyright (c) 2008, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.gef.internal.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.papyrus.infra.ui.editor.CoreMultiDiagramEditor;


/**
 * Provide methods and services needed for Gef Editors.
 * This class is intended to be used as a delegate inside classes providing MultiDiagramEditor.
 *
 * @author dumoulin
 *
 */
public class MultiDiagramEditorGefDelegate implements Supplier<ActionRegistry> {

	private static final Map<CoreMultiDiagramEditor, MultiDiagramEditorGefDelegate> instances = new HashMap<>();

	private final CoreMultiDiagramEditor editor;

	/** The registry holding actions associated to the main editor */
	protected ActionRegistry actionRegistry;

	/** List of listeners on SelectionChanged event */
	private List<ISelectionChangedListener> selectionListeners = new ArrayList<ISelectionChangedListener>(1);


	protected MultiDiagramEditorGefDelegate(CoreMultiDiagramEditor editor) {
		super();

		this.editor = editor;
		editor.onClose(this::dispose);
	}

	static MultiDiagramEditorGefDelegate getInstance(CoreMultiDiagramEditor editor) {
		return instances.computeIfAbsent(editor, MultiDiagramEditorGefDelegate::new);
	}

	/**
	 * This method should be called by the containing class.
	 */
	public void dispose() {
		instances.remove(editor);

		selectionListeners.clear();
		if (actionRegistry != null) {
			actionRegistry.dispose();
		}
	}

	/**
	 * Adds a listener for selection changes in this selection provider. Has no effect if an identical listener is already registered.
	 *
	 * @param listener
	 *            a selection changed listener
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public ActionRegistry get() {
		return getActionRegistry();
	}

	/**
	 * Lazily creates and returns the action registry.
	 *
	 * @return the action registry
	 */
	public ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	/**
	 * Removes the given selection change listener from this selection provider. Has no affect if an identical listener is not registered.
	 *
	 * @param listener
	 *            the selection changed listener to be removed
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	/**
	 * Sets the current selection for this selection provider.
	 *
	 * @param selection
	 *            the new selection
	 */
	public void setSelection(ISelection selection) {
	}

}
