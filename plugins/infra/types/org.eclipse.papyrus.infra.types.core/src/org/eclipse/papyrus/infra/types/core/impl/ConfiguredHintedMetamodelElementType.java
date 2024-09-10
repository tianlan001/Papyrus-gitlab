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
package org.eclipse.papyrus.infra.types.core.impl;

import java.net.URL;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredHintedElementType;

public class ConfiguredHintedMetamodelElementType extends MetamodelType implements IConfiguredHintedElementType {

	private String semanticHint;

	private ElementTypeConfiguration configuration;

	public ConfiguredHintedMetamodelElementType(String id, URL iconURL, String displayName, EClass eClass, IEditHelper editHelper, String semanticHint, MetamodelTypeConfiguration configuration) {
		super(id, iconURL, displayName, eClass, editHelper);
		this.semanticHint = semanticHint;
		this.configuration = configuration;
	}

	public String getSemanticHint() {
		return semanticHint;
	}

	public void setSemanticHint(String semanticHint) {
		this.semanticHint = semanticHint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Configured Metamodel Type: " + getDisplayName() + " [" + getId() + "]";
	}

	/**
	 * @see org.eclipse.papyrus.infra.types.IConfiguredHintedElementType#getConfiguration()
	 *
	 * @return
	 */
	@Override
	public ElementTypeConfiguration getConfiguration() {
		return configuration;
	}
}
