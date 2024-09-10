/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;

/**
 * Advice factory for advice described by the combination stereotype matcher and advice configuration.
 */
public class StereotypeMatcherAdviceAdviceFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {

	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return StereotypeApplicationMatcherFactory.eINSTANCE.createStereotypeMatcherAdviceConfiguration();
	}

	@Override
	protected IEditHelperAdvice getEditHelperAdvice(AbstractAdviceBindingConfiguration adviceConfiguration) {
		return (adviceConfiguration instanceof StereotypeMatcherAdviceConfiguration)
				? new StereotypeMatcherEditHelperAdvice((StereotypeMatcherAdviceConfiguration) adviceConfiguration)
				: NullEditHelperAdvice.getInstance();
	}

}
