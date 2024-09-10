/*****************************************************************************
 * Copyright (c) 2008, 2018 CEA LIST.
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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *****************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.ui;

import java.util.List;

import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

/**
 * Reconciler that uses several independent strategies.
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.MultiReconciler} instead.
 */
@Deprecated
public class MultiReconciler extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.MultiReconciler {

	/**
	 * Creates a new reconciler that uses several reconciling strategies to reconcile its document
	 * independent of the type of the document's contents.
	 *
	 * @param strategies
	 *            the reconciling strategies to be used
	 * @param isIncremental
	 *            the indication whether strategy is incremental or not
	 */
	// @unused
	public MultiReconciler(List<IReconcilingStrategy> strategies, boolean isIncremental) {
		super(strategies, isIncremental);
	}
}
