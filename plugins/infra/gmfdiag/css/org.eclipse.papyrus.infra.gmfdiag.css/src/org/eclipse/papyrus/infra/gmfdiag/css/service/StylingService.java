/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.service;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.spi.IStylingProvider;
import org.eclipse.papyrus.infra.tools.util.CompositeServiceTracker;
import org.osgi.framework.BundleContext;

/**
 * The notation view styling service. It delegates to registered
 * OSGi service implementations of the {@link IStylingProvider} protocol.
 * 
 */
public class StylingService implements IStylingProvider {

	private final CompositeServiceTracker<IStylingProvider> tracker;

	public StylingService(BundleContext context) {
		super();

		tracker = new CompositeServiceTracker<>(context,
				IStylingProvider.class,
				IStylingProvider.NONE,
				IStylingProvider::compose);
		tracker.open();
	}

	/**
	 * Obtains the Styling Service that delegates to registered providers.
	 * 
	 * @return the Styling Service
	 */
	public static StylingService getInstance() {
		return Activator.getDefault().getStylingService();
	}

	public void dispose() {
		tracker.close();
	}

	@Override
	public void resetStyle(View view) {
		tracker.getService().resetStyle(view);
	}

	@Override
	public Iterable<EClass> getSupportedSemanticClasses() {
		return tracker.getService().getSupportedSemanticClasses();
	}

	@Override
	public Predicate<EStructuralFeature> getSemanticPropertySupportedPredicate() {
		return tracker.getService().getSemanticPropertySupportedPredicate();
	}
}
