/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
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
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorChildReference
import xpt.Common_qvto

@com.google.inject.Singleton class Utils_qvto {
	@Inject extension Common_qvto;

	def int getMaxVisualID(GenNavigator navigator) {
		getNavigatorNodes(navigator).sortBy[n|n.visualID].lastOrNull.visualID
	}

	def Iterable<GenCommonBase> getNavigatorContainerNodes(GenNavigator navigator) {
		return getNavigatorNodes(navigator).filter[n|getChildReferencesFrom(navigator, n).notEmpty]
	}

	def Iterable<GenCommonBase> getNavigatorContainedNodes(GenNavigator navigator) {
		return getNavigatorNodes(navigator).filter[n|getChildReferencesTo(navigator, n).notEmpty]
	}

	def Iterable<GenCommonBase> getNavigatorNodes(GenNavigator navigator) {
		var result = <GenCommonBase>newLinkedList()
		result.addAll(navigator.editorGen.diagram.getAllContainers())
		result.addAll(navigator.editorGen.diagram.links)
		return result.sortBy[n|n.visualID]
	}

	def Set<String> getGroupNames(Iterable<GenNavigatorChildReference> refs) {
		return refs.filter[ref|ref.isInsideGroup()].map[ref|ref.groupName].toSet()
	}

	def GenNavigatorChildReference getNavigatorReference(String groupName,
		Iterable<GenNavigatorChildReference> childReferences) {
		return childReferences.findFirst[r|r.groupName == groupName]
	}

	def Iterable<GenNavigatorChildReference> getChildReferencesFrom(GenNavigator navigator, GenCommonBase parent) {
		return if (parent === null) //
			navigator.childReferences.filter[r|r.parent === null] //
		else
			navigator.childReferences.filter[r|r.parent !== null && parent.visualID == r.parent.visualID]
	}

	def Iterable<GenNavigatorChildReference> getChildReferencesTo(GenNavigator navigator, GenCommonBase child) {
		return navigator.childReferences.filter[r|child.visualID == r.child.visualID]
	}

	def GenNavigatorChildReference getDiagramTopReference(GenNavigator navigator) {
		return navigator.childReferences.filter[cr|null === cr.parent].filter[cr|cr.child == navigator.editorGen.diagram].
			head;
	}

	def boolean isStringFeature(GenFeature feature) {
		return feature.ecoreFeature.EType.name == 'EString'
	}

}
