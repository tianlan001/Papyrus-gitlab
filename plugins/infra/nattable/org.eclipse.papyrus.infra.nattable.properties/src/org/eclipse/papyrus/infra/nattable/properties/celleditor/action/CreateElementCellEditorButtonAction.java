/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.celleditor.action;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor;
import org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractOpenDialogCellEditorButtonAction;
import org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction;
import org.eclipse.papyrus.infra.nattable.properties.celleditor.PropertyDialogCellEditor;
import org.eclipse.papyrus.infra.nattable.properties.messages.Messages;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * This {@link ICellEditorButtonAction} has a dependency to property view framework, that'w why we store it here, but this class is usable in a Papyrus Nattable editor context, and not only in
 * NatTable Property View
 *
 * @since 2.5
 */
public class CreateElementCellEditorButtonAction extends AbstractOpenDialogCellEditorButtonAction {

	/**
	 * The elements we propose for the creation
	 */
	private List<IElementType> elementsForCreation;

	/**
	 * The selected element type for creation
	 */
	private IElementType selectedElementType = null;

	/**
	 * The context to use for creation
	 */
	private EObject creationContext;

	/**
	 * if <code>true</code> the creation the button is action is available only when the cell is empty
	 */
	private boolean onlyOnEmptyCell;

	/**
	 *
	 * Constructor.
	 *
	 * @param elementsForCreation
	 *            The elements we propose for the creation
	 */
	public CreateElementCellEditorButtonAction(final List<IElementType> elementsForCreation) {
		this(elementsForCreation, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param elementsForCreation
	 *            The elements we propose for the creation
	 * @param onlyOnEmptyCell
	 *            if <code>true</code> the action is proposed only on empty cell
	 */
	public CreateElementCellEditorButtonAction(final List<IElementType> elementsForCreation, final boolean onlyOnEmptyCell) {
		super();
		setImage(Activator.getDefault().getImage("/icons/Add_12x12.gif")); //$NON-NLS-1$
		setText(""); //$NON-NLS-1$
		setTooltipText(Messages.CreateNewElementCellEditorButtonAction_CreateNewElement);
		this.elementsForCreation = elementsForCreation;
		this.onlyOnEmptyCell = onlyOnEmptyCell;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractOpenDialogCellEditorButtonAction#createDialogCellEditor()
	 *
	 * @return
	 */
	@Override
	public AbstractDialogCellEditor createDialogCellEditor() {
		return new PropertyDialogCellEditor();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractOpenDialogCellEditorButtonAction#runAction(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 * @return
	 */
	@Override
	public int runAction(SelectionEvent e) {
		openMenuForCreation(parent);
		if (this.selectedElementType != null) {
			final TransactionalEditingDomain editingDomain = getEditingDomain();
			final CreateElementRequest request = createCreateElementRequest(editingDomain, this.creationContext, this.selectedElementType, null);
			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(this.creationContext);
			if (provider != null) {
				final ICommand cmd = provider.getEditCommand(request);

				final AbstractTransactionalCommand abs = new AbstractTransactionalCommand(editingDomain, "Create and Edit new element", null) { //$NON-NLS-1$

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor arg0, IAdaptable arg1) throws ExecutionException {
						cmd.execute(arg0, arg1);
						CommandResult res = cmd.getCommandResult();
						Object value = res.getReturnValue();
						Object previousCanonicalValue = CreateElementCellEditorButtonAction.this.originalCanonicalValue;
						CreateElementCellEditorButtonAction.this.originalCanonicalValue = value;
						int resIntermediare = CreateElementCellEditorButtonAction.super.runAction(e);
						if (resIntermediare == Window.OK) {
							return CommandResult.newOKCommandResult(value);
						} else {
							setResult(CommandResult.newCancelledCommandResult());
							CreateElementCellEditorButtonAction.this.originalCanonicalValue = previousCanonicalValue;
							return getCommandResult();

						}
					}
				};
				getEditingDomain().getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(abs));

				CommandResult res = abs.getCommandResult();
				if (res.getStatus().isOK()) {
					this.selectedElementType = null;
					return Window.OK;
				}
			}
		}
		this.selectedElementType = null;
		return Window.CANCEL;
	}

	/**
	 *
	 * @param editingDomain
	 *            the editing domain to use to edit the model
	 * @param container
	 *            the container of the created element
	 * @param elementType
	 *            the elementType to use to create a new element
	 * @param containmentFeature
	 *            the feature which will contains the created element
	 * @return
	 *         the request to use to create a new element
	 *
	 */
	protected CreateElementRequest createCreateElementRequest(final TransactionalEditingDomain editingDomain, final EObject container, final IElementType elementType, final EReference containmentFeature) {
		return new CreateElementRequest(editingDomain, container, elementType, containmentFeature);
	}


	/**
	 * This method opens an intermediate menu to choose the element to create (if {@link #elementsForCreation}'s size > 1)
	 *
	 * @param parent
	 *            the parent composite
	 */
	private void openMenuForCreation(final Composite parent) {
		if (elementsForCreation.size() == 0) {
			return;
		}
		if (elementsForCreation.size() == 1) {
			this.selectedElementType = elementsForCreation.get(0);
			return;
		}
		final Menu menu = new Menu(parent);
		final String ELEMENT_TYPE = "elementTypeId"; //$NON-NLS-1$

		for (IElementType current : this.elementsForCreation) {
			final MenuItem item = new MenuItem(menu, SWT.NONE);
			item.setText(current.getDisplayName());
			ImageDescriptor imgDesc = ImageDescriptor.createFromURL(current.getIconURL());
			item.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(imgDesc));

			// item.setImage(current.getImage());
			item.setData(ELEMENT_TYPE, current);
			item.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					setSelectedElementType((IElementType) item.getData(ELEMENT_TYPE));
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// nothing to do
				}
			});
		}

		menu.setVisible(true);

		// The menu is blocking the thread
		Display display = Display.getDefault();
		while (menu.isVisible()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Throwable ex) {
				// Activator.log.error(ex);
			}
		}
		if (!display.isDisposed()) {
			display.update();
		}
	}

	/**
	 *
	 * @param elementType
	 *            the element type to use for creation
	 */
	private void setSelectedElementType(IElementType elementType) {
		selectedElementType = elementType;
	}

	/**
	 *
	 * @param creationContext
	 *            the element to use to create the new element
	 */
	protected void setCreationContext(final EObject creationContext) {
		this.creationContext = creationContext;
	}

	/**
	 * @see fr.sncf.customization.parameters.table.cell.editor.button.ICellEditorButtonAction#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		boolean result = getNattableModelManager() != null && this.creationContext != null && this.elementsForCreation.size() > 0;
		if (result && onlyOnEmptyCell) {
			result = getCurrentCellValue() == null;
		}
		return result;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractCellEditorButtonAction#configureAction(org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor, org.eclipse.swt.widgets.Composite, java.lang.Object,
	 *      org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param editor
	 * @param parent
	 * @param originalCanonicalValue
	 * @param cell
	 * @param configRegistry
	 */
	@Override
	public boolean configureAction(final IActionCellEditor editor, final Composite parent, final Object originalCanonicalValue, final ILayerCell cell, final IConfigRegistry configRegistry) {
		super.configureAction(editor, parent, originalCanonicalValue, cell, configRegistry);
		if (getNattableModelManager() != null && getNattableModelManager().getTable() != null) {
			this.creationContext = getNattableModelManager().getTable().getOwner();
		}
		return isEnabled();
	}

}
