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

import java.lang.reflect.Field;

import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.papyrus.dev.types.Activator;


public class ElementTypeRegistryUtils {


	static public synchronized Field getNullElementTypeEditHelper() {

		Field declaredField = null;
		try {
			declaredField = MetamodelType.class.getDeclaredField("editHelper");
		} catch (SecurityException e1) {
			Activator.log.error(e1);
			return null;
		} catch (NoSuchFieldException e1) {
			Activator.log.error(e1);
			return null;
		}
		if (declaredField == null) {
			Activator.log.error("impossible to find editHelper", null);
			return null;
		}
		declaredField.setAccessible(true);

		return declaredField;
	}

}
