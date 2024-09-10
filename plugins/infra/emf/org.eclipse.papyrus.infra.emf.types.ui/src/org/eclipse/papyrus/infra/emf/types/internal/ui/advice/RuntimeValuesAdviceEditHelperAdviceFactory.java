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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Add createAdviceBindingConfiguration method
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.internal.ui.advice;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceFactory;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;

public class RuntimeValuesAdviceEditHelperAdviceFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {

	@Override
	protected IEditHelperAdvice getEditHelperAdvice(AbstractAdviceBindingConfiguration adviceConfiguration) {
		if (adviceConfiguration instanceof RuntimeValuesAdviceConfiguration) {
			IEditHelperAdvice editHelperAdvice = new RuntimeValuesAdviceEditHelperAdvice((RuntimeValuesAdviceConfiguration) adviceConfiguration);
			return editHelperAdvice;
		}
		return NullEditHelperAdvice.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return RuntimeValuesAdviceFactory.eINSTANCE.createRuntimeValuesAdviceConfiguration();
	}
}
