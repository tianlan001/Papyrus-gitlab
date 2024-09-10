/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Vincent Lorenzo (CEA-LIST)  - duplicated and adapted code from nattable project.
 *     Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue - Bug 482790, 481835
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.nattable.manager.cell.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.DialogEditHandler;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigHelper;
import org.eclipse.nebula.widgets.nattable.edit.ICellEditHandler;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IEditErrorHandler;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.papyrus.infra.nattable.celleditor.AbstractPapyrusStyledTextCellEditor;
import org.eclipse.papyrus.infra.nattable.converter.StringResolutionProblemPapyrusConverter;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.CrossAxisWrapper;
import org.eclipse.papyrus.infra.widgets.editors.StringEditorWithCompletionWrapper;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.papyrus.uml.nattable.editor.MultiReferenceCellEditor;
import org.eclipse.papyrus.uml.nattable.editor.SingleReferenceValueCellEditor;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrus.uml.tools.util.UMLReferenceConverter;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * {@link ICellEditor} implementation that wraps a SWT {@link Text} control to
 * support text editing. This is also the default editor in NatTable if you
 * didn't configure something else.
 *
 * duplicated and adapted code from nattable project. Add the method {@link #keyPressed(Composite, KeyEvent)} to allow to override it
 */
public class UMLReferenceTextWithCompletionCellEditor extends AbstractPapyrusStyledTextCellEditor {

	/**
	 * The Text control which is the editor wrapped by this TextCellEditor.
	 */
	private StringEditorWithCompletionWrapper textCompletion = null;

	/**
	 * The name resolution helper to use to find UML Element from a string
	 */
	private INameResolutionHelper helper;

	/**
	 * the papyrus parser to use
	 */
	private IPapyrusConverter parser;
	/**
	 * boolean indicating if we are editing a multivalued field
	 */
	private boolean isMultiValued = false;

	/**
	 * The cell editor which allow to use the dialog reference selection.
	 * It must be used when the text completion can't reach the value.
	 * @since 3.0
	 */
	protected AbstractDialogCellEditor referenceValueCellEditor;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The current table.
	 * @param axisElement
	 *            The axis element to manage.
	 * @param elementProvider
	 *            The element provider for the current axis to edit.
	 * @param commitOnUpDown
	 *            Flag to configure whether the editor should commit and move the selection in the corresponding way if the up or down key is pressed.
	 * @param moveSelectionOnEnter
	 *            Flag to configure whether the selection should move after a value was committed after pressing enter.
	 */
	public UMLReferenceTextWithCompletionCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider, final boolean commitOnUpDown, final boolean moveSelectionOnEnter) {
		super(table, axisElement, elementProvider, commitOnUpDown, moveSelectionOnEnter);
	}

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The current table.
	 * @param axisElement
	 *            The axis element to manage.
	 * @param elementProvider
	 *            The element provider for the current axis to edit.
	 * @param commitOnUpDown
	 *            Flag to configure whether the editor should commit and move the selection in the corresponding way if the up or down key is pressed.
	 */
	public UMLReferenceTextWithCompletionCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider, final boolean commitOnUpDown) {
		super(table, axisElement, elementProvider, commitOnUpDown);
	}

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The current table.
	 * @param axisElement
	 *            The axis element to manage.
	 * @param elementProvider
	 *            The element provider for the current axis to edit.
	 */
	public UMLReferenceTextWithCompletionCellEditor(final Table table, final Object axisElement, final ITableAxisElementProvider elementProvider) {
		super(table, axisElement, elementProvider);
	}

	/**
	 * Set the multi valued boolean value.
	 * 
	 * @param isMultivalued
	 *            The multi valued boolean value.
	 */
	public void setIsMultiValued(boolean isMultivalued) {
		this.isMultiValued = isMultivalued;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#activateCell(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 */
	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		final CrossAxisWrapper<EObject, EStructuralFeature> editedElement = UMLTableUtils.getRealEditedObject(layerCell, elementProvider);
		final EObject element = editedElement.getFirstAxis();

		boolean useSimpleDialog = true;

		Control control = null;

		// Bug 482790: Check if this is not an object with a null namespace
		if (!(element instanceof NamedElement && null == ((NamedElement) element).getNamespace())) {
			this.helper = createNameResolutionHelper();
			parser = new StringResolutionProblemPapyrusConverter(new UMLReferenceConverter(this.helper, isMultiValued));
			setPapyrusConverter(parser);

			// Get the display value
			Object displayValue = null;
			if (this.displayConverter != null) {
				displayValue = this.displayConverter.canonicalToDisplayValue(this.layerCell, this.configRegistry, originalCanonicalValue);
			} else {
				displayValue = originalCanonicalValue;
			}

			// Bug 481835 : If the display value is empty and the data value is not null,
			// this means the namespace of the object to edit cannot be resolved (same Objects name? other?).
			// In this case, use the simple reference dialog.
			// Bug 492989 : For the multi-valued feature, the value is not null but empty
			if (displayValue.toString().isEmpty()
					&& ((!isMultiValued && null != layerCell.getDataValue())
							|| (isMultiValued && layerCell.getDataValue() instanceof Collection && !((Collection<?>) layerCell.getDataValue()).isEmpty()))) {
				helper = null;
				parser = null;
				setPapyrusConverter(parser);
			} else {
				useSimpleDialog = false;
			}
		}

		if (useSimpleDialog) {
			// Bug 482790: The parse can't be used because no name resolution helper can't be created
			// So re-initialize the display converter to null
			this.displayConverter = null;

			// The reference dialog used is managed by the SingleReferenceValueCellEditor cell editor

			if (isMultiValued) {
				referenceValueCellEditor = new MultiReferenceCellEditor(axisElement, elementProvider) {

					@Override
					public Control activateCell(Composite parent, Object originalCanonicalValue, EditModeEnum editMode, ICellEditHandler editHandler, ILayerCell cell, IConfigRegistry configRegistry) {
						this.parent = parent;
						this.layerCell = cell;
						this.configRegistry = configRegistry;

						final List<String> configLabels = cell.getConfigLabels().getLabels();
						this.displayConverter = UMLReferenceTextWithCompletionCellEditor.this.displayConverter;
						this.dataValidator = configRegistry.getConfigAttribute(
								EditConfigAttributes.DATA_VALIDATOR,
								DisplayMode.EDIT,
								configLabels);

						this.conversionEditErrorHandler = EditConfigHelper.getEditErrorHandler(
								configRegistry,
								EditConfigAttributes.CONVERSION_ERROR_HANDLER,
								configLabels);
						this.validationEditErrorHandler = EditConfigHelper.getEditErrorHandler(
								configRegistry,
								EditConfigAttributes.VALIDATION_ERROR_HANDLER,
								configLabels);

						this.dialog = createDialogInstance();

						setCanonicalValue(originalCanonicalValue);

						// this method is only needed to initialize the dialog editor, there
						// will be no control to return
						return null;
					}

				};
			} else {
				referenceValueCellEditor = new SingleReferenceValueCellEditor(axisElement, elementProvider) {

					@Override
					public Control activateCell(Composite parent, Object originalCanonicalValue, EditModeEnum editMode, ICellEditHandler editHandler, ILayerCell cell, IConfigRegistry configRegistry) {
						this.parent = parent;
						this.layerCell = cell;
						this.configRegistry = configRegistry;

						final List<String> configLabels = cell.getConfigLabels().getLabels();
						this.displayConverter = UMLReferenceTextWithCompletionCellEditor.this.displayConverter;
						this.dataValidator = configRegistry.getConfigAttribute(
								EditConfigAttributes.DATA_VALIDATOR,
								DisplayMode.EDIT,
								configLabels);

						this.conversionEditErrorHandler = EditConfigHelper.getEditErrorHandler(
								configRegistry,
								EditConfigAttributes.CONVERSION_ERROR_HANDLER,
								configLabels);
						this.validationEditErrorHandler = EditConfigHelper.getEditErrorHandler(
								configRegistry,
								EditConfigAttributes.VALIDATION_ERROR_HANDLER,
								configLabels);

						this.dialog = createDialogInstance();

						setCanonicalValue(originalCanonicalValue);

						// this method is only needed to initialize the dialog editor, there
						// will be no control to return
						return null;
					}

				};
			}

			// Activate the cell of the single reference cell editor
			control = referenceValueCellEditor.activateCell(parent, originalCanonicalValue, EditModeEnum.DIALOG, new DialogEditHandler(), layerCell, configRegistry);
			// Manage the dialog return
			if (Window.OK == referenceValueCellEditor.open()) {
				// The user click on 'OK' button, so set the canonical value
				setCanonicalValue(referenceValueCellEditor.getEditorValue());
				// Commit the value
				commit(MoveDirectionEnum.NONE);
			} else {
				referenceValueCellEditor.close();
			}
		} else {
			control = super.activateCell(parent, originalCanonicalValue);
		}
		return control;
	}

	/**
	 * This allows to set the parser to the display converter.
	 * 
	 * @param parser
	 *            the parser to use to find the references.
	 */
	private void setPapyrusConverter(final IPapyrusConverter parser) {
		if (this.displayConverter instanceof ISetPapyrusConverter) {
			((ISetPapyrusConverter) this.displayConverter).setPapyrusConverter(parser);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#createStyledText(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected StyledText createStyledText(final Composite parent, final int style) {
		this.textCompletion = new StringEditorWithCompletionWrapper(parent, this.parser);
		return this.textCompletion.getTextViewer().getTextWidget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(final KeyEvent event) {
		if (!(isMultiValued && textCompletion.isContentAssistOpened())) {
			super.keyPressed(event);
		}
	}


	/**
	 * This allows to create the name resolution helper managed by the namespace.
	 * 
	 * @return
	 * 		the created name resolution helper
	 */
	protected INameResolutionHelper createNameResolutionHelper() {
		final CrossAxisWrapper<EObject, EStructuralFeature> editedElement = UMLTableUtils.getRealEditedObject(layerCell, elementProvider);
		final EObject element = editedElement.getFirstAxis();
		Element scope;
		if (element instanceof Element) {
			scope = (Element) element;
		} else {
			// it could be a stereotype application
			scope = org.eclipse.uml2.uml.util.UMLUtil.getBaseElement(element);
		}

		INameResolutionHelper helper = null;

		final EStructuralFeature feature = editedElement.getSecondAxis();
		final EClassifier eType = feature.getEType();
		if (eType instanceof EClass) {
			helper = new NameResolutionHelper(scope, (EClass) eType);
		}
		return helper;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractCellEditor#getCanonicalValue(org.eclipse.nebula.widgets.nattable.edit.editor.IEditErrorHandler)
	 */
	@Override
	public Object getCanonicalValue(final IEditErrorHandler conversionErrorHandler) {
		Object canonnicalValue = super.getCanonicalValue(conversionErrorHandler);
		if (canonnicalValue instanceof Collection<?>) {
			return canonnicalValue;
		}
		if (isMultiValued) {
			Collection<Object> coll = new ArrayList<Object>();
			coll.add(canonnicalValue);
			return coll;
		} else {
			return canonnicalValue;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#getEditorValue()
	 */
	@Override
	public Object getEditorValue() {
		Object result = null;
		if (null != referenceValueCellEditor) {
			result = referenceValueCellEditor.getEditorValue();
		}
		return null != result ? result : super.getEditorValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractStyledTextCellEditor#close()
	 */
	@Override
	public void close() {
		if (null != this.textCompletion && null != this.textCompletion.getTextWidget() && !this.textCompletion.getTextWidget().isDisposed()) {
			this.textCompletion.getTextWidget().dispose();
		}
		super.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractPapyrusStyledTextCellEditor#getEditedEObject()
	 */
	@Override
	protected EObject getEditedEObject() {
		return null;
	}
}
