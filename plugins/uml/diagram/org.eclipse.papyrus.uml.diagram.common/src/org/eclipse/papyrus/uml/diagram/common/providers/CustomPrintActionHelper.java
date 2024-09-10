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
 * 		IBM Corporation - initial API and implementation
 * 		Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 424803
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.Collections;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.printing.internal.l10n.DiagramUIPrintingMessages;
import org.eclipse.gmf.runtime.diagram.ui.printing.internal.util.SWTDiagramPrinter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.ui.IEditorPart;

/**
 * Provides basic printing functionality. This does a print from a default print
 * dialog.
 * Customization of <code>DefaultPrintActionHelper</code>
 * 
 * @since 3.0
 *
 */
public class CustomPrintActionHelper {

	/**
	 * Prints the diagram after a default print dialog is opened for the user.
	 *
	 * @param editorPart
	 *            the IEditorPart containing the diagram to print
	 * @param diagramPrinter
	 *            the diagram printer that does the work of actually printing
	 *            the diagrams
	 */
	public static void doRun(final IEditorPart editorPart, final SWTDiagramPrinter diagramPrinter) {
		// print the editor contents.
		final PrintDialog dialog = new PrintDialog(editorPart.getSite().getShell(), SWT.NULL);
		final PrinterData data = dialog.open();

		if (null != data) {
			boolean isPrintToFit = MessageDialog.openQuestion(null,
					DiagramUIPrintingMessages.Print_MessageDialogTitle,
					DiagramUIPrintingMessages.Print_MessageDialogMessage);

			final Printer printer = new Printer(data);

			diagramPrinter.setPrinter(printer);
			diagramPrinter.setDisplayDPI(dialog.getParent().getDisplay().getDPI());

			DiagramEditPart diagramEditPart = null;
			if (editorPart instanceof IDiagramWorkbenchPart) {
				diagramEditPart = ((IDiagramWorkbenchPart) editorPart).getDiagramEditPart();
			} else if (editorPart instanceof IMultiDiagramEditor) {
				IEditorPart activeEditor = ((IMultiDiagramEditor) editorPart).getActiveEditor();
				if (activeEditor instanceof IDiagramWorkbenchPart) {
					diagramEditPart = ((IDiagramWorkbenchPart) activeEditor).getDiagramEditPart();
				}
			}

			if (null != diagramEditPart) {
				diagramPrinter.setDiagrams(Collections.singletonList(diagramEditPart.getDiagramView().getDiagram()));

				if (isPrintToFit) {
					diagramPrinter.setColumns(1);
					diagramPrinter.setRows(1);
				} else {
					diagramPrinter.setScaledPercent(100);
				}

				diagramPrinter.run();
				printer.dispose();
			}
		}
	}

	/**
	 * Private constructor prevents instantiation
	 */
	private CustomPrintActionHelper() {
		// utility class
	}

}
