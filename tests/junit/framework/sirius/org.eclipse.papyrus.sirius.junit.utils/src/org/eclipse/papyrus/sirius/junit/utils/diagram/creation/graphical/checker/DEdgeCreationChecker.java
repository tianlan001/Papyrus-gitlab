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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.viewpoint.DRepresentationElement;


/**
 * Creation checker for the graphical edge.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DEdgeCreationChecker extends AbstractGraphicalEdgeCreationChecker {

	private final String edgeMappingType;

	private Consumer<? super EdgeTarget> sourceVerification;

	private Consumer<? super EdgeTarget> targetVerification;

	/**
	 * Constructor.
	 *
	 * @param diagram
	 *            the GMF diagram
	 * @param container
	 *            the graphical parent of the element to create
	 * @param edgeMappingType
	 *            the expected edge mapping name.
	 */
	public DEdgeCreationChecker(final Diagram diagram, final EObject container, String edgeMappingType) {
		super(diagram, container);
		this.edgeMappingType = edgeMappingType;
	}

	/**
	 * Constructor.
	 *
	 * @param diagram
	 *            the GMF diagram
	 * @param container
	 *            the graphical parent of the element to create
	 * @param edgeMappingType
	 *            the expected edge mapping name.
	 */
	public DEdgeCreationChecker(final Diagram diagram, String edgeMappingType) {
		this(diagram, diagram.getElement(), edgeMappingType);
	}

	/**
	 * Sets the verification rule for source.
	 * <p>
	 * When null, no verification is performed on the source.
	 * </p>
	 *
	 * @param verification
	 *            consumer of source
	 */
	public void setSourceVerification(Consumer<? super EdgeTarget> sourceVerification) {
		this.sourceVerification = sourceVerification;
	}

	/**
	 * Sets the verification rule for target.
	 * <p>
	 * When null, no verification is performed on the target.
	 * </p>
	 *
	 * @param verification
	 *            consumer of target
	 */
	public void setTargetVerification(Consumer<? super EdgeTarget> verification) {
		this.targetVerification = verification;
	}


	@Override
	public void validateRepresentationElement(DRepresentationElement createdElementRepresentation) {
		validateEnds(createdElementRepresentation);
		super.validateRepresentationElement(createdElementRepresentation);
	}

	protected void validateEnds(DRepresentationElement view) {
		if (sourceVerification != null) {
			sourceVerification.accept(((DEdge) view).getSourceNode());
		}
		if (targetVerification != null) {
			targetVerification.accept(((DEdge) view).getTargetNode());
		}
	}

	@Override
	protected String getEdgeMappingType() {
		return this.edgeMappingType;
	}
}