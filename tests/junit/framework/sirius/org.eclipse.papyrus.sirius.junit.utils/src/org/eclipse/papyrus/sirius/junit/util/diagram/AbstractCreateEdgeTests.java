/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST.
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
package org.eclipse.papyrus.sirius.junit.util.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.rules.SiriusDiagramEditorFixture;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Abstract Test for Edge Creation
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public abstract class AbstractCreateEdgeTests extends AbstractSiriusDiagramTests {

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
	 * The graphical source of the edge.
	 */
	private EObject edgeSource;

	/**
	 * The graphical target of the edge.
	 */
	private EObject edgeTarget;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.root = this.fixture.getModel();
	}

	/**
	 * This method creates the edge and checks that the diagram is unsynchronized
	 * 
	 * @param creationToolId
	 *            the ID of the creation tool to used
	 * @param checker
	 *            the checker used to validate the creation of the edge
	 * @param graphicalContainer
	 *            the graphical container to use to create the edge
	 */
	protected void createEdge(final String creationToolId, final SemanticAndGraphicalCreationChecker checker) {
		createEdge(creationToolId, checker, false);
	}

	/**
	 * This method create the edge and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param creationToolId
	 *            the ID of the creation tool to used
	 * @param checker
	 *            the checker used to validate the creation of the edge
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized, <code>false</code> otherwise.
	 */
	protected void createEdge(final String creationToolId, final SemanticAndGraphicalCreationChecker checker, final boolean isSynchronized) {
		checkSiriusDiagramSynchronization(isSynchronized);

		Diagram diagram = getDiagram();
		boolean result = this.applyCreationTool(creationToolId, getDDiagram(), (EdgeTarget) getEdgeSource(), (EdgeTarget) getEdgeTarget());
		Assert.assertTrue("The creation of edge failed", result); //$NON-NLS-1$
		fixture.flushDisplayEvents();

		Assert.assertEquals("The diagram must contain only one additional edge after creating an Edge", 1, diagram.getEdges().size()); //$NON-NLS-1$
		Object createdEdge = diagram.getEdges().get(0);
		Assert.assertTrue("The created edge must be a View", createdEdge instanceof View); //$NON-NLS-1$
		EObject siriusNewRepresentation = ((View) createdEdge).getElement();
		Assert.assertTrue("The created sirus edge must be an DEdge", siriusNewRepresentation instanceof DEdge); //$NON-NLS-1$

		checker.validateRepresentationElement((DEdge) siriusNewRepresentation);

		// undo
		fixture.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();
		checker.validateAfterUndo();

		// redo
		fixture.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();
		checker.validateAfterRedo();
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
	 * Set the semantic target of the edge.
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
	 * Applies the {@code creationToolId} tool on the provided {@code diagram} between {@code edgeSource} and {@code edgeTarget}.
	 * <p>
	 * This method can be overridden to change how the tool is actually invoked. For example, it can be overridden to use
	 * {@link SiriusDiagramEditorFixture#applyEdgeCreationToolFromPalette(String, DDiagram, EdgeTarget, EdgeTarget, org.eclipse.draw2d.geometry.Point, org.eclipse.draw2d.geometry.Point)}
	 * instead of the default tool creation process.
	 * </p>
	 * 
	 * @param creationToolId
	 *            the identifier of the creation tool
	 * @param diagram
	 *            the diagram
	 * @param edgeSource
	 *            the source of the edge to create
	 * @param edgeTarget
	 *            the target of the edge to create
	 * @return {@code true} if the tool could be applied, {@code false} otherwise
	 */
	protected boolean applyCreationTool(String creationToolId, DDiagram diagram, EdgeTarget edgeSource, EdgeTarget edgeTarget) {
		return fixture.applyEdgeCreationTool(creationToolId, getDDiagram(), edgeSource, edgeTarget);
	}
}
