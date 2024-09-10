/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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

package org.eclipse.papyrus.editor;

import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.ui.editor.CoreMultiDiagramEditor;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;

/**
 * Papyrus main MultiEditor. This class add GMF adaptation dedicated to Papyrus.
 * <p/>
 * TODO : move GMF dependencies into this plugin.
 *
 * @author dumoulin
 */
public class PapyrusMultiDiagramEditor extends CoreMultiDiagramEditor implements INavigationLocationProvider  {

	/**
	 * The Papyrus Editor ID
	 */
	public static final String EDITOR_ID = "org.eclipse.papyrus.infra.core.papyrusEditor"; //$NON-NLS-1$

	private PapyrusPaletteSynchronizer myPaletteViewSynchronizer;
	
	private PapyrusNavigationHistorySynchronizer myNavigationHistorySynchronizer;

	@Override
	protected void activate() {
		super.activate();
		myPaletteViewSynchronizer = new PapyrusPaletteSynchronizer(this);
		myNavigationHistorySynchronizer = new PapyrusNavigationHistorySynchronizer(this);
		getISashWindowsContainer().addPageChangedListener(myPaletteViewSynchronizer);
		getISashWindowsContainer().addPageChangedListener(myNavigationHistorySynchronizer);
	}

	@Override
	protected void deactivate() {
		if (myPaletteViewSynchronizer != null) {
			ISashWindowsContainer sashContainer = getISashWindowsContainer();
			if (sashContainer != null && !sashContainer.isDisposed()) {
				sashContainer.removePageChangedListener(myPaletteViewSynchronizer);
				sashContainer.removePageChangedListener(myNavigationHistorySynchronizer);
			}
			myPaletteViewSynchronizer.dispose();
			myPaletteViewSynchronizer = null;
		}
		super.deactivate();
	}

	/**
	 * @see org.eclipse.ui.INavigationLocationProvider#createEmptyNavigationLocation()
	 *
	 * @return
	 */
	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.INavigationLocationProvider#createNavigationLocation()
	 *
	 * @return
	 */
	@Override
	public INavigationLocation createNavigationLocation() {
		return new PapyrusNavigationLocation(this);
	}
}
