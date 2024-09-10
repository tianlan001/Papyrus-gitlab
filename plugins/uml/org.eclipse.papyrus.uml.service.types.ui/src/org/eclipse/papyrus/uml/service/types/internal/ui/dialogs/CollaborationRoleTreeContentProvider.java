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
package org.eclipse.papyrus.uml.service.types.internal.ui.dialogs;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;

/**
 * This class is a basic content provider that assumes a {@link CollaborationUse} is given as the
 * root parent, and that will calculate the possible roles that can be used for binding.
 *
 */
public class CollaborationRoleTreeContentProvider implements ITreeContentProvider {

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		// no implementation needed.
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object getParent(Object element) {
		// no implementation needed.
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(Object element) {
		// no implementation needed.
		return false;
	}

	/**
	 * <pre>
	 * This method returns the role referenced by the {@link Collaboration} that is
	 * the type of the {@link CollaborationUse}.
	 *
	 * {@inheritDoc}
	 * </pre>
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		Object[] children = null;

		if (inputElement instanceof CollaborationUse) {
			CollaborationUse parentUMLElement = (CollaborationUse) inputElement;
			if (parentUMLElement.getType() != null) {
				children = parentUMLElement.getType().getCollaborationRoles().toArray();
			}
		}
		return children;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		// no implementation needed.
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// no implementation needed.
	}

}
