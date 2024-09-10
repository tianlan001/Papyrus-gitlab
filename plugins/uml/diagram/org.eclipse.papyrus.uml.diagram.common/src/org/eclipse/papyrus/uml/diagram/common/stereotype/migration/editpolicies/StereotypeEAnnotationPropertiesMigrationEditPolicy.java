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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.commands.RemoveEAnnotationDetailCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayDiagramReconciler_1_2_0;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayMigrationConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeCommentPropertiesMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypePropertiesMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * Edit Policy for the Stereotype Properties user preferences migration.
 * Should be applied to every graphicalEditPart on which Stereotypes Properties can be displayed.
 *
 * @author Céline JANSSENS
 *
 * @deprecated Replaced by {@link StereotypeDisplayDiagramReconciler_1_2_0}
 */
@Deprecated
public class StereotypeEAnnotationPropertiesMigrationEditPolicy extends StereotypeEAnnotationMigrationEditPolicy {

	public static final String LABEL = "Migration Stereotype Properties";//$NON-NLS-1$



	/**
	 * Clean the Key of EAnnotation related to the StereotypeLabel:
	 * <ul>
	 * <li>{@link StereotypeDisplayMigrationConstant.STEREOTYPE_PROPERTY_LOCATION}</li>
	 * <li>{@link StereotypeDisplayMigrationConstant.PROPERTY_STEREOTYPE_DISPLAY}</li>
	 * <li>{@link StereotypeDisplayMigrationConstant.STEREOTYPE_PRESENTATION_KIND}</li>
	 * </ul>
	 *
	 * @param view
	 *            The view owning the EAnnotation to clean
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies.StereotypeEAnnotationMigrationEditPolicy#cleanEAnnotationDetails(org.eclipse.gmf.runtime.notation.View)
	 *
	 */
	@Override
	public void cleanEAnnotationDetails(final View view) {

		TransactionalEditingDomain domain = CommandUtil.resolveEditingDomain(editPart);

		if (domain != null) {
			RemoveEAnnotationDetailCommand deleteLocation = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_PROPERTY_LOCATION);
			CommandUtil.executeUnsafeCommand(deleteLocation, domain);
			RemoveEAnnotationDetailCommand deleteProperties = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.PROPERTY_STEREOTYPE_DISPLAY);
			CommandUtil.executeUnsafeCommand(deleteProperties, domain);
			RemoveEAnnotationDetailCommand deleteKind = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_PRESENTATION_KIND);
			CommandUtil.executeUnsafeCommand(deleteKind, domain);
		}

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasEAnnotationDetails(final View view) {

		return migrationHelper.getAppliedStereotypesPropertiesToDisplay(hostView) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getStereotypeMigrationTransactionalCommand(final View view) {
		ICommand migrationCommand = null;
		if (migrationHelper.getAppliedStereotypesPropertiesLocalization(view).equals(StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION)) {
			// In case of properties in Comment, a dedicated Command is required.
			migrationCommand = new StereotypeCommentPropertiesMigrationCommand(LABEL, view, true);
		} else {
			migrationCommand = new StereotypePropertiesMigrationCommand(LABEL, view);
		}

		return migrationCommand;
	}
}
