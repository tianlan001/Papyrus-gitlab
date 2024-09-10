/****************************************************************************
 * Copyright (c) 2009 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *		Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - moved class behavior to classEditPart
 *      Vincent Lorenzo (CEA-LIST)  - vincent.lorenzo@cea.fr - 464129: [Class Diagram] Italic font style not kept after re-opening any diagrams - italic is now done with CSS
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.helper.BasicClassifierCompartmentLayoutHelper;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Classifier edit part.
 *
 * @author tlandre
 */
public abstract class ClassifierEditPart extends RoundedCompartmentEditPart {

	/**
	 * isAbstract Classifier property
	 */
	private boolean isAbstract;

	/**
	 * Constructor
	 *
	 * @param view
	 */
	public ClassifierEditPart(View view) {
		super(view);
		setCompartmentLayoutHelper(BasicClassifierCompartmentLayoutHelper.getInstances());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);
		Object feature = notification.getFeature();
		if (resolveSemanticElement() != null) {
			// Manage isAbstract
			if (UMLPackage.eINSTANCE.getClassifier_IsAbstract().equals(feature)) {
				isAbstract = notification.getNewBooleanValue();
				refreshFont();
			}
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (getPrimaryShape() != null && resolveSemanticElement() != null) {
			refreshAbstract();
		}
	}

	protected void refreshAbstract() {
		if (getUMLElement() instanceof Classifier) {
			isAbstract = ((Classifier) getUMLElement()).isAbstract();
			refreshFont();

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FontData getFontData(FontStyle style) {
		return super.getFontData(style);

		// next line commented for bug 464129: [Class Diagram] Italic font style not kept after re-opening any diagrams
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=464129
		// new implementation use CSS (umlBase.css)


		// FontData data = super.getFontData(style);
		// if (isAbstract) {
		// data.setStyle(data.getStyle() | SWT.ITALIC); // Force the Italic flag
		// } else {
		// data.setStyle(data.getStyle() & ~SWT.ITALIC); // Remove the Italic flag
		// }
		// return data;
	}

}
