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
 *****************************************************************************/
package org.eclipse.papyrus.gmf.common.codegen;


/**
 * @author artem
 */
public interface ImportAssistant {
	void emitPackageStatement(StringBuffer stringBuffer);
	void markImportLocation(StringBuffer stringBuffer);
	void emitSortedImports();
	String getImportedName(String qualifiedName);
	void addImport(String qualifiedName);
	void registerInnerClass(String innerClassName);

	/**
	 * Import assistant knows name of the unit which gonna contain template outcome  
	 */
	String getCompilationUnitName();
}
