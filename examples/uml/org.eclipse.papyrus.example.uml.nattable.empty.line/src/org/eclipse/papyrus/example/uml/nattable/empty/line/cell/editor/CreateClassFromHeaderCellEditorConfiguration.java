/*****************************************************************************
 * Copyright (c) 2020 CEA LIST
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
  *****************************************************************************/

package org.eclipse.papyrus.example.uml.nattable.empty.line.cell.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.IMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.mouse.action.AbstractCellMouseAction;
import org.eclipse.papyrus.infra.nattable.mouse.action.EmptyLineRowHeaderEventMatch;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * We created this configuration to respect the Papyrus Table Pattern, but we are not able to configure the
 * table with a customization registered on header
 *
 * This configuration allows to create a new element from a Single LeftClick on the RowHeader area
 */
public class CreateClassFromHeaderCellEditorConfiguration implements ICellAxisConfiguration {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return "org.eclipse.papyrus.example.uml.nattable.empty.line.cell.editor.CreateClassFromHeaderCellEditorConfiguration"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "Create new Class on empty line"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 */
	@Override
	public boolean handles(Table table, Object axisElement) {
		// return Activator.SCHEDULER_TABLE_KIND_ID.equals(table.getTableKindId());
		// return false, because the framework is not able to register cell editor on header area.
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param axis
	 * @param configLabel
	 */
	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		final INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final NatTable natTable = manager.getAdapter(NatTable.class);
		final UiBindingRegistry uiBindingRegistry = natTable.getUiBindingRegistry();
		uiBindingRegistry.registerSingleClickBinding(getMouseEventMatcher(), getMouseAction());
	}

	/**
	 * @return
	 */
	private IMouseEventMatcher getMouseEventMatcher() {
		return new EmptyLineRowHeaderEventMatch(SWT.None, GridRegion.ROW_HEADER, MouseEventMatcher.LEFT_BUTTON);
	}

	/**
	 * @return
	 */
	private IMouseAction getMouseAction() {
		return new CreateElementCellMouseAction();
	}


	private class CreateElementCellMouseAction extends AbstractCellMouseAction {

		/**
		 * @see org.eclipse.papyrus.infra.nattable.mouse.action.AbstractCellMouseAction#doRun(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent, java.lang.Object, java.lang.Object)
		 *
		 * @param natTable
		 * @param event
		 * @param rowElement
		 * @param columnElement
		 */
		@Override
		public void doRun(NatTable natTable, MouseEvent event, final Object rowElement, final Object columnElement) {
			if (rowElement instanceof ITreeItemAxis && ((ITreeItemAxis) rowElement).getElement() == null) {
				final ITreeItemAxis currentAxis = (ITreeItemAxis) rowElement;
				final ITreeItemAxis parentAxis = currentAxis.getParent();

				final Object parentElement = parentAxis.getElement();
				if (parentElement instanceof TreeFillingConfiguration) {
					IAxis axisProvider = ((TreeFillingConfiguration) parentElement).getAxisUsedAsAxisProvider();
					Object representedElement = AxisUtils.getRepresentedElement(axisProvider);

					// for a real usage, a check about the filter configuration could be better to be sure the created class
					// will appears in the table
					if (UMLPackage.eINSTANCE.getPackage_PackagedElement().equals(representedElement)) {
						// we will create a new class
						final INattableModelManager manager = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
						final EObject creationParent = manager.getTable().getContext();
						if (creationParent instanceof Package) {
							final CreateElementRequest request = new CreateElementRequest(creationParent, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
							final IElementEditService service = ElementEditServiceUtils.getCommandProvider(creationParent);

							final ICommand cmd = service.getEditCommand(request);
							request.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));

						}
					}
				}
			}
		}
	}



}
