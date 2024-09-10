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
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSElementContext;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css.theme.ThemeManager;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.w3c.dom.Element;

/**
 * A Singleton CSSEngine, handling the CSS stylesheets applied to the whole
 * workspace.
 *
 * This Engine is a child of the BaseCSSEngine.
 *
 * It should not be used directly.
 *
 * @author Camille Letavernier
 *
 * @see DiagramCSSEngine
 */
public class WorkspaceCSSEngine extends ExtendedCSSEngineImpl {

	private WorkspaceCSSEngine() {
		super(BaseCSSEngine.INSTANCE);
	}

	public static final ExtendedCSSEngine instance = new WorkspaceCSSEngine();

	@Override
	protected void reloadStyleSheets() {
		styleSheetURLs.clear();
		styleSheets.clear();
		for (StyleSheet styleSheet : ThemeManager.instance.getWorkspaceStyleSheets()) {

			if (styleSheet instanceof StyleSheetReference) {
				IFile iFile = FileUtil.getIFile(((StyleSheetReference) styleSheet).getPath());
				if (iFile != null) {
					styleSheets.add(styleSheet);
				} else {
					try {

						URL styleSheetURL = new URL(((StyleSheetReference) styleSheet).getPath());
						styleSheetURLs.add(styleSheetURL);
					} catch (MalformedURLException e) {
						Activator.log.error(e);
					}
				}

			} else {
				styleSheets.add(styleSheet);
			}

		}
	}

	// Unsupported operations. The WorkspaceCSSEngine should never be used directly.

	@Override
	public Element getElement(Object node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElementProvider getElementProvider() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementProvider(IElementProvider elementProvider) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CSSElementContext getCSSElementContext(Object node) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngineImpl#getCascadeScope()
	 *
	 * @return
	 */
	@Override
	public CascadeScope getCascadeScope() {
		return CascadeScope.USER;
	}
}
