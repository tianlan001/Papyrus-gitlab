/******************************************************************************
 * Copyright (c) 2015, 2020, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - Use project or worksapce preference as new line characters
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;
import org.eclipse.papyrus.gmf.internal.common.codegen.JavaClassEmitter;

import com.google.inject.Injector;


public class Xtend2ClassEmitter extends Xtend2Emitter implements JavaClassEmitter {

	public Xtend2ClassEmitter(Injector injector, Class<?> clazz, String methodName) {
		super(injector, clazz, methodName);
	}

	@Override
	public String getQualifiedClassName(String lineSeparator, Object... input) throws UnexpectedBehaviourException {
		return getQualifiedClassName("qualifiedClassName", lineSeparator, input);
	}

	@Override
	public String getQualifiedClassName(String FQNMethodName, String lineSeparator, Object... input) throws UnexpectedBehaviourException {
		return getText(FQNMethodName, lineSeparator, input);
	}

	private String getText(String method, String lineSeparator, Object... input) throws UnexpectedBehaviourException {
		try {
			// Use project or worksapce preference as new line characters
			return this.generate(new NullProgressMonitor(), method, input, lineSeparator);
		} catch (InvocationTargetException e) {
			throw new UnexpectedBehaviourException("Invocation method exception "+ method + " on class " + getTemplateClass().getName(), e);
		} catch (InterruptedException e) {
			throw new UnexpectedBehaviourException("Execute method exception "+ method + " on class " + getTemplateClass().getName(), e);
		}
	}
}
