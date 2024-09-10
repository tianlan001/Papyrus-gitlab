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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.providers;

import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.swt.graphics.Image;

public class ElementTypesDetailsLabelProvider extends org.eclipse.jface.viewers.LabelProvider {

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		String result = "";
		if (element instanceof String) {
			result += element;
		} else if (element instanceof List<?>) {
			result += "Advices: ";
		} else if (element instanceof IEditHelperAdvice) {
			result += element;
		}
		return result;
	}
}
