package org.eclipse.papyrus.uml.diagram.statemachine.custom.policies;

import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractUMLShowHideRelatedLinkEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;

/**
 * Show Hide link editpolicy for StateMachine Diagram
 *
 */
public class ShowHideRelatedLinkEditPolicy extends AbstractUMLShowHideRelatedLinkEditPolicy {

	/**
	 *
	 * Constructor.
	 */
	public ShowHideRelatedLinkEditPolicy() {
		super();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IDiagramInformationProviderEditPolicy#getDiagramStructure()
	 *
	 * @return
	 */
	@Override
	public DiagramStructure getDiagramStructure() {
		return UMLVisualIDRegistry.TYPED_INSTANCE;
	}
}