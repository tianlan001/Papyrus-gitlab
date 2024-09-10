/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A wrapper to convert a IRevealSemanticElement to an INavigationTarget
 *
 * @author Camille Letavernier
 *
 */
public class RevealSemanticElementWrapper implements NavigationTarget {

	private final IRevealSemanticElement revealSemanticElement;

	public RevealSemanticElementWrapper(IRevealSemanticElement revealSemanticElement) {
		this.revealSemanticElement = revealSemanticElement;
	}

	@Override
	public boolean revealElement(Object element) {
		return revealElement(Collections.singletonList(element));
	}

	@Override
	public boolean revealElement(Collection<?> elements) {
		revealSemanticElement.revealSemanticElement(new LinkedList<Object>(elements));
		return false;
	}

}
