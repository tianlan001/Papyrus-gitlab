/*****************************************************************************
 * Copyright (c) 2015, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Michael Golubev (Montages) - initial API and implementation
 * Anatoliy Tischenko - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up override + type<template?>
 *****************************************************************************/
package xpt

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase

@Singleton class CodeStyle {
	@Inject extension GenEditorGenerator_qvto
	/**
	 * FIXME: [MG] inline this, we now safely assume everywhere that it is > 6
	 */
	def overrideC(Object xptSelf) '''
		@Override
	'''

	def overrideI(Object xptSelf)'''
		@Override
	'''

	/**
	 * The diamond operator, allows the right hand side of the assignment to be defined as a true generic instance with the same type parameters as the left side.
	 */
	def diamondOp(GenCommonBase xptSelf, String... tokens) {
		if (xptSelf.jdkComplianceLevel() < 7) {
			return '''«tokens.join(", ")»'''
		}
	}
}