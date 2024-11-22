/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.drop.graphical;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.ContainmentFeatureHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionOperand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the graphical drop in a {@link CombinedFragment}.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoCombinedFragmentTest extends AbstractGraphicalDropNodeTests {

	private static final String DIAGRAM = "Diagram"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropSequenceDiagramSirius"; //$NON-NLS-1$

	/**
	 * The separator used to identify elements inside other elements (e.g. {@code Node_InNode}).
	 */
	private static final String IN = "_In"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String elementToDropMappingType;

	private final String graphicalToolId;

	private final EReference newContainmentFeature;

	/**
	 * Initializes the test with the provided parameters.
	 *
	 * @param elementToDropType
	 *            the type of the element to drop
	 */
	public GraphicalDropIntoCombinedFragmentTest(Class<? extends Element> elementToDropType) {
		this.elementToDropName = getElementNameInDiagram(elementToDropType);
		this.graphicalToolId = GraphicalDropToolsIds.getGraphicalDropToolsId(elementToDropType);
		this.elementToDropMappingType = MappingTypes.getMappingType(elementToDropType);
		this.newContainmentFeature = ContainmentFeatureHelper.getContainmentFeature(elementToDropType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropElementIntoCombinedFragment() {
		String graphicalOwnerName = getElementNameInDiagram(CombinedFragment.class);
		String newSemanticOwnerName = getElementNameInCombinedFragment(InteractionOperand.class);
		dropElementIntoContainer(this.elementToDropName, graphicalOwnerName, MappingTypes.getMappingType(CombinedFragment.class), MappingTypes.getMappingType(InteractionOperand.class),
				this.graphicalToolId, this.elementToDropMappingType, newSemanticOwnerName, this.newContainmentFeature.getName());
	}

	private static String getElementNameInDiagram(Class<? extends Element> elementToDropType) {
		return elementToDropType.getSimpleName() + IN + DIAGRAM;
	}

	private static String getElementNameInCombinedFragment(Class<? extends Element> elementToDropType) {
		return elementToDropType.getSimpleName() + IN + CombinedFragment.class.getSimpleName();
	}

	@Parameters(name = "{index} drop graphical element {0}")
	public static List<Class<? extends Element>> data() {
		return List.of(Constraint.class, Comment.class);
	}
}
