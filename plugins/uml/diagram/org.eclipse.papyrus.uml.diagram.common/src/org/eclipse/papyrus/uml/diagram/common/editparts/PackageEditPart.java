/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @author Mickael ADAM
 *
 */
public abstract class PackageEditPart extends RoundedCompartmentEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public PackageEditPart(View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultNamePosition()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultNamePosition() {
		return PositionConstants.LEFT;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultIsPackage()
	 *
	 * @return
	 */
	@Override
	protected boolean getDefaultIsPackage() {
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultBottomNameMargin()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultBottomNameMargin() {
		return 2;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultBottomNameMargin()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultTopNameMargin() {
		return 0;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultBottomNameMargin()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultLeftNameMargin() {
		return 5;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultBottomNameMargin()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultRightNameMargin() {
		return 5;
	}

}
