/******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package metamodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage

@com.google.inject.Singleton class Facility_qvto {
	def getNameToken(GenPackage gp) {
		return gp.prefix
	}

	def fieldName(GenPackage gp) {
		return 'instance' + getNameToken(gp)
	}

}