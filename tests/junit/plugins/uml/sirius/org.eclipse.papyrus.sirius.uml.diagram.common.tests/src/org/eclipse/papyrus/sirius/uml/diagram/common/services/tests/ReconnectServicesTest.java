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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.ReconnectServices;
import org.eclipse.papyrus.uml.domain.services.IViewQuerier;
import org.eclipse.papyrus.uml.domain.services.edges.IDomainBasedEdgeContainerProvider;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Dependency;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link ReconnectServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class ReconnectServicesTest extends AbstractServicesTest {

	/**
	 * Service used to reconnect edges.
	 */
	private ReconnectServices reconnectServices;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.reconnectServices = new ReconnectServices();
	}

	/**
	 * Test that a {@link Dependency} can be reconnected to a {@link Class} as source.
	 */
	@Test
	public void testCanReconnectSource() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Class class3 = create(Class.class);
		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);

		boolean canReconnectSource = reconnectServices.canReconnectSource(dependency, dEdge, class1, class3, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return semanticSource;
			}
		});
		assertTrue(canReconnectSource);
	}

	/**
	 * Test that a {@link Dependency} cannot be reconnected to a {@link Class} as source if no container found.
	 */
	@Test
	public void testCannotReconnectSourceIfNoContainerFound() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Class class3 = create(Class.class);

		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);

		boolean canReconnectSource = reconnectServices.canReconnectSource(dependency, dEdge, class1, class3, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return null;
			}
		});
		assertFalse(canReconnectSource);
	}

	/**
	 * Test that a {@link Dependency} cannot be reconnected to a {@link Comment} as source.
	 */
	@Test
	public void testCannotReconnectSource() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Comment comment = create(Comment.class);

		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);


		boolean canReconnectSource = reconnectServices.canReconnectSource(dependency, dEdge, class1, comment, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return semanticSource;
			}
		});
		assertFalse(canReconnectSource);
	}


	/**
	 * Test that a {@link Dependency} can be reconnected to a {@link Class} as target if no container found.
	 */
	@Test
	public void testCannotReconnectTargetIfNoContainerFound() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Class class3 = create(Class.class);

		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);

		boolean canReconnectTarget = reconnectServices.canReconnectTarget(dependency, dEdge, class2, class3, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return null;
			}
		});
		assertFalse(canReconnectTarget);
	}

	/**
	 * Test that a {@link Dependency} can be reconnected to a {@link Class} as target.
	 */
	@Test
	public void testCanReconnectTarget() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Class class3 = create(Class.class);

		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);

		boolean canReconnectTarget = reconnectServices.canReconnectTarget(dependency, dEdge, class2, class3, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return semanticSource;
			}
		});
		assertTrue(canReconnectTarget);
	}

	/**
	 * Test that a {@link Dependency} cannot be reconnected to a {@link Comment} as target.
	 */
	@Test
	public void testCannotReconnectTarget() {
		Class class1 = create(Class.class);
		Class class2 = create(Class.class);
		Dependency dependency = create(Dependency.class);
		dependency.getClients().add(class1);
		dependency.getSuppliers().add(class2);
		Comment comment = create(Comment.class);

		DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
		dEdge.setTarget(dependency);
		DNodeContainer class1Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class1Container.setTarget(class1);
		DNodeContainer class2Container = DiagramFactory.eINSTANCE.createDNodeContainer();
		class2Container.setTarget(class2);
		dEdge.setSourceNode(class1Container);
		dEdge.setTargetNode(class2Container);

		boolean canReconnectTarget = reconnectServices.canReconnectTarget(dependency, dEdge, class2, comment, null, new IDomainBasedEdgeContainerProvider() {

			@Override
			public EObject getContainer(EObject semanticSource, EObject semanticTarget, EObject semanticEdge, IViewQuerier querier, Object sourceNode, Object targetNode) {
				return semanticSource;
			}
		});
		assertFalse(canReconnectTarget);
	}


}
