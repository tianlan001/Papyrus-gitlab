/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.label.provider;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider;
import org.eclipse.papyrus.infra.emf.nattable.registry.EStructuralFeatureImageRegistry;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.LabelConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.nattable.messages.Messages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * The label provider for the stereotype property in the stereotype display tree table
 */
public class StereotypeDisplayPropertyHeaderLabelProvider extends EMFFeatureHeaderLabelProvider {

	/** The string displayed in the header when the required profile is not avalaible */
	public static final String REQUIRED_PROFILE_NOT_AVALAIBLE = Messages.StereotypePropertyHeaderLabelProvider_RequiredProfileNotFound;

	/** the icon for the shared aggregation */
	public static final String AGGREG_SHARED = "icons/aggreg_shared.gif"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(final Object element) {

		if (element instanceof ILabelProviderContextElementWrapper) {
			Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof IAxis) {
				object = ((IAxis) object).getElement();
			}
			return object instanceof Property && ((Property) object).eContainer() instanceof Stereotype && !(object instanceof EStructuralFeature);
		}
		return false;
	}

	/**
	 *
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		final LabelProviderCellContextElementWrapper wrapper = (LabelProviderCellContextElementWrapper) element;
		final IConfigRegistry configRegistry = wrapper.getConfigRegistry();
		final Object value = getWrappedValue(wrapper);
		String alias = ""; //$NON-NLS-1$
		if (value instanceof FeatureAxis) {
			alias = ((FeatureAxis) value).getAlias();
		}

		ILabelProviderConfiguration conf = getLabelConfiguration(wrapper);

		String returnedValue = null;
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayLabel()) {
			returnedValue = ""; //$NON-NLS-1$
		} else {
			Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			Property prop = null;
			if (object instanceof IAxis) {
				prop = (Property) ((IAxis) object).getElement();
			}

			if (prop != null) {
				if (alias != null && !alias.isEmpty()) {
					returnedValue = alias;
				} else {
					returnedValue = UMLLabelInternationalization.getInstance().getLabel(prop);// getLabelProviderService(configRegistry).getLabelProvider(prop).getText(prop);
				}
				if (conf != null) {
					if (alias != null && !alias.equals("")) { //$NON-NLS-1$
						returnedValue = alias;
					}
					returnedValue = getText((FeatureLabelProviderConfiguration) conf, configRegistry, returnedValue, prop.getType(), prop.isDerived(), prop.getLower(), prop.getUpper());
				}
			}
		}
		return returnedValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getLabelConfiguration(org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper)
	 */
	@Override
	protected ILabelProviderConfiguration getLabelConfiguration(final ILabelProviderCellContextElementWrapper wrapper) {
		ILabelProviderConfiguration conf = null;
		final IConfigRegistry configRegistry = wrapper.getConfigRegistry();
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final Table table = modelManager.getTable();
		if (wrapper instanceof ILabelProviderCellContextElementWrapper) {
			LabelStack labels = ((ILabelProviderCellContextElementWrapper) wrapper).getConfigLabels();
			if (labels.hasLabel(GridRegion.COLUMN_HEADER)) {
				conf = LabelConfigurationManagementUtils.getUsedColumnFeatureLabelConfiguration(table);
			} else if (labels.hasLabel(GridRegion.ROW_HEADER)) {
				conf = LabelConfigurationManagementUtils.getUsedRowFeatureLabelConfiguration(table);
			}
		}
		return conf;
	};

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element) {
		ILabelProviderConfiguration conf = getLabelConfiguration((LabelProviderCellContextElementWrapper) element);
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayIcon()) {
			return null;
		}

		Object object = ((ILabelProviderContextElementWrapper) element).getObject();
		Property prop = null;
		if (object instanceof IAxis) {
			prop = (Property) ((IAxis) object).getElement();
		}

		if (prop != null) {
			final Type type = prop.getType();
			if (type instanceof DataType) {
				return EStructuralFeatureImageRegistry.getAttributeIcon();
			}
			switch (prop.getAggregation().getValue()) {
			case AggregationKind.NONE:
				return EStructuralFeatureImageRegistry.getLinkIcon();
			case AggregationKind.COMPOSITE:
				return EStructuralFeatureImageRegistry.getAggregIcon();
			case AggregationKind.SHARED:
				return Activator.getDefault().getImage(org.eclipse.papyrus.uml.nattable.Activator.PLUGIN_ID, AGGREG_SHARED);
			}
		}
		return null;
	}
}
