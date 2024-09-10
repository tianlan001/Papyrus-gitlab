/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractTestMultiLink;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.junit.Test;

public class TestMultiLink extends AbstractTestMultiLink {

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected String getProjectName() {
		return IClassDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IClassDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage dependency.
	 */
	@Test
	public void testToManageMultiDependency() {
		testToManageMultiLink(UMLElementTypes.Dependency_Edge, UMLElementTypes.Dependency_BranchEdge, true);
	}

	/**
	 * Test to manage multiassociation.
	 */
	@Test
	public void testToManageMultiAssociation() {
		testToManageMultiLink(UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE, UMLElementTypes.Association_BranchEdge, false);
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}
}
