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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.commands.RemoveEAnnotationDetailCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeCommentPropertiesMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypePropertiesMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * Class for the Stereotype Properties user preferences migration.
 *
 */
public class StereotypeEAnnotationPropertiesMigration extends StereotypeEAnnotationMigration {

	public static final String LABEL = "Migration Stereotype Properties"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public StereotypeEAnnotationPropertiesMigration(final View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cleanEAnnotationDetails(final View view) {
		TransactionalEditingDomain domain = CommandUtil.resolveEditingDomain(view);

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
	public ICommand getStereotypeMigrationCommand(final View view) {
		ICommand migrationCommand = null;
		if (migrationHelper.getAppliedStereotypesPropertiesLocalization(view).equals(StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION)) {
			// In case of properties in Comment, a dedicated Command is required.
			migrationCommand = new StereotypeCommentPropertiesMigrationCommand(LABEL, view, false);
		} else {
			migrationCommand = new StereotypePropertiesMigrationCommand(LABEL, view);
		}

		return migrationCommand;
	}

}
