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
 *  Mickael adam (ALL4TEC) mickael.adam@all4tec.net - Add of userAgentStyleSheet extension point
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSElementContext;
import org.eclipse.papyrus.infra.gmfdiag.common.handler.IRefreshHandlerPart;
import org.eclipse.papyrus.infra.gmfdiag.common.handler.RefreshHandler;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;
import org.eclipse.ui.IEditorPart;
import org.w3c.dom.Element;

/**
 * The base CSS Engine. It contains the default stylesheet, which will be applied
 * in all cases (With the lowest priority).
 *
 * It should not be used directly.
 *
 * @author Camille Letavernier
 *
 * @see DiagramCSSEngine
 */
public class BaseCSSEngine extends ExtendedCSSEngineImpl implements IRefreshHandlerPart {

	/** Extension point ID. */
	public static final String EXTENSION_ID = Activator.PLUGIN_ID + ".userAgentStyleSheet"; //$NON-NLS-1$

	private BaseCSSEngine() {
		RefreshHandler.register(this);
		try {
			styleSheetURLs.add(new URL("platform:/plugin/" + Activator.PLUGIN_ID + "/resources/base.css")); //$NON-NLS-1$ //$NON-NLS-2$
			// Loads contribution from extension point
			loadCSSContributions();
		} catch (MalformedURLException ex) {
			Activator.log.error(ex);
		}
	}

	/**
	 * The Singleton instance of BaseCSSEngine
	 */
	public static final ExtendedCSSEngine INSTANCE = new BaseCSSEngine();

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.handler.IRefreshHandlerPart#refresh(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void refresh(IEditorPart editorPart) {
		// Resets this engine, regardless of the current editor
		this.reset();
	}

	// Unsupported operations. The BaseCSSEngine should never be used directly.

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
		return CascadeScope.USER_AGENT;
	}

	/**
	 * Load CSS contributions from extension point userAgentStyleSheet.
	 */
	private void loadCSSContributions() {

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (IConfigurationElement cssConfig : config) {
			// Get the path attribute
			String path = cssConfig.getAttribute("stylesheetPath"); //$NON-NLS-1$
			try {
				// construct the URL
				URL url = new URL("platform:/plugin/" + cssConfig.getContributor().getName() + "/" + path);//$NON-NLS-1$ //$NON-NLS-2$
				// add it the the styleSheet
				styleSheetURLs.add(url);

			} catch (IOException ex) {
				Activator.log.error(ex);
			}
		}
	}
}
