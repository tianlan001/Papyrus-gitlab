/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.custom.figures;

import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Figure for DurationObservation
 */
// to delete
public class DurationObservationNodeFigure extends AbstractObservationNodeFigure {

	public static final String IMAGE_DURATION = "DurationObservation.gif"; //$NON-NLS-1$

	@Override
	public void setAppliedStereotypeIcon(Image image) {
		if (image == null) {
			setIcon(Activator.getPluginIconImage(ID, PATH + IMAGE_DURATION));
		} else {
			setIcon(image);
		}
	}

}
