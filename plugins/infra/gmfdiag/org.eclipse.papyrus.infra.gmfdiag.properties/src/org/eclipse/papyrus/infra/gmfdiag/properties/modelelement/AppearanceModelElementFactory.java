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
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.gmfdiag.properties.extension.StyleHandlerManager;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElementFactory;

/**
 * A ModelElementFactory for the Appearance property view. Dispatches the
 * creation of ModelElement to the registered StyleHandlerProvider with
 * the highest priority.
 *
 * @author Camille Letavernier
 */
public class AppearanceModelElementFactory implements ModelElementFactory {

	public ModelElement createFromSource(Object sourceElement, DataContextElement context) {
		for (StyleHandlerProvider provider : StyleHandlerManager.instance.getStyleHandlerProviders()) {
			if (provider.isProviderFor(sourceElement)) {
				ModelElement element = provider.createModelElement(sourceElement, context);
				if (element == null) {
					Activator.log.warn("The StyleHandlerProvider " + provider.getClass().getName() + " provided an invalid ModelElement");
				}
				return element;
			}
		}

		Activator.log.warn("No StyleHandlerProvider found for the following object: " + sourceElement);
		return null;
	}

}
