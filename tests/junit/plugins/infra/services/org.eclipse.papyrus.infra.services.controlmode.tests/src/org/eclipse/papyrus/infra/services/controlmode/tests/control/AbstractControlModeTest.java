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
 *     Christian W. Damus - bug 485220
 *     
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.ParameterValuesException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.services.controlmode.commands.ControlModeCommandParameterValues;
import org.eclipse.papyrus.infra.services.controlmode.commands.ResourceLocationParameterValues;
import org.eclipse.papyrus.infra.services.controlmode.handler.ControlCommandHandler;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

public abstract class AbstractControlModeTest extends AbstractPapyrusTest {

	public static final String COMMAND_ID = "org.eclipse.papyrus.infra.services.controlmode.createsubmodel"; //$NON-NLS-1$

	/** The house keeper. */
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	/** The editor fixture. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	protected IMultiDiagramEditor editor = null;

	protected Model model;

	protected List<Element> selectedElements;

	protected static ModelExplorerView view;

	protected static IFile modelFile;

	public AbstractControlModeTest() {
		super();
	}


	@Before
	public void setUp() throws Exception {

		// Set the current resource loading strategy to the default
		houseKeeper.cleanUpLater(new StrategyChooserFixture(editorFixture.getServiceRegistry(), 0));

		openEditor();

	}

	/**
	 * Open editor.
	 */
	protected void openEditor() {
		editor = editorFixture.open();
		view = editorFixture.getModelExplorerView();
		model = (Model) ModelExplorerUtils.getRootInModelExplorer(view);

	}


	@After
	public void cleanUp() throws Exception {
		activateDialog();
		selectedElements = null;
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
	 * Gets the controlled elements.
	 *
	 * @return the controlled elements
	 */
	protected List<Element> getControlledElements() {
		List<Element> controlledElements = new ArrayList<Element>();
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
	 * @throws Exception
	 */
	protected void save() throws Exception {
		ISaveAndDirtyService saveService = editorFixture.getServiceRegistry().getService(ISaveAndDirtyService.class);
		assertTrue("Nothing to save", saveService.isDirty());
		saveService.doSave(new NullProgressMonitor());
	}

	/**
	 * @throws NotDefinedException
	 * @throws ParameterValuesException
	 */
	protected void desactivateDialog() throws NotDefinedException, ParameterValuesException {
		IParameter dialogParameter = HandlerUtils.getCommand(COMMAND_ID).getParameter(ControlCommandHandler.CONTROLMODE_USE_DIALOG_PARAMETER);
		ControlModeCommandParameterValues controlModePlatformValues = (ControlModeCommandParameterValues) dialogParameter.getValues();
		controlModePlatformValues.put("showDialog", false);
	}

	/**
	 * @throws NotDefinedException
	 * @throws ParameterValuesException
	 */
	protected void activateDialog() throws NotDefinedException, ParameterValuesException {
		IParameter dialogParameter = HandlerUtils.getCommand(COMMAND_ID).getParameter(ControlCommandHandler.CONTROLMODE_USE_DIALOG_PARAMETER);
		ControlModeCommandParameterValues controlModePlatformValues = (ControlModeCommandParameterValues) dialogParameter.getValues();
		controlModePlatformValues.put("showDialog", true);
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

	/**
	 * Sets the submoddel location.
	 *
	 * @param file
	 *            the new submoddel location
	 * @throws Exception
	 * @throws Exception
	 */
	protected void setSubmodelLocation(String file) throws Exception {
		URI resourceURi = getURIFileInProject(file);
		IParameter handlerParameter = HandlerUtils.getCommand(COMMAND_ID).getParameter(ResourceLocationParameterValues.ID);
		ResourceLocationParameterValues parameter = (ResourceLocationParameterValues) handlerParameter.getValues();
		parameter.setResourceLocation(resourceURi);

	}


	/**
	 * The Class ControlModeRunnableAssertion.
	 */
	public class ControlModeAssertion {
		protected String message;

		/**
		 * Constructor.
		 *
		 * @param assertioMessage
		 *            the assertio message
		 */
		public ControlModeAssertion(String assertioMessage) {
			message = assertioMessage;
		}

		/**
		 * @see java.lang.Runnable#run()
		 *
		 */
		public void testControl() {
			selectElementToControl();
			assertBeforeControl();
			control(HandlerUtils.getCommand(getCommandId()));
			assertBeforeSave();
			save();
			assertAfterSave();
		}

		protected String getCommandId() {
			return COMMAND_ID;
		}

		/**
		 * Assert after save.
		 */
		protected void assertAfterSave() {

		}

		/**
		 * Select the first subpackage of the root package in the model explorer
		 * 
		 */
		private void selectElementToControl() {

			selectedElements = new ArrayList<Element>();
			for (PackageableElement packageableElement : model.getPackagedElements()) {
				if (packageableElement instanceof org.eclipse.uml2.uml.Package) {
					selectedElements.add(packageableElement);
				}
			}
			ModelExplorerUtils.setSelectionInTheModelexplorer(view, Arrays.asList(getElementToControl()));
		}

		/**
		 * Gets the selected elements.
		 *
		 * @return the selected elements
		 */
		protected List<Element> getSelectedElements() {
			return selectedElements;
		}

		/**
		 * Gets the elements to control.
		 *
		 * @return the elements to control
		 */
		protected Element getElementToControl() {
			return selectedElements.get(0);
		}

		/**
		 * Assert before control.
		 */
		protected void assertBeforeControl() {
			Assert.assertTrue(message, HandlerUtils.getActiveHandlerFor(COMMAND_ID).isEnabled());
		}

		/**
		 * Run control mode command on selected elements.
		 * 
		 * @param cmd
		 */
		private void control(Command cmd) {
			try {
				HandlerUtils.executeCommand(cmd);

			} catch (Throwable e) {
				String s = "*********************************************\n";
				do {
					StackTraceElement[] stackTrace = e.getStackTrace();
					s += e.getLocalizedMessage() + "\n";
					for (StackTraceElement stackTraceElement : stackTrace) {
						s += stackTraceElement.toString() + "\n";
					}
					s += "-------------------------------------------------\n";
				} while ((e = e.getCause()) != null);
				fail(s);

			}


		}

		protected void assertBeforeSave() {
			// Execute save
			// Assert that the parent package is controlled
			Element submodel = getElementToControl();
			Assert.assertNotEquals("The controlled submodel's resource equals its parent's", model.eResource(), submodel.eResource());

			// Assert that the model and submodel belong to different
			// resources
			Assert.assertNotEquals(Messages.AbstractControlModeTest_1, model, submodel);
		}

		protected void save() {
			editorFixture.saveAll();

		}


	}




}
