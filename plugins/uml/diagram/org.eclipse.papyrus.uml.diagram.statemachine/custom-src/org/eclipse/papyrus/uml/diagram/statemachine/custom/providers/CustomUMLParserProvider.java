package org.eclipse.papyrus.uml.diagram.statemachine.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CustomAppliedStereotypeContextLinkLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLParserProvider;

public class CustomUMLParserProvider extends UMLParserProvider {

	public CustomUMLParserProvider() {
		super();
	}

	protected IParser getAppliedStereotypeParser(String defaultEditString) {
		return new AppliedStereotypeParser(defaultEditString);
	}

	@Override
	protected IParser getParser(String visualID) {
		switch (visualID) {
		case ContextLinkAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedStereotypeParser(CustomAppliedStereotypeContextLinkLabelDisplayEditPolicy.APPLIED_STEREOTYPE_LABEL);
		}
		return super.getParser(visualID);
	}
}