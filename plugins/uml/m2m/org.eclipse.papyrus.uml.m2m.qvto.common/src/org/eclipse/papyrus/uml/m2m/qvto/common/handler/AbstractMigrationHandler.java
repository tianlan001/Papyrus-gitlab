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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.properties.ui.creation.PropertyEditorFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.IImportTransformationLauncher;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Vincent Lorenzo
 *         Abstract handler for Migration action
 */
public abstract class AbstractMigrationHandler extends AbstractHandler {

	/**
	 * the extensions of the files to import
	 */
	private Set<String> extensionOfFilesToImport;

	/**
	 * 
	 * Constructor.
	 *
	 * @param acceptedFileExtension
	 *            the extension of the files to import
	 */
	protected AbstractMigrationHandler(final Set<String> acceptedFileExtension) {
		this.extensionOfFilesToImport = acceptedFileExtension;
	}

	/**
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty()) {
			return null;
		}

		Set<IFile> filesToImport = new HashSet<IFile>();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Iterator<?> selectionIterator = structuredSelection.iterator();
			while (selectionIterator.hasNext()) {
				Object selectedElement = selectionIterator.next();
				if (selectedElement instanceof IAdaptable) {
					IFile selectedFile = (IFile) ((IAdaptable) selectedElement).getAdapter(IFile.class);
					if (selectedFile == null) {
						continue;
					}

					String fileExtension = selectedFile.getFileExtension();
					if (extensionOfFilesToImport.contains(fileExtension)) {
						filesToImport.add(selectedFile);
					}
				}
			}
		}

		if (filesToImport.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			Iterator<String> iter = extensionOfFilesToImport.iterator();
			while (iter.hasNext()) {
				builder.append("*.");
				builder.append(iter.next());
				if (iter.hasNext()) {
					builder.append(", ");
				}
			}
			Activator.log.warn(NLS.bind("The selection doesn't contain any file with one of these extensions: {0}", builder.toString()));
		} else {
			importFiles(filesToImport, event);
		}

		return null;
	}


	public void importFiles(Set<IFile> selectedFiles, ExecutionEvent event) {
		ThreadConfig config = getTransformationParameters(event);

		if (config == null) {
			return;
		}


		// The Event's control is (or may be) a Context Menu, which will be disposed soon: retrieve its own parent instead (The main Window), if it has one.
		Control baseControl = HandlerUtil.getActiveShell(event);
		if (baseControl != null && !baseControl.isDisposed() && baseControl.getParent() != null) {
			baseControl = baseControl.getParent();
		}

		// On some platforms, it seems that the ActiveShell (Context Menu) may already be disposed (Bug 455011). Use the Active Workbench Window directly
		if (baseControl == null || baseControl.isDisposed()) {
			baseControl = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		}

		runTransformation(config, baseControl, selectedFiles);

	}

	protected void runTransformation(final ThreadConfig config, final Control baseControl, final Set<IFile> selectedFiles) {
		List<URI> urisToImport = new LinkedList<URI>();

		for (IFile selectedFile : selectedFiles) {
			URI uri = URI.createPlatformResourceURI(selectedFile.getFullPath().toString(), true);
			urisToImport.add(uri);
		}
		runTransformation(config, baseControl, urisToImport);
	}

	protected void runTransformation(final ThreadConfig config, final Control baseControl, final List<URI> urisToImport) {
		IImportTransformationLauncher launcher = createImportTransformationLauncher(config, baseControl);
		launcher.run(urisToImport);
	}


	/**
	 * 
	 * @param config
	 *            the configuration used to launch the import
	 * @param baseControl
	 * 
	 * @return
	 * 		the launcher of the import transformation
	 */
	protected abstract IImportTransformationLauncher createImportTransformationLauncher(final ThreadConfig config, final Control baseControl);

	public ThreadConfig getTransformationParameters(ExecutionEvent event) {
		ThreadConfig config = createConfigParameters();

		Shell activeShell = HandlerUtil.getActiveShell(event);

		final AtomicBoolean okPressed = new AtomicBoolean(true);
		PropertyEditorFactory factory = new PropertyEditorFactory() {
			@Override
			public String getEditionDialogTitle(Object objectToEdit) {
				return "Transformation parameters";
			}

			@Override
			protected void handleEditCancelled(Control widget, Object source) {
				okPressed.set(false);
				super.handleEditCancelled(widget, source);
			}
		};

		Object result = factory.edit(activeShell, config);

		if (!okPressed.get()) {
			return null;
		}

		// Result can be null, the source config, or a new config
		if (result instanceof ThreadConfig) {
			config = (ThreadConfig) result;
		}

		return config;
	}

	/**
	 * 
	 * @return
	 * 		This method return a new instance of the configuration to use for the migration
	 */
	protected ThreadConfig createConfigParameters() {
		return MigrationParametersFactory.eINSTANCE.createThreadConfig();
	}


}
