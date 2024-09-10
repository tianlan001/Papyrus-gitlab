/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.tests;

import static org.junit.Assert.fail;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditServiceProvider;
import org.eclipse.papyrus.infra.services.edit.tests.edit.helper.EPackageEditHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Abstract Papyrus initialization class (required to get Service activation).
 */
public abstract class AbstractTestElementEditService extends AbstractPapyrusTest {

	private static Bundle testBundle;

	protected IElementType eClassType;

	protected IElementType ePackgType;

	protected IClientContext context;

	protected IElementEditServiceProvider provider;

	protected static IEditorPart editor;

	@BeforeClass
	public static void openPapyrusWithAnEmptyProject() throws Exception {
		// Prepare new project for tests
		IProject testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("TestProject");
		if (!testProject.exists()) {
			testProject.create(new NullProgressMonitor());
		}
		if (!testProject.isOpen()) {
			testProject.open(new NullProgressMonitor());
		}

		// Copy EmptyModel from bundle to the test project
		final IFile emptyModel_di = testProject.getFile("EmptyModel.di");
		IFile emptyModel_no = testProject.getFile("EmptyModel.notation");
		IFile emptyModel_uml = testProject.getFile("EmptyModel.uml");

		emptyModel_di.create(getTestBundle().getResource("/model/EmptyModel.di").openStream(), true, new NullProgressMonitor());
		emptyModel_no.create(getTestBundle().getResource("/model/EmptyModel.notation").openStream(), true, new NullProgressMonitor());
		emptyModel_uml.create(getTestBundle().getResource("/model/EmptyModel.uml").openStream(), true, new NullProgressMonitor());

		// Open the EmptyModel.di file with Papyrus (assumed to be the default editor for "di" files here).
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(emptyModel_di.getName());
				try {
					editor = page.openEditor(new FileEditorInput(emptyModel_di), desc.getId());
				} catch (Exception ex) {
					ex.printStackTrace(System.out);
				}
			}
		});

		Assert.assertNotNull(editor);
	}

	protected static Bundle getTestBundle() {
		if (testBundle == null) {
			testBundle = FrameworkUtil.getBundle(AbstractTestElementEditService.class);
		}
		return testBundle;
	}

	@Before
	public void setUp() {
		// To prepare this test two element types are added to Papyrus Client Context
		ElementTypeRegistry.getInstance().register(new MetamodelType("ECLASS_TEST_ID", null, "ECLASS", EcorePackage.eINSTANCE.getEClass(), null)); //$NON-NLS-1$
		ElementTypeRegistry.getInstance().register(new MetamodelType("EPACKG_TEST_ID", null, "EPACKG", EcorePackage.eINSTANCE.getEPackage(), new EPackageEditHelper())); //$NON-NLS-1$

		eClassType = ElementTypeRegistry.getInstance().getType("ECLASS_TEST_ID");
		ePackgType = ElementTypeRegistry.getInstance().getType("EPACKG_TEST_ID");

		try {
			context = TypeContext.getDefaultContext();
			context.bindId("ECLASS_TEST_ID"); //$NON-NLS-1$
			context.bindId("EPACKG_TEST_ID"); //$NON-NLS-1$
		} catch (ServiceException e) {
			fail("Default client context is not found");
			return;
		}

		provider = ElementEditServiceUtils.getEditServiceProvider(context);
		if(provider == null) {
			fail("Element edit service can not be found.");
		}
	}

	@AfterClass
	public static void closePapyrusAndCleanProject() {
		RunnableWithResult<Boolean> closeEditorsRunnable;
		Display.getDefault().syncExec(closeEditorsRunnable = new RunnableWithResult.Impl<Boolean>() {

			public void run() {
				setResult(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false));
			}
		});
		editor = null;

		// Close all editors
		Assert.assertTrue("Could not close editors correctly.", closeEditorsRunnable.getResult());

		// Delete test project
		IProject testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("TestProject");
		try {
			testProject.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Could delete test project correctly (" + e.getMessage() + ").");
		}
	}
}
