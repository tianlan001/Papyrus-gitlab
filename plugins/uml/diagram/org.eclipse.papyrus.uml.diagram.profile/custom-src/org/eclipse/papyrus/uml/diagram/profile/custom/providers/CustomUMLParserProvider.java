package org.eclipse.papyrus.uml.diagram.profile.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AppliedStereotypeElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ContextLinkAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLParserProvider;

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
		case AppliedStereotypeElementImportEditPart.VISUAL_ID:
			return getAppliedStereotypeParser("ElementImport"); //$NON-NLS-1$
		case ContextLinkAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedStereotypeParser("context"); //$NON-NLS-1$
		}
		return super.getParser(visualID);
	}
}