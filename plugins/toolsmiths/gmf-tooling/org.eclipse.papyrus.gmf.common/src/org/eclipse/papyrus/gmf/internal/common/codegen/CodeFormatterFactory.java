/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Montages AG - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;

public interface CodeFormatterFactory {

	public final CodeFormatterFactory DEFAULT = new NullFormatterFactory();

	public CodeFormatter createCodeFormatter();

	static class NullFormatterFactory implements CodeFormatterFactory {

		public CodeFormatter createCodeFormatter() {
			return ToolFactory.createCodeFormatter(null);
		}
	}

}
