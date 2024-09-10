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

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.checkers;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ElementTypeConfigurationsIndex;

/**
 * Custom validation rules for <em>Element Creation Menu</em> models.
 */
public class NewChildCustomValidator extends CustomModelChecker.SwitchValidator {

	public NewChildCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(CreationMenu creationMenu, DiagnosticChain diagnostics, Map<Object, Object> context) {
		validateCreationMenuRole(creationMenu, diagnostics, context);
	}

	private void validateCreationMenuRole(CreationMenu creationMenu, DiagnosticChain diagnostics, Map<Object, Object> context) {
		String role = creationMenu.getRole();
		ElementTypeConfiguration type = creationMenu.getElementType();

		if (role != null && !role.isBlank() && type != null && !type.eIsProxy()) {
			if (!ElementTypeConfigurationsIndex.getInstance().isCreatableInRole(type, role)) {
				String label = creationMenu.getLabel();
				if (label == null || label.isBlank()) {
					label = type.getName();
				}

				diagnostics.add(createDiagnostic(Diagnostic.WARNING, creationMenu, ElementCreationMenuModelPackage.Literals.CREATION_MENU__ROLE,
						format("No element type seems to have a role ''{1}'' that can create a ''{0}''.", context, label, role)));
			}
		}
	}

}
