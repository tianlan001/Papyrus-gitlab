/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticEdgeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of Profile Diagram edges.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.profile.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeCreationTest extends AbstractCreateEdgeTests {

	private static final Collection<Object[]> data = new ArrayList<>();


	// Activity represents an imported metaClass "Activity"
	private static final String METACLASS_ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String CLASS = "Class"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration"; //$NON-NLS-1$

	private static final String PROFILE = "Profile"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE = "PrimitiveType"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "Edge_ProfileDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(CLASS, MappingTypes.CLASS_NODE_TYPE, CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE, DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, ENUMERATION,
			MappingTypes.ENUMERATION_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, PRIMITIVETYPE, MappingTypes.PRIMITIVETYPE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, METACLASS_ACTIVITY,
			MappingTypes.METACLASS_NODE_TYPE);

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;

	/**
	 * Default constructor.
	 *
	 */
	public EdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createAssociationTest() {
		if (!isMetaClass(this.sourceName) && !isMetaClass(this.targetName)) {
			List<Class<?>> forbiddenTypes = List.of(Package.class, Constraint.class, Profile.class);
			NamedElement source = this.root.getMember(this.sourceName);
			NamedElement target = this.root.getMember(this.targetName);
			if (!isInstanceOf(forbiddenTypes, source) && !isInstanceOf(forbiddenTypes, target)) {
				testEdgeCreation(CreationToolsIds.CREATE__ASSOCIATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Association.class,
						this.root, MappingTypes.ASSOCIATION_EDGE_TYPE);
			}
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createGeneralizationTest() {
		if (!isMetaClass(this.sourceName)) {
			List<Class<?>> forbiddenTypes = List.of(Package.class, Constraint.class, Profile.class);
			NamedElement target;
			if (!isMetaClass(this.targetName)) {
				target = this.root.getMember(this.targetName);
			} else {
				target = null;
			}
			NamedElement source = this.root.getMember(this.sourceName);
			if (!isInstanceOf(forbiddenTypes, source) && !isInstanceOf(forbiddenTypes, target)) {
				testEdgeCreation(CreationToolsIds.CREATE__GENERALIZATION__TOOL, UMLPackage.eINSTANCE.getClassifier_Generalization(), Generalization.class,
						source, MappingTypes.GENERALIZATION_EDGE_TYPE);
			}
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createExtensionTest() {
		List<String> authorizedTarget = List.of(CLASS, STEREOTYPE, METACLASS_ACTIVITY);
		if (STEREOTYPE.equals(this.sourceName) && authorizedTarget.contains(this.targetName)) {
			NamedElement source = this.root.getMember(this.sourceName);
			testEdgeCreation(CreationToolsIds.CREATE__EXTENSION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Extension.class,
					source, MappingTypes.EXTENSION_EDGE_TYPE);
		}
	}

	private boolean isInstanceOf(List<Class<?>> forbiddenTypes, NamedElement element) {
		return forbiddenTypes.stream().filter(type -> type.isInstance(element)).findFirst().isPresent();
	}

	private boolean isMetaClass(String elementName) {
		return METACLASS_ACTIVITY.equals(elementName);
	}

	private void testEdgeCreation(String toolId, EReference containmentFeature, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		this.setSemanticSource(this.root.getMember(this.sourceName));
		this.setSemanticTarget(this.root.getMember(this.targetName));
		this.setEdgeSource(getNodeFromDiagram(this.sourceName, this.mappingSourceTypeName)); // $NON-NLS-1$
		this.setEdgeTarget(getNodeFromDiagram(this.targetName, this.mappingTargetTypeName)); // $NON-NLS-1$
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(getDiagram(), getDDiagram(), expectedEdgeMappingType);
		createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker));
	}

	@Parameters(name = "{index} create edge between {0} and {1}")
	public static Collection<Object[]> data() {
		if (data.isEmpty()) {
			for (String sourceName : elementTypeToMapping.keySet()) {
				for (String targetName : elementTypeToMapping.keySet()) {
					Object[] array = { sourceName + getDigit(sourceName, true), targetName + getDigit(targetName, false), elementTypeToMapping.get(sourceName), elementTypeToMapping.get(targetName) };
					data.add(array);
				}
			}
		}
		return data;
	}

	private static String getDigit(String name, boolean isSource) {
		String digit = ""; //$NON-NLS-1$
		if (!METACLASS_ACTIVITY.equals(name)) {
			if (isSource) {
				digit = "1"; //$NON-NLS-1$
			} else {
				digit = "2"; //$NON-NLS-1$
			}
		}
		return digit;
	}

}
