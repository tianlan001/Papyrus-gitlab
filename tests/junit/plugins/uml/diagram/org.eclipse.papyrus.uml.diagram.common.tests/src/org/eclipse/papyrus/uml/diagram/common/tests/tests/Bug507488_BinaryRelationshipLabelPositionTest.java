/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 507488
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 518125
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test the label position for uml binary relationship.
 */
@PluginResource("/resources/507488/model.di")
public class Bug507488_BinaryRelationshipLabelPositionTest extends AbstractPapyrusTest {

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	/** The class diagram. */
	private Diagram classDiagram = null;

	/**
	 * Initialize tests.
	 */
	@Before
	public void init() {

		classDiagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "Class Diagram"); //$NON-NLS-1$
		assertNotNull(classDiagram);
	}

	/** Constants. */
	private static int DEFAULT_X_ERROR_MARGIN = 50;
	private static int DEFAULT_Y_ERROR_MARGIN = 50;

	/**
	 * Test the position of an association's name, role and multiplicity.
	 */
	@Test
	public void testAssociationRoleMultiplicityPosition() {

		final IGraphicalEditPart class1 = getEditPart(classDiagram, "Class1"); //$NON-NLS-1$
		assertNotNull(class1);

		final IGraphicalEditPart class2 = getEditPart(classDiagram, "Class2"); //$NON-NLS-1$
		assertNotNull(class2);

		final IGraphicalEditPart ass1 = getEditPart(classDiagram, "Association1"); //$NON-NLS-1$
		assertNotNull(ass1);
		final Rectangle ass1Bounds = ass1.getFigure().getBounds();

		final IGraphicalEditPart ass1Name = ass1.getChildBySemanticHint("Association_NameLabel"); //$NON-NLS-1$
		assertNotNull(ass1Name);
		final Rectangle ass1NameBounds = ass1Name.getFigure().getBounds();

		// Name label is centered with respect to the association
		assertEquals((ass1NameBounds.x + ass1NameBounds.width / 2), (ass1Bounds.x + ass1Bounds.width / 2));

		final IGraphicalEditPart ass1SourceRole = ass1.getChildBySemanticHint("Association_SourceRoleLabel"); //$NON-NLS-1$
		assertNotNull(ass1SourceRole);
		final Rectangle ass1SourceRoleBounds = ass1SourceRole.getFigure().getBounds();
		assertBounds(ass1SourceRoleBounds, 353, 193);

		final IGraphicalEditPart ass1TargetRole = ass1.getChildBySemanticHint("Association_TargetRoleLabel"); //$NON-NLS-1$
		assertNotNull(ass1TargetRole);
		final Rectangle ass1TargetRoleBounds = ass1TargetRole.getFigure().getBounds();
		assertBounds(ass1TargetRoleBounds, 241, 153);

		final IGraphicalEditPart ass1SourceMult = ass1.getChildBySemanticHint("Association_SourceMultiplicityLabel"); //$NON-NLS-1$
		assertNotNull(ass1SourceMult);
		final Rectangle ass1SourceMultBounds = ass1SourceMult.getFigure().getBounds();
		assertBounds(ass1SourceMultBounds, 259, 193);

		final IGraphicalEditPart ass1TargetMult = ass1.getChildBySemanticHint("Association_TargetMultiplicityLabel"); //$NON-NLS-1$
		assertNotNull(ass1TargetMult);
		final Rectangle ass1TargetMultBounds = ass1TargetMult.getFigure().getBounds();
		assertBounds(ass1TargetMultBounds, 371, 153);

		// Source role and target multiplicity are centered with each other
		assertEquals((ass1SourceRoleBounds.x + ass1SourceRoleBounds.width / 2), (ass1TargetMultBounds.x + ass1TargetMultBounds.width / 2));
		// And they are vertically symetric with respect to the association
		assertEquals(((ass1SourceRoleBounds.y + ass1SourceRoleBounds.height / 2) + (ass1TargetMultBounds.y + ass1TargetMultBounds.height / 2)) / 2, (ass1Bounds.y + ass1Bounds.height / 2));

		// Target role and source multiplicity are centerned with each other
		assertEquals((ass1TargetRoleBounds.x + ass1TargetRoleBounds.width / 2), (ass1SourceMultBounds.x + ass1SourceMultBounds.width / 2));
		// And they are vertically symetric with respect to the association
		assertEquals(((ass1TargetRoleBounds.y + ass1TargetRoleBounds.height / 2) + (ass1SourceMultBounds.y + ass1SourceMultBounds.height / 2)) / 2, (ass1Bounds.y + ass1Bounds.height / 2));
	}

	/**
	 * Test the position of a dependency's name.
	 */
	@Test
	public void testDependencyNamePosition() {

		final IGraphicalEditPart class3 = getEditPart(classDiagram, "Class3"); //$NON-NLS-1$
		assertNotNull(class3);

		final IGraphicalEditPart class4 = getEditPart(classDiagram, "Class4"); //$NON-NLS-1$
		assertNotNull(class4);

		final IGraphicalEditPart dependency1 = getEditPart(classDiagram, "Dependency1"); //$NON-NLS-1$
		assertNotNull(dependency1);
		final Rectangle dependency1Bounds = dependency1.getFigure().getBounds();

		final IGraphicalEditPart dependency1Name = dependency1.getChildBySemanticHint("Dependency_NameLabel"); //$NON-NLS-1$
		assertNotNull(dependency1Name);
		final Rectangle dependency1NameBounds = dependency1Name.getFigure().getBounds();

		// Name label is centered with respect to the dependency
		assertEquals((dependency1NameBounds.x + dependency1NameBounds.width / 2), (dependency1Bounds.x + dependency1Bounds.width / 2));
	}

	/**
	 * Test the position of a context link's name.
	 */
	@Test
	public void testContextLinkNamePosition() {

		final IGraphicalEditPart class5 = getEditPart(classDiagram, "Class5"); //$NON-NLS-1$
		assertNotNull(class5);

		final IGraphicalEditPart constraint1 = getEditPart(classDiagram, "Constraint1"); //$NON-NLS-1$
		assertNotNull(constraint1);


		final IGraphicalEditPart contextLink = (IGraphicalEditPart) constraint1.getSourceConnections().get(0);
		assertNotNull(contextLink);
		final Rectangle contextLinkBounds = contextLink.getFigure().getBounds();

		final IGraphicalEditPart contextLabel = contextLink.getChildBySemanticHint("Constraint_KeywordLabel"); //$NON-NLS-1$
		assertNotNull(contextLabel);
		final Rectangle contextLabelBounds = contextLabel.getFigure().getBounds();

		// Keyword "context" label is centered with respect to the context link
		assertEquals((contextLabelBounds.x + contextLabelBounds.width / 2), (contextLinkBounds.x + contextLinkBounds.width / 2));
	}

	/**
	 * Test the position of an interface realization's name.
	 */
	@Test
	public void testIntRealizationNamePosition() {

		final IGraphicalEditPart class6 = getEditPart(classDiagram, "Class6"); //$NON-NLS-1$
		assertNotNull(class6);

		final IGraphicalEditPart interface1 = getEditPart(classDiagram, "Interface1"); //$NON-NLS-1$
		assertNotNull(interface1);

		final IGraphicalEditPart intRealization = getEditPart(classDiagram, "IntRealization"); //$NON-NLS-1$
		assertNotNull(intRealization);
		final Rectangle intRealizationBounds = intRealization.getFigure().getBounds();

		final IGraphicalEditPart intRealizationName = intRealization.getChildBySemanticHint("InterfaceRealization_NameLabel"); //$NON-NLS-1$
		assertNotNull(intRealizationName);
		final Rectangle intRealizationNameBounds = intRealizationName.getFigure().getBounds();

		// Name label is centered with respect to the interface realization
		assertEquals((intRealizationNameBounds.x + intRealizationNameBounds.width / 2), (intRealizationBounds.x + intRealizationBounds.width / 2));
	}

	/**
	 * Get the edit part.
	 *
	 * @param diagram
	 *            The diagram where to look for
	 *
	 * @param semanticElement
	 *            The semantic element
	 * @return the edit part
	 */
	private IGraphicalEditPart getEditPart(final Diagram diagram, final String semanticElement) {

		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}

	/**
	 * Compare bounds with expected x and y.
	 * 
	 * @param bounds
	 *            The bounds to check.
	 * @param expectedX
	 *            The expected X.
	 * @param expectedY
	 *            The expected Y.
	 */
	protected static void assertBounds(final Rectangle bounds, final int expectedX, final int expectedY) {
		if (bounds.x != -1) {
			assertTrue("The element should have a horizontal position at about " + bounds.x + " pixels", Math.abs(expectedX - bounds.x) <= DEFAULT_X_ERROR_MARGIN); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (bounds.y != -1) {
			assertTrue("The element should have a vertical position at about " + bounds.y + " pixels", Math.abs(expectedY - bounds.y) <= DEFAULT_Y_ERROR_MARGIN); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
