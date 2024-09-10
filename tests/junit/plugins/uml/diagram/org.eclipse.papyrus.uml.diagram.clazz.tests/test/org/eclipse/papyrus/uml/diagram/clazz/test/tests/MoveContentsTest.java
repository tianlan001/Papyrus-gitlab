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
 *   Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * 
 * Used to test the deletion of the views of contents (Attributes, Operations, Receptions and NestedClassifiers) when it is moved.
 *
 */
@PluginResource("/model/MoveContents/model.di")
public class MoveContentsTest extends AbstractPapyrusTest {

	/** The name of the Class1. */
	private static final String NAME_CLASS_1 = "Class1"; //$NON-NLS-1$
	/** The name of the Class2. */
	private static final String NAME_CLASS_2 = "Class2"; //$NON-NLS-1$
	/** The name of the Class3. */
	private static final String NAME_CLASS_3 = "Class3"; //$NON-NLS-1$
	/** The name of the Class5. */
	private static final String NAME_CLASS_5 = "Class5"; //$NON-NLS-1$
	/** The name of the diagram used for testing. */
	private static final String NAME_DIAGRAM = "Class Diagram"; //$NON-NLS-1$

	/** The Papyrus editor fixture. */
	@Rule
	public final PapyrusEditorFixture papyrusEditorFixture = new PapyrusEditorFixture();

	/** The model of the fixture. */
	private Package model = null;

	/** The list of the contents of the element. */
	private List<EObject> contents = null;

	/** The diagram. */
	private Diagram diagram = null;

	@Before
	public void init() {
		model = papyrusEditorFixture.getModel();
		assertNotNull("The model cannot be null", model); //$NON-NLS-1$

		diagram = DiagramUtils.getNotationDiagram(papyrusEditorFixture.getModelSet(), NAME_DIAGRAM);
		assertNotNull(diagram);

		// Get the source element.
		EObject element = model.getMember(NAME_CLASS_3);
		assertTrue("The object must be a Class.", element instanceof Class); //$NON-NLS-1$

		// Get all the contents. The element contains 1 attribute, 1 operation, 1 reception and 6 nested classifiers.
		contents = new ArrayList<EObject>();
		contents.addAll(((Class) element).getAllAttributes());
		// 1 attribute
		assertTrue("The list must contains 1 element but was " + contents.size(), 1 == contents.size()); //$NON-NLS-1$
		contents.addAll(((Class) element).getAllOperations());
		// 1 Attribute + 1 Operation
		assertTrue("The list must contains 2 elements but was " + contents.size(), 2 == contents.size()); //$NON-NLS-1$
		contents.addAll(((Class) element).getOwnedReceptions());
		// 1 Attribute + 1 Operation + 1 Reception
		assertTrue("The list must contains 3 elements but was " + contents.size(), 3 == contents.size()); //$NON-NLS-1$
		contents.addAll(((Class) element).getNestedClassifiers());
		// 1 Attribute + 1 Operation + 1 Reception + 6 NestedClassifiers
		assertTrue("The list must contains 9 elements but was " + contents.size(), 9 == contents.size()); //$NON-NLS-1$

		papyrusEditorFixture.getPageManager().openPage(diagram);
		papyrusEditorFixture.flushDisplayEvents();
	}

	/**
	 * Test to move all the contents (Attributes, Operations and Nest Classifiers) of the class 'Class3' to 'Class5'.
	 * No inheritance exists between the classes.
	 */
	@Test
	public void testAllContentsNoInheritance() {
		// The target class is 'Class5'.
		NamedElement targetMember = model.getMember(NAME_CLASS_5);
		assertTrue("The object must be a Class.", targetMember instanceof Class); //$NON-NLS-1$

		// Retrieve the command to move the elements.
		ICommand command = getCommandMovedElements(contents, targetMember);
		assertNotNull("The command must not be null.", command); //$NON-NLS-1$

		papyrusEditorFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		for (EObject eObject : contents) {
			// Target class 'Class5' is a parent of Class1, the views must not be deleted
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			// Target class 'Class5' is not a parent of Class2, the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
			// Target class 'Class5' is not a parent of Class3, the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
		}

		papyrusEditorFixture.getEditingDomain().getCommandStack().undo();

		for (EObject eObject : contents) {
			// All the views are shown after the undo.
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView);//$NON-NLS-1$
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
		}

		papyrusEditorFixture.getEditingDomain().getCommandStack().redo();

		for (EObject eObject : contents) {
			// Target class 'Class5' is a parent of 'Class1', the views must not be deleted
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			// Target class 'Class5' is not a parent of 'Class2', the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
			// Target class 'Class5' is not a parent of 'Class3', the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
		}
	}

	/**
	 * Test to move all the contents (Attributes, Operations and Nest Classifiers) of the class 'Class3' to 'Class2'.
	 * The class 'Class2' is a child of 'Class3'.
	 */
	@Test
	public void testAllContentsToChild() {
		// The target class is 'Class2'.
		NamedElement targetMember = model.getMember(NAME_CLASS_2);
		assertTrue("The object must be a Class.", targetMember instanceof Class); //$NON-NLS-1$

		// Retrieve the command to move the elements.
		ICommand command = getCommandMovedElements(contents, targetMember);
		assertNotNull("The command must not be null.", command); //$NON-NLS-1$

		papyrusEditorFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		for (EObject eObject : contents) {
			// Target class 'Class2' is a parent of Class1, the views must not be deleted
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			// It's the target class, the views must not be deleted.
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNotNull("Many views must be found..", objectView); //$NON-NLS-1$
			// Target class 'Class2' is not a parent of 'Class2', the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
		}

		papyrusEditorFixture.getEditingDomain().getCommandStack().undo();

		for (EObject eObject : contents) {
			// All the views are shown after the undo.
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
		}

		papyrusEditorFixture.getEditingDomain().getCommandStack().redo();

		for (EObject eObject : contents) {
			// Target class 'Class2' is a parent of Class1, the views must not be deleted
			View objectView = getDiagramViewEObject(eObject, NAME_CLASS_1);
			assertNotNull("Many views must be found.", objectView); //$NON-NLS-1$
			// It's the target class, the views must not be deleted.
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_2);
			assertNotNull("Many views must be found..", objectView); //$NON-NLS-1$
			// Target class 'Class2' is not a parent of 'Class2', the views must be deleted
			objectView = getDiagramViewEObject(eObject, NAME_CLASS_3);
			assertNull("No view must be found.", objectView); //$NON-NLS-1$
		}
	}

	/**
	 * This creates the command to move the Elements.
	 *
	 * @param elementsToMove
	 *            The list of the elements to move.
	 * @return The command to move the elements.
	 */
	private ICommand getCommandMovedElements(final List<EObject> elementsToMove, final EObject elementTarget) {
		ICommand command = null;

		final MoveRequest moveRequest = new MoveRequest(elementTarget, elementsToMove);
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementTarget);

		assertNotNull(provider);

		// Retrieve delete command from the Element Edit service
		command = provider.getEditCommand(moveRequest);

		return command;
	}

	/**
	 * Gets view of an EObject on the diagram and on a class.
	 *
	 * @param eObject
	 *            The EObject.
	 * @param classNameReference
	 *            The name of the class in which the EObject reference must be.
	 * @return A corresponding view of the EObject.
	 */
	private View getDiagramViewEObject(final EObject eObject, final String classNameReference) {
		View diagramView = findViewReference(diagram, eObject);

		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				final Object object = iterator.next();
				if (object instanceof View) {
					EObject element = ((View) object).getElement();
					if (element instanceof Classifier) {
						if (classNameReference.equals(((Classifier) element).getName())) {
							diagramView = findViewReference((View) object, eObject);
						}
					}
				}
			}
		}

		return diagramView;
	}

	/**
	 * Gets view of the EObject on a specific element.
	 *
	 * @param container
	 *            The specific element to find EObject.
	 * @param eObject
	 *            EObject to find.
	 * @return A view of the EObject.
	 */
	private View findViewReference(final View container, final EObject eObject) {
		for (final Object viewObject : container.getChildren()) {
			final View view = (View) viewObject;
			final EObject element = view.getElement();
			if (eObject.equals(element)) {
				return view;
			}
		}
		return null;
	}

}
