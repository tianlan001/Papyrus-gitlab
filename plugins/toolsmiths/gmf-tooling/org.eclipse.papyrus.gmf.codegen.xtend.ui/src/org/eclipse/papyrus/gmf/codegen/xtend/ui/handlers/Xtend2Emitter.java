/*******************************************************************************
* Copyright (c) 2013, 2020 Montages A.G., CEA LIST, Artal
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*  	Guillaume Hillairet (Montages A.G.) : initial implementation
*    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
*****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.xtend.ui.handlers;

import com.google.inject.Injector;

public class Xtend2Emitter extends org.eclipse.papyrus.gmf.codegen.util.Xtend2Emitter {

	public Xtend2Emitter(Injector injector, Class<?> clazz, String methodName) {
		super(injector, clazz, methodName);
	}
}