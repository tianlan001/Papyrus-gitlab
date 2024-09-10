/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.viewers.Viewer;

/**
 * Abstract Implementation for {@link IRestrictedContentProvider}
 *
 * @author Juan Cadavid
 *
 */
public abstract class AbstractRestrictedContentProvider implements IRestrictedContentProvider {

	/**
	 * flag to indicate the useage of the restriction in the content provider
	 */
	private boolean isRestricted;

	/**
	 * if <code>false</code> the inherited features will be displayed
	 */
	private boolean ignoreInheritedFeatures;

	/**
	 *
	 * Constructor.
	 *
	 * @param isRestricted
	 */
	public AbstractRestrictedContentProvider(final boolean isRestricted) {
		this.isRestricted = isRestricted;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider#setRestriction(boolean)
	 *
	 * @param isRestricted
	 */
	@Override
	public final void setRestriction(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @param viewer
	 * @param oldInput
	 * @param newInput
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {

	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#setIgnoreInheritedElements(boolean)
	 *
	 * @param ignoreInheritedElements
	 */
	@Override
	public void setIgnoreInheritedElements(boolean ignoreInheritedElements) {
		this.ignoreInheritedFeatures = ignoreInheritedElements;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#isIgnoringInheritedElements()
	 *
	 * @return
	 */
	@Override
	public boolean isIgnoringInheritedElements() {
		return this.ignoreInheritedFeatures;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider#isRestricted()
	 *
	 * @return
	 */
	@Override
	public boolean isRestricted() {
		return this.isRestricted;
	}

}
