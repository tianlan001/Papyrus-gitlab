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
package org.eclipse.papyrus.infra.types.rulebased.core;

import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.SpecializationTypeFactory;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;

public class RuleElementTypeFactory extends SpecializationTypeFactory {

	@Override
	protected IElementMatcher createElementMatcher(SpecializationTypeConfiguration configuration) {
		if (configuration instanceof RuleBasedTypeConfiguration) {
			return new DefaultRuleElementMatcher((RuleBasedTypeConfiguration) configuration);
		}
		return null;
	}

}
