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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.modelelement;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;

/**
 * The model element factory or palette configuration.
 */
public class PaletteConfigurationModelElementFactory extends EMFModelElementFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EMFModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {
		EMFModelElement modelElement = null;
		EditingDomain domain = EMFHelper.resolveEditingDomain(sourceElement);
		if ("PaletteConfiguration".equals(context.getName()) && sourceElement instanceof PaletteConfiguration) {//$NON-NLS-1$
			modelElement = new PaletteConfigurationModelElement(((PaletteConfiguration) sourceElement), domain);
		} else if ("Configuration".equals(context.getName()) && sourceElement instanceof Configuration) {//$NON-NLS-1$
			// Create the modelElement
			modelElement = new ConfigurationModelElement(((Configuration) sourceElement), domain);
		} else if ("ToolConfiguration".equals(context.getName()) && sourceElement instanceof ToolConfiguration) {//$NON-NLS-1$
			// Create the modelElement
			modelElement = new ConfigurationModelElement(((Configuration) sourceElement), domain);
		} else if ("ElementDescriptor".equals(context.getName()) && sourceElement instanceof ElementDescriptor) {//$NON-NLS-1$
			// Create the modelElement
			modelElement = new ElementDescriptorModelElement(((ElementDescriptor) sourceElement), domain);
		} else {
			modelElement = super.doCreateFromSource(sourceElement, context);
		}
		return modelElement;
	}

}
