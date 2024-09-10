/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.xtext.integration.celleditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;

/**
 * ICellEditor implementation that wraps a SWT Text control to
 * support text editing. This is also the default editor in NatTable if you
 * didn't configure something else.
 */
public abstract class AbstractXtextCellEditor extends
		AbstractNatTableXTextCellEditor {

	/**
	 * Creates the default TextCellEditor that does not commit on pressing the
	 * up/down arrow keys and will not move the selection on committing a value
	 * by pressing enter.
	 */
	public AbstractXtextCellEditor(final Table table,
			final Object axisElement,
			final ITableAxisElementProvider elementProvider) {
		super(table, axisElement, elementProvider);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.xtext.integration.celleditor.AbstractNatTableXTextCellEditor#createXTextEditorConfiguration()
	 * 
	 * @return
	 */
	protected DefaultXtextDirectEditorConfiguration createXTextEditorConfiguration() {
		int columnIndex = this.layerCell.getColumnIndex();
		int rowIndex = this.layerCell.getRowIndex();
		Object row = this.elementProvider.getRowElement(rowIndex);
		Object column = this.elementProvider.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);

		return getConfigurationFromEditedEObject(row, column);
	}

	/**
	 * This allow to get the configuration for edited object.
	 * 
	 * @param editedEObject
	 *            The edited object.
	 * @return The {@link DefaultXtextDirectEditorConfiguration} corresponding the the edited object.
	 */
	protected DefaultXtextDirectEditorConfiguration getConfigurationFromEditedEObject(Object row, Object column) {
		if (row instanceof EObject && column instanceof EStructuralFeature) {

			final Object editedEObject = ((EObject) row).eGet((EStructuralFeature) column);

			if (editedEObject != null && editedEObject instanceof EObject) {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				String semanticClassName = ((EObject) editedEObject).eClass().getInstanceClassName();

				String key = IDirectEditorsIds.EDITOR_FOR_ELEMENT
						+ semanticClassName;
				String languagePreferred = store.getString(key);

				if (languagePreferred != null && !languagePreferred.equals("")) { //$NON-NLS-1$
					IDirectEditorConfiguration configuration = DirectEditorsUtil
							.findEditorConfiguration(languagePreferred, (EObject) editedEObject);
					if (configuration instanceof DefaultXtextDirectEditorConfiguration) {

						DefaultXtextDirectEditorConfiguration xtextConfiguration = (DefaultXtextDirectEditorConfiguration) configuration;
						xtextConfiguration.preEditAction(editedEObject);
						return xtextConfiguration;
					}
				}
			}
		}
		return null;
	}
}
