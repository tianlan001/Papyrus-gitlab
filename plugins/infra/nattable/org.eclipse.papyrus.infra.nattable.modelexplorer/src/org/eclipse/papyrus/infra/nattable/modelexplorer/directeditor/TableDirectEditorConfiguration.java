/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.modelexplorer.directeditor;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.AbstractBasicDirectEditorConfiguration;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Specific direct editor configuration to rename Table.
 */
public class TableDirectEditorConfiguration extends AbstractBasicDirectEditorConfiguration {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTextToEdit(final Object objectToEdit) {
		String result = null;
		if (objectToEdit instanceof Table) {
			final String tableLabel = LabelInternationalization.getInstance().getTableLabelWithoutName(((Table) objectToEdit));
			if (null != tableLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((Table) objectToEdit))) {
				result = tableLabel;
			} else {
				result = ((Table) objectToEdit).getName();
			}
		}

		return null != result ? result : super.getTextToEdit(objectToEdit);
	}

	/**
	 * This allows to determinate if the label is set and can be modified.
	 * 
	 * @param objectToEdit
	 *            The object to edit.
	 * @return <code>true</code> if this is a label modification, <code>false</code> otherwise.
	 */
	public boolean isLabelSet(final Object objectToEdit) {
		boolean result = false;
		if (objectToEdit instanceof Table) {
			final String tableLabel = LabelInternationalization.getInstance().getTableLabelWithoutName(((Table) objectToEdit));
			result = null != tableLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((Table) objectToEdit));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParser createDirectEditorParser() {
		return new TableDirectEditorParser(getTextToEdit(objectToEdit), isLabelSet(objectToEdit));
	}
}
