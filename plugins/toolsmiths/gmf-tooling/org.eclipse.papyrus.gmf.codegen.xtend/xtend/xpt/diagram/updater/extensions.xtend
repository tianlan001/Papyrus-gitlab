/*****************************************************************************
 * Copyright (c) 2007, 2020, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Gabriel Pascual (ALL4TEC) -  Bug 372322 : [Diagram - Refresh] The refresh action is not correctly binded to F5
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : PapyrusGmfExtension epackage merge into gmfgen
 *****************************************************************************/
package xpt.diagram.updater

import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater

@Singleton class extensions {

	def extensions(GenDiagramUpdater it) {
		// Override Refresh contribution
	}

}
