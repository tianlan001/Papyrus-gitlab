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
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to gmfgraph and ModelViewMap
 *****************************************************************************/
package xpt.diagram.editparts;

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType

import xpt.Common_qvto

@Singleton class Utils_qvto {
	@Inject extension Common_qvto

	def boolean isStoringChildPositions(GenNode node) {
		node.getLayoutType() == ViewmapLayoutType::XY_LAYOUT
	}

	def Iterable<GenExternalNodeLabel> getExternalLabels(GenNode node) {
		node.labels.filter(typeof(GenExternalNodeLabel))
	}

	def Iterable<GenChildSideAffixedNode> getSideAffixedChildren(GenNode node) {
		return node.childNodes.filter(typeof(GenChildSideAffixedNode));
	}

	private def Iterable<? extends GenLabel> getInnerLabels(GenNode node) {
		return node.labels.filter[i|!oclIsKindOf(i, typeof(GenExternalNodeLabel))];
	}

	def Iterable<? extends GenLabel> getInnerFixedLabels(GenNode node) {
		val innerLabels = getInnerLabels(node);
		return innerLabels.filter[e|e.viewmap.oclIsKindOf(typeof(ParentAssignedViewmap))];
	}

	def Iterable<GenCompartment> getPinnedCompartments(GenNode node) {
		return node.compartments.filter[e|e.viewmap.oclIsKindOf(typeof(ParentAssignedViewmap))]
	}

	def boolean hasFixedChildren(GenNode node) {
		return //
		getInnerFixedLabels(node).size > 0 || // 
		getPinnedCompartments(node).size > 0
		;
	}

	def boolean listCompartmentHasChildren(GenCompartment compartment) {
		return compartment.listLayout && compartment.childNodes.size > 0
	}

	def boolean hasChildrenInListCompartments(GenNode node) {
		return node.compartments.exists[e|listCompartmentHasChildren(e)]
	}

	def boolean hasBorderItems(GenNode node) {
		return getSideAffixedChildren(node).size > 0 || getExternalLabels(node).size > 0
	}

	def boolean needsGraphicalNodeEditPolicy(GenNode node) {
		return node.modelFacet !== null && node.reorientedIncomingLinks.size > 0
	}

	def boolean shouldGenerateDiagramViewmap(GenDiagram genDiagram) {
		val typesWithoutViewmaps = newHashSet(ViewmapLayoutType::UNKNOWN_LITERAL, ViewmapLayoutType::XY_LAYOUT_LITERAL);
		return !typesWithoutViewmaps.contains(genDiagram.viewmap.layoutType)
	}

	def Iterable<GenLink> getAssistantOutgoingLinks(GenNode node) {
		return node.genOutgoingLinks.filter [ link |
			link.targets.exists(t|t.oclIsKindOf(typeof(GenNode)))
		]
	}

	def Iterable<GenLink> getAssistantIncomingLinks(GenNode node) {
		return node.genIncomingLinks.filter [ link |
			link.sources.exists(s|s.oclIsKindOf(typeof(GenNode)))
		]
	}

	def Iterable<GenNode> selectGenNodes(Iterable<GenLinkEnd> ends) {
		return ends.filter(typeof(GenNode))
	}

	def boolean haveOneOfChildNodesIncomimgLinks(GenCompartment it) {
		return it.childNodes.exists[n| n.assistantIncomingLinks.notEmpty];
	}

	def List<GenLink> collectIncomingLinks(GenCompartment it) {
		var Set<GenLink> incomingLinks = new HashSet<GenLink>();
		for (childNode : it.childNodes) {
			if (childNode.assistantIncomingLinks.notEmpty) {
				incomingLinks.addAll(childNode.assistantIncomingLinks);
			}
		}
		return incomingLinks.sortBy(l|l.visualID);
	}
}
