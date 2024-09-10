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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.utils.AbstractPasteInsertInTableHandler;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;
import org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteEmptyOverwriteTest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;

/**
 * This allows to test the paste in attached mode when a stereotype property is typed by an enum.
 */
public class PasteAttachedModeEnumTest extends AbstractPasteEmptyOverwriteTest {

	/**
	 * The path of the model to test.
	 */
	public static final String PASTE_FOLDER_PATH = "/resources/bugs/bug456841/"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public PasteAttachedModeEnumTest() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteEmptyOverwriteTest#testPaste()
	 *
	 * @throws Exception
	 */
	@Override
	public void testPaste() throws Exception {
		super.testPaste();

		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		final NattableModelManager manager = (NattableModelManager) currentManager;

		// Check the table content after command
		checkTableContent(manager, RESULT_POST_FILE_NAME);

		// Check if the stereotype is applied to created class and its enum literal set
		checkCreatedObject(currentManager);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteInsertTest#checkReturned_Status(org.eclipse.core.runtime.IStatus)
	 */
	@Override
	protected void checkReturned_Status(final IStatus status) {
		Assert.assertEquals("Warning must be caught", IStatus.WARNING, status.getSeverity()); //$NON-NLS-1$
		Assert.assertEquals("Warning message is not the expected message", Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasBeenDoneWithSomeProblems, status.getMessage()); //$NON-NLS-1$
		Assert.assertTrue("Status must be a multi-status", status instanceof MultiStatus); //$NON-NLS-1$
		Assert.assertEquals("Warning message is not the expected message", "The identifier 'Class2' was not found in the selection, so the object was created", ((MultiStatus) status).getChildren()[0].getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This allows to test the created object.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkCreatedObject(final INattableModelManager currentManager) throws Exception {
		// Check if the stereotype is applied to created class and its enum literal set
		final List<Object> rowElementsList = currentManager.getRowElementsList();
		final Object object = rowElementsList.get(1);

		Assert.assertTrue("The row element must be an EOBjectAxis", object instanceof EObjectAxis); //$NON-NLS-1$
		Assert.assertTrue("The created object must be a class", ((EObjectAxis) object).getElement() instanceof Class); //$NON-NLS-1$

		final Class clazz = (Class) ((EObjectAxis) object).getElement();

		final Stereotype stereotype = (Stereotype) getRootUMLModel().getProfileApplications().get(0).getAppliedProfile().getPackagedElement("Stereotype1"); //$NON-NLS-1$
		Assert.assertEquals("The created class must be stereotyped", false, clazz.getAppliedStereotypes().isEmpty()); //$NON-NLS-1$
		Assert.assertEquals("The created class must be stereotyped with Stereotype1", stereotype, clazz.getAppliedStereotypes().get(0)); //$NON-NLS-1$

		Assert.assertEquals("The attribute value set must be the second literal", ((Enumeration) stereotype.getAllAttributes().get(8).getType()).getOwnedLiterals().get(1), currentManager.getDataValue(1, 1));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteOverwriteTest#manageParameters(java.util.Map)
	 */
	@Override
	public void manageParameters(final Map<Object, Object> parameters) {
		super.manageParameters(parameters);
		parameters.put(AbstractPasteInsertInTableHandler.USER_ACTION__PREFERRED_USER_ACTION, UserActionConstants.ADD_USER_ACTION);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteOverwriteTest#getSourcePath()
	 */
	@Override
	protected String getSourcePath() {
		return PASTE_FOLDER_PATH;
	}

}
