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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;


/**
 * An handler getting the selected element and reporting them in console.
 *
 * @author Cedric dumoulin
 *
 */
public class ShowPreferencesHandler extends AbstractHandler implements IHandler {


	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 * @param event
	 * @return
	 * @throws ExecutionException
	 *
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {


		ITabTooltipPreferences settings = new TabTooltipPreferences();

		showPreferences(settings);


		return null;
	}

	private void showPreferences(ITabTooltipPreferences settings) {

		System.out.println(ITabTooltipPreferences.isTooltipEnable + "=" + settings.isTooltipEnable()); //$NON-NLS-1$
		System.out.println(ITabTooltipPreferences.isTooltipForCurrentTabShown + "=" + settings.isTooltipForCurrentTabShown()); //$NON-NLS-1$
		System.out.println(ITabTooltipPreferences.scaledFactor + "=" + settings.getScaledFactor()); //$NON-NLS-1$
		System.out.println(ITabTooltipPreferences.scaledFactor + "(int)=" + settings.getIntScaledFactor()); //$NON-NLS-1$
		System.out.println(ITabTooltipPreferences.tooltipAutoCloseDelay + "=" + settings.getTooltipAutoCloseDelay()); //$NON-NLS-1$

	}

	/**
	 * Get the name used in the {@link RecordingCommand}. This name will be visible in
	 * undo/redo.
	 *
	 * @return The command name to show.
	 */
	public String getCommandName() {
		return "Read Preferences"; //$NON-NLS-1$
	}


}
