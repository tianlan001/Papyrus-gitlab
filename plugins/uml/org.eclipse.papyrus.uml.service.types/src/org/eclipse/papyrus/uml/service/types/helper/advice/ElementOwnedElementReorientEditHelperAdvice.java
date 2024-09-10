package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.command.ElementOwnedElementReorientCommand;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;


public class ElementOwnedElementReorientEditHelperAdvice extends AbstractFeatureRelationshipReorientEditHelperAdvice {

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractFeatureRelationshipReorientEditHelperAdvice#elementTypeIDToSpecialize()
	 *
	 * @return
	 */
	@Override
	protected String elementTypeIDToSpecialize() {
		return UMLElementTypes.ELEMENT_OWNEDELEMENT.getId();
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractFeatureRelationshipReorientEditHelperAdvice#getReorientCommand()
	 *
	 * @return
	 */
	@Override
	protected ICommand getFeatureRelationshipReorientCommand(ReorientReferenceRelationshipRequest request) {
		return new ElementOwnedElementReorientCommand(request);
	}
}
