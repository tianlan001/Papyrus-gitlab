/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
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
package org.eclipse.papyrus.infra.gmfdiag.properties.constraint;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.DisplayUnit;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.gmfdiag.properties.extension.StyleHandlerManager;
import org.eclipse.papyrus.infra.gmfdiag.properties.modelelement.StyleHandlerProvider;

/**
 * A constraint matching an Object on which the Appearance properties can
 * be applied. This constraint dispatches
 *
 * @author Camille Letavernier
 */
public class AppearanceConstraint implements Constraint {

	private final List<Constraint> constraints;

	private DisplayUnit display;

	private ConstraintDescriptor descriptor;

	public AppearanceConstraint() {
		constraints = new LinkedList<Constraint>();
		for (StyleHandlerProvider provider : StyleHandlerManager.instance.getStyleHandlerProviders()) {
			try {
				Constraint constraint = provider.createConstraint();
				if (constraint == null) {
					Activator.log.warn("StyleHandlerProvider " + provider.getClass().getName() + " provided an invalid Constraint");
				} else {
					constraints.add(constraint);
				}
			} catch (Exception ex) {
				Activator.log.error(ex);
			}
		}
	}

	public boolean overrides(Constraint constraint) {
		// Unsupported. The appearance property view constraints are not automatically overrideable
		return false;
	}

	public boolean match(Collection<?> selection) {
		for (Constraint constraint : constraints) {
			if (constraint.match(selection)) {
				return true;
			}
		}
		return false;
	}

	public void setConstraintDescriptor(ConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
		display = getDisplay(descriptor);

		for (Constraint constraint : constraints) {
			try {
				constraint.setConstraintDescriptor(descriptor);
			} catch (Exception ex) {
				Activator.log.error(ex);
			}
		}
	}

	public DisplayUnit getDisplayUnit() {
		return display;
	}

	private DisplayUnit getDisplay(ConstraintDescriptor descriptor) {
		if (descriptor.getDisplay() == null) {
			if (descriptor.eContainer() instanceof ConstraintDescriptor) {
				return getDisplay((ConstraintDescriptor) descriptor.eContainer());
			}
		}
		return descriptor.getDisplay();
	}

	public ConstraintDescriptor getDescriptor() {
		return descriptor;
	}

}
