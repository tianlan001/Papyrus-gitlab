/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.directeditor;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.AbstractBasicDirectEditorConfiguration;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * This class provides a Specific direct editor configuration to rename TextDocument.
 *
 */
public class TextDocumentDirectEditorConfiguration extends AbstractBasicDirectEditorConfiguration {


	/**
	 * This allows to determinate if the label is set and can be modified.
	 *
	 * @param objectToEdit
	 *            The object to edit.
	 * @return <code>true</code> if this is a label modification, <code>false</code> otherwise.
	 */
	public boolean isLabelSet(final Object objectToEdit) {
		boolean result = false;
		if (objectToEdit instanceof TextDocument) {
			final String documentLabel = LabelInternationalizationUtils.getLabelWithoutSubstract(((TextDocument) objectToEdit), true);
			result = null != documentLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((TextDocument) objectToEdit));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTextToEdit(final Object objectToEdit) {
		String result = null;
		if (objectToEdit instanceof TextDocument) {
			final String documentLabel = LabelInternationalizationUtils.getLabelWithoutSubstract(((TextDocument) objectToEdit), true);
			if (null != documentLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((TextDocument) objectToEdit))) {
				result = documentLabel;
			} else {
				result = ((TextDocument) objectToEdit).getName();
			}
		}

		return null != result ? result : super.getTextToEdit(objectToEdit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParser createDirectEditorParser() {
		return new TextDocumentDirectEditorParser(getTextToEdit(objectToEdit), isLabelSet(objectToEdit));
	}

}
