/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramUMLLabelServices;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link SequenceDiagramUMLLabelServices} services.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramUMLLabelServicesTest extends AbstractServicesTest {

	private final static String MESSAGE_NAME = "message"; //$NON-NLS-1$

	private SequenceDiagramUMLLabelServices labelServices;

	private Message message;

	private DDiagram testDiagram = DiagramFactory.eINSTANCE.createDDiagram();

	@Before
	public void setUp() {
		this.labelServices = new SequenceDiagramUMLLabelServices();
	}

	@Test
	public void testRenderMessageLabelNullMessage() {
		assertThrows(NullPointerException.class, () -> labelServices.renderMessageLabelSD(null, testDiagram));
	}

	@Test
	public void testRenderMessageLabelNoSignatureNoArgument() {
		message = create(Message.class);
		message.setName(MESSAGE_NAME);
		String label = this.labelServices.renderMessageLabelSD(message, testDiagram);
		assertEquals(MESSAGE_NAME, label);
	}

	@Test
	public void testRenderMessageLabelNoSignatureWithArguments() {
		message = create(Message.class);
		message.setName(MESSAGE_NAME);

		ValueSpecification argument1 = create(LiteralString.class);
		argument1.setName("Argument1"); //$NON-NLS-1$
		message.getArguments().add(argument1);

		Type argument2Type = create(org.eclipse.uml2.uml.Class.class);
		argument2Type.setName("Argument2Type"); //$NON-NLS-1$
		ValueSpecification argument2 = create(Expression.class);
		argument2.setName("Argument2"); //$NON-NLS-1$
		argument2.setType(argument2Type);
		message.getArguments().add(argument2);

		String label = this.labelServices.renderMessageLabelSD(message, testDiagram);
		assertEquals(MESSAGE_NAME + "(<Undefined> Argument1, Argument2Type Argument2)", //$NON-NLS-1$
				label);
	}

	@Test
	public void testRenderMessageLabelOperationSignature() {
		message = create(Message.class);
		message.setName(MESSAGE_NAME);
		Operation operation = create(Operation.class);
		operation.setName("Operation"); //$NON-NLS-1$

		Type parameter1Type = create(org.eclipse.uml2.uml.Class.class);
		parameter1Type.setName("Parameter1Type"); //$NON-NLS-1$

		Parameter parameter1 = create(Parameter.class);
		parameter1.setName("Parameter1"); //$NON-NLS-1$
		parameter1.setType(parameter1Type);
		parameter1.setDirection(ParameterDirectionKind.OUT_LITERAL);
		operation.getOwnedParameters().add(parameter1);


		Type parameter2Type = create(org.eclipse.uml2.uml.Class.class);
		parameter2Type.setName("Parameter2Type"); //$NON-NLS-1$

		Parameter parameter2 = create(Parameter.class);
		parameter2.setName("Parameter2"); //$NON-NLS-1$
		parameter2.setType(parameter2Type);
		parameter2.setDirection(ParameterDirectionKind.IN_LITERAL);
		operation.getOwnedParameters().add(parameter2);

		Type returnParameterType = create(org.eclipse.uml2.uml.Class.class);
		returnParameterType.setName("ReturnParameterType"); //$NON-NLS-1$

		Parameter returnParameter = create(Parameter.class);
		returnParameter.setName("ReturnParameter"); //$NON-NLS-1$
		returnParameter.setType(returnParameterType);
		returnParameter.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		operation.getOwnedParameters().add(returnParameter);

		message.setSignature(operation);

		String label = labelServices.renderMessageLabelSD(message, testDiagram);
		assertEquals("Operation(Parameter1: Parameter1Type, Parameter2: Parameter2Type): ReturnParameterType", //$NON-NLS-1$
				label);
	}

	@Test
	public void testRenderMessageLabelSignalSignature() {
		message = create(Message.class);
		message.setName(MESSAGE_NAME);
		Signal signal = create(Signal.class);
		signal.setName("Signal"); //$NON-NLS-1$

		Type property1Type = create(org.eclipse.uml2.uml.Class.class);
		property1Type.setName("Property1Type"); //$NON-NLS-1$

		Property property1 = create(Property.class);
		property1.setName("Property1"); //$NON-NLS-1$
		property1.setType(property1Type);
		signal.getOwnedAttributes().add(property1);

		Property property2 = create(Property.class);
		property2.setName("Property2"); //$NON-NLS-1$
		signal.getOwnedAttributes().add(property2);

		message.setSignature(signal);

		String label = labelServices.renderMessageLabelSD(message, testDiagram);
		assertEquals("Signal(Property1: Property1Type, Property2: <Undefined>)", label); //$NON-NLS-1$
	}

	@Test
	public void testRenderMessageLabelClassSignature() {
		message = create(Message.class);
		message.setName(MESSAGE_NAME);
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		clazz.setName("Class"); //$NON-NLS-1$
		message.setSignature(clazz);

		String label = labelServices.renderMessageLabelSD(message, testDiagram);
		assertEquals("Class", label); //$NON-NLS-1$
	}
}
