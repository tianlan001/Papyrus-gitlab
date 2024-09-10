/*****************************************************************************
 * Copyright (c) 2007-2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Artem Tikhomirov (Borland) - [257632] do not rely on EditPart presence for element deletion
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import utils.UtilsItemSemanticEditPolicy
import xpt.Common
import xpt.diagram.editpolicies.childContainerCreateCommand
import xpt.diagram.editpolicies.Utils_qvto
import xpt.editor.VisualIDRegistry

/**
 *	This template should be called only for non-design nodes (modelFacet != null)
 *	because *ItemSemanticEditPolicy responsible for dealing with semantic model
 *	elements and meaningless (should not be generated) for pure design nodes.
 */
@Singleton class NodeItemSemanticEditPolicy {

	@Inject extension Common;
	@Inject extension Utils_qvto;
	@Inject extension UtilsItemSemanticEditPolicy

	@Inject BaseItemSemanticEditPolicy xptBaseItemSemanticEditPolicy;
	@Inject childContainerCreateCommand xptChildContainerCreateCommand;
	@Inject linkCommands xptLinkCommands;
	@Inject VisualIDRegistry xptVisualIDRegistry;

	def className(GenNode it) '''«it.itemSemanticEditPolicyClassName»'''

	def packageName(GenNode it) '''«it.getDiagram().editPoliciesPackageName»'''

	def qualifiedClassName(GenNode it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNode it) '''«qualifiedClassName(it)»'''

	def NodeItemSemanticEditPolicy(GenNode it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» extends «xptBaseItemSemanticEditPolicy.qualifiedClassName(getDiagram())» {

			«xptBaseItemSemanticEditPolicy.defaultConstructor(it)»

			«xptChildContainerCreateCommand.childContainerCreateCommand(it.childNodes)»

			«««	Papyrus REM :
			«««	Test to know how the delete of this EditPart is done : we used the DeleteService or the "Traditional method"

			«IF usingDeleteService»
				«generatedMemberComment»
				«getDestroyElementCommandByService(it)»
			«ELSE»
				«getDestroyElementCommand(it)»
				«IF hasChildrenOrCompartments(it)»
					«addDestroyChildNodesCommand(it)»
				«ENDIF»
			«ENDIF»

			«xptLinkCommands.linkCommands(it)»
		}
	'''

	def getDestroyElementCommand(GenNode it) '''
		«generatedMemberComment()»
		protected org.eclipse.gef.commands.Command getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest req) {
			org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) getHost().getModel();
			org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand cmd = new org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand(getEditingDomain(), null);
			cmd.setTransactionNestingEnabled(true);
			«««	«destroyEdges('view')»
			org.eclipse.emf.ecore.EAnnotation annotation = view.getEAnnotation("Shortcut");«nonNLS»
			if (annotation == null) {
				// there are indirectly referenced children, need extra commands: «it.childNodes.union(compartments.map(c | c.childNodes).flatten).exists[GenChildNode gcn | !isDirectlyOwned(gcn, it)]»
				«IF hasChildrenOrCompartments(it)»
					addDestroyChildNodesCommand(cmd);
				«ENDIF»
				addDestroyShortcutsCommand(cmd, view);
				// delete host element
				java.util.List<org.eclipse.emf.ecore.EObject> todestroy=new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
				todestroy.add(req.getElementToDestroy());
				//cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(req));
				cmd.add(new org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper(new org.eclipse.emf.edit.command.DeleteCommand(getEditingDomain(),todestroy )));
			} else {«««Here, we may play smart and don't generate else for non-toplevel nodes(which can't be shortcuts). Is it worth doing?
				cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), view));
			}
			return getGEFWrapper(cmd.reduce());
		}
	'''

	def addDestroyChildNodesCommand(GenNode it) '''
	«generatedMemberComment()»
	protected void addDestroyChildNodesCommand(org.eclipse.gmf.runtime.common.core.command.ICompositeCommand cmd) {
		org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) getHost().getModel();
		for (java.util.Iterator<?> nit = view.getChildren().iterator(); nit.hasNext();) {
			org.eclipse.gmf.runtime.notation.Node node = (org.eclipse.gmf.runtime.notation.Node) nit.next();
			String vid = «xptVisualIDRegistry.getVisualIDMethodCall(it.diagram)»(node);
			if (vid != null) {
				switch (vid) {
				«FOR cn : it.childNodes»
					«destroyChildNodes(cn, 'node', it)»
				«ENDFOR»
				«FOR compartment : it.compartments»
				«xptVisualIDRegistry.caseVisualID(compartment)»
					for (java.util.Iterator<?> cit = node.getChildren().iterator(); cit.hasNext();) {
						org.eclipse.gmf.runtime.notation.Node cnode = (org.eclipse.gmf.runtime.notation.Node) cit.next();
						String cvid = «xptVisualIDRegistry.getVisualIDMethodCall(it.diagram)»(cnode);
						if (cvid != null) {
							switch (cvid) {
							«FOR cn : compartment.childNodes»
								«destroyChildNodes(cn, 'cnode', it)»
							«ENDFOR»
							}
						}
					}
					break;
				«ENDFOR»
				}
			}
		}
	}
	'''

	def destroyChildNodes(GenChildNode it, String nodeVar, GenNode genNode) '''
	«xptVisualIDRegistry.caseVisualID(it)»
		«destroyEdges(nodeVar)»
		cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest(getEditingDomain(), «nodeVar».getElement(), false))); // directlyOwned: «it.isDirectlyOwned(genNode)»
		// don't need explicit deletion of «nodeVar» as parent's view deletion would clean child views as well
		// cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), «nodeVar»));
		break;
	'''

	/**
	* @param view - Notation element for the passed node
	* assumes 'cmd' to point to composite command
	*/
	def destroyEdges(GenNode it, String view) '''
		«
		///	XXX: Though semantic editpolicy is supposed to create commands that operate with semantic elements only,
		///	old code used to delegate child/link deletion to respective editparts, which in turn led to semantic commands
		///	being combined with notational commands (BaseItemSemanticEditPolicy#addDeleteViewCommand()).
		///	---
		///	Use DiagramUpdater.get[Incoming|Outgoing]View instead, to clean links that are not present on a diagram
		///	(but don't forget to clean corresponding Edge, if any)

		///// This part is commented for Papyrus
		///// Some Papyrus diagrams with lots of elements are reaching the 65K Java limit for method size.
		///// The following change is not supposed to modify the method behavior, just propose a slight more
		///// compact code to avoid size limit.
		///
		////IF genIncomingLinks->notEmpty()-»
		///	for (/EXPAND CodeStyle::G('java.util.Iterator', '?' /*FIXME Refactor once Notation model is Java5*/)» it = /view».getTargetEdges().iterator(); it.hasNext();) {
		///		org.eclipse.gmf.runtime.notation.Edge incomingLink = (org.eclipse.gmf.runtime.notation.Edge) it.next();
		////FOREACH genIncomingLinks AS il-»
		///		if (/EXPAND xpt::editor::VisualIDRegistry::getVisualIDMethodCall FOR getDiagram()»(incomingLink) == /EXPAND xpt::editor::VisualIDRegistry::visualID FOR il») {
		///			/EXPAND impl::diagram::commands::DeleteLinkCommand::newRequest('r', 'incomingLink') FOR il-»
		///			cmd.add(/EXPAND impl::diagram::commands::DeleteLinkCommand::newInstance('r') FOR il»);
		///			cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), incomingLink));
		///			continue;
		///		}
		////ENDFOREACH-»
		///	}
		////ENDIF-»
		////IF genOutgoingLinks->notEmpty()-»
		///	for (/EXPAND CodeStyle::G('java.util.Iterator', '?' /*FIXME Refactor once Notation model is Java5*/)» it = /view».getSourceEdges().iterator(); it.hasNext();) {
		///		org.eclipse.gmf.runtime.notation.Edge outgoingLink = (org.eclipse.gmf.runtime.notation.Edge) it.next();
		////FOREACH genOutgoingLinks AS ol-»
		///		if (/EXPAND xpt::editor::VisualIDRegistry::getVisualIDMethodCall FOR getDiagram()»(outgoingLink) == /EXPAND xpt::editor::VisualIDRegistry::visualID FOR ol») {
		///			/EXPAND impl::diagram::commands::DeleteLinkCommand::newRequest('r', 'outgoingLink') FOR ol-»
		///			cmd.add(/EXPAND impl::diagram::commands::DeleteLinkCommand::newInstance('r') FOR ol»);
		///			cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), outgoingLink));
		///			continue;
		///		}
		////ENDFOREACH-»
		///	}
		////ENDIF-»
 		IF !genIncomingLinks.isEmpty()»
			for (java.util.Iterator<?> it = «view».getTargetEdges().iterator(); it.hasNext();) {
				org.eclipse.gmf.runtime.notation.Edge incomingLink = (org.eclipse.gmf.runtime.notation.Edge) it.next();
				String vid = «xptVisualIDRegistry.getVisualIDMethodCall(getDiagram())»(incomingLink);
				if (vid != null) {
					switch(vid) {
						«IF !genIncomingLinks.filter[l | l.modelFacet instanceof FeatureLinkModelFacet].empty»
							«FOR il : genIncomingLinks.filter[l | l.modelFacet instanceof FeatureLinkModelFacet]»
								case «VisualIDRegistry.visualID(il)»:
							«ENDFOR»
							org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest destroyRefReq = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest(incomingLink.getSource().getElement(), null, incomingLink.getTarget().getElement(), false);
							cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand(destroyRefReq));
							cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), incomingLink));
							break;
						«ENDIF»
						«IF !genIncomingLinks.filter[l | l.modelFacet instanceof TypeLinkModelFacet].empty»
							«FOR il : genIncomingLinks.filter[l | l.modelFacet instanceof TypeLinkModelFacet]»
								case «VisualIDRegistry.visualID(il)»:
							«ENDFOR»
							org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest destroyEltReq = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest(incomingLink.getElement(), false);
							cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(destroyEltReq));
							cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), incomingLink));
							break;
						«ENDIF»
					}
				}
			}
		«ENDIF»

		«IF genOutgoingLinks.isEmpty()»
			for (java.util.Iterator<?> it = «view».getSourceEdges().iterator(); it.hasNext();) {
				org.eclipse.gmf.runtime.notation.Edge outgoingLink = (org.eclipse.gmf.runtime.notation.Edge) it.next();
				String vid = «xptVisualIDRegistry.getVisualIDMethodCall(getDiagram())»(outgoingLink);
				if (vid != null) {
					switch(vid) {
						«IF !genOutgoingLinks.filter[l | l.modelFacet instanceof FeatureLinkModelFacet].empty»
							«FOR ol : genOutgoingLinks.filter[l | l.modelFacet instanceof FeatureLinkModelFacet]»
								case «VisualIDRegistry.visualID(ol)»:
							«ENDFOR»
							org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest destroyRefReq = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest(outgoingLink.getSource().getElement(), null, outgoingLink.getTarget().getElement(), false);
							cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand(destroyRefReq));
							cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), outgoingLink));
							break;
						«ENDIF»
						«IF !genOutgoingLinks.filter[l | l.modelFacet instanceof TypeLinkModelFacet].empty»
							«FOR ol : genOutgoingLinks.filter[l | l.modelFacet instanceof TypeLinkModelFacet]»
								case «VisualIDRegistry.visualID(ol)»:
							«ENDFOR»
							org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest destroyEltReq = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest(outgoingLink.getElement(), false);
							cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(destroyEltReq));
							cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), outgoingLink));
							break;
						«ENDIF»
					}
				}
			}
		«ENDIF»
	'''

	private def static <T> Iterable<T> union(Iterable<? extends T> listA, Iterable<? extends T> listB) {
		var List<T> result = newLinkedList();
		result.addAll(listA);
		result.addAll(listB);
		return result;
	}

}
