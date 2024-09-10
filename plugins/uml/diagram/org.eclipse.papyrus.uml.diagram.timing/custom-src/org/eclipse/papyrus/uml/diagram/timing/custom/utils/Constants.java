/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.utils;

import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantVerticalLineEditPart;

public final class Constants {

	private Constants() {
		// not instantiable
	}

	/**
	 * The minimum distance between the center of two occurrence specifications (to avoid indistinguishable overlapping
	 * figures)
	 */
	public static final int MINIMUM_DISTANCE_BETWEEN_OCCURRENCE_SPECIFICATIONS = 4;

	public static final int MARGIN_BETWEEN_LIFELINES = 8;

	public static final int TIME_RULER_HEIGHT = 30;

	public static final int STATE_DEFINITIONS_WIDTH = 100;

	public static final String fullStateInvariantId = FullStateInvariantEditPartCN.VISUAL_ID;

	public static final String compactStateInvariantId = CompactStateInvariantEditPartCN.VISUAL_ID;

	public static final String verticalLineId = FullStateInvariantVerticalLineEditPart.VISUAL_ID;
}
