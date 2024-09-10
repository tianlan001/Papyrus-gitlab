/*****************************************************************************
 * Copyright (c) 2009, 2022 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 493430
 *  Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Bug 580838
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.uml.diagram.clazz.custom.command.AssociationClassViewCreateCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.command.CustomDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.helper.ElementHelper;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Property;

/**
 * The Class AssociationClassHelper has in charge to create command to display or to drop an
 * associationclass
 */
public class AssociationClassHelper extends ElementHelper {


	/**
	 * Instantiates a new association class helper.
	 *
	 * @param editDomain
	 *            the edit domain
	 */
	public AssociationClassHelper(TransactionalEditingDomain editDomain) {
		this.editDomain = editDomain;
	}

	/**
	 * Drop association class.
	 *
	 * @param associationClass
	 *            the association class the semantic element
	 * @param viewer
	 *            the viewer
	 * @param diagramPreferencesHint
	 *            the diagram preferences hint
	 * @param location
	 *            the location of the drop
	 * @param containerView
	 *            the container view that will contain the associationClass views
	 *
	 * @return the command
	 */
	public Command dropAssociationClass(AssociationClass associationClass, EditPartViewer viewer, PreferencesHint diagramPreferencesHint, Point location, View containerView) {
		CompositeCommand cc = new CompositeCommand("drop");
		// 0. Obtain list of property to display
		List<Property> endToDisplay = new ArrayList<>(associationClass.getMemberEnds());
		GraphicalEditPart[] endEditPart = new GraphicalEditPart[associationClass.getMemberEnds().size()];
		// 2. for each element create a graphical representation of the type and
		// finally the branch
		Iterator<Property> iteratorProp = endToDisplay.iterator();
		int index = 0;
		while (iteratorProp.hasNext()) {
			// source editPart
			EditPart sourceEditPart = null;
			// end of the association end
			Property currentEnd = iteratorProp.next();
			// look for if an editpart exist for this element
			@SuppressWarnings("unchecked")
			Collection<EditPart> editPartSet = viewer.getEditPartRegistry().values();
			Iterator<EditPart> editPartIterator = editPartSet.iterator();
			while (editPartIterator.hasNext() && sourceEditPart == null) {
				EditPart currentEditPart = editPartIterator.next();
				if ((!(currentEditPart instanceof CompartmentEditPart)) && currentEditPart instanceof GraphicalEditPart && currentEnd.getType().equals(((GraphicalEditPart) currentEditPart).resolveSemanticElement())) {
					endEditPart[index] = (GraphicalEditPart) currentEditPart;
				}
			}
			index += 1;
		}

		// 2. creation of the associationClass Node without semantic element
		// creation of the node
		IAdaptable elementAdapter = new EObjectAdapter(associationClass);
		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, null, ViewUtil.APPEND, true, diagramPreferencesHint);
		CreateCommand associationClassNodeCreationCommand = new CreateCommand(getEditingDomain(), descriptor, containerView);
		cc.compose(associationClassNodeCreationCommand);
		SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) associationClassNodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 100));
		cc.compose(setBoundsCommand);
		IAdaptable sourceAdapter = null;
		IAdaptable targetAdapter = null;
		// 3. creation of the dashed line between the associationClass link
		// and associationClass Node
		// target
		// The Target Type is contains on the first property.
		if (endEditPart[0] == null) {
			// creation of the node
			ViewDescriptor _descriptor = new ViewDescriptor(new EObjectAdapter(endToDisplay.get(0)), Node.class, null, ViewUtil.APPEND, true, diagramPreferencesHint);
			// get the command and execute it.
			CreateCommand endNodeCreationCommand = new CreateCommand(getEditingDomain(), _descriptor, containerView);
			cc.compose(endNodeCreationCommand);
			setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) endNodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 100));
			cc.compose(setBoundsCommand);
			targetAdapter = (IAdaptable) endNodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			targetAdapter = new SemanticAdapter(null, endEditPart[0].getModel());
		}
		// The Source Type is contains on the second property.
		if (endEditPart[1] == null) {
			// creation of the node
			ViewDescriptor _descriptor = new ViewDescriptor(new EObjectAdapter(endToDisplay.get(1)), Node.class, null, ViewUtil.APPEND, true, diagramPreferencesHint);
			// get the command and execute it.
			CreateCommand endNodeCreationCommand = new CreateCommand(getEditingDomain(), _descriptor, containerView);
			cc.compose(endNodeCreationCommand);
			setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) endNodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y + 100));
			cc.compose(setBoundsCommand);
			sourceAdapter = (IAdaptable) endNodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			sourceAdapter = new SemanticAdapter(null, endEditPart[1].getModel());
		}
		// create association link
		SemanticElementAdapter adapter = new SemanticElementAdapter(associationClass, UMLElementTypes.AssociationClass_Edge);
		ConnectionViewDescriptor viewDescriptor = new ConnectionViewDescriptor(adapter, ((IHintedType) UMLElementTypes.AssociationClass_Edge).getSemanticHint(), diagramPreferencesHint);
		// Creation of the associationLink
		CustomDeferredCreateConnectionViewCommand associationcClassLinkCommand = new CustomDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) UMLElementTypes.AssociationClass_Edge).getSemanticHint(), sourceAdapter, targetAdapter, viewer,
				diagramPreferencesHint, viewDescriptor, null);
		associationcClassLinkCommand.setElement(associationClass);
		cc.compose(associationcClassLinkCommand);
		// creation of the dashedLine
		ConnectionViewDescriptor dashedLineViewDescriptor = new ConnectionViewDescriptor(UMLElementTypes.AssociationClass_TetherEdge, ((IHintedType) UMLElementTypes.AssociationClass_TetherEdge).getSemanticHint(), diagramPreferencesHint);
		CustomDeferredCreateConnectionViewCommand dashedLineCmd = new CustomDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) UMLElementTypes.AssociationClass_TetherEdge).getSemanticHint(),
				((IAdaptable) associationcClassLinkCommand.getCommandResult()
						.getReturnValue()),
				((IAdaptable) associationClassNodeCreationCommand.getCommandResult().getReturnValue()), viewer, diagramPreferencesHint, dashedLineViewDescriptor, null);
		dashedLineCmd.setElement(associationClass);
		cc.compose(dashedLineCmd);
		return new ICommandProxy(cc);
	}

	/**
	 * This method is used to produce a set of commands in charge of the creation of
	 * AssociationClass.
	 *
	 * @param createConnectionViewAndElementRequest
	 *            the create connection view and element request
	 * @param command
	 *            the command that is used to create the link associationClass
	 *
	 * @return the set of command to create an association Class
	 */
	public Command getAssociationClassElementCommand(CreateConnectionViewAndElementRequest createConnectionViewAndElementRequest, Command command) {
		if (command == null || false == createConnectionViewAndElementRequest.getSourceEditPart() instanceof GraphicalEditPart) {
			return UnexecutableCommand.INSTANCE;
		}
		GraphicalEditPart sourceEditPart = (GraphicalEditPart) createConnectionViewAndElementRequest.getSourceEditPart();
		// 1. calculus of the position of the associationClass node
		Point p = sourceEditPart.getFigure().getBounds().getTopRight().getCopy();
		sourceEditPart.getFigure().translateToAbsolute(p);
		int edgeCount = sourceEditPart.getNotationView().getSourceEdges().size();
		int offset = (edgeCount * 50) - 100;
		p = p.translate(100, offset);
		// 2. creation of the associationClass Node without semantic element
		GraphicalEditPart parent = (GraphicalEditPart) sourceEditPart.getParent();
		AssociationClassViewCreateCommand assCommand = new AssociationClassViewCreateCommand(createConnectionViewAndElementRequest, getEditingDomain(), (View) parent.getModel(), sourceEditPart.getViewer(), sourceEditPart.getDiagramPreferencesHint(), p);
		command = command.chain(new ICommandProxy(assCommand));
		// 3. creation of the dashed line between the associationClass link
		// and associationClass Node
		// target
		IAdaptable associationClassLinkViewAdapter = (IAdaptable) (createConnectionViewAndElementRequest.getNewObject());
		String semanticHint = ((IHintedType) UMLElementTypes.AssociationClass_TetherEdge).getSemanticHint();
		ConnectionViewDescriptor viewDescriptor = new ConnectionViewDescriptor(UMLElementTypes.AssociationClass_TetherEdge, semanticHint, sourceEditPart.getDiagramPreferencesHint());
		ICommand dashedLineCmd = new CustomDeferredCreateConnectionViewCommand(getEditingDomain(), semanticHint, associationClassLinkViewAdapter, null, sourceEditPart.getViewer(),
				sourceEditPart.getDiagramPreferencesHint(), viewDescriptor, assCommand);
		command = command.chain(new ICommandProxy(dashedLineCmd));
		return command;
	}
}
