/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.internal;

import static org.eclipse.papyrus.infra.properties.internal.InfraPropertiesPlugin.LOG;
import static org.eclipse.papyrus.infra.properties.internal.InfraPropertiesPlugin.PLUGIN_ID;

import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintsManager;

/**
 * Handles the extension point <tt>org.eclipse.papyrus.infra.properties.environments</tt>.
 * Registers the given Environment models to the Property View framework
 *
 * @author Camille Letavernier
 */
public class EnvironmentExtensionPoint {

	private final String EXTENSION_ID = PLUGIN_ID + ".environments"; //$NON-NLS-1$

	public EnvironmentExtensionPoint(ModelConsumer environmentAcceptor) {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (IConfigurationElement e : config) {
			final String environmentResource = e.getAttribute("environmentModel"); //$NON-NLS-1$
			URI uri = URI.createURI("ppe:/environment/" + e.getContributor().getName() + "/" + environmentResource); //$NON-NLS-1$ //$NON-NLS-2$

			try {
				environmentAcceptor.accept(uri);
				ConstraintsManager.instance.addEnvironment(uri); // Add it to the constraints extension point, which we "extend"
			} catch (IOException ex) {
				LOG.error("The plugin " + e.getContributor() + " contributed an invalid " + "extension for " + EXTENSION_ID, ex); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			}
		}
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
		void accept(URI modelURI) throws IOException;
	}

}
