/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.providers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.helper.XtextStylesheetHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ruleset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.stylesheet;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;


public class ExistingStyleContentProvider implements IHierarchicContentProvider {

	protected Map<StyleSheet, stylesheet> stylesheets;

	protected final View context;

	public ExistingStyleContentProvider(View context) {
		this.context = context;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Collection<StyleSheet> stylesheets = getStyleSheets();
		if (stylesheets.isEmpty()) {
			// Display a message to let the user know why he cannot edit a stylesheet
			return new Object[] { "No stylesheet available" };
		}
		return stylesheets.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof StyleSheet) {
			stylesheet xtextStylesheet = stylesheets.get(parentElement);

			return xtextStylesheet.getRuleset().toArray();
		} else {
			return new Object[0];
		}
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof stylesheet) {
			return null;
		}

		if (element instanceof ruleset) {
			return ((ruleset) element).eContainer();
		}

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public boolean isValidValue(Object element) {
		return element instanceof ruleset;
	}

	protected Collection<StyleSheet> getStyleSheets() {
		if (stylesheets == null) {
			stylesheets = new LinkedHashMap<StyleSheet, stylesheet>();
			if (context.getDiagram() instanceof CSSDiagram) {
				CSSDiagram diagram = (CSSDiagram) context.getDiagram();
				parseStyleSheets(diagram.getStyleSheets());
			}
		}
		return stylesheets.keySet();
	}

	protected void parseStyleSheets(List<StyleSheet> appliedStylesheets) {
		for (StyleSheet stylesheet : appliedStylesheets) {
			if (stylesheet instanceof StyleSheetReference) {
				parseStyleSheet((StyleSheetReference) stylesheet);
			} else if (stylesheet instanceof EmbeddedStyleSheet) {
				// Unsupported yet
			}
		}
	}

	protected void parseStyleSheet(StyleSheetReference stylesheet) {
		Resource resource = XtextStylesheetHelper.loadStylesheet(stylesheet, null, context, null);
		if (resource.getContents().isEmpty()) {
			return;
		}

		for (EObject rootElement : resource.getContents()) {
			if (rootElement instanceof stylesheet) {
				stylesheets.put(stylesheet, (stylesheet) rootElement);
			}
		}
	}

}
