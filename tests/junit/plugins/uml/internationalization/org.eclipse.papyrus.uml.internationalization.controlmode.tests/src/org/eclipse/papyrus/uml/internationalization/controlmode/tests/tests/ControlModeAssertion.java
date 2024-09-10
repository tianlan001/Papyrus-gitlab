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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.controlmode.tests.tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.ModelExplorerUtils;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Assert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * The Class ControlModeRunnableAssertion.
 */
public abstract class ControlModeAssertion {
	
	public static final String COMMAND_ID = "org.eclipse.papyrus.infra.services.controlmode.createsubmodel"; //$NON-NLS-1$
	
	protected String message;
	
	protected List<Element> selectedElements;
	
	protected Model model;
	
	protected ModelExplorerView view;

	/**
	 * Constructor.
	 *
	 * @param assertioMessage
	 *            the assertion message
	 */
	public ControlModeAssertion(final Model model, final ModelExplorerView view) {
		message = "The handler for control mode is not enabled!";
		this.selectedElements = new ArrayList<Element>();
		this.model = model;
		this.view = view;
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

	private void selectElementToControl() {

		selectedElements = ImmutableList.copyOf(getElementsToSelectForControl());

		// Assert that this element is controlled
		ModelExplorerUtils.setSelectionInTheModelexplorer(view, Arrays.asList(getElementToControl()));
	}

	protected Iterable<? extends PackageableElement> getElementsToSelectForControl() {
		return Iterables.filter(model.getPackagedElements(), org.eclipse.uml2.uml.Package.class);
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
		Assert.assertNotEquals("The model and submodel are not in different resources", model, submodel);
	}
	
	protected abstract void save();

}