/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.validation.internal.providers;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.papyrus.infra.services.markerlistener.IPapyrusMarker;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * This is the SeverityLabelProvider type. Enjoy.
 */
public class SeverityLabelProvider
		extends ProblemLabelProvider {

	private ISharedImages images;

	public SeverityLabelProvider() {
		super();

		this.images = PlatformUI.getWorkbench().getSharedImages();
	}

	@Override
	public String getText(Object element) {
		return null;
	}

	@Override
	public Image getImage(Object element) {
		Image result = null;

		if (element instanceof IPapyrusMarker) {
			IPapyrusMarker marker = (IPapyrusMarker) element;
			switch (marker.getAttribute(IPapyrusMarker.SEVERITY,
					IPapyrusMarker.SEVERITY_ERROR)) {
			case IPapyrusMarker.SEVERITY_INFO:
				result = images.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
				break;
			case IPapyrusMarker.SEVERITY_WARNING:
				result = images.getImage(ISharedImages.IMG_OBJS_WARN_TSK);
				break;
			default:
				result = images.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
				break;
			}
		}

		return result;
	}

	@Override
	public ViewerComparator createSorter() {
		return new ViewerComparator() {

			@Override
			public int category(Object element) {
				int result = -1;

				if (element instanceof IPapyrusMarker) {
					IPapyrusMarker marker = (IPapyrusMarker) element;
					result = IPapyrusMarker.SEVERITY_ERROR
							- marker.getAttribute(IPapyrusMarker.SEVERITY, -1);
				}

				return result;
			}
		};
	}
}
