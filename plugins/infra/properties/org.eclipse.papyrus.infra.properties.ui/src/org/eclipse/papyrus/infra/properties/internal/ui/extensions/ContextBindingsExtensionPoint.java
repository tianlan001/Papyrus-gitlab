/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - adds isVisible implementation
 *  Christian W. Damus - bugs 469188, 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.internal.ui.extensions;

import java.util.function.BiConsumer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;

/**
 * Handles the extension point <tt>org.eclipse.papyrus.infra.properties.ui.context</tt>
 * Registers the given Context preference page bindings.
 *
 * @author Camille Letavernier
 */
public class ContextBindingsExtensionPoint {


	private static final String PREFPAGE_BINDING = "preferencePageBinding"; //$NON-NLS-1$

	private static final String CONTEXT = "context"; //$NON-NLS-1$

	private static final String PAGE = "page"; //$NON-NLS-1$

	/** The extension id. */
	private final String EXTENSION_ID = "org.eclipse.papyrus.infra.properties.ui.context"; //$NON-NLS-1$

	/**
	 * Constructor
	 */
	public ContextBindingsExtensionPoint(BiConsumer<String, String> bindingProcessor) {
		super();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (IConfigurationElement e : config) {
			try {
				switch (e.getName()) {
				case PREFPAGE_BINDING:
					processPrefPageBinding(e, bindingProcessor);
					break;
				}
			} catch (Exception ex) {
				Activator.log.error(ex);
			}
		}
	}

	private void processPrefPageBinding(IConfigurationElement config, BiConsumer<String, String> bindingProcessor) {
		boolean valid = true;

		String context = config.getAttribute(CONTEXT);
		if ((context == null) || context.isEmpty()) {
			valid = false;
			Activator.log.warn(String.format("Missing context name in preference page binding extension in plug-in %s", config.getContributor().getName()));
		}

		String page = config.getAttribute(PAGE);
		if ((page == null) || page.isEmpty()) {
			valid = false;
			Activator.log.warn(String.format("Missing page ID in preference page binding extension in plug-in %s", config.getContributor().getName()));
		}

		if (valid) {
			bindingProcessor.accept(context, page);
		}
	}
}
