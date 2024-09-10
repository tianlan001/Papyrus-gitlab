/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView;
import org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferencePropertyEditorWithPropertyView;
import org.eclipse.swt.widgets.Composite;

/**
 * A {@link MultiReferencePropertyEditorWithPropertyView} editor for {@link StereotypeToApply}.
 *
 */
public class FeaturesToSetPropertyEditorWithPropertyView extends MultiReferencePropertyEditorWithPropertyView {

	/**
	 * Constructor.
	 */
	public FeaturesToSetPropertyEditorWithPropertyView(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferencePropertyEditorWithPropertyView#createMultiReferenceEditorWithPropertyView(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected MultiReferenceEditorWithPropertyView createMultiReferenceEditorWithPropertyView(final Composite parent, final int style) {
		return new FeaturesToSetEditorWithPropertyView(parent, style);
	}

}
