/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal.preferences;



public interface ITabTooltipPreferences {

	/**
	 * Constant used in Eclipse preferences
	 */
	public String isTooltipEnable = "isTooltipEnable"; //$NON-NLS-1$
	public String isTooltipForCurrentTabShown = "isTooltipForCurrentTabShown"; //$NON-NLS-1$
	public String scaledFactor = "scaledFactor"; //$NON-NLS-1$
	public String tooltipAutoCloseDelay = "tooltipAutoCloseDelay"; //$NON-NLS-1$


	/**
	 * Is the tooltip Enable ?
	 * If true, tooltip are displayed.
	 * If false, they are not displayed.
	 *
	 * @return
	 */
	public abstract boolean isTooltipEnable();

	/**
	 * Is an tooltip shown when the flied tabs is the tab for the cuurently shown diagram ?
	 * If true, the tooltip is shown.
	 * If false, the tooltip is not shown.
	 *
	 * @return
	 */
	public abstract boolean isTooltipForCurrentTabShown();

	/**
	 *
	 * @return
	 */
	public abstract float getScaledFactor();

	/**
	 *
	 * @return
	 */
	public int getIntScaledFactor();

	/**
	 * Return the delay used to close automatically the tooltip.
	 *
	 * @return The delay in millisecond or -1 if there is no delay.
	 */
	public abstract int getTooltipAutoCloseDelay();

}
