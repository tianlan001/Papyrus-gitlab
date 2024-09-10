/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
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
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.properties.environment.Environment;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.SemanticEMFContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;

/**
 * A Content provider for returning objects for the registered environments
 *
 * @author Camille Letavernier
 */
public class EnvironmentContentProvider extends SemanticEMFContentProvider implements IStaticContentProvider {

	/**
	 * Constructor.
	 *
	 * @param feature
	 *            The EStructuralFeature used to retrieve the elements from the
	 *            different environments.
	 */
	public EnvironmentContentProvider(EStructuralFeature feature) {
		super(null, feature, getRoots(feature), Activator.getDefault().getCustomizationManager());
	}

	private static EObject[] getRoots(EStructuralFeature feature) {
		if (!(feature.getEType() instanceof EClass)) {
			Activator.log.warn("The feature " + feature + " cannot be handled by this content provider");
			return new EObject[0];
		}

		List<Object> allObjects = new LinkedList<>();
		for (Environment environment : ConfigurationManager.getInstance().getPropertiesRoot().getEnvironments()) {
			allObjects.addAll((List<?>) environment.eGet(feature));
		}
		return allObjects.toArray(new EObject[0]);
	}

}
