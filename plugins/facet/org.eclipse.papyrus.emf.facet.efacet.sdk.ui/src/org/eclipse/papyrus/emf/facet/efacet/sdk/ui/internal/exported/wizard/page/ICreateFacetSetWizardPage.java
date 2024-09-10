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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.wizard.page.CreateFacetSetWizardPage;

/**
 * @see CreateFacetSetWizardPage
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICreateFacetSetWizardPage extends IWizardPage {

	/**
	 * @return the model file.
	 */
	IFile getModelFile();

	/**
	 * @return the model file name.
	 */
	String getFileName();

	/**
	 * @param fileName
	 */
	void setFileName(String fileName);

}