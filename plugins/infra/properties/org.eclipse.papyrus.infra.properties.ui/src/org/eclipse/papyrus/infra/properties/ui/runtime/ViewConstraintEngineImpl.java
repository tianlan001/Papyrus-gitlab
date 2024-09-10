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
 *  Christian W. Damus (CEA) - bug 417409
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.runtime;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.runtime.DefaultConstraintEngine;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.View;

/**
 * The implementation for ViewConstraintEngine
 *
 * @author Camille Letavernier
 */
public class ViewConstraintEngineImpl extends DefaultConstraintEngine<View> implements ViewConstraintEngine {

	private final IConfigurationManager configManager;

	public ViewConstraintEngineImpl(IConfigurationManager configManager) {
		super(View.class);

		this.configManager = configManager;
	}

	@Override
	public synchronized void refresh() {
		constraints.clear();
		Collection<Context> contexts = configManager.getEnabledContexts();
		for (Context context : contexts) {
			addContext(context);
		}

		fireConstraintsChanged();
	}

	@Override
	public void addContext(final Context context) {
		for (View view : context.getViews()) {
			for (ConstraintDescriptor descriptor : view.getConstraints()) {
				addConstraint(descriptor);
			}
		}
	}

	@Override
	public Set<View> getViews(final ISelection forSelection) {
		return getDisplayUnits(forSelection);
	}
}
