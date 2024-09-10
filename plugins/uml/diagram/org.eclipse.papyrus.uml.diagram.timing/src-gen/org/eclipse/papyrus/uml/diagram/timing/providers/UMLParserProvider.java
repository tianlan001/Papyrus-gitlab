/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.timing.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.timing.custom.parsers.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.timing.custom.parsers.StateDefinitionParser;
import org.eclipse.papyrus.uml.diagram.timing.custom.parsers.TickParser;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GateLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GeneralOrderingNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateDefinitionLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateInvariantAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TickNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser interaction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_NameLabel_Parser() {
		if (interaction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interaction_NameLabel_Parser = parser;
		}
		return interaction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser lifeline_FullNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLifeline_FullNameLabel_Parser() {
		if (lifeline_FullNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			lifeline_FullNameLabel_Parser = parser;
		}
		return lifeline_FullNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser lifeline_CompactNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLifeline_CompactNameLabel_Parser() {
		if (lifeline_CompactNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			lifeline_CompactNameLabel_Parser = parser;
		}
		return lifeline_CompactNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser stateInvariant_FullStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateInvariant_FullStereotypeLabel_Parser() {
		if (stateInvariant_FullStereotypeLabel_Parser == null) {
			stateInvariant_FullStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return stateInvariant_FullStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stateInvariant_CompactNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateInvariant_CompactNameLabel_Parser() {
		if (stateInvariant_CompactNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stateInvariant_CompactNameLabel_Parser = parser;
		}
		return stateInvariant_CompactNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser stateInvariant_CompactStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateInvariant_CompactStereotypeLabel_Parser() {
		if (stateInvariant_CompactStereotypeLabel_Parser == null) {
			stateInvariant_CompactStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return stateInvariant_CompactStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser occurrenceSpecification_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOccurrenceSpecification_NameLabel_Parser() {
		if (occurrenceSpecification_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			occurrenceSpecification_NameLabel_Parser = parser;
		}
		return occurrenceSpecification_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser occurrenceSpecification_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOccurrenceSpecification_StereotypeLabel_Parser() {
		if (occurrenceSpecification_StereotypeLabel_Parser == null) {
			occurrenceSpecification_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return occurrenceSpecification_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser messageOccurrenceSpecification_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessageOccurrenceSpecification_NameLabel_Parser() {
		if (messageOccurrenceSpecification_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			messageOccurrenceSpecification_NameLabel_Parser = parser;
		}
		return messageOccurrenceSpecification_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser messageOccurrenceSpecification_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessageOccurrenceSpecification_StereotypeLabel_Parser() {
		if (messageOccurrenceSpecification_StereotypeLabel_Parser == null) {
			messageOccurrenceSpecification_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return messageOccurrenceSpecification_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StateDefinitionParser node_StateDefinitionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_StateDefinitionNameLabel_Parser() {
		if (node_StateDefinitionNameLabel_Parser == null) {
			node_StateDefinitionNameLabel_Parser = new StateDefinitionParser();
		}
		return node_StateDefinitionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser timeConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_BodyLabel_Parser() {
		if (timeConstraint_BodyLabel_Parser == null) {
			timeConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return timeConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser timeConstraint_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_StereotypeLabel_Parser() {
		if (timeConstraint_StereotypeLabel_Parser == null) {
			timeConstraint_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return timeConstraint_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeObservation_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeObservation_NameLabel_Parser() {
		if (timeObservation_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeObservation_NameLabel_Parser = parser;
		}
		return timeObservation_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser timeObservation_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeObservation_StereotypeLabel_Parser() {
		if (timeObservation_StereotypeLabel_Parser == null) {
			timeObservation_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return timeObservation_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser durationConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_BodyLabel_Parser() {
		if (durationConstraint_BodyLabel_Parser == null) {
			durationConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return durationConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationObservation_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationObservation_NameLabel_Parser() {
		if (durationObservation_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationObservation_NameLabel_Parser = parser;
		}
		return durationObservation_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser generalOrdering_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralOrdering_NameLabel_Parser() {
		if (generalOrdering_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			generalOrdering_NameLabel_Parser = parser;
		}
		return generalOrdering_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private TickParser node_TickNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_TickNameLabel_Parser() {
		if (node_TickNameLabel_Parser == null) {
			node_TickNameLabel_Parser = new TickParser();
		}
		return node_TickNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser destructionOccurrenceSpecification_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestructionOccurrenceSpecification_NameLabel_Parser() {
		if (destructionOccurrenceSpecification_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			destructionOccurrenceSpecification_NameLabel_Parser = parser;
		}
		return destructionOccurrenceSpecification_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser destructionOccurrenceSpecification_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestructionOccurrenceSpecification_StereotypeLabel_Parser() {
		if (destructionOccurrenceSpecification_StereotypeLabel_Parser == null) {
			destructionOccurrenceSpecification_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return destructionOccurrenceSpecification_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser gate_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGate_NameLabel_Parser() {
		if (gate_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			gate_NameLabel_Parser = parser;
		}
		return gate_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_SynchNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_SynchNameLabel_Parser() {
		if (message_SynchNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_SynchNameLabel_Parser = parser;
		}
		return message_SynchNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_SynchStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_SynchStereotypeLabel_Parser() {
		if (message_SynchStereotypeLabel_Parser == null) {
			message_SynchStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_SynchStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_AsynchNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_AsynchNameLabel_Parser() {
		if (message_AsynchNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_AsynchNameLabel_Parser = parser;
		}
		return message_AsynchNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_AsynchStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_AsynchStereotypeLabel_Parser() {
		if (message_AsynchStereotypeLabel_Parser == null) {
			message_AsynchStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_AsynchStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_ReplyNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_ReplyNameLabel_Parser() {
		if (message_ReplyNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_ReplyNameLabel_Parser = parser;
		}
		return message_ReplyNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_ReplyStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_ReplyStereotypeLabel_Parser() {
		if (message_ReplyStereotypeLabel_Parser == null) {
			message_ReplyStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_ReplyStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_CreateNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_CreateNameLabel_Parser() {
		if (message_CreateNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_CreateNameLabel_Parser = parser;
		}
		return message_CreateNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_CreateStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_CreateStereotypeLabel_Parser() {
		if (message_CreateStereotypeLabel_Parser == null) {
			message_CreateStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_CreateStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_DeleteNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_DeleteNameLabel_Parser() {
		if (message_DeleteNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_DeleteNameLabel_Parser = parser;
		}
		return message_DeleteNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_DeleteStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_DeleteStereotypeLabel_Parser() {
		if (message_DeleteStereotypeLabel_Parser == null) {
			message_DeleteStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_DeleteStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_LostNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_LostNameLabel_Parser() {
		if (message_LostNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_LostNameLabel_Parser = parser;
		}
		return message_LostNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_LostStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_LostStereotypeLabel_Parser() {
		if (message_LostStereotypeLabel_Parser == null) {
			message_LostStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_LostStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser message_FoundNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_FoundNameLabel_Parser() {
		if (message_FoundNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			message_FoundNameLabel_Parser = parser;
		}
		return message_FoundNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser message_FoundStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMessage_FoundStereotypeLabel_Parser() {
		if (message_FoundStereotypeLabel_Parser == null) {
			message_FoundStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return message_FoundStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case InteractionNameEditPart.VISUAL_ID:
				return getInteraction_NameLabel_Parser();
			case FullLifelineNameEditPart.VISUAL_ID:
				return getLifeline_FullNameLabel_Parser();
			case CompactLifelineNameEditPart.VISUAL_ID:
				return getLifeline_CompactNameLabel_Parser();
			case FullStateInvariantAppliedStereotypeEditPart.VISUAL_ID:
				return getStateInvariant_FullStereotypeLabel_Parser();
			case CompactStateInvariantNameEditPart.VISUAL_ID:
				return getStateInvariant_CompactNameLabel_Parser();
			case StateInvariantAppliedStereotypeEditPart.VISUAL_ID:
				return getStateInvariant_CompactStereotypeLabel_Parser();
			case OccurrenceSpecificationLabelEditPart.VISUAL_ID:
				return getOccurrenceSpecification_NameLabel_Parser();
			case OccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID:
				return getOccurrenceSpecification_StereotypeLabel_Parser();
			case MessageOccurrenceSpecificationLabelEditPart.VISUAL_ID:
				return getMessageOccurrenceSpecification_NameLabel_Parser();
			case MessageOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID:
				return getMessageOccurrenceSpecification_StereotypeLabel_Parser();
			case StateDefinitionLabelEditPart.VISUAL_ID:
				return getNode_StateDefinitionNameLabel_Parser();
			case TimeConstraintSpecificationEditPart.VISUAL_ID:
				return getTimeConstraint_BodyLabel_Parser();
			case TimeConstraintAppliedStereotypeEditPart.VISUAL_ID:
				return getTimeConstraint_StereotypeLabel_Parser();
			case TimeObservationNameEditPart.VISUAL_ID:
				return getTimeObservation_NameLabel_Parser();
			case TimeObservationAppliedStereotypeEditPart.VISUAL_ID:
				return getTimeObservation_StereotypeLabel_Parser();
			case DurationConstraintSpecificationEditPart.VISUAL_ID:
				return getDurationConstraint_BodyLabel_Parser();
			case DurationObservationNameEditPart.VISUAL_ID:
				return getDurationObservation_NameLabel_Parser();
			case GeneralOrderingNameEditPart.VISUAL_ID:
				return getGeneralOrdering_NameLabel_Parser();
			case TickNameEditPart.VISUAL_ID:
				return getNode_TickNameLabel_Parser();
			case DestructionOccurrenceSpecificationLabelEditPart.VISUAL_ID:
				return getDestructionOccurrenceSpecification_NameLabel_Parser();
			case DestructionOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID:
				return getDestructionOccurrenceSpecification_StereotypeLabel_Parser();
			case GateLabelEditPart.VISUAL_ID:
				return getGate_NameLabel_Parser();
			case MessageSyncNameLabelEditPart.VISUAL_ID:
				return getMessage_SynchNameLabel_Parser();
			case MessageSyncAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_SynchStereotypeLabel_Parser();
			case MessageAsyncNameLabelEditPart.VISUAL_ID:
				return getMessage_AsynchNameLabel_Parser();
			case MessageAsyncAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_AsynchStereotypeLabel_Parser();
			case MessageReplyNameLabelEditPart.VISUAL_ID:
				return getMessage_ReplyNameLabel_Parser();
			case MessageReplyAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_ReplyStereotypeLabel_Parser();
			case MessageCreateNameLabelEditPart.VISUAL_ID:
				return getMessage_CreateNameLabel_Parser();
			case MessageCreateAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_CreateStereotypeLabel_Parser();
			case MessageDeleteNameLabelEditPart.VISUAL_ID:
				return getMessage_DeleteNameLabel_Parser();
			case MessageDeleteAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_DeleteStereotypeLabel_Parser();
			case MessageLostNameLabelEditPart.VISUAL_ID:
				return getMessage_LostNameLabel_Parser();
			case MessageLostAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_LostStereotypeLabel_Parser();
			case MessageFoundNameLabelEditPart.VISUAL_ID:
				return getMessage_FoundNameLabel_Parser();
			case MessageFoundAppliedStereotypeEditPart.VISUAL_ID:
				return getMessage_FoundStereotypeLabel_Parser();
			}
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 *
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		String vid = hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(vid));
		}
		View view = hint.getAdapter(View.class);
		if (view != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (UMLElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
}
