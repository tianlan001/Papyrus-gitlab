/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for preferences tree of Direct Editor and Constraint.
 * 
 * @author Gabriel Pascual
 *
 */
public class DirectEditorLabelProvider extends LabelProvider implements ILabelProvider {

	/** The Constant UNKNOWN_LABEL. */
	private static final String UNKNOWN_LABEL = "<Unknown>";

	/**
	 * Default constructor.
	 *
	 */
	public DirectEditorLabelProvider() {
		super();
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		String text = UNKNOWN_LABEL;
		if (element instanceof DirectEditorTreeItem) {
			// For a Direct Editor, the meta class to edit is displayed
			text = ((DirectEditorTreeItem) element).getMetaClassToEdit();
		} else if (element instanceof IDirectEditorConstraint) {
			// For a Constraint, the label of the constraint is displayed
			text = ((IDirectEditorConstraint) element).getLabel();
		}
		return text;
	}

}
