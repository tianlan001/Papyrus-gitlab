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
 */
package org.eclipse.papyrus.emf.facet.query.java.core.internal;

import org.eclipse.papyrus.emf.facet.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueListFactory;

/**
 * Implementation of {@link IParameterValueListFactory}
 */
// deprecated APIs that are still maintained
@SuppressWarnings("deprecation")
public class ParameterValueListFactoryImpl implements
		IParameterValueListFactory {

	public IParameterValueList createParameterValueList(
			final ParameterValue... values) {
		return new ParameterValueList(values);
	}

}
