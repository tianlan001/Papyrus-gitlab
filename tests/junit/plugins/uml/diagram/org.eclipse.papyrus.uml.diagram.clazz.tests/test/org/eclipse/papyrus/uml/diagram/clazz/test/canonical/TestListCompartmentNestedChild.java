/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 450944
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedClassForClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedClassForComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedClassForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedComponentForClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedComponentForComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedComponentForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedInterfaceForClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedInterfaceForComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedInterfaceForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestListCompartmentHelper;
import org.junit.Test;

/**
 *
 */
public class TestListCompartmentNestedChild extends AbstractPapyrusTestCase {

	private TestListCompartmentHelper myHelper;

	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getProjectName()
	 *
	 * @return
	 */
	@Override
	protected String getProjectName() {
		return IClassDiagramTestsConstants.PROJECT_NAME;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getFileName()
	 *
	 * @return
	 */
	@Override
	protected String getFileName() {
		return IClassDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testNestedComponentVsClass() throws Exception {
		getHelper().checkChildCreate(ClassEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPart.VISUAL_ID, NestedComponentForClassEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedComponentVsInterface() throws Exception {
		getHelper().checkChildCreate(InterfaceEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID, NestedComponentForInterfaceEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedComponentVsComponent() throws Exception {
		getHelper().checkChildCreate(ComponentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID, NestedComponentForComponentEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedClasstVsClass() throws Exception {
		getHelper().checkChildCreate(ClassEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPart.VISUAL_ID, NestedClassForClassEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedClassVsInterface() throws Exception {
		getHelper().checkChildCreate(InterfaceEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID, NestedClassForInterfaceEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedClassVsComponent() throws Exception {
		getHelper().checkChildCreate(ComponentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID, NestedClassForComponentEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedInterfacetVsClass() throws Exception {
		getHelper().checkChildCreate(ClassEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPart.VISUAL_ID, NestedInterfaceForClassEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedInterfaceVsInterface() throws Exception {
		getHelper().checkChildCreate(InterfaceEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID, NestedInterfaceForInterfaceEditPart.VISUAL_ID);
	}

	@Test
	public void testNestedInterfcaeVsComponent() throws Exception {
		getHelper().checkChildCreate(ComponentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID, NestedInterfaceForComponentEditPart.VISUAL_ID);
	}

	private TestListCompartmentHelper getHelper() {
		if (myHelper == null) {
			myHelper = new ClassDiagramListCompartmentTestHelper(getDiagramEditPart(), diagramEditor);
		}
		return myHelper;
	}
}
