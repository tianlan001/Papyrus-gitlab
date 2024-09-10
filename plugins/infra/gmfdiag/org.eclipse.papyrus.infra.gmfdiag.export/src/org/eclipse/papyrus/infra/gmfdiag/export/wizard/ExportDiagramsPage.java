/*******************************************************************************
 * Copyright (c) 2011 AtoS
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Anass RADOUANI (AtoS)
 *    Fred Eckertson (Cerner) - fred.eckertson@cerner.com - Bug 502705
 *******************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.export.wizard;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.gmfdiag.export.actions.ExportComposite;
import org.eclipse.papyrus.infra.gmfdiag.export.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * wizard page to export all diagram from a Papyrus model
 */
public class ExportDiagramsPage extends WizardPage {

	private ExportComposite export;

	public ExportComposite getExport() {
		return export;
	}

	private final IResource outputDirectory;

	/**
	 * Create the wizard.
	 * @since 2.0
	 */
	public ExportDiagramsPage(IResource outputDirectory) {
		super(Messages.ExportDiagramsPage_0);
		this.outputDirectory = outputDirectory;
		setTitle(Messages.ExportDiagramsPage_0);
		setDescription(Messages.ExportDiagramsPage_2);
	}

	/**
	 * Create contents of the wizard.
	 *
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		export = new ExportComposite(parent, SWT.NONE);
		export.setOutputDirectory(outputDirectory);
		setControl(export);
	}
}
