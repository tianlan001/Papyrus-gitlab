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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The cell editor for the DataType with a dialog.
 * @since 3.0
 */
public class DatatypeDialogCellEditor extends AbstractUMLMultiValueCellEditor {

	/**
	 * Constructor.
	 *
	 * @param axisElement
	 *            The axis element.
	 * @param provider
	 *            The table axis element provider.
	 */
	public DatatypeDialogCellEditor(final Object axisElement, final ITableAxisElementProvider provider) {
		super(axisElement, provider);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.editor.AbstractUMLMultiValueCellEditor#createDialogInstance()
	 */
	@Override
	public Object createDialogInstance() {
		int columnIndex = this.layerCell.getColumnIndex();
		int rowIndex = this.layerCell.getRowIndex();
		Object row = this.manager.getRowElement(rowIndex);
		Object column = this.manager.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);
		Element editedElement = null;
		Object feature = null;
		if (row instanceof EObject && column == getAxisElement()) {
			editedElement = (Element) row;
			feature = column;
		} else {
			editedElement = (Element) column;
			feature = row;
		}

		EStructuralFeature realFeature = null;
		EObject realEditedObject = null;
		Stereotype stereotype = null;
		List<Stereotype> stereotypesWithEditedFeatureAppliedOnElement = null;
		if (feature instanceof EStructuralFeature) {
			realFeature = (EStructuralFeature) feature;
			realEditedObject = editedElement;
		} else {
			final String id = AxisUtils.getPropertyId(getAxisElement());
			stereotypesWithEditedFeatureAppliedOnElement = UMLTableUtils.getAppliedStereotypesWithThisProperty(editedElement, id);
			stereotype = stereotypesWithEditedFeatureAppliedOnElement.get(0);
			realEditedObject = editedElement.getStereotypeApplication(stereotypesWithEditedFeatureAppliedOnElement.get(0));
			Property prop = UMLTableUtils.getRealStereotypeProperty(editedElement, id);
			realFeature = realEditedObject.eClass().getEStructuralFeature(prop.getName());
		}
		if (stereotypesWithEditedFeatureAppliedOnElement != null && stereotypesWithEditedFeatureAppliedOnElement.size() > 1) {
			// TODO : not yet managed
		} else {
			this.dialog = createDialog(realEditedObject, realFeature, stereotype, editedElement.eResource().getResourceSet());
		}
		return this.dialog;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.editor.AbstractUMLMultiValueCellEditor#getFactory()
	 */
	@Override
	protected ReferenceValueFactory getFactory() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.editor.AbstractUMLMultiValueCellEditor#getElementSelector(boolean, org.eclipse.jface.viewers.ILabelProvider, org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider)
	 */
	@Override
	protected IElementSelector getElementSelector(final boolean isUnique, final ILabelProvider labelProvider, final IStaticContentProvider contentProvider) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.editor.AbstractUMLMultiValueCellEditor#createDialog(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.uml2.uml.Stereotype, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	@Override
	protected Object createDialog(final EObject realEditedObject, final EStructuralFeature realFeature, final Stereotype stereotype, final ResourceSet resourceSet) {
		return new EObjectTreeDialog(Display.getDefault().getActiveShell(), realEditedObject, realFeature, "DataType Edition") { //$NON-NLS-1$
			@Override
			protected void okPressed() {
				super.okPressed();
				Collection<Object> newValue = new ArrayList<Object>();
				Object[] result = this.getResult();
				for (Object object : result) {
					newValue.add(object);

				}
				DatatypeDialogCellEditor.this.editHandler.commit(newValue, MoveDirectionEnum.NONE);
			}
		};
	}
}
