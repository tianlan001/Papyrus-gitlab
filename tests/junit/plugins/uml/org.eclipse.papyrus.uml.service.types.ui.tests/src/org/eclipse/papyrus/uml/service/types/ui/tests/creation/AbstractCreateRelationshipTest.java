/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.ui.tests.creation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.newchild.SetTargetAndRelationshipCommand;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.auxtests.IAuxTest;
import org.junit.Assert;

/**
 * Abstract class for tests on relationsip creation
 */
public abstract class AbstractCreateRelationshipTest extends AbstractPapyrusTest {

	protected static final boolean RESULT_EXPECTED = true;
	protected static final boolean RESULT_NOT_EXPECTED = false;
	protected static final boolean CAN_CREATE = true;
	protected static final boolean CAN_NOT_CREATE = false;

	protected static TransactionalEditingDomain transactionalEditingDomain;
	protected static IMultiDiagramEditor openPapyrusEditor;

	/**
	 * Constructor.
	 *
	 */
	public AbstractCreateRelationshipTest() {
		super();
	}

	/**
	 * Runs the test on pure semantic creation of the relationship
	 * 
	 * @param container
	 *            the container of the link. It can be <code>null</code>
	 * @param source
	 *            the source of the link. It should not be <code>null</code>
	 * @param target
	 *            the target of the link. It can be <code>null</code>
	 * @param hintedType
	 *            the type of the link to create
	 * @param canCreate
	 *            indicates if the link can be created or not.
	 * @param resultExpected
	 *            indicates if there should be a command result of the request of creation or not.
	 * @param aux
	 *            auxiliary tests on the created element
	 * @throws Exception
	 *             exception thrown if anything goes wrong
	 */
	protected void runCreationRelationshipTest(EObject container, EObject source, EObject target, IHintedType hintedType, boolean canCreate, boolean resultExpected, IAuxTest aux) throws Exception {
		Assert.assertTrue("Editor should not be dirty before test", !openPapyrusEditor.isDirty());
		Command command = getCreateRelationshipCommand(container, source, target, hintedType, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (canCreate) {
			transactionalEditingDomain.getCommandStack().execute(command);
			Collection<?> commandResult = command.getResult();

			if (commandResult.isEmpty() && resultExpected) {
				Assert.fail("Command should have a non-empty result");
			}

			if (!commandResult.isEmpty() && !resultExpected) {
				Assert.fail("Command should have a empty result");
			}

			if (aux != null && !commandResult.isEmpty()) {
				aux.test(container, source, target, hintedType, commandResult);
			}

			if (resultExpected) {
				Assert.assertTrue("Editor should be dirty after executed command", openPapyrusEditor.isDirty());
				transactionalEditingDomain.getCommandStack().undo();
				Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
				transactionalEditingDomain.getCommandStack().redo();
				transactionalEditingDomain.getCommandStack().undo();
				// assert editor is not dirty
				Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
			}

			if (openPapyrusEditor.isDirty()) {
				transactionalEditingDomain.getCommandStack().undo();
			}
			Assert.assertTrue("Editor should not be dirty after test", !openPapyrusEditor.isDirty());
		}
	}

	/**
	 * Creates the relationship in the given owner element, between the source and target elements, undo and redo the action
	 *
	 * @param container
	 *            container of the new element
	 * @param source
	 *            the source of the relationship
	 * @param target
	 *            the target of the relationship
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 * 
	 * @return the command
	 */
	protected Command getCreateRelationshipCommand(EObject container, EObject source, EObject target, IHintedType hintedType, boolean canCreate) throws ServiceException {
		// getCommandprovider from any of the non null EObject
		TransactionalEditingDomain transactionalEditingDomain = null;

		if (source != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(source);
		} else if (target != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(target);
		} else if (container != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(container);
		} else {
			fail("impossible to run the test with null parameters");
		}

		ICommand command = buildWrappedRelationshipCommand(transactionalEditingDomain, hintedType, source, target, null);
		// test if the command is enable and compare with the canCreate parameter
		boolean canExecute = command.canExecute();
		if (canExecute) {

			if (!(command instanceof SetTargetAndRelationshipCommand)) {
				assertTrue("Command should be a SetTargetAndRelationshipCommand", command instanceof SetTargetAndRelationshipCommand);
			}

			// executable but was expected as not executable
			if (!canCreate) {
				fail("Creation command is executable but it was expected as not executable");
			} else {
				// command is executable, and it was expected to => run the creation
				Command emfCommand = new org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper(command);
				return emfCommand;
			}
		} else {
			if (canCreate) {
				fail("Creation command is not executable but it was expected to be executable");
			}
		}
		return null;
	}


	/**
	 * 
	 * @param ted
	 * @param elementType
	 * @param container
	 * @param target
	 * @param reference
	 * @return
	 */
	protected ICommand buildWrappedRelationshipCommand(TransactionalEditingDomain ted, IElementType elementType, EObject container, EObject target, EReference reference) {
		IClientContext context;
		try {
			context = TypeContext.getContext(ted);
		} catch (ServiceException e) {
			return null;
		}
		IElementEditService serviceProvider = ElementEditServiceUtils.getCommandProvider(elementType, context);
		ITreeSelectorDialog dialog = new TestTargetSelectionDialog(target);
		SetTargetAndRelationshipCommand createGMFCommand = new SetTargetAndRelationshipCommand(ted, "Create " + elementType.getDisplayName(), serviceProvider, reference, container, elementType, dialog);
		return createGMFCommand;
	}

	public static class TestTargetSelectionDialog implements ITreeSelectorDialog {
		private EObject target;

		/**
		 * Constructor.
		 *
		 * @param target
		 */
		public TestTargetSelectionDialog(EObject target) {
			this.target = target;
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setLabelProvider(org.eclipse.jface.viewers.ILabelProvider)
		 *
		 * @param provider
		 */
		@Override
		public void setLabelProvider(ILabelProvider provider) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setContentProvider(org.eclipse.jface.viewers.ITreeContentProvider)
		 *
		 * @param provider
		 */
		@Override
		public void setContentProvider(ITreeContentProvider provider) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setDescription(java.lang.String)
		 *
		 * @param description
		 */
		@Override
		public void setDescription(String description) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setInput(java.lang.Object)
		 *
		 * @param input
		 */
		@Override
		public void setInput(Object input) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setInitialElementSelections(java.util.List)
		 *
		 * @param selectedElements
		 */
		@Override
		public void setInitialElementSelections(List selectedElements) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#open()
		 *
		 * @return
		 */
		@Override
		public int open() {
			return Window.OK;
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#getResult()
		 *
		 * @return
		 */
		@Override
		public Object[] getResult() {
			return new Object[] { target };
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setTitle(java.lang.String)
		 *
		 * @param label
		 */
		@Override
		public void setTitle(String label) {
		}
	}

}