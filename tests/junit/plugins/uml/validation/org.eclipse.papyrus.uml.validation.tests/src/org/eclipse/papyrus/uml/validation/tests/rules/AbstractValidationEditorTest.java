/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.validation.tests.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.uml2.uml.Element;


/**
 * Abstract test for validat
 * import org.eclipse.uml2.uml.Element;ion rules
 */
public abstract class AbstractValidationEditorTest extends AbstractEditorTest {

	protected static final String PLATFORM_PLUGIN = "platform:/plugin/"; //$NON-NLS-1$
	
	protected static final String VALIDATE_COMMAND_ID = "org.eclipse.papyrus.validation.ValidateModelCommand"; //$NON-NLS-1$

	protected static final String PROJECT_PREFIX = "org.eclipse.testproject."; //$NON-NLS-1$

	protected static final String RESOURCES_PATH = "/resources/"; //$NON-NLS-1$

	protected static final String CAN_NOT_FIND_ELEMENT = "Can not find element %s in model %s"; //$NON-NLS-1$
	
	/** validation diagnostic */
	protected Diagnostic globalDiagnostic;

	/**
	 * find diagnostic by source
	 * (Should be unique per element if the source is correctly defined)
	 */
	public List<Diagnostic> findDiagnosticBySource(Diagnostic diagnostic, String source) {
		List<Diagnostic> foundDiagnostic = new ArrayList<Diagnostic>();
		List<Diagnostic> children = diagnostic.getChildren();
		if (source.equals(diagnostic.getSource())) {
			foundDiagnostic.add(diagnostic);
		}
		if (children != null && !children.isEmpty()) {
			for (Diagnostic diagnostic2 : children) {
				foundDiagnostic.addAll(findDiagnosticBySource(diagnostic2, source));
			}
		}
		return foundDiagnostic;
	}

	public List<Diagnostic> filterDiagnosticsByElement(List<Diagnostic> diagnostics, Element element) {
		List<Diagnostic> filteredDiagnostics = new ArrayList<Diagnostic>();
		for (Diagnostic diagnostic : diagnostics) {
			List<?> datas = diagnostic.getData();
			if (datas != null && !datas.isEmpty()) {
				// try to get first element. According to Diagnostic#getData() documentation : The first element is typically the object that is the primary source of the problem;
				Object o = datas.get(0);
				if (element.equals(o)) {
					filteredDiagnostics.add(diagnostic);
				}
			}
		}
		return filteredDiagnostics;
	}
	
	@Override
	protected String getSourcePath() {
		return RESOURCES_PATH;
	}
}
