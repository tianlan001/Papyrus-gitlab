/*****************************************************************************
 * Copyright (c) 2010, 2014, 2019 LIFL, CEA, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 436047
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550321
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource.ModelKind;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Run as "JUnit Plugin-Test"
 *
 * @author cedric dumoulin
 *
 */
public class AbstractModelWithSharedResourceTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test that the resource is created only once.
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#createModel(org.eclipse.core.runtime.IPath)}.
	 */
	@Test
	public void testCreateModel() {

		String model1Key = "ecore";
		String model2Key = "genmodel";

		// Create models with different key, but use same extension (default from FakeModelWithSharedResource)
		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		// Call creates
		modelSet.createsModels(model1File);

		// Do check
		assertNotNull("resource created", model1.getResouce());
		assertNotNull("resource created", model2.getResouce());
		assertEquals("Resource is shared", model1.getResouce(), model2.getResouce());


	}

	/**
	 * Create and save models.
	 *
	 * @throws IOException
	 * @throws CoreException
	 */
	private void createAndSave(String model1Key, String model2Key) throws IOException, CoreException {

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");
		if (!p.exists()) {
			p.create(new NullProgressMonitor());
		}
		p.open(new NullProgressMonitor());

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		// Call creates and save

		modelSet.createsModels(model1File);
		model1.getResouce().setModified(true);
		modelSet.save(new NullProgressMonitor());
		modelSet.unload();
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#loadModel(org.eclipse.core.runtime.IPath)}.
	 *
	 * @throws CoreException
	 * @throws IOException
	 * @throws ModelMultiException
	 */
	@Test
	public void testLoadModelIPath() throws IOException, CoreException, ModelMultiException {


		String model1Key = "ecore";
		String model2Key = "genmodel";

		createAndSave(model1Key, model2Key);

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		// Call creates
		modelSet.loadModels(model1File);

		// Do check
		assertNotNull("resource loaded", model1.getResouce());
		assertNotNull("resource loaded", model2.getResouce());
		assertEquals("Resource is shared", model1.getResouce(), model2.getResouce());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#saveModel()}.
	 *
	 * @throws IOException
	 * @throws CoreException
	 */
	@Test
	public void testSaveModel() throws IOException, CoreException {
		String model1Key = "ecore";
		String model2Key = "genmodel";

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");
		if (!p.exists()) {
			p.create(new NullProgressMonitor());
		}
		p.open(new NullProgressMonitor());

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		// Call creates and save

		modelSet.createsModels(model1File);
		model1.getResouce().setModified(true);
		modelSet.save(new NullProgressMonitor());

		// Do check
		assertNotNull("resource created", model1.getResouce());
		assertNotNull("resource created", model2.getResouce());
		assertEquals("Resource is shared", model1.getResouce(), model2.getResouce());
		// Check weither it is save
		assertFalse("Resource is saved", model1.getResouce().isModified());
		assertFalse("Resource is saved", model2.getResouce().isModified());
		// TODO : check that it is saved only once !

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#getModelRoot()}.
	 */
	@Test
	public void testGetModelRoot() {
		String model1Key = "ecore";
		String model2Key = "genmodel";

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		final TransactionalEditingDomain transactionalEditingDomain = modelSet.getTransactionalEditingDomain();

		// Call creates
		modelSet.createsModels(model1File);
		// Add elements in both model
		EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		final Command addModelRootCommandp1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model1.getResouce(), p1));
		addModelRootCommandp1.execute();

		EClass c1 = EcoreFactory.eINSTANCE.createEClass();
		final Command addModelRootCommandc1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model2.getResouce(), c1));
		addModelRootCommandc1.execute();

		// Do check
		assertEquals("root found", p1, model1.getModelRoot());
		assertEquals("root found", c1, model2.getModelRoot());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#getModelRoot()}.
	 */
	@Test
	public void testGetModelRoots() {
		String model1Key = "ecore";
		String model2Key = "genmodel";

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		final TransactionalEditingDomain transactionalEditingDomain = modelSet.getTransactionalEditingDomain();

		// Call creates
		modelSet.createsModels(model1File);
		// Add elements in both model
		EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		final Command addModelRootCommandp1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model1.getResouce(), p1));
		addModelRootCommandp1.execute();
		EPackage p2 = EcoreFactory.eINSTANCE.createEPackage();
		final Command addModelRootCommandp2 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model1.getResouce(), p2));
		addModelRootCommandp2.execute();
		EPackage p3 = EcoreFactory.eINSTANCE.createEPackage();
		final Command addModelRootCommandp3 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model1.getResouce(), p3));
		addModelRootCommandp3.execute();

		EClass c1 = EcoreFactory.eINSTANCE.createEClass();
		final Command addModelRootCommandc1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model2.getResouce(), c1));
		addModelRootCommandc1.execute();
		EClass c2 = EcoreFactory.eINSTANCE.createEClass();
		final Command addModelRootCommandc2 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model2.getResouce(), c2));
		addModelRootCommandc2.execute();
		EClass c3 = EcoreFactory.eINSTANCE.createEClass();
		final Command addModelRootCommandc3 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model2.getResouce(), c3));
		addModelRootCommandc3.execute();

		// Do check
		List<EPackage> lp = model1.getModelRoots();
		List<EClass> lc = model2.getModelRoots();

		assertNotNull("list exist", lp);
		assertEquals("list size", 3, lp.size());
		assertTrue("element found", lp.contains(p1));
		assertTrue("element found", lp.contains(p2));
		assertTrue("element found", lp.contains(p3));

		assertNotNull("list exist", lc);
		assertEquals("list size", 3, lc.size());
		assertTrue("element found", lc.contains(c1));
		assertTrue("element found", lc.contains(c2));
		assertTrue("element found", lc.contains(c3));
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#addModelRoot(org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testAddModelRoot() {
		String model1Key = "ecore";
		String model2Key = "genmodel";

		FakeModelWithSharedResource<EPackage> model1 = new FakeModelWithSharedResource<>(ModelKind.master, model1Key, EPackage.class);
		FakeModelWithSharedResource<EClass> model2 = new FakeModelWithSharedResource<>(model2Key, EClass.class);

		ModelSet modelSet = houseKeeper.cleanUpLater(new ModelSet());
		modelSet.registerModel(model1);
		modelSet.registerModel(model2);

		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.infra.core");

		IFile model1File = p.getFile("tmp/model1." + model1Key);

		final TransactionalEditingDomain transactionalEditingDomain = modelSet.getTransactionalEditingDomain();

		// Call creates
		modelSet.createsModels(model1File);
		// Add elements in both model
		EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		final Command addModelRootCommandp1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model1.getResouce(), p1));
		addModelRootCommandp1.execute();

		EClass c1 = EcoreFactory.eINSTANCE.createEClass();
		final Command addModelRootCommandc1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, model2.getResouce(), c1));
		addModelRootCommandc1.execute();

		// Do check
		assertTrue("model contain element", model1.getResouce().getContents().contains(p1));
		assertTrue("model contain element", model2.getResouce().getContents().contains(c1));
	}

	/**
	 * This allows to add root to resource by command.
	 */
	private class AddToResourceCommand extends AbstractTransactionalCommand {

		/**
		 * The resource.
		 */
		private final Resource resource;

		/**
		 * The object to add to the resource.
		 */
		private final EObject toAdd;

		/**
		 * Constructor.
		 *
		 * @param domain
		 *            The editing domain.
		 * @param resource
		 *            the resource.
		 * @param toAdd
		 *            the object to add to the resource.
		 */
		public AddToResourceCommand(final TransactionalEditingDomain domain, final Resource resource, final EObject toAdd) {
			super(domain, "Add an object to a resource", null);
			this.resource = resource;
			this.toAdd = toAdd;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			if (!getEditingDomain().isReadOnly(resource)) {
				this.resource.getContents().add(this.toAdd);
			}
			return CommandResult.newOKCommandResult();
		}
	}
}
