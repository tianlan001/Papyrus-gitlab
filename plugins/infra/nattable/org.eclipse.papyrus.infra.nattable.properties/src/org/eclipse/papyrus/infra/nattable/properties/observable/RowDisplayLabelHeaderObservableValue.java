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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.fr - Bug 516314
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.properties.observable;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.nebula.widgets.nattable.util.ClientAreaAdapter;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.utils.NatTableFocusUtils;


/**
 * The row observable value for {@link AbstractHeaderAxisConfiguration#displayLabel}
 *
 * @author Vincent Lorenzo
 *
 */
public class RowDisplayLabelHeaderObservableValue extends AbstractRowHeaderAxisConfigurationObservableValue {

	/** The nattable model manager, used to hide/show the Display label of row. */
	INattableModelManager nattableManager = null;

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 */
	public RowDisplayLabelHeaderObservableValue(final Table table) {
		super(table, NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayLabel());
		this.nattableManager = NatTableFocusUtils.getInstance().getCurrentNattableModelManager();
	}

	/**
	 *
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 *
	 * @return
	 */
	@Override
	public Object getValueType() {
		return EcorePackage.eINSTANCE.getEBoolean();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void postDoSetValue() {
		// If the current nattable manager is a tree one, show/hide the label column according to the Display Label flag
		if (this.nattableManager instanceof TreeNattableModelManager) {

			final TreeNattableModelManager treeNattableManager = (TreeNattableModelManager) this.nattableManager;
			final ClientAreaAdapter clientAreaAdapter = new ClientAreaAdapter(treeNattableManager.getRowHeaderLayerStack().getViewportLayer().getClientAreaProvider());

			// If display label is turned on, set width of slider composite to the relevant one to show the label column,
			// otherwise, set width to 0 to hide it
			if (treeNattableManager.getTable().getLocalRowHeaderAxisConfiguration().isDisplayLabel()) {
				clientAreaAdapter.setWidth(treeNattableManager.getWidthOfSliderComposite());
			} else {
				clientAreaAdapter.setWidth(0);
			}

			treeNattableManager.getRowHeaderLayerStack().getViewportLayer().setClientAreaProvider(clientAreaAdapter);
		}
	}
}
