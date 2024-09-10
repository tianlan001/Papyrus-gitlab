/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Displays
 *   Christian W. Damus - bug 466629
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.StereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayCommandExecution;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayMigrationConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Command applies the user preferences for the Stereotype Label
 * In charge of check the visibility, depth and persistence based on the Old EAnnotation
 *
 * @author Céline JANSSENS
 *
 */
public class StereotypeLabelMigrationCommand extends AbstractTransactionalCommand {

	// String Constant
	private static final String QUALIFIED_NAME_DEPTH = "full"; //$NON-NLS-1$

	private final View mainView;

	// Static Instances of helpers and providers
	private static StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();
	private static StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();
	private static StereotypeDisplayCommandExecution commandHelper = StereotypeDisplayCommandExecution.getInstance();
	private IStereotypeViewProvider provider;


	/**
	 * Constructor.
	 *
	 * @param label
	 *            The Label of the Command
	 * @param content
	 *            The View of the element that is migrated.
	 */
	public StereotypeLabelMigrationCommand(final String label, final View content) {
		super(migrationHelper.getDomain(content), label, null);
		this.mainView = content;
	}

	/**
	 * Migrate the Stereotype Label from the old Version.
	 *
	 * @param View
	 *            The element of the diagram to migrate
	 */
	protected void migrateStereotypeLabel(final View view) {
		// Create the provider
		setProvider(view);

		if (migrationHelper.hasStereotypeEAnnotation(view)) {

			// Update Visibility
			String oldStereotype = getOldStereotypeToDisplay(view);
			updateNewStereotypeVisibility(view, oldStereotype);

			// Update Depth
			String oldQNStereotype = getOldQNStereotypeToDisplay(view);
			updateNewStereotypeDepth(view, oldQNStereotype);

		}
	}

	/**
	 * From the Stereotype List to display with they Qualified Name, update the Depth to full
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param stereotypeList
	 *            List of Stereotype to display with their Qualified Name
	 */
	protected void updateNewStereotypeDepth(final View view, final String stereotypeList) {
		StringTokenizer tokenizer = new StringTokenizer(stereotypeList, StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR);

		while (tokenizer.hasMoreTokens()) {
			String stereotypeName = tokenizer.nextToken();
			View label = helper.getStereotypeLabel(view, stereotypeName);
			if (label != null) {
				commandHelper.setDepth(migrationHelper.getDomain(view), migrationHelper.getStereotypeFromString(view, stereotypeName), view, QUALIFIED_NAME_DEPTH, false);
			}
		}
	}


	/**
	 * From the Stereotype List to display , update the Visibility to true
	 * And hide visible Stereotype Label that should not be shown.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param stereotypeList
	 *            List of Stereotype to display
	 */
	protected void updateNewStereotypeVisibility(final View view, final String stereotypeList) {

		// Hide all the Label displayed but that should not to be shown
		hideStereotypeLabelNotDisplayed(stereotypeList, view);

	}


	/**
	 * Show each Stereotype from the Stereotype to display list.
	 *
	 * @param stereotypeList
	 *            The list of stereotype to display
	 * @param view
	 *            The view of the object that is migrated
	 */
	private void showStereotypeLabelToBeDisplayed(final String stereotypeList, final View view) {
		StringTokenizer tokenizer = new StringTokenizer(stereotypeList, StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR);
		while (tokenizer.hasMoreTokens()) {
			String stereotypeName = tokenizer.nextToken();
			showStereotypeLabel(view, stereotypeName);
		}
	}

	/**
	 * Show the Label To display.
	 * Updating Visibility and Persistence
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param stereotypeList
	 *            List of Stereotype to display
	 */
	protected void showStereotypeLabel(final View view, final String stereotypeName) {
		View label = provider.getLabel(migrationHelper.getStereotypeFromString(view, stereotypeName));
		if (label != null) {
			migrationHelper.updateVisibilityAndPersistence(label, view, true);
		}
	}


	/**
	 * Hide visible Stereotype Label that should not be shown.
	 * List all the applied Stereotypes, if the applied stereotype is not in the Stereotype to Display list,
	 * hide the Node.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param stereotypeList
	 *            List of Stereotype to display
	 */
	private void hideStereotypeLabelNotDisplayed(final String stereotypeList, final View view) {
		EList<Stereotype> appliedStereotypes = migrationHelper.getAppliedStereotypesFromView(view);
		Iterator<Stereotype> stereotypes = appliedStereotypes.iterator();
		while (stereotypes.hasNext()) {
			String stereotypeName = stereotypes.next().getQualifiedName();
			if (stereotypeList.indexOf(stereotypeName) == -1) {
				hideStereotypeLabel(view, stereotypeName);
			}
		}
	}




	/**
	 * Hide the Stereotype Label
	 *
	 * @param view
	 *            The view of the object that is migrated
	 * @param stereotypeName
	 *            Name of the Stereotype of which the Label should be hidden
	 */
	private void hideStereotypeLabel(final View view, final String stereotypeName) {
		View label = provider.getLabel(migrationHelper.getStereotypeFromString(view, stereotypeName));
		migrationHelper.updateVisibilityAndPersistence(label, mainView, false);

	}


	/**
	 * Get List of Stereotype to display with their Qualified Name from the EAnnotation.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @return The List of Stereotype to display with their Qualified Name
	 */
	protected String getOldQNStereotypeToDisplay(final View view) {
		return migrationHelper.getStereotypesQNToDisplay(view);
	}


	/**
	 * Get List of Stereotype to display from the EAnnotation.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @return The List of Stereotype to display
	 */
	protected String getOldStereotypeToDisplay(final View view) {

		return migrationHelper.getStereotypesToDisplay(view);
	}

	/**
	 * Create the provider
	 *
	 * @param view
	 *            The view on which stereotypes is applied and from which the Stereotype Views will be provided
	 */
	private void setProvider(final View view) {
		provider = new StereotypeViewProvider(view);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) throws ExecutionException {
		migrateStereotypeLabel(mainView);
		return CommandResult.newOKCommandResult();
	}
}
