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

package org.eclipse.papyrus.uml.diagram.activity.edit.part;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ObjectFlowNameEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.activity.preferences.IActivityPreferenceConstants;


public class CustomObjectFlowNameEditPart extends ObjectFlowNameEditPart {

	private IPropertyChangeListener preferenceListener;

	private final IPreferenceStore preferenceStore = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();

	/**
	 * add preference listener to enable/disable the label
	 */
	public CustomObjectFlowNameEditPart(View view) {
		super(view);
		// a preference listener to enable/disable the label
		preferenceListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL.equals(event.getProperty())) {
					refreshLabel();
				}
			}
		};
	}

	@Override
	public void activate() {
		super.activate();
		preferenceStore.addPropertyChangeListener(preferenceListener);
	}

	@Override
	public void deactivate() {
		preferenceStore.removePropertyChangeListener(preferenceListener);
		super.deactivate();
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	protected void performDirectEdit() {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (showName) {
			getManager().show();
		}
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	protected void performDirectEdit(Point eventLocation) {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (showName) {
			((TextDirectEditManager) getManager()).show(eventLocation.getSWTPoint());
		}
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	protected void performDirectEdit(char initialCharacter) {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (showName) {
			super.performDirectEdit(initialCharacter);
		} else {
			performDirectEdit();
		}
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	protected void performDirectEditRequest(Request request) {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (!showName) {
			return;
		}
		super.performDirectEditRequest(request);
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	public String getEditText() {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (!showName) {
			return ""; //$NON-NLS-1$
		}
		return super.getEditText();
	}

	/**
	 * consult preference store before displaying label
	 */
	@Override
	protected String getLabelText() {
		// consult preference store before displaying label
		boolean showName = preferenceStore.getBoolean(IActivityPreferenceConstants.PREF_ACTIVITY_EDGE_SHOW_NAME_LABEL);
		if (showName) {
			return super.getLabelText();
		} else {
			return ""; //$NON-NLS-1$
		}
	}
}
