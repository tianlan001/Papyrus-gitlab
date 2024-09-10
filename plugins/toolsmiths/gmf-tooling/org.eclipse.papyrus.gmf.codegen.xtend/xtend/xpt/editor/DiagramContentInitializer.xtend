/*****************************************************************************
 * Copyright (c) 2007, 2016, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Florian Noyrit - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.updater.DiagramUpdater
import xpt.diagram.updater.Utils_qvto
import xpt.diagram.updater.NodeDescriptor
import xpt.diagram.updater.LinkDescriptor

@com.google.inject.Singleton class DiagramContentInitializer {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject DiagramUpdater xptDiagramUpdater;
	@Inject Activator xptActivator;
	@Inject NodeDescriptor nodeDescriptor
	@Inject LinkDescriptor linkDescriptor

	def className(GenDiagram it) '''«diagramContentInitializerClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def DiagramContentInitializer(GenDiagram it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» {

			«attributes(it)»

			«initDiagramContent(it)»

			«FOR container : getAllContainers().filter[container|!container.sansDomain]»
				«createChildren(container)»
			«ENDFOR»

			«createNode(it)»

			«createLinks(it)»
			«IF getAllContainers().filter(typeof(GenCompartment)).notEmpty»
				«getCompartment(it)»
			«ENDIF»
		}
	'''

	def attributes(GenDiagram it) '''
			«generatedMemberComment»
			private java.util.Map myDomain2NotationMap = new java.util.HashMap();

			«generatedMemberComment»
			private java.util.Collection myLinkDescriptors = new java.util.LinkedList();
	'''

	def initDiagramContent(GenDiagram it) '''
		«generatedMemberComment»
		public void initDiagramContent(org.eclipse.gmf.runtime.notation.Diagram diagram) {
			if (!«VisualIDRegistry::modelID(it)».equals(diagram.getType())) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Incorrect diagram passed as a parameter: " + diagram.getType());
				return;
			}
			if («xptMetaModel.NotInstance(domainDiagramElement, 'diagram.getElement()')») {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Incorrect diagram element specified: " + diagram.getElement() + " instead of «domainDiagramElement.
			ecoreClass.name»");
				return;
			}
			«createChildrenMethodName(it)»(diagram);
			createLinks(diagram);
		}
	'''

	@MetaDef def createChildrenMethodName(GenContainerBase it) '''create«it.stringUniqueIdentifier»_Children'''

	def createChildren(GenContainerBase it) '''
		«generatedMemberComment»
		private void «createChildrenMethodName(it)»(org.eclipse.gmf.runtime.notation.View view) {
			«collectContainedLinks(it)»
			«IF hasSemanticChildren(it)»
				java.util.Collection childNodeDescriptors = «xptDiagramUpdater.getSemanticChildrenMethodCall(it)»(view);
				for (java.util.Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
					createNode(view, («nodeDescriptor.qualifiedClassName(diagram.editorGen.diagramUpdater)») it.next());
				}
			«ENDIF»
			«createCompartmentsChildren(it)»
		}
	'''

	def dispatch collectContainedLinks(GenContainerBase it) ''''''

	def dispatch collectContainedLinks(GenNode it) '''
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(«xptDiagramUpdater.getOutgoingLinksMethodCall(it)»(view));
	'''

	def dispatch createCompartmentsChildren(GenContainerBase it) ''''''

	def dispatch createCompartmentsChildren(GenNode it) '''
		«FOR comp : it.compartments»
			«callCreateCompartmentChildren(comp)»
		«ENDFOR»
	'''

	/**
	 * Will be called for each compartment of GenNode for GenNode.isSansDomain() == false.
	 * if !GenNode.isSansDomain() => !GenCompartment.isSansDomain() so should not check
	 * !this.isSansDomain() here.
	 */
	def callCreateCompartmentChildren(GenCompartment it) '''
		«createChildrenMethodName(it)»(getCompartment(view, «VisualIDRegistry::visualID(it)»));
	'''

	def createNode(GenDiagram it) '''
		«generatedMemberComment»
		private void createNode(org.eclipse.gmf.runtime.notation.View parentView, «nodeDescriptor.qualifiedClassName(editorGen.diagramUpdater)» nodeDescriptor) {
			final String nodeType = «xptVisualIDRegistry.typeMethodCall(it, 'nodeDescriptor.getVisualID()')»;
			org.eclipse.gmf.runtime.notation.Node node = org.eclipse.gmf.runtime.diagram.core.services.ViewService.createNode(parentView, nodeDescriptor.getModelElement(), nodeType, «xptActivator.
			preferenceHintAccess(editorGen)»);
			switch (nodeDescriptor.getVisualID()) {
				«FOR n : getAllNodes().filter[node|!node.sansDomain]»
					case «VisualIDRegistry::visualID(n)»:
						«createChildrenMethodName(n)»(node);
					return;
				«ENDFOR»		
			}
		}
	'''

	/**
	 * Adopt this code to work with links to links
	 */
	def createLinks(GenDiagram it) '''
		«generatedMemberComment»
		private void createLinks(org.eclipse.gmf.runtime.notation.Diagram diagram) {
			for (boolean continueLinkCreation = true; continueLinkCreation;) {
				continueLinkCreation = false;
				java.util.Collection additionalDescriptors = new java.util.LinkedList();
				for (java.util.Iterator it = myLinkDescriptors.iterator(); it.hasNext();) {
					«linkDescriptor.qualifiedClassName(editorGen.diagramUpdater)» nextLinkDescriptor = («
			linkDescriptor.qualifiedClassName(editorGen.diagramUpdater)») it.next();
					if (!myDomain2NotationMap.containsKey(nextLinkDescriptor.getSource()) || !myDomain2NotationMap.containsKey(nextLinkDescriptor.getDestination())) {
						continue;
					}
					final String linkType = «xptVisualIDRegistry.typeMethodCall(it, 'nextLinkDescriptor.getVisualID()')»;
					org.eclipse.gmf.runtime.notation.Edge edge = org.eclipse.gmf.runtime.diagram.core.services.ViewService.getInstance().createEdge(nextLinkDescriptor.getSemanticAdapter(), diagram, linkType, org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.APPEND, true, «xptActivator.
			preferenceHintAccess(editorGen)»);
					if (edge != null) {
						edge.setSource((org.eclipse.gmf.runtime.notation.View) myDomain2NotationMap.get(nextLinkDescriptor.getSource()));
						edge.setTarget((org.eclipse.gmf.runtime.notation.View) myDomain2NotationMap.get(nextLinkDescriptor.getDestination()));
						it.remove();
						if (nextLinkDescriptor.getModelElement() != null) {
							myDomain2NotationMap.put(nextLinkDescriptor.getModelElement(), edge);
						}
						continueLinkCreation = true;
						switch (nextLinkDescriptor.getVisualID()) {
							«FOR link : it.links»
								«IF link.metaClass !== null»
								case «VisualIDRegistry::visualID(link)»:
								additionalDescriptors.addAll(«xptDiagramUpdater.getOutgoingLinksMethodCall(link)»(edge));
								break;
								«ENDIF»
							«ENDFOR»		
						}
					}
				}
				myLinkDescriptors.addAll(additionalDescriptors);
			}
		}
	'''

	def getCompartment(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.gmf.runtime.notation.Node getCompartment(org.eclipse.gmf.runtime.notation.View node, String visualID) {
			String type = «xptVisualIDRegistry.typeMethodCall(it, 'visualID')»;
			for (java.util.Iterator it = node.getChildren().iterator(); it.hasNext();) {
				org.eclipse.gmf.runtime.notation.View nextView = (org.eclipse.gmf.runtime.notation.View) it.next();
				if (nextView instanceof org.eclipse.gmf.runtime.notation.Node && type.equals(nextView.getType())) {
					return (org.eclipse.gmf.runtime.notation.Node) nextView;
				}
			}
			return null;
		}
	'''
}