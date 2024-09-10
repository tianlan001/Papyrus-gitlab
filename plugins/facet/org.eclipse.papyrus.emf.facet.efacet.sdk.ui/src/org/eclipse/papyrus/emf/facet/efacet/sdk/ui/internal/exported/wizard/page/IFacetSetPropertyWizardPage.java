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
package org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.exported.wizard.page;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.wizard.page.FacetSetPropertyWizardPage;

/**
 * @see FacetSetPropertyWizardPage
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IFacetSetPropertyWizardPage extends IWizardPage {

	/**
	 * @return the nsURI.
	 */
	String getNsUri();

	/**
	 * Set the nsURI.
	 *
	 * @param nsUri
	 *            the nsURI to set.
	 */
	void setNsUri(String nsUri);

	/**
	 * @return the prefix.
	 */
	String getPrefix();

	/**
	 * Set the prefix.
	 *
	 * @param prefix
	 *            the prefix to set.
	 */
	void setPrefix(String prefix);

}