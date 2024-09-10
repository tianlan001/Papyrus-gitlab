/****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;

/**
 * the goal of this class is to store all direct editors and to provides them by taking account
 * object to edit, constraint, and priority
 *
 */
public class DirectEditorRegistry {

	// map of direct editor indexed by priorities
	protected TreeMap<Integer, List<IDirectEditorExtensionPoint>> editorMap = new TreeMap<Integer, List<IDirectEditorExtensionPoint>>();
	// list of objects that can be edited
	protected ArrayList<String> objectToEdits = new ArrayList<String>();

	/**
	 * add a direct editor
	 * if this direct editor is already used as default in preferences, its priority becomes 0
	 *
	 * @param directEditor
	 *            a direct editor, cannot be null
	 */
	public void add(IDirectEditorExtensionPoint directEditor) {
		assert (directEditor != null);
		objectToEdits.add(directEditor.getObjectToEdit());
		Integer priority = directEditor.getPriority();

		// take in account priority of preferences
		String preferedLanguage = Activator.getDefault().getPreferenceStore().getString(IDirectEditorsIds.EDITOR_FOR_ELEMENT + directEditor.getObjectToEdit());

		// if the language equals is store in preferences this is the default direct editor
		if (preferedLanguage.equals(directEditor.getLanguage())) {
			priority = new Integer(0);
		}
		List<IDirectEditorExtensionPoint> currentValue = editorMap.get(priority);
		if (currentValue == null) {
			currentValue = new ArrayList<IDirectEditorExtensionPoint>();
		}

		currentValue.add(directEditor);
		editorMap.put(priority, currentValue);
	}

	/**
	 * put in preferences the editor with the more important priority
	 */
	protected void adaptPreferences() {
		Iterator<String> iter = objectToEdits.iterator();
		IDirectEditorExtensionPoint defaultDirectEditor = null;
		while (iter.hasNext()) {
			String objectToEdit = iter.next();
			defaultDirectEditor = getDefaultDirectEditor(objectToEdit);

			String id = IDirectEditorsIds.EDITOR_FOR_ELEMENT + defaultDirectEditor.getObjectToEdit();
			String language = defaultDirectEditor.getLanguage();
			// if preference set direct editor as default, do nothing
			if (!Activator.getDefault().getPreferenceStore().getString(id).equals(IDirectEditorsIds.SIMPLE_DIRECT_EDITOR)) {
				Activator.getDefault().getPreferenceStore().setValue(id, language);
			}
		}
	}

	@Override
	public String toString() {
		String out = "";
		Iterator<Integer> keyIterator = editorMap.keySet().iterator();

		while (keyIterator.hasNext()) {
			Integer index = keyIterator.next();
			out = out + "\n[" + index + "]" + "=[" + editorMap.get(index) + "]";

		}
		return out;
	}

	/**
	 * used to fill the registry with an array of direct editors
	 *
	 * @param directEditors
	 *            cannot be null
	 */
	public void init(IDirectEditorExtensionPoint[] directEditors) {
		editorMap = new TreeMap<Integer, List<IDirectEditorExtensionPoint>>();
		objectToEdits = new ArrayList<String>();
		for (int i = 0; i < directEditors.length; i++) {
			add(directEditors[i]);

		}
		adaptPreferences();

	}

	/**
	 * get the direct editor with the higher priority for a given object to edit.
	 *
	 * @param ObjectToEdit
	 *            the string that represents the element to edit
	 * @return a direct editor, it can be null
	 */
	public IDirectEditorExtensionPoint getDefaultDirectEditor(String ObjectToEdit) {
		Iterator<Integer> keyIterator = editorMap.keySet().iterator();
		TreeMap<Integer, IDirectEditorExtensionPoint> directEditorExtensionPoints = new TreeMap<Integer, IDirectEditorExtensionPoint>();
		while (keyIterator.hasNext()) {
			Integer index = keyIterator.next();
			Iterator<IDirectEditorExtensionPoint> iter = editorMap.get(index).iterator();
			while (iter.hasNext()) {
				IDirectEditorExtensionPoint directEditorExtensionPoint = iter.next();
				if (directEditorExtensionPoint.getObjectToEdit().equals(ObjectToEdit)) {
					directEditorExtensionPoints.put(directEditorExtensionPoint.getPriority(), directEditorExtensionPoint);
				}

			}
			// if the user add a direct editor with highest priority, do not return the default direct editor but the direct editor with highest priority
			// this will set the highest priority direct editor to the default one
			if (!directEditorExtensionPoints.isEmpty()) {
				return directEditorExtensionPoints.firstEntry().getValue();
			}
		}
		return null;
	}


}
