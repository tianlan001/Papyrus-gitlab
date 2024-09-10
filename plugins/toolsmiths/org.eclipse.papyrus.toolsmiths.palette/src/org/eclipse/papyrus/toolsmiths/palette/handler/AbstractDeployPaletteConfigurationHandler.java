/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Permits to display open diagram editor id
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.common.core.service.ProviderPriority;
import org.eclipse.gmf.runtime.common.ui.services.editor.EditorService;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PaletteUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * @author Remi Schnekenburger
 */
public abstract class AbstractDeployPaletteConfigurationHandler extends AbstractHandler {

	public static class UpdateContentDialog extends TrayDialog {

		/** the palette name */
		public String paletteName;

		/** the provider priority */
		public ProviderPriority priority;

		/** the editor id */
		public String editorID;

		/** list of required profiles */
		public String requiredProfiles;

		/** file name */
		public String fileName;

		/** the name field */
		private Text nameText;

		/** the editor filed as a combo */
		private Combo editorIdCombo;

		/** the profiles text field */
		private Text profilesText;

		/** combo for priority */
		private CCombo priorityCombo;


		/**
		 * Constructor.
		 * 
		 * @param shell
		 *            the shell
		 * @param priority
		 *            the priority
		 * @param editorID
		 *            the editor id
		 * @param requiredProfiles
		 *            the required profiles
		 */
		protected UpdateContentDialog(final Shell shell, final String fileName, final String paletteName, final ProviderPriority priority, final String editorID, final Set<String> requiredProfiles) {
			super(shell);
			this.fileName = fileName;
			this.paletteName = paletteName;
			this.priority = priority;
			this.editorID = editorID;
			if (requiredProfiles != null) {
				this.requiredProfiles = PaletteUtil.getSerializedProfileList(requiredProfiles);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void configureShell(final Shell newShell) {
			super.configureShell(newShell);
			if (newShell != null) {
				newShell.setText(Messages.AbstractDeployPaletteConfigurationHandler_ConfigureDeploymentOfThePalette);
				newShell.setSize(600, 400);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected Control createDialogArea(final Composite parent) {
			Composite superComposite = (Composite) super.createDialogArea(parent);
			Composite composite = new Composite(superComposite, SWT.NONE);
			composite.setLayout(new GridLayout(2, false));
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			// new grid data will all necessary info
			Label nameLabel = new Label(composite, SWT.NONE);
			nameLabel.setText(Messages.AbstractDeployPaletteConfigurationHandler_Identifier);
			nameText = new Text(composite, SWT.BORDER);
			if (paletteName == null) {
				paletteName = fileName;
			}
			nameText.setText(paletteName);
			nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

			Label priorityLabel = new Label(composite, SWT.NONE);
			priorityLabel.setText(Messages.AbstractDeployPaletteConfigurationHandler_Priority);
			priorityCombo = new CCombo(composite, SWT.BORDER);
			priorityCombo.setEditable(false);
			priorityCombo.setItems(new String[] { ProviderPriority.LOWEST.getName(), ProviderPriority.LOW.getName(), ProviderPriority.MEDIUM.getName(), ProviderPriority.HIGH.getName(), ProviderPriority.HIGHEST.getName() });
			if (ProviderPriority.LOWEST.equals(priority)) {
				priorityCombo.select(0);
			} else if (ProviderPriority.LOW.equals(priority)) {
				priorityCombo.select(1);
			} else if (ProviderPriority.MEDIUM.equals(priority)) {
				priorityCombo.select(2);
			} else if (ProviderPriority.HIGH.equals(priority)) {
				priorityCombo.select(3);
			} else if (ProviderPriority.HIGHEST.equals(priority)) {
				priorityCombo.select(4);
			} else {
				priorityCombo.select(2); // default = medium
			}
			priorityCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

			Label editorLabel = new Label(composite, SWT.NONE);
			editorLabel.setText(Messages.AbstractDeployPaletteConfigurationHandler_Editor);
			editorIdCombo = new Combo(composite, SWT.BORDER);
			if (editorID == null) {
				editorID = ""; //$NON-NLS-1$
			}
			editorIdCombo.setItems(findOpenedDiagramEditor().toArray(new String[] {}));
			editorIdCombo.setText(editorID);
			editorIdCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

			// list of profiles
			Label profilesLabel = new Label(composite, SWT.NONE);
			profilesLabel.setText(Messages.AbstractDeployPaletteConfigurationHandler_Profiles);
			profilesText = new Text(composite, SWT.BORDER);
			if (requiredProfiles != null) {
				profilesText.setText(requiredProfiles);
			}
			profilesText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

			return superComposite;
		}

		/**
		 * Get the list of id of opened diagram editor
		 * 
		 * @return the list of opened diagram editor
		 */
		public static List<String> findOpenedDiagramEditor() {

			// The list of diagram id
			List<String> ids = new ArrayList<String>();

			// The list of open editor part
			List<?> diagramEditors = EditorService.getInstance().getRegisteredEditorParts();

			// Filter it and fill the list with contribution id of each DiagramEditor
			diagramEditors.stream()
					.filter(diagram -> diagram instanceof DiagramEditor)
					.forEach(diagram -> {
						ids.add(((DiagramEditor) diagram).getContributorId());
					});

			return ids;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void okPressed() {
			// updates values
			if (null != nameText && !nameText.isDisposed()) {
				paletteName = nameText.getText();
			}

			if (null != editorIdCombo && !editorIdCombo.isDisposed()) {
				editorID = editorIdCombo.getText();
			}

			if (null != priorityCombo && !priorityCombo.isDisposed()) {
				priority = ProviderPriority.parse(priorityCombo.getText());
			}

			if (null != profilesText && !profilesText.isDisposed()) {
				requiredProfiles = profilesText.getText();
			}

			super.okPressed();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection) || currentSelection.isEmpty()) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) currentSelection;

		final Shell activeShell = HandlerUtil.getActiveShell(event);


		doExecute(selection, activeShell, new NullProgressMonitor());
		return null;

	}

	protected void doExecute(final IStructuredSelection selection, final Shell activeShell, final IProgressMonitor monitor) {

		Iterator<?> selectionIterator = selection.iterator();

		MultiStatus result = new MultiStatus(Activator.ID, IStatus.OK, Messages.AbstractDeployPaletteConfigurationHandler_ThePaletteConfigurationHasBeenSuccessfullyDeployedAndActivated, null);

		while (selectionIterator.hasNext()) {
			Object selectedElement = selectionIterator.next();
			if (selectedElement instanceof IAdaptable) {
				IFile selectedFile = ((IAdaptable) selectedElement).getAdapter(IFile.class);
				if (selectedFile == null) {
					monitor.worked(1);
					result.add(new Status(IStatus.ERROR, Activator.ID, Messages.AbstractDeployPaletteConfigurationHandler_TheSelectedElementIsNotAFile));
					continue;
				}


				String fileName = selectedFile.getFullPath().removeFileExtension().lastSegment();
				monitor.subTask(Messages.AbstractDeployPaletteConfigurationHandler_Deploy + fileName);
				boolean alreadyDeployed = false;
				// retrieve info => open a dialog, filled by current opened editor ?
				ProviderPriority priority = getPriority(fileName);
				if (null == priority) {
					priority = ProviderPriority.MEDIUM;
				} else {
					alreadyDeployed = true;
				}

				String editorID = getEditorID(fileName);
				if (null != editorID) {
					alreadyDeployed = true;
				}

				String paletteName = getPaletteName(fileName);
				if (null != paletteName) {
					alreadyDeployed = true;
				}
				Set<String> requiredProfiles = getRequiredProfiles(fileName);
				if (requiredProfiles != null && requiredProfiles.size() > 0) {
					alreadyDeployed = true;
				}
				String path = selectedFile.getFullPath().toString();


				UpdateContentDialog dialog = new UpdateContentDialog(activeShell, fileName, paletteName, priority, editorID, requiredProfiles);
				int returnCode = dialog.open();
				if (Window.OK == returnCode) {
					boolean validUpdate = true;

					// update values from the editor
					if (dialog.paletteName != null && dialog.paletteName.length() > 0) {
						paletteName = dialog.paletteName;
					} else {
						validUpdate = false;
					}

					if (dialog.priority != null) {
						priority = dialog.priority;
					} else {
						validUpdate = false;
					}

					if (dialog.editorID != null && dialog.editorID.length() > 0) {
						editorID = dialog.editorID;
					} else {
						validUpdate = false;
					}

					if (dialog.requiredProfiles != null && dialog.requiredProfiles.length() > 0) {
						requiredProfiles = PaletteUtil.getProfileSetFromString(dialog.requiredProfiles);
					} else {
						requiredProfiles = Collections.emptySet();
					}

					if (validUpdate) {
						if (alreadyDeployed) {
							// update values and set visible again
							result.add(activatePalette(fileName, paletteName, path, priority, editorID, requiredProfiles));
						} else {
							result.add(deployPalette(fileName, paletteName, path, priority, editorID, requiredProfiles));
						}
					} else {
						result.add(new Status(IStatus.ERROR, Activator.ID, Messages.AbstractDeployPaletteConfigurationHandler_Error_ContentDialogNotValid));
					}
				} else {
					result.add(new Status(IStatus.ERROR, Activator.ID, Messages.AbstractDeployPaletteConfigurationHandler_Error_UserCancelDialog));
				}

			}
		}

		if (result.getChildren().length == 1) {
			if (result.isOK()) {
				MessageDialog.openInformation(activeShell, Messages.AbstractDeployPaletteConfigurationHandler_Success, result.getMessage());
			} else if (result.getSeverity() < IStatus.ERROR) { // Errors are already logged
				StatusManager.getManager().handle(result, StatusManager.SHOW);
			}
		} else { // Merge the result and specify an appropriate message based on the result
			if (result.isOK()) {
				MessageDialog.openInformation(activeShell, Messages.AbstractDeployPaletteConfigurationHandler_Success, result.getMessage());
			} else {
				MultiStatus actualResult = new MultiStatus(Activator.ID, result.getCode(), Messages.AbstractDeployPaletteConfigurationHandler_Error_SomeErrorsOccured, result.getException());
				actualResult.merge(result);
			}
		}
	}

	/**
	 * get the Required Profiles
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the required profiles
	 */
	protected Set<String> getRequiredProfiles(final String identifier) {
		return PapyrusPalettePreferences.getRequiredProfiles(identifier, getMemento());
	}

	/**
	 * get the Palette Name
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the Palette Name
	 */
	protected String getPaletteName(final String identifier) {
		return PapyrusPalettePreferences.getPaletteName(identifier, getMemento());
	}

	/**
	 * get the Editor ID
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the Editor ID
	 */
	protected String getEditorID(final String identifier) {
		return PapyrusPalettePreferences.getEditorID(identifier, getMemento());
	}

	/**
	 * get the priority
	 * 
	 * Warning. Can be <code>null</code>!
	 *
	 * @param identifier
	 *            the identifier
	 * @return the provider priority
	 */
	protected ProviderPriority getPriority(final String identifier) {
		return PapyrusPalettePreferences.getPalettePriority(identifier, getMemento());
	}

	/** get the xml memento */
	protected abstract XMLMemento getMemento();

	/**
	 * deploy the Palette
	 * 
	 * @param fileName
	 * @param paletteName
	 * @param path
	 * @param priority
	 * @param editorID
	 * @param requiredProfiles
	 * @return
	 */
	protected abstract IStatus deployPalette(final String fileName, final String paletteName, final String path, final ProviderPriority priority, final String editorID, final Set<String> requiredProfiles);

	/**
	 * activate the Palette
	 * 
	 * @param fileName
	 * @param paletteName
	 * @param path
	 * @param priority
	 * @param editorID
	 * @param requiredProfiles
	 * @return
	 */
	protected abstract IStatus activatePalette(final String fileName, final String paletteName, final String path, final ProviderPriority priority, final String editorID, final Set<String> requiredProfiles);

}
