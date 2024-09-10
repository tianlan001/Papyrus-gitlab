/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - Support updating of multiple selected files
 *****************************************************************************/
package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import org.eclipse.core.resources.IFile;
import org.w3c.dom.Node;


public class PomUpdater extends XMLDependencyUpdater {

	public PomUpdater() {
		super();
	}

	@Override
	public boolean canUpdate(IFile file) {
		return "xml".equals(file.getFileExtension()) && file.getName().contains("pom"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected String getXpath() {
		return "/project/repositories/repository/url"; //$NON-NLS-1$
	}

	@Override
	protected String getCurrentLocation(Node uri) {
		return uri.getTextContent();
	}

	@Override
	protected void updateUri(Node uri, String location) {
		if (location.startsWith("http://download.eclipse.org")) {
			location = location.replace("http://download.eclipse.org", "${eclipse.download}");
		}
		uri.setTextContent(location);
	}

}
