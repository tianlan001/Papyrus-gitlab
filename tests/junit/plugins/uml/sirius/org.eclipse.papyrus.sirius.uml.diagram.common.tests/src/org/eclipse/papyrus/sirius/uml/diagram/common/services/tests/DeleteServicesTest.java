/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link DeleteServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DeleteServicesTest extends AbstractServicesTest {

	/**
	 * Path of simple model containing a {@link Model} with a {@link Class}.
	 */
	private static final String SIMPLE_MODEL_PATH = "resources/SimpleModel.uml"; //$NON-NLS-1$

	/**
	 * Service used to delete elements.
	 */
	private DeleteServices deleteServices;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.deleteServices = new DeleteServices();
	}

	/**
	 * Test that a {@link Class} from not read only resource is removed.
	 */
	@Test
	public void testDeleteClassFromResourceSet() {
		Model model = (Model) getRootFromUmlModel(SIMPLE_MODEL_PATH, false);
		Class c1 = (Class) model.getPackagedElements().get(0);

		boolean isRemoved = this.deleteServices.delete(c1);
		assertTrue(isRemoved);
		assertTrue(model.getPackagedElements().isEmpty());
	}

	/**
	 * Test that a {@link Class} without resource is removed.
	 */
	@Test
	public void testDeleteClassWithoutResourceSet() {
		Class c1 = create(Class.class);
		boolean isRemoved = this.deleteServices.delete(c1);
		assertTrue(isRemoved);
	}

	/**
	 * Test that a {@link Class} from read only resource is not removed.
	 */
	@Test
	public void testDeleteClassReadOnly() throws IOException {
		Model model = (Model) getRootFromUmlModel(SIMPLE_MODEL_PATH, true);
		Class c1 = (Class) model.getPackagedElements().get(0);

		// check deletion has not be completed
		boolean isRemoved = this.deleteServices.delete(c1);
		assertFalse(isRemoved);
	}

	@Test
	public void testDeleteLinkFromConstraint() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Constraint constraint = create(Constraint.class);
		constraint.getConstrainedElements().addAll(List.of(class1, class2));
		assertTrue(constraint.getConstrainedElements().containsAll(List.of(class1, class2)));
		this.deleteServices.deleteLink(constraint, createDEdgeWithGivenSemanticTarget(class2));
		assertEquals(1, constraint.getConstrainedElements().size());
		assertTrue(constraint.getConstrainedElements().contains(class1));
	}

	@Test
	public void testDeleteLinkFromComment() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Comment comment = create(Comment.class);
		comment.getAnnotatedElements().addAll(List.of(class1, class2));
		assertTrue(comment.getAnnotatedElements().containsAll(List.of(class1, class2)));
		this.deleteServices.deleteLink(comment, createDEdgeWithGivenSemanticTarget(class2));
		assertEquals(1, comment.getAnnotatedElements().size());
		assertTrue(comment.getAnnotatedElements().contains(class1));
	}

	@Test
	public void testDeleteLinkFromInvalid() {
		Class class2 = create(Class.class);
		Actor actor = create(Actor.class);
		this.deleteServices.deleteLink(actor, createDEdgeWithGivenSemanticTarget(class2));
	}

	@Test
	public void testDeleteLinkFromNull() {
		Class class2 = create(Class.class);
		this.deleteServices.deleteLink(null, createDEdgeWithGivenSemanticTarget(class2));
	}

	@Test
	public void testDeleteLinkToInvalidTarget() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Comment comment = create(Comment.class);
		comment.getAnnotatedElements().addAll(List.of(class1, class2));
		assertTrue(comment.getAnnotatedElements().containsAll(List.of(class1, class2)));
		this.deleteServices.deleteLink(comment, createDEdgeWithGivenSemanticTarget(null));
		assertEquals(2, comment.getAnnotatedElements().size());
		assertTrue(comment.getAnnotatedElements().containsAll(List.of(class1, class2)));
	}

	private DEdge createDEdgeWithGivenSemanticTarget(Element target) {
		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		DNode dNode = DiagramFactory.eINSTANCE.createDNode();
		dNode.setTarget(target);
		dEdge.setTargetNode(dNode);
		return dEdge;
	}

}
