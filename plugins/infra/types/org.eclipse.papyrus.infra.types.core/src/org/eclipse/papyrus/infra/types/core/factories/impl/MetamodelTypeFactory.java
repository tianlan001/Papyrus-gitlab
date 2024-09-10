/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedMetamodelElementType;

public class MetamodelTypeFactory extends AbstractElementTypeConfigurationFactory<MetamodelTypeConfiguration> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IHintedType createElementType(MetamodelTypeConfiguration configuration) {
		return new ConfiguredHintedMetamodelElementType(getID(configuration), getIconURL(configuration), getDisplayName(configuration), getEClass(configuration), createEditHelper(configuration), getSemanticHint(configuration), configuration);
	}

	protected EClass getEClass(MetamodelTypeConfiguration configuration) {
		return configuration.getEClass();
	}

	protected IEditHelper createEditHelper(MetamodelTypeConfiguration configuration) {
		String editHelperClassName = configuration.getEditHelperClassName();
		if (editHelperClassName == null) {
			return null;
		}
		IEditHelper editHelper = ClassLoaderHelper.newInstance(editHelperClassName, IEditHelper.class, EcoreUtil.getURI(configuration));
		return editHelper;
	}
}
