/******************************************************************************
 * Copyright (c) 2014, 2020 Borland Software Corporation, CEA LIST, Artal and others
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
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - newline characters preference api consistency
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;



public interface JavaClassEmitter extends TextEmitter {
	
	public String getQualifiedClassName(String lineSeparator, Object... input)  throws UnexpectedBehaviourException;
	
	public String getQualifiedClassName(String fqnMethodName, String lineSeparator, Object... input)  throws UnexpectedBehaviourException;
}
