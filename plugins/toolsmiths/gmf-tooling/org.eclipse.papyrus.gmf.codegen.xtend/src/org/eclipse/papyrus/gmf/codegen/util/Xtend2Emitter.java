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
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;
import org.eclipse.papyrus.gmf.internal.common.codegen.TextEmitter;
import org.eclipse.xtend2.lib.StringConcatenation;

import com.google.inject.Injector;

public class Xtend2Emitter implements TextEmitter {

	private final Class<?> myXtendGenerator;

	private final String myMethodName;

	private final Injector myInjector;

	public Xtend2Emitter(Injector injector, Class<?> clazz, String methodName) {
		myInjector = injector;
		myXtendGenerator = clazz;
		myMethodName = methodName;
	}

	@Override
	public String generate(IProgressMonitor monitor, Object[] arguments, String lineSeparator) throws InterruptedException, InvocationTargetException, UnexpectedBehaviourException {
		return generate(monitor, myMethodName, arguments, lineSeparator);
	}
	
	protected String generate(IProgressMonitor monitor, String methodName, Object[] arguments, String lineSeparator) throws InterruptedException, InvocationTargetException, UnexpectedBehaviourException {
		if (monitor != null && monitor.isCanceled()) {
			throw new InterruptedException();
		}
		if (arguments.length > 1) {
			List<Object> fixedArgs = new LinkedList<Object>();
			fixedArgs.add(arguments[0]);
			for (int i = 1; i < arguments.length; i++) {
				fixedArgs.add(arguments[i]);
			}
			arguments = fixedArgs.toArray();
		}
		Object generator = instantiateGenerator();
		Method method = getGeneratorMethod(arguments.length, methodName);
		Object result;
		try {
			result = method.invoke(generator, arguments);
		} catch (IllegalArgumentException e) {
			throw new UnexpectedBehaviourException("Invocation failed for: " + this, e);
		} catch (IllegalAccessException e) {
			throw new UnexpectedBehaviourException("Invocation failed for: " + this, e);
		}

		if (result == null) {
			throw new UnexpectedBehaviourException("Xtend generator returned null for " + this);
		}
		
		//  Bug 569174 - Use project or worksapce preference as new line characters
		// - here it is at creation time (!= merge if already exist)
		// -- --  use post processing instead of intrusive changes in APIs
		StringConcatenation _builder = new StringConcatenation(lineSeparator);
		_builder.append(result);
		return _builder.toString();
	}

	private Object instantiateGenerator() throws UnexpectedBehaviourException {
		Object instance = myInjector.getInstance(myXtendGenerator);
		if (instance == null) {
			throw new UnexpectedBehaviourException("Can't instantiate Xtend generator object " + this);
		}
		return instance;
	}

	private Method getGeneratorMethod(int paramsCount, String methodName) throws UnexpectedBehaviourException {
		Method[] allMethods;
		try {
			allMethods = myXtendGenerator.getDeclaredMethods();
		} catch (SecurityException e) {
			throw new UnexpectedBehaviourException("For : " + this, e);
		}
		Method candidate = null;
		for (Method next : allMethods) {
			if (methodName.equals(next.getName()) && next.getParameterTypes().length == paramsCount) {
				if (candidate != null) {
					throw new UnexpectedBehaviourException("More than 1 method found for " + this + ", " + candidate + " vs " + next);
				}
				candidate = next;
			}
		}
		if (candidate == null) {
			throw new UnexpectedBehaviourException("No such method (with params count = " + paramsCount + ") for : " + this);
		}
		return candidate;
	}

	@Override
	public String toString() {
		return "xtend2:[" + myXtendGenerator.getSimpleName() + "#" + myMethodName + "]";
	}

	protected Object extractTarget(Object[] arguments) {
		assert arguments != null && arguments.length > 0;
		return arguments[0];
	}

	protected Injector getInjector() {
		return myInjector;
	}

	protected Class<?> getTemplateClass() {
		return myXtendGenerator;
	}

}