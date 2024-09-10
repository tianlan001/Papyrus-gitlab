/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.dnd.strategy;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Type;

/**
 * This strategy is is a specialization in order to be able to drop a Type as a Port in a Classifier
 */
public class TypeToClassDropStrategy extends TransactionalDropStrategy {

	@Override
	public String getLabel() {
		return "Drop as Port";
	}

	@Override
	public String getID() {
		return "org.eclipse.papyrus.uml.diagram.parametric.dnd.PortToTypesPortDropStrategy"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "This strategy is is a specialization in order to be able to drop a Type as a Port in a Classifier.";
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getImage()
	 *
	 * @return
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getPriority()
	 *
	 * @return
	 * @deprecated
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public Command doGetCommand(Request request, final EditPart targetEditPart) {
		if (request instanceof DropObjectsRequest) {
			List<EObject> sourceElements = getSourceEObjects(request);
			if (!sourceElements.isEmpty() && targetEditPart instanceof GraphicalEditPart) {
				GraphicalEditPart graphicalEditPart = (GraphicalEditPart) targetEditPart;
				EObject semanticElement = graphicalEditPart.resolveSemanticElement();
				if (semanticElement instanceof org.eclipse.uml2.uml.Class) {
					org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) semanticElement;
					EObject eObject = sourceElements.get(0);
					if (eObject instanceof Type) {
						if (graphicalEditPart instanceof ClassCompositeEditPart) {
							return getDropTypeOnClassifierCommand((ClassCompositeEditPart) graphicalEditPart, (Type) eObject, clazz);
						} else if (graphicalEditPart.getParent() instanceof ClassCompositeEditPart) {
							return getDropTypeOnClassifierCommand((ClassCompositeEditPart) graphicalEditPart.getParent(), (Type) eObject, clazz);
						}
					}
				}
			}
		}
		return null; // UnexecutableCommand.INSTANCE;
	}

	private Command getDropTypeOnClassifierCommand(ClassCompositeEditPart classCompositeEditPart, Type type, org.eclipse.uml2.uml.Class clazz) {
		Command cmd = new Command() {
			@Override
			public void execute() {
				Port port = clazz.createOwnedPort(type.getName(), type);
				ViewService.createNode(classCompositeEditPart.getNotationView(), port, "Port_Shape", classCompositeEditPart.getDiagramPreferencesHint());
			}

		};
		return cmd;
	}

}
