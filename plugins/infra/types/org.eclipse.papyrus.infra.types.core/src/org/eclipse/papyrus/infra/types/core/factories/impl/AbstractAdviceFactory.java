/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 459174
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingInheritance;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredEditHelperAdviceDescriptor;
import org.eclipse.papyrus.infra.types.core.factories.IEditHelperAdviceFactory;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredEditHelperAdviceDescriptor;

public abstract class AbstractAdviceFactory<T extends AdviceConfiguration> implements IEditHelperAdviceFactory<T> {

	@Override
	public IConfiguredEditHelperAdviceDescriptor<T> createEditHelperAdviceDescriptor(T adviceConfiguration) {
		return new ConfiguredEditHelperAdviceDescriptor<T>(getId(adviceConfiguration), getTypeId(adviceConfiguration), getMatcher(adviceConfiguration), getContainerDescriptor(adviceConfiguration), getEditHelperAdvice(adviceConfiguration),
				getInheritance(adviceConfiguration));
	}

	protected abstract String getId(T adviceConfiguration);

	protected abstract String getTypeId(T adviceConfiguration);

	protected abstract IElementMatcher getMatcher(T adviceConfiguration);

	protected abstract IContainerDescriptor getContainerDescriptor(T adviceConfiguration);

	protected abstract IEditHelperAdvice getEditHelperAdvice(T adviceConfiguration);

	protected abstract AdviceBindingInheritance getInheritance(T adviceConfiguration);
}
