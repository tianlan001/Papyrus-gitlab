/*****************************************************************************
 * Copyright (c) 2019, 2020 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Remi Schnekenburger (EclipseSource) -  Bug 568495
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;

/**
 * This allows to manage markers.
 */
public class MarkersManagementUtils {

	/**
	 * This allows to create a marker for a resource.
	 *
	 * @param resource
	 *            The resource where create the marker.
	 * @param type
	 *            The type of the marker validation.
	 * @param message
	 *            The message of the marker.
	 * @param severity
	 *            The severity of the marker.
	 * @return The created marker or <code>null</code> if there is an error.
	 */
	public static IMarker createMarker(final IResource resource, final String type, final String message, final int severity) {
		IMarker createdMarker = createMarker(resource, type);
		if (createdMarker != null) {
			try {
				createdMarker.setAttribute(IMarker.MESSAGE, message);
				createdMarker.setAttribute(IMarker.SEVERITY, severity);
			} catch (CoreException e) {
				Activator.log.error("Error while setting marker attributes", e); //$NON-NLS-1$
			}
		}

		return createdMarker;
	}

	/**
	 * This allows to create a marker for a resource.
	 *
	 * @param resource
	 *            The resource where create the marker.
	 * @param type
	 *            The type of the marker validation.
	 * @return The created marker or <code>null</code> if there is an error.
	 */
	public static IMarker createMarker(final IResource resource, final String type) {
		IMarker createdMarker = null;
		try {
			createdMarker = resource.createMarker(type);
		} catch (CoreException e) {
			Activator.log.error("Error while creating marker", e); //$NON-NLS-1$
		}

		return createdMarker;
	}

	/**
	 * This allows to delete markers of a resource.
	 *
	 * @param resource
	 *            The resource.
	 * @param type
	 *            The type of markers to delete.
	 */
	public static void deleteMarkers(final IResource resource, final String type) {
		try {
			resource.deleteMarkers(type, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			Activator.log.error("Error while deleting markers", e); //$NON-NLS-1$
		}
	}

}
