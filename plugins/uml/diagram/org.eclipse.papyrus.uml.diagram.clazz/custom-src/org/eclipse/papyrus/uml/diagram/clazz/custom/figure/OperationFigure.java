/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST and others.
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
 *   Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.figure;

import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;

public class OperationFigure extends PapyrusWrappingLabel {

	static final org.eclipse.swt.graphics.Font THIS_FONT = new org.eclipse.swt.graphics.Font(org.eclipse.swt.widgets.Display.getCurrent(), "Arial", 10, org.eclipse.swt.SWT.NORMAL); //$NON-NLS-1$

	public OperationFigure() {
		setText(""); //$NON-NLS-1$
		setFont(THIS_FONT);
	}
}
