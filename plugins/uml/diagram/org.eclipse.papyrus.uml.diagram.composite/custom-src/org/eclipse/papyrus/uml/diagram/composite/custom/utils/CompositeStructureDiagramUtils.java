/*****************************************************************************
 * Copyright (c) 2014, 2017, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 493869, 513067
 *  Ansgar Radermacher - move & adapt from PapyrusRT to base Papyrus, bug 527181
 *  Asma Smaoui - bug 527181
 *  Ansgar Radermacher - handle NPE in 527181
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.utils;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.helper.RelativePortLocation;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * Utility method for composite structure diagrams
 *
 * @since 3.1
 */
public class CompositeStructureDiagramUtils {

	/**
	 * Returns <code>true</code> if the diagram is a composite structure diagram (and has a class as base element)
	 *
	 * @param diagram
	 *            the diagram to check
	 * @return <code>true</code> if the diagram is a composite capsule structure diagram
	 */
	public static boolean isCompositeStructureDiagram(Diagram diagram) {
		if (diagram == null) {
			return false;
		}
		if (CompositeStructureDiagramEditPart.MODEL_ID.equals(diagram.getType())) {
			EObject businessElement = diagram.getElement();
			if (businessElement instanceof org.eclipse.uml2.uml.Class) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtains the Composite Structure Diagram for a class
	 *
	 * @param component
	 *            b * @return its Composite Structure Diagram, or {@code null} if none is currently known (perhaps
	 *            because it is in a resource that is not yet loaded)
	 */
	public static Diagram getCompositeStructureDiagram(Class component) {
		return DiagramEditPartsUtil.getDiagram(component, CompositeStructureDiagramUtils::isCompositeStructureDiagram);
	}

	/**
	 * Obtains the first view of the Type (here the Class) in any diagrams
	 *
	 * @param typingClass:
	 *            the type of the Part
	 *
	 */
	public static View getPartTypeFirstView(Class typingClass) {
		View view = null;
		try {
			ServicesRegistry servicesRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(typingClass);
			ModelSet modelSet = servicesRegistry.getService(ModelSet.class);
			NotationModel notation = (NotationModel) modelSet.getModel(NotationModel.MODEL_ID);
			List<Diagram> diagrams = notation.getResource().getContents().stream()
					.map(Diagram.class::cast)
					.collect(Collectors.toList());

			for (Diagram d : diagrams) {
				for (Iterator children = d.getChildren().iterator(); children.hasNext();) {
					View child = (View) children.next();
					EObject semanticChild = ViewUtil.resolveSemanticElement(child);
					if (semanticChild != null && semanticChild.equals(typingClass)) {
						return child;
					}
				}
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}
		return view;
	}

	/**
	 * @param partEditPart
	 *            the edit part of an element
	 * @return the type (if it is a UML Class) for a given partEditPart
	 */
	public static Class getPartType(EditPart partEditPart) {
		Object modelObj = partEditPart.getModel();
		if (modelObj instanceof View) {
			View model = (View) modelObj;
			EObject elementEObj = model.getElement();
			if (elementEObj instanceof Property) {
				Type type = ((Property) elementEObj).getType();
				if (type instanceof Class) {
					return (Class) type;
				}
			}
		}
		return null;
	}

	/**
	 * Return the initial position of a port on a part.
	 *
	 * @param partEditPart
	 *            edit part of the part within a composite (for which we want to display a port)
	 * @param port
	 *            the semantic UML2 port which we want to display
	 * @return the initial location of the port or null (if none could be determined)
	 */
	public static Point getInitialPortLocation(EditPart partEditPart, EObject port, ViewDescriptor descriptor) {
		Class typingClass = getPartType(partEditPart);
		View typingClassView = null;
		Diagram d = null;
		if (typingClass != null) {
			d = CompositeStructureDiagramUtils.getCompositeStructureDiagram(typingClass);
			// look for another diagram if no owning composite diagram
			if (d != null) {
				typingClassView = ViewUtil.getChildBySemanticHint(d, ClassCompositeEditPart.VISUAL_ID);
			} else {
				typingClassView = getPartTypeFirstView(typingClass);
			}
			if (typingClassView != null) {
				// we can only continue, if an view of the typing class is found.
				View portOnTypingClass = null;
				for (Object child : typingClassView.getChildren()) {
					if (child instanceof View && ((View) child).getElement() == port) {
						portOnTypingClass = (View) child;
						break;
					}
				}
				if (portOnTypingClass != null) {
					Bounds typingClassBounds = TypeUtils.as(((Node) typingClassView).getLayoutConstraint(), Bounds.class);
					Bounds portOnTypingClassBounds = TypeUtils.as(((Node) portOnTypingClass).getLayoutConstraint(), Bounds.class);
					int portWidth = portOnTypingClassBounds.getWidth();
					int portHeight = portOnTypingClassBounds.getHeight();
					Rectangle r = new Rectangle(portOnTypingClassBounds.getX(), portOnTypingClassBounds.getY(), portWidth, portHeight);
					// use 0, 0 as starting point, since the port coordinates are relative to the part.
					RelativePortLocation relative = RelativePortLocation.of(r, new Rectangle(0, 0, typingClassBounds.getWidth(), typingClassBounds.getHeight()));
					final Rectangle partBounds = ((NodeEditPart) partEditPart).getFigure().getBounds();

					return relative.applyTo(partBounds, new Dimension(portWidth, portHeight));
				}
			}
		}
		return null;
	}
}
