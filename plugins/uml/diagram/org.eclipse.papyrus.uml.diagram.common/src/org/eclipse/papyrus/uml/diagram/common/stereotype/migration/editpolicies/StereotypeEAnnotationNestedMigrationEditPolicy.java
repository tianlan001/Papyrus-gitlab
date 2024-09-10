/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayDiagramReconciler_1_2_0;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeNestedPropertiesMigrationCommand;

/**
 * Edit Policy to manage the migration of the object into Compartment :
 * <ul>
 * <li>Operations</li>
 * <li>Properties</li>
 * <li>Nested Element</li>
 * <li>Enumeration</li>
 * <li>....</li>
 * </ul>
 *
 * To be applied on all the instances of {@link UMLCompartmentEditPart}.
 *
 * @author Céline JANSSENS
 *
 * @deprecated Replaced by {@link StereotypeDisplayDiagramReconciler_1_2_0}
 */
@Deprecated
public class StereotypeEAnnotationNestedMigrationEditPolicy extends StereotypeEAnnotationPropertiesMigrationEditPolicy {


	/**
	 * Constructor.
	 *
	 */
	public StereotypeEAnnotationNestedMigrationEditPolicy() {
		super();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		if (getHost() instanceof GraphicalEditPart) {
			this.editPart = (GraphicalEditPart) getHost();
		}

		if (editPart instanceof UMLCompartmentEditPart && editPart.getModel() instanceof View) {
			this.hostView = (View) editPart.getModel();
		}

		if (editPart != null && hostView != null) {
			this.eAnnotation = migrationHelper.getStereotypeEAnnotation(hostView);
			migrateStereotype();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getStereotypeMigrationTransactionalCommand(final View view) {
		return new StereotypeNestedPropertiesMigrationCommand(LABEL, view);
	}

}
