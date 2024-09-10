/*****************************************************************************
 * Copyright (c) 2015, 2017, 2018 CEA LIST.
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
 *   Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - bug 528199
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.xtext.valuespecification.celleditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.xtext.integration.celleditor.AbstractXtextCellEditor;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * {@link ICellEditor} implementation that wraps a SWT {@link Text} control to
 * support text editing. This is also the default editor in NatTable if you
 * didn't configure something else.
 */
public class ValueSpecificationCellEditor extends AbstractXtextCellEditor {

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The table.
	 * @param axisElement
	 *            The axis element object.
	 * @param elementProvider
	 *            The element provider.
	 */
	public ValueSpecificationCellEditor(final Table table,
			final Object axisElement,
			final ITableAxisElementProvider elementProvider) {
		super(table, axisElement, elementProvider);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractPapyrusStyledTextCellEditor#getEditedEObject()
	 */
	@Override
	protected EObject getEditedEObject() {
		int columnIndex = this.layerCell.getColumnIndex();
		int rowIndex = this.layerCell.getRowIndex();
		Object row = this.elementProvider.getRowElement(rowIndex);
		Object column = this.elementProvider.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);
		EObject editedEObject = null;
		if (row instanceof EObject && column instanceof EStructuralFeature) {
			if (UMLPackage.Literals.VALUE_SPECIFICATION == ((EStructuralFeature) column)
					.getEType()) {
				editedEObject = (EObject) row;
			}
		} else if (row instanceof EStructuralFeature && column instanceof EObject) {
			if (UMLPackage.Literals.VALUE_SPECIFICATION == ((EStructuralFeature) row)
					.getEType()) {
				editedEObject = (EObject) column;
			}
		}
		return editedEObject;
	}

	/**
	 * This allow to get the configuration for edited object.
	 * 
	 * @param row
	 *            The row element.
	 * @param column
	 *            The column element.
	 * @return The {@link DefaultXtextDirectEditorConfiguration} corresponding
	 *         the the edited object.
	 */
	protected DefaultXtextDirectEditorConfiguration getConfigurationFromEditedEObject(
			final Object row, final Object column) {
		if (row instanceof EObject && column instanceof EStructuralFeature || row instanceof EStructuralFeature && column instanceof EObject) {
			final EStructuralFeature feature = (EStructuralFeature) (column instanceof EStructuralFeature ? column : row);
			final EObject contextElement = (EObject) (row instanceof EObject ? row : column);
			final Object featureValue = contextElement.eGet(feature);
			
			if (featureValue instanceof EObject) {
				IDirectEditorConfiguration directEditorConfiguration = DirectEditorsUtil.findEditorConfiguration(null, (EObject) featureValue);
				if (directEditorConfiguration instanceof DefaultXtextDirectEditorConfiguration) {
					DefaultXtextDirectEditorConfiguration xtextConfiguration = (DefaultXtextDirectEditorConfiguration) directEditorConfiguration;
					xtextConfiguration.preEditAction(featureValue);
					return xtextConfiguration;
				}
			}
		}
		return null;
	}
}
