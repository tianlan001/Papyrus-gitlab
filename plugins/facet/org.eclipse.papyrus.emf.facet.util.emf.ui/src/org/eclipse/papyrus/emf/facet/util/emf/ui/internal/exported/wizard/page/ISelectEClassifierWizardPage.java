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
package org.eclipse.papyrus.emf.facet.util.emf.ui.internal.exported.wizard.page;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.emf.facet.util.emf.ui.internal.exported.util.wizard.page.SelectEClassifierWizardPage;

/**
 * Interface for {@link SelectEClassifierWizardPage}.
 *
 * @see SelectEClassifierWizardPage
 * @since 0.3
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ISelectEClassifierWizardPage<T extends EClassifier> extends
		IWizardPage {

	/**
	 * @return the selected {@link EClassifier}.
	 */
	T getSelectedEClassifier();

	/**
	 * Set the {@link EClassifier}.
	 *
	 * @param selection
	 *            the name of the {@link EClassifier} to select.
	 */
	void selectEClassifier(final String selection);

}