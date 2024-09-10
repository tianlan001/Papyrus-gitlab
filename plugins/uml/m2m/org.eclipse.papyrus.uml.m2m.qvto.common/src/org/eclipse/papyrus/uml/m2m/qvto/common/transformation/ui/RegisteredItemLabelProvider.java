/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.transformation.ui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.uml.extensionpoints.IRegisteredItem;
import org.eclipse.swt.graphics.Image;

/**
 * Label Provider for RegisteredItems (Libraries & Profiles)
 *
 * @author Camille Letavernier
 *
 */
public class RegisteredItemLabelProvider extends LabelProvider {
	@Override
	public Image getImage(Object element) {
		if (element instanceof IRegisteredItem) {
			IRegisteredItem library = (IRegisteredItem) element;
			return library.getImage();
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IRegisteredItem) {
			IRegisteredItem library = (IRegisteredItem) element;
			return library.getName();
		}

		return super.getText(element);
	}
}