/*****************************************************************************
 * Copyright (c) 2013, 2014, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Michael Golubev (Montages) - initial API and implementation
 * Florian Noyrit - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.plugin

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin
import plugin.Activator
import xpt.Common
import xpt.editor.DocumentProvider
import xpt.expressions.OCLExpressionFactory
import xpt.expressions.getExpression
import xpt.providers.ElementInitializers

@com.google.inject.Singleton class ActivatorImpl {
	@Inject extension Common;
	@Inject extension xpt.CodeStyle;

	@Inject Activator xptActivator;
	@Inject DocumentProvider xptDocProvider;
	@Inject ElementInitializers xptElementInitializers;
	@Inject getExpression xptExpr;
	@Inject OCLExpressionFactory oclFactory;

	def ActivatorImpl(GenPlugin it)'''
		«copyright(editorGen)»
		package «xptActivator.packageName(it)»;

		«generatedClassComment»
		public class «xptActivator.className(it)» extends org.eclipse.ui.plugin.AbstractUIPlugin {

			«attrs(it)»
			«constructor(it)»
			«start(it)»
			«stop(editorGen)»
			«getInstance(it)»
			«getPreferenceStore(it)»
			«getItemProvidersAdaptorFactory(it)»
			«getItemImageDescriptor(it)»
			«getBundleDescriptorImage(it)»
			«findImageDescriptor(it)»
			«getBundleImage(it)»
			«getString(it)»
			«documentProviderGetter(editorGen.diagram)»
			«linkConstraint(editorGen.diagram)»
			«initializerGetter(editorGen.diagram)»
			«initializerSetter(editorGen.diagram)»
			«providersAccessMethods(it)»
			«logError(it)»
			«logInfo(it)»
			«getLogError(it)»
		}
	'''

	def attrs(GenPlugin it)'''
		«generatedMemberComment»
		public static final String ID = "«ID»"; //$NON-NLS-1$

		«generatedMemberComment»
		private org.eclipse.papyrus.infra.core.log.LogHelper myLogHelper;

		«generatedMemberComment»
		public static final org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint DIAGRAM_PREFERENCES_HINT =«// no new line
		»new org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint(ID);

		«generatedMemberComment»
		private static «xptActivator.className(it)» instance;

		«generatedMemberComment»
		private org.eclipse.emf.common.notify.AdapterFactory adapterFactory;

		«generatedMemberComment»
		private «xptDocProvider.qualifiedClassName(editorGen.diagram)» documentProvider;

		«IF editorGen.diagram.links.exists(l| !l.sansDomain)»
			«generatedMemberComment»
			private «editorGen.diagram.getLinkCreationConstraintsQualifiedClassName()» linkConstraints;
		«ENDIF»

		«generatedMemberComment»
		private «xptElementInitializers.qualifiedClassName(editorGen.diagram)» initializers;

		«IF it.editorGen.expressionProviders !== null »
			«FOR p : it.editorGen.expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
				«generatedMemberComment»
				private «xptExpr.getExpressionInterpriterQualifiedClassName(p)» «p.language»Factory;
			«ENDFOR»
		«ENDIF»
	'''

	def constructor(GenPlugin it)'''
		«generatedMemberComment»
		public «xptActivator.className(it)»() {
		}
	'''

	def start(GenPlugin it)'''
		«generatedMemberComment»
		«editorGen.diagram.overrideC»
		public void start(org.osgi.framework.BundleContext context) throws Exception {
			super.start(context);
			instance = this;
			myLogHelper = new org.eclipse.papyrus.infra.core.log.LogHelper(this);
			org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint.registerPreferenceStore(DIAGRAM_PREFERENCES_HINT, getPreferenceStore());
			adapterFactory = org.eclipse.papyrus.infra.gmfdiag.common.Activator.getInstance().getItemProvidersAdapterFactory();
			«it.editorGen.diagram.preferencesPackageName».DiagramPreferenceInitializer diagramPreferenceInitializer = new «it.editorGen.diagram.preferencesPackageName».DiagramPreferenceInitializer();
			diagramPreferenceInitializer.initializeDefaultPreferences();
		}
	'''

	def stop(GenEditorGenerator it)'''
		«generatedMemberComment»
		«diagram.overrideC»
		public void stop(org.osgi.framework.BundleContext context) throws Exception {
			adapterFactory = null;
			«IF diagram.links.exists(l| !l.sansDomain)»
				linkConstraints = null;
			«ENDIF»
			initializers = null;
			«IF expressionProviders !== null »
				«FOR p : expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
					«p.language»Factory = null;
				«ENDFOR»
			«ENDIF»
			instance = null;
			super.stop(context);
		}
	'''

	def getInstance(GenPlugin it)'''
		«generatedMemberComment»
		public static «xptActivator.className(it)» getInstance() {
		return instance;
		}
	'''

	def createAdapterFactory(GenDiagram it)'''
		«generatedMemberComment»
		protected org.eclipse.emf.edit.provider.ComposedAdapterFactory createAdapterFactory() {
			java.util.ArrayList<org.eclipse.emf.common.notify.AdapterFactory> factories = new java.util.ArrayList<«diamondOp('org.eclipse.emf.common.notify.AdapterFactory')»>();
			fillItemProviderFactories(factories);
			return new org.eclipse.emf.edit.provider.ComposedAdapterFactory(factories);
		}
	'''

	def fillItemProviderFactories(GenEditorGenerator it)'''
		«generatedMemberComment»
		protected void fillItemProviderFactories(java.util.List<org.eclipse.emf.common.notify.AdapterFactory> factories) {
			«populateItemProviderFactories('factories', it)»
			factories.add(new org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory());
			factories.add(new org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory());
		}
	'''

	def populateItemProviderFactories(String factoryListVar, GenEditorGenerator it)'''
		«FOR genPackage : it.getAllDomainGenPackages(true)»
			«factoryListVar».add(new «genPackage.qualifiedItemProviderAdapterFactoryClassName»());
		«ENDFOR»
	'''

	def getItemProvidersAdaptorFactory(GenPlugin it)'''
		«generatedMemberComment»
		public org.eclipse.emf.common.notify.AdapterFactory getItemProvidersAdapterFactory() {
			return adapterFactory;
		}
	'''

	def getItemImageDescriptor(GenPlugin it)'''
		«generatedMemberComment»
		public org.eclipse.jface.resource.ImageDescriptor getItemImageDescriptor(Object item) {
			org.eclipse.emf.edit.provider.IItemLabelProvider labelProvider = (org.eclipse.emf.edit.provider.IItemLabelProvider) adapterFactory.adapt(item, org.eclipse.emf.edit.provider.IItemLabelProvider.class);
			if (labelProvider != null) {
				return org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry.getInstance().getImageDescriptor(labelProvider.getImage(item));
			}
			return null;
		}
	'''

	def getBundleDescriptorImage(GenPlugin it)'''
		«generatedMemberComment('Returns an image descriptor for the image file at the given\n'
		+ 'plug-in relative path.',
		'@param path\n'
		+ '           the path\n'
		+ '@return the image descriptor')»
		public static org.eclipse.jface.resource.ImageDescriptor getBundledImageDescriptor(String path) {
			return org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
		}
	'''

	def findImageDescriptor(GenPlugin it)'''
		«generatedMemberComment('Respects images residing in any plug-in. If path is relative,\n'
		+ 'then this bundle is looked up for the image, otherwise, for absolute\n'
		+ 'path, first segment is taken as id of plug-in with image',
		'@param path\n'
		+ '           the path to image, either absolute (with plug-in id as first segment), or relative for bundled images\n'
		+ '@return the image descriptor')»
		public static org.eclipse.jface.resource.ImageDescriptor findImageDescriptor(String path) {
			final org.eclipse.core.runtime.IPath p = new org.eclipse.core.runtime.Path(path);
			if (p.isAbsolute() && p.segmentCount() > 1) {
				return org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin(p.segment(0), p.removeFirstSegments(1).makeAbsolute().toString());
			} else {
				return getBundledImageDescriptor(p.makeAbsolute().toString());
			}
		}
	'''

	def getBundleImage(GenPlugin it)'''
		«generatedMemberComment('Returns an image for the image file at the given plugin relative path.\n'
		+ 'Client do not need to dispose this image. Images will be disposed automatically.',
		'@param path\n'
		+ '           the path\n'
		+ '@return image instance')»
		public org.eclipse.swt.graphics.Image getBundledImage(String path) {
			org.eclipse.swt.graphics.Image image = getImageRegistry().get(path);
			if (image == null) {
				getImageRegistry().put(path, getBundledImageDescriptor(path));
				image = getImageRegistry().get(path);
			}
			return image;
		}
	'''

	def getString(GenPlugin it)'''
		«generatedMemberComment('Returns string from plug-in\'s resource bundle')»
		public static String getString(String key) {
			return org.eclipse.core.runtime.Platform.getResourceString(getInstance().getBundle(), "%" + key); «nonNLS»
		}
	'''

	def documentProviderGetter(GenDiagram it)'''
		«generatedMemberComment»
		public «xptDocProvider.qualifiedClassName(it)» getDocumentProvider() {
			if (documentProvider == null) {
				documentProvider = new «xptDocProvider.qualifiedClassName(it)»();
			}
			return documentProvider;
		}
	'''

	def linkConstraint(GenDiagram it)'''
		«IF links.exists(l| !l.sansDomain) »
			«linkConstraintsGetter(it)»
			«linkConstraintsSetter(it)»
		«ENDIF»
	'''

	def linkConstraintsGetter(GenDiagram it)'''
		«generatedMemberComment»
		public «getLinkCreationConstraintsQualifiedClassName()» getLinkConstraints() {
			return linkConstraints;
		}
	'''

	def linkConstraintsSetter(GenDiagram it)'''
		«generatedMemberComment»
		public void setLinkConstraints(«getLinkCreationConstraintsQualifiedClassName()» lc) {
			this.linkConstraints = lc;
		}
	'''

	def initializerGetter(GenDiagram it)'''
		«generatedMemberComment»
		public «xptElementInitializers.qualifiedClassName(it)» getElementInitializers() {
			return initializers;
		}
	'''

	def initializerSetter(GenDiagram it)'''
		«generatedMemberComment»
		public void setElementInitializers(«xptElementInitializers.qualifiedClassName(it)» i) {
			this.initializers = i;
		}
	'''

	def providersAccessMethods(GenPlugin it)'''
		«IF it.editorGen.expressionProviders !== null »
			«FOR p : it.editorGen.expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
				«providerGetter(p)»
				«providerSetter(p)»
			«ENDFOR»
		«ENDIF»
	'''

	def providerGetter(GenExpressionInterpreter it)'''
		«generatedMemberComment»
		public «oclFactory.qualifiedClassName(it)» get«oclFactory.className(it)»() {
			return «language»Factory;
		}
	'''

	def providerSetter(GenExpressionInterpreter it)'''
		«generatedMemberComment»
		public void set«oclFactory.className(it)»(«oclFactory.qualifiedClassName(it)» f) {
			this.«language»Factory = f;
		}
	'''

	def logError(GenPlugin it)'''
		«generatedMemberComment»
		public void logError(String error) {
			getLogHelper().warn(error);
		}

		«generatedMemberComment»
		public void logError(String error, Throwable throwable) {
			getLogHelper().error(error, throwable);
		}
	'''

	def logInfo(GenPlugin it)'''
		«generatedMemberComment»
		public void logInfo(String message) {
			getLogHelper().info(message);
		}

		«generatedMemberComment»
		public void logInfo(String message, Throwable throwable) {
			getLogHelper().error(message, throwable);
		}
	'''

	def getLogError(GenPlugin it) '''
		«generatedMemberComment»
		public org.eclipse.papyrus.infra.core.log.LogHelper getLogHelper() {
			return myLogHelper;
		}
	'''

	// Perhaps, xpt:editor::Editor or some xpt::CommonCode would be better place for
	// this accessor.
	// XXX besides, consider using preference store directly, without a hint (see comment in Editor.xpt#getPreferencesHint)
	def preferenceHintAccess(GenEditorGenerator it)'''«xptActivator.qualifiedClassName(plugin)».DIAGRAM_PREFERENCES_HINT'''

	def getPreferenceStore(GenPlugin it)'''
		«generatedMemberComment»
		«editorGen.diagram.overrideC»
		public org.eclipse.jface.preference.IPreferenceStore getPreferenceStore() {
			org.eclipse.jface.preference.IPreferenceStore store=org.eclipse.papyrus.infra.gmfdiag.preferences.Activator.getDefault().getPreferenceStore();
			return store;
		}
	'''
}