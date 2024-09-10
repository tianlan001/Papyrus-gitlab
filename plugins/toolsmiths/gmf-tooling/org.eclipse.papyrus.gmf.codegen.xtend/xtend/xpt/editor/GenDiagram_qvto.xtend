/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others
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
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.ETypedElement
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram

@com.google.inject.Singleton class GenDiagram_qvto {
	def boolean standaloneDomainModel(GenDiagram genDiagram) {
		return !genDiagram.editorGen.sameFileForDiagramAndModel && genDiagram.domainDiagramElement !== null
	}

	def boolean hasDocumentRoot(GenDiagram genDiagram) {
		return getDocumentRoot(genDiagram) !== null
	}

	def GenClass getDocumentRoot(GenDiagram genDiagram) {
		if (genDiagram.domainDiagramElement === null) {
			return null;
		}
		return genDiagram.domainDiagramElement.genPackage.documentRoot;
	}

	def GenFeature getDocumentRootSetFeature(GenDiagram genDiagram) {
		var root = getDocumentRoot(genDiagram);
		if (root === null) {
			return null;
		}
		return root.genFeatures.findFirst[f|isDocRootSetFeature(f, genDiagram.domainDiagramElement.ecoreClass)]
	}

	def boolean isDocRootSetFeature(GenFeature gf, EClass eType) {
		return !gf.listType && //
		//
		gf.ecoreFeature !== null && // 
			gf.ecoreFeature.changeable && //
			gf.ecoreFeature.upperBound == ETypedElement::UNSPECIFIED_MULTIPLICITY && //
			gf.ecoreFeature.eClass.name == 'EReference' && //
			(gf.ecoreFeature.EType as EClass).isSuperTypeOf(eType);
	}

}
