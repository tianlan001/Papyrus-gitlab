/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - newline characters preference api consistency
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;

/**
 * @author artem
 */
public class JETEmitterAdapter implements TextEmitter {
	private final JETEmitter myEmitter;

	public JETEmitterAdapter(JETEmitter emitter){
		myEmitter = emitter;
	}
	
	public String generate(IProgressMonitor monitor, Object[] params, String lineSeparator) throws InterruptedException, InvocationTargetException {
		try {
			if (monitor != null && monitor.isCanceled()) {
				throw new InterruptedException();
			}
			return myEmitter.generate(monitor, adaptArgumentsForSkeleton(params));
		} catch (JETException ex) {
			throw new InvocationTargetException(ex);
		}
	}

	/**
	 * JET's generate() method usually takes single argument as input, unless overriden in skeleton 
	 */
	protected Object[] adaptArgumentsForSkeleton(Object[] params) {
		if (params == null || params.length <= 1) {
			return params;
		}
		// more than one argument, hence need to wrap into single object
		return new Object[] { params };
	}
}
