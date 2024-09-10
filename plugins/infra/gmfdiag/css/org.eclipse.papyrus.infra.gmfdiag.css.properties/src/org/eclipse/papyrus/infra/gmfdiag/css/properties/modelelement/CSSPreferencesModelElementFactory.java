/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 417409
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.modelelement;

import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.PreferencesModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.PreferencesModelElementFactory;


public class CSSPreferencesModelElementFactory extends PreferencesModelElementFactory {

	@Override
	protected PreferencesModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		return new CSSPreferencesModelElement(context);
	}
}
