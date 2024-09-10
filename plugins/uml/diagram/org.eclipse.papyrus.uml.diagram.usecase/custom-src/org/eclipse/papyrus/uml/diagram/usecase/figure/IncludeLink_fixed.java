/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST.
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
 *  CEA LIST- Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) - bug 520755
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.figure;

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

/**
 *
 * @deprecated since 4.0 (bug 520755)
 *             This wrapping label class should be removed in the same time than its associated view and its associated editpart (update of the gmfgen file).
 *             Moreover removing all these things requires to write a diagram reconciler.
 *             As it is for a Service release, we don't do it yet.
 *
 */
@Deprecated
public class IncludeLink_fixed extends WrappingLabel {

	public IncludeLink_fixed() {
		/*
		 * bug 520755
		 * the keyword is now in the stereotype label
		 * for compatibility, we keep this label, but is will display nothing
		 */

		// this.setText("\u00ABinclude\u00BB");
	}
}
