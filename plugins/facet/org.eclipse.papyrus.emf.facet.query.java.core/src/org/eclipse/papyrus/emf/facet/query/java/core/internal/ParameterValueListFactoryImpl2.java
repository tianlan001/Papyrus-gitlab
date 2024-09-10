/**
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Guyomar (Mia-Software) - Bug 334615 - Java Query for EMF Facet
 *     Emmanuelle Rouillé (Mia-Software) - Bug 352618 - To be able to use non derived facet structural features and save them values.
 */
package org.eclipse.papyrus.emf.facet.query.java.core.internal;

import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueListFactory2;

public class ParameterValueListFactoryImpl2 implements
		IParameterValueListFactory2 {

	public IParameterValueList2 createParameterValueList(
			final ParameterValue... values) {
		return new ParameterValueList2(values);
	}
}
