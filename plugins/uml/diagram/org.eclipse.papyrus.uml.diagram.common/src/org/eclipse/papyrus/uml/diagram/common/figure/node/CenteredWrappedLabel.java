/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;

/**
 * This class is a {@link WrappingLabel}, which default behavior is set
 * differently :
 *
 * The text is centered and automatically wraps on several lines.
 */
public class CenteredWrappedLabel extends PapyrusWrappingLabel {

	/**
	 * Construct an empty wrapping label with customized alignment.
	 */
	public CenteredWrappedLabel() {
		super();
		setTextJustification(PositionConstants.CENTER);
		setAlignment(PositionConstants.CENTER);
		setTextWrap(true);
	}
}
