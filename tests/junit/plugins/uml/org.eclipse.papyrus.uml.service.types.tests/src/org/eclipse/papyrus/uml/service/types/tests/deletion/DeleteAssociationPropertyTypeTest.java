/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Francois LE FEVRE (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.deletion;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelUtils;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.helper.advice.PropertyHelperAdvice;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests the {@link PropertyHelperAdvice} that deletes an association and preserve the classifier property when changing the property type to null.
 * See bug 477724
 */
public class DeleteAssociationPropertyTypeTest extends AbstractPapyrusTest {

	/**
	 * The house keeper.
	 */
	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	/**
	 * The created project for the JUnit test.
	 */
	protected static IProject createProject;

	/**
	 * The file of the existing papyrus model to copy.
	 */
	protected static IFile copyPapyrusModel;

	/**
	 * The multi diagram editor of the papyrus project.
	 */
	protected static IMultiDiagramEditor openPapyrusEditor;

	/**
	 * The transactionnal editing domain.
	 */
	protected static TransactionalEditingDomain transactionalEditingDomain;

	/**
	 * The current model set used.
	 */
	protected static ModelSet modelset;

	/**
	 * The UML model.
	 */
	protected static UmlModel umlIModel;

	/**
	 * The root model of the papyrus project.
	 */
	protected static Model rootModel;


	private static Class class1;

	private static Class class2;
	
	private static Association association;
	
	private static Property class1PropertyClass2;

	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() throws Exception {

		// create Project
		createProject = houseKeeper.createProject("UMLServiceTypesTest");

		// import test model
		try {
			copyPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(createProject, Platform.getBundle("org.eclipse.papyrus.uml.service.types.tests"), "/resource/bug477724/", "AssociationPropertyTypeDeletion");
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// open project
		openPapyrusEditor = houseKeeper.openPapyrusEditor(copyPapyrusModel);

		transactionalEditingDomain = openPapyrusEditor.getAdapter(TransactionalEditingDomain.class);
		assertTrue("Impossible to init editing domain", transactionalEditingDomain instanceof TransactionalEditingDomain);

		// retrieve UML model from this editor
		try {
			modelset = ModelUtils.getModelSetChecked(openPapyrusEditor.getServicesRegistry());
			umlIModel = UmlUtils.getUmlModel(modelset);
			rootModel = (Model) umlIModel.lookupRoot();
		} catch (ServiceException e) {
			fail(e.getMessage());
		} catch (NotFoundException e) {
			fail(e.getMessage());
		} catch (ClassCastException e) {
			fail(e.getMessage());
		}
		try {
			initExistingElements();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// force load of the element type registry. Will need to load in in UI thread because of some advice in communication diag: see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				ElementTypeSetConfigurationRegistry registry = ElementTypeSetConfigurationRegistry.getInstance();
				Assert.assertNotNull("Registry should not be null after init", registry);
				Assert.assertNotNull("Association element type should not be null", UMLElementTypes.ASSOCIATION);
			}
		});
	}

	/**
	 * Init fields corresponding to element in the test model
	 */
	private static void initExistingElements() throws Exception {
		// get the elements of the model
		class1 = (Class) rootModel.getOwnedMember("Class1"); //$NON-NLS-1$
		class2 = (Class) rootModel.getOwnedMember("Class2"); //$NON-NLS-1$
		class1PropertyClass2 = class1.getAttribute("class2", null); //$NON-NLS-1$
		association = (Association) rootModel.getOwnedMember("myAssociation"); //$NON-NLS-1$

		Assert.assertNotNull(class1);
		Assert.assertNotNull(class2);
		Assert.assertNotNull(association);
		Assert.assertNotNull(class1PropertyClass2);

	}
	
	
	/**
	 * Test if the deletion of type of a property with an association, delete this association and keep the property
	 * 
	 * @throws InterruptedException
	 *             The InterruptedException caught exception.
	 * @throws RollbackException
	 *             The RollbackException caught exception.
	 */
	@Test
	public void testDeletion() throws InterruptedException, RollbackException {
		TransactionalEditingDomain domain = openPapyrusEditor.getAdapter(TransactionalEditingDomain.class);
		assertTrue("Impossible to init editing domain", transactionalEditingDomain instanceof TransactionalEditingDomain);
		
		final IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(rootModel);
		
		//Create the request
		SetRequest validRequest = new SetRequest(domain, class1PropertyClass2, UMLPackage.eINSTANCE.getTypedElement_Type() , null);
		
		//Ask the command linked to the request, should go through elementtype
		final ICommand editCommand = commandService.getEditCommand(validRequest);
		
		//Execute the command
		InternalTransactionalEditingDomain editingDomain = (InternalTransactionalEditingDomain) TransactionUtil.getEditingDomain(rootModel);
		InternalTransaction startTransaction = editingDomain.startTransaction(false, null);
		transactionalEditingDomain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(editCommand));
		startTransaction.commit();
		
		//Test effective deletion of association
		association = (Association) rootModel.getOwnedMember("myAssociation"); //$NON-NLS-1$
		Assert.assertNull(association);
		Assert.assertNull(class1.getAttribute("class2", null).getType());
		
		

	}
}
