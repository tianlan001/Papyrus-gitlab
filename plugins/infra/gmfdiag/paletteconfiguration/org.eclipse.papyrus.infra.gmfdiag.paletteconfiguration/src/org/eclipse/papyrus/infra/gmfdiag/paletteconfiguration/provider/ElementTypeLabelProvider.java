/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Label Provider for {@link ElementTypeSetConfiguration} model used in palette configuration.
 */
public class ElementTypeLabelProvider extends LabelProvider implements IStyledLabelProvider {
	/** default type bundle id. */
	private String typesEditBundle = "org.eclipse.papyrus.infra.types.edit"; //$NON-NLS-1$

	/** Icon path for {@link SpecializationTypeConfiguration} */
	private String SpecializationTypeConfigurationIconPath = "/icons/full/obj16/SpecializationTypeConfiguration.gif"; //$NON-NLS-1$

	/** Icon path for {@link ElementTypeSetConfiguration} */
	private String ElementTypeSetConfigurationIconPath = "/icons/full/obj16/ElementTypeSetConfiguration.gif"; //$NON-NLS-1$

	/** Icon path for {@link SpecializationTypeConfiguration} */
	private String MetamodelTypeConfigurationIconPath = "/icons/full/obj16/SpecializationTypeConfiguration.gif"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		return getStyledText(element).getString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element) {
		Image image = null;
		if (element instanceof ElementTypeSetConfiguration) {
			image = Activator.getDefault().getImage(typesEditBundle, ElementTypeSetConfigurationIconPath);
		} else if (element instanceof MetamodelTypeConfiguration) {
			image = Activator.getDefault().getImage(typesEditBundle, MetamodelTypeConfigurationIconPath);
		} else if (element instanceof SpecializationTypeConfiguration) {
			image = Activator.getDefault().getImage(typesEditBundle, SpecializationTypeConfigurationIconPath);
		}
		return image;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(final Object element) {
		StyledString text = new StyledString();

		if (element instanceof ElementTypeSetConfiguration && null != ((ElementTypeSetConfiguration) element).getName()) {
			text.append(((ElementTypeSetConfiguration) element).getName());
			text.append(" - " + ((ElementTypeSetConfiguration) element).getIdentifier(), StyledString.QUALIFIER_STYLER);//$NON-NLS-1$
		} else if (element instanceof ElementTypeConfiguration && null != ((ElementTypeConfiguration) element).getName()) {
			text.append(((ElementTypeConfiguration) element).getName());
			text.append(" - " + ((ElementTypeConfiguration) element).getIdentifier(), StyledString.QUALIFIER_STYLER);//$NON-NLS-1$
		} else {
			text.append(super.getText(element));
		}
		return text;
	}
}