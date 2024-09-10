/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.emf.core.internal.serialization.serializers;

import org.eclipse.papyrus.emf.facet.util.emf.core.serialization.ISerializer;

public class IntegerSerializer implements ISerializer<Integer> {

	public IntegerSerializer() {
		//
	}

	public Class<Integer> getType() {
		return Integer.class;
	}

	public String serialize(final Integer value) {
		return value.toString();
	}

	public Integer deserialize(final String serializedValue) {
		return Integer.valueOf(serializedValue);
	}
}
