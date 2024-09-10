/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;

/**
 * Utility class for Connector edit helpers.
 */
public class ConnectorUtils {

	/**
	 * key used for parameters in the request. The value will be a {@link Property}
	 */
	public static final String PART_WITH_PORT = "partWithPort"; //$NON-NLS-1$

	public static final String OPPOSITE_PART_WITH_PORT = "oppositePartWithPort"; //$NON-NLS-1$

	/**
	 * Tries to find a common structure container view for source and target.
	 * 
	 * @param source
	 *        the source view
	 * @param target
	 *        the target view
	 * @return the least common structure container view
	 */
	public View deduceViewContainer(View source, View target) {

		List<View> sourceProposedContainers = getStructureContainers(source);
		List<View> targetProposedContainers = getStructureContainers(target);

		// The deduced container is the StructuredClassifier related to the least
		// common containing View.
		View deducedContainer = null;
		for(View container : sourceProposedContainers) {
			if(targetProposedContainers.contains(container)) {
				deducedContainer = container;
				break;
			}
		}

		return deducedContainer;
	}

	public Property getPartWithPort(View checkedView, View oppositeView) {
		Property result = null;
		EObject targetPort = checkedView.getElement();
		if(targetPort instanceof Port) {
			// Only look for PartWithPort if the role is a Port.

			View parentView = ViewUtil.getContainerView(checkedView);
			EObject semanticParent = parentView.getElement();
			if((semanticParent instanceof Property) && !(semanticParent instanceof Port)) {
				// Only add PartWithPort for assembly (not for delegation)
				if(!EcoreUtil.isAncestor(parentView, oppositeView)) {
					result = (Property)semanticParent;
				}
			}

		}

		return result;
	}


	/**
	 * Tries to find a common StructuredClassifier container to add the new Connector.
	 * 
	 * @param source
	 *        the source graphical view
	 * @param target
	 *        the target graphical view
	 * @return a common StructuredClassifier container (graphical search through views)
	 */
	public StructuredClassifier deduceContainer(View source, View target) {
		return (deduceViewContainer(source, target) != null) ? getStructuredClassifier(deduceViewContainer(source, target)) : null;
	}

	/**
	 * Collect container hierarchy for a view (including itself and keeping containment order).
	 * 
	 * @param view
	 *        the graphical view
	 * @return the list of containing Views.
	 */
	public List<View> getStructureContainers(View view) {

		List<View> containerViews = new ArrayList<>();

		for(View currentView = view; currentView != null; currentView = ViewUtil.getContainerView(currentView)) {
			if((currentView instanceof Shape) && (getStructuredClassifier(currentView) != null)) {
				containerViews.add(currentView);
			}
		}

		return containerViews;
	}

	/**
	 * Get the {@link StructuredClassifier} related to a View.
	 * If the view relates to a Property, returns its type in case it is a StructuredClassifier.
	 * 
	 * @param view
	 *        the graphical view
	 * @return the related {@link StructuredClassifier}
	 */
	public StructuredClassifier getStructuredClassifier(View view) {
		StructuredClassifier structuredClassifier = null;
		EObject semanticElement = view.getElement();
		if(semanticElement instanceof StructuredClassifier) {
			structuredClassifier = (StructuredClassifier)semanticElement;

		} else if(semanticElement instanceof Property) {
			Property property = (Property)semanticElement;
			if(property.getType() instanceof StructuredClassifier) {
				structuredClassifier = (StructuredClassifier)property.getType();
			}

		} 
		return structuredClassifier;
	}

	public ConnectorEnd getSourceConnectorEnd(Connector connector) {
		return connector.getEnds().get(0);
	}

	public ConnectorEnd getTargetConnectorEnd(Connector connector) {
		return connector.getEnds().get(1);
	}

	/**
	 * This methods looks for inconsistent views to delete in case the connector is deleted or
	 * re-oriented.
	 * 
	 * @param connector
	 *        the modified connector
	 * @return the list of {@link View} to delete
	 */
	public final Set<View> getViewsRepresentingConnector(final Connector connector) {
		final Set<View> viewsToDestroy = new HashSet<>();

		// Find Views in Diagrams that are referencing current member
		final Iterator<View> viewIt = CrossReferencerUtil.getCrossReferencingViews(connector, null).iterator();
		while(viewIt.hasNext()) {
			View view = viewIt.next();
			viewsToDestroy.add(view);
		}

		return viewsToDestroy;
	}

	/**
	 * 
	 * @param connector
	 *        the connector
	 * @return
	 *         <code>true</code> if we should apply UML rules
	 */
	//TODO : find a better way for that!
	public static final boolean applyUMLRulesForConnector(final Connector connector) {
		final Element element = connector.getOwner();
		return element.getAppliedStereotype("SysML::Blocks::Block") == null && element.getAppliedStereotype("SysML::ConstraintBlocks::ConstraintBlock") == null ; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * 
	 * @param connectorOwner
	 *            the owner of the connector
	 * @return
	 * 		the list of the possible role for the connector, according to the UML Standard 2.4:
	 *         We can deduce the possible roles for a UML Connector, because the roles must have the same owner than the connector:
	 *         UML Standard, p.181 : [3] The ConnectableElements attached as roles to each ConnectorEnd owned by a Connector must be roles
	 *         of the Classifier, that owned the Connector, or they must be ports of such roles. (p.181)
	 */
	public static final Collection<Property> getUMLPossibleRoles(final StructuredClassifier connectorOwner) {
		final Set<EncapsulatedClassifier> availableClassifiers = new HashSet<>();
		availableClassifiers.addAll(collectEncapsulatedClassifiers(connectorOwner.getAllAttributes()));
		availableClassifiers.addAll(collectEncapsulatedClassifiers(connectorOwner.getOwnedElements()));
		final Iterator<EncapsulatedClassifier> iterator = availableClassifiers.iterator();
		final Set<Property> result = new HashSet<>();
		while (iterator.hasNext()) {
			final EncapsulatedClassifier nextClassifier = iterator.next();
			result.addAll(nextClassifier.getOwnedPorts());
		}
		return result;
	}

	/**
	 * @param properties
	 *            the list of properties
	 * @return
	 * 		the list of encapsulated classifiers
	 */
	private static List<EncapsulatedClassifier> collectEncapsulatedClassifiers(List<Property> properties) {
		final List<EncapsulatedClassifier> result = new ArrayList<>();
		final Iterator<Property> propIterator = properties.iterator();
		while (propIterator.hasNext()) {
			final Property nextProp = propIterator.next();
			final Type type = nextProp.getType();
			if (type instanceof EncapsulatedClassifier) {
				result.add((EncapsulatedClassifier) type);
			}
		}
		return result;
	}

	/**
	 * @param ownedElements
	 *            the list of owned elements
	 * @return
	 * 		the list of encapsulated classifiers
	 */
	private static List<EncapsulatedClassifier> collectEncapsulatedClassifiers(EList<Element> ownedElements) {
		if (ownedElements == null) {
			return Collections.emptyList();
		}
		final List<EncapsulatedClassifier> result = new ArrayList<>();
		final Iterator<Element> packageIterator = ownedElements.iterator();
		while (packageIterator.hasNext()) {
			final Element next = packageIterator.next();
			if (next instanceof EncapsulatedClassifier) {
				result.add((EncapsulatedClassifier) next);
			}
		}
		return result;
	}	
	
}
