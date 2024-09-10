/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	CEA LIST -  Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 441962
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *****************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.ui;

/**
 * Contribution to contextual menu for Direct Editor extension.
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.MultiReconciler} instead.
 */
@Deprecated
public class DirectEditorsContributionItem extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.DirectEditorsContributionItem {

	/**
	 *
	 */
	public DirectEditorsContributionItem() {
		super();
	}

	/**
	 * @param id
	 */
	public DirectEditorsContributionItem(String id) {
		super(id);
	}
}
