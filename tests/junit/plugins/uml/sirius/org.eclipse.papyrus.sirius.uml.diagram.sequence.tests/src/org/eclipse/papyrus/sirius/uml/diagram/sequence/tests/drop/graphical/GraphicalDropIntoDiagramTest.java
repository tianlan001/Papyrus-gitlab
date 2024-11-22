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

import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.ContainmentFeatureHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionOperand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to graphical drop in the Sequence diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(Parameterized.class)
public class GraphicalDropIntoDiagramTest extends AbstractGraphicalDropNodeTests {

	private static final String DIAGRAM_NAME = "GraphicalDropSequenceDiagramSirius"; //$NON-NLS-1$

	/**
	 * The separator used to identify elements inside other elements (e.g. {@code Node_InNode}).
	 */
	private static final String IN = "_In"; //$NON-NLS-1$

	private final DropInto usecase;

	/**
	 * Constructor.
	 */
	public GraphicalDropIntoDiagramTest(DropInto data) {
		this.usecase = data;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropElementIntoDiagram() {
		dropElementToDiagram(usecase.getElementName(), usecase.getTool(), usecase.getDroppedMapping(), usecase.getNewContainment());
	}

	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<DropInto> data() {
		return List.of(
				new DropInto(Constraint.class, InteractionOperand.class),
				new DropInto(Comment.class, InteractionOperand.class));
	}

	/**
	 * This record groups information on the type of the dropped element and the type of its initial container.
	 * 
	 * @param droppedType
	 *            the type of the element to drop
	 * @param initialContainerType
	 *            the type of the container initially containing the element to drop
	 */
	private record DropInto(Class<? extends Element> droppedType, Class<? extends Element> initialContainerType) {
		String getElementName() {
			return droppedType.getSimpleName() + IN + initialContainerType.getSimpleName();
		}

		String getTool() {
			return GraphicalDropToolsIds.getGraphicalDropToolsId(droppedType);
		}

		String getDroppedMapping() {
			return MappingTypes.getMappingType(droppedType);
		}

		String getNewContainment() {
			return ContainmentFeatureHelper.getContainmentFeature(droppedType).getName();
		}

		@Override
		public final String toString() {
			return String.format("%s from %s to Diagram", droppedType.getSimpleName(), initialContainerType.getSimpleName()); //$NON-NLS-1$
		}
	}
}
