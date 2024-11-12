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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services;

import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.LabelElementNameProvider;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.UMLLabelServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.labels.INamedElementNameProvider;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Provides Sequence Diagram-specific labels for semantic elements.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class SequenceDiagramUMLLabelServices {

	private final UMLLabelServices labelServices = new UMLLabelServices();

	private final INamedElementNameProvider namedElementNameProvider = new LabelElementNameProvider();

	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();

	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	private static final String UNDEFINED = "<Undefined>"; //$NON-NLS-1$

	/**
	 * Computes the label of the provided {@link Message}.
	 * <p>
	 * The label contains either the name of the {@link Message}, or its signature if it contains one.
	 * <p>
	 * <b>Note</b>: the formatting of the signature is specific to the sequence diagram.
	 *
	 * @param message
	 *            the {@link Message} to compute the label from
	 * @return the computed label
	 */
	public String renderMessageLabelSD(Message message, DDiagram diagram) {
		Objects.requireNonNull(message);
		String label;
		// Code from org.eclipse.papyrus.uml.diagram.sequence.edit.helpers.MessageLabelHelper.elementLabel(GraphicalEditPart)
		NamedElement signature = message.getSignature();
		if (signature instanceof Operation) {
			label = this.computeOperationLabel((Operation) signature);
		} else if (signature instanceof Signal) {
			label = this.computeSignalLabel((Signal) signature);
		} else if (signature != null) {
			label = this.namedElementNameProvider.getName(signature);
		} else {
			// Signature is null
			StringBuffer buffer = new StringBuffer();
			buffer.append(labelServices.renderLabel(message, diagram));
			String arguments = computeMessageArgumentsLabel(message, diagram);
			buffer.append(arguments);
			label = buffer.toString();
		}
		return label;
	}

	/**
	 * Computes the label of the referred {@link Interaction} from the provided {@code interactionUse}.
	 * <p>
	 * This method is typically used to compute the <i>Center Label Expression</i> of interaction uses.
	 * </p>
	 *
	 * @param interactionUse
	 *            the {@link InteractionUse} to compute the referred {@link Interaction} label from
	 * @return the referred {@link Interaction} label, or an empty String if there is no referred interaction in the provided {@code interactionUse}
	 */
	public String renderReferredInteractionLabelSD(InteractionUse interactionUse) {
		String result = UMLCharacters.EMPTY;
		if (interactionUse.getRefersTo() != null) {
			result = interactionUse.getRefersTo().getName();
		}
		return result;
	}

	/**
	 * Computes the label of a {@link TimeObservation} represented by the provided {@code event}.
	 *
	 * @param event
	 *            the {@link EAnnotation} representing the ordering end where the {@link TimeObservation} is attached
	 * @param diagram
	 *            the diagram
	 * @return the label of the {@link TimeObservation} represented by the provided {@code event}
	 */
	public String renderTimeObservationLabelSD(EAnnotation event, DDiagram diagram) {
		return umlHelper.getTimeObservationFromEnd(event)
				.map(timeObsevation -> labelServices.renderLabel(timeObsevation, diagram))
				.orElse(UMLCharacters.EMPTY);
	}

	private String computeOperationLabel(Operation operation) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.util.OperationUtil.getCustomLabel(Message, Operation, Collection<String>)
		StringBuffer buffer = new StringBuffer();
		buffer.append(namedElementNameProvider.getName(operation));
		String parameters = computeOperationParametersLabel(operation);
		buffer.append(parameters);
		String returnParameter = computeReturnParameterLabel(operation);
		buffer.append(returnParameter);
		return buffer.toString();
	}

	private String computeSignalLabel(Signal signal) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.util.SignalUtil.getCustomLabel(Message, Signal, Collection<String>)
		StringBuffer buffer = new StringBuffer();
		buffer.append(namedElementNameProvider.getName(signal));
		String properties = computeSignalPropertiesLabel(signal);
		buffer.append(properties);
		return buffer.toString();
	}

	private String computeMessageArgumentsLabel(Message message, DDiagram diagram) {
		if (message.getArguments().isEmpty()) {
			return UMLCharacters.EMPTY;
		}
		return message.getArguments().stream()
				.map(argument -> this.computeArgumentLabel(argument, diagram))
				.collect(Collectors.joining(UMLCharacters.COMMA + UMLCharacters.SPACE, UMLCharacters.OPEN_PARENTHESE, UMLCharacters.CLOSE_PARENTHESE));
	}

	private String computeSignalPropertiesLabel(Signal signal) {
		if (signal.getOwnedAttributes().isEmpty()) {
			return UMLCharacters.EMPTY;
		}
		return signal.getOwnedAttributes().stream()
				.map(this::computePropertyLabel)
				.collect(Collectors.joining(UMLCharacters.COMMA + UMLCharacters.SPACE, UMLCharacters.OPEN_PARENTHESE, UMLCharacters.CLOSE_PARENTHESE));
	}

	private String computeOperationParametersLabel(Operation operation) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.util.OperationUtil.getParametersAsString(Message, Operation, Collection<String>)
		String result = UMLCharacters.EMPTY;
		String operationParameters = operation.getOwnedParameters().stream()
				.filter(parameter -> !parameter.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL))
				.map(this::computeParameterLabel)
				.collect(Collectors.joining(UMLCharacters.COMMA + UMLCharacters.SPACE));
		if (!operationParameters.isBlank()) {
			result = UMLCharacters.OPEN_PARENTHESE + operationParameters + UMLCharacters.CLOSE_PARENTHESE;
		}
		return result;
	}

	private String computeReturnParameterLabel(Operation operation) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.util.OperationUtil.getReturnParameter(Operation)
		// Retrieve the return parameter (assume to be unique if defined).
		Parameter returnParameter = operation.getOwnedParameters().stream()
				.filter(parameter -> parameter.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL))
				.findFirst()
				.orElse(null);
		StringBuffer buffer = new StringBuffer();
		if (returnParameter != null) {
			buffer.append(UMLCharacters.D_DOTS);
			buffer.append(UMLCharacters.SPACE);
			String typeString = this.computeTypeLabel(returnParameter.getType());
			buffer.append(typeString);
		}
		return buffer.toString();
	}

	private String computeArgumentLabel(ValueSpecification argument, DDiagram diagram) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.edit.helpers.MessageLabelHelper.getMessageLabel(Message, Collection<String>)
		String typeString = this.computeTypeLabel(argument.getType());
		return typeString + UMLCharacters.SPACE + labelServices.renderLabel(argument, diagram);
	}

	private String computePropertyLabel(Property property) {
		// Code from org.eclipse.papyrus.uml.diagram.sequence.util.SignalUtil.getCustomPropertyLabel(Message, Property, Collection<String>)
		StringBuffer buffer = new StringBuffer();
		buffer.append(namedElementNameProvider.getName(property));
		buffer.append(UMLCharacters.D_DOTS);
		buffer.append(UMLCharacters.SPACE);
		String typeString = this.computeTypeLabel(property.getType());
		buffer.append(typeString);
		return buffer.toString();
	}

	private String computeParameterLabel(Parameter parameter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(namedElementNameProvider.getName(parameter));
		buffer.append(UMLCharacters.D_DOTS);
		buffer.append(UMLCharacters.SPACE);
		String typeString = this.computeTypeLabel(parameter.getType());
		buffer.append(typeString);
		return buffer.toString();
	}

	private String computeTypeLabel(Type type) {
		String result = UNDEFINED;
		if (type != null) {
			result = namedElementNameProvider.getName(type);
		}
		return result;
	}

}
