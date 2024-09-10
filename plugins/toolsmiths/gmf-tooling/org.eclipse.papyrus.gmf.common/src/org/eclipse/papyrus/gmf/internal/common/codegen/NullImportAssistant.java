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
package org.eclipse.papyrus.gmf.internal.common.codegen;

import org.eclipse.papyrus.gmf.common.codegen.ImportAssistant;

/**
 * NO-OP implementation of import assistant. Just keeps all qualified names fully-qualified.
 * @author artem
 */
public class NullImportAssistant implements ImportAssistant {
	private final String myPackageName;
	private final String myUnitName;

	
	public NullImportAssistant(String packageName, String compilationUnitName) {
		assert compilationUnitName != null && compilationUnitName.trim().length() > 0;
		myPackageName = packageName;
		myUnitName = compilationUnitName;
	}

	public String getCompilationUnitName() {
		return myUnitName;
	}

	public void emitPackageStatement(StringBuffer stringBuffer) {
		if (myPackageName == null || myPackageName.trim().length() == 0) {
			return;
		}
		stringBuffer.append("\npackage ");
		stringBuffer.append(myPackageName);
		stringBuffer.append(';');
	}

	/*
	 * NO-OP
	 */
	public void markImportLocation(StringBuffer stringBuffer) {
	}

	/*
	 * NO-OP
	 */
	public void emitSortedImports() {
	}

	/**
	 * Simply returns argument
	 * @return unmodified argument
	 */
	public String getImportedName(String qualifiedName) {
		return qualifiedName;
	}

	/*
	 * NO-OP
	 */
	public void addImport(String qualifiedName) {
	}

	/*
	 * NO-OP
	 */
	public void registerInnerClass(String innerClassName) {
	}
}
