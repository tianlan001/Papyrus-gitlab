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
 */
package org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.exported.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.wizard.WizardFactory;
import org.eclipse.papyrus.emf.facet.util.ui.internal.exported.wizard.IExtendedWizard;

/**
 * Factory for the wizards creation.
 *
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IWizardFactory {

	IWizardFactory INSTANCE = new WizardFactory();

	/**
	 * Create a concrete instance of {@link ICreateFacetSetWizard}
	 *
	 * @param selection
	 *            the original selection for the wizard.
	 * @return a new instance of {@link ICreateFacetSetWizard}
	 */
	IExtendedWizard createCreateFacetSetWizard(ISelection selection);
}
