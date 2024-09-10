/******************************************************************************
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
 *    Michael Golubev (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - maintenance and extra configuration 
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPreferencePage;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Cleaner;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Copier;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Decision;
import org.eclipse.papyrus.gmf.internal.common.reconcile.DefaultDecision;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Matcher;
import org.eclipse.papyrus.gmf.internal.common.reconcile.MergeListsDecision;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Reconciler;
import org.eclipse.papyrus.gmf.internal.common.reconcile.ReconcilerConfigBase;
import org.eclipse.papyrus.gmf.internal.common.reconcile.ReflectiveMatcher;
import org.eclipse.papyrus.gmf.internal.common.reconcile.StringPatternDecision;

public class GMFGenConfig extends ReconcilerConfigBase {

	public GMFGenConfig() {
		final GMFGenPackage GMFGEN = GMFGenPackage.eINSTANCE;
		
		setMatcher(GMFGEN.getGenEditorGenerator(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getGenEditorGenerator_CopyrightText());
		preserveIfSet(GMFGEN.getGenEditorGenerator_PackageNamePrefix());
		preserveIfSet(GMFGEN.getGenEditorGenerator_DomainFileExtension());
		preserveIfSet(GMFGEN.getGenEditorGenerator_DiagramFileExtension());
		preserveIfSet(GMFGEN.getGenEditorGenerator_SameFileForDiagramAndModel());
		preserveIfSet(GMFGEN.getGenEditorGenerator_ModelID());
		preserveIfSet(GMFGEN.getGenEditorGenerator_DynamicTemplates());
		preserveIfSet(GMFGEN.getGenEditorGenerator_TemplateDirectory());
		preserveIfSet(GMFGEN.getGenEditorGenerator_PluginDirectory());

		setMatcher(GMFGEN.getGenPlugin(), ALWAYS_MATCH); // exactly one feature for ALWAYS_MATCH GenEditorGenerator
		preserveIfSet(GMFGEN.getGenPlugin_ID());
		preserveIfSet(GMFGEN.getGenPlugin_Name());
		preserveIfSet(GMFGEN.getGenPlugin_Provider());
		preserveIfSet(GMFGEN.getGenPlugin_Version());
		preserveIfSet(GMFGEN.getGenPlugin_ActivatorClassName());
		preserveIfSet(GMFGEN.getGenPlugin_PrintingEnabled());
		addDecision(GMFGEN.getGenPlugin(), new MergeListsDecision(GMFGEN.getGenPlugin_RequiredPlugins()));

		setMatcher(GMFGEN.getGenEditorView(), ALWAYS_MATCH); //exactly one 
		preserveIfSet(GMFGEN.getGenEditorView_IconPath());
		preserveIfSet(GMFGEN.getGenEditorView_ClassName());
		preserveIfSet(GMFGEN.getGenEditorView_ID());

		setMatcher(GMFGEN.getGenDiagram(), ALWAYS_MATCH);
		restoreOld(GMFGEN.getGenDiagram(), GMFGEN.getGenCommonBase_EditPartClassName());
		restoreOld(GMFGEN.getGenDiagram(), GMFGEN.getGenCommonBase_ItemSemanticEditPolicyClassName());
		restoreOld(GMFGEN.getGenDiagram(), GMFGEN.getGenContainerBase_CanonicalEditPolicyClassName());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getGenDiagram_Synchronized());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getMeasurementUnit_Units());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getShortcuts_ContainsShortcutsTo());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getShortcuts_ShortcutsProvidedFor());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getEditorCandies_CreationWizardIconPath());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getEditorCandies_CreationWizardCategoryID());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getEditorCandies_EditingDomainID());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationEnabled());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_LiveValidationUIFeedback());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationDecorators());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationDecoratorProviderClassName());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationDecoratorProviderPriority());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationProviderClassName());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_ValidationProviderPriority());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_MetricProviderPriority());
		preserveIfSet(GMFGEN.getGenDiagram(), GMFGEN.getBatchValidation_MetricProviderClassName());

		for (EClass node : new EClass[] { GMFGEN.getGenTopLevelNode(), GMFGEN.getGenChildNode(), GMFGEN.getGenChildLabelNode(), GMFGEN.getGenChildSideAffixedNode() }) {
			setMatcher(node, new VisualIDMatcher());
			preserveIfSet(node, GMFGEN.getGenCommonBase_NotationViewFactoryClassName());
			restoreOld(node, GMFGEN.getGenCommonBase_EditPartClassName());
			restoreOld(node, GMFGEN.getGenCommonBase_ItemSemanticEditPolicyClassName());
			restoreOld(node, GMFGEN.getGenContainerBase_CanonicalEditPolicyClassName());
			restoreOld(node, GMFGEN.getGenNode_PrimaryDragEditPolicyQualifiedClassName());
			restoreOld(node, GMFGEN.getGenNode_GraphicalNodeEditPolicyClassName());
			restoreOld(node, GMFGEN.getGenNode_CreateCommandClassName());
		}

		setMatcher(GMFGEN.getGenCompartment(), new VisualIDMatcher());
		preserveIfSet(GMFGEN.getGenCompartment(), GMFGEN.getGenCommonBase_NotationViewFactoryClassName());
		restoreOld(GMFGEN.getGenCompartment(), GMFGEN.getGenCommonBase_EditPartClassName());
		restoreOld(GMFGEN.getGenCompartment(), GMFGEN.getGenCommonBase_ItemSemanticEditPolicyClassName());
		restoreOld(GMFGEN.getGenCompartment(), GMFGEN.getGenContainerBase_CanonicalEditPolicyClassName());
		restoreOld(GMFGEN.getGenCompartment_ListLayout());
		preserveIfSet(GMFGEN.getGenCompartment_CanCollapse());
		preserveIfSet(GMFGEN.getGenCompartment_HideIfEmpty());
		preserveIfSet(GMFGEN.getGenCompartment_NeedsTitle());

		setMatcher(GMFGEN.getGenLink(), new VisualIDMatcher());
		preserveIfSet(GMFGEN.getGenLink(), GMFGEN.getGenCommonBase_NotationViewFactoryClassName());
		restoreOld(GMFGEN.getGenLink(), GMFGEN.getGenCommonBase_EditPartClassName());
		restoreOld(GMFGEN.getGenLink(), GMFGEN.getGenCommonBase_ItemSemanticEditPolicyClassName());
		restoreOld(GMFGEN.getGenLink_CreateCommandClassName());
		restoreOld(GMFGEN.getGenLink_ReorientCommandClassName());
		preserveIfSet(GMFGEN.getGenLink_IncomingCreationAllowed());
		preserveIfSet(GMFGEN.getGenLink_OutgoingCreationAllowed());
		preserveIfSet(GMFGEN.getGenLink_ViewDirectionAlignedWithModel());
		preserveIfSet(GMFGEN.getGenLink_TreeBranch());

		for (EClass label : new EClass[] { GMFGEN.getGenLinkLabel(), GMFGEN.getGenNodeLabel(), GMFGEN.getGenExternalNodeLabel() }) {
			setMatcher(label, new VisualIDMatcher());
			preserveIfSet(label, GMFGEN.getGenCommonBase_NotationViewFactoryClassName());
			restoreOld(label, GMFGEN.getGenCommonBase_EditPartClassName());
			restoreOld(label, GMFGEN.getGenCommonBase_ItemSemanticEditPolicyClassName());
		}

		//if parent node is matched, then viemap is matched automatically because it is [1] feature.
		//there are nothing to reconcile for viewmaps, all their properties are derived
		//we need this only to dig into viewmap attributes
		setMatcherForAllSubclasses(GMFGEN.getViewmap(), ALWAYS_MATCH);

		setMatcher(GMFGEN.getDefaultSizeAttributes(), ALWAYS_MATCH);
		setCopier(GMFGEN.getDefaultSizeAttributes(), Copier.COMPLETE_COPY_NO_CROSSREF);
		preserveIfSet(GMFGEN.getDefaultSizeAttributes_Height());
		preserveIfSet(GMFGEN.getDefaultSizeAttributes_Width());
		
		setMatcherForAllSubclasses(GMFGEN.getAttributes(), ALWAYS_MATCH);
		setCopierForAllSubclasses(GMFGEN.getAttributes(), Copier.COMPLETE_COPY_NO_CROSSREF);

		// provided GenCommonBase matched, custom behaviour should be kept as is
		setMatcher(GMFGEN.getCustomBehaviour(), ALWAYS_MATCH);
		setCopier(GMFGEN.getCustomBehaviour(), Copier.COMPLETE_COPY_NO_CROSSREF);

		// We assume there's only one OpenDiagramBehaviour per GenCommonBase, hence do not attempt to match anything but metaclass
		setMatcher(GMFGEN.getOpenDiagramBehaviour(), ALWAYS_MATCH);
		class KeepOldIfNewIsByPatternOrNotSet extends StringPatternDecision {

			KeepOldIfNewIsByPatternOrNotSet(EAttribute feature, String pattern) {
				super(pattern, feature);
			}

			@Override
			public void apply(EObject current, EObject old) {
				String newValue = (String) current.eGet(getFeature());
				if (isValueSet(old) && (newValue == null || myPattern.matcher(newValue).matches())) {
					preserveOld(current, old);
				} else {
					acceptNew(current, old);
				}
			}
		}
		// XXX not sure whether to keep old value on newValue == null is right, as null value is perfectly
		// legal and sensible (meaning this very diagram). Keeping old for now because it seems easier to reset 
		// value to null rather than to type once again.
		addDecision(GMFGEN.getOpenDiagramBehaviour(), new KeepOldIfNewIsByPatternOrNotSet(GMFGEN.getOpenDiagramBehaviour_DiagramKind(), "^FIXME.*")); //$NON-NLS-1$
		addDecision(GMFGEN.getOpenDiagramBehaviour(), new KeepOldIfNewIsByPatternOrNotSet(GMFGEN.getOpenDiagramBehaviour_EditorID(), "^FIXME.*")); //$NON-NLS-1$
		preserveIfSet(GMFGEN.getOpenDiagramBehaviour_EditPolicyClassName());
		preserveIfSet(GMFGEN.getOpenDiagramBehaviour_OpenAsEclipseEditor());

		// if there's a need to keep manually written openDiagramBehavior, uncomment next line 
		// for Reconciler#handleNotMatchedOld to perform a copy
		// setCopier(GMFGEN.getOpenDiagramBehaviour(), Copier.COMPLETE_COPY);

		setMatcher(GMFGEN.getMetamodelType(), ALWAYS_MATCH);
		restoreOld(GMFGEN.getMetamodelType_EditHelperClassName());
		preserveIfSet(GMFGEN.getMetamodelType(), GMFGEN.getElementType_DisplayName());
		preserveIfSet(GMFGEN.getMetamodelType(), GMFGEN.getElementType_DefinedExternally());

		setMatcher(GMFGEN.getSpecializationType(), ALWAYS_MATCH);
		restoreOld(GMFGEN.getSpecializationType_EditHelperAdviceClassName());
		preserveIfSet(GMFGEN.getSpecializationType(), GMFGEN.getElementType_DisplayName());
		preserveIfSet(GMFGEN.getSpecializationType(), GMFGEN.getElementType_DefinedExternally());

		setMatcher(GMFGEN.getGenPropertySheet(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getGenPropertySheet_ReadOnly());
		preserveIfSet(GMFGEN.getGenPropertySheet_NeedsCaption());
		preserveIfSet(GMFGEN.getGenPropertySheet_PackageName());
		preserveIfSet(GMFGEN.getGenPropertySheet_LabelProviderClassName());

		setMatcherForAllSubclasses(GMFGEN.getGenPropertyTab(), new ReflectiveMatcher(GMFGEN.getGenPropertyTab_ID()));
		preserveIfSet(GMFGEN.getGenStandardPropertyTab(), GMFGEN.getGenPropertyTab_Label());

		addDecision(GMFGEN.getGenCustomPropertyTab(), new KeepOldIfNewIsByPatternOrNotSet(GMFGEN.getGenPropertyTab_Label(), "^Core$")); //$NON-NLS-1$
		preserveIfSet(GMFGEN.getGenCustomPropertyTab_ClassName());
		setCopier(GMFGEN.getGenCustomPropertyTab(), Copier.COMPLETE_COPY_NO_CROSSREF);

		setMatcher(GMFGEN.getGenNavigator(), ALWAYS_MATCH);
		preserveIfRemoved(GMFGEN.getGenEditorGenerator(), GMFGEN.getGenEditorGenerator_Navigator());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_GenerateDomainModelNavigator());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainContentExtensionID());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainContentExtensionName());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainContentExtensionPriority());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainContentProviderClassName());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainLabelProviderClassName());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainModelElementTesterClassName());
		preserveIfSet(GMFGEN.getGenNavigator(), GMFGEN.getGenDomainModelNavigator_DomainNavigatorItemClassName());
		preserveIfSet(GMFGEN.getGenNavigator_ContentExtensionID());
		preserveIfSet(GMFGEN.getGenNavigator_ContentExtensionName());
		preserveIfSet(GMFGEN.getGenNavigator_ContentExtensionPriority());
		preserveIfSet(GMFGEN.getGenNavigator_LinkHelperExtensionID());
		preserveIfSet(GMFGEN.getGenNavigator_SorterExtensionID());
		preserveIfSet(GMFGEN.getGenNavigator_ActionProviderID());
		preserveIfSet(GMFGEN.getGenNavigator_ContentProviderClassName());
		preserveIfSet(GMFGEN.getGenNavigator_LabelProviderClassName());
		preserveIfSet(GMFGEN.getGenNavigator_LinkHelperClassName());
		preserveIfSet(GMFGEN.getGenNavigator_SorterClassName());
		preserveIfSet(GMFGEN.getGenNavigator_ActionProviderClassName());
		preserveIfSet(GMFGEN.getGenNavigator_AbstractNavigatorItemClassName());
		preserveIfSet(GMFGEN.getGenNavigator_NavigatorGroupClassName());
		preserveIfSet(GMFGEN.getGenNavigator_NavigatorItemClassName());
		preserveIfSet(GMFGEN.getGenNavigator_UriInputTesterClassName());
		preserveIfSet(GMFGEN.getGenNavigator_PackageName());

		setMatcher(GMFGEN.getGenDiagramPreferences(), ALWAYS_MATCH);
		setCopier(GMFGEN.getGenDiagramPreferences(), Copier.COMPLETE_COPY_NO_CROSSREF);

		setMatcher(GMFGEN.getGenApplication(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getGenApplication_ID());
		preserveIfSet(GMFGEN.getGenApplication_Title());
		preserveIfSet(GMFGEN.getGenApplication_PackageName());
		preserveIfSet(GMFGEN.getGenApplication_ClassName());
		preserveIfSet(GMFGEN.getGenApplication_PerspectiveId());
		preserveIfSet(GMFGEN.getGenApplication_SupportFiles());

		//
		// XXX ReflectiveMatcher(Kind) instead?
		setMatcher(GMFGEN.getGenStandardPreferencePage(), new Matcher() {

			public boolean match(EObject current, EObject old) {
				assert current instanceof GenStandardPreferencePage;
				assert old instanceof GenStandardPreferencePage;
				GenStandardPreferencePage curPage = (GenStandardPreferencePage) current;
				GenStandardPreferencePage oldPage = (GenStandardPreferencePage) old;
				if (curPage.getParent() == null) {
					//single root page, just match it with other root
					return oldPage.getParent() == null;
				}
				return curPage.getKind() == oldPage.getKind();
			}
		});
		restoreOld(GMFGEN.getGenStandardPreferencePage(), GMFGEN.getGenPreferencePage_ID());
		restoreOld(GMFGEN.getGenStandardPreferencePage(), GMFGEN.getGenPreferencePage_Name());
		setCleaner(GMFGEN.getGenStandardPreferencePage(), new Cleaner.Remove());
		// Although copying old standard pages may seem odd here (i.e. if new model comes without some standard page, most likely this is 
		// intentionally and we don't need to restore the old one), there are cases when copying old is necessary - i.e. user moved standard page
		// out of GenPreferencePage hierarchy (up to GenDiagram) and set #parentCategory explicitly. In latter case, we need to copy his changes.
		// I believe in former case user can just delete the page he no longer needs (and his DGMT no longer gives it to him)
		setCopier(GMFGEN.getGenStandardPreferencePage(), Copier.COMPLETE_COPY_NO_CROSSREF);

		setMatcher(GMFGEN.getGenCustomPreferencePage(), GMFGEN.getGenCustomPreferencePage_QualifiedClassName());
		restoreOld(GMFGEN.getGenCustomPreferencePage_GenerateBoilerplate());
		setCopier(GMFGEN.getGenCustomPreferencePage(), Copier.COMPLETE_COPY_NO_CROSSREF);
		// kepp parent category, if set, for both standard and custom pages 
		preserveIfSet(GMFGEN.getGenStandardPreferencePage(), GMFGEN.getGenPreferencePage_ParentCategory());
		preserveIfSet(GMFGEN.getGenCustomPreferencePage(), GMFGEN.getGenPreferencePage_ParentCategory());

		//
		setMatcher(GMFGEN.getGenPreference(), GMFGEN.getGenPreference_Name()); // XXX or KEY, perhaps, if persistence format is more important?
		preserveIfSet(GMFGEN.getGenPreference_Key());
		preserveIfSet(GMFGEN.getGenPreference_DefaultValue());
		setCopier(GMFGEN.getGenPreference(), Copier.COMPLETE_COPY_NO_CROSSREF);
		//
		// preserve model access attributes, or completely copy old if none in the new model found.
		setMatcher(GMFGEN.getDynamicModelAccess(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getDynamicModelAccess_ClassName());
		preserveIfSet(GMFGEN.getDynamicModelAccess_PackageName());
		setCopier(GMFGEN.getDynamicModelAccess(), Copier.COMPLETE_COPY_NO_CROSSREF);
		
		setMatcher(GMFGEN.getGenExpressionProviderContainer(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getGenJavaExpressionProvider(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getGenJavaExpressionProvider_InjectExpressionBody());

		setMatcher(GMFGEN.getGenParsers(), ALWAYS_MATCH);
		preserveIfSet(GMFGEN.getGenParsers_ClassName());
		preserveIfSet(GMFGEN.getGenParsers_PackageName());
		preserveIfSet(GMFGEN.getGenParsers_ProviderPriority());
		preserveIfSet(GMFGEN.getGenParsers_ExtensibleViaService());

		// next three matchers are pure "record match and do nothing" matchers
		// we need them for CustomParser's copy to update crossreferences afterwards correctly
		// this approach, with only matcher and no further decisions, seems not bad, as GenLabel
		// references only single labelModelFacet, hence we could alsways assume they are matching.
		setMatcher(GMFGEN.getFeatureLabelModelFacet(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getDesignLabelModelFacet(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getLabelModelFacet(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getCustomParser(), GMFGEN.getCustomParser_QualifiedName());
		setCopier(GMFGEN.getCustomParser(), Copier.COMPLETE_COPY_WITH_CROSSREF);

		setMatcher(GMFGEN.getGenContextMenu(), new VisualIDMatcher(GMFGEN.getGenContextMenu_Context()));
		preserveIfSet(GMFGEN.getGenContextMenu(), GMFGEN.getGenContributionManager_ID());
		setCopier(GMFGEN.getGenContextMenu(), Copier.COMPLETE_COPY_WITH_CROSSREF);
		setMatcher(GMFGEN.getGenCustomAction(), GMFGEN.getGenCustomAction_QualifiedClassName());
		preserveIfSet(GMFGEN.getGenCustomAction_GenerateBoilerplate());
		preserveIfSet(GMFGEN.getGenCustomAction_Name());
		setCopier(GMFGEN.getGenCustomAction(), Copier.COMPLETE_COPY_NO_CROSSREF);
		setMatcher(GMFGEN.getGenCommandAction(), GMFGEN.getGenCommandAction_CommandIdentifier());
		preserveIfSet(GMFGEN.getGenCommandAction_Name());
		setCopier(GMFGEN.getGenCommandAction(), Copier.COMPLETE_COPY_NO_CROSSREF); // copy then, if none found
		setMatcher(GMFGEN.getGenSeparator(), GMFGEN.getGenSeparator_GroupName());
		setMatcher(GMFGEN.getGenGroupMarker(), GMFGEN.getGenGroupMarker_GroupName());
		
		setMatcher(GMFGEN.getCreateShortcutAction(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getInitDiagramAction(), ALWAYS_MATCH);
		setMatcher(GMFGEN.getLoadResourceAction(), ALWAYS_MATCH);
		setCopier(GMFGEN.getCreateShortcutAction(), Copier.COMPLETE_COPY_NO_CROSSREF);
		setCopier(GMFGEN.getInitDiagramAction(), Copier.COMPLETE_COPY_NO_CROSSREF);
		setCopier(GMFGEN.getLoadResourceAction(), Copier.COMPLETE_COPY_NO_CROSSREF);
		preserveIfSet(GMFGEN.getCreateShortcutAction(), GMFGEN.getGenAction_Name());
		preserveIfSet(GMFGEN.getCreateShortcutAction(), GMFGEN.getGenAction_QualifiedClassName());
		preserveIfSet(GMFGEN.getInitDiagramAction(), GMFGEN.getGenAction_Name());
		preserveIfSet(GMFGEN.getInitDiagramAction(), GMFGEN.getGenAction_QualifiedClassName());
		preserveIfSet(GMFGEN.getLoadResourceAction(), GMFGEN.getGenAction_Name());
		preserveIfSet(GMFGEN.getLoadResourceAction(), GMFGEN.getGenAction_QualifiedClassName());
		
		setMatcher(GMFGEN.getGenCustomGeneratorExtension(), GMFGEN.getGenCustomGeneratorExtension_Name());
		setCopier(GMFGEN.getGenCustomGeneratorExtension(), new Copier.WithCrossRefsCopier(){
			@Override
			public EObject copyToCurrent(EObject currentParent, EObject old, Reconciler reconciler) {
				EObject result = null;
				if (old instanceof GenCustomGeneratorExtension){
					GenCustomGeneratorExtension oldExtension = (GenCustomGeneratorExtension)old;
					if (!oldExtension.isFromCustomBridge()) {
						result = super.copyToCurrent(currentParent, old, reconciler);
					}
				}
				return result;
			}
		});
	}

	private void restoreOld(EClass eClass, EAttribute feature) {
		addDecision(eClass, new Decision.ALWAYS_OLD(feature));
	}

	private void restoreOld(EAttribute feature) {
		assert !feature.getEContainingClass().isAbstract();
		restoreOld(feature.getEContainingClass(), feature);
	}

	// use this shorthand for features from concrete (non-abstract) classes
	private void preserveIfSet(EAttribute feature) {
		addDecision(feature.getEContainingClass(), new DefaultDecision(feature));
	}

	//FIXME: only attributes for now, allow references
	private void preserveIfSet(EClass eClass, EAttribute feature) {
		// decisions are queried against specific types, hence need to make sure no abstract types get through
		assert !eClass.isAbstract();
		addDecision(eClass, new DefaultDecision(feature));
	}

	private void preserveIfRemoved(EClass eClass, EStructuralFeature feature) {
		addDecision(eClass, new DefaultDecision(feature, true));
	}
}
