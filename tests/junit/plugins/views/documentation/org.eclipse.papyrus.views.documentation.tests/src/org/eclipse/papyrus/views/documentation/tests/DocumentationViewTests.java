/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.documentation.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.views.documentation.Activator;
import org.eclipse.papyrus.views.documentation.views.DocumentationView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@PluginResource("/resources/documentationViewTests/model.di")
/**
 * Class to test that the documentation view is well loaded.
 */
public class DocumentationViewTests extends AbstractPapyrusTest {

	/** the id of the documentation view */
	public static final String viewId = "org.eclipse.papyrus.views.documentation.DocumentationView"; //$NON-NLS-1$

	/** Papyrus editor fixture. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/** the documenation view */
	private DocumentationView documentationView;

	/**
	 * Initialize tests.
	 */
	@Before
	public void initTests() {

		Activator.getDefault().setToggleEditorSetting(true);

		RunnableWithResult<?> runnable;

		Display.getDefault().syncExec(runnable = new RunnableWithResult.Impl<Object>() {

			public void run() {
				documentationView = getDocumentationView();
				documentationView.setFocus();
				setStatus(Status.OK_STATUS);
			}
		});

		Assert.assertEquals(runnable.getStatus().getMessage(), IStatus.OK, runnable.getStatus().getSeverity());

		editorFixture.ensurePapyrusPerspective();

	}


	/**
	 * Test if the documentation view exist.
	 */
	@Test
	public void documentationViewTests() {
		assertNotNull("documentation view must exist", documentationView);
	}

	/**
	 * Show the view 'documentation'.
	 */
	public DocumentationView getDocumentationView() {
		try {
			final IWorkbenchPage activePage = EditorHelper.getActiveWindow().getActivePage();
			return (DocumentationView) activePage.showView(DocumentationView.ID);
		} catch (final PartInitException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage()));
			return null;
		}
	}


}
