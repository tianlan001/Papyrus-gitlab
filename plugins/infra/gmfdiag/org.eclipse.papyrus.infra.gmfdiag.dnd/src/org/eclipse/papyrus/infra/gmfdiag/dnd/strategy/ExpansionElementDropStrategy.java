/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.dnd.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.dnd.Activator;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.ChildrenListRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.graphics.Image;

/**
 * This strategy has been added in order allow dropping new element done by using expansion model
 * See Requirement #org.eclipse.papyrus.infra.gmfdiag.expansion.Req_020
 *
 */
public class ExpansionElementDropStrategy extends TransactionalDropStrategy {

	private DiagramExpansionsRegistry diagramExpansionRegistry;

	/**
	 * Constructor.
	 *
	 */
	public ExpansionElementDropStrategy() {
		this.diagramExpansionRegistry = DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry();
	}

	public String getLabel() {
		return "Expansion element drag and drop";
	}

	public String getID() {
		return "org.eclipse.papyrus.infra.gmfdiag.dnd.expansiondropsteategy";
	}

	public String getDescription() {
		return "This strategy is used to allow dropping of expansion of elements in extended diagrams.";
	}

	public String getCategoryID() {
		return "org.eclipse.papyrus.infra.gmfdiag.dnd.expansiondropsteategy";
	}

	public String getCategoryLabel() {
		return "Expansion element drag and drop";
	}	
	
	public Image getImage() {
		return null;
	}

	public int getPriority() {
		return 0;
	}

	public void setOptions(Map<String, Object> options) {
		// Nothing
	}

	/**
	 * get the diagram type from a view.
	 *
	 * @param currentView
	 *            the current view
	 * @return the diagram type it can be also a view point
	 */
	protected String getDiagramType(View currentView) {
		Diagram diagram = currentView.getDiagram();
		String currentDiagramType = null;
		ViewPrototype viewPrototype = org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPrototype(diagram);
		if (viewPrototype != null) {
			currentDiagramType = viewPrototype.getLabel();
		} else {
			currentDiagramType = diagram.getType();
		}
		return currentDiagramType;
	}

	@Override
	public Command doGetCommand(Request request, final EditPart targetEditPart) {

		if (RequestConstants.REQ_MOVE_CHILDREN.equals(request.getType())) {
			return null;
		}		
		
		CompositeCommand cc = new CompositeCommand(getLabel());
		if (targetEditPart instanceof GraphicalEditPart) {
			IGraphicalEditPart graphicalEditPart = (IGraphicalEditPart) targetEditPart;
			String diagramType = getDiagramType(graphicalEditPart.getNotationView());
			ChildrenListRepresentation listRepresentation = diagramExpansionRegistry.mapChildreen.get(diagramType);
			// to the current diagram, a expansion is added.
			if (listRepresentation == null) {
				return null;
			}
			// look for all possible children for the current target.
			List<String> childrenList = null;
			if (graphicalEditPart instanceof DiagramEditPart) {
				childrenList = listRepresentation.parentChildrenRelation.get(diagramType);
			} else {
				childrenList = listRepresentation.parentChildrenRelation.get(graphicalEditPart.getNotationView().getType());
			}

			if (childrenList == null) {
				return null;
			}
			List<EObject> sourceElements = getSourceEObjects(request);
			if (sourceElements.isEmpty()) {
				return null;
			}
			final List<EObject> valuesToAdd = new ArrayList<>(sourceElements.size());
			// getList of accepted ElementType
			final ArrayList<ISpecializationType> acceptedElementTypes = new ArrayList<>();


			for (String posibleID : childrenList) {
				AbstractRepresentation abstractRepresentation = listRepresentation.IDMap.get(posibleID);
				if (abstractRepresentation instanceof Representation) {
					ElementTypeConfiguration graphicalElementTypeRef = ((Representation) abstractRepresentation).getGraphicalElementTypeRef();
					if (graphicalElementTypeRef != null) {
						String elementTypeID = graphicalElementTypeRef.getIdentifier();
						if (elementTypeID != null && !elementTypeID.isEmpty()) {
							final IElementType elementType = ElementTypeRegistry.getInstance().getType(elementTypeID);
							if (elementType instanceof ISpecializationType) {
								acceptedElementTypes.add((ISpecializationType) elementType);
							}
						}						
					}
				}
			}
			Activator.log.trace(Activator.EXPANSION_TRACE, "try to drop " + sourceElements + " inside " + graphicalEditPart.getNotationView().getType() + " accepts " + childrenList); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			// get the sub list of accepted source element that match to elementType
			for (EObject sourceElement : sourceElements) {
				// the source element must be a children of the container
				if (sourceElement.eContainer() == graphicalEditPart.resolveSemanticElement()) {
					Command cmd = null;
					int acceptedElementTypesIndex = 0;
					while (cmd == null && acceptedElementTypesIndex < acceptedElementTypes.size()) {
						final ISpecializationType iSpecializationType = acceptedElementTypes.get(acceptedElementTypesIndex);
						IElementMatcher matcher = iSpecializationType.getMatcher();
						IElementType[] superElementTypes = iSpecializationType.getSpecializedTypes();
						if (matcher == null) {
							int index = superElementTypes.length - 1;
							while (matcher == null && index > 0) {
								if (superElementTypes[index] instanceof ISpecializationType) {
									matcher = ((ISpecializationType) superElementTypes[index]).getMatcher();
								}
								index--;
							}
						}
						if (matcher != null && matcher.matches(sourceElement)) {
							cmd = addCommandDrop(targetEditPart, cc, valuesToAdd, sourceElement, iSpecializationType);
						} else if (matcher == null) {
							EClass eclass = iSpecializationType.getEClass();
							if (eclass.isSuperTypeOf(sourceElement.eClass())) {
								cmd = addCommandDrop(targetEditPart, cc, valuesToAdd, sourceElement, iSpecializationType);
							} else {
								acceptedElementTypesIndex++;
							}
						} else {
							acceptedElementTypesIndex++;
						}

					}

				}
			}
		}

		return cc.canExecute() ? new ICommandProxy(cc.reduce()) : null;
	}

	protected Command addCommandDrop(final EditPart targetEditPart, CompositeCommand cc, final List<EObject> valuesToAdd, EObject sourceElement, final ISpecializationType iSpecializationType) {

		valuesToAdd.add(sourceElement);
		Activator.log.trace(Activator.EXPANSION_TRACE, "try to drop command created for " + sourceElement + " " + iSpecializationType);//$NON-NLS-1$ //$NON-NLS-2$
		Command cmd = new Command() {
			@Override
			public void execute() {
				if (iSpecializationType instanceof IHintedType) {
					ViewService.createNode(((GraphicalEditPart) targetEditPart).getNotationView(), sourceElement, ((IHintedType) iSpecializationType).getSemanticHint(), ((GraphicalEditPart) targetEditPart).getDiagramPreferencesHint());
				}
			}

		};
		cc.add(new CommandProxy(cmd));
		return cmd;
	}



}
