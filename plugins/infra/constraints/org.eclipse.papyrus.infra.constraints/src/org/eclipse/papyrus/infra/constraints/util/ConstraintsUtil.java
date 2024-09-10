/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.constraints.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.infra.constraints.ConfigProperty;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.constraints.environment.util.EnvironmentSwitch;
import org.eclipse.uml2.common.util.UML2Util;

/**
 * Utilities for working with constraint models.
 */
public class ConstraintsUtil extends UML2Util {

	private static final Switch<String> NAME_SWITCH = createNameSwitch();

	/**
	 * Get the model name (unlocalized) of the given {@code object}, if it has one.
	 *
	 * @param object
	 *            an object in the constraints model
	 * @return its name, or {@code null} if none
	 */
	public static final String getName(EObject object) {
		return NAME_SWITCH.doSwitch(object);
	}

	/**
	 * Get a localized label for the given constraints model element, suitable for presentation in the
	 * UI or other user-facing messages.
	 *
	 * @param object
	 *            an object in the constraints model
	 * @return its localized label
	 */
	public static final String getLabel(EObject object) {
		return getLabel(object, true);
	}

	public static final String getLabel(EObject object, boolean localize) {
		String name = getName(object);
		return getString(object,
				"_UI_label_" + getValidJavaIdentifier(isEmpty(name)
						? EMPTY_STRING
						: name.replace(':', '_')),
				name, localize);
	}

	protected static String getString(EObject context, String key, String defaultValue, boolean localize) {
		if (context instanceof ConfigProperty) {
			// The context to look up the label is the constraint type that defines the property
			EObject container = context.eContainer();
			if (container instanceof SimpleConstraint) {
				ConstraintType type = ((SimpleConstraint) container).getConstraintType();
				if (type != null) {
					context = type;
				}
			}
		}

		return UML2Util.getString(context, key, defaultValue, localize);
	}

	private static final Switch<String> createNameSwitch() {
		Switch<String> constraints = new ConstraintsSwitch<>() {
			public String caseConstraintDescriptor(ConstraintDescriptor object) {
				return object.getName();
			}

			@Override
			public String caseConfigProperty(ConfigProperty object) {
				return object.getName();
			}
		};

		Switch<String> environment = new EnvironmentSwitch<>() {
			@Override
			public String caseConstraintType(ConstraintType object) {
				return object.getLabel();
			}
		};

		return new ComposedSwitch<>(List.of(constraints, environment));
	}

}
