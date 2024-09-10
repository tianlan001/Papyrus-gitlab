/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.validation;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.osgi.util.NLS;

/**
 * utils method for dependency validation in Ecore model
 *
 */
public class DependencyValidationUtils {

	/*
	 * For your information:
	 * Eclipse launch config use these IMarker types:
	 * - IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER
	 * - PDEMarkerFactory.MARKER_ID)
	 *
	 * Ecore use this Marker ID : "org.eclipse.core.resources.problemmarker"; see org.eclipse.emf.common.ui.MarkerHelper.getMarkerID()
	 *
	 */

	public static final String MISSING_DEPENDENCIES = "Missing Dependencies"; //$NON-NLS-1$

	public static final String DEPENDENCY_SEPARATOR = ","; //$NON-NLS-1$

	private DependencyValidationUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param eobjectInError
	 * @param feature
	 * @param missingDependencies
	 * @return
	 */
	public static final Diagnostic createMissingDependenciesDiagnostic(final EObject eobjectInError, final EStructuralFeature feature, final Collection<String> missingDependencies) {
		final StringBuilder missingDependenciesBuilder = new StringBuilder();
		final Iterator<String> iter = missingDependencies.iterator();
		while (iter.hasNext()) {
			missingDependenciesBuilder.append(iter.next());
			if (iter.hasNext()) {
				missingDependenciesBuilder.append(DEPENDENCY_SEPARATOR);
			}
		}

		final String data[] = { MISSING_DEPENDENCIES, eobjectInError.eClass().getName(), feature.getName(), missingDependencies.toString() };

		final String message = NLS.bind("{0} : The field {1}#{2} required to add the dependencies {3} in the Manifest file", data); //$NON-NLS-1$

		BasicDiagnostic bDiag = new BasicDiagnostic(Diagnostic.ERROR, EObjectValidator.DIAGNOSTIC_SOURCE, 1, message,
				new Object[] { eobjectInError });
		return bDiag;
	}
}
