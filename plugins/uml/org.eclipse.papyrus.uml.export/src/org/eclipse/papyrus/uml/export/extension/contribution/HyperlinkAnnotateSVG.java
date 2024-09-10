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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;
import org.eclipse.papyrus.uml.export.util.HTMLUtil;
import org.w3c.dom.Element;


/**
 * The Class HyperlinkAnnotateSVG.
 */
public class HyperlinkAnnotateSVG implements AnnotateSVG {

	/** The Constant IS_DEFAULT_NAVIGATION. */
	private static final String IS_DEFAULT_NAVIGATION = "is_default_navigation";//$NON-NLS-1$
	
	/** The Constant PAPYRUS_HYPER_LINK_PAGE. */
	private static final String PAPYRUS_HYPER_LINK_PAGE = "PapyrusHyperLink_Page";//$NON-NLS-1$

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
		EAnnotation eAnnotation = view.getEAnnotation(PAPYRUS_HYPER_LINK_PAGE);
		if (eAnnotation != null) {
			String string = eAnnotation.getDetails().get(IS_DEFAULT_NAVIGATION);
			if (Boolean.parseBoolean(string)) { // default
				EList<EObject> references = eAnnotation.getReferences();
				if (!references.isEmpty()) {
					EObject eObject = references.get(0);
					if (eObject instanceof Diagram) {
						Diagram diagram = (Diagram) eObject;
						String diagramRelativPath = HTMLUtil.diagramRelativPath(diagram) + "."+ImageFileFormat.SVG.toString();//$NON-NLS-1$
						rectangle.setAttributeNS(null, "onclick","window.parent.postMessage('" + diagramRelativPath + "','*');");//$NON-NLS-1$
						return true;
					}

				}
			}
		}
		return false;
	}

}
