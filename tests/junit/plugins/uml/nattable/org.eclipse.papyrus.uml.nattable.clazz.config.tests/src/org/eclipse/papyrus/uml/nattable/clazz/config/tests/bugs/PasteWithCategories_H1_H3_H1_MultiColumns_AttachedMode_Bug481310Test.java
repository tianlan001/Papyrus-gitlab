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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesMultiColumnsAttachedModeTests;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;

/**
 * Test pastes with 1 hidden category to the 1st level (H1), 3 hidden categories on the 2nd level (H3) and 1 hidden category to the 3rd level (H1).
 * If only the first depth elements are added, the paste must be done correctly.
 */
public class PasteWithCategories_H1_H3_H1_MultiColumns_AttachedMode_Bug481310Test extends AbstractPasteWithCategoriesMultiColumnsAttachedModeTests {

	/**
	 * The bug 481310 folder.
	 */
	public static final String BUG_481310_FOLDER = "/resources/bugs/bug481310/"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesTests#getSourcePath()
	 */
	@Override
	protected String getSourcePath() {
		return BUG_481310_FOLDER;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesTests#validateReturnedStatus(org.eclipse.core.runtime.IStatus)
	 */
	@Override
	protected void validateReturnedStatus(final IStatus status) {
		Assert.assertTrue(status.isOK());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesTests#verifyModel_1_3_1()
	 */
	@Override
	protected void verifyModel_1_3_1() throws ServiceException {
		final EObject context = getTable().getContext();
		Assert.assertTrue(context instanceof Model);
		final Model pack = (Model) context;
		final List<NamedElement> members = pack.getMembers();
		Assert.assertEquals(2, members.size());
		for (int i = 0; i < members.size(); i++) {
			final NamedElement tmp = members.get(i);
			Assert.assertTrue(tmp instanceof Class);
			final Class clazz = (Class) tmp;
			final StringBuilder className = new StringBuilder(CLASS_BASE_NAME);
			className.append(i);
			Assert.assertEquals(className.toString(), clazz.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesTests#checkChildrenClasses(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 */
	@Override
	protected void checkRootClasses(final ITreeItemAxis root, final EObject parent) throws Exception {
		// Do nothing : no child in classes
	}

}
