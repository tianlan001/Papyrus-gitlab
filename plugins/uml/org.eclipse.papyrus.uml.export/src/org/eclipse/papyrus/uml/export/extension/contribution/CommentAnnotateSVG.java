/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.extension.contribution;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.NamedElement;
import org.w3c.dom.Element;
import org.w3c.dom.Text;



/**
 * The Class CommentAnnotateSVG.
 */
public class CommentAnnotateSVG implements AnnotateSVG {

	/** The Constant ATTRIBUTE_TITLE. */
	// W3C title attribute
	private static final String ATTRIBUTE_TITLE = "title";//$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.uml.export.extension.AnnotateSVG#addAnnotation(org.eclipse.gmf.runtime.notation.View, org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG, org.w3c.dom.Element)
	 *
	 * @param view
	 * @param svgG
	 * @param rectangle
	 * @return
	 */
	@Override
	public boolean addAnnotation(View view, GraphicsSVG svgG, Element rectangle) {
		EObject eObject = view.getElement();
		if (eObject instanceof NamedElement) {
			NamedElement namedElement = (NamedElement) eObject;
			EList<Comment> ownedComments = namedElement.getOwnedComments();
			if (!ownedComments.isEmpty()) {
				rectangle.setAttributeNS(null, ATTRIBUTE_TITLE, ownedComments.get(0).getBody());
				Element title = svgG.getDocument().createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, ATTRIBUTE_TITLE);
				Text comment = svgG.getDocument().createTextNode(ownedComments.get(0).getBody());
				title.appendChild(comment);
				rectangle.appendChild(title);
				return true;
			}
		}
		return false;
	}

}
