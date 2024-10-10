/*****************************************************************************
 * Copyright (c) 2024 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.architecture;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand;
import org.eclipse.sirius.diagram.DSemanticDiagram;

/**
 * Dummy Command required to register the EEF property view into the Papyrus architecture framework
 */
public class CreateDummyEEFDiagramEditorCommand implements ICreateSiriusDiagramEditorCommand{

	/**
	 * @see org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String, org.eclipse.emf.ecore.EObject, boolean)
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param open
	 * @return
	 */
	
	@Override
	public DSemanticDiagram execute(ViewPrototype prototype, String name, EObject semanticContext, boolean open) {
		//nothing to do
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean)
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param graphicalContext
	 * @param open
	 * @return
	 */
	
	@Override
	public DSemanticDiagram execute(ViewPrototype prototype, String name, EObject semanticContext, EObject graphicalContext, boolean open) {
		//nothing to do
		return null;
	}

}
