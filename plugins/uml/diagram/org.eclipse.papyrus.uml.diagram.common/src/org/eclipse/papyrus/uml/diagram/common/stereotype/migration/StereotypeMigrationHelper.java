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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayCommandExecution;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This class regroups the methods required to create the new Stereotype Structure based on the Old Structure.
 *
 * @author Céline JANSSENS
 *
 */
public final class StereotypeMigrationHelper {
	public static final String EMPTY_STRING = "";//$NON-NLS-1$

	/**
	 * singleton instance
	 */
	private static StereotypeMigrationHelper migrationHelper;

	/** Other Helper */
	private static StereotypeDisplayCommandExecution commandHelper = StereotypeDisplayCommandExecution.getInstance();
	private static StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/** Singleton constructor */
	private StereotypeMigrationHelper() {
	}

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static StereotypeMigrationHelper getInstance() {
		if (migrationHelper == null) {
			migrationHelper = new StereotypeMigrationHelper();
		}
		return migrationHelper;
	}


	/**
	 * Get the Semantic Element of the Comment View.
	 * The Comment View is not attached to its semantic element through the method {@link View#getElement()}.
	 * To retrieve the semantic element, it has been added as a namedStyle with the name {@link StereotypeDisplayConstant#STEREOTYPE_COMMENT_RELATION_NAME}
	 *
	 * @param view
	 *            The Stereotype Comment View (of type "StereotypeComment")
	 * @return The Base Element of the Comment
	 */
	public Element getOldCommentSemanticElement(final View view) {

		Element elementSemantic = null;
		if (isOldComment(view)) {
			// Retrieve the Base Element of the Comment as the Semantic element
			EObject baseElement = NotationUtils.getEObjectValue(view, StereotypeDisplayConstant.STEREOTYPE_COMMENT_RELATION_NAME, null);

			if (baseElement instanceof Element) {
				elementSemantic = (Element) baseElement;

			}
		}
		return elementSemantic;
	}



	/**
	 * Get a Stereotype from its StereotypeName
	 *
	 * @param view
	 *            View on which we try to retrieve the Stereotype
	 * @param stereotypeName
	 *            Stereotype Name
	 * @return Stereotype Associated to the stereotype Name for the View.
	 */
	public Stereotype getStereotypeFromString(final View view, final String stereotypeName) {
		Stereotype stereotype = null;

		Element element = helper.getSemanticElement(view);
		if (null != element) {
			stereotype = element.getAppliedStereotype(stereotypeName);
		}

		return stereotype;
	}

	/**
	 * Get a Property from its Name and its Stereotype
	 *
	 * @param view
	 *            The View Containing the Property Compartment (i.e : ClassImpl)
	 * @param propertyName
	 *            The Property Name
	 * @return The Property related to the View and to the Stereotype.
	 */
	public Property getPropertyFromString(final View view, final Stereotype stereotype, final String propertyName) {
		Property property = null;
		if (view != null && !propertyName.isEmpty()) {

			Iterator<Property> propertyIterator = stereotype.getAttributes().iterator();
			while (propertyIterator.hasNext() && property == null) {
				Property propertyTest = propertyIterator.next();
				if (propertyTest.getName().equals(propertyName)) {
					property = propertyTest;
				}
			}
		}

		return property;
	}



	/**
	 * Check is the node is persistent and make it persistent if not
	 * Check if the visibility should be modified, and do it accordingly
	 *
	 * @param view
	 *            The View to modify
	 * @param diagram
	 *            The Diagram that is migrated
	 * @param visible
	 *            true if the view has to be visible.
	 */
	public void updateVisibilityAndPersistence(final View view, final EObject object, final boolean visible) {
		if (view != null) {
			if (view.eContainer() instanceof View && !((View) view.eContainer()).getPersistedChildren().contains(view)) {
				commandHelper.setPersistency(migrationHelper.getDomain(object), view, false);
			}

			if (visible != view.isVisible()) {
				commandHelper.setVisibility(migrationHelper.getDomain(object), view, visible, false);
			}
		}


	}

	/**
	 * Get the Editing domain from Eobject
	 *
	 * @param object
	 *            Object used to retrieve the Transactional Editing Domain
	 * @see {@link CommandUtil#resolveEditingDomain(Object)}
	 *
	 * @return Transactional Domain
	 */
	public TransactionalEditingDomain getDomain(final EObject object) {
		return CommandUtil.resolveEditingDomain(object);
	}

	/**
	 * Get the EAnnotation, dedicated to the Stereotype Display User preferences (Old structure to be replaced by the new one)
	 *
	 * @param view
	 *            The View of which the EAnnotation is necessary.
	 * @return The EAnnotation of the view . Null if does not exist.
	 */
	public EAnnotation getStereotypeEAnnotation(final EModelElement view) {
		EAnnotation annotation = null;
		if (view != null) {
			annotation = view.getEAnnotation(StereotypeDisplayMigrationConstant.STEREOTYPE_ANNOTATION);
		}
		return annotation;

	}

	/**
	 *
	 * Define if a view has an Stereotype EAnnotation
	 *
	 * @param content
	 *            The view
	 * @return true if the Stereotype is not null.
	 */
	public boolean hasStereotypeEAnnotation(final View content) {

		return getStereotypeEAnnotation(content) != null;
	}

	/**
	 * Retrieve the list of the appliedStereotypes from a View
	 *
	 * @param view
	 *            The view for which the List of Stereotypes is asked
	 * @return The appliedStereotype List. Null if no UML element related to the View.
	 */
	public EList<Stereotype> getAppliedStereotypesFromView(final View view) {
		EList<Stereotype> list = null;

		Element element = helper.getSemanticElement(view);

		if (null != element) {
			list = element.getAppliedStereotypes();
		}

		return list;
	}


	/* ******************* Old EAnnotation Method *************************/

	/**
	 * get lhe list of stereotype to display with the qulifiedName.
	 *
	 * @param view
	 *            the view of the uml element
	 *
	 * @return the list of applied stereotype to display with their qualified name
	 */
	public String getStereotypesQNToDisplay(final EModelElement view) {
		EAnnotation eannotation = getStereotypeEAnnotation(view);
		if (eannotation != null) {
			EMap<String, String> entries = eannotation.getDetails();

			String stereotypesToDisplay = entries.get(StereotypeDisplayMigrationConstant.STEREOTYPE_WITHQN_LIST);
			if (stereotypesToDisplay != null) {
				return stereotypesToDisplay;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * get the list of stereotype to display from the eannotation.
	 *
	 * @param view
	 *            the view
	 *
	 * @return the list of stereotypes to display separated by a comma. the applied stereotype to
	 *         display is represented by the qualified name of the stereotype
	 *         Empty String by default.
	 */
	public String getStereotypesToDisplay(final EModelElement view) {
		EAnnotation eannotation = getStereotypeEAnnotation(view);
		if (eannotation != null) {
			EMap<String, String> entries = eannotation.getDetails();

			String stereotypesToDisplay = entries.get(StereotypeDisplayMigrationConstant.STEREOTYPE_LIST);
			if (stereotypesToDisplay != null) {
				return stereotypesToDisplay;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * Gets the applied stereotypes properties localization.
	 *
	 * @param view
	 *            the view
	 *
	 * @return the applied stereotypes properties localization
	 */
	public String getAppliedStereotypesPropertiesLocalization(final EModelElement view) {
		EAnnotation eannotation = getStereotypeEAnnotation(view);
		if (eannotation != null) {
			EMap<String, String> entries = eannotation.getDetails();

			String displayLocalization = entries.get(StereotypeDisplayConstant.STEREOTYPE_PROPERTY_LOCATION);
			if (displayLocalization != null && !displayLocalization.equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION)) {
				return displayLocalization;
			}
		}


		return StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION;
	}


	/**
	 * Gets the applied stereotypes properties to display.
	 *
	 * @param view
	 *            the view the view that displays the stereotyped element
	 *
	 * @return the applied stereotypes properties to display
	 */
	public String getAppliedStereotypesPropertiesToDisplay(final EModelElement view) {
		EAnnotation eannotation = getStereotypeEAnnotation(view);
		if (eannotation != null) {
			EMap<String, String> entries = eannotation.getDetails();

			String stereotypesToDisplay = entries.get(StereotypeDisplayMigrationConstant.PROPERTY_STEREOTYPE_DISPLAY);
			if (stereotypesToDisplay != null) {
				return stereotypesToDisplay;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * Define if the passed object is the Old Comment View
	 *
	 * @param object
	 *            The object to be tested
	 * @return true if the object is a view of type {@link StereotypeDisplayMigrationConstant#OLD_COMMENT_TYPE}
	 */
	public boolean isOldComment(final Object object) {

		if (object instanceof View) {
			View view = (View) object;
			return (view.getType().equals(StereotypeDisplayMigrationConstant.OLD_COMMENT_TYPE));

		}

		return false;
	}



	/**
	 * Retrieve the old Comment from a hostView
	 *
	 * @param hostView
	 *            The view of which the old comment is retrieve
	 * @return The OldComment View or Null if not found.
	 */
	public View getOldStereotypeComment(final View semanticView) {
		Node node = null;
		if (semanticView != null && semanticView.getSourceEdges() != null) {
			// look for all links with the id AppliedStereotypesCommentLinkEditPart.ID
			Iterator<Edge> edgeIterator = semanticView.getSourceEdges().iterator();
			Edge appliedStereotypeLink = null;
			while (edgeIterator.hasNext() && appliedStereotypeLink == null) {
				Edge edge = edgeIterator.next();
				if (edge.getType().equals(StereotypeDisplayMigrationConstant.OLD_COMMENT_LINK_TYPE)) {
					appliedStereotypeLink = edge;
				}
			}
			if (appliedStereotypeLink == null) {
				return null;
			}
			node = (Node) appliedStereotypeLink.getTarget();
		}
		return node;
	}

	/**
	 * Retrieve the Old Link of the HostView
	 *
	 * @param hostView
	 *            The view of which the old link is retrieve
	 * @return The Old Link View or Null if not found.
	 */
	public Edge getOldStereotypeLinkComment(final View semanticView) {
		Edge appliedStereotypeLink = null;
		if (semanticView != null && semanticView.getSourceEdges() != null) {
			// look for all links with the id AppliedStereotypesCommentLinkEditPart.ID
			Iterator<Edge> edgeIterator = semanticView.getSourceEdges().iterator();

			while (edgeIterator.hasNext() && appliedStereotypeLink == null) {
				Edge edge = edgeIterator.next();
				if (edge.getType().equals(StereotypeDisplayMigrationConstant.OLD_COMMENT_LINK_TYPE)) {
					appliedStereotypeLink = edge;
				}
			}
		}
		return appliedStereotypeLink;
	}

	/**
	 * Define if an Old Comment is Orphan
	 *
	 * @param view
	 *            The View of the Old Comment
	 * @return True if the Comment is not related to another Element or if no stereotype is applied to this Element.
	 */
	public boolean isOrphanComment(final View view) {
		boolean orphanComment = false;

		if (isOldComment(view)) {
			// if no stereotypes applied on the Base element, the comment is considered as Orphan
			Element element = getOldCommentSemanticElement(view);
			if (element == null || element.getAppliedStereotypes().size() == 0) {
				orphanComment = true;
			}

		}
		return orphanComment;
	}





}
