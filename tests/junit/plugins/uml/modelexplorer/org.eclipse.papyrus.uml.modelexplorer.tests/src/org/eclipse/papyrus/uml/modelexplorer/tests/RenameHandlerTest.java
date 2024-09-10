/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.tests;

import java.util.List;

import org.eclipse.core.commands.IHandler;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.views.modelexplorer.tests.AbstractHandlerTest;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


@PluginResource("/resources/model.di")
public class RenameHandlerTest extends AbstractHandlerTest {

	/**
	 * the id of the command to rename the element
	 */
	private static final String commandId = "org.eclipse.ui.edit.rename";

	/**
	 * 
	 * Constructor.
	 * 
	 */
	public RenameHandlerTest() {
		super(commandId, Activator.getDefault().getBundle());
	}

	@Ignore("Ignored as the rename is now done using F2 with direct editors => Bug 497289")
	@Test
	public void renameUMLUnnamedElementTest() {
		List<Comment> comments = ((Model) getRootOfTheModel()).getOwnedComments();
		Assert.assertFalse(comments.isEmpty());
		for (int i = 0; i < ((Model) getRootOfTheModel()).getOwnedComments().size(); i++) {
			selectElementInTheModelexplorer(((Model) getRootOfTheModel()).getOwnedComments().get(i));
			IHandler handler = getActiveHandler();
			if (handler != null) {
				Assert.assertFalse("Rename handler is not disabled on UnnamedElement", handler.isEnabled()); //$NON-NLS-1$
			}
		}
	}

	@Ignore("Ignored as the rename is now done using F2 with direct editors => Bug 497289")
	@Test
	public void renameUMLNamedElementTest() {
		selectElementInTheModelexplorer(getRootOfTheModel());
		IHandler handler = getActiveHandler();
		Assert.assertTrue(handler.isEnabled());
	}
}
