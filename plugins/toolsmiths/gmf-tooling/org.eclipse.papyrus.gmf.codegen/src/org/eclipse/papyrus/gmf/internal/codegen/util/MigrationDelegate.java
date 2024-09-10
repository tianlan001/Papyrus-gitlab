/******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *	 Borland - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.internal.common.migrate.MigrationDelegateImpl;

/**
 * Migration from old, 2005-styled GMF models to instances of dynamic 2006 model 
 * (should be updated further to normal 2008 using CustomCopier)
 */
class MigrationDelegate extends MigrationDelegateImpl {
	private EReference myGenAuditContainer_ChildContainers;
	private EReference myGenAuditRoot_Audits;
	private EAttribute myGenAuditRoot_Id;
	private EAttribute myGenAuditRoot_Name;
	private EAttribute myGenAuditRoot_Description;
	private EObject myRootContainer;
	private LinkedHashMap<EObject, Collection<String>> myRequiredPlugins;
	private EObject myProvidersContainer;

	MigrationDelegate() {
	}

	private EPackage gmfgen2006 = EPackage.Registry.INSTANCE.getEPackage(ModelVersions.GMFGEN_2_0);
	private EFactory factory2006 = gmfgen2006.getEFactoryInstance();
	private GMFGenPackage gmfgenNew = GMFGenPackage.eINSTANCE; // any 2008 or later GMFGen as the only thing we use here are class/feature names, not EClasses/EStrFea 

	private EClassifier class_editorCandies = gmfgen2006.getEClassifier(gmfgenNew.getEditorCandies().getName());
	private EClassifier class_providerClassNames = gmfgen2006.getEClassifier(gmfgenNew.getProviderClassNames().getName());
	private EClassifier class_editPartCandies = gmfgen2006.getEClassifier(gmfgenNew.getEditPartCandies().getName());
	private EClassifier class_typeLinkModelFacet = gmfgen2006.getEClassifier(gmfgenNew.getTypeLinkModelFacet().getName());
	private EClass class_genPlugin = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenPlugin().getName());
	private EClass class_genExpressionInterpreter = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenExpressionInterpreter().getName());
	private EClass class_featureLabelModelFacet = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getFeatureLabelModelFacet().getName());
	private EClass class_genAuditRule = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenAuditRule().getName());
	private EClass class_genAuditContainer = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenAuditContainer().getName());
	private EClass class_genAuditRoot = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenAuditRoot().getName());
	private EClass class_genFeatureValueSpec = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenFeatureValueSpec().getName());
	private EClass class_genExpressionProviderBase = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenExpressionProviderBase().getName());
	private EClass class_genExpressionProviderContainer = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenExpressionProviderContainer().getName());
	private EClass class_genEditorGenerator = (EClass) gmfgen2006.getEClassifier(gmfgenNew.getGenEditorGenerator().getName());

	private EStructuralFeature feature_genExpressionProviderBase_container = class_genExpressionProviderBase.getEStructuralFeature(gmfgenNew.getGenExpressionProviderBase_Container().getName());
	private EStructuralFeature feature_genExpressionProviderContainer_providers = class_genExpressionProviderContainer.getEStructuralFeature(gmfgenNew.getGenExpressionProviderContainer_Providers().getName());
	private EStructuralFeature feature_genExpressionProviderContainer_editorGen = class_genExpressionProviderContainer.getEStructuralFeature(gmfgenNew.getGenExpressionProviderContainer_EditorGen().getName());
	private EStructuralFeature feature_genEditorGenerator_expressionProviders = class_genEditorGenerator.getEStructuralFeature(gmfgenNew.getGenEditorGenerator_ExpressionProviders().getName());
	private EStructuralFeature feature_genEditorGenerator_plugin = class_genEditorGenerator.getEStructuralFeature(gmfgenNew.getGenEditorGenerator_Plugin().getName());
	private EStructuralFeature feature_genPlugin_requiredPlugins = class_genPlugin.getEStructuralFeature(gmfgenNew.getGenPlugin_RequiredPlugins().getName());
	private EStructuralFeature feature_featureLabelModelFacet_metaFeatures = class_featureLabelModelFacet.getEStructuralFeature(gmfgenNew.getFeatureLabelModelFacet_MetaFeatures().getName());
	private EStructuralFeature feature_genAuditContainer_path = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Path().getName());
	private EStructuralFeature feature_genAuditContainer_id = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Id().getName());
	private EStructuralFeature feature_genAuditContainer_name = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Name().getName());
	private EStructuralFeature feature_genAuditContainer_description = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Description().getName());
	private EStructuralFeature feature_genAuditContainer_audits = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Audits().getName());
	private EStructuralFeature feature_genAuditContainer_root = class_genAuditContainer.getEStructuralFeature(gmfgenNew.getGenAuditContainer_Root().getName());
	private EStructuralFeature feature_genAuditRoot_categories = class_genAuditRoot.getEStructuralFeature(gmfgenNew.getGenAuditRoot_Categories().getName());
	private EStructuralFeature feature_genAuditRoot_rules = class_genAuditRoot.getEStructuralFeature(gmfgenNew.getGenAuditRoot_Rules().getName());
	private EStructuralFeature feature_genAuditRule_category = class_genAuditRule.getEStructuralFeature(gmfgenNew.getGenAuditRule_Category().getName());

	void init() {
		registerDeletedAttributes(class_editorCandies,
						"diagramFileCreatorClassName", //$NON-NLS-1$
						"preferenceInitializerClassName" //$NON-NLS-1$
		);
		registerDeletedAttributes(class_providerClassNames, 
						"abstractParserClassName", //$NON-NLS-1$
						"structuralFeatureParserClassName", //$NON-NLS-1$
						"structuralFeaturesParserClassName", //$NON-NLS-1$
						"paletteProviderClassName", //$NON-NLS-1$
						"paletteProviderPriority", //$NON-NLS-1$
						"propertyProviderClassName", //$NON-NLS-1$
						"propertyProviderPriority" //$NON-NLS-1$
		);
		registerDeletedAttributes(class_editPartCandies, 
						"referenceConnectionEditPolicyClassName", //$NON-NLS-1$
						"externalNodeLabelHostLayoutEditPolicyClassName" //$NON-NLS-1$
		);
		registerDeletedAttributes(class_typeLinkModelFacet, "createCommandClassName"); //$NON-NLS-1$
		{
			Map<String, EStructuralFeature> renamings = new HashMap<String, EStructuralFeature>();
			renamings.put("requiredPluginIDs", feature_genPlugin_requiredPlugins); //$NON-NLS-1$
			registerRenamedAttributes(class_genExpressionInterpreter, renamings);
		}
		{
			Map<String, EStructuralFeature> renamings = new HashMap<String, EStructuralFeature>();
			renamings.put("metaFeature", feature_featureLabelModelFacet_metaFeatures); //$NON-NLS-1$
			registerRenamedAttributes(class_featureLabelModelFacet, renamings);
		}
		registerRenamedType("CompositeFeatureLabelModelFacet", class_featureLabelModelFacet); //$NON-NLS-1$
		myGenAuditContainer_ChildContainers = createNewReference("childContainers", class_genAuditContainer, true); //$NON-NLS-1$
		{
			Map<String, EStructuralFeature> renamings = new HashMap<String, EStructuralFeature>();
			renamings.put(myGenAuditContainer_ChildContainers.getName(), myGenAuditContainer_ChildContainers);
			registerRenamedAttributes(class_genAuditContainer, renamings);
		}
		myGenAuditRoot_Id = (EAttribute) EcoreUtil.copy(gmfgenNew.getGenAuditContainer_Id());
		myGenAuditRoot_Name = (EAttribute) EcoreUtil.copy(gmfgenNew.getGenAuditContainer_Name());
		myGenAuditRoot_Description = (EAttribute) EcoreUtil.copy(gmfgenNew.getGenAuditContainer_Description());
		myGenAuditRoot_Audits = createNewReference("audits", class_genAuditRule, true); //$NON-NLS-1$
		{
			Map<String, EStructuralFeature> renamings = new HashMap<String, EStructuralFeature>();
			renamings.put(myGenAuditRoot_Audits.getName(), myGenAuditRoot_Audits);
			renamings.put(myGenAuditContainer_ChildContainers.getName(), myGenAuditContainer_ChildContainers);
			renamings.put(myGenAuditRoot_Id.getName(), myGenAuditRoot_Id);
			renamings.put(myGenAuditRoot_Name.getName(), myGenAuditRoot_Name);
			renamings.put(myGenAuditRoot_Description.getName(), myGenAuditRoot_Description);
			registerRenamedAttributes(class_genAuditRoot, renamings);
		}
		// --->
		registerNarrowedAbstractType("GenFeatureInitializer", class_genFeatureValueSpec); //$NON-NLS-1$

		myRootContainer = null;
		myProvidersContainer = null;
		myRequiredPlugins = null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean setValue(EObject object, EStructuralFeature feature, Object value, int position) {
		if (!gmfgen2006.equals(object.eClass().getEPackage())) {
			return super.setValue(object, feature, value, position);
		}
		if (feature_genPlugin_requiredPlugins.equals(feature) && class_genExpressionInterpreter.isInstance(object)) {
			String requiredPlugin = (String) value;
			saveRequiredPlugin(object, requiredPlugin);
		} else if (myGenAuditRoot_Id.equals(feature)) {
			EObject root = object;
			String id = (String) value;
			EObject rootContainer = getOrCreateRootContainerOnce(root);
			rootContainer.eSet(feature_genAuditContainer_id, id);
			fireMigrationApplied(true);
		} else if (myGenAuditRoot_Name.equals(feature)) {
			EObject root = object;
			String name = (String) value;
			EObject rootContainer = getOrCreateRootContainerOnce(root);
			rootContainer.eSet(feature_genAuditContainer_name, name);
			fireMigrationApplied(true);
		} else if (myGenAuditRoot_Description.equals(feature)) {
			EObject root = object;
			String description = (String) value;
			EObject rootContainer = getOrCreateRootContainerOnce(root);
			rootContainer.eSet(feature_genAuditContainer_description, description);
			fireMigrationApplied(true);
		} else if (myGenAuditContainer_ChildContainers.equals(feature) && class_genAuditRoot.isInstance(object)) {
			EObject root = object;
			EObject container = (EObject)value;
			if (myRootContainer != null) {
				((List<EObject>)container.eGet(feature_genAuditContainer_path)).add(myRootContainer);
			}
			((List<EObject>)root.eGet(feature_genAuditRoot_categories)).add(container);
			fireMigrationApplied(true);
		} else if (myGenAuditRoot_Audits.equals(feature) && class_genAuditRoot.isInstance(object)) {
			EObject root = object;
			EObject rule = (EObject)value;
			if (myRootContainer != null) {
				rule.eSet(feature_genAuditRule_category, myRootContainer);
				fireMigrationApplied(true);
			}
			((List<EObject>)root.eGet(feature_genAuditRoot_rules)).add(rule);
		} else if (myGenAuditContainer_ChildContainers.equals(feature) && class_genAuditContainer.isInstance(object)) {
			EObject parent = object;
			EObject container = (EObject)value;
			((List<EObject>)container.eGet(feature_genAuditContainer_path)).addAll(((List<EObject>)parent.eGet(feature_genAuditContainer_path)));
			((List<EObject>)container.eGet(feature_genAuditContainer_path)).add(parent);
			((List<EObject>)getOrCreateRoot(parent).eGet(feature_genAuditRoot_categories)).add(container);
			fireMigrationApplied(true);
		} else if (feature_genAuditContainer_audits.equals(feature) && class_genAuditContainer.isInstance(object)) {
			EObject container = object;
			EObject rule = (EObject)value;
			rule.eSet(feature_genAuditRule_category, container);
			((List<EObject>)getOrCreateRoot(container).eGet(feature_genAuditRoot_rules)).add(rule);
		} else {
			// other cases are would be processed as defaults
			return super.setValue(object, feature, value, position);
		}
		return true;
	}

	private void saveRequiredPlugin(EObject expressionProvider, String requiredPlugin) {
		if (myRequiredPlugins == null) {
			myRequiredPlugins = new LinkedHashMap<EObject, Collection<String>>();
		}
		Collection<String> requiredPlugins = myRequiredPlugins.get(expressionProvider);
		if (requiredPlugins == null) {
			requiredPlugins = new ArrayList<String>();
		}
		requiredPlugins.add(requiredPlugin);
		myRequiredPlugins.put(expressionProvider, requiredPlugins);
	}
	
	private Map<EObject, Collection<String>> getSavedRequiredPlugins() {
		return myRequiredPlugins;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preResolve() {
		if (getSavedRequiredPlugins() != null) {
			for (EObject expressionProvider : getSavedRequiredPlugins().keySet()) {
				EObject container = (EObject) expressionProvider.eGet(feature_genExpressionProviderBase_container);
				if (container == null) {
					container = getOrCreateParenlessProvidersContainerOnce(expressionProvider);
					((List<EObject>)container.eGet(feature_genExpressionProviderContainer_providers)).add(expressionProvider);
				}
				EObject editor = (EObject) container.eGet(feature_genExpressionProviderContainer_editorGen);
				if (editor == null) {
					editor = factory2006.create(class_genEditorGenerator);
					container.eResource().getContents().add(editor); //XXX!!!
					editor.eSet(feature_genEditorGenerator_expressionProviders, container);
				}
				EObject plugin = (EObject) editor.eGet(feature_genEditorGenerator_plugin);
				if (plugin == null) {
					plugin = factory2006.create(class_genPlugin);
					editor.eSet(feature_genEditorGenerator_plugin, plugin);
				}
				((List<String>)plugin.eGet(feature_genPlugin_requiredPlugins)).addAll(getSavedRequiredPlugins().get(expressionProvider));
				fireMigrationApplied(true);
			}
			getSavedRequiredPlugins().clear();
		}
	}
	
	private EObject getOrCreateParenlessProvidersContainerOnce(EObject expressionProvider) {
		if (myProvidersContainer == null) {
			myProvidersContainer = factory2006.create(class_genExpressionProviderContainer);
			expressionProvider.eResource().getContents().add(myProvidersContainer);
		}
		return myProvidersContainer;
	}

	@SuppressWarnings("unchecked")
	private EObject getOrCreateRootContainerOnce(EObject root) {
		if (myRootContainer == null) {
			myRootContainer = factory2006.create(class_genAuditContainer);
			((List<EObject>) root.eGet(feature_genAuditRoot_categories)).add(myRootContainer);
		}
		return myRootContainer;
	}

	@SuppressWarnings("unchecked")
	private EObject getOrCreateRoot(EObject auditContainer) {
		EObject result = (EObject) auditContainer.eGet(feature_genAuditContainer_root);
		if (result == null) {
			result = createRoot(auditContainer);
			((List<EObject>) result.eGet(feature_genAuditRoot_categories)).add(auditContainer);
		}
		return result;
	}

	private EObject createRoot(EObject child) {
		EObject result = factory2006.create(class_genAuditRoot);
		if (child.eContainer() == null) {
			child.eResource().getContents().add(result);
			fireMigrationApplied(true);
		}
		return result;
	}

	/**
	 * Allows us to use factory of dynamic package from year 2006 to create all instances,
	 */
	@Override
	public String getURI(String prefix, String uri) {
		if (is2005GenModel(prefix, uri)) {
			return ModelVersions.GMFGEN_2_0;
		}
		return super.getURI(prefix, uri);
	}
	
	static boolean is2005GenModel(String prefix, String uri) {
		return "gmfgen".equals(prefix) && (ModelVersions.GMFGEN_1_0.equals(uri) || ModelVersions.GMFGEN_PRE_2_0.equals(uri)); //$NON-NLS-1$
	}
}
