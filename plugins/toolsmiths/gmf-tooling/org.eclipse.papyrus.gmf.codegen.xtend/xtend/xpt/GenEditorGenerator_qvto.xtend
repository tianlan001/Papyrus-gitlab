/*******************************************************************************
 * Copyright (c) 2007, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Artem Tikhomirov (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt

import com.google.inject.Singleton
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator

@Singleton class GenEditorGenerator_qvto {

	/**
	 * FIXME remove java-only GenEditorGenerator#hasAudits or declare it in metamodel
	 */
	def boolean hasAudits(GenEditorGenerator editorGen) {
		return editorGen.audits !== null && !editorGen.audits.rules.empty
	}

	/**
	 * Returns java version number.
	 *
	 * @returns 4 for Java1.4, 5 for JDK 5.0, 6 for JDK6.0 and 7 for JDK7.0, etc.
	 */
	def int jdkComplianceLevel(GenEditorGenerator xptSelf) {
		var GenJDKLevel l = if (xptSelf === null || xptSelf.jdkComplianceLevel === null)
				GenJDKLevel::JDK110_LITERAL
			else
				xptSelf.jdkComplianceLevel;
		switch (l) {
			case GenJDKLevel::JDK14_LITERAL: 4
			default: Double.valueOf(l.literal).intValue
		}
	}

	def int jdkComplianceLevel(GenCommonBase xptSelf) {
		return xptSelf.diagram.editorGen.jdkComplianceLevel()
	}

}
