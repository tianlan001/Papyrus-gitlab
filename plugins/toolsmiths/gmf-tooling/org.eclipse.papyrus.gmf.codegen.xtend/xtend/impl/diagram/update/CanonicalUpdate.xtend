/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Artem Tikhomirov             - refactoring of containerBaseCanonicalMethods.xpt; extraction of API/non-API of CEP templates
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package impl.diagram.update

import com.google.inject.Inject
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.updater.Utils_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import metamodel.MetaModel
import xpt.diagram.updater.DiagramUpdater
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.editor.VisualIDRegistry
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater
import xpt.diagram.updater.NodeDescriptor
import xpt.diagram.updater.LinkDescriptor

@com.google.inject.Singleton class CanonicalUpdate {

	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;
	
	@Inject MetaModel xptMetaModel;
	@Inject DiagramUpdater xptDiagramUpdater;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject domain2notation xptDomain2notation;
	@Inject NodeDescriptor nodeDescriptor;
	@Inject LinkDescriptor linkDescriptor;
	
def body(GenContainerBase it) '''
	«attributes(it)»

	«refreshOnActivateMethod(it)»

	«getFeaturesToSynchronizeMethod(it)»
	
	«getSemanticChildrenListMethod(it)»

	«isOrphanedMethod(it)»

	«isMyDiagramElementMethod(it)»
	
	«isShortcutMethod(it)»
'''

// used to be package-local field
def attributes(GenContainerBase it) '''
«IF getSemanticChildrenContainmentFeatures(it).size > 1»

	«generatedMemberComment»
	private java.util.Set<org.eclipse.emf.ecore.EStructuralFeature> myFeaturesToSynchronize;
«ENDIF»
'''

/**
 * The canonical update mechanism of the GMF Runtime heavily depends on EditPart listeners being registered
 * prior to actual canonical update (thus, any change from this policy gets known to the interested EditParts.
 * NodeEditPart.activate() installs a transaction post-commit listener that refreshes the edit part. Canonical EditPolicy
 * is installed on a top (diagram) element, and its activation happens before diagram children get activated (and have a chance
 * to install their listeners). Hence this what I believe to be an questionable hack (at least, GEF's
 * activation sequence gets violated). See bugzilla https://bugs.eclipse.org/bugs/show_bug.cgi?id=314670
 */
def refreshOnActivateMethod(GenContainerBase it) '''
	«generatedMemberComment»
	protected void refreshOnActivate() {
		// Need to activate editpart children before invoking the canonical refresh for EditParts to add event listeners
		java.util.List<?> c = getHost().getChildren();
		for (int i = 0; i < c.size(); i++) {
			((org.eclipse.gef.EditPart) c.get(i)).activate();
		}
		super.refreshOnActivate();
	}
'''

def getFeaturesToSynchronizeMethod(GenContainerBase it) '''
		«IF getSemanticChildrenChildFeatures(it).size == 1»
			
				«generatedMemberComment»
				protected org.eclipse.emf.ecore.EStructuralFeature getFeatureToSynchronize() {
					return «xptMetaModel.MetaFeature(getSemanticChildrenContainmentFeatures(it).head)»;
				}
		«ELSEIF getSemanticChildrenChildFeatures(it).size > 1»
			
				«generatedMemberComment»
				protected java.util.Set<org.eclipse.emf.ecore.EStructuralFeature> getFeaturesToSynchronize() {
					if (myFeaturesToSynchronize == null) {
						myFeaturesToSynchronize = new java.util.HashSet<org.eclipse.emf.ecore.EStructuralFeature>();
						«FOR f : getSemanticChildrenContainmentFeatures(it)»
							«addContainmentFeature(f)»
						«ENDFOR»
					}
					return myFeaturesToSynchronize;
				}
		«ENDIF»
	'''

def getSemanticChildrenListMethod(GenContainerBase it) '''
		«generatedMemberComment»
		protected java.util.List<org.eclipse.emf.ecore.EObject> getSemanticChildrenList() {
			«IF hasSemanticChildren(it)/*REVISIT: is there real need for this check - Generator seems to consult needsCanonicalEP, which in turns ensures there are semantic children?*/»
				org.eclipse.gmf.runtime.notation.View viewObject = (org.eclipse.gmf.runtime.notation.View) getHost().getModel();
				java.util.LinkedList<org.eclipse.emf.ecore.EObject> result = new java.util.LinkedList<org.eclipse.emf.ecore.EObject>();
				java.util.List<«nodeDescriptor.qualifiedClassName(it.diagram.editorGen.diagramUpdater)»> childDescriptors = «xptDiagramUpdater.
			getSemanticChildrenMethodCall(it)»(viewObject);
				for («nodeDescriptor.qualifiedClassName(it.diagram.editorGen.diagramUpdater)» d : childDescriptors) {
					result.add(d.getModelElement());
				}
				return result;
			«ELSE»
				return java.util.Collections.EMPTY_LIST;
			«ENDIF»
		}
	'''

def boolean isDiagramThatContainsShortcurs(GenContainerBase it) {
	return it.oclIsKindOf(typeof(GenDiagram)) && (it as GenDiagram).containsShortcutsTo.notEmpty;
}

def dispatch isShortcutMethod(GenContainerBase it) ''''''
def dispatch isShortcutMethod(GenDiagram it) '''
«IF containsShortcutsTo.notEmpty»
«generatedMemberComment»
protected static boolean isShortcut(org.eclipse.gmf.runtime.notation.View view) {
	return view.getEAnnotation("Shortcut") != null; «nonNLS(1)»
}
«ENDIF»
'''

def isOrphanedMethod(GenContainerBase it) '''
«generatedMemberComment»
protected boolean isOrphaned(java.util.Collection<org.eclipse.emf.ecore.EObject> semanticChildren, final org.eclipse.gmf.runtime.notation.View view) {
	«IF isDiagramThatContainsShortcurs(it)»
		if (isShortcut(view)) {
			return «xptDiagramUpdater.qualifiedClassName(it.diagram.editorGen.diagramUpdater)».isShortcutOrphaned(view);
		}
	«ENDIF»
	return isMyDiagramElement(view) && !semanticChildren.contains(view.getElement());
}
'''

def isMyDiagramElementMethod(GenContainerBase it) '''
«generatedMemberComment»
private boolean isMyDiagramElement(org.eclipse.gmf.runtime.notation.View view) {
	«var semanticChildrenSize = getSemanticChildren(it).size»
	«IF semanticChildrenSize == 0 /* shall not happen, provided #needsCanonicalEditPolicy is correct */»
	return false;
	«ELSEIF semanticChildrenSize == 1»
	return «VisualIDRegistry::visualID(getSemanticChildren(it).head)» == «xptVisualIDRegistry.getVisualIDMethodCall(it.diagram)»(view);
	«ELSE»
	int visualID = «xptVisualIDRegistry.getVisualIDMethodCall(it.diagram)»(view);
	«IF semanticChildrenSize > 3 /* do not produce switch statement for short lists */»
	switch (visualID) {
		«FOR ch : getSemanticChildren(it)»
		case «VisualIDRegistry::visualID(ch)»:
		«ENDFOR»
			return true;
	}
	return false;
	«ELSE»
	return visualID == «FOR ch : getSemanticChildren(it) SEPARATOR ' || visualID == '»«VisualIDRegistry::visualID(ch)»«ENDFOR»; 
	«ENDIF»
	«ENDIF»
}
'''

//
// accessible from outside

// Alternative implementation of CEP.refreshSemanticChildren
def refreshSemanticChildren(GenContainerBase it, String createdViewsVar, GenDiagramUpdater diagramUpdater) '''
	«var childrenShareSameMetaclass = hasConformableSemanticChildren(it)»
	java.util.List<«nodeDescriptor.qualifiedClassName(diagramUpdater)»> childDescriptors = «
		IF hasSemanticChildren(it) /*REVISIT: is there real need for this check - Generator seems to consult needsCanonicalEP, which in turns ensures there are semantic children?, but with respect to #352271*/»
			«xptDiagramUpdater.getSemanticChildrenMethodCall(it)»((org.eclipse.gmf.runtime.notation.View) getHost().getModel());
		«ELSE»
			/* see #352271 */ java.util.Collections.emptyList();
		«ENDIF»
		java.util.LinkedList<org.eclipse.gmf.runtime.notation.View> orphaned = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>(); 
		// we care to check only views we recognize as ours«IF isDiagramThatContainsShortcurs(it)» and not shortcuts«ENDIF»
		java.util.LinkedList<org.eclipse.gmf.runtime.notation.View> knownViewChildren = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>();
		for (org.eclipse.gmf.runtime.notation.View v : getViewChildren()) {
			«IF isDiagramThatContainsShortcurs(it)»
			if (isShortcut(v)) {
				if («xptDiagramUpdater.qualifiedClassName(it.diagram.editorGen.diagramUpdater)».isShortcutOrphaned(v)) {
					orphaned.add(v);
				}
				continue;
			} 
			«ENDIF»
			if (isMyDiagramElement(v)) {
				knownViewChildren.add(v);
			}
		}
		// alternative to #cleanCanonicalSemanticChildren(getViewChildren(), semanticChildren)
		«IF childrenShareSameMetaclass»
		java.util.HashMap<«nodeDescriptor.qualifiedClassName(diagramUpdater)», java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>> potentialViews = new java.util.HashMap<«nodeDescriptor.qualifiedClassName(diagramUpdater)», java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>>(); 
		«ENDIF»
		//
		// iteration happens over list of desired semantic elements, trying to find best matching View, while original CEP
		// iterates views, potentially losing view (size/bounds) information - i.e. if there are few views to reference same EObject, only last one 
		// to answer isOrphaned == true will be used for the domain element representation, see #cleanCanonicalSemanticChildren()
		for (java.util.Iterator<«diagramUpdater.nodeDescriptorQualifiedClassName»> descriptorsIterator = childDescriptors.iterator(); descriptorsIterator.hasNext();) {
			«nodeDescriptor.qualifiedClassName(diagramUpdater)» next = descriptorsIterator.next();
			String hint = «xptVisualIDRegistry.typeMethodCall(it, 'next.getVisualID()')»;
			java.util.LinkedList<org.eclipse.gmf.runtime.notation.View> perfectMatch = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>(); // both semanticElement and hint match that of NodeDescriptor
			«IF childrenShareSameMetaclass»
			java.util.LinkedList<org.eclipse.gmf.runtime.notation.View> potentialMatch = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>();  // semanticElement matches, hint does not
			«ENDIF»
			for (org.eclipse.gmf.runtime.notation.View childView : getViewChildren()) {
				org.eclipse.emf.ecore.EObject semanticElement = childView.getElement();
				if (next.getModelElement().equals(semanticElement)) {
					if (hint.equals(childView.getType())) {
						perfectMatch.add(childView);
						// actually, can stop iteration over view children here, but
						// may want to use not the first view but last one as a 'real' match (the way original CEP does
						// with its trick with viewToSemanticMap inside #cleanCanonicalSemanticChildren
					«IF childrenShareSameMetaclass»
					} else {
						potentialMatch.add(childView);
					«ENDIF»
					}«/* do not break, loop through all potential view children in hope there would be precise match */»
				}
			}
			if (perfectMatch.size() > 0) {
				descriptorsIterator.remove(); // precise match found no need to create anything for the NodeDescriptor
				// use only one view (first or last?), keep rest as orphaned for further consideration
				knownViewChildren.remove(perfectMatch.getFirst());
			«IF childrenShareSameMetaclass»
			} else if (potentialMatch.size() > 0) {
				«/*
				 * Bug 224206 -  Support switching figure of EditPart with domain element property change: need to create new view for the node descriptor and copy old attributes.
				 * To address this, old implementation tries to change VID of the first View known to reference same semantic element
				 * but it is possible some potentialMatch would be perfectMatch for later NodeDescriptor
				 */»potentialViews.put(next, potentialMatch); 
			«ENDIF»
			}
		}
		// those left in knownViewChildren are subject to removal - they are our diagram elements we didn't find match to,
		// or those we have potential matches to, and thus need to be recreated, preserving size/location information.
		orphaned.addAll(knownViewChildren);
		//
		«IF childrenShareSameMetaclass»
		org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand boundsCommand = new org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand(host().getEditingDomain(), org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages.SetLocationCommand_Label_Resize);
		«ENDIF»
		java.util.ArrayList<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor> viewDescriptors = new java.util.ArrayList<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor>(childDescriptors.size());
		for («nodeDescriptor.qualifiedClassName(diagramUpdater)» next : childDescriptors) {
			String hint = «xptVisualIDRegistry.typeMethodCall(it, 'next.getVisualID()')»;
			org.eclipse.core.runtime.IAdaptable elementAdapter = new CanonicalElementAdapter(next.getModelElement(), hint);
			org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor descriptor = new org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor(elementAdapter, org.eclipse.gmf.runtime.notation.Node.class, hint, org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.APPEND, false, host().getDiagramPreferencesHint());
			viewDescriptors.add(descriptor);
		«IF childrenShareSameMetaclass /*code to preserve bounds makes sense only when there are children of same metaclass that may have distinctive attribute changed */
			/*XXX: IN FACT, we should generate this code IFF child nodes are shapes - no sense to expect bounds in comparments. */»
			java.util.LinkedList<org.eclipse.gmf.runtime.notation.View> possibleMatches = potentialViews.get(next);
			if (possibleMatches != null) {
				// from potential matches, leave those that were not eventually used for some other NodeDescriptor (i.e. those left as orphaned)
				possibleMatches.retainAll(knownViewChildren);
			}
			if (possibleMatches != null && !possibleMatches.isEmpty()) {
				org.eclipse.gmf.runtime.notation.View originalView = possibleMatches.getFirst();
				knownViewChildren.remove(originalView); // remove not to copy properties of the same view again and again
				// add command to copy properties
				if (originalView instanceof org.eclipse.gmf.runtime.notation.Node) {
					if (((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint() instanceof org.eclipse.gmf.runtime.notation.Bounds) {
						org.eclipse.gmf.runtime.notation.Bounds b = (org.eclipse.gmf.runtime.notation.Bounds) ((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint();
						boundsCommand.add(new org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand(boundsCommand.getEditingDomain(), boundsCommand.getLabel(), descriptor, new org.eclipse.draw2d.geometry.Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight())));
					} else if (((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint() instanceof org.eclipse.gmf.runtime.notation.Location) {
						org.eclipse.gmf.runtime.notation.Location l = (org.eclipse.gmf.runtime.notation.Location) ((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint();
						boundsCommand.add(new org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand(boundsCommand.getEditingDomain(), boundsCommand.getLabel(), descriptor, new org.eclipse.draw2d.geometry.Point(l.getX(), l.getY())));
					} else if (((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint() instanceof org.eclipse.gmf.runtime.notation.Size) {
						org.eclipse.gmf.runtime.notation.Size s = (org.eclipse.gmf.runtime.notation.Size) ((org.eclipse.gmf.runtime.notation.Node) originalView).getLayoutConstraint();
						boundsCommand.add(new org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand(boundsCommand.getEditingDomain(), boundsCommand.getLabel(), descriptor, new org.eclipse.draw2d.geometry.Dimension(s.getWidth(), s.getHeight())));
					}
				«/**
				 * Note, we don't support Ratio update (as it was done in #populateViewProperties).
				 * First, SetCompartmentRatioCommand is internal; second reason - I don't believe we generate CEP to update compartments
				 * FIXME However, makes sense to extract this piece for potential extensions.
				 */»}
			}
		«ENDIF»
		}

		boolean changed = deleteViews(orphaned.iterator());
		//
		org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest request = getCreateViewRequest(viewDescriptors);
		org.eclipse.gef.commands.Command cmd = getCreateViewCommand(request);
		if (cmd != null && cmd.canExecute() ) {
			org.eclipse.gmf.runtime.diagram.ui.commands.SetViewMutabilityCommand.makeMutable(new org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter(host().getNotationView())).execute();
			executeCommand(cmd);
			«IF childrenShareSameMetaclass»if (boundsCommand.canExecute()) {
				executeCommand(new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(boundsCommand.reduce()));
			}
			«ENDIF»
			@SuppressWarnings("unchecked")
			java.util.List<org.eclipse.core.runtime.IAdaptable> nl = (java.util.List<org.eclipse.core.runtime.IAdaptable>) request.getNewObject();
			«createdViewsVar».addAll(nl);
		}
		if (changed || «createdViewsVar».size() > 0) {
			postProcessRefreshSemantic(createdViews);
		}
'''

def executeLayoutCommand(GenContainerBase it, String createdViewsVar) '''
	if («createdViewsVar».size() > 1) {
		// perform a layout of the container
		org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand layoutCmd = new org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand(host().getEditingDomain(), «createdViewsVar», host());
		executeCommand(new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(layoutCmd));
	}
'''

def refreshConnectionsBody(GenDiagram it) '''
		«Domain2Notation(it)» domain2NotationMap = new «Domain2Notation(it)»();
		java.util.Collection<«linkDescriptor.qualifiedClassName(editorGen.diagramUpdater)»> linkDescriptors = collectAllLinks(getDiagram(), domain2NotationMap);
		java.util.List<org.eclipse.gmf.runtime.notation.View> edges = new java.util.ArrayList<org.eclipse.gmf.runtime.notation.View>();
		for (Object edge : getDiagram().getEdges())
		{
			if (edge instanceof org.eclipse.gmf.runtime.notation.View)
			{
				edges.add((org.eclipse.gmf.runtime.notation.View) edge);
			}
		}
		java.util.Collection<org.eclipse.gmf.runtime.notation.View> existingLinks = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>(edges);
		for (java.util.Iterator<org.eclipse.gmf.runtime.notation.View> linksIterator = existingLinks.iterator(); linksIterator.hasNext();) {
			org.eclipse.gmf.runtime.notation.Edge nextDiagramLink = (org.eclipse.gmf.runtime.notation.Edge) linksIterator.next();
			int diagramLinkVisualID = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(nextDiagramLink);
			if (diagramLinkVisualID == -1«FOR link : links.filter[gl|gl.modelFacet === null]»«compareLinkVisualID(link)»«ENDFOR») {
				if (nextDiagramLink.getSource() != null && nextDiagramLink.getTarget() != null) {
					linksIterator.remove();
				}
				continue;
			}
			org.eclipse.emf.ecore.EObject diagramLinkObject = nextDiagramLink.getElement();
			org.eclipse.emf.ecore.EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
			org.eclipse.emf.ecore.EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
			for (java.util.Iterator<«it.editorGen.diagramUpdater.linkDescriptorQualifiedClassName»> linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator.hasNext();) {
				«linkDescriptor.qualifiedClassName(it.editorGen.diagramUpdater)» nextLinkDescriptor = linkDescriptorsIterator.next();
				if (diagramLinkObject == nextLinkDescriptor.getModelElement() && diagramLinkSrc == nextLinkDescriptor.getSource() && diagramLinkDst == nextLinkDescriptor.getDestination() && diagramLinkVisualID == nextLinkDescriptor.getVisualID()) {
					linksIterator.remove();
					linkDescriptorsIterator.remove();
					break;
				}
			}
		}
		deleteViews(existingLinks.iterator());
		return createConnections(linkDescriptors, domain2NotationMap);
	'''

def refreshConnectionsAuxMethods(GenDiagram it) '''
	«collectAllLinksMethod(it)»
	«createConnectionsMethod(it)»
	«getEditPartMethod(it)»
	«getDiagramMethod(it)»
	«getSourceEditPartMethod(it)»
	«getTargetEditPartMethod(it)»
	«getHintedEditPartMethod(it)»
'''

def collectAllLinksMethod(GenDiagram it) '''
		«generatedMemberComment»
		private java.util.Collection<«linkDescriptor.qualifiedClassName(it.editorGen.diagramUpdater)»> collectAllLinks(org.eclipse.gmf.runtime.notation.View view, «Domain2Notation(
				it)» domain2NotationMap) {
			if (!«VisualIDRegistry::modelID(it)».equals(«xptVisualIDRegistry.getModelIDMethodCall(it)»(view))) {
				return java.util.Collections.emptyList();
			}
			java.util.LinkedList<«linkDescriptor.qualifiedClassName(it.editorGen.diagramUpdater)»> result = new java.util.LinkedList<«linkDescriptor.
				qualifiedClassName(it.editorGen.diagramUpdater)»>();
			String vid = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(view);
			if (vid != null) {
				switch (vid) {
					«FOR se : it.allSemanticElements»
					«caseSemanticElement(se)»
					«ENDFOR»
				}
			}
			for (java.util.Iterator<?> children = view.getChildren().iterator(); children.hasNext();) {
				result.addAll(collectAllLinks((org.eclipse.gmf.runtime.notation.View) children.next(), domain2NotationMap));
			}
			for (java.util.Iterator<?> edges = view.getSourceEdges().iterator(); edges.hasNext();) {
				result.addAll(collectAllLinks((org.eclipse.gmf.runtime.notation.View) edges.next(), domain2NotationMap));
			}
			return result;
		}
		'''

def createConnectionsMethod(GenDiagram it) '''
«generatedMemberComment»
private java.util.Collection<org.eclipse.core.runtime.IAdaptable> createConnections(java.util.Collection<«linkDescriptor.qualifiedClassName(it.editorGen.diagramUpdater)»> linkDescriptors, «Domain2Notation(it)» domain2NotationMap) {
	java.util.LinkedList<org.eclipse.core.runtime.IAdaptable> adapters = new java.util.LinkedList<org.eclipse.core.runtime.IAdaptable>();
	for («linkDescriptor.qualifiedClassName(editorGen.diagramUpdater)» nextLinkDescriptor : linkDescriptors) {
		org.eclipse.gef.EditPart sourceEditPart = getSourceEditPart(nextLinkDescriptor, domain2NotationMap);
		org.eclipse.gef.EditPart targetEditPart = getTargetEditPart(nextLinkDescriptor, domain2NotationMap);
		if (sourceEditPart == null || targetEditPart == null) {
			continue;
		}
		org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor(nextLinkDescriptor.getSemanticAdapter(), «
			xptVisualIDRegistry.typeMethodCall(it, 'nextLinkDescriptor.getVisualID()')», org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.APPEND, false, ((org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
		org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest ccr = new org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest(descriptor);
		ccr.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_CONNECTION_START);
		ccr.setSourceEditPart(sourceEditPart);
		sourceEditPart.getCommand(ccr);
		ccr.setTargetEditPart(targetEditPart);
		ccr.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_CONNECTION_END);
		org.eclipse.gef.commands.Command cmd = targetEditPart.getCommand(ccr);
		if (cmd != null && cmd.canExecute()) {
			executeCommand(cmd);
			org.eclipse.core.runtime.IAdaptable viewAdapter = (org.eclipse.core.runtime.IAdaptable) ccr.getNewObject();
			if (viewAdapter != null) {
				adapters.add(viewAdapter);
			}
		}
	}
	return adapters;
}
'''

def getEditPartMethod(GenDiagram it) '''
			«generatedMemberComment»
			private org.eclipse.gef.EditPart getEditPart(org.eclipse.emf.ecore.EObject domainModelElement, «Domain2Notation(it)» domain2NotationMap) {
				org.eclipse.gmf.runtime.notation.View view = domain2NotationMap.get(domainModelElement);
				if (view != null) {
					return (org.eclipse.gef.EditPart) getHost().getViewer().getEditPartRegistry().get(view);
				}
				return null;
			}
		'''

def getHintedEditPartMethod(GenDiagram it) '''
			«generatedMemberComment»
			protected final org.eclipse.gef.EditPart getHintedEditPart(org.eclipse.emf.ecore.EObject domainModelElement, «Domain2Notation(it)» domain2NotationMap, int hintVisualId) { 
				org.eclipse.gmf.runtime.notation.View view = domain2NotationMap.getHinted(domainModelElement, «xptVisualIDRegistry.typeMethodCall(it, 'hintVisualId')»);
				if (view != null) {
					return (org.eclipse.gef.EditPart) getHost().getViewer().getEditPartRegistry().get(view);
				}
				return null;
			}
		'''

def getSourceEditPartMethod(GenDiagram it) '''
	«generatedMemberComment»
private org.eclipse.gef.EditPart getSourceEditPart(org.eclipse.gmf.tooling.runtime.update.UpdaterLinkDescriptor descriptor, «Domain2Notation(it)» domain2NotationMap) { 
	return getEditPart(descriptor.getSource(), domain2NotationMap);
}
'''

def getTargetEditPartMethod(GenDiagram it) '''
	«generatedMemberComment»
private org.eclipse.gef.EditPart getTargetEditPart(org.eclipse.gmf.tooling.runtime.update.UpdaterLinkDescriptor descriptor, «Domain2Notation(it)» domain2NotationMap) { 
	return getEditPart(descriptor.getDestination(), domain2NotationMap);
}
'''


def getDiagramMethod(GenContainerBase it) '''
«generatedMemberComment»
private org.eclipse.gmf.runtime.notation.Diagram getDiagram() {
	return ((org.eclipse.gmf.runtime.notation.View) getHost().getModel()).getDiagram();
}
'''



//
// privates

protected def Domain2Notation(GenDiagram it) '''«xptDomain2notation.DomainToNotationClassName(it)»'''

protected def addContainmentFeature(GenFeature it) '''
myFeaturesToSynchronize.add(«xptMetaModel.MetaFeature(it)»);
'''

def compareLinkVisualID(GenLink it) ''' || diagramLinkVisualID == «xpt::editor::VisualIDRegistry::visualID(it)»'''

/**
 * GMF has no links to "leaf" child nodes.
 */
def dispatch caseSemanticElement(GenChildLabelNode it) ''''''
def dispatch caseSemanticElement(GenCommonBase it) '''
case «VisualIDRegistry::visualID(it)»: {
	if (!domain2NotationMap.containsKey(view.getElement())) {
	«/**
	 * Processing each domain element only once. Prevents us from
	 * having duplicated links if a shortcut to one of the "main"
	 * diagram elements present on diagram.
	 */»result.addAll(«xptDiagramUpdater.getContainedLinksMethodCall(it)»(view));
	}
	domain2NotationMap.putView(view.getElement(), view);
	break;	
}
'''
}
