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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 433206
 *   Christian W. Damus - bug 473148
 *   Christian W. Damus - bug 478558
 *   Christian W. Damus - bug 477384
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.canonical;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static org.eclipse.papyrus.uml.tools.utils.UMLUtil.isAssociationEnd;
import static org.eclipse.papyrus.uml.tools.utils.UMLUtil.isRelationship;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ISemanticChildrenStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.EdgeWithNoSemanticElementRepresentationImpl;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Default semantic children strategy for canonical UML views.
 */
public class DefaultUMLSemanticChildrenStrategy implements ISemanticChildrenStrategy {

	public DefaultUMLSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<Element> result = null;

		if (semanticFromEditPart instanceof Element) {
			Element element = (Element) semanticFromEditPart;
			Iterable<Element> owned = element.getOwnedElements();

			// Never include relationships that are owned, because they would be found
			// when relationships are requested
			owned = Iterables.filter(owned, not(isRelationship()));

			// And also don't include association ends because we visualize the association,
			// unless we actually won't visualize the association because the other type
			// is not present in the diagram, in which case the attribute style is the only
			// way we can show the end
			owned = Iterables.filter(owned, not(and(isAssociationEnd(), isPropertyTypeVisualized(viewFromEditPart))));

			result = Lists.newArrayList(owned);
		}

		return result;
	}

	protected Predicate<Object> isPropertyTypeVisualized(View viewContext) {
		final Diagram diagram = viewContext.getDiagram();

		return new Predicate<Object>() {
			@Override
			public boolean apply(Object input) {
				boolean result = false;

				if (input instanceof Property) {
					Type type = ((Property) input).getType();
					if (type != null) {
						for (View view : DiagramEditPartsUtil.getEObjectViews(type)) {
							if (view.getDiagram() == diagram) {
								// type is visualized in the same diagram as the property
								result = true;
								break;
							}
						}
					}
				}

				return result;
			}
		};
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		List<EObject> result = null;

		if (semanticFromEditPart instanceof Element) {
			Element element = (Element) semanticFromEditPart;

			// Add relationships
			result = Lists.<EObject> newArrayList(element.getRelationships());

			// But we don't visualize associations as relationships of properties (which can
			// be visualized as shapes in a composite structure diagram, for example)
			result = new CleanRelationshipsSwitch(viewFromEditPart, result).doSwitch(element);

			// And then add relationship-like elements
			result = new ConnectionsSwitch(viewFromEditPart, result).doSwitch(element);
		}

		return result;
	}


	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		Collection<Element> result = null;

		if (semanticFromEditPart instanceof NamedElement) {
			// Handle changes to client/supplier dependencies
			// TODO: This will cause a lot of unneeded refresh. Performance problem? Better way?
			Element package_ = ((NamedElement) semanticFromEditPart).getNearestPackage();
			if (package_ != null) {
				result = Collections.singletonList(package_);
			}
		}

		return result;
	}

	protected Property getExpectedPartWithPort(View possiblePortView) {
		Property result = null;

		if (possiblePortView.getElement() instanceof Port) {
			View parentView = TypeUtils.as(possiblePortView.eContainer(), View.class);
			if (parentView != null) {
				result = TypeUtils.as(parentView.getElement(), Property.class);
			}
		}

		return result;
	}

	@Override
	public Object getSource(EObject connectionElement) {
		return ConnectionSourceSwitch.INSTANCE.doSwitch(connectionElement);
	}

	@Override
	public Object getTarget(EObject connectionElement) {
		return ConnectionTargetSwitch.INSTANCE.doSwitch(connectionElement);
	}

	//
	// Nested types
	//

	/**
	 * A computation of the elements associated with some element
	 * that are not {@link Relationship}s but are visualized as
	 * connections in a manner similar to {@link Relationship}s.
	 */
	private class ConnectionsSwitch extends UMLSwitch<List<EObject>> {
		private final View visualContext;
		private final List<EObject> result;

		ConnectionsSwitch(View visualContext, List<EObject> result) {
			super();

			this.visualContext = visualContext;
			this.result = result;
		}

		@Override
		public List<EObject> doSwitch(EObject eObject) {
			super.doSwitch(eObject);

			return result;
		}

		@Override
		public List<EObject> caseConnectableElement(ConnectableElement object) {
			Property expectedPartWithPort = getExpectedPartWithPort(visualContext);

			for (ConnectorEnd next : object.getEnds()) {
				// Only report connectors for which the part-with-port matches in the visual context
				if (next.getPartWithPort() == expectedPartWithPort) {
					result.add(next.getOwner()); // the Connector
				}
			}
			return super.caseConnectableElement(object);
		}

		@Override
		public List<EObject> caseActivityNode(ActivityNode object) {
			// Use intermediate unique EList to avoid duplicates
			EList<EObject> outAndIn = new UniqueEList<EObject>();
			outAndIn.addAll(object.getOutgoings());
			outAndIn.addAll(object.getIncomings());
			result.addAll(outAndIn);
			return super.caseActivityNode(object);
		}

		@Override
		public List<EObject> caseVertex(Vertex object) {
			// Use intermediate unique EList to avoid duplicates
			EList<EObject> outAndIn = new UniqueEList<EObject>();
			outAndIn.addAll(object.getOutgoings());
			outAndIn.addAll(object.getIncomings());
			result.addAll(outAndIn);
			return super.caseVertex(object);
		}

		@Override
		public List<EObject> caseMessageEnd(MessageEnd object) {
			result.add(object.getMessage());
			return super.caseMessageEnd(object);
		}

		@Override
		public List<EObject> caseConstraint(Constraint object) {
			@SuppressWarnings("unchecked")
			List<EObject> sourceEdges = visualContext.getSourceEdges();
			for (EObject nextEdge : sourceEdges) {
				if (false == nextEdge instanceof View) {
					continue;
				}
				if (((View) nextEdge).getElement() == null) {
					result.add(new EdgeWithNoSemanticElementRepresentationImpl(object, object.eContainer(), ((View) nextEdge).getType()));
					break;
				}
			}
			return super.caseConstraint(object);
		}
	}

	/**
	 * An algorithm that removes relationships or relationship-like elements
	 * from an element's semantic connections that it is not appropriate to
	 * try to visualize in a given diagram context.
	 */
	private class CleanRelationshipsSwitch extends UMLSwitch<List<EObject>> {
		@SuppressWarnings("unused") // It isn't used, *yet*
		private final View visualContext;
		private final List<EObject> result;

		CleanRelationshipsSwitch(View visualContext, List<EObject> result) {
			super();

			this.visualContext = visualContext;
			this.result = result;
		}

		@Override
		public List<EObject> doSwitch(EObject eObject) {
			super.doSwitch(eObject);

			return result;
		}

		@Override
		public List<EObject> caseProperty(Property object) {
			if (object.getAssociation() != null) {
				result.remove(object.getAssociation());
			}
			return super.caseProperty(object);
		}
	}

	/**
	 * A computation of the source element of a relationship or
	 * relationship-like element that is to be visualized.
	 */
	private static class ConnectionSourceSwitch extends UMLSwitch<Object> {
		static final ConnectionSourceSwitch INSTANCE = new ConnectionSourceSwitch();

		@Override
		public Object caseDirectedRelationship(DirectedRelationship object) {
			List<Element> sources = object.getSources();
			return sources.isEmpty() ? null : sources.get(0);
		}

		@Override
		public Object caseRelationship(Relationship object) {
			List<Element> related = object.getRelatedElements();
			return related.isEmpty() ? null : related.get(0);
		}

		@Override
		public Object caseAssociation(Association object) {
			Property sourceEnd = getSourceEnd(object);

			// The classifier at the source end is the other end's type (the
			// source end may not be owned by the source type)
			return (sourceEnd == null) ? null : sourceEnd.getOtherEnd().getType();
		}

		static Property getSourceEnd(Association association) {

			Property result;

			List<Property> ends = association.getMemberEnds();
			if (ends.size() != 2) {
				result = null;
			} else {
				Property end1 = ends.get(0);
				Property end2 = ends.get(1);

				// The first end that is classifier-owned is the "source"
				if (end1.getOwningAssociation() == null) {
					result = end1;
				} else if (end2.getOwningAssociation() == null) {
					result = end2;
				} else {
					// Otherwise, the first end that is navigable is the "source"
					if (association.getNavigableOwnedEnds().contains(end1)) {
						result = end1;
					} else if (association.getNavigableOwnedEnds().contains(end2)) {
						result = end2;
					} else {
						// Otherwise, it's just the first end
						result = end1;
					}
				}
			}

			return result;
		}

		@Override
		public Object caseConnector(Connector object) {
			Object result = null;

			List<ConnectorEnd> ends = object.getEnds();
			ConnectorEnd source = ends.isEmpty() ? null : ends.get(0);
			if (source != null) {
				result = (source.getPartWithPort() != null)
						? ISemanticChildrenStrategy.createVisualStack(source.getPartWithPort(), source.getRole())
						: source.getRole();
			}

			return result;
		}

		@Override
		public Object caseTransition(Transition object) {
			return object.getSource();
		}

		@Override
		public Object caseActivityEdge(ActivityEdge object) {
			return object.getSource();
		}

		@Override
		public Object caseMessage(Message object) {
			return object.getSendEvent();
		}
	}

	/**
	 * A computation of the target element of a relationship or
	 * relationship-like element that is to be visualized.
	 */
	private static class ConnectionTargetSwitch extends UMLSwitch<Object> {
		static final ConnectionTargetSwitch INSTANCE = new ConnectionTargetSwitch();

		@Override
		public Object caseDirectedRelationship(DirectedRelationship object) {
			List<Element> targets = object.getTargets();
			return targets.isEmpty() ? null : targets.get(0);
		}

		@Override
		public Object caseRelationship(Relationship object) {
			List<Element> related = object.getRelatedElements();
			return (related.size() < 2) ? null : related.get(1);
		}

		@Override
		public Object caseAssociation(Association object) {
			Property sourceEnd = ConnectionSourceSwitch.getSourceEnd(object);

			// The target type is the type of the source end
			return (sourceEnd == null) ? null : sourceEnd.getType();
		}

		@Override
		public Object caseConnector(Connector object) {
			Object result = null;

			List<ConnectorEnd> ends = object.getEnds();
			ConnectorEnd source = ends.isEmpty() ? null : ends.get(1);
			if (source != null) {
				result = (source.getPartWithPort() != null)
						? ISemanticChildrenStrategy.createVisualStack(source.getPartWithPort(), source.getRole())
						: source.getRole();
			}

			return result;
		}

		@Override
		public Object caseTransition(Transition object) {
			return object.getTarget();
		}

		@Override
		public Object caseActivityEdge(ActivityEdge object) {
			return object.getTarget();
		}

		@Override
		public Object caseMessage(Message object) {
			return object.getReceiveEvent();
		}
	}
}
