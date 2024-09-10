/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA LIST.
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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 519381
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.util.ClientAreaAdapter;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;

/**
 * The handler to change the boolean value to display label header for row
 *
 * @author Vincent Lorenzo
 *
 */
public class RowDisplayLabelHeaderHandler extends AbstractRowChangeHeaderConfigurationHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractChangeHeaderConfigurationHandler#getEditedFeature()
	 *
	 * @return
	 */
	@Override
	protected EStructuralFeature getEditedFeature() {
		return NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayLabel();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Overridden to hide/unhide the row label column in the row header of tree table.
	 * </pre>
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		super.execute(event);

		if (getCurrentNattableModelManager() instanceof TreeNattableModelManager) {
			TreeNattableModelManager treeNattableManager = (TreeNattableModelManager) getCurrentNattableModelManager();
			ClientAreaAdapter clientAreaAdapter = new ClientAreaAdapter(treeNattableManager.getRowHeaderLayerStack().getViewportLayer().getClientAreaProvider());

			// If display label is turned on, set width of slider composite to the relevant one to show the label column,
			// otherwise, set width to 0 to hide it
			if (treeNattableManager.getTable().getLocalRowHeaderAxisConfiguration().isDisplayLabel()) {
				clientAreaAdapter.setWidth(treeNattableManager.getWidthOfSliderComposite());
			} else {
				clientAreaAdapter.setWidth(0);
			}

			treeNattableManager.getRowHeaderLayerStack().getViewportLayer().setClientAreaProvider(clientAreaAdapter);
		}

		return null;
	}
}
