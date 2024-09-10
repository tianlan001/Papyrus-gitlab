/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos Origin Integration, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.ui.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.onefile.internal.ui.Activator;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Utility methods for the UI
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class OneFileUIUtils {

	/**
	 * Check if the object is in an already opened editor
	 *
	 * @param inputElement
	 * @return
	 */
	public static IEditorPart isOpenInEditor(Object inputElement) {
		IEditorPart editor = findEditor(inputElement, false);
		if (editor != null) {
			return editor;
		}
		IEditorInput input = getEditorInput(inputElement);
		if (input != null) {

			IWorkbenchPage p = getActivePage();
			if (p != null) {
				return p.findEditor(input);
			}
		}
		return null;
	}

	/**
	 * Find an editor opening the input element
	 *
	 * @param inputElement
	 * @param activate
	 *            , if activate is true, once the editor is found it is
	 *            activated
	 * @return null if no editor is found
	 */
	private static IEditorPart findEditor(Object inputElement, boolean activate) {
		if (inputElement instanceof IPapyrusFile) {
			IPapyrusFile cu = (IPapyrusFile) inputElement;
			if (cu != null) {
				IWorkbenchPage page = getActivePage();
				for (IEditorReference ref : page.getEditorReferences()) {
					IEditorPart editor = ref.getEditor(false);
					if (editor != null) {
						IEditorInput editorInput;
						editorInput = editor.getEditorInput();
						if (cu.getMainFile().equals(editorInput.getAdapter(IFile.class))) {
							if (activate && page.getActivePart() != editor) {
								page.activate(editor);
							}
							return editor;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Open the editor corresponding to the inpur element
	 *
	 * @param inputElement
	 * @param activate
	 * @return
	 * @throws PartInitException
	 */
	public static IEditorPart openInEditor(Object inputElement, boolean activate) throws PartInitException {

		if (inputElement instanceof IFile) {
			return openInEditor((IFile) inputElement, activate);
		}
		IEditorPart editor = findEditor(inputElement, activate);
		if (editor != null) {
			return editor;
		}
		IEditorInput input = getEditorInput(inputElement);
		if (input == null) {
			throw new PartInitException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "not found"));
		}
		return openInEditor(input, getEditorID(input), activate);
	}

	/**
	 * Get the editor input for the given element
	 *
	 * @param inputElement
	 * @return
	 */
	private static IEditorInput getEditorInput(Object inputElement) {
		if (inputElement instanceof IFile) {
			IFile file = (IFile) inputElement;
			return new FileEditorInput(file);
		}
		return null;
	}

	/**
	 * Get the editor id for an editor input
	 *
	 * @param input
	 * @return
	 * @throws PartInitException
	 */
	public static String getEditorID(IEditorInput input) throws PartInitException {

		Assert.isNotNull(input);

		IEditorDescriptor editorDescriptor;

		if (input instanceof IFileEditorInput) {
			editorDescriptor = IDE.getEditorDescriptor(((IFileEditorInput) input).getFile());
		} else {
			editorDescriptor = IDE.getEditorDescriptor(input.getName());
		}
		return editorDescriptor.getId();

	}

	/**
	 * @param file
	 * @param activate
	 * @return
	 * @throws PartInitException
	 */
	private static IEditorPart openInEditor(IFile file, boolean activate) throws PartInitException {

		if (file == null) {
			throw new PartInitException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "not found"));
		}

		IWorkbenchPage p = getActivePage();

		if (p == null) {
			throw new PartInitException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "not found"));
		}

		IEditorPart editorPart = IDE.openEditor(p, file, activate);

		return editorPart;

	}

	/**
	 * Get the active Page
	 *
	 * @return
	 */
	public static IWorkbenchPage getActivePage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return null;
		}
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		return activeWorkbenchWindow.getActivePage();
	}

	private static IEditorPart openInEditor(IEditorInput input, String editorID, boolean activate) throws PartInitException {
		Assert.isNotNull(input);
		Assert.isNotNull(editorID);
		IWorkbenchPage p = getActivePage();
		if (p == null) {
			throw new PartInitException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "not found"));
		}
		IEditorPart editorPart = p.openEditor(input, editorID, activate);
		return editorPart;

	}

}
