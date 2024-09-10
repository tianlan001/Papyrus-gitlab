/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 497841
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;


public class NotationTypes {

	private static final double HIMETRIC_UNITS_PER_INCH = 2540.0;

	private static float pseudoHimetricDPI = 72.0f;
	private static double pseudoHimetricScale = 1.0;

	static {
		pseudoHimetricScale = pseudoHimetricDPI / HIMETRIC_UNITS_PER_INCH;
	}

	@Operation(contextual = true, kind = Kind.QUERY)
	public static int toPixels(LayoutConstraint self, Integer himetric) {
		return (himetric == null) ? -1 : convertToPixels(himetric);
	}

	@Operation(contextual = true, kind = Kind.QUERY)
	public static int toPixels(View self, Integer himetric) {
		return (himetric == null) ? -1 : convertToPixels(himetric);
	}

	@Operation(contextual = true, kind = Kind.QUERY)
	public static int toPixels(Anchor self, Integer himetric) {
		return (himetric == null) ? -1 : convertToPixels(himetric);
	}

	@Operation(contextual = true, kind = Kind.HELPER)
	public static void copyBendpoints(final RelativeBendpoints source, RelativeBendpoints target, boolean convertToPixels) {
		List<RelativeBendpoint> result = new LinkedList<RelativeBendpoint>();

		for (Object point : source.getPoints()) {
			if (point instanceof RelativeBendpoint) {
				RelativeBendpoint pointCopy = new RelativeBendpoint(((RelativeBendpoint) point).convertToString());
				result.add(pointCopy);
			}
		}

		if (convertToPixels) {
			List<RelativeBendpoint> convertedResult = new LinkedList<RelativeBendpoint>();

			for (RelativeBendpoint point : result) {
				convertedResult.add(convertToPixels(point));
			}

			result = convertedResult;
		}

		target.setPoints(result);
	}

	private static RelativeBendpoint convertToPixels(RelativeBendpoint bendpoint) {
		int newSourceX, newSourceY, newTargetX, newTargetY;

		newSourceX = convertToPixels(bendpoint.getSourceX());
		newSourceY = convertToPixels(bendpoint.getSourceY());
		newTargetX = convertToPixels(bendpoint.getTargetX());
		newTargetY = convertToPixels(bendpoint.getTargetY());

		return new RelativeBendpoint(newSourceX, newSourceY, newTargetX, newTargetY);
	}

	private static int convertToPixels(int source) {
		// TODO: Fix hard-coded pixel defaults in the QVTos and
		// elsewhere that assume a 72-DPI display (cf. bug 497841).

		// return MapModeTypes.HIMETRIC_MM.LPtoDP(source);
		return (int) (source * pseudoHimetricScale);
	}
}
