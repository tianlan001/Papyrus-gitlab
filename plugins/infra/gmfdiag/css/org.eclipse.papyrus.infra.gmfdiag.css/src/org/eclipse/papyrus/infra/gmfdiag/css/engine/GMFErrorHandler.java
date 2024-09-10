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
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;

/**
 * A CSSErrorHandler which forwards exceptions to the Papyrus log
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public class GMFErrorHandler implements CSSErrorHandler {

	/**
	 * {@inheritDoc}
	 *
	 * Forwards the exception to the Papyrus log
	 */
	@Override
	public void error(Exception ex) {
		Activator.log.error("CSS Error", ex); //$NON-NLS-1$
	}

}
