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
 *	Svyatoslav Kovalsky (Montages) - initial API and implementation
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt

import org.eclipse.emf.ecore.ETypedElement

/**
 * XXX: [MG] not needed anymore, all ocl-related problems are gone with xtend, merge it with GenModelUtils_qvto
 */
@com.google.inject.Singleton class OclMigrationProblems_qvto {
	def boolean isUnbounded(ETypedElement typedElement) {
		return typedElement.upperBound == ETypedElement::UNBOUNDED_MULTIPLICITY
	}

	def isSingleValued(ETypedElement typedElement) {
		return typedElement.upperBound == 1 || typedElement.upperBound == ETypedElement::UNSPECIFIED_MULTIPLICITY
	}

}
