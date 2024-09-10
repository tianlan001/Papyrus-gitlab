/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.custom;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.PapyrusXTextEditor;

/**
 * This class allows to get error markers in the {@link PapyrusXTextEditor}.
 * Strangely it seems work fine doing nothing
 */
public class PapyrusXTextMarker extends org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel {



	/**
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#retrieveMarkers()
	 *
	 * @return
	 * @throws CoreException
	 */
	@Override
	protected IMarker[] retrieveMarkers() throws CoreException {
		return new IMarker[0];
	}

	/**
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#deleteMarkers(org.eclipse.core.resources.IMarker[])
	 *
	 * @param markers
	 * @throws CoreException
	 */
	@Override
	protected void deleteMarkers(IMarker[] markers) throws CoreException {
		// nothing to do
	}

	/**
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#listenToMarkerChanges(boolean)
	 *
	 * @param listen
	 */
	@Override
	protected void listenToMarkerChanges(boolean listen) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#isAcceptable(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 * @return
	 */
	@Override
	protected boolean isAcceptable(IMarker marker) {
		// nothing to do, no idea about true or false...
		return false;
	}
}