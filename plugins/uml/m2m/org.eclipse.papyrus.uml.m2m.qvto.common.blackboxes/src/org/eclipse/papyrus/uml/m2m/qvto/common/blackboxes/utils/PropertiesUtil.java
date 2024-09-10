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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.blackboxes.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.m2m.qvt.oml.util.IContext;


public class PropertiesUtil {

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public String getStringProperty(IContext context, String property) {
		return (String)context.getConfigProperty(property);
	}

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public boolean getBooleanProperty(IContext context, String property) {
		return Boolean.parseBoolean(context.getConfigProperty(property).toString());
	}

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public int getIntegerProperty(IContext context, String property) {
		return Integer.parseInt(context.getConfigProperty(property).toString());
	}

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public EObject getEObjectProperty(IContext context, String property) {
		Object value = context.getConfigProperties().get(property);
		if(value instanceof EObject) {
			return (EObject)value;
		}
		return null;
	}

}
