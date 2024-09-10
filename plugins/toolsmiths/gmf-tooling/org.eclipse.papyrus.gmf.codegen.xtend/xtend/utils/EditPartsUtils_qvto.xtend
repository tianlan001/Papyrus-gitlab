/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Yann Tanguy (CEA LIST) - initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package utils

import com.google.inject.Singleton
import java.util.List
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode

@Singleton class EditPartsUtils_qvto {

	def boolean hasSpecificLocator(GenExternalNodeLabel it) {
		return locatorClassName !== null
	}

	def String getSpecificLocator(GenExternalNodeLabel it) {
		return locatorClassName;
	}

	def List<GenExternalNodeLabel> getExternalLabelsWithoutSpecificLocator(GenNode it) {
		return it.labels.filter(typeof(GenExternalNodeLabel)).filter[l|!hasSpecificLocator(l)].toList;
	}

	def List<GenExternalNodeLabel> getExternalLabelsWithSpecificLocator(GenNode it) {
		return it.labels.filter(typeof(GenExternalNodeLabel)).filter[l|hasSpecificLocator(l)].toList;
	}

}
