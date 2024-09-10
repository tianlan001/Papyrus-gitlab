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
 *	Dmitry Stadnik (Borland) - initial API and implementation
 *	Michael Golubev (Borland) - #244970 (GenChildLabelNode can't be link's source/target)
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 */
package xpt.providers

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import plugin.Activator
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import xpt.CodeStyle
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase

@com.google.inject.Singleton class ModelingAssistantProvider {
	@Inject extension Common;
	@Inject extension ExternalizerUtils_qvto;
	@Inject extension CodeStyle;

	@Inject Activator xptActivator;
	@Inject Externalizer xptExternalizer;

	def className(GenDiagram it) '''«it.modelingAssistantProviderClassName»'''

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider'''

	def ModelingAssistantProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«selectExistingElementForSource(it)»

			«selectExistingElementForTarget(it)»

			«selectExistingElement(it)»

			«isApplicableElement(it)»

			«selectElement(it)»
		}
	'''

	def selectExistingElementForSource(GenDiagram it) '''
		«generatedMemberComment»
		public org.eclipse.emf.ecore.EObject selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable target, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			return selectExistingElement(target, getTypesForSource(target, relationshipType));
		}
	'''

	def selectExistingElementForTarget(GenDiagram it) '''
		«generatedMemberComment»
		public org.eclipse.emf.ecore.EObject selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable source, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			return selectExistingElement(source, getTypesForTarget(source, relationshipType));
		}
	'''

	def selectExistingElement(GenDiagram it) '''
		«generatedMemberComment»
		protected org.eclipse.emf.ecore.EObject selectExistingElement(org.eclipse.core.runtime.IAdaptable host, java.util.Collection types) {
			if (types.isEmpty()) {
			return null;
			}
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart editPart =
			host.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			if (editPart == null) {
			return null;
			}
			org.eclipse.gmf.runtime.notation.Diagram diagram =
			(org.eclipse.gmf.runtime.notation.Diagram) editPart.getRoot().getContents().getModel();
			java.util.HashSet<org.eclipse.emf.ecore.EObject> elements = new java.util.HashSet<«diamondOp('org.eclipse.emf.ecore.EObject')»>();
			for (java.util.Iterator<org.eclipse.emf.ecore.EObject> it = diagram.getElement().eAllContents(); it.hasNext();) {
			org.eclipse.emf.ecore.EObject element = it.next();
			if (isApplicableElement(element, types)) {
			elements.add(element);
			}
			}
			if (elements.isEmpty()) {
			return null;
			}
			return selectElement((org.eclipse.emf.ecore.EObject[]) elements.toArray(new org.eclipse.emf.ecore.EObject[elements.size()]));
		}
	'''

	def isApplicableElement(GenDiagram it) '''
		«generatedMemberComment»
		protected boolean isApplicableElement(org.eclipse.emf.ecore.EObject element, java.util.Collection types) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType type =
					org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry.getInstance().getElementType(element);
			return types.contains(type);
		}
	'''

	def selectElement(GenDiagram it) '''
		«generatedMemberComment»
		protected org.eclipse.emf.ecore.EObject selectElement(org.eclipse.emf.ecore.EObject[] elements) {
			org.eclipse.swt.widgets.Shell shell = org.eclipse.swt.widgets.Display.getCurrent().getActiveShell();
			org.eclipse.jface.viewers.ILabelProvider labelProvider =
				new org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider(«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getItemProvidersAdapterFactory());
			org.eclipse.ui.dialogs.ElementListSelectionDialog dialog =
					new org.eclipse.ui.dialogs.ElementListSelectionDialog(shell, labelProvider);
			dialog.setMessage(«xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForModelingAssistantProvider(it)))»);
			dialog.setTitle(«xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForModelingAssistantProvider(it)))»);
			dialog.setMultipleSelection(false);
			dialog.setElements(elements);
			org.eclipse.emf.ecore.EObject selected = null;
			if (dialog.open() == org.eclipse.jface.window.Window.OK) {
				selected = (org.eclipse.emf.ecore.EObject) dialog.getFirstResult();
			}
			return selected;
		}
	'''

	def newArrayListOfElementTypes(GenCommonBase it, String varName) '''
		java.util.ArrayList<org.eclipse.gmf.runtime.emf.type.core.IElementType> «varName» = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>
	'''

	@Localization protected def String i18nKeyForModelingAssistantProvider(GenDiagram it) {
		return '' + className(it)
	}

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForModelingAssistantProvider(it)), 'Select domain model element')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForModelingAssistantProvider(it)), 'Available domain model elements:')»
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForModelingAssistantProvider(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForModelingAssistantProvider(it)))»
	'''

}
