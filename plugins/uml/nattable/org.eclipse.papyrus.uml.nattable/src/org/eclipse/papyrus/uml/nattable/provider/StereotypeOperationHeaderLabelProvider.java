/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.nattable.provider.EMFEOperationHeaderLabelProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.LabelOperationConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.nattable.utils.StereotypeOperationUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Operation;

/**
 * This label provider provides the label for operations of stereotypes displayed in header area
 *
 * @since 5.4
 *
 */
public class StereotypeOperationHeaderLabelProvider extends EMFEOperationHeaderLabelProvider {

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEOperationHeaderLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof OperationIdAxis) {
				final String id = ((OperationIdAxis) object).getElement();
				return id != null && id.startsWith(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX);
			}
		}
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		final ILabelProviderContextElementWrapper wrapper = (ILabelProviderContextElementWrapper) element;
		final IConfigRegistry configRegistry = wrapper.getConfigRegistry();
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final Table table = modelManager.getTable();
		final EObject tableContext = table.getContext();
		final Object value = getWrappedValue(wrapper);
		String alias = ""; //$NON-NLS-1$
		if (value instanceof IAxis) {
			alias = ((IAxis) value).getAlias();
		}

		ILabelProviderConfiguration conf = null;
		if (wrapper instanceof ILabelProviderCellContextElementWrapper) {
			conf = getLabelConfiguration((ILabelProviderCellContextElementWrapper) wrapper);
		}

		String returnedValue = null;
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayLabel()) {
			returnedValue = ""; //$NON-NLS-1$
		} else {
			String id = AxisUtils.getPropertyId(value);
			final Operation operation = (Operation) StereotypeOperationUtils.getRealStereotypeOperation(tableContext, id);
			if (operation == null) {
				id = id.replace(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$
				returnedValue = id + " " + "Required Profile not available"; //$NON-NLS-1$
				return returnedValue;
			}
			if (alias != null && !alias.isEmpty()) {
				returnedValue = alias;
			} else {
				returnedValue = UMLLabelInternationalization.getInstance().getLabel(operation);// getLabelProviderService(configRegistry).getLabelProvider(prop).getText(prop);
			}
			if (conf != null) {
				if (alias != null && !alias.equals("")) { //$NON-NLS-1$
					returnedValue = alias;
				}
				returnedValue = getText((OperationLabelProviderConfiguration) conf, configRegistry, returnedValue, operation.getType(), operation.getLower(), operation.getUpper());
			}
		}
		return returnedValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getLabelConfiguration(org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper)
	 */
	@Override
	protected ILabelProviderConfiguration getLabelConfiguration(ILabelProviderCellContextElementWrapper wrapper) {
		ILabelProviderConfiguration conf = null;
		final IConfigRegistry configRegistry = wrapper.getConfigRegistry();
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final Table table = modelManager.getTable();
		if (wrapper instanceof ILabelProviderCellContextElementWrapper) {
			LabelStack labels = wrapper.getConfigLabels();
			if (labels.hasLabel(GridRegion.COLUMN_HEADER)) {
				conf = LabelOperationConfigurationManagementUtils.getUsedColumnOperationLabelConfiguration(table);
			} else if (labels.hasLabel(GridRegion.ROW_HEADER)) {
				conf = LabelOperationConfigurationManagementUtils.getUsedRowOperationLabelConfiguration(table);
			}
		}
		return conf;
	};

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		final Object value = getWrappedValue((ILabelProviderContextElementWrapper) element);
		final IConfigRegistry configRegistry = ((ILabelProviderContextElementWrapper) element).getConfigRegistry();
		final INattableModelManager modelManager = getAxisContentProvider(configRegistry);
		ILabelProviderConfiguration conf = getLabelConfiguration((ILabelProviderCellContextElementWrapper) element);
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayIcon()) {
			return null;
		}

		final EObject tableContext = modelManager.getTable().getContext();
		final String id = AxisUtils.getPropertyId(value);
		final Operation operation = (Operation) StereotypeOperationUtils.getRealStereotypeOperation(tableContext, id);
		final ILabelProvider p = getLabelProviderService(configRegistry).getLabelProvider(operation);
		if (p != null) {
			return p.getImage(operation);
		}
		return null;
	}

}
