/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST, EclipseSource and others.
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
 *  Camille Letavernier (EclipseSource) - Bug 519412
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.listener.StyleSheetChangeListener;
import org.eclipse.papyrus.infra.gmfdiag.css.lists.ExtendedStyleSheetList;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.stylesheets.StyleSheet;

/**
 * An extended, lazy version of a CSSEngine
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public interface ExtendedCSSEngine extends LazyCSSEngine, CSSEngine {

	/**
	 * Adds a listener to be notified each time a StyleSheet has changed
	 *
	 * @param listener
	 */
	public void addStyleSheetChangeListener(StyleSheetChangeListener listener);

	/**
	 * Removes a StyleSheetChangeListener
	 *
	 * @param listener
	 */
	public void removeStyleSheetChangedListener(StyleSheetChangeListener listener);

	/**
	 * @return The list of all stylesheets for this Engine, including the
	 *         parent ones
	 */
	public ExtendedStyleSheetList getAllStylesheets();

	/**
	 * {@inheritDoc}
	 *
	 * This method never throws an Exception
	 */
	@Override
	public Object convert(CSSValue cssValue, Object toType, Object context);

	/**
	 * {@inheritDoc}
	 *
	 * Should be called when the StyleSheets have changed
	 */
	@Override
	public void reset();

	/**
	 * Resets the CSS cache
	 *
	 * Should be called when the semantic model has changed.
	 */
	public void resetCache();

	/**
	 * Notifies a change from the given Element
	 *
	 * @param element
	 */
	public void notifyChange(Element element);

	/**
	 * Notifies the Engine that the given widget has been disposed
	 *
	 * @param nativeWidget
	 */
	public void handleDispose(Object nativeWidget);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Element getElement(Object node);


	/**
	 * Returns the scope of this Stylesheet Engine
	 *
	 * @see {@link http://www.w3.org/TR/CSS2/cascade.html#cascading-order}
	 *
	 * @return
	 */
	public CascadeScope getCascadeScope();

	/**
	 * Returns the {@link CascadeScope} associated to the given stylesheet
	 *
	 * @param stylesheet
	 * @return
	 */
	public CascadeScope getCascadeScope(StyleSheet stylesheet);

	/**
	 * Scope of the CSSEngine, defining its priority level. Based on the W3C levels, with Papyrus-specific additions.
	 * Priority is: {@link #USER_AGENT} &lt; {@link #USER} &lt; {@link #VIEWPOINT} &lt; {@link #AUTHOR}.
	 * Rules defined in a lower priority scope engine will always be overridden by rules from a higher priority scope engine.
	 * If the scope is equal, then the priority is based on the CSS Rule selector complexity, as defined by the W3C.
	 */
	public enum CascadeScope {
		/**
		 * The User Agent level, defining tool-specific rules (Mostly default values).
		 * This is the lowest priority level.
		 */
		USER_AGENT(0),

		/**
		 * The User level, defining general user-specific rules. This is used for e.g. Workspace-level Theme.
		 */
		USER(1),

		/**
		 * The Viewpoint level, defining context-specific rules.
		 * This is used for Architecture Context/Viewpoint customization. The priority is higher
		 * than {@link #USER}, but lower than {@link #AUTHOR}
		 * 
		 * @since 2.2
		 */
		//Added in Bug 519412: Viewpoint Stylesheets have a very high priority
		VIEWPOINT(2),

		/**
		 * Project-, Model- or Diagram-specific rules. These rules have the highest priority
		 */
		AUTHOR(3);

		/**
		 * The integer priority level of this scope. Lower value means lower priority
		 * 
		 * @return
		 * 		The priority
		 */
		public int getOrder() {
			return order;
		}

		private int order;

		private CascadeScope(int order) {
			this.order = order;
		}

	}

}
