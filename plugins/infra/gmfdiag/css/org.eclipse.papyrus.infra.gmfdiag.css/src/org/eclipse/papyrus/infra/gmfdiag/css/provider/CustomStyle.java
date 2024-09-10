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
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.provider;

/**
 * An element which supports Papyrus custom styles (elementIcon,
 * qualifiedNameDepth, shadow).
 *
 * @author Camille Letavernier
 */
public interface CustomStyle {

	public boolean showElementIcon();

	public int getQualifiedNameDepth();

	public boolean showShadow();

}
