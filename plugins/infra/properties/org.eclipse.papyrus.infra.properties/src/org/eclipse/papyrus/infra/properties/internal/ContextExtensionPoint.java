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
package org.eclipse.papyrus.infra.properties.internal;

import static org.eclipse.papyrus.infra.properties.internal.InfraPropertiesPlugin.LOG;
import static org.eclipse.papyrus.infra.properties.internal.InfraPropertiesPlugin.PLUGIN_ID;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;

/**
 * Handles the extension point <tt>org.eclipse.papyrus.infra.properties.contexts</tt>.
 * Registers the given Context models to the Property View framework
 *
 * @author Camille Letavernier
 */
public class ContextExtensionPoint {


	/** The Constant IS_CUSTOMIZABLE. */
	private static final String IS_CUSTOMIZABLE = "isCustomizable";//$NON-NLS-1$

	/** The Constant IS_VISIBLE. */
	private static final String APPLIED_BY_DEFAULT = "appliedByDefault";//$NON-NLS-1$

	private static final String CONTEXT_MODEL = "contextModel"; //$NON-NLS-1$

	private static final String CONTEXT = "context"; //$NON-NLS-1$

	/** The extension id. */
	private final String EXTENSION_ID = PLUGIN_ID + ".contexts"; //$NON-NLS-1$

	/**
	 * Constructor
	 */
	public ContextExtensionPoint(ModelConsumer modelAcceptor) {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (IConfigurationElement e : config) {
			try {
				switch (e.getName()) {
				case CONTEXT:
					processContext(e, modelAcceptor);
					break;
				}

			} catch (IOException ex) {
				LOG.error("The plugin " + e.getContributor() + " contributed an invalid extension for " + EXTENSION_ID, ex); //$NON-NLS-1$//$NON-NLS-2$
			} catch (Exception ex) {
				LOG.error(ex);
			}
		}
	}

	private void processContext(IConfigurationElement e, ModelConsumer modelAcceptor) throws IOException {
		final String contextResource = e.getAttribute(CONTEXT_MODEL);

		final boolean isCustomizable;
		if (Arrays.asList(e.getAttributeNames()).contains(IS_CUSTOMIZABLE)) {
			isCustomizable = Boolean.parseBoolean(e.getAttribute(IS_CUSTOMIZABLE));
		} else {
			isCustomizable = true; // Default value
		}

		final boolean appliedByDefault;
		if (Arrays.asList(e.getAttributeNames()).contains(APPLIED_BY_DEFAULT)) {
			appliedByDefault = Boolean.parseBoolean(e.getAttribute(APPLIED_BY_DEFAULT));
		} else {
			appliedByDefault = true; // Default value
		}

		URI uri = URI.createURI("ppe:/context/" + e.getContributor().getName() + "/" + contextResource); //$NON-NLS-1$ //$NON-NLS-2$

		modelAcceptor.accept(uri, appliedByDefault, isCustomizable);
	}

	//
	// Nested types
	//

	/**
	 * An analogue of the Java {@link Consumer} protocol that accepts models and
	 * potentially throws {@link IOException} on failure to load a model.
	 */
	@FunctionalInterface
	public interface ModelConsumer {
		/**
		 * Accepts a model URI.
		 * 
		 * @param modelURI
		 *            the model URI (presumably to be loaded)
		 * 
		 * @throws IOException
		 *             on failure to load the model from the given URI
		 */
		void accept(URI modelURI, boolean appliedByDefault, boolean isCustomizable) throws IOException;
	}
}
