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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.reconnection;

import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;

/**
 * Abstract class to test reconnection of the target for ActivityEdges in Activity diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class AbstractEdgeReconnectionTargetActivityEdgeTest extends AbstractReconnectTargetEdgeTests {

	protected static final String AD_PREFIX = "AD_"; //$NON-NLS-1$

	protected static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	protected static final String NEW_END = "NewEnd"; //$NON-NLS-1$

	protected static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	protected static final String CALL_BEHAVIOR_ACTION = "CallBehaviorAction"; //$NON-NLS-1$

	protected static final String EXPANSION_REGION = "ExpansionRegion"; //$NON-NLS-1$

	protected final String newTargetName;

	protected final String mappingNewTargetTypeName;

	protected Activity rootActivity;

	/**
	 * Constructor.
	 *
	 * @param newTargetName
	 *            the name of the element type used to calculate the new target.
	 */
	public AbstractEdgeReconnectionTargetActivityEdgeTest(String newTargetName) {
		this.newTargetName = newTargetName + NEW_END;
		this.mappingNewTargetTypeName = AD_PREFIX + newTargetName;
	}

	/**
	 * Initialize the test by setting all required fields.
	 *
	 * @param sourceName
	 *            the name of the source element before executing the test
	 * @param targetName
	 *            the name of the target element before executing the test
	 * @param mappingSourceTypeName
	 *            the mapping of the source element before executing the test
	 * @param mappingTargetTypeName
	 *            the mapping of the target element before executing the test
	 */
	protected void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		DDiagramElement activityGraphicalContainer = this.getActivityGraphicalContainer();
		// initialize semantic context for test
		this.setSemanticSource(this.getElementByNameFromRoot(sourceName).get());
		this.setSemanticTarget(this.getElementByNameFromRoot(targetName).get());
		// initialize graphical context for test
		this.setEdgeSource(this.getNodeFromContainer(sourceName, mappingSourceTypeName, activityGraphicalContainer));
		this.setEdgeTarget(this.getNodeFromContainer(targetName, mappingTargetTypeName, activityGraphicalContainer));
		if (this.isBorderNode()) {
			this.setBorderNodeNewTarget(activityGraphicalContainer);
		} else {
			this.setSemanticNewTarget(this.getElementByNameFromRoot(this.newTargetName).get());
			this.setEdgeNewTarget(this.getNodeFromContainer(this.newTargetName, this.mappingNewTargetTypeName, activityGraphicalContainer));
		}
	}

	/**
	 * This method is used to set the new semantic target and the new graphical target of the edge when the new target is a border node.
	 *
	 * @param activityGraphicalContainer
	 *            the root activity DDiagramElement
	 */
	private void setBorderNodeNewTarget(DDiagramElement activityGraphicalContainer) {
		NamedElement borderNodeContainer = null;
		AbstractDNode graphicalBorderNodeContainer = null;
		switch (this.mappingNewTargetTypeName) {
		case MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(ACTIVITY + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(ACTIVITY + NEW_END, MappingTypes.SUB_ACTIVITY_NODE_TYPE, activityGraphicalContainer);
			break;
		case MappingTypes.EXPANSION_NODE_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(EXPANSION_REGION + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(EXPANSION_REGION + NEW_END, MappingTypes.EXPANSION_REGION_NODE_TYPE, activityGraphicalContainer);
			break;
		case MappingTypes.INPUT_PIN_NODE_TYPE:
		case MappingTypes.OUTPUT_PIN_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(CALL_BEHAVIOR_ACTION + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(CALL_BEHAVIOR_ACTION + NEW_END, MappingTypes.CALL_BEHAVIOR_ACTION_NODE_TYPE, activityGraphicalContainer);
			break;
		default:
			break;
		}
		Assert.assertNotNull("We can't find the semantic container of the border node", borderNodeContainer); //$NON-NLS-1$
		Assert.assertNotNull("We can't find the graphical container of the border node", graphicalBorderNodeContainer); //$NON-NLS-1$
		this.setSemanticNewTarget(this.getElementByName(borderNodeContainer, this.newTargetName).get());
		this.setEdgeNewTarget(this.getNodeFromContainer(this.newTargetName, this.mappingNewTargetTypeName, graphicalBorderNodeContainer));
	}

	/**
	 * Check if the mapping of the new target is a border node.
	 *
	 * @return <code>true</code> if the mapping is a BorderNode, <code>false</code> otherwise.
	 */
	private boolean isBorderNode() {
		return MappingTypes.isBorderNode(this.mappingNewTargetTypeName);
	}

	/**
	 * Get the {@link Activity} graphical container which is the root of the diagram.
	 *
	 * @return the {@link Activity} graphical container.
	 */
	private DDiagramElement getActivityGraphicalContainer() {
		DNodeContainer container = (DNodeContainer) this.getNodeFromDiagram(ROOT_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE);
		final DDiagramElement compartment = container.getOwnedDiagramElements().get(0);
		return compartment;
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}
}
