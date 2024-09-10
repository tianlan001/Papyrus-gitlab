/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.extensionpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.customization.properties.generation.Activator;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;

/**
 * Handles the extension point org.eclipse.papyrus.customization.properties.generation.generator
 * Registers the given Generator to the Property view generation wizard
 *
 * @author Camille Letavernier
 */
public class GeneratorExtensionPoint {

	private final String EXTENSION_ID = "org.eclipse.papyrus.customization.properties.generation.generator"; //$NON-NLS-1$

	private final List<IGenerator> generators;

	/**
	 * Constructor.
	 */
	public GeneratorExtensionPoint() {

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		List<IGenerator> generators = new ArrayList<IGenerator>(2);
		for (IConfigurationElement e : config) {
			String generatorClassName = e.getAttribute("generator"); //$NON-NLS-1$
			IGenerator generator = ClassLoaderHelper.newInstance(generatorClassName, IGenerator.class);
			if (generator == null) {
				Activator.log.warn("Cannot instantiate the generator : " + generatorClassName); //$NON-NLS-1$
				continue;
			}
			generators.add(generator);
		}

		this.generators = Collections.unmodifiableList(generators);
	}

	public List<IGenerator> getGenerators() {
		return generators;
	}
}
