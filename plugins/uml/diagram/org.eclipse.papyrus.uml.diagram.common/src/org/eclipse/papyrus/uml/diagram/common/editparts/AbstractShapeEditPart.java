/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import java.net.MalformedURLException;
import java.util.Iterator;

import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.editorsfactory.PageIconsRegistry;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ShapeNamedElementFigure;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Image;
import org.eclipse.uml2.uml.Stereotype;

/**
 * this is an abstract class used to display an element as a shape. it looks for
 * image in the associated stereotype
 *
 */
public abstract class AbstractShapeEditPart extends UMLNodeEditPart implements IPapyrusEditPart {

	private static final String SHAPE_CONSTANT = "shape";//$NON-NLS-1$

	public AbstractShapeEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createMainFigure() {
		return null;
	}

	private IPageIconsRegistry editorRegistry;

	/**
	 * Return the EditorRegistry for nested editor descriptors. Subclass should
	 * implements this method in order to return the registry associated to the
	 * extension point namespace.
	 *
	 * @return the EditorRegistry for nested editor descriptors
	 *
	 */
	protected IPageIconsRegistry createEditorRegistry() {
		try {
			return ServiceUtilsForEditPart.getInstance().getService(IPageIconsRegistry.class, this);
		} catch (ServiceException e) {
			// Not found, return an empty one which return null for each
			// request.
			return new PageIconsRegistry();
		} catch (NullPointerException e) {
			// if the editor is null null pointer exception is raised
			// Not found, return an empty one which return null for each
			// request.
			return new PageIconsRegistry();
		}
	}

	/**
	 * Get the EditorRegistry used to create editor instances. This default
	 * implementation return the singleton eINSTANCE. This method can be
	 * subclassed to return another registry.
	 *
	 * @return the singleton eINSTANCE of editor registry
	 *
	 */
	protected IPageIconsRegistry getEditorRegistry() {
		if (editorRegistry == null) {
			editorRegistry = createEditorRegistry();
		}
		return editorRegistry;
	}

	/**
	 * get the figure
	 *
	 * @return
	 */
	@Override
	public abstract ShapeNamedElementFigure getPrimaryShape();

	@Override
	public Element getUMLElement() {
		return (Element) resolveSemanticElement();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshIcons();
		refreshTransparency();
	}


	/**
	 * refresh the icon by taking in account the type of the diagram
	 */
	private void refreshIcons() {

		Iterator<Stereotype> iter = getUMLElement().getAppliedStereotypes().iterator();
		// FIXME For the time being the first stereotype Icon is Used. But should be improved
		// when the user would be able to choose manually the Icon to applied.
		Stereotype stereotype = iter.next();
		Image icon = ElementUtil.getStereotypeImage(getUMLElement(), stereotype, SHAPE_CONSTANT);
		if (icon != null) {
			ShapeNamedElementFigure shape = getPrimaryShape();
			if (shape != null) {
				if (icon.getLocation() != null && !icon.getLocation().isEmpty()) {
					try {


						shape.setIcon(icon.getLocation());

					} catch (MalformedURLException e) {

						Activator.log.error(String.format("%s %s", icon.getLocation(), e.getLocalizedMessage()), e);
					}
				} else {
					shape.setIcon(Activator.getShape(getUMLElement(), stereotype, false));
				}
			}
		}

	}
}
