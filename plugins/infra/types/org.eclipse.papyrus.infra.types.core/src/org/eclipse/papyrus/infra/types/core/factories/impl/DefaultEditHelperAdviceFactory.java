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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.types.EditHelperAdviceConfiguration;

public class DefaultEditHelperAdviceFactory extends AbstractEditHelperAdviceFactory<EditHelperAdviceConfiguration> {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceFactory#getEditHelperAdvice(org.eclipse.papyrus.infra.types.AdviceConfiguration)
	 *
	 * @param adviceConfiguration
	 * @return
	 */
	@Override
	protected IEditHelperAdvice getEditHelperAdvice(EditHelperAdviceConfiguration adviceConfiguration) {
		IEditHelperAdvice editHelperAdvice = ClassLoaderHelper.newInstance(adviceConfiguration.getEditHelperAdviceClassName(), IEditHelperAdvice.class, EcoreUtil.getURI(adviceConfiguration));
		return editHelperAdvice;
	}

}
