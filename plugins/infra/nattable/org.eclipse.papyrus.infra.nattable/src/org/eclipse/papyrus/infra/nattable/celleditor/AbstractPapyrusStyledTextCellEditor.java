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
package org.eclipse.papyrus.infra.nattable.celleditor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Abstract Class for StyledText cell editor.
 * This class is used for XText Cell Editor and text editor with completion
 *
 * @author Vincent Lorenzo
 * @deprecated since 6.6, use {@link AbstractActionStyledTextCellEditor} instead
 */
@Deprecated
public abstract class AbstractPapyrusStyledTextCellEditor extends AbstractStyledTextCellEditor {

	/**
	 * the table
	 */
	protected final Table table;

	/**
	 * the axis element on which this cell editor is declared
	 */
	protected final Object axisElement;

	/**
	 * the table element provider
	 */
	protected final ITableAxisElementProvider elementProvider;

	/**
	 * the configuration of the additional button
	 */
	protected AbstractOpenDialogCellEditorButtonAction buttonConfiguration;

	/**
	 * the controller of the editor
	 */
	private Control editorControl;

	/**
	 * the initial value
	 */
	private Object originalValue;

	/**
	 * the styled text
	 */
	private StyledText text;


	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 * @param axisElement
	 * @param elementProvider
	 * @param commitOnUpDown
	 * @param moveSelectionOnEnter
	 */
	public AbstractPapyrusStyledTextCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider, boolean commitOnUpDown, boolean moveSelectionOnEnter) {
		super(commitOnUpDown, moveSelectionOnEnter);
		this.table = table;
		this.axisElement = axisElement;
		this.elementProvider = elementProvider;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 * @param axisElement
	 * @param elementProvider
	 */
	public AbstractPapyrusStyledTextCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider) {
		this(table, axisElement, elementProvider, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 * @param axisElement
	 * @param elementProvider
	 * @param commitOnUpDown
	 */
	public AbstractPapyrusStyledTextCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider, boolean commitOnUpDown) {
		this(table, axisElement, elementProvider, commitOnUpDown, false);
	}

	/**
	 *
	 * @return the edited object
	 */
	protected abstract EObject getEditedEObject();


	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#activateCell(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 *
	 * @param parent
	 * @param originalCanonicalValue
	 * @return
	 */
	@Override
	protected Control activateCell(Composite parent, Object originalCanonicalValue) {
		this.originalValue = originalCanonicalValue;
		Composite realParent = parent;
		if (this.buttonConfiguration != null) {
			realParent = new Composite(parent, SWT.NONE);
		}
		this.text = (StyledText) super.activateCell(realParent, originalCanonicalValue);
		if (this.buttonConfiguration != null) {
			this.editorControl = realParent;
			GridLayout gridLayout = new GridLayout(2, false);
			gridLayout.horizontalSpacing = 0;
			gridLayout.verticalSpacing = 0;
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			realParent.setLayout(gridLayout);

			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			text.setLayoutData(gridData);

			Button b = createAdditionalButton(realParent);
			GridData buttonGridData = new GridData(SWT.RIGHT, SWT.FILL, false, true);
			b.setLayoutData(buttonGridData);
			this.buttonConfiguration.setCellLocation(layerCell.getColumnIndex(), layerCell.getRowIndex());
		} else {
			this.editorControl = text;
		}
		return this.editorControl;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#getEditorControl()
	 *
	 * @return
	 */
	@Override
	public Control getEditorControl() {
		return editorControl;
	}

	/**
	 *
	 * @param composite
	 *            the composite parent
	 * @return
	 *         the created button
	 *
	 */
	protected Button createAdditionalButton(Composite composite) {
		Button button = new Button(composite, SWT.NONE);
		button.setText(this.buttonConfiguration.getText());
		button.setToolTipText(this.buttonConfiguration.getTooltipText());
		button.setImage(this.buttonConfiguration.getImage());
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				runButtonAction();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		return button;
	}


	/**
	 * execute the action declared in the button
	 */
	protected void runButtonAction() {
		if (Window.OK == this.buttonConfiguration.openDialog(parent, originalValue, layerCell, configRegistry)) {
			setCanonicalValue(this.buttonConfiguration.getEditorValue());
			commit(MoveDirectionEnum.NONE);
		} else {
			close();
		}
	}

	/**
	 * This method allow to define the behavior of the an additional button located at the right of the text field in the cell
	 *
	 * @param additionalAction
	 */
	public void setOpenDialogCellEditorButtonAction(AbstractOpenDialogCellEditorButtonAction additionalAction) {
		this.buttonConfiguration = additionalAction;
		if (additionalAction != null) {
			Assert.isNotNull(additionalAction);
		}
	}
}
