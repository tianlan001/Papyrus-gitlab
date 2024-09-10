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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * This Edit Policy is in charge to migrate user preferences from old stereotype display structure
 * (which were using EAnnotation) to the new structure using NamedStyle and additional notation Views
 */
public abstract class StereotypeEAnnotationMigration {
	protected StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();
	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	protected View hostView = null;
	protected EAnnotation eAnnotation = null;

	/**
	 * Set the attributes and launch the migration.
	 *
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#activate()
	 *
	 */
	public StereotypeEAnnotationMigration(final View view) {
		this.hostView = view;
	}

	public void activate() {
		// set EAnnotation and start the migration process
		if (null != hostView) {
			this.eAnnotation = migrationHelper.getStereotypeEAnnotation(hostView);
			if (null != eAnnotation && !eAnnotation.getDetails().isEmpty()) {
				migrateStereotype();
			}
		}

	}

	/**
	 * Migrate all the Stereotype User preferences
	 *
	 * @throws ExecutionException
	 *
	 */
	protected void migrateStereotype() {
		if (eAnnotation != null) {
			if (hasEAnnotationDetails(hostView)) {
				// Retrieve the migration Command and execute it.
				ICommand command = getStereotypeMigrationCommand(hostView);
				CommandUtil.executeUnsafeCommand(command, hostView);

			}
			// Clean the details associated to each sub edit policies;
			cleanEAnnotationDetails(hostView);
		}
	}

	/**
	 * Clean EAnnotation Details in the model depending of what has been treated.
	 *
	 * @param hostView
	 *            The view on which the Stereotype has been applied
	 */
	public abstract void cleanEAnnotationDetails(final View view);

	/**
	 * Define if the Edit Policy detail for the specific EANnotation Detail is not Empty.
	 *
	 * @param view
	 *            The view on which the Stereotype has been applied
	 */
	public abstract boolean hasEAnnotationDetails(final View view);

	/**
	 * Get the command to update the Node visibility related to the EAnnotation.
	 *
	 * @param hostView
	 *            The view on which the Stereotype has been applied
	 */
	public abstract ICommand getStereotypeMigrationCommand(final View view);

}
