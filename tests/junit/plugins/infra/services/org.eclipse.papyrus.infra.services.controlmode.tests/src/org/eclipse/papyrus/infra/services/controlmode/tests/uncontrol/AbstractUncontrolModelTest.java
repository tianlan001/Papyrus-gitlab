/*******************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid <juan.cadavid@cea.fr> implementation
 *     Christian W. Damus (CEA) - bug 437217 - control-mode strategy changes interfere with later tests
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 459427
 *     Christian W. Damus - bugs 480209, 485220
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.controlmode.tests.Messages;
import org.eclipse.papyrus.infra.services.controlmode.tests.StrategyChooserFixture;
import org.eclipse.papyrus.infra.services.controlmode.util.ControlHelper;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveAndDirtyService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.ModelExplorerUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * The Class AbstractUncontrolModelTest.
 */
public abstract class AbstractUncontrolModelTest extends AbstractPapyrusTest {

	public static final String COMMAND_ID = "org.eclipse.papyrus.infra.services.controlmode.reintegratesubmodel"; //$NON-NLS-1$

	protected static ModelExplorerView view;

	protected List<PackageableElement> selectedElements;

	protected IMultiDiagramEditor editor = null;

	protected Model model;

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	public AbstractUncontrolModelTest() {
		super();
	}

	@Before
	public void setUp() {
		// Set the current resource loading strategy to the default
		houseKeeper.cleanUpLater(new StrategyChooserFixture(editorFixture.getServiceRegistry(), 0));

		openEditor();
	}

	/**
	 * Open the Papyrus Editor.
	 */
	protected void openEditor() {
		editor = editorFixture.open();
		view = editorFixture.getModelExplorerView();
		model = (Model) ModelExplorerUtils.getRootInModelExplorer(view);

	}

	/**
	 * Undo.
	 */
	protected void undo() {
		editorFixture.getEditingDomain().getCommandStack().undo();
	}

	/**
	 * Redo.
	 */
	protected void redo() {
		editorFixture.getEditingDomain().getCommandStack().redo();
	}

	/**
	 * Gets the URI file in project.
	 *
	 * @param file
	 *            the file
	 * @return the URI file in project
	 */
	protected URI getURIFileInProject(String file) {
		return URI.createPlatformResourceURI(editorFixture.getProject().getProject().getFile(file).getFullPath().toString(), true);

	}


	protected List<PackageableElement> getControlledElements() {
		List<PackageableElement> controlledElements = new ArrayList<PackageableElement>();
		for (PackageableElement packageableElement : model.getPackagedElements()) {
			if (packageableElement instanceof org.eclipse.uml2.uml.Package && ControlHelper.isRootControlledObject(packageableElement)) {
				controlledElements.add(packageableElement);
			}
		}

		return controlledElements;
	}


	/**
	 * Save.
	 * 
	 * @throws ServiceException
	 */
	protected void save() throws ServiceException {
		ISaveAndDirtyService saveService = editorFixture.getServiceRegistry().getService(ISaveAndDirtyService.class);
		saveService.doSave(new NullProgressMonitor());
	}



	/**
	 * The Class UncontrolModeRunnableAssertion.
	 */
	public class UncontrolModeAssertion {

		private String message;

		/**
		 * Constructor.
		 *
		 */
		public UncontrolModeAssertion() {
			this(Messages.UncontrolModelTest_4);
		}

		/**
		 * Constructor.
		 *
		 */
		public UncontrolModeAssertion(String assertionMessage) {
			message = assertionMessage;
		}



		public void assertUncontrol() {

			selectElementToUncontrol();
			assertBeforeUncontrol();
			uncontrol(HandlerUtils.getCommand(getCommandId()));
			assertBeforeSave();
			save();
			assertAfterSave();
		}

		protected String getCommandId() {
			return COMMAND_ID;
		}

		private void selectElementToUncontrol() {

			selectedElements = ImmutableList.copyOf(getElementsToSelectForUncontrol());

			// Assert that this element is controlled
			ModelExplorerUtils.setSelectionInTheModelexplorer(view, Arrays.asList(getElementToUnControl()));
		}

		protected Iterable<? extends PackageableElement> getElementsToSelectForUncontrol() {
			return Iterables.filter(model.getPackagedElements(), org.eclipse.uml2.uml.Package.class);
		}

		/**
		 * Assert before uncontrol.
		 */
		protected void assertBeforeUncontrol() {
			Element element = getElementToUnControl();
			Assert.assertNotNull(element);
			Assert.assertNotSame("The controlled submodel's resource equals its parent's", element.eContainer().eResource(), element.eResource());
			Assert.assertTrue(message, HandlerUtils.getActiveHandlerFor(getCommandId()).isEnabled());

		}


		protected void uncontrol(Command cmd) {
			try {
				HandlerUtils.executeCommand(cmd);
			} catch (Exception e) {
				fail(e.getMessage());
			}

		}


		/**
		 * Assert before save.
		 */
		protected void assertBeforeSave() {

		}


		/**
		 * Save.
		 */
		protected void save() {
			editorFixture.saveAll();
		}


		/**
		 * Assert after save.
		 */
		protected void assertAfterSave() {

			// Assert that the resource for the model and the submodel
			// are the same
			Element element = getElementToUnControl();
			Assert.assertSame(Messages.AbstractUncontrolModelTest_1, element.eContainer().eResource(), element.eResource());

		}


		/**
		 * @param submodel
		 */
		protected Element getElementToUnControl() {
			return selectedElements.get(0);
		}

	}
}
