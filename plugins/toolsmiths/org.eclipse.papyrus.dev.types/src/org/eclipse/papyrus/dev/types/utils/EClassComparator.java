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
package org.eclipse.papyrus.dev.types.utils;

import java.util.Comparator;

import org.eclipse.emf.ecore.EClass;

public class EClassComparator implements Comparator<EClass> {

	@Override
	public int compare(EClass o1, EClass o2) {
		return o1.getName().compareTo(o2.getName());
	}
}