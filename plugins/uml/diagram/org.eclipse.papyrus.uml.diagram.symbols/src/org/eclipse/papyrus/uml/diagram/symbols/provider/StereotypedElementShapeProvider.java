/*****************************************************************************
 * Copyright (c) 2012-2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.symbols.provider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.draw2d.ui.render.RenderedImage;
import org.eclipse.gmf.runtime.draw2d.ui.render.factory.RenderedImageFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.service.shape.AbstractShapeProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.shape.ProviderNotificationManager;
import org.eclipse.papyrus.infra.gmfdiag.common.service.shape.ShapeService;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.symbols.Activator;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;
import org.w3c.dom.svg.SVGDocument;

/**
 * This provider is linked to the {@link ShapeService}. It returns the shapes for a given element corresponding to the stereotypes applied on the
 * business element.
 */
public class StereotypedElementShapeProvider extends AbstractShapeProvider {

	private static final String SHAPE_CONSTANT = "shape";

	/** name of the CSS property that manages the enablement of that provider */
	protected static final String SHAPE_STEREOTYPE_PROPERTY = "shapeStereotype";

	/** name of the CSS property that manages the enablement of that provider for decoration */
	protected static final String SHAPE_DECORATION_STEREOTYPE_PROPERTY = "shapeDecorationStereotype";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RenderedImage> getShapes(EObject view) {
		if (!(view instanceof View)) {
			return null;
		}
		View v = (View) view;
		// check the css property for the status (enable or not) of that provider
		if (!isShapeStereotypeEnable(v)) {
			return null;
		}
		
		return doGetShapes(v);
	}

	protected List<RenderedImage> doGetShapes(View view) {
		EObject element = view.getElement();
		if (element instanceof Element) {
			List<RenderedImage> images = new ArrayList<RenderedImage>();
			// it has already been checked that

			Iterator<Stereotype> appliedStereotypes = ((Element) element).getAppliedStereotypes().iterator();
			while (appliedStereotypes.hasNext()) {
				try {
					Stereotype appliedStereotype = appliedStereotypes.next();
					View stereotypeLabel = StereotypeDisplayUtil.getInstance().getStereotypeLabel(view, appliedStereotype);
					if (stereotypeLabel != null && stereotypeLabel.isVisible()) {
						org.eclipse.uml2.uml.Image icon = ElementUtil.getStereotypeImage(((Element) element), appliedStereotype, SHAPE_CONSTANT);
						if (icon != null) {
							if (icon.getLocation() != null && !("".equals(icon.getLocation()))) {
								SVGDocument document = getSVGDocument(icon.getLocation());
								if (document != null) {
									images.add(renderSVGDocument(view, document));
								} else {
									URL url = new URL(icon.getLocation());
									images.add(RenderedImageFactory.getInstance(url));
								}
							}
						}
					}
				} catch (Exception ex) {
					Activator.log.error(ex);
					continue;
				}
			}

			return images;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RenderedImage> getShapesForDecoration(EObject view) {
		if (!(view instanceof View)) {
			return null;
		}
		View v = (View) view;
		// check the css property for the status (enable or not) of that provider
		if (!isShapeDecorationStereotypeEnable(v)) {
			return null;
		}

		return doGetShapesForDecoration(v);
	}

	protected List<RenderedImage> doGetShapesForDecoration(View view) {
		return doGetShapes(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean providesShapes(EObject view) {
		if (!(view instanceof View)) {
			return false;
		}

		View v = (View) view;
		if (!isShapeStereotypeEnable(v)) {
			return false;
		}

		EObject element = v.getElement();
		if (element instanceof Element) {
			// This is an element. does it have stereotypes ? If yes, do the stereotypes have shapes associated ?
			Iterator<Stereotype> appliedStereotypes = ((Element) element).getAppliedStereotypes().iterator();
			while (appliedStereotypes.hasNext()) {

				Stereotype appliedStereotype = appliedStereotypes.next();
				View stereotypeLabel = StereotypeDisplayUtil.getInstance().getStereotypeLabel(v, appliedStereotype);
				if (stereotypeLabel != null && stereotypeLabel.isVisible()) {
					org.eclipse.uml2.uml.Image icon = ElementUtil.getStereotypeImage(((Element) element), appliedStereotype, SHAPE_CONSTANT);

					if (icon != null) {
						if (!"".equals(icon.getLocation()) && icon.getLocation() != null) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns <code>false</code> if the given view specifically removes the support for shapes by stereotypes.
	 * 
	 * @param view
	 *            the view to check style
	 * @return <code>false</code> if the given view specifically removes the support for shapes by stereotypes, otherwise <code>true</code>.
	 */
	private boolean isShapeStereotypeEnable(View view) {
		return NotationUtils.getBooleanValue(view, SHAPE_STEREOTYPE_PROPERTY, true);
	}

	/**
	 * Returns <code>false</code> if the given view specifically removes the support for shapes as decoration by stereotypes.
	 * 
	 * @param view
	 *            the view to check style
	 * @return <code>false</code> if the given view specifically removes the support for shapes as decoration by stereotypes, otherwise <code>true</code>.
	 */
	private boolean isShapeDecorationStereotypeEnable(View view) {
		return NotationUtils.getBooleanValue(view, SHAPE_DECORATION_STEREOTYPE_PROPERTY, true);
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public List<SVGDocument> getSVGDocument(EObject view) {
		if (!(view instanceof View)) {
			return null;
		}

		View v = (View) view;
		if (!isShapeStereotypeEnable(v)) {
			return null;
		}

		EObject element = v.getElement();
		if (element instanceof Element) {
			List<SVGDocument> images = new ArrayList<SVGDocument>();
			// it has already been checked that
			Iterator<Stereotype> appliedStereotypes = ((Element) element).getAppliedStereotypes().iterator();
			while (appliedStereotypes.hasNext()) {

				Stereotype appliedStereotype = appliedStereotypes.next();
				View stereotypeLabel = StereotypeDisplayUtil.getInstance().getStereotypeLabel(v, appliedStereotype);
				if (stereotypeLabel != null && stereotypeLabel.isVisible()) {
					org.eclipse.uml2.uml.Image icon = ElementUtil.getStereotypeImage(((Element) element), appliedStereotype, SHAPE_CONSTANT);
					if (icon != null) {
						if (icon.getLocation() != null && !("".equals(icon.getLocation()))) {
							SVGDocument document = getSVGDocument(icon.getLocation());
							if (document != null) {
								images.add(document);
							}
						}
					}
				}
			}
			return images;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProviderNotificationManager createProviderNotificationManager(DiagramEventBroker diagramEventBroker, EObject view, NotificationListener listener) {
		// retrieve semantic element from the view and add a notification listener on the Type feature if the semantic element is a TypedElement
		if (view == null || !(view instanceof View)) {
			return null;
		}

		StereotypedElementShapeProviderNotificationManager notificationManager = new StereotypedElementShapeProviderNotificationManager(diagramEventBroker, view, listener);
		return notificationManager;
	}

	/**
	 * Notification Manager for the {@link StereotypedElementShapeProvider}.
	 */
	public class StereotypedElementShapeProviderNotificationManager extends ProviderNotificationManager implements NotificationListener {

		/**
		 * Creates a new StereotypedElementShapeProviderNotificationManager.
		 *
		 * @param diagramEventBroker
		 *            event broker specific to the diargam displaying the shapes.
		 * @param view
		 *            the view from which all elements to listen will be computed.
		 * @param listener
		 *            the listener to which notifications will be forwarded.
		 */
		public StereotypedElementShapeProviderNotificationManager(DiagramEventBroker diagramEventBroker, EObject view, NotificationListener listener) {
			super(diagramEventBroker, view, listener);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void registerListeners() {
			if (view == null || !(view instanceof View)) {
				return;
			}
			diagramEventBroker.addNotificationListener(view, this);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispose() {
			if (view == null || !(view instanceof View)) {
				return;
			}
			diagramEventBroker.removeNotificationListener(view, this);
			super.dispose();
		}

		/**
		 * @see org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 *
		 * @param notification
		 */
		@Override
		public void notifyChanged(Notification notification) {
			// TODO

		}



	}

}
