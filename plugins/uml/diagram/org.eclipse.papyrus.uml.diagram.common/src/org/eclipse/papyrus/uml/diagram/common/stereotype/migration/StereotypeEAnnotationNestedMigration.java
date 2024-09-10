/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeNestedPropertiesMigrationCommand;

/**
 * Class to manage the migration of the object into Compartment :
 * <ul>
 * <li>Operations</li>
 * <li>Properties</li>
 * <li>Nested Element</li>
 * <li>Enumeration</li>
 * <li>....</li>
 * </ul>
 */
public class StereotypeEAnnotationNestedMigration extends StereotypeEAnnotationPropertiesMigration {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public StereotypeEAnnotationNestedMigration(final View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		if (null != hostView) {
			this.eAnnotation = migrationHelper.getStereotypeEAnnotation(hostView);
			migrateStereotype();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getStereotypeMigrationCommand(final View view) {
		return new StereotypeNestedPropertiesMigrationCommand(LABEL, view);
	}

}
