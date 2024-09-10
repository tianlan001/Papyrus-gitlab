/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.uml.service.types.command;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;


public class GeneralizationSetCreationCommand extends CreateRelationshipCommand {

	/** The generalization setto create. */
	private GeneralizationSet generalizationSettoCreate;

	public GeneralizationSetCreationCommand(CreateRelationshipRequest request) {
		super(request);
	}

	/**
	 * The listener interface for receiving dialog events. The class that is interested in
	 * processing a dialog event implements this interface, and the object created with that class
	 * is registered with a component using the component's <code>addDialogListener<code> method. When
	 * the dialog event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see DialogEvent
	 */
	private class DialogListener implements Listener {

		/** The abutton ok. */
		private Button abuttonOk;

		/** The acombo. */
		private List acombo;

		/** The ageneralization set. */
		private GeneralizationSet ageneralizationSet = null;

		/** The ageneralization set list. */
		private java.util.List<GeneralizationSet> ageneralizationSetList;

		/**
		 * Instantiates a new dialog listener.
		 *
		 * @param generalizationSetList
		 *            the generalization set list
		 * @param combo
		 *            the combo
		 * @param buttonOK
		 *            the button ok
		 */
		public DialogListener(java.util.List<GeneralizationSet> generalizationSetList, List combo, Button buttonOK) {
			this.ageneralizationSetList = generalizationSetList;
			this.acombo = combo;
			this.abuttonOk = buttonOK;
		}

		/**
		 * Gets the result.
		 *
		 * @return the result
		 */
		public GeneralizationSet getResult() {
			return ageneralizationSet;
		}

		/**
		 * {@inheritedDoc}
		 */
		@Override
		public void handleEvent(Event event) {
			if (event.widget.equals(abuttonOk)) {
				// look for selected button
				int selectedButtonIndex = 0;
				selectedButtonIndex = acombo.getSelectionIndex();
				if (selectedButtonIndex >= 0) {
					ageneralizationSet = ageneralizationSetList.get(selectedButtonIndex);
				}
				((Shell) abuttonOk.getParent()).close();
			}
		}
	}

	/**
	 * Launch dialog when a GeneralizationSet is created. I ask to the user if he would like to
	 * create a new semantic or reuse an existed semantic
	 *
	 * @param generalizationSetList
	 *            the generalization set list
	 * @param editingDomain
	 *            the editing domain
	 */
	private void launchDialog(final java.util.List<GeneralizationSet> generalizationSetList, TransactionalEditingDomain editingDomain) {
		// Thread myThread = new Thread(new Runnable() {
		// public void run() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				Display display = Display.getCurrent();// new Display();
				final Button[] radios = new Button[2];
				final Shell dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				dialog.setBackground(ColorConstants.white);
				GridLayout gridLayout = new GridLayout(2, false);
				gridLayout.verticalSpacing = 8;
				dialog.setLayout(gridLayout);
				dialog.setText(Messages.GeneralizationSetCreationCommand_0);
				// Line1: Proposition of a new GeneralizationSet
				Label text = new Label(dialog, SWT.CENTER);
				text.setText(Messages.GeneralizationSetCreationCommand_1);
				text.setBackground(ColorConstants.white);
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gridData.horizontalSpan = 1;
				text.setLayoutData(gridData);
				radios[0] = new Button(dialog, SWT.RADIO);
				radios[0].setBackground(ColorConstants.white);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 1;
				radios[0].setLayoutData(gridData);
				// Line2:
				text = new Label(dialog, SWT.CENTER);
				text.setBackground(ColorConstants.white);
				text.setText(Messages.GeneralizationSetCreationCommand_2);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gridData.horizontalSpan = 1;
				text.setLayoutData(gridData);
				radios[1] = new Button(dialog, SWT.RADIO);
				radios[1].setBackground(ColorConstants.white);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 1;
				radios[1].setLayoutData(gridData);
				// Line3..X: the combo
				final List list = new List(dialog, SWT.MULTI | SWT.BORDER);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 2;
				list.setLayoutData(gridData);
				for (int i = 0; i < generalizationSetList.size(); i++) {
					if (generalizationSetList.get(i).getLabel() != null) {
						list.add(generalizationSetList.get(i).getLabel());
					} else {
						list.add("GeneralizationSet" + i); //$NON-NLS-1$
					}
				}
				radios[0].addSelectionListener(new SelectionListener() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						list.setEnabled(!radios[0].getSelection());
					}
				});
				radios[1].setSelection(true);
				// button
				final org.eclipse.swt.widgets.Button buttonok = new Button(dialog, SWT.PUSH);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
				gridData.horizontalSpan = 1;
				gridData.horizontalAlignment = GridData.END;
				buttonok.setLayoutData(gridData);
				buttonok.setText(Messages.GeneralizationSetCreationCommand_4);
				// button Cancel
				Button cancel = new Button(dialog, SWT.PUSH);
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 1;
				gridData.horizontalAlignment = GridData.END;
				cancel.setLayoutData(gridData);
				cancel.setText(Messages.GeneralizationSetCreationCommand_5);
				// listener of button
				DialogListener listener = new DialogListener(generalizationSetList, list, buttonok);
				buttonok.addListener(SWT.Selection, listener);
				cancel.addListener(SWT.Selection, listener);
				dialog.pack();
				dialog.open();
				while (!dialog.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				generalizationSettoCreate = listener.getResult();
			}
		});
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Generalization source = (Generalization) getSource();
		Generalization target = (Generalization) getTarget();
		Package container = (Package) ((CreateRelationshipRequest) getRequest()).getContainer();

		final java.util.List<GeneralizationSet> generalizationSetList = new ArrayList<GeneralizationSet>(source.getGeneralizationSets());
		Iterator<GeneralizationSet> iterator = target.getGeneralizationSets().iterator();
		while (iterator.hasNext()) {
			GeneralizationSet currentGeneralizationSet = iterator.next();
			if (!generalizationSetList.contains(currentGeneralizationSet)) {
				generalizationSetList.add(currentGeneralizationSet);
			}
		}
		if (generalizationSetList.size() > 0) {
			launchDialog(generalizationSetList, getEditingDomain());
		}
		if (generalizationSettoCreate == null) {
			generalizationSettoCreate = UMLFactory.eINSTANCE.createGeneralizationSet();
			generalizationSettoCreate.setName("GeneralizationSet_" + source.getSpecific().getName() + "_" + target.getSpecific().getName()); //$NON-NLS-1$ //$NON-NLS-2$
			container.getPackagedElements().add(generalizationSettoCreate);
		}
		if (!generalizationSettoCreate.getGeneralizations().contains(source)) {
			generalizationSettoCreate.getGeneralizations().add(source);
		}
		if (!generalizationSettoCreate.getGeneralizations().contains(target)) {
			generalizationSettoCreate.getGeneralizations().add(target);
		}

		// Put the newly created element in the request so that the
		// 'after' commands have access to it.
		getCreateRequest().setNewElement(generalizationSettoCreate);

		return CommandResult.newOKCommandResult();
	}


}
