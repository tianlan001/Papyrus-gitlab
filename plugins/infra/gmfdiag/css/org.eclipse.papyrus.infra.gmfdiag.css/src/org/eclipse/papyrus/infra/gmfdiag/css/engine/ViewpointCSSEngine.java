/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Camille Letavernier (EclipseSource) - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsFactory;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * CSSEngine for stylesheets contributed via the architecture context ({@link PapyrusDiagram#getCustomStyle()}).
 * 
 * Although it is manipulating the Diagram, it has a slightly lower priority than the {@link DiagramCSSEngine},
 * which contains user-defined stylesheets.
 * 
 * @since 2.2
 */
//Bug 519412: Viewpoint Stylesheets have a very high priority
public class ViewpointCSSEngine extends ExtendedCSSEngineImpl {

	private CSSDiagram diagram;

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param diagram
	 */
	public ViewpointCSSEngine(ExtendedCSSEngine parent, CSSDiagram diagram) {
		super(parent);
		this.diagram = diagram;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.css.engine.DiagramCSSEngine#reloadStyleSheets()
	 *
	 */
	@Override
	protected void reloadStyleSheets() {
		styleSheets.clear();
		StyleSheet css = getViewpointDefinedStylesheet();
		if (css != null) {
			styleSheets.add(css);
		}
	}

	private StyleSheet getViewpointDefinedStylesheet() {
		ViewPrototype proto = ViewPrototype.get(diagram);
		if (proto == null) {
			return null;
		}
		PapyrusRepresentationKind conf = proto.getRepresentationKind();
		if (conf == null || !(conf instanceof PapyrusDiagram)) {
			return null;
		}
		String path = ((PapyrusDiagram) conf).getCustomStyle();
		if (path == null || path.isEmpty()) {
			return null;
		}
		StyleSheetReference ref = StylesheetsFactory.eINSTANCE.createStyleSheetReference();
		ref.setPath(path);
		return ref;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine#getCascadeScope()
	 *
	 * @return
	 */
	@Override
	public CascadeScope getCascadeScope() {
		return CascadeScope.VIEWPOINT;
	}

}
