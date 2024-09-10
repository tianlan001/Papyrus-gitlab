/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 461629
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusDiagramEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.IPapyrusElementProvider;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.w3c.dom.Element;

/**
 * A CSS Engine associated to a Diagram.
 *
 * This Engine is a child of a ModelCSSEngine.
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public class DiagramCSSEngine extends ExtendedCSSEngineImpl implements IChangeListener {

	private CSSDiagram diagram;

	/**
	 * Constructor
	 *
	 * Builds a DiagramCSSEngine for a given CSSDiagram
	 *
	 * @param parent
	 *            The diagram's parent CSSEngine. Its stylesheets will be inherited
	 * @param diagram
	 *            This engine's diagram
	 */
	public DiagramCSSEngine(ExtendedCSSEngine parent, CSSDiagram diagram) {
		super(parent);
		this.diagram = diagram;

		setElementProvider(new ElementProviderWrapper(diagram));
	}

	@Override
	protected void reloadStyleSheets() {
		styleSheets.clear();
		for (StyleSheet styleSheet : diagram.getStyleSheets()) {
			// Do not call super#addStyleSheet(styleSheet) to avoid a StackOverFlow
			styleSheets.add(styleSheet);
		}
	}

	@Override
	protected void parseStyleSheet(StyleSheetReference styleSheet) throws IOException {
		String path = styleSheet.getPath();
		if (null != path) {
			if (path.startsWith("/")) { // Either plug-in or workspace
				path = "platform:/resource" + path;
				URL url = new URL(path);
				try {
					url.openConnection();
				} catch (IOException ex) {
					path = "platform:/plugin" + styleSheet.getPath();
				}
			} else {
				URI uri = URI.createURI(styleSheet.getPath());
				if (uri.isRelative()) {
					uri = uri.resolve(diagram.eResource().getURI());
				}

				path = uri.toString();
			}
			URL url = new URL(path);
			parseStyleSheet(url.openStream());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Returns the GMF Notation EObject
	 */
	@Override
	public EObject getNativeWidget(Object element) {
		element = super.getNativeWidget(element);

		if (element == null) {
			return null;
		}

		if (!(element instanceof EObject)) {
			throw new IllegalArgumentException("Unknown element : " + element);
		}

		return (EObject) element; // GMFElement
	}

	@Override
	public void handleChange(ChangeEvent event) {
		resetCache();

		if (diagram != null) {
			for (DiagramEditPart next : PapyrusDiagramEditPart.getDiagramEditPartsFor(diagram)) {
				DiagramHelper.forceRefresh(next);
			}
		}
	}


	@Override
	public Element getElement(Object node) {
		if (node == null) {
			return null;
		}

		EObject notationElement = getNativeWidget(node);
		IElementProvider elementProvider = getElementProvider();


		View canonicalNotationElement = null;
		if (elementProvider instanceof IPapyrusElementProvider) {
			canonicalNotationElement = ((IPapyrusElementProvider) elementProvider).getPrimaryView(notationElement);


			// Orphaned view
			if (canonicalNotationElement.getDiagram() == null) {
				return null;
			}
		}

		// A View and a Compartment associated to the same Semantic Element
		// must have the same XML Element. They share the same children.
		// This is required to map the Semantic model (Used by the CSS selectors)
		// to the Notation model (Used by the CSS properties)
		return super.getElement(canonicalNotationElement);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngineImpl#getCascadeScope()
	 *
	 * @return
	 */
	@Override
	public CascadeScope getCascadeScope() {
		return CascadeScope.AUTHOR;
	}



}
