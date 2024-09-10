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
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayDiagramReconciler_1_2_0;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayMigrationConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypeLabelMigrationCommand;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * Edit Policy for the Stereotype Label user preferences migration.
 * Should be applied to every graphicalEditPart on which Stereotypes can be applied.
 *
 * @author Céline JANSSENS
 *
 * @deprecated Replaced by {@link StereotypeDisplayDiagramReconciler_1_2_0}
 */
@Deprecated
public class StereotypeEAnnotationLabelMigrationEditPolicy extends StereotypeEAnnotationMigrationEditPolicy {

	public static final String LABEL = "Migration of Stereotype Label";//$NON-NLS-1$


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies.StereotypeEAnnotationMigrationEditPolicy#cleanEAnnotationDetails(org.eclipse.gmf.runtime.notation.View)
	 *
	 *
	 *      Clean the Key of EAnnotation related to the StereotypeLabel:
	 *      <li>{@link StereotypeDisplayMigrationConstant.STEREOTYPE_LIST}</li>
	 *      <li>{@link StereotypeDisplayMigrationConstant.STEREOTYPE_WITHQN_LIST}</li>
	 *
	 *
	 * @param view
	 *            The view owning the EAnnotation to clean
	 *
	 */
	@Override
	public void cleanEAnnotationDetails(View view) {


		TransactionalEditingDomain domain = CommandUtil.resolveEditingDomain(view);

		RemoveEAnnotationDetailCommand deleteStereotype = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_LIST);
		CommandUtil.executeUnsafeCommand(deleteStereotype, view);

		RemoveEAnnotationDetailCommand deleteQNList = new RemoveEAnnotationDetailCommand(domain, eAnnotation, StereotypeDisplayMigrationConstant.STEREOTYPE_WITHQN_LIST);
		CommandUtil.executeUnsafeCommand(deleteQNList, view);


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasEAnnotationDetails(View view) {

		return migrationHelper.getStereotypesToDisplay(hostView) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getStereotypeMigrationTransactionalCommand(View view) {
		return new StereotypeLabelMigrationCommand(LABEL, view);
	}
}
