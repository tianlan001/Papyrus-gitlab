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
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeLabelMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * Class for the Stereotype Label user preferences migration.
 *
 */
public class StereotypeEAnnotationLabelMigration extends StereotypeEAnnotationMigration {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public StereotypeEAnnotationLabelMigration(final View view) {
		super(view);
	}

	public static final String LABEL = "Migration of Stereotype Label";//$NON-NLS-1$

	@Override
	public void cleanEAnnotationDetails(final View view) {
		TransactionalEditingDomain domain = CommandUtil.resolveEditingDomain(view);

		RemoveEAnnotationDetailCommand deleteStereotype = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_LIST);
		CommandUtil.executeUnsafeCommand(deleteStereotype, view);

		RemoveEAnnotationDetailCommand deleteQNList = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_WITHQN_LIST);
		CommandUtil.executeUnsafeCommand(deleteQNList, view);
	}

	@Override
	public boolean hasEAnnotationDetails(final View view) {
		return migrationHelper.getStereotypesToDisplay(hostView) != null;
	}

	@Override
	public ICommand getStereotypeMigrationCommand(final View view) {
		return new StereotypeLabelMigrationCommand(LABEL, view);
	}

}
