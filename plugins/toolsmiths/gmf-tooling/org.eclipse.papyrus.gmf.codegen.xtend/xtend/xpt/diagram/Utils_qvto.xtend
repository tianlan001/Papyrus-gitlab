/*******************************************************************************
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
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram

import java.util.List
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet

@com.google.inject.Singleton class Utils_qvto {

	/**
	 * @return all elements that may have element type: diagram, nodes and links
	 */
	def List<GenCommonBase> getAllTypedElements(GenDiagram it) {
		var List<GenCommonBase> result = newLinkedList();
		result.add(it);
		result.addAll(it.allNodes);
		result.addAll(it.links);
		return result;
	}

	def boolean hasExplicitChildFeature(TypeModelFacet facet) {
		return !facet.childMetaFeature.derived && facet.childMetaFeature != facet.containmentMetaFeature
	}

	/**
	* -- true when link is contained in an object other than link's source,
	* -- (1) sourceMetaFeature is specified
	* -- (2) childMetaFeature is not the same as containment and belongs to a different class than container
	* -- for (2), consider scenario from bug #226882, container "A", that holds link "L" and link's source and target, 
	* -- "N1" and "N2". "N1" has a reference to "L" which is being used as childMetaFeature. Hence, link's container, "C"
	* -- is different from link's source, "N1". As nothing can prevent "C" from subclassing "N1", there's no sure way to
	* -- tell if container is really different from the source or not, and we'd better assume they are different.
	* 
	*/
	def boolean hasContainerOtherThanSource(TypeLinkModelFacet facet) {
		return facet.sourceMetaFeature !== null || facet.childMetaFeature != facet.containmentMetaFeature
	}

	def List<GenPreferencePage> allPreferencePages(GenDiagram genDiagram) {
		var List<GenPreferencePage> result = newLinkedList();
		result.addAll(genDiagram.preferencePages);
		return recursePages(result)
	}

	private def List<GenPreferencePage> recursePages(List<GenPreferencePage> pages) {
		if (pages.empty) {
			return pages;
		}
		var children = pages.fold(<GenPreferencePage>newLinkedList(), [list, page|list.addAll(page.children); list])
		pages.addAll(recursePages(children))
		return pages
	}

	/**
	 * if diagram is in pixel map mode, no reason to use getMapMode()
	 * XXX [MG] we don't use map mode anymore, do we?
	 **/
	def boolean isPixelMapMode(GenDiagram it) {
		it.units == 'Pixel';
	}

}
