/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Alban Ménager (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 *  	Grégoire Dupé (Mia-Software) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.util.ui.internal.exported.util.widget.query;

import org.eclipse.papyrus.emf.facet.util.ui.internal.exported.util.widget.command.AbstractCommandWidget;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class must be used by the plug-ins extending this plug-in. It
 * provides a simple way to create the widget for the edition of the query.
 *
 * @since 0.3
 */
public abstract class AbstractQueryWidget<P extends Object> extends
		AbstractCommandWidget {

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            the parent of this widget.
	 * @param editingDomain
	 *            the current editing domain.
	 * @param properties
	 *            the properties.
	 */
	protected AbstractQueryWidget(final Composite parent) {
		super(parent);
	}
}
