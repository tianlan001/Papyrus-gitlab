/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 * Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 * Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Image;

/**
 * This a WrappingLabel that can display applied stereotypes
 */
public class AppliedStereotypeWrappingLabelFigure extends PapyrusWrappingLabel implements IPapyrusUMLElementFigure {

	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {
		setText(stereotypes);
		setTextWrap(true);
	}

}
