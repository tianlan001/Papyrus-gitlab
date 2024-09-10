/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.figure;

/**
 * the figure of a instance specification
 * it contains moreover two label to display roles
 */
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure;

public class InstanceSpecificationLinkFigure extends UMLEdgeFigure {

	protected WrappingLabel targetLabel;

	protected WrappingLabel sourceLabel;

	/**
	 *
	 * Constructor.
	 *
	 */
	public InstanceSpecificationLinkFigure() {
		super();
		targetLabel = new PapyrusWrappingLabel();
		this.add(targetLabel);
		sourceLabel = new PapyrusWrappingLabel();
		this.add(sourceLabel);
	}

	/**
	 *
	 * @return the label of the target, never null
	 */
	public WrappingLabel getTargetLabel() {
		return targetLabel;
	}

	/**
	 *
	 * @return return the source of the label, never null
	 */
	public WrappingLabel getSourceLabel() {
		return sourceLabel;
	}
}
