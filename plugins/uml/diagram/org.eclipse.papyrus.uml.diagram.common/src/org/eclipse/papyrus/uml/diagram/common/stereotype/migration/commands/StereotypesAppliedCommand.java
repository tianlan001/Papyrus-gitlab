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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.StereotypeLocationEnum;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.StereotypeViewProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypeCommentViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypeCompartmentCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypePropertyViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateStereotypeLabelCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayMigrationConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Command to create the structure of all the application of stereotypes.
 */
public class StereotypesAppliedCommand extends AbstractTransactionalCommand {
	protected Diagram diagram;
	protected View currentView;

	protected IStereotypeViewProvider provider;

	/*
	 * Helpers
	 */
	protected StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();
	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/*
	 * Constants
	 */


	/**
	 * Constructor.
	 *
	 * @param label
	 * @param diagram
	 */
	public StereotypesAppliedCommand(final TransactionalEditingDomain domain, final Diagram diagram) {
		super(domain, "Stereotypes Applied", null); ////$NON-NLS-1$
		this.diagram = diagram;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) {
		TreeIterator<EObject> allContentIterator = diagram.eAllContents();

		while (allContentIterator.hasNext()) {
			EObject eObject = allContentIterator.next();
			if (eObject instanceof View) {
				final View view = (View) eObject;

				if (StereotypeMigrationHelper.getInstance().hasStereotypeEAnnotation(view)) {
					if (null != view.getElement()) {
						currentView = view;
						provider = new StereotypeViewProvider(view);

						List<Stereotype> allStereotypes = getAllStereotypes();
						if (!allStereotypes.isEmpty()) {
							/*
							 * Label
							 */
							for (Stereotype stereotype : allStereotypes) {
								createAppliedLabelStereotype(view, stereotype);
							}

							/*
							 * StereotypePropertyLocation
							 */
							String appliedStereotypesPropertiesLocalization = migrationHelper.getAppliedStereotypesPropertiesLocalization(view);

							/*
							 * StereotypePropertyLocation : Compartment
							 */
							if (StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION.equals(appliedStereotypesPropertiesLocalization)) {
								for (Stereotype stereotype : allStereotypes) {
									createAppliedCompartmentStereotype(stereotype);
								}

								/*
								 * StereotypePropertyLocation : Brace
								 */
							} else if (StereotypeDisplayConstant.STEREOTYPE_BRACE_LOCATION.equals(appliedStereotypesPropertiesLocalization)) {
								for (Stereotype stereotype : allStereotypes) {
									createAppliedBraceCompartmentStereotype(stereotype);
								}

							}
							/*
							 * Always Create Comment and CommentLink but not displayed
							 */
							createComment(allStereotypes);
						}
					}
				}
			}
		}

		return CommandResult.newOKCommandResult();
	}

	/**
	 * To create the comment.
	 *
	 * @param stereotypes
	 */
	private void createComment(final List<Stereotype> stereotypes) {
		Node comment = helper.getStereotypeComment(currentView);
		String appliedStereotypesLocalization = migrationHelper.getAppliedStereotypesPropertiesLocalization(currentView);

		TransactionalEditingDomain currentDomain = getEditingDomain();
		if (null == comment) {
			// if Comment doesn't exist => Create it and copy the structure from the host
			CreateAppliedStereotypeCommentViewCommand command = new CreateAppliedStereotypeCommentViewCommand(currentDomain, currentView, 200, 100, currentView.getElement(), false);
			CommandUtil.executeUnsafeCommand(command, currentView);
			comment = helper.getStereotypeComment(currentView);

		}
		if (comment != null) {
			for (Stereotype stereotype : stereotypes) {
				BasicCompartment compartmentStructure = helper.getStereotypeCompartment(comment, stereotype);
				if (compartmentStructure == null) { // No Compartment Exist for this Stereotype
					if (!helper.isCompartmentExist(comment, stereotype)) {
						// Create Compartment
						CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(currentDomain, comment, stereotype, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);
						CommandUtil.executeUnsafeCommand(command, currentView);
					}
				}
				compartmentStructure = helper.getStereotypeCompartment(comment, stereotype);
				if (null != compartmentStructure && null != stereotype) {
					EList<Property> properties = stereotype.allAttributes();
					for (Property property : properties) {
						// if stereotype is null all property of stereotype has to be removed!
						if (property != null && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
							if (!helper.isPropertyExist(compartmentStructure, property)) {
								// go through each stereotype property
								CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(currentDomain, compartmentStructure, property, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE);
								CommandUtil.executeUnsafeCommand(command, currentView);
							}
						}
					}
				}

				BasicCompartment braceStructure = helper.getStereotypeCompartment(comment, stereotype);
				if (braceStructure == null) { // No Label Exist for this Stereotype
					// doesn't exist already
					if (!helper.isCompartmentExist(comment, stereotype)) {
						// Create Compartment
						CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(currentDomain, comment, stereotype, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
						CommandUtil.executeUnsafeCommand(command, currentView);

					}
					braceStructure = helper.getStereotypeBraceCompartment(comment, stereotype);
					if (braceStructure != null && stereotype != null) {

						EList<Property> properties = stereotype.allAttributes();
						for (Property property : properties) {
							// if stereotype is null all property of stereotype has to be removed!
							if (property != null && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
								if (!helper.isBracePropertyExist(braceStructure, property)) {
									// go through each stereotype property
									CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(currentDomain, braceStructure, property, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_BRACE_TYPE);
									CommandUtil.executeUnsafeCommand(command, currentView);
								}
							}
						}
					}
				}
			}

			if (StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION.equals(appliedStereotypesLocalization)) {
				hideStereotypePropertiesNotDisplayed(StereotypeLocationEnum.IN_COMMENT);
			}
		}
	}

	/**
	 * Get all the stereotypes applied on the view.
	 *
	 * @return The list of stereotypes.
	 */
	private List<Stereotype> getAllStereotypes() {
		List<Stereotype> stereotypes = new ArrayList<>();
		// Get the stereotype on the stereotype list
		String stereotypesToDisplay = migrationHelper.getStereotypesToDisplay(currentView);
		if (!StereotypeMigrationHelper.EMPTY_STRING.equals(stereotypesToDisplay)) {
			String[] split = stereotypesToDisplay.split(StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR);
			for (String stereotypeString : split) {
				Stereotype stereotypeFromString = migrationHelper.getStereotypeFromString(currentView, stereotypeString);
				if (null != stereotypeFromString) {
					stereotypes.add(stereotypeFromString);
				}
			}
		}

		// Get the stereotypes on the PropStereoDisplay details
		List<String> parsePropStereoDisplay = parsePropStereoDisplay();
		for (String stereotypeName : parsePropStereoDisplay) {
			Stereotype stereotypeFromString = migrationHelper.getStereotypeFromString(currentView, stereotypeName);
			if ((null != stereotypeFromString) && (!stereotypes.contains(stereotypeFromString))) {
				stereotypes.add(stereotypeFromString);
			}
		}

		return stereotypes;
	}

	/**
	 * Check all the Stereotype on the "PropStereoDisplay" details
	 *
	 * @return
	 */
	private List<String> parsePropStereoDisplay() {
		List<String> stringStereotypes = new ArrayList<>();
		String appliedStereotypesPropertiesToDisplay = migrationHelper.getAppliedStereotypesPropertiesToDisplay(currentView);
		if (!StereotypeMigrationHelper.EMPTY_STRING.equals(appliedStereotypesPropertiesToDisplay)) {
			String[] splitedProperties = appliedStereotypesPropertiesToDisplay.split(StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR);
			for (String property : splitedProperties) {
				// SPlit on the property separator to get only the name of the Stereotype
				int indexOf = property.indexOf(StereotypeDisplayMigrationConstant.EANNOTATION_PROPERTY_SEPARATOR);
				if (-1 != indexOf) {
					String substring = property.substring(0, indexOf);
					if (!stringStereotypes.contains(substring)) {
						stringStereotypes.add(substring);
					}
				}
			}
		}

		return stringStereotypes;
	}

	/**
	 *
	 * @param view
	 * @param stereotype
	 */
	private void createAppliedLabelStereotype(final View view, final Stereotype stereotype) {
		TransactionalEditingDomain currentDomain = getEditingDomain();
		// create only if the Label doesn't exist yet
		if (!helper.isLabelExist(view, stereotype)) {
			CreateStereotypeLabelCommand command = new CreateStereotypeLabelCommand(currentDomain, view, stereotype);
			CommandUtil.executeUnsafeCommand(command, view);
		}
	}

	private void createAppliedCompartmentStereotype(final Stereotype stereotype) {
		TransactionalEditingDomain currentDomain = getEditingDomain();
		if (!helper.isCompartmentExist(currentView, stereotype)) {
			CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(currentDomain, currentView, stereotype, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);
			CommandUtil.executeUnsafeCommand(command, currentView);
		}

		BasicCompartment compartment = helper.getStereotypeCompartment(currentView, stereotype);
		if (null != compartment && null != stereotype) {
			EList<Property> properties = stereotype.allAttributes();
			for (Property property : properties) {
				if (null != property && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
					if (!helper.isPropertyExist(compartment, property)) {
						// go through each stereotype property
						CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(currentDomain, compartment, property,
								StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE);

						// Record for undo if possible, otherwise unprotected
						CommandUtil.executeUnsafeCommand(command, currentView);
					}
				}
			}

			hideStereotypePropertiesNotDisplayed(StereotypeLocationEnum.IN_COMPARTMENT);
		}
	}

	/**
	 *
	 * @param stereotype
	 */
	private void createAppliedBraceCompartmentStereotype(final Stereotype stereotype) {
		TransactionalEditingDomain currentDomain = getEditingDomain();
		if (!helper.isBraceCompartmentExist(currentView, stereotype)) {
			// Create Compartment
			CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(currentDomain, currentView, stereotype, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
			CommandUtil.executeUnsafeCommand(command, currentView);
		}

		BasicCompartment braceCompartment = helper.getStereotypeBraceCompartment(currentView, stereotype);
		if (null != braceCompartment && null != stereotype) {
			EList<Property> properties = stereotype.allAttributes();
			for (Property property : properties) {
				// if stereotype is null all property of stereotype has to be removed!
				if (property != null && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
					if (!helper.isBracePropertyExist(braceCompartment, property)) {
						CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(currentDomain, braceCompartment, property, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_BRACE_TYPE);
						CommandUtil.executeUnsafeCommand(command, currentView);
					}
				}
			}

			hideStereotypePropertiesNotDisplayed(StereotypeLocationEnum.IN_BRACE);
		}
	}

	/**
	 * Hide visible Stereotype Property that should not be shown.
	 * List all the applied Stereotypes, if the applied stereotype is not in the Stereotype to Display list,
	 * hide the Node.
	 *
	 * @param view
	 *            The view of the object to migrate
	 */
	private void hideStereotypePropertiesNotDisplayed(final Enum<?> location) {
		String propertyList = migrationHelper.getAppliedStereotypesPropertiesToDisplay(currentView);
		if (!StereotypeMigrationHelper.EMPTY_STRING.equals(propertyList)) {
			List<String> displayedProperties = new ArrayList<>(Arrays.asList(propertyList.split(StereotypeDisplayMigrationConstant.EANNOTATION_LIST_SEPARATOR)));
			// If applied Stereotype is not in the Stereotype list to display, set the visibility to false to hide the Label.
			EList<Stereotype> appliedStereotypesFromView = migrationHelper.getAppliedStereotypesFromView(currentView);
			if (null != appliedStereotypesFromView) {
				Iterator<Stereotype> stereotypes = appliedStereotypesFromView.iterator();
				while (stereotypes.hasNext()) {
					Stereotype stereotype = stereotypes.next();
					for (Property property : stereotype.allAttributes()) {
						if (!displayedProperties.contains(property.getQualifiedName())) {
							hideProperty(stereotype, property, location);
						}
					}
				}
			}
		}
	}

	/**
	 * Hide a property.
	 *
	 * @param stereotype
	 * @param property
	 * @param location
	 */
	private void hideProperty(final Stereotype stereotype, final Property property, final Enum<?> location) {
		// Create the Property view if not existing and Make the properties node into the Compartment visible
		View compartment = provider.getCompartment(stereotype, location);
		if (compartment != null) {
			View propertyView = provider.getProperty(property, stereotype, location);
			if (propertyView != null) {
				migrationHelper.updateVisibilityAndPersistence(propertyView, currentView, false);
			}
		}
	}
}
