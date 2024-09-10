/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;

/**
 * The handler to change the boolean value used to display the image in the row label header
 *
 * @author Vincent Lorenzo
 *
 */
public class RowDisplayLabelImageHandler extends AbstractRowChangeLabelConfigurationValueHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractChangeLabelConfigurationValueHandler#getEditedFeature()
	 *
	 * @return
	 */
	@Override
	protected EStructuralFeature getEditedFeature() {
		return NattablelabelproviderPackage.eINSTANCE.getObjectLabelProviderConfiguration_DisplayIcon();
	}

}
