/*****************************************************************************
 * Copyright (c) 2013 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.databinding;

import java.text.NumberFormat;
import java.text.ParseException;

import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.UnitsConverterUtils;



@SuppressWarnings("restriction")
// suppress warning for WorkspaceViewerProperties
public class RulersUnitObservableValue extends IntegerDiagramViewObservableValue {

	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 *            the view
	 * @param store
	 *            the edited preference store
	 */
	public RulersUnitObservableValue(final Diagram view, final IPreferenceStore store) {
		super(view, WorkspaceViewerProperties.RULERUNIT, store);
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.properties.databinding.IntegerDiagramViewObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(final Object value) {
		if (value instanceof Integer) {
			final Integer oldValue = (Integer) doGetValue();
			final IPreferenceStore wsPreferenceStore = getEditedPreferenceStore();
			final Double oldGridSpacing = wsPreferenceStore.getDouble(WorkspaceViewerProperties.GRIDSPACING);


			final String newValue = UnitsConverterUtils.convertUnits(oldValue, (Integer) value, NumberFormat.getInstance().format(oldGridSpacing));

			Number number = null;
			try {
				number = NumberFormat.getInstance().parse(newValue);
			} catch (ParseException e) {
				// nothing to do
				number = null;
				return;
			}

			// we set the new unit value
			super.doSetValue(value);

			// we set the new spacing value
			final double newGridSpacing = number.doubleValue();
			wsPreferenceStore.setValue(WorkspaceViewerProperties.GRIDSPACING, newGridSpacing);
		}
	}

}
