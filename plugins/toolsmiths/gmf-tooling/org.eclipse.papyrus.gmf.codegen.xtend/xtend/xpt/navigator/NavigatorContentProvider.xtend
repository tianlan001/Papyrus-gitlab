/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Modified by Patrick Tessier (CEA LIST)
 * Emilien Perico (Atos Origin) - update template for GMF 2.2 compliance
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.codegen.util.CodeGenUtil
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorChildReference
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorPathSegment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorReferenceType
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.Externalizer
import xpt.editor.VisualIDRegistry

@Singleton class NavigatorContentProvider {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;
	@Inject extension CodeStyle

	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject Externalizer xptExternalizer;
	@Inject NavigatorGroup navigatorGroup;
	@Inject NavigatorAbstractNavigatorItem abstractNavigatorItem;
	@Inject NavigatorItem xptNavigatorItem

	def className(GenNavigator it) '''«it.contentProviderClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def NavigatorContentProvider(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» implements org.eclipse.ui.navigator.ICommonContentProvider {

			«attributes(it)»
			«constructor(it)»
			«genAllMethodNodeCase» 
			«iContentProvider(it)»
			«iStructuredContentProvider(it)»
			«iMementoAware(it)»
			«iCommonContentProvider(it)»
			«iTreeContentProvider(it)»
		}
	'''

	def attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private static final Object[] EMPTY_ARRAY = new Object[0];

		«generatedMemberComment()»
		private org.eclipse.jface.viewers.Viewer myViewer;

		«generatedMemberComment()»
		private org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain myEditingDomain;

		«generatedMemberComment()»
		private org.eclipse.emf.workspace.util.WorkspaceSynchronizer myWorkspaceSynchronizer;

		«generatedMemberComment()»
		private Runnable myViewerRefreshRunnable;
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		public «className(it)»() {
			«initCommonAttributes(it)»
		}
	'''

	def initCommonAttributes(GenNavigator it) '''
		org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = «createEditingDomain(it)»;
		myEditingDomain = (org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain) editingDomain;
		@SuppressWarnings("serial")
		java.util.Map<org.eclipse.emf.ecore.resource.Resource, java.lang.Boolean> map = new java.util.HashMap<«diamondOp(it.editorGen.diagram, "org.eclipse.emf.ecore.resource.Resource", "java.lang.Boolean")»>() {
			«overrideI»
			public java.lang.Boolean get(java.lang.Object key) {
				if (!containsKey(key)) {
					if (key instanceof org.eclipse.emf.ecore.resource.Resource) {
						put((org.eclipse.emf.ecore.resource.Resource) key, java.lang.Boolean.TRUE);
					}
				}
				return super.get(key);
			}
		};
		myEditingDomain.setResourceToReadOnlyMap(map);
		myViewerRefreshRunnable = new Runnable() {
			«overrideI»
			public void run() {
				if (myViewer != null) {
					myViewer.refresh();
				}
			}
		};
		myWorkspaceSynchronizer = new org.eclipse.emf.workspace.util.WorkspaceSynchronizer(editingDomain, new org.eclipse.emf.workspace.util.WorkspaceSynchronizer.Delegate() {
			«overrideC»
			public void dispose() {
			}

			«overrideC»
			public boolean handleResourceChanged(final org.eclipse.emf.ecore.resource.Resource resource) {
				«processChanges(it)»
			}

			«overrideC»
			public boolean handleResourceDeleted(org.eclipse.emf.ecore.resource.Resource resource) {
				«processChanges(it)»
			}

			«overrideC»
			public boolean handleResourceMoved(org.eclipse.emf.ecore.resource.Resource resource, final org.eclipse.emf.common.util.URI newURI) {
				«processChanges(it)»
			}
		});
	'''

	def processChanges(GenNavigator it) '''
		for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = myEditingDomain.getResourceSet().getResources().iterator(); it.hasNext();) {
			org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
				nextResource.unload();
		}
		if (myViewer != null) {
			myViewer.getControl().getDisplay().asyncExec(myViewerRefreshRunnable);
		}
		return true;
	'''

	def iContentProvider(GenNavigator it) '''
		«dispose(it)»

		«inputChanged(it)»

		«/** unloadAllResources and asyncRefresh are package-visible because are accessed from WorkspaceSynchronizer.Delegate inner class */generatedMemberComment()»
		void unloadAllResources() {
			for (org.eclipse.emf.ecore.resource.Resource nextResource : myEditingDomain.getResourceSet().getResources()) {
				nextResource.unload();
			}
		}

		«generatedMemberComment()»
		void asyncRefresh() {
			if (myViewer != null && !myViewer.getControl().isDisposed()) {
				myViewer.getControl().getDisplay().asyncExec(myViewerRefreshRunnable);
			}
		}
	'''

	def dispose(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public void dispose() {
			myWorkspaceSynchronizer.dispose();
			myWorkspaceSynchronizer = null;
			myViewerRefreshRunnable = null;

			for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = myEditingDomain.getResourceSet().getResources().iterator(); it.hasNext();) {
				org.eclipse.emf.ecore.resource.Resource resource = it.next();
				resource.unload();
			}

			((org.eclipse.emf.transaction.TransactionalEditingDomain) myEditingDomain).dispose();
			myEditingDomain = null;
		}
	'''

	def inputChanged(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void inputChanged(org.eclipse.jface.viewers.Viewer viewer, Object oldInput, Object newInput) {
			myViewer = viewer;
		}
	'''

	def iStructuredContentProvider(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
	'''

	def iMementoAware(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void restoreState(org.eclipse.ui.IMemento aMemento) {
		}

		«generatedMemberComment()»
		«overrideI»
		public void saveState(org.eclipse.ui.IMemento aMemento) {
		}
	'''

	def iCommonContentProvider(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void init(org.eclipse.ui.navigator.ICommonContentExtensionSite aConfig) {
		}
	'''

	def iTreeContentProvider(GenNavigator it) '''
		«getChildren(it)»

		«getParent(it)»

		«hasChildren(it)»
	'''

	def getChildren(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof org.eclipse.core.resources.IFile) {
				«getFileChildren(it)»
				 	} 

				 	if (parentElement instanceof «navigatorGroup.qualifiedClassName(it)») {
				«getGroupChildren(it)»
			} 

			if (parentElement instanceof «xptNavigatorItem.qualifiedClassName(it)») {
				«getItemChildren()»
			}

			«IF editorGen.diagram.generateShortcutIcon()»
				«getAdaptableChildren(it)»
			«ENDIF»
			«getOtherChildren(it)»
		}

		«getViewChildren(it)»

		«utilityMethods(it)»
	'''

	def getParent(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public Object getParent(Object element) {
			if (element instanceof «abstractNavigatorItem.qualifiedClassName(it)») {
				«abstractNavigatorItem.qualifiedClassName(it)» abstractNavigatorItem = («abstractNavigatorItem.qualifiedClassName(it)») element;
				return abstractNavigatorItem.getParent();
			}
			return null;
		}
	'''

	def hasChildren(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public boolean hasChildren(Object element) {
			return element instanceof org.eclipse.core.resources.IFile || getChildren(element).length > 0;
		}
	'''

	def getFileChildren(GenNavigator it) '''
		«var references = getChildReferencesFrom(it, null)»
		«getFileResource(it)»
		java.util.Collection<Object> result = new java.util.ArrayList<«it.editorGen.diagram.diamondOp('Object')»>();
		«FOR groupName : getGroupNames(references)» 
			«initGroupVariables(groupName, it, references, 'file', null)»
		«ENDFOR»
		java.util.List<org.eclipse.gmf.runtime.notation.View> topViews = new java.util.ArrayList<«it.editorGen.diagram.diamondOp('org.eclipse.gmf.runtime.notation.View')»>(resource.getContents().size());
		for (org.eclipse.emf.ecore.EObject o : resource.getContents()) {
			if (o instanceof org.eclipse.gmf.runtime.notation.View) {
				topViews.add((org.eclipse.gmf.runtime.notation.View) o);
			}
		}
		«FOR ref : references»
			«addNavigatorItemsPrefix(ref)»selectViewsByType(resource.getContents(), «getChildViewType(ref.child)»)«addNavigatorItemsSuffix(ref, 'file', false)»
		«ENDFOR»
		«FOR groupName : getGroupNames(references)»
			«addGroups(groupName, references)»
		«ENDFOR»
		return result.toArray();
	'''

	def getFileResource(GenNavigator it) '''
		org.eclipse.core.resources.IFile file = (org.eclipse.core.resources.IFile) parentElement;
		org.eclipse.emf.common.util.URI fileURI = org.eclipse.emf.common.util.URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		org.eclipse.emf.ecore.resource.Resource resource = myEditingDomain.getResourceSet().getResource(fileURI, true);
	'''

	def getGroupChildren(GenNavigator it) '''
		«navigatorGroup.qualifiedClassName(it)» group = («navigatorGroup.qualifiedClassName(it)») parentElement;
		return group.getChildren();
	'''

	def getItemChildren(GenNavigator it) '''
		«xptNavigatorItem.qualifiedClassName(it)» navigatorItem = («xptNavigatorItem.qualifiedClassName(it)») parentElement;
		if (navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView())) {
			return EMPTY_ARRAY;
		}
		return getViewChildren(navigatorItem.getView(), parentElement);
	'''

	def getAdaptableChildren(GenNavigator it) '''
		/*
		 * Due to plugin.xml restrictions this code will be called only for views representing
		 * shortcuts to this diagram elements created on other diagrams. 
		*/ 
		if (parentElement instanceof org.eclipse.core.runtime.IAdaptable) {
			org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) ((org.eclipse.core.runtime.IAdaptable) parentElement).getAdapter(org.eclipse.gmf.runtime.notation.View.class);
			if (view != null) {
				return getViewChildren(view, parentElement);
			}
		}
	'''

	def getOtherChildren(GenNavigator it) '''
		return EMPTY_ARRAY;
	'''

	def getViewChildren(GenNavigator it) '''
		«generatedMemberComment()»
		private Object[] getViewChildren(org.eclipse.gmf.runtime.notation.View view, Object parentElement) {
			String vid = «xptVisualIDRegistry.getVisualIDMethodCall(it.editorGen.diagram)»(view);
			if (vid != null) { 
				switch (vid) {
				«««	BEGIN: PapyrusGenCode
				««« Restructuration of the case 
				«FOR node : getNavigatorContainerNodes(it)»
					«caseNavigatorNode(node, it)»	
				«ENDFOR»
				«««BEGIN: PapyrusGenCode
				}
			}
			return EMPTY_ARRAY;
		}
	'''

	def utilityMethods(GenNavigator it) '''
		«IF getNavigatorContainerNodes(it).notEmpty»
			«generatedMemberComment»
			private java.util.Collection<?> getLinksSourceByType(java.util.Collection<?> edges, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = edges.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.Edge nextEdge = (org.eclipse.gmf.runtime.notation.Edge) it.next();
					org.eclipse.gmf.runtime.notation.View nextEdgeSource = nextEdge.getSource();
					if (type.equals(nextEdgeSource.getType()) && isOwnView(nextEdgeSource)) {
						result.add(nextEdgeSource);
					}
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> getLinksTargetByType(java.util.Collection<?> edges, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = edges.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.Edge nextEdge = (org.eclipse.gmf.runtime.notation.Edge) it.next();
					org.eclipse.gmf.runtime.notation.View nextEdgeTarget = nextEdge.getTarget();
					if (type.equals(nextEdgeTarget.getType()) && isOwnView(nextEdgeTarget)) {
						result.add(nextEdgeTarget);
					}
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> getOutgoingLinksByType(java.util.Collection<?> nodes, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = nodes.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.View nextNode = (org.eclipse.gmf.runtime.notation.View) it.next();
					result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> getIncomingLinksByType(java.util.Collection<?> nodes, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = nodes.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.View nextNode = (org.eclipse.gmf.runtime.notation.View) it.next();
					result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> getChildrenByType(java.util.Collection<?> nodes, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = nodes.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.View nextNode = (org.eclipse.gmf.runtime.notation.View) it.next();
					result.addAll(selectViewsByType(nextNode.getChildren(), type));
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> getDiagramLinksByType(java.util.Collection<?> diagrams, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = diagrams.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.Diagram nextDiagram = (org.eclipse.gmf.runtime.notation.Diagram) it.next();
					result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
				}
				return result;
			}
		
			«generatedMemberComment»
			private java.util.Collection<?> selectViewsByType(java.util.Collection<?> views, String type) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = views.iterator(); it.hasNext();) {
					org.eclipse.gmf.runtime.notation.View nextView = (org.eclipse.gmf.runtime.notation.View) it.next();
					if (type.equals(nextView.getType()) && isOwnView(nextView)) {
						result.add(nextView);
					}
				}
				return result;
			}

			«generatedMemberComment»
			private java.util.Collection<?> createNavigatorItems(java.util.Collection<?> views, Object parent, boolean isLeafs) {
				java.util.Collection<?> result = new java.util.ArrayList<>();
				for (java.util.Iterator<?> it = views.iterator(); it.hasNext();) {
				result.add(new «getNavigatorItemQualifiedClassName()»((org.eclipse.gmf.runtime.notation.View) it.next(), parent, isLeafs));
				}
				return result;
			}
		«ENDIF»
		«generatedMemberComment()»
		private boolean isOwnView(org.eclipse.gmf.runtime.notation.View view) {
			return «VisualIDRegistry::modelID(editorGen.diagram)».equals(«xptVisualIDRegistry.
			getModelIDMethodCall(editorGen.diagram)»(view));
		}
		«getForeignShortcuts(it)»
	'''

	def getForeignShortcuts(GenNavigator it) '''
		«IF editorGen.diagram.generateCreateShortcutAction() && getChildReferencesFrom(it, editorGen.diagram).notEmpty»
				«generatedMemberComment()»
				private java.util.Collection<?> getForeignShortcuts(org.eclipse.gmf.runtime.notation.Diagram diagram, Object parent) {
					java.util.Collection<?> result = new java.util.ArrayList<>();
					for (java.util.Iterator<?> it = diagram.getChildren().iterator(); it.hasNext();) {
						org.eclipse.gmf.runtime.notation.View nextView = (org.eclipse.gmf.runtime.notation.View) it.next();
						if (!isOwnView(nextView) && nextView.getEAnnotation("Shortcut") != null) { «nonNLS»
							result.add(nextView);
						}
					}
					return createNavigatorItems(result, parent, false);
				}
		«ENDIF»
	'''

	def createEditingDomain(GenNavigator it) '''org.eclipse.emf.workspace.WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain()'''

	//BEGIN: PapyrusGenCode
	//Loop to call generator of each method
	def genAllMethodNodeCase(GenNavigator it)'''
		«FOR container :getNavigatorContainerNodes(it)»
			« caseMethodNodeNode(container,it) »
		«ENDFOR»
	'''

	def initGroupVariables(String groupName, GenNavigator navigator, Iterable<GenNavigatorChildReference> references, String parentVarName, GenCommonBase contextElement) '''
		«navigatorGroup.qualifiedClassName(navigator)» «CodeGenUtil::validJavaIdentifier(groupName)» = new «navigatorGroup.qualifiedClassName(navigator)»(«xptExternalizer.accessorCall(navigator.editorGen, i18nKeyForGroup(groupName, contextElement))», "«getNavigatorReference(groupName, references).groupIcon»", «parentVarName»); «nonNLS(1)»
	'''

	@Localization def String i18nKeyForGroup(String groupName, GenCommonBase contextElement) {
	return 'NavigatorGroupName.' + (if(null !== contextElement) contextElement.stringUniqueIdentifier else 'File') + '.' +
		CodeGenUtil::validJavaIdentifier(groupName)
}

	def addNavigatorItemsPrefix(GenNavigatorChildReference it) '''«IF isInsideGroup()»«CodeGenUtil::validJavaIdentifier(groupName)».addChildren(«ELSE»result.addAll(«ENDIF»createNavigatorItems('''

	def addNavigatorItemsSuffix(GenNavigatorChildReference it, String parentVarName, boolean isLeaf) // 
		''', «IF isInsideGroup()»«CodeGenUtil::validJavaIdentifier(groupName)»«ELSE»«parentVarName»«ENDIF», «isLeaf»));'''

	def addGroups(String groupName, Iterable<GenNavigatorChildReference> references) '''
		«var ref = getNavigatorReference(groupName, references)»
		«IF ref.hideIfEmpty»
			if (!«CodeGenUtil::validJavaIdentifier(groupName)».isEmpty()) {
				result.add(«CodeGenUtil::validJavaIdentifier(groupName)»);
			}
		«ELSE»
			result.add(«CodeGenUtil::validJavaIdentifier(groupName)»);
		«ENDIF»
	'''

	def dispatch getChildViewType(GenDiagram it) '''«VisualIDRegistry::modelID(it)»'''

	def dispatch getChildViewType(GenCommonBase it) '''«xptVisualIDRegistry.typeMethodCall(it)»'''

	def caseNavigatorNode(GenCommonBase it, GenNavigator navigator) '''
		case «VisualIDRegistry::visualID(it)»: {
			«
			//«««BEGIN: PapyrusGenCode
			//	««« this code has been modified to call directly submethods 
			»
			//modification of the template to avoid mistake of 65kb.
				return getViewChildrenFor«it.editPartClassName»(view, parentElement);
			«
			//«««END: PapyrusGenCode
			»
		}
	'''

	def dispatch nailedDownVariable(GenCommonBase it, String varName, String expressionToCast) '''«/* NO-OP, all specific subclasses should be handled */»'''

	def dispatch nailedDownVariable(GenLink it, String varName, String expressionToCast) '''
		org.eclipse.gmf.runtime.notation.Edge «varName» = (org.eclipse.gmf.runtime.notation.Edge) «expressionToCast»;
	'''

	def dispatch nailedDownVariable(GenNode it, String varName, String expressionToCast) '''
		org.eclipse.gmf.runtime.notation.Node «varName» = (org.eclipse.gmf.runtime.notation.Node) «expressionToCast»;
	'''

	def dispatch nailedDownVariable(GenDiagram it, String varName, String expressionToCast) '''
		org.eclipse.gmf.runtime.notation.Diagram «varName» = (org.eclipse.gmf.runtime.notation.Diagram) «expressionToCast»;
	'''

	def dispatch addForeignShortcuts(GenDiagram it) '''
		«IF generateCreateShortcutAction()»
			result.addAll(getForeignShortcuts((org.eclipse.gmf.runtime.notation.Diagram) view, parentElement));
		«ENDIF»
	'''

	def dispatch addForeignShortcuts(GenCommonBase it) ''''''

	def dispatch childrenMethodName(GenLink it, GenNavigatorReferenceType referenceType, GenNavigatorPathSegment segment) '''
		«IF referenceType == GenNavigatorReferenceType::OUT_TARGET_LITERAL»getLinksTargetByType«ELSE»getLinksSourceByType«ENDIF»
	'''

	def dispatch childrenMethodName(GenCommonBase it, GenNavigatorReferenceType referenceType, GenNavigatorPathSegment segment) //
	'''«IF referenceType == GenNavigatorReferenceType::OUT_TARGET_LITERAL»getOutgoingLinksByType« //
	ELSEIF referenceType == GenNavigatorReferenceType::IN_SOURCE_LITERAL»getIncomingLinksByType« //
	ELSEIF segment.from.oclIsKindOf(typeof(GenDiagram)) && segment.to.oclIsKindOf(typeof(GenLink))»getDiagramLinksByType« //
	ELSE»getChildrenByType«ENDIF»'''

	@Localization def i18nAccessors(GenNavigator it) '''
		«FOR groupName : getGroupNames(getChildReferencesFrom(it, null))»
			«internal_i18nAccessors(groupName, null)»
		«ENDFOR»
		«FOR contextElement : getNavigatorContainerNodes(it)»
			«FOR groupName : getGroupNames(getChildReferencesFrom(it, contextElement))»
				«internal_i18nAccessors(groupName, contextElement)»
			«ENDFOR»
		«ENDFOR»
	'''

	@Localization def internal_i18nAccessors(String groupName, GenCommonBase contextElement) '''«IF null !== groupName »«xptExternalizer.
		accessorField(i18nKeyForGroup(groupName, contextElement))»«ENDIF»'''

	@Localization def i18nValues(GenNavigator it) '''
		«FOR groupName : getGroupNames(getChildReferencesFrom(it, null))»
			«internal_i18nValues(groupName, null)»
		«ENDFOR»
		«FOR contextElement : getNavigatorContainerNodes(it)»
			«FOR groupName : getGroupNames(getChildReferencesFrom(it, contextElement))»
				«internal_i18nValues(groupName, contextElement)»
			«ENDFOR»
		«ENDFOR»
	'''

	@Localization def internal_i18nValues(String groupName, GenCommonBase contextElement) '''«IF null !== groupName »«xptExternalizer.
		messageEntry(i18nKeyForGroup(groupName, contextElement), groupName)»«ENDIF»'''

	//END: PapyrusGenCode
	//BEGIN: PapyrusGenCode
	//this template has been modified to fixe bug generation by GMF framework.
	//Is avoid generated method that are greater than 64Kb
	def caseMethodNodeNode(GenCommonBase it, GenNavigator navigator) '''
		/**
		 *
		 *Papyrus Template
		 *this method is a modification of gmf code in order to avoid  getViewChidreen() method becoming greater than 64kb.
		 *@generated
		**/
		private Object[] getViewChildrenFor«it.editPartClassName»(org.eclipse.gmf.runtime.notation.View view, Object parentElement){
			java.util.Collection<?> result = new java.util.ArrayList<>();
			«addForeignShortcuts(it)»
			«var _references = getChildReferencesFrom(navigator, it)»
			«FOR groupNames : getGroupNames(_references)»
				«initGroupVariables(groupNames,navigator, _references, 'parentElement', it)»
			«ENDFOR»

			«IF ! _references.empty»
			«FOR referencesIterator : 1.._references.size»
			«var reference = _references.get(referencesIterator-1)»
			«IF ! reference.findConnectionPaths.empty»
				«FOR pathsIterator : 1..reference.findConnectionPaths.size»
					«var path = reference.findConnectionPaths.get(pathsIterator-1)»
					«IF ! path.segments.empty»
						«FOR segmentsIterator : 1..path.segments.size»
							«var segment = path.segments.get(segmentsIterator-1)»
							«IF referencesIterator==1 && pathsIterator==1 && segmentsIterator==1»java.util.Collection<?> «ENDIF»
								connectedViews = «childrenMethodName(segment.from,reference.referenceType, segment) »(«IF segmentsIterator==1»java.util.Collections.singleton(view)«ELSE»connectedViews«ENDIF», «xptVisualIDRegistry.typeMethodCall(segment.to)»);
						«ENDFOR»
					«ENDIF»
					«addNavigatorItemsPrefix(reference)»connectedViews«addNavigatorItemsSuffix(reference,'parentElement', reference.referenceType != GenNavigatorReferenceType.CHILDREN_LITERAL) »
				«ENDFOR»
			«ENDIF»
			«ENDFOR»
			«ENDIF»

			«FOR groupNames : getGroupNames(_references)»
				« addGroups(groupNames,_references) »
			«ENDFOR»

			return result.toArray();
		}
	'''
}
