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
package org.eclipse.papyrus.sirius.junit.util.diagram;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Abstract Test for target reconnection of edge.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public abstract class AbstractReconnectTargetEdgeTests extends AbstractSiriusDiagramTests {

	/**
	 * the root of the model
	 */
	protected Package root;

	/**
	 * The semantic source of the edge.
	 */
	private EObject semanticSource;

	/**
	 * The semantic target of the edge.
	 */
	private EObject semanticTarget;

	/**
	 * The new semantic target of the edge.
	 */
	private EObject semanticNewTarget;

	/**
	 * The graphical source of the edge.
	 */
	private EObject edgeSource;

	/**
	 * The graphical target of the edge.
	 */
	private EObject edgeTarget;

	/**
	 * The new graphical target of the edge.
	 */
	private EObject edgeNewTarget;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.root = this.fixture.getModel();
	}

	/**
	 * This method reconnect the target of edge and checks that the diagram is unsynchronized
	 * 
	 * @param reconnectionTargetToolId
	 *            the ID of the reconnection tool to used
	 * @param edge
	 *            the edge to reconnect
	 */
	protected void reconnectTargetEdge(final String reconnectionTargetToolId, final DEdge edge) {
		reconnectTargetEdge(reconnectionTargetToolId, edge, false);
	}

	/**
	 * This method reconnect the target of the edge and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param reconnectionTargetToolId
	 *            the ID of the reconnection tool to used
	 * @param edge
	 *            the edge to reconnect
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized, <code>false</code> otherwise.
	 */
	protected void reconnectTargetEdge(final String reconnectionTargetToolId, final DEdge edge, final boolean isSynchronized) {
		checkSiriusDiagramSynchronization(isSynchronized);

		Diagram diagram = getDiagram();
		int initalNbEdges = diagram.getEdges().size();
		EClass initialEdgeType = edge.getTarget().eClass();
		boolean result = fixture.applyEdgeReconnectionTool(reconnectionTargetToolId, getDDiagram(), edge, (EdgeTarget) getEdgeTarget(), (EdgeTarget) getEdgeNewTarget());
		Assert.assertTrue("The reconnexion of edge failed", result); //$NON-NLS-1$
		fixture.flushDisplayEvents();

		Assert.assertEquals("Number of edge on diagram should be different after reconnexion.", initalNbEdges, diagram.getEdges().size()); //$NON-NLS-1$
		Assert.assertEquals("New edge target does not match with reconnection target.", (EdgeTarget) getEdgeNewTarget(), edge.getTargetNode()); //$NON-NLS-1$

		final List<EObject> semanticElements = edge.getSemanticElements();
		Assert.assertEquals("The reconnected edge representation must have 1 associated semantic element", 1, semanticElements.size()); //$NON-NLS-1$
		final EObject element = semanticElements.get(0);
		Assert.assertEquals("The type of the reconnected edge should not change.", initialEdgeType, element.eClass()); //$NON-NLS-1$
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		Assert.assertEquals("The semantic source should not change.", this.getSemanticSource(), domainBasedEdgeServices.getSource(element)); //$NON-NLS-1$
		Assert.assertEquals("The semantic target does not match with reconnection semantic target.", this.getSemanticNewTarget(), domainBasedEdgeServices.getTargets(element).get(0)); //$NON-NLS-1$

		// undo
		fixture.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();
		Assert.assertEquals("The semantic source should not change.", this.getSemanticSource(), domainBasedEdgeServices.getSource(element)); //$NON-NLS-1$
		Assert.assertEquals("The semantic target should be reseted.", this.getSemanticTarget(), domainBasedEdgeServices.getTargets(element).get(0)); //$NON-NLS-1$

		// redo
		fixture.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();
		Assert.assertEquals("The semantic source should not change.", this.getSemanticSource(), domainBasedEdgeServices.getSource(element)); //$NON-NLS-1$
		Assert.assertEquals("The semantic target does not match with reconnection semantic target.", this.getSemanticNewTarget(), domainBasedEdgeServices.getTargets(element).get(0)); //$NON-NLS-1$

	}

	/**
	 * This method clean the test environment
	 */
	@After
	public void tearDown() {
		this.root = null;
	}

	/**
	 * Get the semantic source of the edge.
	 * 
	 * @return the semanticSource
	 */
	public EObject getSemanticSource() {
		return semanticSource;
	}

	/**
	 * Set the semantic source of the edge.
	 * 
	 * @param semanticSource
	 *            the semanticSource to set
	 */
	public void setSemanticSource(EObject semanticSource) {
		this.semanticSource = semanticSource;
	}

	/**
	 * Get the semantic target of the edge.
	 * 
	 * @return the semanticTarget
	 */
	public EObject getSemanticTarget() {
		return semanticTarget;
	}

	/**
	 * Set the semantic target of the edge.
	 * 
	 * @param semanticTarget
	 *            the semanticTarget to set
	 */
	public void setSemanticTarget(EObject semanticTarget) {
		this.semanticTarget = semanticTarget;
	}

	/**
	 * Get the new semantic target of the edge.
	 * 
	 * @return the semanticTarget
	 */
	public EObject getSemanticNewTarget() {
		return semanticNewTarget;
	}

	/**
	 * Set the new semantic target of the edge.
	 * 
	 * @param semanticTarget
	 *            the semanticTarget to set
	 */
	public void setSemanticNewTarget(EObject semanticNewTarget) {
		this.semanticNewTarget = semanticNewTarget;
	}

	/**
	 * Get the graphical edge source.
	 * 
	 * @return the graphical edge source.
	 */
	public EObject getEdgeSource() {
		return edgeSource;
	}

	/**
	 * Set the graphical edge source.
	 * 
	 * @param edgeSource
	 *            the graphical edge source to set
	 */
	public void setEdgeSource(EObject edgeSource) {
		this.edgeSource = edgeSource;
	}

	/**
	 * Get the graphical edge target.
	 * 
	 * @return the graphical edge target
	 */
	public EObject getEdgeTarget() {
		return edgeTarget;
	}

	/**
	 * Set the graphical edge target.
	 * 
	 * @param edgeTarget
	 *            the graphical edge target to set
	 */
	public void setEdgeTarget(EObject edgeTarget) {
		this.edgeTarget = edgeTarget;
	}

	/**
	 * Get the new graphical edge target.
	 * 
	 * @return the graphical edge target
	 */
	public EObject getEdgeNewTarget() {
		return edgeNewTarget;
	}

	/**
	 * Set the new graphical edge target.
	 * 
	 * @param edgeTarget
	 *            the graphical edge target to set
	 */
	public void setEdgeNewTarget(EObject edgeNewTarget) {
		this.edgeNewTarget = edgeNewTarget;
	}
}
