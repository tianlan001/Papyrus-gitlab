/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535055
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.richtext.celleditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.widgets.editors.richtext.GenericRichTextEditor;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrus.uml.ui.editors.UMLRichtextEditorWithReferences;
import org.eclipse.papyrus.uml.ui.editors.UMLToolbarConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * @author Vincent Lorenzo
 *
 */
public class RichTextCellEditorWithUMLReferences extends RichTextCellEditor {


	/**
	 * 
	 * Constructor.
	 *
	 */
	public RichTextCellEditorWithUMLReferences() {
		this(SWT.RESIZE);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param style
	 */
	public RichTextCellEditorWithUMLReferences(int style) {
		this(new UMLToolbarConfiguration(), style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param toolbarConfiguration
	 * @param style
	 */
	public RichTextCellEditorWithUMLReferences(UMLToolbarConfiguration toolbarConfiguration, int style) {
		super(toolbarConfiguration, style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param toolbarConfiguration
	 */
	public RichTextCellEditorWithUMLReferences(UMLToolbarConfiguration toolbarConfiguration) {
		this(toolbarConfiguration, SWT.RESIZE);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellEditor#createRichTextEditor(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected RichTextEditor createRichTextEditor(Composite parent) {
		return new UMLRichtextEditorWithReferences(parent, this.style) {
			@Override
			protected int getMinimumHeight() {
				return getMinimumDimension().y;
			}

			@Override
			protected int getMinimumWidth() {
				return getMinimumDimension().x;
			}
		};
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellEditor#activateCell(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 *
	 * @param parent
	 * @param originalCanonicalValue
	 * @return
	 */
	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		Control ctrl = super.activateCell(parent, originalCanonicalValue);
		if (this.editor instanceof GenericRichTextEditor) {

			final List<Object> objects = organizeEditedObjectAndFeature();
			if (null != objects && 2 == objects.size()) {
				final EObject realEditedObject = (EObject) objects.get(0);
				final EStructuralFeature realFeature = (EStructuralFeature) objects.get(1);
				((GenericRichTextEditor) this.editor).configureEdition(realEditedObject, realFeature);
			}
		}
		return ctrl;
	}

	/**
	 * 
	 * @return
	 * 		<code>null</code> or a list containing 2 elements when we found them. The first one is the edited eobject and the second one is the edited feature of the first one.
	 */
	private List<Object> organizeEditedObjectAndFeature() {
		final INattableModelManager manager = this.configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		int columnIndex = this.layerCell.getColumnIndex();
		int rowIndex = this.layerCell.getRowIndex();
		Object row = manager.getRowElement(rowIndex);
		Object column = manager.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);

		Element editedElement = null;
		Object feature = null;

		if (row instanceof EObject && (column instanceof EStructuralFeature || column instanceof String)) {
			editedElement = (Element) row;
			feature = column;
		} else if (column instanceof EObject && (row instanceof EStructuralFeature || row instanceof String)) {
			editedElement = (Element) column;
			feature = row;
		} else {
			return null;
		}


		EStructuralFeature realFeature = null;
		EObject realEditedObject = null;
		if (feature instanceof EStructuralFeature) {
			realFeature = (EStructuralFeature) feature;
			realEditedObject = editedElement;
		} else if (feature instanceof String) {
			final String id = AxisUtils.getPropertyId(feature);
			final List<Stereotype> stereotypesWithEditedFeatureAppliedOnElement = UMLTableUtils.getAppliedStereotypesWithThisProperty(editedElement, id);
			if (1 == stereotypesWithEditedFeatureAppliedOnElement.size()) {
				realEditedObject = editedElement.getStereotypeApplication(stereotypesWithEditedFeatureAppliedOnElement.get(0));
				final Property prop = UMLTableUtils.getRealStereotypeProperty(editedElement, id);
				if (null != prop.getName()) {
					realFeature = realEditedObject.eClass().getEStructuralFeature(prop.getName());
				}
			}
		}
		if (null != realEditedObject && null != realFeature) {
			final List<Object> returnedValue = new ArrayList<Object>();
			returnedValue.add(realEditedObject);
			returnedValue.add(realFeature);
		}
		return null;
	}


	/**
	 * @return
	 * 		the edited feature
	 */
	protected EStructuralFeature getEditedFeature() {
		final List<Object> objects = organizeEditedObjectAndFeature();
		if (null != objects && 2 == objects.size()) {
			return (EStructuralFeature) objects.get(1);
		}
		return null;
	}

	/**
	 * @return
	 * 		the edited element
	 */
	protected Element getEditedElement() {
		final EObject eobject = getEditedEObject();
		if (eobject instanceof Element) {
			return (Element) eobject;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * 		the edited eobject
	 */
	private EObject getEditedEObject() {
		final List<Object> objects = organizeEditedObjectAndFeature();
		if (null != objects && 2 == objects.size()) {
			return (EObject) objects.get(0);
		}
		return null;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellEditor#getMinimumDimension()
	 *
	 * @return
	 */
	@Override
	protected Point getMinimumDimension() {
		return new Point(370, 400);
	}

}
