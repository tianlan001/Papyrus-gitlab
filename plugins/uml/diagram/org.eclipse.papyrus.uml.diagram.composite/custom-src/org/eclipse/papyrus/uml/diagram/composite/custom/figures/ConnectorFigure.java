/*****************************************************************************
 * Copyright (c) 2009-2017 CEA LIST, ALL4TEC and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 515661
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.figures;

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure;

/**
 * Figure for Connector representation.
 */
public class ConnectorFigure extends UMLEdgeFigure {

	/** Owned label for Connector Name **/
	private WrappingLabel fNameLabel;

	/** Owned label for Connector source multiplicity **/
	private WrappingLabel fMultiplicitySourceLabel;

	/** Owned label for Connector target multiplicity **/
	private WrappingLabel fMultiplicityTargetLabel;

	/**
	 * Default constructor
	 */
	public ConnectorFigure() {
		super();
		createContents();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createContents() {
		super.createContents();

		fNameLabel = new WrappingLabel();
		// fNameLabel.setText(""); //$NON-NLS-1$

		this.add(fNameLabel);

		fMultiplicitySourceLabel = new PapyrusWrappingLabel();
		// fMultiplicitySourceLabel.setText(""); //$NON-NLS-1$

		this.add(fMultiplicitySourceLabel);

		fMultiplicityTargetLabel = new PapyrusWrappingLabel();
		// fMultiplicityTargetLabel.setText(""); //$NON-NLS-1$

		this.add(fMultiplicityTargetLabel);
	}

	/**
	 * Gets the applied stereotype association label.
	 *
	 * @return the applied stereotype association label
	 */
	public WrappingLabel getAppliedStereotypeAssociationLabel() {
		return appliedStereotypeLabel;
	}

	/**
	 * Gets the Connector name label.
	 *
	 * @return the Connector name label
	 */
	public WrappingLabel getNameLabel() {
		return fNameLabel;
	}

	/**
	 * Gets the multiplicity source label.
	 *
	 * @return the multiplicity source label
	 */
	public WrappingLabel getMultiplicitySourceLabel() {
		return fMultiplicitySourceLabel;
	}

	/**
	 * Gets the multiplicity target label.
	 *
	 * @return the multiplicity target label
	 */
	public WrappingLabel getMultiplicityTargetLabel() {
		return fMultiplicityTargetLabel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.edge.PapyrusEdgeFigure#resetStyle()
	 */
	@Override
	public void resetStyle() {
		super.resetStyle();
		setSourceDecoration(null);
		setTargetDecoration(null);
	}
}
