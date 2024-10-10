/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.languageexpression.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.IEEFWidgetController;
import org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtReferenceUIPlugin;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.Messages;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFLanguageExpressionDescription;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.Activator;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.properties.preferences.LanguageRegistry;
import org.eclipse.papyrus.uml.properties.widgets.DynamicBodyEditor;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.uml2.uml.BodyOwner;

/**
 * This lifecycle manager is used to handle the Language Expression Widget for
 * {@link org.eclipse.uml2.uml.BodyOwner} types.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class LanguageExpressionLifecycleManager extends AbstractEEFWidgetLifecycleManager {

	/**
	 * Index of an invalid selection in the table. This index is used to avoid
	 * selecting an item at the refresh of the table.
	 */
	private static final int INVALID_SELECTION_INDEX = -1;

	/**
	 * The tooltip text for the Edit button.
	 */
	private static final String EDIT_BUTTON_TOOLTIP = "Edit the selected value"; //$NON-NLS-1$

	/**
	 * The string used to initialize Bodies text.
	 */
	private static final String DEFAULT_BODY_STRING = ""; //$NON-NLS-1$

	/**
	 * The dialog message of the "Add Language Dialog" invoked when clicking the Add
	 * button.
	 */
	private static final String ADD_LANGUAGE_DIALOG_MESSAGE = "Create a new Language"; //$NON-NLS-1$

	/**
	 * The dialog title of the "Add Language Dialog" invoked when clicking the Add
	 * button.
	 */
	private static final String ADD_LANGUAGE_DIALOG_TITLE = "New Language"; //$NON-NLS-1$

	/**
	 * The dialog title of the "Browse Language Dialog" invoked when clicking the
	 * Browse button.
	 */
	private static final String BROWSE_LANGUAGES_DIALOG_TITLE = "Language"; //$NON-NLS-1$

	/**
	 * The dialog message of the "Edit Language Dialog" invoked when clicking the
	 * Edit button.
	 */
	private static final String EDIT_LANGUAGE_DIALOG_MESSAGE = "Edit the value of the Language"; //$NON-NLS-1$

	/**
	 * The dialog title of the "Edit Language Dialog" invoked when clicking the Edit
	 * button.
	 */
	private static final String EDIT_LANGUAGE_DIALOG_TITLE = "Edit Language"; //$NON-NLS-1$

	/**
	 * This enumeration is used for the direction of the up and down buttons.
	 */
	private enum Direction {
		/**
		 * Up.
		 */
		UP,

		/**
		 * Down.
		 */
		DOWN
	}

	/**
	 * The composed adapter factory.
	 */
	private ComposedAdapterFactory composedAdapterFactory;

	/**
	 * The description of the language expression.
	 */
	private EEFLanguageExpressionDescription description;
	/**
	 * The widget controller.
	 */
	private LanguageExpressionController controller;

	/**
	 * The main composite which contains the table, buttons and text editor.
	 */
	private Composite mainComposite;

	/**
	 * The table displayed in the left part of the widget. It contains all the
	 * existing languages for the language feature.
	 */
	private TableViewer tableViewer;

	/**
	 * The dynamic text editor displayed in the right part of the widget. It
	 * displays property of the body feature according to the language selected in
	 * the table.
	 */
	private DynamicBodyEditor bodyEditor;

	/**
	 * The up button used to move the selected element up.
	 */
	private Button upButton;

	/**
	 * The down button used to move the selected element down.
	 */
	private Button downButton;

	/**
	 * The browse button used to add a new element to the list by browsing
	 * compatible values.
	 */
	private Button browseButton;

	/**
	 * The add button used to add a new element to the list.
	 */
	private Button addButton;

	/**
	 * The remove button used to delete the selected element in the list.
	 */
	private Button removeButton;

	/**
	 * The edit button used to edit the selected element in the list.
	 */
	private Button editButton;

	/**
	 * The listener for the up button.
	 */
	private ButtonSelectionListener upButtonListener;

	/**
	 * The listener for the down button.
	 */
	private ButtonSelectionListener downButtonListener;

	/**
	 * The listener for the browse button.
	 */
	private ButtonSelectionListener browseButtonListener;

	/**
	 * The listener for the add button.
	 */
	private ButtonSelectionListener addButtonListener;

	/**
	 * The listener for the remove button.
	 */
	private ButtonSelectionListener removeButtonListener;

	/**
	 * The listener for the edit button.
	 */
	private ButtonSelectionListener editButtonListener;

	/**
	 * The double click listener for the table viewer.
	 */
	private IDoubleClickListener tableDoubleClickListener;

	/**
	 * The selection listener for the table viewer.
	 */
	private ISelectionChangedListener tableSelectionChangedListener;

	/**
	 * The main object managed by the Widget.
	 */
	private BodyOwner target;

	/**
	 * The current Expression selected in the widget.
	 */
	private Expression currentExpression;

	/**
	 * The constructor.
	 * 
	 * @param controlDescription    the description of the language expression
	 * @param target                the objet managed by the widget
	 * @param variableManager       the variable manager which contain variables
	 *                              used by Interpreter to evaluate AQL expression
	 * @param interpreter           the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter the adapter used to modify model elements
	 */
	public LanguageExpressionLifecycleManager(EEFLanguageExpressionDescription controlDescription, BodyOwner target,
			IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		super(variableManager, interpreter, editingContextAdapter);
		this.description = controlDescription;
		this.target = target;
	}

	@Override
	protected void createMainControl(Composite parent, IEEFFormContainer formContainer) {
		this.mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout(2, false);
		this.mainComposite.setLayout(mainLayout);
		GridData mainLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.mainComposite.setLayoutData(mainLayoutData);

		createLanguageEditor(mainComposite);
		createBodyEditor(mainComposite);

		this.controller = new LanguageExpressionController(description, variableManager, interpreter,
				editingContextAdapter);
	}

	/**
	 * Creates the language editor, the left part of the widget which contains the
	 * table and the buttons.
	 * 
	 * @param parent the parent composite
	 */
	protected void createLanguageEditor(Composite parent) {
		Composite languageEditorComposite = new Composite(parent, SWT.NONE);
		GridLayout languageEditorLayout = new GridLayout(2, false);
		languageEditorComposite.setLayout(languageEditorLayout);
		GridData languageEditorlayoutData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		languageEditorComposite.setLayoutData(languageEditorlayoutData);

		// Create the table
		int style = SWT.READ_ONLY | SWT.VIRTUAL;
		int selectionStyle = SWT.FULL_SELECTION | SWT.SINGLE;
		int tableStyle = SWT.V_SCROLL | SWT.BORDER;
		style = style | selectionStyle | tableStyle;
		Table table = new Table(languageEditorComposite, style);
		GridData tableGridData = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		tableGridData.widthHint = 150;
		table.setLayoutData(tableGridData);
		this.tableViewer = new TableViewer(table);

		this.composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.tableViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
				new AdapterFactoryLabelProvider.StyledLabelProvider(this.composedAdapterFactory, this.tableViewer)));

		this.tableViewer.setInput(getExpressionsList());
		this.tableSelectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (tableViewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) tableViewer.getSelection();
					Expression selectedExpression = (Expression) structuredSelection.getFirstElement();
					if (selectedExpression != currentExpression) {
						currentExpression = selectedExpression;
						bodyEditor.display(selectedExpression);
					}
				}
				setEnabled(isEnabled());
			}
		};
		this.tableDoubleClickListener = new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (isEnabled()) {
					editingContextAdapter.performModelChange(() -> {
						editButtonCallback();
					});
				}
			}
		};
		this.tableViewer.addSelectionChangedListener(tableSelectionChangedListener);
		this.tableViewer.addDoubleClickListener(tableDoubleClickListener);

		Composite buttonsComposite = this.createButtons(languageEditorComposite);
		Point computedSize = buttonsComposite.computeSize(-1, -1);
		tableGridData.heightHint = computedSize.y - table.getItemHeight();
	}

	/**
	 * Creates the buttons part which allows to manage languages from the table.
	 * 
	 * @param parent the parent composite
	 * @return the created composite
	 */
	protected Composite createButtons(Composite parent) {
		Composite buttonsComposite = new Composite(parent, SWT.NONE);
		GridLayout buttonCompositeGridLayout = new GridLayout(1, false);
		buttonCompositeGridLayout.marginHeight = 0;
		buttonsComposite.setLayout(buttonCompositeGridLayout);
		GridData buttonCompositeGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonsComposite.setLayoutData(buttonCompositeGridData);

		Image upImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.UP_ICON_PATH));
		Image downImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.DOWN_ICON_PATH));
		Image browseImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.BROWSE_ICON_PATH));
		Image addImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.ADD_ICON_PATH));
		Image removeImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.REMOVE_ICON_PATH));
		Image editImage = ExtendedImageRegistry.INSTANCE.getImage(Activator.getDefault().getImage(Activator.EDIT_ICON));

		this.upButton = this.createButton(buttonsComposite, upImage);
		this.downButton = this.createButton(buttonsComposite, downImage);
		this.browseButton = this.createButton(buttonsComposite, browseImage);
		this.addButton = this.createButton(buttonsComposite, addImage);
		this.removeButton = this.createButton(buttonsComposite, removeImage);
		this.editButton = this.createButton(buttonsComposite, editImage);

		return buttonsComposite;
	}

	/**
	 * Creates the body editor, the right part of the widget which contains the text
	 * editor displayed when a language is selected in the table.
	 * 
	 * @param parent the parent composite
	 */
	protected void createBodyEditor(Composite parent) {
		this.bodyEditor = new DynamicBodyEditor(parent, SWT.NONE);

		GridLayout bodyEditorLayout = new GridLayout(1, false);
		this.bodyEditor.setLayout(bodyEditorLayout);
		GridData bodyEditorlayoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.bodyEditor.setLayoutData(bodyEditorlayoutData);

		this.bodyEditor.setContext(new UMLModelElement((EObject) this.target));

		this.bodyEditor.addChangeListener(new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newValue = bodyEditor.getValue();
				if (currentExpression != null) {
					if (newValue == null || currentExpression.getBody().equals(newValue)) {
						return;
					}
					editingContextAdapter.performModelChange(() -> {
						target.getBodies().set(expressionIndex(currentExpression), newValue);
						currentExpression.setBody(newValue);
					});
				}
			}
		});
	}

	/**
	 * Creates a button used to manage the language feature.
	 * 
	 * @param parent the parent composite
	 * @param image  the image of the button
	 * @return the created button
	 */
	protected Button createButton(Composite parent, Image image) {
		Button button = new Button(parent, SWT.NONE);
		button.setImage(image);

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		button.setLayoutData(gridData);

		return button;
	}

	@Override
	protected IEEFWidgetController getController() {
		return this.controller;
	}

	@Override
	protected EEFLanguageExpressionDescription getWidgetDescription() {
		return this.description;
	}

	@Override
	protected Control getValidationControl() {
		return this.mainComposite;
	}

	/**
	 * Refreshes the widget. An item can be selected in the table if it is different
	 * from {@value #INVALID_SELECTION_INDEX}.
	 * 
	 * @param selectionIndice the index of the item to select in the table
	 */
	public void refresh(int selectionIndice) {
		super.refresh();
		this.tableViewer.setInput(getExpressionsList());
		this.tableViewer.refresh();

		this.controller.refresh();
		List<Expression> expressionsList = this.getExpressionsList();
		if (selectionIndice != INVALID_SELECTION_INDEX && selectionIndice < expressionsList.size()) {
			Expression expression = expressionsList.get(selectionIndice);
			this.tableViewer.setSelection(new StructuredSelection(expression), true);
		} else {
			tableViewer.setSelection(null);
		}
	}

	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();

		this.initializeMoveButton();
		this.initializeBrowseButton();
		this.initializeAddButton();
		this.initializeRemoveButton();
		this.initializeEditButton();
	}

	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();

		if (this.tableViewer != null) {
			this.tableViewer.removeSelectionChangedListener(this.tableSelectionChangedListener);
			this.tableViewer.removeDoubleClickListener(this.tableDoubleClickListener);
		}
		this.removeListener(upButton, upButtonListener);
		this.removeListener(downButton, downButtonListener);
		this.removeListener(browseButton, browseButtonListener);
		this.removeListener(addButton, addButtonListener);
		this.removeListener(removeButton, removeButtonListener);
		this.removeListener(editButton, editButtonListener);
	}

	@Override
	protected void setEnabled(boolean isEnabled) {
		Table table = this.tableViewer.getTable();
		boolean manyItems = table.getItemCount() > 1;
		TableItem[] selection = table.getSelection();
		boolean elementIsSelected = selection != null && selection.length > 0;
		if (this.addButton != null && !this.addButton.isDisposed()) {
			this.addButton.setEnabled(isEnabled);
		}
		if (this.removeButton != null && !this.removeButton.isDisposed()) {
			if (elementIsSelected) {
				this.removeButton.setEnabled(isEnabled);
			} else {
				this.removeButton.setEnabled(false);
			}
		}
		if (this.browseButton != null && !this.browseButton.isDisposed()) {
			this.browseButton.setEnabled(isEnabled);
		}
		if (this.editButton != null && !this.editButton.isDisposed()) {
			if (elementIsSelected) {
				this.editButton.setEnabled(isEnabled);
			} else {
				this.editButton.setEnabled(false);
			}
		}
		if (this.upButton != null && !this.upButton.isDisposed()) {
			if (elementIsSelected) {
				boolean isFirstItem = table.indexOf(selection[0]) <= 0;
				if (manyItems && !isFirstItem) {
					this.upButton.setEnabled(isEnabled);
				} else {
					this.upButton.setEnabled(false);
				}
			} else {
				this.upButton.setEnabled(false);
			}
		}
		if (this.downButton != null && !this.downButton.isDisposed()) {
			if (elementIsSelected) {
				boolean isLastItem = table.indexOf(selection[0]) >= table.getItemCount() - 1;
				if (manyItems && !isLastItem) {
					this.downButton.setEnabled(isEnabled);
				} else {
					this.downButton.setEnabled(false);
				}
			} else {
				this.downButton.setEnabled(false);
			}
		}
	}

	/**
	 * Initializes the up and down buttons.
	 */
	protected void initializeMoveButton() {
		this.upButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.moveButtonCallback(Direction.UP));
		this.upButton.addSelectionListener(this.upButtonListener);
		this.upButton.setToolTipText(Messages.ReferenceUpButton_tooltipText);
		this.downButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.moveButtonCallback(Direction.DOWN));
		this.downButton.addSelectionListener(this.downButtonListener);
		this.downButton.setToolTipText(Messages.ReferenceDownButton_tooltipText);
	}

	/**
	 * Initializes the browse button.
	 */
	protected void initializeBrowseButton() {
		this.browseButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.browseButtonCallback());
		this.browseButton.addSelectionListener(this.browseButtonListener);
		this.browseButton.setToolTipText(Messages.ReferenceBrowseButton_tooltipText);
	}

	/**
	 * Initializes the add button.
	 */
	protected void initializeAddButton() {
		this.addButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.addButtonCallback());
		this.addButton.addSelectionListener(this.addButtonListener);
		this.addButton.setToolTipText(Messages.ReferenceAddButton_tooltipText);
	}

	/**
	 * Initializes the remove button.
	 */
	protected void initializeRemoveButton() {
		this.removeButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.removeButtonCallback());
		this.removeButton.addSelectionListener(this.removeButtonListener);
		this.removeButton.setToolTipText(Messages.ReferenceRemoveButton_containmentTooltipText);
	}

	/**
	 * Initializes the edit button.
	 */
	protected void initializeEditButton() {
		this.editButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.editButtonCallback());
		this.editButton.addSelectionListener(editButtonListener);
		this.editButton.setToolTipText(EDIT_BUTTON_TOOLTIP);
	}

	/**
	 * This method is called once a move button is clicked in order to move the
	 * selected element up or down depending of the given direction.
	 * 
	 * @param direction the direction of the button clicked, UP or DOWN
	 */
	private void moveButtonCallback(Direction direction) {
		List<Expression> selectedExpression = this.selectionToList(this.tableViewer.getSelection());

		EList<String> languageList = this.target.getLanguages();
		EList<String> bodyList = this.target.getBodies();
		if (selectedExpression.size() == 1) {
			int expressionIndex = expressionIndex(this.currentExpression);
			int newExpressionIndex;
			if (direction == Direction.UP) {
				newExpressionIndex = Math.max(0, expressionIndex - 1);
			} else {
				newExpressionIndex = Math.min(languageList.size() - 1, expressionIndex + 1);
			}
			languageList.move(newExpressionIndex, expressionIndex);
			bodyList.move(newExpressionIndex, expressionIndex);
			this.refresh(newExpressionIndex);
		}
	}

	/**
	 * This method is called once the browse button is clicked to select an existing
	 * language.
	 */
	private void browseButtonCallback() {
		ListDialog dialog = new ListDialog(Display.getCurrent().getActiveShell()) {
			@Override
			protected int getTableStyle() {
				int style = SWT.READ_ONLY | SWT.VIRTUAL;
				int selectionStyle = SWT.FULL_SELECTION | SWT.MULTI;
				int tableStyle = SWT.V_SCROLL | SWT.BORDER;
				return style | selectionStyle | tableStyle;
			}
		};
		dialog.setTitle(BROWSE_LANGUAGES_DIALOG_TITLE);
		dialog.setInput(LanguageRegistry.instance.getLanguages());
		dialog.setContentProvider(ArrayContentProvider.getInstance());
		dialog.setLabelProvider(new LabelProvider());
		int indexToSelect = INVALID_SELECTION_INDEX;
		if (dialog.open() == Window.OK) {
			Object[] selectedItems = dialog.getResult();
			List<String> addedItems = new ArrayList<>();
			for (Object item : selectedItems) {
				boolean alreadyExists = this.getExpressionsList().stream().anyMatch(e -> e.getLanguage().equals(item));
				if (item instanceof String && !alreadyExists) {
					String selectedLanguage = (String) item;
					addedItems.add(selectedLanguage);
					this.target.getLanguages().add(selectedLanguage);
					this.target.getBodies().add(DEFAULT_BODY_STRING);
				}
			}
			if (addedItems.size() > 0) {
				Expression firstAddedExpression = this.getExpressionsList().stream()
						.filter(e -> e.getLanguage().equals(addedItems.get(0))).findFirst().get();
				indexToSelect = this.expressionIndex(firstAddedExpression);
			}

		} else {
			throw new OperationCanceledException();
		}
		this.refresh(indexToSelect);
	}

	/**
	 * This method is called once the add button is clicked in order to open the
	 * "add language dialog".
	 */
	private void addButtonCallback() {
		InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), ADD_LANGUAGE_DIALOG_TITLE,
				ADD_LANGUAGE_DIALOG_MESSAGE, null, null);
		if (dialog.open() == Window.OK) {
			String newLanguage = dialog.getValue();
			List<Expression> expressionsList = this.getExpressionsList();
			boolean alreadyExists = expressionsList.stream().anyMatch(e -> e.getLanguage().equals(newLanguage));
			if (!alreadyExists) {
				int indexToAdd = expressionsList.size();
				this.target.getLanguages().add(newLanguage);
				this.target.getBodies().add(DEFAULT_BODY_STRING);
				this.refresh(indexToAdd);
			}
		} else {
			throw new OperationCanceledException();
		}
	}

	/**
	 * This method is called once the remove button is clicked in order to remove
	 * the selected element.
	 */
	private void removeButtonCallback() {
		List<Expression> selectedExpressions = this.selectionToList(this.tableViewer.getSelection());
		for (Expression expression : selectedExpressions) {
			int expressionIndex = expressionIndex(expression);
			this.target.getLanguages().remove(expressionIndex);
			this.target.getBodies().remove(expressionIndex);
		}
		this.refresh(INVALID_SELECTION_INDEX);
	}

	/**
	 * This method is called once the edit button is clicked in order to open the
	 * "edit language dialog".
	 */
	private void editButtonCallback() {
		List<Expression> selectedExpressions = this.selectionToList(this.tableViewer.getSelection());
		if (!selectedExpressions.isEmpty()) {
			InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), EDIT_LANGUAGE_DIALOG_TITLE,
					EDIT_LANGUAGE_DIALOG_MESSAGE, this.currentExpression.getLanguage(), null);
			if (dialog.open() == Window.OK) {
				String newLanguage = dialog.getValue();
				int expressionIndex = expressionIndex(this.currentExpression);
				this.target.getLanguages().set(expressionIndex, newLanguage);
				this.refresh(expressionIndex);
			} else {
				throw new OperationCanceledException();
			}
		}
	}

	/**
	 * Creates a list of Expressions to set the input of the table.
	 * 
	 * @return the expressions list
	 */
	private List<Expression> getExpressionsList() {
		List<Expression> expressionsList = new ArrayList<>();
		List<String> languageList = this.target.getLanguages();
		List<String> bodyList = this.target.getBodies();
		for (int i = 0; i < languageList.size(); i++) {
			Expression e = new Expression();
			e.setLanguage(languageList.get(i));
			if (bodyList != null && i < bodyList.size()) {
				e.setBody(bodyList.get(i));
			} else {
				e.setBody(DEFAULT_BODY_STRING);
				this.editingContextAdapter.performModelChange(() -> {
					bodyList.add(DEFAULT_BODY_STRING);
				});
			}
			expressionsList.add(e);
		}
		return expressionsList;
	}

	/**
	 * Returns the index of the expression relative to the languages list in the
	 * feature language. Languages and Bodies (and Expressions) use the same order
	 * as displayed in the table.
	 * 
	 * @param expression the expression to find
	 * @return the index of the expression
	 */
	private int expressionIndex(Expression expression) {
		return this.target.getLanguages().indexOf(expression.getLanguage());
	}

	/**
	 * Returns a list containing all the objects of the given selection.
	 *
	 * @param selection The selection
	 * @return The objects of the given selection
	 */
	private List<Expression> selectionToList(ISelection selection) {
		List<Expression> expressions = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object selectedObject : structuredSelection.toArray()) {
				if (selectedObject instanceof Expression) {
					Expression selectedExpression = (Expression) selectedObject;
					expressions.add(selectedExpression);
				}
			}
		}
		return expressions;
	}

	/**
	 * Removes the given listener from the given button.
	 *
	 * @param button   The button
	 * @param listener The listener to remove
	 */
	protected void removeListener(Button button, ButtonSelectionListener listener) {
		if (button != null && !button.isDisposed()) {
			button.removeSelectionListener(listener);
		}
	}

	/**
	 * Copied from
	 * org.eclipse.eef.ide.ui.ext.widgets.reference.internal.AbstractEEFExtReferenceLifecycleManager.ButtonSelectionListener.
	 * Utility class used to encapsulate the selection listener.
	 *
	 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
	 */
	protected static class ButtonSelectionListener implements SelectionListener {

		/**
		 * The context adapter.
		 */
		private EditingContextAdapter editingContextAdapter;

		/**
		 * The behavior to execute when the button is clicked.
		 */
		private Runnable runnable;

		/**
		 * The constructor.
		 *
		 * @param editingContextAdapter The {@link EditingContextAdapter}
		 * @param runnable              The behavior to execute when the button is
		 *                              clicked
		 */
		public ButtonSelectionListener(EditingContextAdapter editingContextAdapter, Runnable runnable) {
			this.editingContextAdapter = editingContextAdapter;
			this.runnable = runnable;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent event) {
			this.editingContextAdapter.performModelChange(this.runnable);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			this.editingContextAdapter.performModelChange(this.runnable);
		}
	}

}
