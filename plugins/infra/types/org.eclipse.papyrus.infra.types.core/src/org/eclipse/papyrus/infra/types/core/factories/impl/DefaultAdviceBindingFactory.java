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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add createAdviceBindingConfiguration method
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.AdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;

public class DefaultAdviceBindingFactory extends AbstractAdviceBindingFactory<AdviceBindingConfiguration> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IEditHelperAdvice getEditHelperAdvice(final AdviceBindingConfiguration adviceConfiguration) {
		String className = adviceConfiguration.getEditHelperAdviceClassName();
		URI uri = EcoreUtil.getURI(adviceConfiguration);
		IEditHelperAdvice editHelperAdvice = ClassLoaderHelper.newInstance(className, IEditHelperAdvice.class, uri);
		return editHelperAdvice;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
	}

}
