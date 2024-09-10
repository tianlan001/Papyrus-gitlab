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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.architectureview.providers;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;

/**
 * This allows to define the content provider for the architecture view tree viewer.
 */
public class ArchitectureViewContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof Collection) {
			return ((Collection<?>) inputElement).toArray();
		}
		return null;
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// nothing
	}

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public boolean hasChildren(final Object element) {
		return 0 != getChildren(element).length;
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof MergedArchitectureDomain) {
			return ((MergedArchitectureDomain) parentElement).getContexts().toArray();
		} else if (parentElement instanceof MergedArchitectureContext) {
			return ((MergedArchitectureContext) parentElement).getViewpoints().toArray();
		}
		return new Object[0];
	}

}
