/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - support adapter instead of custom resource impl for CSS (CDO)
 *  Christian W. Damus - bugs 433206, 464443, 436665
 *  Camille Letavernier (EclipseSource) - Bug 519412
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.notation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.EObjectListValueStyle;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.DiagramCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ViewpointCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.resource.CSSNotationResource;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSView;
import org.eclipse.papyrus.infra.gmfdiag.css.style.impl.CSSViewDelegate;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;

/**
 * Default implementation for CSSDiagram
 *
 * @author Camille letavernier
 */
@SuppressWarnings("restriction")
public class CSSDiagramImpl extends DiagramImpl implements CSSDiagram, CSSView.Internal {

	protected ExtendedCSSEngine engine;
	private ExtendedCSSEngine viewpointEngine;

	private CSSView cssView;

	@Override
	public ExtendedCSSEngine getEngine() {
		if (engine == null) {
			ExtendedCSSEngine modelEngine = getModelEngine();
			viewpointEngine = new ViewpointCSSEngine(modelEngine, this);
			engine = createEngine(viewpointEngine);
		}
		return engine;
	}

	/**
	 * Create the DiagramCSS
	 * @param viewpointCSSEngine
	 * @return
	 */
	protected ExtendedCSSEngine createEngine(ExtendedCSSEngine viewpointCSSEngine) {
		return new DiagramCSSEngine(viewpointEngine, this);
	}

	@Override
	public void resetCSS() {
		if (engine != null) {
			cssView = null;

			engine.dispose();
			engine = null;
			viewpointEngine.dispose();
			viewpointEngine = null;

			// And walk our contents to make all views forget their engine
			eAllContents().forEachRemaining(o -> {
				if (o instanceof CSSView.Internal) {
					((CSSView.Internal) o).resetCSS();
				}
			});
		}
	}

	@Override
	protected void eSetDirectResource(Resource.Internal resource) {
		Resource.Internal oldResource = eInternalResource();

		super.eSetDirectResource(resource);

		if (oldResource != resource) {
			// My engine is now invalid because my resource is different than before,
			// which makes its parent engine is obsolete
			resetCSS();
		}
	}

	protected ExtendedCSSEngine getModelEngine() {
		Resource resource = eResource();
		return (resource == null) ? null : CSSNotationResource.getEngine(resource);
	}

	@Override
	public List<StyleSheet> getStyleSheets() {
		List<StyleSheet> result = new LinkedList<>();

		for (Object styleObject : getStyles()) {
			if (styleObject instanceof NamedStyle) {

				NamedStyle style = (NamedStyle) styleObject;

				if (CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY.equals(style.getName())) {
					if (style instanceof EObjectListValueStyle) {

						EObjectListValueStyle stylesheetsStyle = (EObjectListValueStyle) style;

						for (Object eObjectValue : stylesheetsStyle.getEObjectListValue()) {
							if (eObjectValue instanceof StyleSheet) {
								result.add((StyleSheet) eObjectValue);
							}
						}
					}
				}
			}
		}

		return result;
	}



	protected CSSView getCSSView() {
		if (cssView == null) {
			cssView = new CSSViewDelegate(this, getEngine());
		}
		return cssView;
	}

	// //////////////////////////////////
	// Implements the isVisible method //
	// //////////////////////////////////

	@Override
	public boolean isVisible() {
		// return super.isVisible();
		return isCSSVisible();
	}

	@Override
	public boolean isCSSVisible() {
		boolean value = super.isVisible();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getView_Visible(), value)) {
			return value;
		} else {
			return getCSSView().isCSSVisible();
		}
	}

	// //////////////////////////////////////
	// Implements the getNamedStyle method //
	// //////////////////////////////////////

	@Override
	public NamedStyle getNamedStyle(EClass eClass, String name) {
		return getCSSNamedStyle(eClass, name);
	}

	@Override
	public NamedStyle getCSSNamedStyle(EClass eClass, String name) {
		NamedStyle userStyle = super.getNamedStyle(eClass, name);
		if (userStyle != null) {
			return userStyle;
		}

		return getCSSView().getCSSNamedStyle(eClass, name);
	}

	// /////////////////////////////////
	// Implements the getStyle method //
	// /////////////////////////////////

	@Override
	public Style getStyle(EClass eClass) {
		return getCSSStyle(eClass);
	}

	@Override
	public Style getCSSStyle(EClass eClass) {
		Style userStyle = super.getStyle(eClass);
		if (userStyle != null) {
			return userStyle;
		}

		return getCSSView().getCSSStyle(eClass);
	}

}
