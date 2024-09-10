/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.properties.constraints;

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.LabelConfigurationManagementUtils;

/**
 * The java constraint to check if the table from the active nattable editor has an object column label configuration.
 * 
 * @since 2.2
 */
public class EObjectInTableHasColumnObjectLabelConfigurationConstraint extends EObjectInTableJavaConstraint {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.properties.constraints.EObjectInTableJavaConstraint#checkMoreConstraints(org.eclipse.papyrus.infra.nattable.model.nattable.Table)
	 */
	@Override
	protected boolean checkMoreConstraints(final Table table) {
		final ObjectLabelProviderConfiguration objectConf = LabelConfigurationManagementUtils.getUsedColumnObjectLabelConfiguration(table);
		final FeatureLabelProviderConfiguration featureConf = LabelConfigurationManagementUtils.getUsedColumnFeatureLabelConfiguration(table);
		return null != objectConf && null == featureConf;
	}

}
