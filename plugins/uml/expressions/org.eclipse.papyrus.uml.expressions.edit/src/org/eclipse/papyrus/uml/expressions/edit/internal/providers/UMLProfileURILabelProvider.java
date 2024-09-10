/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.providers;

import org.eclipse.uml2.uml.Profile;

/**
 * This label provides a label for UML Element (like {@link UMLLabelProvider}
 * We just customized the label for Profile where we displayed its URI too (when it exists)
 *
 */
public class UMLProfileURILabelProvider extends UMLLabelProvider {

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		final String text = super.getText(element);
		if (null != text && element instanceof Profile) {
			final StringBuilder builder = new StringBuilder(text);
			builder.append(" - "); //$NON-NLS-1$
			final String profileURI = ((Profile) element).getURI();
			builder.append(null == profileURI || profileURI.isEmpty() ? "No URI" : profileURI); //$NON-NLS-1$
			return builder.toString();
		}
		return text;
	}

}
