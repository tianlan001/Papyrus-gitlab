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
package org.eclipse.papyrus.infra.emf.types.converter;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.ValueSpecification;


/**
 * Converter for enumeration values
 */
public class ValueSpecificationToEnumerationConverter extends Converter implements IConverter {

	/**
	 * @param fromType
	 * @param toType
	 */
	public ValueSpecificationToEnumerationConverter() {
		super(ValueSpecification.class, Enumeration.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convert(Object fromObject) {
		if(fromObject instanceof InstanceValue) {
			InstanceSpecification instanceSpecification = ((InstanceValue)fromObject).getInstance();
			if(instanceSpecification instanceof EnumerationLiteral) {
				return (instanceSpecification);
			}
		}

		return fromObject;
	}
}
