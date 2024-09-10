/******************************************************************************
 * Copyright (c) 2006, 2020, 2021 Borland Software Corporation, CEA LIST, Artal
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
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;

/**
 * @author artem
 */
public interface TextEmitter {
	String generate(IProgressMonitor monitor, Object[] arguments, String lineSeparator) throws InterruptedException, InvocationTargetException, UnexpectedBehaviourException;
}
