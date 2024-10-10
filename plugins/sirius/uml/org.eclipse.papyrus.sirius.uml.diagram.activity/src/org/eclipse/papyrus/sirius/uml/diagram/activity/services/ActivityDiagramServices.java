/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.ReconnectServices;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityGroup;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the Activity diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class ActivityDiagramServices extends AbstractDiagramServices {

	private static final String ACTIVITY_EDGE_CONTAINMENT_FEATURE_NAME = "edge"; //$NON-NLS-1$
	private static final String AD_DECISION_NODE = "AD_DecisionNode"; //$NON-NLS-1$
	private static final String DECISION_INPUT = "decisionInput"; //$NON-NLS-1$

	/**
	 * Used to retrieve ActivityNodes to display according to the semantic context.
	 * <ul>
	 * <li>For an Activity, ActivityNodes that are not already displayed in an ActivityPartition.</li>
	 * <li>For an ActivityPartition, ActivityNodes from the ActivityPartition#node feature.</li>
	 * </ul>
	 *
	 * @param semanticContext
	 *            the context in which ActivityNodes should be displayed.
	 * @return all ActivityNodes that can be displayed in the specified context
	 */
	public Collection<ActivityNode> getActivityNodeCandidates(final EObject semanticContext) {
		Collection<ActivityNode> activityNodes = Collections.emptyList();
		if (semanticContext instanceof Activity) {
			Activity activity = (Activity) semanticContext;
			activityNodes = activity.getNodes().stream().filter(node -> node.getInGroups().isEmpty() && node.getInStructuredNode() == null).collect(Collectors.toList());
		} else if (semanticContext instanceof ActivityPartition) {
			ActivityPartition activityPartition = (ActivityPartition) semanticContext;
			activityNodes = activityPartition.getNodes().stream().filter(node -> node.getInStructuredNode() == null).collect(Collectors.toList());
		} else if (semanticContext instanceof InterruptibleActivityRegion) {
			InterruptibleActivityRegion interruptibleActivityRegion = (InterruptibleActivityRegion) semanticContext;
			activityNodes = interruptibleActivityRegion.getNodes().stream().filter(node -> node.getInStructuredNode() == null).collect(Collectors.toList());
		} else if (semanticContext instanceof StructuredActivityNode) {
			StructuredActivityNode structuredActivityNode = (StructuredActivityNode) semanticContext;
			activityNodes = structuredActivityNode.getNodes();
		}
		return activityNodes;
	}

	/**
	 * Used to retrieve ActivityPartitions to display according to the semantic context.
	 * <ul>
	 * <li>For an Activity, ActivityPartitions filtered from the Activity#ownedGroup feature.</li>
	 * <li>For an ActivityPartition, ActivityPartitions from the ActivityPartition#subpartition feature.</li>
	 * </ul>
	 *
	 * @param semanticContext
	 *            the context in which ActivityPartitions should be displayed
	 * @return all ActivityPartitions that can be displayed in the specified context
	 */
	public Collection<ActivityPartition> getActivityPartitionCandidates(final EObject semanticContext) {
		Collection<ActivityPartition> activityPartitions = Collections.emptyList();
		if (semanticContext instanceof Activity) {
			Activity activity = (Activity) semanticContext;
			activityPartitions = activity.getPartitions();
		} else if (semanticContext instanceof ActivityPartition) {
			ActivityPartition activityPartition = (ActivityPartition) semanticContext;
			activityPartitions = activityPartition.getSubpartitions();
		}
		return activityPartitions;
	}

	/**
	 * Used to retrieve InterruptibleActivityRegion to display according to the semantic context.
	 * <ul>
	 * <li>For an Activity, InterruptibleActivityRegions filtered from the Activity#ownedGroup feature.</li>
	 * </ul>
	 *
	 * @param semanticContext
	 *            the context in which InterruptibleActivityRegions should be displayed
	 * @return all InterruptibleActivityRegions that can be displayed in the specified context
	 */
	public Collection<InterruptibleActivityRegion> getInterruptibleActivityRegionCandidates(final EObject semanticContext) {
		Collection<InterruptibleActivityRegion> interruptibleActivityRegions = Collections.emptyList();
		if (semanticContext instanceof Activity) {
			Activity activity = (Activity) semanticContext;
			interruptibleActivityRegions = activity.getOwnedGroups().stream()
					.filter(InterruptibleActivityRegion.class::isInstance)
					.map(InterruptibleActivityRegion.class::cast)
					.collect(Collectors.toList());
		}
		return interruptibleActivityRegions;
	}

	/**
	 * Provides the input or output {@link ExpansionNode} from the given {@link ExpansionRegion}
	 *
	 * @param expansionRegion
	 *            the parent {@link ExpansionRegion}.
	 * @return the list of {@link ExpansionNode}.
	 */
	public Collection<ExpansionNode> getExpansionNodesCandidates(final ExpansionRegion expansionRegion) {
		Collection<ExpansionNode> expansionNodes = new HashSet<>();
		expansionNodes.addAll(expansionRegion.getInputElements());
		expansionNodes.addAll(expansionRegion.getOutputElements());
		return expansionNodes;
	}

	/**
	 * This method is used to create an element in the Activity Diagram.
	 *
	 * @param parent
	 *            the semantic parent that should contain the object to create
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @param targetView
	 *            the target view
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createAD(Element parent, String type, String referenceName, DSemanticDecorator targetView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		return commonDiagramServices.createElement(parent, type, referenceName, targetView);
	}

	private EObject caseActivityNodeInActivityGroup(Element parent, String type, DSemanticDecorator targetView, CommonDiagramServices commonDiagramServices, EStructuralFeature parentActivityContainmentFeature) {
		Activity parentActivity = this.findActivity(parent);
		EObject createdElement = null;
		if (parentActivity != null) {
			createdElement = commonDiagramServices.createElement(parentActivity, type, parentActivityContainmentFeature.getName(), targetView);
			if (createdElement instanceof ActivityNode) {
				ElementFeatureModifier featureModifier = new ElementFeatureModifier(this.getECrossReferenceAdapter(parent), new EditableChecker());
				String featureName = null;
				if (parent instanceof ActivityPartition) {
					featureName = UMLPackage.eINSTANCE.getActivityPartition_Node().getName();
				} else if (parent instanceof InterruptibleActivityRegion) {
					featureName = UMLPackage.eINSTANCE.getInterruptibleActivityRegion_Node().getName();
				}
				if (featureName != null) {
					featureModifier.addValue(parent, featureName, createdElement);
				}
			}
		}
		return createdElement;
	}

	/**
	 * Service to create a new ActivityPartition in an Activity or ActivityPartition.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created ActivityPartition or null if something went wrong.
	 */
	public EObject createActivityPartition(Element parent, DSemanticDecorator targetView) {
		EObject returnedElement = null;
		if (parent instanceof Activity) {
			returnedElement = this.createAD(parent, UMLPackage.eINSTANCE.getActivityPartition().getName(), UMLPackage.eINSTANCE.getActivity_Partition().getName(), targetView);
		} else if (parent instanceof ActivityPartition) {
			returnedElement = this.createAD(parent, UMLPackage.eINSTANCE.getActivityPartition().getName(), UMLPackage.eINSTANCE.getActivityPartition_Subpartition().getName(), targetView);
		}
		return returnedElement;
	}

	/**
	 * Service to create a new InterruptibleActivityRegion in an Activity.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created InterruptibleActivityRegion or null if something went wrong.
	 */
	public EObject createInterruptibleActivityRegion(Element parent, DSemanticDecorator targetView) {
		EObject returnedElement = null;
		if (parent instanceof Activity) {
			returnedElement = this.createAD(parent, UMLPackage.eINSTANCE.getInterruptibleActivityRegion().getName(), UMLPackage.eINSTANCE.getActivity_OwnedGroup().getName(), targetView);
		}
		return returnedElement;
	}


	/**
	 * Service to create a new {@link StructuredActivityNode} kind in an {@link Activity}, {@link ActivityPartition},
	 * {@link InterruptibleActivityRegion} or in {@link StructuredActivityNode} kind (including {@link ExpansionRegion}).
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param type
	 *            the type of element to create
	 * @param targetView
	 *            the view on which the tool is applied.
	 */
	public void createStructuredActivityNode(Element parent, String type, DSemanticDecorator targetView) {
		// StructuredActivityNode are owned by the StructuredNode feature of an Activity.
		this.createActivityNode(parent, type, targetView, UMLPackage.eINSTANCE.getActivity_StructuredNode());
	}

	/**
	 * Service to create a new {@link ActivityNode} kind in an {@link Activity}, {@link ActivityPartition},
	 * {@link InterruptibleActivityRegion} or in {@link StructuredActivityNode} kind (including {@link ExpansionRegion}).
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param type
	 *            the type of element to create
	 * @param targetView
	 *            the view on which the tool is applied.
	 */
	public void createActivityNode(Element parent, String type, DSemanticDecorator targetView) {
		this.createActivityNode(parent, type, targetView, UMLPackage.eINSTANCE.getActivity_OwnedNode());
	}

	private void createActivityNode(Element parent, String type, DSemanticDecorator targetView, EStructuralFeature parentActivityContainmentFeature) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		if (parent instanceof Activity) {
			commonDiagramServices.createElement(parent, type, parentActivityContainmentFeature.getName(), targetView);
		} else if (parent instanceof ActivityGroup && !(parent instanceof StructuredActivityNode)) {
			this.caseActivityNodeInActivityGroup(parent, type, targetView, commonDiagramServices, parentActivityContainmentFeature);
		} else if (parent instanceof StructuredActivityNode) {
			commonDiagramServices.createElement(parent, type, UMLPackage.eINSTANCE.getStructuredActivityNode_Node().getName(), targetView);
		}
	}


	/**
	 * Find parent Activity.
	 *
	 * @param editElement
	 *            ActivitiyPartition element
	 * @return <code>null</code> if Activity not found.
	 */
	private Activity findActivity(EObject editElement) {
		Activity container = null;
		if (editElement instanceof ActivityGroup) {
			ActivityGroup activityGroup = (ActivityGroup) editElement;
			if (activityGroup.eContainer() instanceof Activity) {
				container = (Activity) activityGroup.eContainer();
			} else {
				container = this.findActivity(activityGroup.eContainer());
			}
		}
		return container;
	}

	/**
	 * Service to create a new ActionInputPin.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created ActionInputPin.
	 */
	public EObject createActionInputPin(Element parent, DSemanticDecorator targetView) {
		return this.createPin(parent, UMLPackage.eINSTANCE.getActionInputPin().getName(), targetView);
	}

	/**
	 * Service to create a new InputPin.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created InputPin.
	 */
	public EObject createInputPin(Element parent, DSemanticDecorator targetView) {
		return this.createPin(parent, UMLPackage.eINSTANCE.getInputPin().getName(), targetView);
	}

	/**
	 * Service to create a new OutputPin.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created OutputPin.
	 */
	public EObject createOutputPin(Element parent, DSemanticDecorator targetView) {
		return this.createPin(parent, UMLPackage.eINSTANCE.getOutputPin().getName(), targetView);
	}

	/**
	 * Service to create a new ValuePin.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created ValuePin.
	 */
	public EObject createValuePin(Element parent, DSemanticDecorator targetView) {
		return this.createPin(parent, UMLPackage.eINSTANCE.getValuePin().getName(), targetView);
	}

	/**
	 * Create a Pin and add it to the right feature of the semantic parent.
	 *
	 * @param parent
	 *            the semantic parent element on which the tool is applied.
	 * @param typeToCreate
	 *            the name of the EClass Pin to create
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @return the new created pin.
	 */
	private EObject createPin(Element parent, String typeToCreate, DSemanticDecorator targetView) {
		EObject createdObject = null;
		EStructuralFeature feature = new ActivityFeatureProvider().getActivityFeature(parent, typeToCreate);
		if (feature != null) {
			createdObject = this.createAD(parent, typeToCreate, feature.getName(), targetView);
		}
		return createdObject;
	}

	/**
	 * Provides the label to display on the note attached to a {@link DecisionNode} if the {@link DecisionNode#getDecisionInput()} feature is set.
	 *
	 * @param decisionNode
	 *            the {@link DecisionNode} from which retrieving the note label.
	 * @return the label or empty string if it cannot be computed.
	 */
	public String getDecisionInputNoteLabel(DecisionNode decisionNode) {
		return Optional.ofNullable(decisionNode)
				.map(DecisionNode::getDecisionInput)
				.map(this::computeDecisionInputLabel)
				.orElse(UMLCharacters.EMPTY);
	}

	private String computeDecisionInputLabel(Behavior behavior) {
		return UMLCharacters.ST_LEFT + DECISION_INPUT + UMLCharacters.ST_RIGHT + UMLCharacters.SPACE + behavior.getName();
	}

	/**
	 * Checks whether the given {@link DecisionNode} is represented in the given container view. Used to know if we should display the DecisionNode attached note (this mapping is always synchronized).
	 *
	 * @param decisionNode
	 *            the {@link DecisionNode}.
	 * @param containerView
	 *            the container view.
	 * @return true if the {@link DecisionNode} is represented, false otherwise.
	 */
	public boolean isDecisionNodeMappingVisible(DecisionNode decisionNode, DSemanticDecorator containerView) {
		return Optional.ofNullable(containerView)
				.filter(DNodeContainer.class::isInstance)
				.map(DNodeContainer.class::cast)
				.map(DNodeContainer::getNodes)
				.stream()
				.flatMap(List::stream)
				.filter(dNode -> this.isRepresentingDecisionNode(dNode, decisionNode)) // we filter the node to keep only decisionNodes with the given decisionNode as semantic target.
				.findFirst()
				.isPresent(); // If we found at least one node, it's true. False otherwise.
	}

	private boolean isRepresentingDecisionNode(DNode dNode, DecisionNode decisionNode) {
		return decisionNode.equals(dNode.getTarget()) && AD_DECISION_NODE.equals(dNode.getActualMapping().getName());
	}

	/**
	 * Creates a new {@link ExpansionNode} in the given parent {@link ExpansionRegion}.
	 *
	 * @param parentExpansionRegion
	 *            the parent {@link ExpansionRegion}.
	 * @param targetView
	 *            the view on which the tool is applied.
	 * @param isInput
	 *            true if the created {@link ExpansionNode} should be added as input, false as output.
	 */
	public void createExpansionNode(ExpansionRegion parentExpansionRegion, DSemanticDecorator targetView, boolean isInput) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		EObject createdElement = commonDiagramServices.createElement(parentExpansionRegion, "uml::ExpansionNode", UMLPackage.eINSTANCE.getStructuredActivityNode_Node().getName(), targetView); //$NON-NLS-1$
		if (createdElement instanceof ExpansionNode) {
			EStructuralFeature expansionNodeFeature;
			EStructuralFeature expansionRegionFeature;
			if (isInput) {
				expansionNodeFeature = UMLPackage.eINSTANCE.getExpansionNode_RegionAsInput();
				expansionRegionFeature = UMLPackage.eINSTANCE.getExpansionRegion_InputElement();
			} else {
				expansionNodeFeature = UMLPackage.eINSTANCE.getExpansionNode_RegionAsOutput();
				expansionRegionFeature = UMLPackage.eINSTANCE.getExpansionRegion_OutputElement();
			}
			ElementFeatureModifier featureModifier = new ElementFeatureModifier(this.getECrossReferenceAdapter(parentExpansionRegion), new EditableChecker());
			featureModifier.addValue(createdElement, expansionNodeFeature.getName(), parentExpansionRegion);
			featureModifier.addValue(parentExpansionRegion, expansionRegionFeature.getName(), createdElement);
		}
	}

	/**
	 * Service used to check if an object can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateIntoParent(EObject container, EClass objectToCreate) {
		EStructuralFeature feature = new ActivityFeatureProvider().getActivityFeature(container, objectToCreate.getName());
		return super.canCreate(container, objectToCreate, feature);
	}

	/**
	 * Service used to check if an object can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @param containmentReferenceName
	 *            the name of the containment reference to use to attach the new element to the model
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateAD(EObject container, EClass objectToCreate, String containmentReferenceName) {
		return super.canCreate(container, objectToCreate, containmentReferenceName);
	}

	/**
	 * Service used to check if an ActivityPartition can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateActivityPartition(EObject container) {
		boolean canCreate = false;
		if (container instanceof Activity) {
			canCreate = super.canCreate(container, UMLPackage.eINSTANCE.getActivityPartition(), UMLPackage.eINSTANCE.getActivity_OwnedGroup());
		} else if (container instanceof ActivityPartition) {
			canCreate = super.canCreate(container, UMLPackage.eINSTANCE.getActivityPartition(), UMLPackage.eINSTANCE.getActivityPartition_Subpartition());
		}
		return canCreate;
	}

	/**
	 * Service used to check if an {@link ActivityNode} can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateActivityNode(EObject container, EClass objectToCreate) {
		return this.canCreateActivityNode(container, objectToCreate, UMLPackage.eINSTANCE.getActivity_OwnedNode());
	}

	/**
	 * Service used to check if an {@link StructuredActivityNode} can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateStructuredActivityNode(EObject container, EClass objectToCreate) {
		return this.canCreateActivityNode(container, objectToCreate, UMLPackage.eINSTANCE.getActivity_StructuredNode());
	}

	private boolean canCreateActivityNode(EObject container, EClass objectToCreate, EStructuralFeature activityContainmentFeature) {
		boolean canCreate = false;
		if (container instanceof Activity) {
			canCreate = super.canCreate(container, objectToCreate, activityContainmentFeature);
		} else if (container instanceof ActivityGroup && !(container instanceof StructuredActivityNode)) {
			canCreate = this.canCreateActivityNodeInActivityGroup(container, objectToCreate, activityContainmentFeature);
		} else if (container instanceof StructuredActivityNode) {
			canCreate = super.canCreate(container, objectToCreate, UMLPackage.eINSTANCE.getStructuredActivityNode_Node());
		}
		return canCreate;
	}

	/**
	 * Check if an ActivityNode can be created under the specified ActivityPartition or InterruptibleActivityRegion.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @param activityContainmentFeature
	 *            the containment feature from Activity that is supposed to own the element to create.
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	private boolean canCreateActivityNodeInActivityGroup(EObject container, EClass objectToCreate, EStructuralFeature activityContainmentFeature) {
		boolean canCreate = false;
		Activity parentActivity = this.findActivity(container);
		if (parentActivity != null) {
			canCreate = super.canCreate(parentActivity, objectToCreate, activityContainmentFeature);
		}
		return canCreate;
	}

	/**
	 * Service used to check if an InterruptibleActivityRegion can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateInterruptibleActivityRegion(EObject container) {
		boolean canCreate = false;
		if (container instanceof Activity) {
			canCreate = super.canCreate(container, UMLPackage.eINSTANCE.getInterruptibleActivityRegion(), UMLPackage.eINSTANCE.getActivity_OwnedGroup());
		}
		return canCreate;
	}

	/**
	 * Service used to check if an ExpansionNode can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateExpansionNode(EObject container) {
		boolean canCreate = false;
		if (container instanceof ExpansionRegion) {
			canCreate = super.canCreate(container, UMLPackage.eINSTANCE.getExpansionNode(), UMLPackage.eINSTANCE.getStructuredActivityNode_Node());
		}
		return canCreate;
	}

	/**
	 * Provides all {@link ActivityEdge} contained in the hierarchy (that also means all sub-elements) of the given {@link Activity}
	 *
	 * @param semanticContext
	 *            the root semantic context. Probably an {@link Activity}.
	 * @return the list of found {@link ActivityEdge}.
	 */
	public Collection<ActivityEdge> getActivityEdgesCandidates(EObject semanticContext) {
		Set<ActivityEdge> activityEdges = new HashSet<>();
		if (semanticContext instanceof Activity) {
			Activity activity = (Activity) semanticContext;
			activityEdges.addAll(activity.getEdges());
			activity.allOwnedElements().stream()
					.forEach(ownedElement -> {
						activityEdges.addAll(this.getActivityEdgesInElement(ownedElement));
					});
		}
		return activityEdges;
	}

	private Collection<ActivityEdge> getActivityEdgesInElement(Element element) {
		Collection<ActivityEdge> activityEdges = new ArrayList<>();
		if (element instanceof Activity) {
			activityEdges.addAll(((Activity) element).getEdges());
			this.addActivityEdgesFromSubElement(element, activityEdges);
		} else if (element instanceof StructuredActivityNode) {
			activityEdges.addAll(((StructuredActivityNode) element).getEdges());
			this.addActivityEdgesFromSubElement(element, activityEdges);
		}
		return activityEdges;
	}

	private void addActivityEdgesFromSubElement(Element element, Collection<ActivityEdge> activityEdges) {
		element.allOwnedElements().stream()
				.forEach(ownedElement -> {
					activityEdges.addAll(this.getActivityEdgesInElement(ownedElement));
				});
	}

	/**
	 * Service used to create an {@link ControlFlow} edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param sourceView
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetView
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new {@link ControlFlow} or <code>null</code>
	 */
	public EObject createControlFlow(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new ActivityDomainBasedEdgeServices();
		// According to org.eclipse.papyrus.uml.domain.services.activityEdge.ActivityEdgeHelper.deduceContainer(EObject, EObject),
		// two kinds of container are possible: Activity and StructuredActivityNode. The feature name in both type is "edge".
		return domainBasedEdgeServices.createDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getControlFlow().getName(), ACTIVITY_EDGE_CONTAINMENT_FEATURE_NAME, sourceView, targetView);
	}



	/**
	 * Service used to create an {@link ObjectFlow} edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param sourceView
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetView
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new {@link ObjectFlow} or <code>null</code>
	 */
	public EObject createObjectFlow(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new ActivityDomainBasedEdgeServices();
		// According to org.eclipse.papyrus.uml.domain.services.activityEdge.ActivityEdgeHelper.deduceContainer(EObject, EObject),
		// two kinds of container are possible: Activity and StructuredActivityNode. The feature name in both type is "edge".
		return domainBasedEdgeServices.createDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getObjectFlow().getName(), ACTIVITY_EDGE_CONTAINMENT_FEATURE_NAME, sourceView, targetView);
	}

	/**
	 * Reconnects the source of the provided {@code semanticElementEdge} to {@code newSource}.
	 * <p>
	 * This method may create new elements before the reconnection. For example, it creates a new {@link OutputPin} if
	 * {@code newSource} is an {@link OpaqueAction}.
	 * </p>
	 *
	 * @param semanticElementEdge
	 *            the semantic element representing the edge to reconnect
	 * @param oldSource
	 *            the semantic element previously used as source of the edge
	 * @param newSource
	 *            the semantic element used as the new source of the edge
	 * @param newSourceView
	 *            the graphical element representing the new source of the edge
	 */
	public void reconnectSourceAD(EObject semanticElementEdge, Element oldSource, Element newSource, DSemanticDecorator newSourceView) {
		ReconnectServices reconnectServices = new ReconnectServices();
		EObject newSourceToReconnectTo = newSource;
		DSemanticDecorator newSourceViewToReconnectTo = newSourceView;
		if (UMLPackage.eINSTANCE.getObjectFlow().isInstance(semanticElementEdge)
				&& UMLPackage.eINSTANCE.getOpaqueAction().isInstance(newSource)) {
			// The new source of the ObjectFlow is an OpaqueAction, we need to create a new OutputPin on it and
			// reconnect to the pin instead of the action.
			newSourceToReconnectTo = this.createOutputPin(newSource, newSourceView);
			((ObjectFlow) semanticElementEdge).setSource((Pin) newSourceToReconnectTo);
			// Explicitly create the view for the pin: the default view creation mechanism needs to access the
			// containerView variable but it doesn't exist in reconnect tools.
			DSemanticDecorator createdView = this.createAdditionalSourceView(semanticElementEdge, newSourceViewToReconnectTo);
			if (createdView != null) {
				// Set the created view as the new source to use for the reconnection.
				newSourceViewToReconnectTo = createdView;
			}
			reconnectServices.reconnectSource(semanticElementEdge, oldSource, newSourceToReconnectTo, newSourceViewToReconnectTo);
			Session session = SessionManager.INSTANCE.getSession(newSourceToReconnectTo);
			// Sirius doesn't manage the reconnection well because the new source has changed during the execution of the
			// tool. As a result, the reconnected edge is not visible by the end user. We have to manually re-create the
			// edge to make it visible. Note that dangling edges are removed from diagram on the next refresh.
			this.createEdgeView(semanticElementEdge, (DNode) newSourceViewToReconnectTo, session);
		} else {
			reconnectServices.reconnectSource(semanticElementEdge, oldSource, newSourceToReconnectTo, newSourceViewToReconnectTo);
		}
	}

	/**
	 * Reconnects the target of the provided {@code semanticElementEdge} to {@code newTarget}.
	 * <p>
	 * This method may create new elements after the reconnection. For example, it creates a new {@link InputPin} if
	 * {@code newTarget} is an {@link OpaqueAction}.
	 * </p>
	 *
	 * @param semanticElementEdge
	 *            the semantic element representing the edge to reconnect
	 * @param oldTarget
	 *            the semantic element previously used as target of the edge
	 * @param newTarget
	 *            the semantic element used as the new target of the edge
	 * @param newTargetView
	 *            the graphical element representing the new target of the edge
	 */
	public void reconnectTargetAD(EObject semanticElementEdge, Element oldTarget, Element newTarget, DSemanticDecorator newTargetView) {
		ReconnectServices reconnectServices = new ReconnectServices();
		EObject newTargetToReconnectTo = newTarget;
		DSemanticDecorator newTargetViewToReconnectTo = newTargetView;
		if (UMLPackage.eINSTANCE.getObjectFlow().isInstance(semanticElementEdge)
				&& UMLPackage.eINSTANCE.getOpaqueAction().isInstance(newTarget)) {
			// The new target of the ObjectFlow is an OpaqueAction, we need to create a new OutputPin on it and
			// reconnect to the pin instead of the action.
			newTargetToReconnectTo = this.createInputPin(newTarget, newTargetView);
			((ObjectFlow) semanticElementEdge).setTarget((Pin) newTargetToReconnectTo);
			// Explicitly create the view for the pin: the default view creation mechanism needs to access the
			// containerView variable but it doesn't exist in reconnect tools.
			DSemanticDecorator createdView = this.createAdditionalTargetView(semanticElementEdge, newTargetViewToReconnectTo);
			if (createdView != null) {
				// Set the created view as the new target to use for the reconnection.
				newTargetViewToReconnectTo = createdView;
			}
			reconnectServices.reconnectTarget(semanticElementEdge, oldTarget, newTargetToReconnectTo, newTargetViewToReconnectTo);
			Session session = SessionManager.INSTANCE.getSession(newTargetToReconnectTo);
			// Sirius doesn't manage the reconnection well because the new target has changed during the execution of the
			// tool. As a result, the reconnected edge is not visible by the end user. We have to manually re-create the
			// edge to make it visible. Note that dangling edges are removed from diagram on the next refresh.
			this.createEdgeView(semanticElementEdge, (DNode) newTargetViewToReconnectTo, session);
		} else {
			reconnectServices.reconnectTarget(semanticElementEdge, oldTarget, newTargetToReconnectTo, newTargetViewToReconnectTo);
		}
	}

	/**
	 * Creates additional views for the source element of the provided {@code semanticEdge} if necessary.
	 * <p>
	 * This method checks that the source of {@code semanticEdge} matches the provided {@code sourceView},
	 * and creates the required views if it is not the case.
	 *
	 * @param semanticEdge
	 *            the semantic element representing the edge to create additional source views from
	 * @param sourceView
	 *            the graphical element representing the source element of the edge
	 * @return the created elements, if any, or {@code null}
	 */
	private DSemanticDecorator createAdditionalSourceView(EObject semanticEdge, DSemanticDecorator sourceView) {
		DSemanticDecorator result = null;
		if (semanticEdge instanceof ObjectFlow objectFlow) {
			ActivityNode source = objectFlow.getSource();
			Session session = SessionManager.INSTANCE.getSession(semanticEdge);
			IInterpreter interpreter = session.getInterpreter();
			if (interpreter != null) {
				if (source != sourceView.getTarget()) {
					interpreter.setVariable(CONTAINER_VIEW, sourceView);
					try {
						this.createView(source, sourceView, session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					} finally {
						interpreter.unSetVariable(CONTAINER_VIEW);
					}
					Object createdView = interpreter.getVariable("createdView"); //$NON-NLS-1$
					if (createdView instanceof DSemanticDecorator dSemanticDecorator) {
						result = dSemanticDecorator;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Creates additional views for the target element of the provided {@code semanticEdge} if necessary.
	 * <p>
	 * This method checks that the target of {@code semanticEdge} matches the provided {@code targetView},
	 * and creates the required views if it is not the case.
	 *
	 * @param semanticEdge
	 *            the semantic element representing the edge to create additional target views from
	 * @param targetView
	 *            the graphical element representing the target element of the edge
	 * @return the created elements, if any, or {@code null}
	 */
	private DSemanticDecorator createAdditionalTargetView(EObject semanticEdge, DSemanticDecorator targetView) {
		DSemanticDecorator result = null;
		if (semanticEdge instanceof ObjectFlow objectFlow) {
			ActivityNode target = objectFlow.getTarget();
			Session session = SessionManager.INSTANCE.getSession(semanticEdge);
			IInterpreter interpreter = session.getInterpreter();
			if (interpreter != null) {
				if (target != targetView.getTarget()) {
					interpreter.setVariable(CONTAINER_VIEW, targetView);
					try {
						this.createView(target, targetView, session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					} finally {
						interpreter.unSetVariable(CONTAINER_VIEW);
					}
					Object createdView = interpreter.getVariable("createdView"); //$NON-NLS-1$
					if (createdView instanceof DSemanticDecorator dSemanticDecorator) {
						result = dSemanticDecorator;
					}
				}
			}
		}
		return result;
	}
}
