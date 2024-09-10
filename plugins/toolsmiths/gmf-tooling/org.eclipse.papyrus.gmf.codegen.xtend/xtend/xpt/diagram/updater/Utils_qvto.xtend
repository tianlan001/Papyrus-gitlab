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
 *    Alexander Shatalin (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838, Migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram.updater

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import xpt.Common_qvto
import xpt.diagram.editpolicies.LinkUtils_qvto

enum UpdaterLinkType {
	CONTAINED,
	INCOMING,
	OUTGOING
}

@com.google.inject.Singleton class Utils_qvto {
	@Inject extension Common_qvto;
	@Inject extension LinkUtils_qvto;

	def getLinkMethodSuffix(UpdaterLinkType type) {
		switch (type) {
			case UpdaterLinkType::CONTAINED: return 'Contained'
			case UpdaterLinkType::INCOMING: return 'Incoming'
			case UpdaterLinkType::OUTGOING: return 'Outgoing'
			default: throw new IllegalArgumentException("Unknown updaterLinkType: " + type)
		}
	}

	def Iterable<GenLink> computeContainedLinks(GenClass metaClass, GenDiagram diagram) {
		return diagram.links.filter[link|canBeContainer(link, metaClass)]
	}

	def Iterable<GenLink> computeOutgoingLinks(GenLinkEnd linkEnd) {
		return linkEnd.diagram.links.filter[link|linkEnd.genOutgoingLinks.contains(link)]
	}

	def Iterable<GenLink> computeIncomingLinks(GenLinkEnd linkEnd) {
		return linkEnd.diagram.links.filter[link|linkEnd.genIncomingLinks.contains(link)]
	}

	def Iterable<GenLink> getAllContainedLinks(GenDiagram diagram) {
		return diagram.links.filter[link|
			getAllSemanticElements(diagram).filter(commonBase|canBeContainer(link, commonBase.metaClass)).notEmpty]
	}

	def Iterable<GenLink> getAllIncomingLinks(GenDiagram diagram) {
		return diagram.links.filter[link|link.targets.notEmpty]
	}

	def Iterable<GenLink> getAllOutgoingLinks(GenDiagram diagram) {
		return diagram.links.filter[link|link.isOutgoingLink()].filter[link|link.sources.notEmpty]
	}

	def boolean isOutgoingLink(GenLink link) {
		return null !== link.modelFacet && link.modelFacet.isOutgoingLinkModelFacet()
	}

	def dispatch boolean isOutgoingLinkModelFacet(LinkModelFacet facet) {
		return true
	}

	def dispatch boolean isOutgoingLinkModelFacet(TypeLinkModelFacet facet) {
		return null !== facet.sourceMetaFeature
	}

	def Iterable<GenLinkEnd> getAllSemanticDiagramElements(GenDiagram diagram) {
		var result = <GenLinkEnd>newLinkedList();
		result.addAll(diagram.allNodes)
		result.addAll(diagram.links)
		return result.filter[linkEnd|linkEnd.metaClass !== null]
	}

	def Iterable<GenCommonBase> getAllSemanticElements(GenDiagram diagram) {
		if (diagram.domainDiagramElement !== null) {
			var result = <GenCommonBase>newLinkedList(diagram)
			result.addAll(getAllSemanticDiagramElements(diagram))
			return result;
		} else {
			return getAllSemanticDiagramElements(diagram).filter(typeof(GenCommonBase));
		}
	}

	def dispatch GenClass getMetaClass(GenCommonBase some) {
		return null
	}

	def dispatch GenClass getMetaClass(GenDiagram some) {
		return some.domainDiagramElement
	}

	def dispatch GenClass getMetaClass(GenNode some) {
		return if (some.modelFacet === null) null else some.modelFacet.metaClass
	}

	def dispatch GenClass getMetaClass(GenLink some) {
		return if(some.modelFacet === null) null else getMetaClass(some.modelFacet)
	}

	def dispatch GenClass getMetaClass(LinkModelFacet facet) {
		return null
	}

	def dispatch GenClass getMetaClass(TypeLinkModelFacet facet) {
		return facet.metaClass
	}

	def boolean hasSemanticChildren(GenContainerBase container) {
		return !container.sansDomain && container.containedNodes.notEmpty
	}

	def dispatch GenClass getModelElementType(GenContainerBase base) {
		return null
	}

	def dispatch GenClass getModelElementType(GenDiagram diagram) {
		return diagram.domainDiagramElement
	}

	def dispatch GenClass getModelElementType(GenCompartment compartment) {
		return compartment.node.getModelElementType()
	}

	def dispatch GenClass getModelElementType(GenNode node) {
		return node.modelFacet.metaClass
	}

	def Set<GenFeature> getSemanticChildrenChildFeatures(GenContainerBase containerBase) {
		var result = <GenFeature>newLinkedHashSet()
		result.addAll(getNonPhantomSemanticChildren(containerBase).map[node|node.modelFacet.childMetaFeature])
		return result
	}

	def Set<GenFeature> getSemanticChildrenContainmentFeatures(GenContainerBase containerBase) {
		var result = <GenFeature>newLinkedHashSet()
		result.addAll(getNonPhantomSemanticChildren(containerBase).map[node|node.modelFacet.containmentMetaFeature])
		return result
	}

	def Iterable<GenNode> getSemanticChildren(GenContainerBase containerBase, GenFeature childMetaFeature) {
		return getNonPhantomSemanticChildren(containerBase).filter[node|
			node.modelFacet.childMetaFeature == childMetaFeature]
	}

	def Iterable<GenNode> getNonPhantomSemanticChildren(GenContainerBase containerBase) {
		return getSemanticChildren(containerBase).filter[node|!node.modelFacet.isPhantomElement()]
	}

	def boolean hasPhantomNodes(GenDiagram it) {
		if (it.phantomNodes.notEmpty) return true; 
		for (GenNode node: it.allNodes) {
			if (node.phantomNodes.notEmpty) {
				return true;
			}
		}
		return false;
	}

	def dispatch Iterable<GenNode> getPhantomNodes(GenContainerBase it) {
		return <GenNode>newLinkedList()
	}

	def dispatch Iterable<GenNode> getPhantomNodes(GenNode it) {
		return getPhantomNodesForContainers(it)
	}

	def dispatch Iterable<GenNode> getPhantomNodes(GenDiagram it) {
		return getPhantomNodesForContainers(it)
	}

	def Iterable<GenNode> getPhantomNodesForContainers(GenContainerBase it) {
		getSemanticChildren(it).filter[node|node.modelFacet.isPhantomElement()]
	}

	def Iterable<GenNode> getSemanticChildren(GenContainerBase containerBase) {
		return containerBase.containedNodes.filter[node|node.modelFacet !== null]
	}

	/**
	 * @return true when children share same metaclass
	 */
	def boolean hasConformableSemanticChildren(GenContainerBase containerBase) {

		//return let childMetaClasses = getSemanticChildren(containerBase)->collect(node | node.modelFacet.metaClass) in not childMetaClasses->forAll(mc | childMetaClasses->select(mc2 | mc = mc2)->size() = 1)
		var childMetaClasses = getSemanticChildren(containerBase).map[child|child.modelFacet.metaClass];
		return childMetaClasses.size != childMetaClasses.toSet.size
	}

	def dispatch GenClass getLinkEndType(LinkModelFacet facet, UpdaterLinkType type) {
		return null
	}

	def dispatch GenClass getLinkEndType(FeatureLinkModelFacet facet, UpdaterLinkType type) {
		return switch (type) {
			case UpdaterLinkType::INCOMING: facet.targetType
			case UpdaterLinkType::OUTGOING: facet.sourceType
			default: null
		}
	}

	def dispatch GenClass getLinkEndType(TypeLinkModelFacet facet, UpdaterLinkType type) {
		if(type == UpdaterLinkType::INCOMING) return facet.targetType;
		if(type == UpdaterLinkType::OUTGOING && facet.sourceMetaFeature !== null) return facet.sourceType;
		if(facet.containmentMetaFeature === null) return null;
		return facet.containmentMetaFeature.genClass;
	}

}
