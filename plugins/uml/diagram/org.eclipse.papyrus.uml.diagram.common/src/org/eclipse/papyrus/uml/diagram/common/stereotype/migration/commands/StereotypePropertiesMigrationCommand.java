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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
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
import org.eclipse.papyrus.uml.diagram.common.stereotype.StereotypeLocationEnum;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.StereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayMigrationConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * In charge of applying user preferences from the old EAnnotation structure to the new one
 * For Stereotypes Properties
 * This means set visibility to the newly created views .
 *
 */
public class StereotypePropertiesMigrationCommand extends AbstractTransactionalCommand {

	protected View mainView;

	private IStereotypeViewProvider provider;

	// Helpers
	protected static final StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();
	protected static final StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/**
	 * Constructor.
	 *
	 * @param label
	 *            Label of the Command
	 * @param content
	 *            Main view on which the Stereotype is applied
	 */
	public StereotypePropertiesMigrationCommand(final String label, final View content) {
		super(migrationHelper.getDomain(content), label, null);
		this.mainView = content;
	}

	/**
	 * Migrate the Stereotype Properties from the old Version.
	 *
	 * @param view
	 *            View of the Editpart on which the STereotype is applied
	 */
	protected void migrateStereotypeProperties(final View view) {

		createProvider(view);
		if (migrationHelper.hasStereotypeEAnnotation(view)) {
			String oldProperties = getOldPropertiesToDisplay(view);
			String oldPropertiesLocation = getOldLocationToDisplay(view);
			Enum<?> location = getLocation(oldPropertiesLocation);
			updateStereotypePropertyDisplay(view, oldProperties, location);

		}
	}


	/**
	 * Convert the EAnnotation Location to the Property Location to display.
	 * Can be extended.
	 */
	public Enum<?> getLocation(final String oldProperties) {
		Enum<?> location = StereotypeLocationEnum.IN_BRACE;
		if (oldProperties.equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION)) {
			location = StereotypeLocationEnum.IN_COMPARTMENT;
		} else if (oldProperties.equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_LOCATION)) {
			location = StereotypeLocationEnum.IN_BRACE;
		} else if (oldProperties.equals(StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION)) {
			location = StereotypeLocationEnum.IN_COMMENT;
		}
		return location;
	}

	/**
	 * Create the Provider with the associated View
	 *
	 * @param view
	 *            The View of the object that will be taken as Reference to provide the different Stereotype to display View.
	 */
	protected void createProvider(final View view) {
		provider = new StereotypeViewProvider(view);

	}


	/**
	 * From the Stereotype Property List to display , update the Visibility
	 * And hide visible Stereotype property that should not be shown.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param propertyList
	 *            List of property to display
	 * @param location
	 *            The Location of the Properties to be displayed or hidden.
	 */
	protected void updateStereotypePropertyDisplay(final View view, final String propertyList, final Enum<?> location) {
		// Show all the properties that should be displayed
		showStereotypePropertyToBeDisplayed(propertyList, view, location);
	}

	/**
	 * Show all the Properties from the Properties List and their location
	 *
	 * @param propertyList
	 *            List of properties (ie: "SysML::Blocks::Block.isEncapsulated,SysML::Requirement::Requirement.id")
	 * @param view
	 *            The View of the Object migrated (ie: CSSShapeImpl)
	 * @param location
	 *            Place of the the property to be shown: {@link StereotypeLocationEnum}
	 */
	private void showStereotypePropertyToBeDisplayed(final String propertyList, final View view, final Enum<?> location) {
		StringTokenizer tokenizer = new StringTokenizer(propertyList, StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR);
		while (tokenizer.hasMoreTokens()) {

			String propertyString = tokenizer.nextToken();
			Stereotype stereoytpe = getStereotypeFromString(view, propertyString);
			if (stereoytpe != null) {
				Property property = getPropertyFromString(view, propertyString, stereoytpe);
				showStereotypeProperty(view, stereoytpe, property, location);
			}
		}

	}

	/**
	 * Show the property and related Location Compartment to display
	 *
	 * @param view
	 *            The Main View on which the stereotype is applied.
	 * @param location
	 *            The location of the Property to show (Compartment, Comment, With Brace)
	 * @param stereotype
	 *            The Stereotype of the Property to show
	 * @param property
	 *            UML Property to show
	 */
	private void showStereotypeProperty(final View view, final Stereotype stereotype, final Property property, final Enum<?> location) {
		// Make The main View visible (Required for the Comment )
		migrationHelper.updateVisibilityAndPersistence(view, view, true);

		// Make The compartment owning the property visible
		View compartment = provider.getCompartment(stereotype, location);
		migrationHelper.updateVisibilityAndPersistence(compartment, view, true);

		// Make the properties node into the Compartment visible
		View propertyView = provider.getProperty(property, stereotype, location);
		migrationHelper.updateVisibilityAndPersistence(propertyView, view, true);

	}



	/**
	 * Hide visible Properties that should not be shown.
	 * List all the applied Stereotypes and their Properties, if the property is not in the property to Display list,
	 * hide the Node.
	 *
	 * @param view
	 *            The view of the object to migrate
	 * @param stereotypeList
	 *            List of Stereotype to display
	 */
	private void hideStereotypePropertyNotDisplayed(final String propertyList, final View view, final Enum<?> location) {
		// If applied Stereotype is not in the Stereotype list to display, set the visibility to false to hide the Label.
		EList<Stereotype> appliedStereotypesFromView = migrationHelper.getAppliedStereotypesFromView(view);
		if (null != appliedStereotypesFromView) {
			Iterator<Stereotype> stereotypes = appliedStereotypesFromView.iterator();
			while (stereotypes.hasNext()) {
				Stereotype stereotype = stereotypes.next();
				hideStereotypeProperties(stereotype, propertyList, location);
			}
		}
	}


	/**
	 * Hide the Properties from the properties list, location and Stereotypes
	 *
	 * @param stereotype
	 *            The Stereotype of which the Properties should be hidden
	 * @param propertyList
	 *            The list of all the properties to be displayed
	 * @param location
	 *            The location of the Properties to be hidden
	 */
	private void hideStereotypeProperties(final Stereotype stereotype, final String propertyList, final Enum<?> location) {
		for (Property property : stereotype.allAttributes()) {
			if (propertyList.indexOf(property.getName()) == -1) {

				// Create the Property view if not existing and Make the properties node into the Compartment visible
				hideStereotypeProperty(property, stereotype, location);
			}
		}

	}

	/**
	 * Hide a single property View based on the stereotype and the location.
	 *
	 * @param property
	 *            The property to hide
	 * @param stereotype
	 *            The stereotype of the property to hide
	 * @param location
	 *            the location of the property to hide
	 */
	private void hideStereotypeProperty(final Property property, final Stereotype stereotype, final Enum<?> location) {
		View compartment = provider.getCompartment(stereotype, location);
		if (compartment != null) {
			View propertyView = provider.getProperty(property, stereotype, location);
			if (propertyView != null) {
				migrationHelper.updateVisibilityAndPersistence(propertyView, mainView, false);
			}
		}

	}

	/**
	 * Get the property from String
	 *
	 * @param view
	 *            The Main View on which the stereotype is applied.
	 * @param propertyString
	 *            the substring from the Old Version (I.e: "SysML::Blocks::Block.isEncapsulate")
	 * @return The corresponding UML property
	 */
	private Property getPropertyFromString(final View view, final String propertyString, final Stereotype stereotype) {
		String propertyName = propertyString.substring(propertyString.indexOf(StereotypeDisplayMigrationConstant.EANNOTATION_PROPERTY_SEPARATOR) + 1, propertyString.length());
		return migrationHelper.getPropertyFromString(view, stereotype, propertyName);
	}


	/**
	 * Get the stereotype from String
	 *
	 * @param view
	 *            The Main View on which the stereotype is applied.
	 * @param propertyString
	 *            the substring from the Old Version (I.e: "SysML::Blocks::Block")
	 * @return The corresponding UML Stereotype
	 */
	private Stereotype getStereotypeFromString(final View view, final String propertyString) {
		String qualifiedName = propertyString.substring(0, propertyString.indexOf(StereotypeDisplayMigrationConstant.EANNOTATION_PROPERTY_SEPARATOR));
		return migrationHelper.getStereotypeFromString(view, qualifiedName);

	}


	/**
	 * Get the EAnnotation Detail value for the properties Location.
	 *
	 * @param view
	 *            The view on which the stereotype is applied
	 * @return Location value
	 */
	protected String getOldLocationToDisplay(final View view) {
		return migrationHelper.getAppliedStereotypesPropertiesLocalization(view);
	}


	/**
	 * Get the EAnnotation Detail value for the Property list to displayed
	 *
	 * @param view
	 *            The view on which the stereotype is applied
	 * @return The list of the properties to be displayed
	 */
	protected String getOldPropertiesToDisplay(final View view) {
		return migrationHelper.getAppliedStereotypesPropertiesToDisplay(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) throws ExecutionException {
		migrateStereotypeProperties(mainView);
		return CommandResult.newOKCommandResult();
	}
}
