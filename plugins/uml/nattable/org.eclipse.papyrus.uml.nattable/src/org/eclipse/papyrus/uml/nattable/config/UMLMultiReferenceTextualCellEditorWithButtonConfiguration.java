/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
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
package org.eclipse.papyrus.uml.nattable.config;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.celleditor.AbstractOpenDialogCellEditorButtonAction;
import org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.converter.UMLMultiReferenceDisplayConverter;
import org.eclipse.papyrus.uml.nattable.editor.MultiReferenceCellEditor;
import org.eclipse.papyrus.uml.nattable.manager.cell.editor.UMLReferenceTextWithCompletionCellEditor;
import org.eclipse.papyrus.uml.nattable.messages.Messages;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * 
 * This class provides the configuration to edit UML References single and multi-valued with a text editor
 *
 */
public class UMLMultiReferenceTextualCellEditorWithButtonConfiguration implements IAxisCellEditorConfiguration {

	/**
	 * The id of this editor.
	 */
	public static final String ID = "org.eclipse.papyrus.uml.reference.text.with.completion.editor.and.dialog"; //$NON-NLS-1$
	
	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param object
	 * @return
	 */
	@Override
	public boolean handles(Table table, Object object) {
		Object tmp = AxisUtils.getRepresentedElement(object);
		if (tmp instanceof EReference) {
			EReference ref = (EReference) tmp;
			if (ref.isContainment() || !ref.isChangeable() || ref.isDerived()) {
				return false;
			}
			if (!ref.isMany()) {
				return false;
			}

			// TODO : to test on constraint
			// return type instanceof EClass && UMLPackage.eINSTANCE.getNamedElement().isSuperTypeOf((EClass) type);
			return true;

		} else if (object instanceof String && ((String) object).startsWith(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX)) {
			String str = (String) tmp;
			Property prop = UMLTableUtils.getRealStereotypeProperty(table.getContext(), str);
			if (prop != null) {
				if (prop.isDerived()) {
					return false;
				}
				Type type = prop.getType();
				if (type instanceof DataType) {
					return false;
				}
				if (!(type instanceof NamedElement)) {
					return false;
				}
				if (!prop.isMultivalued()) {
					return false;
				}
				switch (prop.getAggregation()) {
				case NONE_LITERAL:
				case SHARED_LITERAL:
					return true;
				case COMPOSITE_LITERAL:
				default:
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDisplayConvert(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object, org.eclipse.jface.viewers.ILabelProvider)
	 *
	 * @param table
	 * @param axisElement
	 * @param provider
	 * @return
	 */
	@Override
	public IDisplayConverter getDisplayConvert(Table table, Object axisElement, ILabelProvider provider) {
		return new UMLMultiReferenceDisplayConverter();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getCellPainter(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 */
	@Override
	public ICellPainter getCellPainter(Table table, Object axisElement) {
		return new TextPainter();
	}




	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getICellEditor(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider)
	 *
	 * @param table
	 * @param axisElement
	 * @param elementProvider
	 * @return
	 */
	@Override
	public ICellEditor getICellEditor(Table table, final Object axisElement, final ITableAxisElementProvider elementProvider) {
		UMLReferenceTextWithCompletionCellEditor editor = new UMLReferenceTextWithCompletionCellEditor(table, axisElement, elementProvider);
		editor.setIsMultiValued(true);
		AbstractOpenDialogCellEditorButtonAction openDialogConfiguration = new AbstractOpenDialogCellEditorButtonAction() {
			/**
			 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractOpenDialogCellEditorButtonAction#createDialogCellEditor()
			 *
			 * @return
			 */
			@Override
			public AbstractDialogCellEditor createDialogCellEditor() {
				return new MultiReferenceCellEditor(axisElement, elementProvider);
			}

		};
		editor.setOpenDialogCellEditorButtonAction(openDialogConfiguration);
		openDialogConfiguration.setText("..."); //$NON-NLS-1$
		openDialogConfiguration.setTooltipText(Messages.UMLReferenceCellEditorConfiguration_OpenDialogToChooseTheValue);
		return editor;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDisplayMode(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 */
	@Override
	public String getDisplayMode(Table table, Object axisElement) {
		return DisplayMode.EDIT;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getEditorConfigId()
	 *
	 * @return
	 */
	@Override
	public String getEditorConfigId() {
		return ID;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDataValidator(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 */
	@Override
	public IDataValidator getDataValidator(Table table, Object axisElement) {
		return null;// By default, the system will use the DefautDataValidator//new UMLReferenceDataValidator();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getEditorDescription()
	 *
	 * @return
	 */
	@Override
	public String getEditorDescription() {
		return "This configuration provides a Text editor with completion for multi references"; //$NON-NLS-1$
	}


}