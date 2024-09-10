/******************************************************************************
 * Copyright (c) 2015, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to xpand/qvto
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;
import org.eclipse.papyrus.gmf.internal.common.codegen.CodeFormatterFactory;

public abstract class GeneratorWithXtend2 extends Generator {


	public GeneratorWithXtend2(GenEditorGenerator genModel, CodegenEmitters emitters) {
		super(genModel, emitters,  new BinaryEmitters(), CodeFormatterFactory.DEFAULT);
	}

	protected void customRun() throws InterruptedException, UnexpectedBehaviourException {
		try {
			super.customRun();
		} finally {
			hookGenerationCompleted();
		}
	}

	protected CodegenEmittersWithXtend2 getEmitters() {
		return (CodegenEmittersWithXtend2) myEmitters;
	}

	protected void hookGenerationCompleted() {
		getEmitters().disposeEmitters();
	}

}
