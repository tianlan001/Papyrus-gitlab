/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.gmf.codegen.gmfgen.*;
import org.eclipse.papyrus.gmf.codegen.gmfgen.*;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GMFGenFactoryImpl extends EFactoryImpl implements GMFGenFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GMFGenFactory init() {
		try {
			GMFGenFactory theGMFGenFactory = (GMFGenFactory)EPackage.Registry.INSTANCE.getEFactory(GMFGenPackage.eNS_URI);
			if (theGMFGenFactory != null) {
				return theGMFGenFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GMFGenFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GMFGenFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GMFGenPackage.GEN_EDITOR_GENERATOR: return createGenEditorGenerator();
			case GMFGenPackage.GEN_DIAGRAM: return createGenDiagram();
			case GMFGenPackage.GEN_EDITOR_VIEW: return createGenEditorView();
			case GMFGenPackage.GEN_CUSTOM_PREFERENCE_PAGE: return createGenCustomPreferencePage();
			case GMFGenPackage.GEN_STANDARD_PREFERENCE_PAGE: return createGenStandardPreferencePage();
			case GMFGenPackage.GEN_DIAGRAM_PREFERENCES: return createGenDiagramPreferences();
			case GMFGenPackage.GEN_STANDARD_FONT: return createGenStandardFont();
			case GMFGenPackage.GEN_CUSTOM_FONT: return createGenCustomFont();
			case GMFGenPackage.GEN_RGB_COLOR: return createGenRGBColor();
			case GMFGenPackage.GEN_CONSTANT_COLOR: return createGenConstantColor();
			case GMFGenPackage.GEN_PREFERENCE: return createGenPreference();
			case GMFGenPackage.GEN_DIAGRAM_UPDATER: return createGenDiagramUpdater();
			case GMFGenPackage.GEN_PLUGIN: return createGenPlugin();
			case GMFGenPackage.DYNAMIC_MODEL_ACCESS: return createDynamicModelAccess();
			case GMFGenPackage.CUSTOM_BEHAVIOUR: return createCustomBehaviour();
			case GMFGenPackage.SHARED_BEHAVIOUR: return createSharedBehaviour();
			case GMFGenPackage.OPEN_DIAGRAM_BEHAVIOUR: return createOpenDiagramBehaviour();
			case GMFGenPackage.GEN_TOP_LEVEL_NODE: return createGenTopLevelNode();
			case GMFGenPackage.GEN_CHILD_NODE: return createGenChildNode();
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE: return createGenChildSideAffixedNode();
			case GMFGenPackage.GEN_CHILD_LABEL_NODE: return createGenChildLabelNode();
			case GMFGenPackage.GEN_COMPARTMENT: return createGenCompartment();
			case GMFGenPackage.GEN_LINK: return createGenLink();
			case GMFGenPackage.GEN_NODE_LABEL: return createGenNodeLabel();
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL: return createGenExternalNodeLabel();
			case GMFGenPackage.GEN_LINK_LABEL: return createGenLinkLabel();
			case GMFGenPackage.METAMODEL_TYPE: return createMetamodelType();
			case GMFGenPackage.SPECIALIZATION_TYPE: return createSpecializationType();
			case GMFGenPackage.NOTATION_TYPE: return createNotationType();
			case GMFGenPackage.LABEL_MODEL_FACET: return createLabelModelFacet();
			case GMFGenPackage.TYPE_MODEL_FACET: return createTypeModelFacet();
			case GMFGenPackage.TYPE_LINK_MODEL_FACET: return createTypeLinkModelFacet();
			case GMFGenPackage.FEATURE_LINK_MODEL_FACET: return createFeatureLinkModelFacet();
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET: return createFeatureLabelModelFacet();
			case GMFGenPackage.DESIGN_LABEL_MODEL_FACET: return createDesignLabelModelFacet();
			case GMFGenPackage.EXPRESSION_LABEL_MODEL_FACET: return createExpressionLabelModelFacet();
			case GMFGenPackage.COLOR_ATTRIBUTES: return createColorAttributes();
			case GMFGenPackage.STYLE_ATTRIBUTES: return createStyleAttributes();
			case GMFGenPackage.RESIZE_CONSTRAINTS: return createResizeConstraints();
			case GMFGenPackage.DEFAULT_SIZE_ATTRIBUTES: return createDefaultSizeAttributes();
			case GMFGenPackage.LABEL_OFFSET_ATTRIBUTES: return createLabelOffsetAttributes();
			case GMFGenPackage.FIGURE_VIEWMAP: return createFigureViewmap();
			case GMFGenPackage.SNIPPET_VIEWMAP: return createSnippetViewmap();
			case GMFGenPackage.INNER_CLASS_VIEWMAP: return createInnerClassViewmap();
			case GMFGenPackage.PARENT_ASSIGNED_VIEWMAP: return createParentAssignedViewmap();
			case GMFGenPackage.MODELED_VIEWMAP: return createModeledViewmap();
			case GMFGenPackage.VALUE_EXPRESSION: return createValueExpression();
			case GMFGenPackage.GEN_CONSTRAINT: return createGenConstraint();
			case GMFGenPackage.PALETTE: return createPalette();
			case GMFGenPackage.TOOL_ENTRY: return createToolEntry();
			case GMFGenPackage.STANDARD_ENTRY: return createStandardEntry();
			case GMFGenPackage.SEPARATOR: return createSeparator();
			case GMFGenPackage.TOOL_GROUP: return createToolGroup();
			case GMFGenPackage.GEN_FEATURE_SEQ_INITIALIZER: return createGenFeatureSeqInitializer();
			case GMFGenPackage.GEN_FEATURE_VALUE_SPEC: return createGenFeatureValueSpec();
			case GMFGenPackage.GEN_REFERENCE_NEW_ELEMENT_SPEC: return createGenReferenceNewElementSpec();
			case GMFGenPackage.GEN_LINK_CONSTRAINTS: return createGenLinkConstraints();
			case GMFGenPackage.GEN_AUDIT_ROOT: return createGenAuditRoot();
			case GMFGenPackage.GEN_AUDIT_CONTAINER: return createGenAuditContainer();
			case GMFGenPackage.GEN_AUDIT_RULE: return createGenAuditRule();
			case GMFGenPackage.GEN_DOMAIN_ELEMENT_TARGET: return createGenDomainElementTarget();
			case GMFGenPackage.GEN_DIAGRAM_ELEMENT_TARGET: return createGenDiagramElementTarget();
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET: return createGenDomainAttributeTarget();
			case GMFGenPackage.GEN_NOTATION_ELEMENT_TARGET: return createGenNotationElementTarget();
			case GMFGenPackage.GEN_METRIC_CONTAINER: return createGenMetricContainer();
			case GMFGenPackage.GEN_METRIC_RULE: return createGenMetricRule();
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET: return createGenAuditedMetricTarget();
			case GMFGenPackage.GEN_AUDIT_CONTEXT: return createGenAuditContext();
			case GMFGenPackage.GEN_EXPRESSION_PROVIDER_CONTAINER: return createGenExpressionProviderContainer();
			case GMFGenPackage.GEN_JAVA_EXPRESSION_PROVIDER: return createGenJavaExpressionProvider();
			case GMFGenPackage.GEN_EXPRESSION_INTERPRETER: return createGenExpressionInterpreter();
			case GMFGenPackage.GEN_LITERAL_EXPRESSION_PROVIDER: return createGenLiteralExpressionProvider();
			case GMFGenPackage.GEN_NAVIGATOR: return createGenNavigator();
			case GMFGenPackage.GEN_NAVIGATOR_CHILD_REFERENCE: return createGenNavigatorChildReference();
			case GMFGenPackage.GEN_NAVIGATOR_PATH: return createGenNavigatorPath();
			case GMFGenPackage.GEN_NAVIGATOR_PATH_SEGMENT: return createGenNavigatorPathSegment();
			case GMFGenPackage.GEN_PROPERTY_SHEET: return createGenPropertySheet();
			case GMFGenPackage.GEN_STANDARD_PROPERTY_TAB: return createGenStandardPropertyTab();
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB: return createGenCustomPropertyTab();
			case GMFGenPackage.TYPE_TAB_FILTER: return createTypeTabFilter();
			case GMFGenPackage.CUSTOM_TAB_FILTER: return createCustomTabFilter();
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM: return createGenSharedContributionItem();
			case GMFGenPackage.GEN_GROUP_MARKER: return createGenGroupMarker();
			case GMFGenPackage.GEN_SEPARATOR: return createGenSeparator();
			case GMFGenPackage.GEN_CUSTOM_ACTION: return createGenCustomAction();
			case GMFGenPackage.GEN_COMMAND_ACTION: return createGenCommandAction();
			case GMFGenPackage.LOAD_RESOURCE_ACTION: return createLoadResourceAction();
			case GMFGenPackage.INIT_DIAGRAM_ACTION: return createInitDiagramAction();
			case GMFGenPackage.CREATE_SHORTCUT_ACTION: return createCreateShortcutAction();
			case GMFGenPackage.GEN_ACTION_FACTORY_CONTRIBUTION_ITEM: return createGenActionFactoryContributionItem();
			case GMFGenPackage.GEN_MENU_MANAGER: return createGenMenuManager();
			case GMFGenPackage.GEN_TOOL_BAR_MANAGER: return createGenToolBarManager();
			case GMFGenPackage.GEN_CONTEXT_MENU: return createGenContextMenu();
			case GMFGenPackage.GEN_APPLICATION: return createGenApplication();
			case GMFGenPackage.GEN_PARSERS: return createGenParsers();
			case GMFGenPackage.PREDEFINED_PARSER: return createPredefinedParser();
			case GMFGenPackage.PREDEFINED_ENUM_PARSER: return createPredefinedEnumParser();
			case GMFGenPackage.OCL_CHOICE_PARSER: return createOclChoiceParser();
			case GMFGenPackage.EXPRESSION_LABEL_PARSER: return createExpressionLabelParser();
			case GMFGenPackage.CUSTOM_PARSER: return createCustomParser();
			case GMFGenPackage.EXTERNAL_PARSER: return createExternalParser();
			case GMFGenPackage.GEN_VISUAL_EFFECT: return createGenVisualEffect();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION: return createGenCustomGeneratorExtension();
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION: return createGenTemplateInvocation();
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION: return createGenFixedInputsTemplateInvocation();
			case GMFGenPackage.GEN_FLOATING_LABEL: return createGenFloatingLabel();
			case GMFGenPackage.REFRESH_HOOK: return createRefreshHook();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case GMFGenPackage.STANDARD_PREFERENCE_PAGES:
				return createStandardPreferencePagesFromString(eDataType, initialValue);
			case GMFGenPackage.RULER_UNITS:
				return createRulerUnitsFromString(eDataType, initialValue);
			case GMFGenPackage.ROUTING:
				return createRoutingFromString(eDataType, initialValue);
			case GMFGenPackage.LINE_STYLE:
				return createLineStyleFromString(eDataType, initialValue);
			case GMFGenPackage.JFACE_FONT:
				return createJFaceFontFromString(eDataType, initialValue);
			case GMFGenPackage.FONT_STYLE:
				return createFontStyleFromString(eDataType, initialValue);
			case GMFGenPackage.DIAGRAM_COLORS:
				return createDiagramColorsFromString(eDataType, initialValue);
			case GMFGenPackage.PROVIDER_PRIORITY:
				return createProviderPriorityFromString(eDataType, initialValue);
			case GMFGenPackage.LINK_LABEL_ALIGNMENT:
				return createLinkLabelAlignmentFromString(eDataType, initialValue);
			case GMFGenPackage.LABEL_TEXT_ACCESS_METHOD:
				return createLabelTextAccessMethodFromString(eDataType, initialValue);
			case GMFGenPackage.VIEWMAP_LAYOUT_TYPE:
				return createViewmapLayoutTypeFromString(eDataType, initialValue);
			case GMFGenPackage.STANDARD_ENTRY_KIND:
				return createStandardEntryKindFromString(eDataType, initialValue);
			case GMFGenPackage.GEN_SEVERITY:
				return createGenSeverityFromString(eDataType, initialValue);
			case GMFGenPackage.GEN_LANGUAGE:
				return createGenLanguageFromString(eDataType, initialValue);
			case GMFGenPackage.GEN_NAVIGATOR_REFERENCE_TYPE:
				return createGenNavigatorReferenceTypeFromString(eDataType, initialValue);
			case GMFGenPackage.GENERATED_TYPE:
				return createGeneratedTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case GMFGenPackage.STANDARD_PREFERENCE_PAGES:
				return convertStandardPreferencePagesToString(eDataType, instanceValue);
			case GMFGenPackage.RULER_UNITS:
				return convertRulerUnitsToString(eDataType, instanceValue);
			case GMFGenPackage.ROUTING:
				return convertRoutingToString(eDataType, instanceValue);
			case GMFGenPackage.LINE_STYLE:
				return convertLineStyleToString(eDataType, instanceValue);
			case GMFGenPackage.JFACE_FONT:
				return convertJFaceFontToString(eDataType, instanceValue);
			case GMFGenPackage.FONT_STYLE:
				return convertFontStyleToString(eDataType, instanceValue);
			case GMFGenPackage.DIAGRAM_COLORS:
				return convertDiagramColorsToString(eDataType, instanceValue);
			case GMFGenPackage.PROVIDER_PRIORITY:
				return convertProviderPriorityToString(eDataType, instanceValue);
			case GMFGenPackage.LINK_LABEL_ALIGNMENT:
				return convertLinkLabelAlignmentToString(eDataType, instanceValue);
			case GMFGenPackage.LABEL_TEXT_ACCESS_METHOD:
				return convertLabelTextAccessMethodToString(eDataType, instanceValue);
			case GMFGenPackage.VIEWMAP_LAYOUT_TYPE:
				return convertViewmapLayoutTypeToString(eDataType, instanceValue);
			case GMFGenPackage.STANDARD_ENTRY_KIND:
				return convertStandardEntryKindToString(eDataType, instanceValue);
			case GMFGenPackage.GEN_SEVERITY:
				return convertGenSeverityToString(eDataType, instanceValue);
			case GMFGenPackage.GEN_LANGUAGE:
				return convertGenLanguageToString(eDataType, instanceValue);
			case GMFGenPackage.GEN_NAVIGATOR_REFERENCE_TYPE:
				return convertGenNavigatorReferenceTypeToString(eDataType, instanceValue);
			case GMFGenPackage.GENERATED_TYPE:
				return convertGeneratedTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorGenerator createGenEditorGenerator() {
		GenEditorGeneratorImpl genEditorGenerator = new GenEditorGeneratorImpl();
		return genEditorGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagram createGenDiagram() {
		GenDiagramImpl genDiagram = new GenDiagramImpl();
		return genDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorView createGenEditorView() {
		GenEditorViewImpl genEditorView = new GenEditorViewImpl();
		return genEditorView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomPreferencePage createGenCustomPreferencePage() {
		GenCustomPreferencePageImpl genCustomPreferencePage = new GenCustomPreferencePageImpl();
		return genCustomPreferencePage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenStandardPreferencePage createGenStandardPreferencePage() {
		GenStandardPreferencePageImpl genStandardPreferencePage = new GenStandardPreferencePageImpl();
		return genStandardPreferencePage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagramPreferences createGenDiagramPreferences() {
		GenDiagramPreferencesImpl genDiagramPreferences = new GenDiagramPreferencesImpl();
		return genDiagramPreferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenStandardFont createGenStandardFont() {
		GenStandardFontImpl genStandardFont = new GenStandardFontImpl();
		return genStandardFont;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomFont createGenCustomFont() {
		GenCustomFontImpl genCustomFont = new GenCustomFontImpl();
		return genCustomFont;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenRGBColor createGenRGBColor() {
		GenRGBColorImpl genRGBColor = new GenRGBColorImpl();
		return genRGBColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenConstantColor createGenConstantColor() {
		GenConstantColorImpl genConstantColor = new GenConstantColorImpl();
		return genConstantColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenPreference createGenPreference() {
		GenPreferenceImpl genPreference = new GenPreferenceImpl();
		return genPreference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagramUpdater createGenDiagramUpdater() {
		GenDiagramUpdaterImpl genDiagramUpdater = new GenDiagramUpdaterImpl();
		return genDiagramUpdater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenPlugin createGenPlugin() {
		GenPluginImpl genPlugin = new GenPluginImpl();
		return genPlugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicModelAccess createDynamicModelAccess() {
		DynamicModelAccessImpl dynamicModelAccess = new DynamicModelAccessImpl();
		return dynamicModelAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomBehaviour createCustomBehaviour() {
		CustomBehaviourImpl customBehaviour = new CustomBehaviourImpl();
		return customBehaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SharedBehaviour createSharedBehaviour() {
		SharedBehaviourImpl sharedBehaviour = new SharedBehaviourImpl();
		return sharedBehaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpenDiagramBehaviour createOpenDiagramBehaviour() {
		OpenDiagramBehaviourImpl openDiagramBehaviour = new OpenDiagramBehaviourImpl();
		return openDiagramBehaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenTopLevelNode createGenTopLevelNode() {
		GenTopLevelNodeImpl genTopLevelNode = new GenTopLevelNodeImpl();
		return genTopLevelNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenChildNode createGenChildNode() {
		GenChildNodeImpl genChildNode = new GenChildNodeImpl();
		return genChildNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenChildSideAffixedNode createGenChildSideAffixedNode() {
		GenChildSideAffixedNodeImpl genChildSideAffixedNode = new GenChildSideAffixedNodeImpl();
		return genChildSideAffixedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenChildLabelNode createGenChildLabelNode() {
		GenChildLabelNodeImpl genChildLabelNode = new GenChildLabelNodeImpl();
		return genChildLabelNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCompartment createGenCompartment() {
		GenCompartmentImpl genCompartment = new GenCompartmentImpl();
		return genCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLink createGenLink() {
		GenLinkImpl genLink = new GenLinkImpl();
		return genLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNodeLabel createGenNodeLabel() {
		GenNodeLabelImpl genNodeLabel = new GenNodeLabelImpl();
		return genNodeLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenExternalNodeLabel createGenExternalNodeLabel() {
		GenExternalNodeLabelImpl genExternalNodeLabel = new GenExternalNodeLabelImpl();
		return genExternalNodeLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLinkLabel createGenLinkLabel() {
		GenLinkLabelImpl genLinkLabel = new GenLinkLabelImpl();
		return genLinkLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetamodelType createMetamodelType() {
		MetamodelTypeImpl metamodelType = new MetamodelTypeImpl();
		return metamodelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecializationType createSpecializationType() {
		SpecializationTypeImpl specializationType = new SpecializationTypeImpl();
		return specializationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotationType createNotationType() {
		NotationTypeImpl notationType = new NotationTypeImpl();
		return notationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelModelFacet createLabelModelFacet() {
		LabelModelFacetImpl labelModelFacet = new LabelModelFacetImpl();
		return labelModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeModelFacet createTypeModelFacet() {
		TypeModelFacetImpl typeModelFacet = new TypeModelFacetImpl();
		return typeModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureLabelModelFacet createFeatureLabelModelFacet() {
		FeatureLabelModelFacetImpl featureLabelModelFacet = new FeatureLabelModelFacetImpl();
		return featureLabelModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DesignLabelModelFacet createDesignLabelModelFacet() {
		DesignLabelModelFacetImpl designLabelModelFacet = new DesignLabelModelFacetImpl();
		return designLabelModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExpressionLabelModelFacet createExpressionLabelModelFacet() {
		ExpressionLabelModelFacetImpl expressionLabelModelFacet = new ExpressionLabelModelFacetImpl();
		return expressionLabelModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeLinkModelFacet createTypeLinkModelFacet() {
		TypeLinkModelFacetImpl typeLinkModelFacet = new TypeLinkModelFacetImpl();
		return typeLinkModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureLinkModelFacet createFeatureLinkModelFacet() {
		FeatureLinkModelFacetImpl featureLinkModelFacet = new FeatureLinkModelFacetImpl();
		return featureLinkModelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ColorAttributes createColorAttributes() {
		ColorAttributesImpl colorAttributes = new ColorAttributesImpl();
		return colorAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StyleAttributes createStyleAttributes() {
		StyleAttributesImpl styleAttributes = new StyleAttributesImpl();
		return styleAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResizeConstraints createResizeConstraints() {
		ResizeConstraintsImpl resizeConstraints = new ResizeConstraintsImpl();
		return resizeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DefaultSizeAttributes createDefaultSizeAttributes() {
		DefaultSizeAttributesImpl defaultSizeAttributes = new DefaultSizeAttributesImpl();
		return defaultSizeAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelOffsetAttributes createLabelOffsetAttributes() {
		LabelOffsetAttributesImpl labelOffsetAttributes = new LabelOffsetAttributesImpl();
		return labelOffsetAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FigureViewmap createFigureViewmap() {
		FigureViewmapImpl figureViewmap = new FigureViewmapImpl();
		return figureViewmap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SnippetViewmap createSnippetViewmap() {
		SnippetViewmapImpl snippetViewmap = new SnippetViewmapImpl();
		return snippetViewmap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InnerClassViewmap createInnerClassViewmap() {
		InnerClassViewmapImpl innerClassViewmap = new InnerClassViewmapImpl();
		return innerClassViewmap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParentAssignedViewmap createParentAssignedViewmap() {
		ParentAssignedViewmapImpl parentAssignedViewmap = new ParentAssignedViewmapImpl();
		return parentAssignedViewmap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModeledViewmap createModeledViewmap() {
		ModeledViewmapImpl modeledViewmap = new ModeledViewmapImpl();
		return modeledViewmap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueExpression createValueExpression() {
		ValueExpressionImpl valueExpression = new ValueExpressionImpl();
		return valueExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenConstraint createGenConstraint() {
		GenConstraintImpl genConstraint = new GenConstraintImpl();
		return genConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Palette createPalette() {
		PaletteImpl palette = new PaletteImpl();
		return palette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ToolEntry createToolEntry() {
		ToolEntryImpl toolEntry = new ToolEntryImpl();
		return toolEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StandardEntry createStandardEntry() {
		StandardEntryImpl standardEntry = new StandardEntryImpl();
		return standardEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Separator createSeparator() {
		SeparatorImpl separator = new SeparatorImpl();
		return separator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ToolGroup createToolGroup() {
		ToolGroupImpl toolGroup = new ToolGroupImpl();
		return toolGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFeatureSeqInitializer createGenFeatureSeqInitializer() {
		GenFeatureSeqInitializerImpl genFeatureSeqInitializer = new GenFeatureSeqInitializerImpl();
		return genFeatureSeqInitializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFeatureValueSpec createGenFeatureValueSpec() {
		GenFeatureValueSpecImpl genFeatureValueSpec = new GenFeatureValueSpecImpl();
		return genFeatureValueSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenReferenceNewElementSpec createGenReferenceNewElementSpec() {
		GenReferenceNewElementSpecImpl genReferenceNewElementSpec = new GenReferenceNewElementSpecImpl();
		return genReferenceNewElementSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLinkConstraints createGenLinkConstraints() {
		GenLinkConstraintsImpl genLinkConstraints = new GenLinkConstraintsImpl();
		return genLinkConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditRoot createGenAuditRoot() {
		GenAuditRootImpl genAuditRoot = new GenAuditRootImpl();
		return genAuditRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditContainer createGenAuditContainer() {
		GenAuditContainerImpl genAuditContainer = new GenAuditContainerImpl();
		return genAuditContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditRule createGenAuditRule() {
		GenAuditRuleImpl genAuditRule = new GenAuditRuleImpl();
		return genAuditRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDomainElementTarget createGenDomainElementTarget() {
		GenDomainElementTargetImpl genDomainElementTarget = new GenDomainElementTargetImpl();
		return genDomainElementTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagramElementTarget createGenDiagramElementTarget() {
		GenDiagramElementTargetImpl genDiagramElementTarget = new GenDiagramElementTargetImpl();
		return genDiagramElementTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDomainAttributeTarget createGenDomainAttributeTarget() {
		GenDomainAttributeTargetImpl genDomainAttributeTarget = new GenDomainAttributeTargetImpl();
		return genDomainAttributeTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNotationElementTarget createGenNotationElementTarget() {
		GenNotationElementTargetImpl genNotationElementTarget = new GenNotationElementTargetImpl();
		return genNotationElementTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMetricContainer createGenMetricContainer() {
		GenMetricContainerImpl genMetricContainer = new GenMetricContainerImpl();
		return genMetricContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMetricRule createGenMetricRule() {
		GenMetricRuleImpl genMetricRule = new GenMetricRuleImpl();
		return genMetricRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditedMetricTarget createGenAuditedMetricTarget() {
		GenAuditedMetricTargetImpl genAuditedMetricTarget = new GenAuditedMetricTargetImpl();
		return genAuditedMetricTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditContext createGenAuditContext() {
		GenAuditContextImpl genAuditContext = new GenAuditContextImpl();
		return genAuditContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenExpressionProviderContainer createGenExpressionProviderContainer() {
		GenExpressionProviderContainerImpl genExpressionProviderContainer = new GenExpressionProviderContainerImpl();
		return genExpressionProviderContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenJavaExpressionProvider createGenJavaExpressionProvider() {
		GenJavaExpressionProviderImpl genJavaExpressionProvider = new GenJavaExpressionProviderImpl();
		return genJavaExpressionProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenExpressionInterpreter createGenExpressionInterpreter() {
		GenExpressionInterpreterImpl genExpressionInterpreter = new GenExpressionInterpreterImpl();
		return genExpressionInterpreter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLiteralExpressionProvider createGenLiteralExpressionProvider() {
		GenLiteralExpressionProviderImpl genLiteralExpressionProvider = new GenLiteralExpressionProviderImpl();
		return genLiteralExpressionProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNavigator createGenNavigator() {
		GenNavigatorImpl genNavigator = new GenNavigatorImpl();
		return genNavigator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNavigatorChildReference createGenNavigatorChildReference() {
		GenNavigatorChildReferenceImpl genNavigatorChildReference = new GenNavigatorChildReferenceImpl();
		return genNavigatorChildReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNavigatorPath createGenNavigatorPath() {
		GenNavigatorPathImpl genNavigatorPath = new GenNavigatorPathImpl();
		return genNavigatorPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenNavigatorPathSegment createGenNavigatorPathSegment() {
		GenNavigatorPathSegmentImpl genNavigatorPathSegment = new GenNavigatorPathSegmentImpl();
		return genNavigatorPathSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenPropertySheet createGenPropertySheet() {
		GenPropertySheetImpl genPropertySheet = new GenPropertySheetImpl();
		return genPropertySheet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenStandardPropertyTab createGenStandardPropertyTab() {
		GenStandardPropertyTabImpl genStandardPropertyTab = new GenStandardPropertyTabImpl();
		return genStandardPropertyTab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomPropertyTab createGenCustomPropertyTab() {
		GenCustomPropertyTabImpl genCustomPropertyTab = new GenCustomPropertyTabImpl();
		return genCustomPropertyTab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeTabFilter createTypeTabFilter() {
		TypeTabFilterImpl typeTabFilter = new TypeTabFilterImpl();
		return typeTabFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomTabFilter createCustomTabFilter() {
		CustomTabFilterImpl customTabFilter = new CustomTabFilterImpl();
		return customTabFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenSharedContributionItem createGenSharedContributionItem() {
		GenSharedContributionItemImpl genSharedContributionItem = new GenSharedContributionItemImpl();
		return genSharedContributionItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenGroupMarker createGenGroupMarker() {
		GenGroupMarkerImpl genGroupMarker = new GenGroupMarkerImpl();
		return genGroupMarker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenSeparator createGenSeparator() {
		GenSeparatorImpl genSeparator = new GenSeparatorImpl();
		return genSeparator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomAction createGenCustomAction() {
		GenCustomActionImpl genCustomAction = new GenCustomActionImpl();
		return genCustomAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCommandAction createGenCommandAction() {
		GenCommandActionImpl genCommandAction = new GenCommandActionImpl();
		return genCommandAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LoadResourceAction createLoadResourceAction() {
		LoadResourceActionImpl loadResourceAction = new LoadResourceActionImpl();
		return loadResourceAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InitDiagramAction createInitDiagramAction() {
		InitDiagramActionImpl initDiagramAction = new InitDiagramActionImpl();
		return initDiagramAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreateShortcutAction createCreateShortcutAction() {
		CreateShortcutActionImpl createShortcutAction = new CreateShortcutActionImpl();
		return createShortcutAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenActionFactoryContributionItem createGenActionFactoryContributionItem() {
		GenActionFactoryContributionItemImpl genActionFactoryContributionItem = new GenActionFactoryContributionItemImpl();
		return genActionFactoryContributionItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMenuManager createGenMenuManager() {
		GenMenuManagerImpl genMenuManager = new GenMenuManagerImpl();
		return genMenuManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenToolBarManager createGenToolBarManager() {
		GenToolBarManagerImpl genToolBarManager = new GenToolBarManagerImpl();
		return genToolBarManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenContextMenu createGenContextMenu() {
		GenContextMenuImpl genContextMenu = new GenContextMenuImpl();
		return genContextMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenApplication createGenApplication() {
		GenApplicationImpl genApplication = new GenApplicationImpl();
		return genApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenParsers createGenParsers() {
		GenParsersImpl genParsers = new GenParsersImpl();
		return genParsers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PredefinedParser createPredefinedParser() {
		PredefinedParserImpl predefinedParser = new PredefinedParserImpl();
		return predefinedParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PredefinedEnumParser createPredefinedEnumParser() {
		PredefinedEnumParserImpl predefinedEnumParser = new PredefinedEnumParserImpl();
		return predefinedEnumParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OclChoiceParser createOclChoiceParser() {
		OclChoiceParserImpl oclChoiceParser = new OclChoiceParserImpl();
		return oclChoiceParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExpressionLabelParser createExpressionLabelParser() {
		ExpressionLabelParserImpl expressionLabelParser = new ExpressionLabelParserImpl();
		return expressionLabelParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomParser createCustomParser() {
		CustomParserImpl customParser = new CustomParserImpl();
		return customParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternalParser createExternalParser() {
		ExternalParserImpl externalParser = new ExternalParserImpl();
		return externalParser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenVisualEffect createGenVisualEffect() {
		GenVisualEffectImpl genVisualEffect = new GenVisualEffectImpl();
		return genVisualEffect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomGeneratorExtension createGenCustomGeneratorExtension() {
		GenCustomGeneratorExtensionImpl genCustomGeneratorExtension = new GenCustomGeneratorExtensionImpl();
		return genCustomGeneratorExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenTemplateInvocation createGenTemplateInvocation() {
		GenTemplateInvocationImpl genTemplateInvocation = new GenTemplateInvocationImpl();
		return genTemplateInvocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFixedInputsTemplateInvocation createGenFixedInputsTemplateInvocation() {
		GenFixedInputsTemplateInvocationImpl genFixedInputsTemplateInvocation = new GenFixedInputsTemplateInvocationImpl();
		return genFixedInputsTemplateInvocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFloatingLabel createGenFloatingLabel() {
		GenFloatingLabelImpl genFloatingLabel = new GenFloatingLabelImpl();
		return genFloatingLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RefreshHook createRefreshHook() {
		RefreshHookImpl refreshHook = new RefreshHookImpl();
		return refreshHook;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardPreferencePages createStandardPreferencePagesFromString(EDataType eDataType, String initialValue) {
		StandardPreferencePages result = StandardPreferencePages.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStandardPreferencePagesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulerUnits createRulerUnitsFromString(EDataType eDataType, String initialValue) {
		RulerUnits result = RulerUnits.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRulerUnitsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Routing createRoutingFromString(EDataType eDataType, String initialValue) {
		Routing result = Routing.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoutingToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineStyle createLineStyleFromString(EDataType eDataType, String initialValue) {
		LineStyle result = LineStyle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLineStyleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JFaceFont createJFaceFontFromString(EDataType eDataType, String initialValue) {
		JFaceFont result = JFaceFont.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJFaceFontToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontStyle createFontStyleFromString(EDataType eDataType, String initialValue) {
		FontStyle result = FontStyle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFontStyleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagramColors createDiagramColorsFromString(EDataType eDataType, String initialValue) {
		DiagramColors result = DiagramColors.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiagramColorsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderPriority createProviderPriorityFromString(EDataType eDataType, String initialValue) {
		ProviderPriority result = ProviderPriority.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProviderPriorityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkLabelAlignment createLinkLabelAlignmentFromString(EDataType eDataType, String initialValue) {
		LinkLabelAlignment result = LinkLabelAlignment.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLinkLabelAlignmentToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LabelTextAccessMethod createLabelTextAccessMethodFromString(EDataType eDataType, String initialValue) {
		LabelTextAccessMethod result = LabelTextAccessMethod.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLabelTextAccessMethodToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewmapLayoutType createViewmapLayoutTypeFromString(EDataType eDataType, String initialValue) {
		ViewmapLayoutType result = ViewmapLayoutType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertViewmapLayoutTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardEntryKind createStandardEntryKindFromString(EDataType eDataType, String initialValue) {
		StandardEntryKind result = StandardEntryKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStandardEntryKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenSeverity createGenSeverityFromString(EDataType eDataType, String initialValue) {
		GenSeverity result = GenSeverity.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenSeverityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenLanguage createGenLanguageFromString(EDataType eDataType, String initialValue) {
		GenLanguage result = GenLanguage.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenLanguageToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenNavigatorReferenceType createGenNavigatorReferenceTypeFromString(EDataType eDataType, String initialValue) {
		GenNavigatorReferenceType result = GenNavigatorReferenceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenNavigatorReferenceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeneratedType createGeneratedTypeFromString(EDataType eDataType, String initialValue) {
		GeneratedType result = GeneratedType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGeneratedTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GMFGenPackage getGMFGenPackage() {
		return (GMFGenPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GMFGenPackage getPackage() {
		return GMFGenPackage.eINSTANCE;
	}

} //GMFGenFactoryImpl
