/*****************************************************************************
 * Copyright (c) 2017 CEA LIST,ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *   Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to xpand/qvto
 *****************************************************************************/

package org.eclipse.papyrus.codegen;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.gmf.codegen.util.GeneratorWithXtend2;
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;

/**
 * Papyrus gmf generator.
 */
public class PapyrusGenerator extends GeneratorWithXtend2 {

	/**
	 * The Papyrus codegen Emitters.
	 */
	private final PapyrusCodegenEmitters emitters;

	/**
	 * The editor gen model.
	 */
	private GenEditorGenerator editorGen;

	/**
	 * Constructor.
	 */
	public PapyrusGenerator(final GenEditorGenerator genModel, final PapyrusCodegenEmitters codegenEmitters) {
		super(genModel, codegenEmitters);
		editorGen = genModel;
		emitters = codegenEmitters;
	}

	/**
	 * @see org.eclipse.papyrus.gmf.codegen.xtend.ui.handlers.GeneratorWithXtend2#customRun()
	 *
	 * @throws InterruptedException
	 * @throws UnexpectedBehaviourException
	 */
	@Override
	protected void customRun() throws InterruptedException, UnexpectedBehaviourException {
		super.customRun();
		// generatePaletteConfiguration(); // commented to disable paletteConfiguration model generation
	}

	/**
	 * Generate palette configuration model.
	 *
	 * @throws UnexpectedBehaviourException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("restriction")
	protected void generatePaletteConfiguration() throws UnexpectedBehaviourException, InterruptedException {
		if (null == editorGen.getDiagram().getPalette()) {
			return;
		}
		doGenerateFile(emitters.getPaletteConfigurationEmitter(), new Path(getRelativePath() + File.separator + editorGen.getModelID() + ".paletteconfiguration"), editorGen); //$NON-NLS-1$
	}

	/**
	 * @return The relative path of the model's resource.
	 */
	protected String getRelativePath() {
		String path = null;

		URI resourceURI = editorGen.eResource().getURI();
		if (resourceURI.isPlatformResource()) {
			String platformString = resourceURI.toPlatformString(true);
			IPath workspacePath = new Path(platformString);
			workspacePath = workspacePath.removeFirstSegments(1);
			workspacePath = workspacePath.removeLastSegments(1);
			path = workspacePath.toString();
		} else {
			path = "model";//$NON-NLS-1$
		}
		return path;
	}

}
