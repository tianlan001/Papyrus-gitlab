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
package org.eclipse.papyrus.uml.diagram.communication.providers;

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
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.communication.custom.parser.LifelineCustomParsers;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.AppliedStereotypeMessageEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ConstraintBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ConstraintNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.DurationObservationLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.DurationObservationStereotypeLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationStereotypeLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry;
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
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interaction_NameLabel_Parser = parser;
		}
		return interaction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interaction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_FloatingNameLabel_Parser() {
		if (interaction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interaction_FloatingNameLabel_Parser = parser;
		}
		return interaction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private LifelineCustomParsers lifeline_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLifeline_NameLabel_Parser() {
		if (lifeline_NameLabel_Parser == null) {
			lifeline_NameLabel_Parser = new LifelineCustomParsers();
		}
		return lifeline_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser lifeline_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLifeline_FloatingNameLabel_Parser() {
		if (lifeline_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			lifeline_FloatingNameLabel_Parser = parser;
		}
		return lifeline_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_NameLabel_Parser() {
		if (constraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_NameLabel_Parser = parser;
		}
		return constraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_BodyLabel_Parser() {
		if (constraint_BodyLabel_Parser == null) {
			constraint_BodyLabel_Parser = new ConstraintParser();
		}
		return constraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private CommentParser comment_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComment_BodyLabel_Parser() {
		if (comment_BodyLabel_Parser == null) {
			comment_BodyLabel_Parser = new CommentParser();
		}
		return comment_BodyLabel_Parser;
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
	private AppliedStereotypeParser durationObservation_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationObservation_StereotypeLabel_Parser() {
		if (durationObservation_StereotypeLabel_Parser == null) {
			durationObservation_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return durationObservation_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser path_MessageLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPath_MessageLabel_Parser() {
		if (path_MessageLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			path_MessageLabel_Parser = parser;
		}
		return path_MessageLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser path_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPath_StereotypeLabel_Parser() {
		if (path_StereotypeLabel_Parser == null) {
			path_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return path_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case InteractionNameEditPart.VISUAL_ID:
				return getInteraction_NameLabel_Parser();
			case InteractionFloatingLabelEditPart.VISUAL_ID:
				return getInteraction_FloatingNameLabel_Parser();
			case LifelineNameEditPart.VISUAL_ID:
				return getLifeline_NameLabel_Parser();
			case LifelineFloatingLabelEditPartCN.VISUAL_ID:
				return getLifeline_FloatingNameLabel_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPartCN.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case TimeObservationNameEditPartCN.VISUAL_ID:
				return getTimeObservation_NameLabel_Parser();
			case TimeObservationStereotypeLabelEditPartCN.VISUAL_ID:
				return getTimeObservation_StereotypeLabel_Parser();
			case DurationObservationLabelEditPartCN.VISUAL_ID:
				return getDurationObservation_NameLabel_Parser();
			case DurationObservationStereotypeLabelEditPartCN.VISUAL_ID:
				return getDurationObservation_StereotypeLabel_Parser();
			case MessageNameEditPart.VISUAL_ID:
				return getPath_MessageLabel_Parser();
			case AppliedStereotypeMessageEditPart.VISUAL_ID:
				return getPath_StereotypeLabel_Parser();
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
