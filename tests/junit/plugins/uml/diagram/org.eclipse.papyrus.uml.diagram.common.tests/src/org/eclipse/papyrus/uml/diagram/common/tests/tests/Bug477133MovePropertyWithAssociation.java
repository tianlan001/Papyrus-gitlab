/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier(CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;

/**
 * The test class to verify that there is no regression in relation to Bug 477133.
 * bug 477133: when we move a property linked to an association the type is badly updated https://bugs.eclipse.org/bugs/show_bug.cgi?id=477133
 * For this test we do not load a model!
 * during the construction the member-end can be the inverse of end-types
 * 
 * @author Patrick Tessier
 */
public class Bug477133MovePropertyWithAssociation extends AbstractPapyrusTest {

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();


	/**
	 * Constructor.
	 *
	 */
	public Bug477133MovePropertyWithAssociation() {
		super();
	}

	/**
	 * Case of an association between A and B, end a is owned by Class B and end b is owned by the association. End a is navigable but not End b. Test changes A to C.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testUMLCase1() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		NamedElement target = modelSetFixture.getModel().getMember("C");
		NamedElement source = modelSetFixture.getModel().getMember("A");
		StructuredClassifier sourceAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("B");

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_TARGET);
		reconnectReq.setConnectionEditPart((ConnectionEditPart) getEditPart(association, diagram));
		reconnectReq.setTargetEditPart(targetEP);
		Command command = targetEP.getCommand(reconnectReq);
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), (Type) target));
		assertNotNull(sourceAssiociation.getOwnedAttribute(source.getName().toLowerCase(), (Type) target));
		assertNotNull(association.getOwnedEnd(sourceAssiociation.getName().toLowerCase(), sourceAssiociation));


	}

	/**
	 * Case of an association between A and B, end a is owned by Class B and end b is owned by the association. End a is navigable but not End b. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testUMLCase2() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectReq.setConnectionEditPart((ConnectionEditPart) getEditPart(association, diagram));
		reconnectReq.setTargetEditPart(targetEP);
		Command command = targetEP.getCommand(reconnectReq);
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), target));
		assertNotNull(target.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertNotNull(association.getOwnedEnd(source.getName().toLowerCase(), target));


	}

	/**
	 * Case of an association between A and B, end a is owned by Class B and end b is owned by Class A. Both ends are navigable. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testUMLCase3() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		// Set End a to Class B
		SetValueCommand setCommand = new SetValueCommand(new SetRequest(targetAssiociation, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), association.getMemberEnd(source.getName().toLowerCase(), source)));
		modelSetFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(setCommand));

		assertTrue(association.getOwnedEnds().isEmpty());

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectReq.setConnectionEditPart((ConnectionEditPart) getEditPart(association, diagram));
		reconnectReq.setTargetEditPart(targetEP);
		Command command = targetEP.getCommand(reconnectReq);
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertTrue(association.getOwnedEnds().isEmpty());
		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), target));
		assertNotNull(target.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertNull(source.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));


	}

	/**
	 * Case of an association between A and B, Both ends are not navigable and contained by the association. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testUMLCase4() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		// Set End a to association
		SetValueCommand setCommand = new SetValueCommand(new SetRequest(association, UMLPackage.eINSTANCE.getAssociation_OwnedEnd(), association.getMemberEnd(targetAssiociation.getName().toLowerCase(), targetAssiociation)));
		modelSetFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(setCommand));

		assertEquals(2, association.getOwnedEnds().size());

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectReq.setConnectionEditPart((ConnectionEditPart) getEditPart(association, diagram));
		reconnectReq.setTargetEditPart(targetEP);
		Command command = targetEP.getCommand(reconnectReq);
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertEquals(2, association.getOwnedEnds().size());
		assertNotNull(association.getOwnedEnd(source.getName().toLowerCase(), target));
		assertNotNull(association.getOwnedEnd(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertFalse(target.eIsSet(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()));
		assertFalse(targetAssiociation.eIsSet(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()));

	}

	/**
	 * Case of an association between A and B, end a is owned by Block B and end b is owned by the association. End a is navigable but not End b. Test changes A to C.
	 * 
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testSysMLCase1() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		NamedElement target = modelSetFixture.getModel().getMember("C");
		NamedElement source = modelSetFixture.getModel().getMember("A");
		StructuredClassifier sourceAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("B");

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReorientRelationshipRequest request = new ReorientRelationshipRequest(association, target, source, ReorientRequest.REORIENT_TARGET);
		Command command = targetEP.getCommand(new EditCommandRequestWrapper(request));
		modelSetFixture.getEditingDomain().getCommandStack().execute(new GEFtoEMFCommandWrapper(command));

		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), (Type) target));
		assertNotNull(sourceAssiociation.getOwnedAttribute(source.getName().toLowerCase(), (Type) target));
		assertNotNull(association.getOwnedEnd(sourceAssiociation.getName().toLowerCase(), sourceAssiociation));


	}

	/**
	 * Case of an association between A and B, end a is owned by Block B and end b is owned by the association. End a is navigable but not End b. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testSysMLCase2() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		NamedElement source = modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReorientRelationshipRequest request = new ReorientRelationshipRequest(association, target, source, ReorientRequest.REORIENT_SOURCE);
		Command command = targetEP.getCommand(new EditCommandRequestWrapper(request));
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), target));
		assertNotNull(target.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertNotNull(association.getOwnedEnd(source.getName().toLowerCase(), target));


	}

	/**
	 * Case of an association between A and B, end a is owned by Block B and end b is owned by Block A. Both ends are navigable. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testSysMLCase3() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		// Set End a to Class B
		SetValueCommand setCommand = new SetValueCommand(new SetRequest(targetAssiociation, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), association.getMemberEnd(source.getName().toLowerCase(), source)));
		modelSetFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(setCommand));

		assertTrue(association.getOwnedEnds().isEmpty());

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReorientRelationshipRequest request = new ReorientRelationshipRequest(association, target, source, ReorientRequest.REORIENT_SOURCE);
		Command command = targetEP.getCommand(new EditCommandRequestWrapper(request));
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertTrue(association.getOwnedEnds().isEmpty());
		assertNotNull(association.getMemberEnd(source.getName().toLowerCase(), target));
		assertNotNull(target.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertNull(source.getOwnedAttribute(targetAssiociation.getName().toLowerCase(), targetAssiociation));


	}

	/**
	 * Case of an association between A and B, Both ends are not navigable and contained by the association. Test changes B to D.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testSysMLCase4() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Association");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("D");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("A");

		// Set End a to association
		SetValueCommand setCommand = new SetValueCommand(new SetRequest(association, UMLPackage.eINSTANCE.getAssociation_OwnedEnd(), association.getMemberEnd(targetAssiociation.getName().toLowerCase(), targetAssiociation)));
		modelSetFixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(setCommand));

		assertEquals(2, association.getOwnedEnds().size());

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "477133");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReorientRelationshipRequest request = new ReorientRelationshipRequest(association, target, source, ReorientRequest.REORIENT_SOURCE);
		Command command = targetEP.getCommand(new EditCommandRequestWrapper(request));
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertEquals(2, association.getOwnedEnds().size());
		assertNotNull(association.getOwnedEnd(source.getName().toLowerCase(), target));
		assertNotNull(association.getOwnedEnd(targetAssiociation.getName().toLowerCase(), targetAssiociation));
		assertFalse(target.eIsSet(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()));
		assertFalse(targetAssiociation.eIsSet(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()));

	}

	/**
	 * Case of an Directed Composition association between B1 and B2, end B2 is owned by Block B1 and end B2 is owned by Association. Test changes B2 to B3.
	 */
	@Test
	@PluginResource("/resources/477133/model.di")
	public void testSysMLCase5() {

		// Get data test
		Association association = (Association) modelSetFixture.getModel().getMember("Directed Composition");
		StructuredClassifier target = (StructuredClassifier) modelSetFixture.getModel().getMember("B3");
		StructuredClassifier source = (StructuredClassifier) modelSetFixture.getModel().getMember("B2");
		StructuredClassifier targetAssiociation = (StructuredClassifier) modelSetFixture.getModel().getMember("B1");

		assertEquals(1, association.getOwnedEnds().size());

		Diagram diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "BDD 446668");
		EditPart targetEP = getEditPart(target, diagram);

		// Build a request and execute it
		ReorientRelationshipRequest request = new ReorientRelationshipRequest(association, target, source, ReorientRequest.REORIENT_TARGET);
		Command command = targetEP.getCommand(new EditCommandRequestWrapper(request));
		modelSetFixture.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		assertEquals(1, association.getOwnedEnds().size());
		assertNotNull(association.getOwnedEnd("b1_1", targetAssiociation));
		assertNotNull(targetAssiociation.getOwnedAttribute("b2_1", target));


	}


	/**
	 * Gets the edits the part.
	 *
	 * @param semanticElement
	 *            the semantic element
	 * @param diagram
	 *            the diagram
	 * @return the edits the part
	 */
	private EditPart getEditPart(NamedElement semanticElement, Diagram diagram) {

		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement.getName());
		if (diagramView == null) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement.getName());
		}
		assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}
}
