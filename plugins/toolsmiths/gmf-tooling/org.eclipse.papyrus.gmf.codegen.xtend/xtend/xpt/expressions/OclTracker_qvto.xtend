/*******************************************************************************
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
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.expressions

/**
 * XXX: [MG] move it somewhere? or rename to ImpactAnalyzerSomething (?)
 */ 
@com.google.inject.Singleton class OclTracker_qvto {
	def boolean isForcedImpactAnalyzerKind(String oclExpression) {
		return null !== oclExpression && oclExpression.startsWith('--IA');
	}

}
