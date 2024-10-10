/*****************************************************************************
 * Copyright (c) 2022 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ibtihel Khemir (CEA LIST) <ibtihel.khemir@cea.fr> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.statemachine.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.papyrus.sirius.uml.diagram.statemachine.StateMachineServices;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

/**
 * 
 */
public class StateMachineDiagramServices extends AbstractDiagramServices {
	/**
	 * A singleton instance to be accessed by other java services.
	 */
	public static final StateMachineDiagramServices INSTANCE = new StateMachineDiagramServices();

	/**
	 * TODO : adapted from ClassDiagram
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createSMD(Element parent, String type, String referenceName, DSemanticDecorator targetView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		if(type.equalsIgnoreCase("Constraint") || type.equalsIgnoreCase("Region")) {
			StateMachineServices stateMachineServices = new StateMachineServices();
			EObject stateParent = stateMachineServices.getStateParent(parent);
			return commonDiagramServices.createElement((Element) stateParent, type, referenceName, targetView);
		}
		else {
			return commonDiagramServices.createElement(parent, type, referenceName, targetView);
		}
	}
	
	/**
	 * TODO : adapted from PapyrusWeb
	 * Creates a new semantic element, initialize, create a view and in addition set Pseudostate.kind
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @return a new instance or <code>null</code> if the creation failed
	 */
    public EObject createPseudoState(Element parent, String type, String referenceName, DSemanticDecorator targetView, String pseudoStateKind) {
    		StateMachineServices stateMachineServices = new StateMachineServices();
    		EObject stateParent = stateMachineServices.getStateParent(parent);
    		EObject newObject=null;
    		String containerViewExpression = "aql:containerView"; //$NON-NLS-1$
    		final Session session = SessionManager.INSTANCE.getSession(parent);
    		if(referenceName.equalsIgnoreCase("subvertex")) { //$NON-NLS-1$
    			newObject = this.createSMD(parent, type, referenceName, targetView);
    			if(parent instanceof Region) {
    				this.createView(newObject, targetView, session, containerViewExpression);
    			}
    		}
    		if(referenceName.equalsIgnoreCase("connectionPoint")) { //$NON-NLS-1$
    			if(parent instanceof State) {
    				newObject = this.createSMD(parent, type, referenceName, targetView);
    				this.createView(newObject, (DSemanticDecorator) targetView.eContainer(), session, containerViewExpression);
    			}
    			if(parent instanceof Region) {
        			newObject = this.createSMD((Element) stateParent, type, referenceName, targetView);
        		}
    		}
    		
    		if (newObject instanceof Pseudostate pseudostate) {
    			pseudostate.setKind(PseudostateKind.get(pseudoStateKind));
    			this.resetDefaultName(pseudostate);
    		}
    		return newObject;
    	}


    /**
     * Service that reset the default name of an object.
     *
     * @param self
     *            the eObject to set
     * @return self
     */
    public NamedElement resetDefaultName(NamedElement self) {
        if (self == null) {
            return self;
        }
        self.setName(null);
        self.setName(new ElementDefaultNameProvider().getDefaultName(self, self.eContainer()));
        return self;
    }

    /**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
    public EObject createDomainBasedEdgeSMD(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
    	DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
    	return domainBasedEdgeServices.createDomainBasedEdge(source, target, type, containementReferenceName, sourceView, targetView);
    }

	/**
	 * TODO : adapted from ClassDiagram
	 * Get the target element of the Link.
	 * 
	 * @param source
	 *            the element ({@link Comment} or {@link Constraint}
	 * @return
	 *         the list of annotated elements if source if a {@link Comment} and the list of constrained elements if the source is a {@link Constraint} and <code>null</code> in other cases
	 */
	public static Collection<Element> link_getTarget_SMD(final Element source) {
		if (source instanceof Constraint) {
			final Constraint sourceElement = (Constraint) source;
			return sourceElement.getConstrainedElements();
		} else if (source instanceof Comment) {
			final Comment sourceElement = (Comment) source;
			return sourceElement.getAnnotatedElements();
		}
		return null;
	}

	/**
	 * TODO : adapted from ClassDiagram
	 * Service used to determine if the selected Link edge source could be reconnected to an element.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param newSource
	 *            Represents the source element pointed by the edge after reconnecting
	 * @return true if the edge could be reconnected
	 */
	public boolean link_canReconnectSource_SMD(final Element context, final Element newSource) {
		// we want to avoid to change the semantic of a link : either is works on Constraint, either it works on Comment
		if (context instanceof Constraint && newSource instanceof Constraint) {
			return true;
		}
		if (context instanceof Comment && newSource instanceof Comment) {
			return true;
		}
		return false;
	}

	/**
	 * TODO : adapted from ClassDiagram
	 * Service used to determine if the selected Link edge target could be
	 * reconnected to an element.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param newSource
	 *            Represents the source element pointed by the edge after reconnecting
	 * @return true if the edge could be reconnected
	 */
	public boolean link_canReconnectTarget_SMD(Element context, Element newSource) {
		return newSource instanceof State
				|| newSource instanceof Comment
				|| newSource instanceof Constraint
				|| newSource instanceof FinalState
				|| newSource instanceof Transition
				|| newSource instanceof Pseudostate;
	}

	/**
	 * TODO : adapted from ClassDiagram
	 * Service used to reconnect a Link source.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param oldsource
	 *            Represents the semantic element pointed by the edge before reconnecting
	 * @param newSource
	 *            Represents the semantic element pointed by the edge after reconnecting
	 * @param otherEnd
	 *            Represents the view attached to the target of the link
	 */
	public void link_reconnectSource_SMD(final Element context, final Element oldSource, final Element newSource, final EObject otherEnd) {
		Element target = null;
		if (otherEnd instanceof DSemanticDecorator) {
			target = (Element) ((DSemanticDecorator) otherEnd).getTarget();
		}

		// remove the target from the old source
		if (oldSource instanceof Comment) {
			((Comment) oldSource).getAnnotatedElements().remove(target);
		} else if (oldSource instanceof Constraint) {
			((Constraint) oldSource).getConstrainedElements().remove(target);
		}

		// add the target to the new source
		if (newSource instanceof Comment) {
			((Comment) newSource).getAnnotatedElements().add(target);
		} else if (newSource instanceof Constraint) {
			((Constraint) newSource).getConstrainedElements().add(target);
		}
	}

	/**
	 * TODO : adapted from ClassDiagram
	 * Service used to reconnect a Link edge target.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param oldTarget
	 *            Represents the semantic element pointed by the edge before reconnecting
	 * @param newTarget
	 *            Represents the semantic element pointed by the edge after reconnecting
	 * @param otherEnd
	 *            Represents the view attached to the source of the link
	 */
	public void link_reconnectTarget_SMD(final Element context, final Element oldTarget, final Element newTarget, final EObject otherEnd) {
		Element source = null;
		if (otherEnd instanceof DSemanticDecorator) {
			source = (Element) ((DSemanticDecorator) otherEnd).getTarget();
		}

		if (source instanceof Comment) {
			((Comment) source).getAnnotatedElements().remove(oldTarget);
			((Comment) source).getAnnotatedElements().add(newTarget);
		} else if (source instanceof Constraint) {
			((Constraint) source).getConstrainedElements().remove(oldTarget);
			((Constraint) source).getConstrainedElements().add(newTarget);
		}
	}

	/**
	 * TODO : adapted from ClassDiagram
	 * Check if the source and target are valid for a ContextLink
	 * 
	 * @param context
	 *            the current context
	 * @param sourceView
	 *            the source view
	 * @param targetView
	 *            the target view
	 * @param source
	 *            the semantic source element
	 * @param target
	 *            the semantic target element
	 * @return true if the source and target are valid
	 */
	public boolean contextLink_isValidSourceAndTarget_SMD(final EObject context, final EObject sourceView, final EObject targetView, final Element source, final Element target) {
		boolean isValid = false;
		if (source == target) {
			// 1. we forbid reflexive Context of course
			return false;
		}

		// 2. semantic condition
		if (source instanceof Constraint) {
			isValid = target instanceof Namespace;
		}
		return isValid;
	}

	/**
	 * Service used to determine if the selected Transition edge source could be reconnected to an element.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param newSource
	 *            Represents the source element pointed by the edge after reconnecting
	 * @return true if the edge could be reconnected
	 */
	public boolean transition_canReconnectSource(final Element context, final Element newSource) {
		return newSource instanceof FinalState
				|| newSource instanceof Pseudostate
				|| newSource instanceof State;
	}

	/**
	 * Service used to determine if the selected Transition edge target could be reconnected to an element.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param newTarget
	 *            Represents the source element pointed by the edge after reconnecting
	 * @return true if the edge could be reconnected
	 */
	public boolean transition_canReconnectTarget(final Element context, final Element newTarget) {
		return newTarget instanceof FinalState
				|| newTarget instanceof Pseudostate
				|| newTarget instanceof State;
	}

	/**
	 * Service used to reconnect a Transition source.
	 *
	 * @param transition
	 *            Element attached to the existing edge
	 * @param newSource
	 *            Represents the semantic element pointed by the edge after reconnecting
	 * @param oldsource
	 *            Represents the semantic element pointed by the edge before reconnecting
	 */
	public void transition_reconnectSource(final Transition transition, final Vertex oldSource, final Vertex newSource) {
		transition.setSource(newSource);
		final Region region = newSource.getContainer();
		final Element owner = transition.getOwner();
		if (owner != region) {
			region.getTransitions().add(transition);
		}
	}

	/**
	 * Service used to reconnect a Transition edge target.
	 *
	 * @param context
	 *            Element attached to the existing edge
	 * @param oldTarget
	 *            Represents the semantic element pointed by the edge before reconnecting
	 * @param newTarget
	 *            Represents the semantic element pointed by the edge after reconnecting
	 */
	public void transition_reconnectTarget(final Transition transition, final Vertex oldTarget, final Vertex newTarget) {
		transition.setTarget(newTarget);
	}

	/**
	 * 
	 * @param semanticContext
	 *            the context in which we are looking for {@link Transition}
	 * @return
	 *         {@link Transition} available in the context
	 */
	public Collection<Transition> transition_getSemanticCandidates(final EObject semanticContext) {
		final Collection<Transition> transitions = new HashSet<Transition>();
		if (semanticContext instanceof StateMachine) {
			final StateMachine stateMachine = (StateMachine) semanticContext;
			for (Region r : stateMachine.getRegions()) {
				transitions.addAll(getAllTransition(r));
			}
		}
		return transitions;
	}

	/**
	 * 
	 * @param reg
	 *            a UML {@link Region}
	 * @return
	 *         all {@link Transition} recursively
	 **/
	private static final Collection<Transition> getAllTransition(final Region reg) {
		final Collection<Transition> transitions = new HashSet<Transition>();
		final Iterator<NamedElement> iter = reg.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Region) {
				transitions.addAll(getAllTransition((Region) current));
			}
			if(current instanceof State) {
				transitions.addAll(getAllTransition((State) current));
			}
			if (current instanceof Transition) {
				transitions.add((Transition) current);
			}
		}
		return transitions;
	}
	
	/**
	 * 
	 * @param reg
	 *            a UML {@link State}
	 * @return
	 *         all {@link Transition} recursively
	 **/
	private static final Collection<Transition> getAllTransition(final State state) {
		final Collection<Transition> transitions = new HashSet<Transition>();
		final Iterator<Region> iter = state.getRegions().iterator();
		while (iter.hasNext()) {
			transitions.addAll(getAllTransition(iter.next()));
		}
		return transitions;
	}


}
