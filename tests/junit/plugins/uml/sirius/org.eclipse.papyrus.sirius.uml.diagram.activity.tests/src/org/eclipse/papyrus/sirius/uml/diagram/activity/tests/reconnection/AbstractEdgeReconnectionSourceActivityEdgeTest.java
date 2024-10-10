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

import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectSourceEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;

/**
 * Abstract class to test reconnection of the source for ActivityEdges in Activity diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class AbstractEdgeReconnectionSourceActivityEdgeTest extends AbstractReconnectSourceEdgeTests {

	protected static final String AD_PREFIX = "AD_"; //$NON-NLS-1$

	protected static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	protected static final String NEW_END = "NewEnd"; //$NON-NLS-1$

	protected static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	protected static final String CALL_BEHAVIOR_ACTION = "CallBehaviorAction"; //$NON-NLS-1$

	protected static final String EXPANSION_REGION = "ExpansionRegion"; //$NON-NLS-1$

	protected String newSourceName;

	protected String mappingNewSourceTypeName;

	protected Activity rootActivity;

	/**
	 * Constructor.
	 *
	 * @param newSourceName
	 *            the name of the element type used to calculate the new source.
	 */
	public AbstractEdgeReconnectionSourceActivityEdgeTest(String newSourceName) {
		this.newSourceName = newSourceName + NEW_END;
		this.mappingNewSourceTypeName = AD_PREFIX + newSourceName;
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
			this.setBorderNodeNewSource(activityGraphicalContainer);
		} else {
			this.setSemanticNewSource(this.getElementByNameFromRoot(this.newSourceName).get());
			this.setEdgeNewSource(this.getNodeFromContainer(this.newSourceName, this.mappingNewSourceTypeName, activityGraphicalContainer));
		}
	}

	/**
	 * This method is used to set the new semantic source and the new graphical source of the edge when the new source is a border node.
	 *
	 * @param activityGraphicalContainer
	 *            the root activity DDiagramElement
	 */
	private void setBorderNodeNewSource(DDiagramElement activityGraphicalContainer) {
		NamedElement borderNodeContainer = null;
		AbstractDNode graphicalBorderNodeContainer = null;
		switch (this.mappingNewSourceTypeName) {
		case MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(ACTIVITY + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(ACTIVITY + NEW_END, MappingTypes.SUB_ACTIVITY_NODE_TYPE, activityGraphicalContainer);
			break;
		case MappingTypes.EXPANSION_NODE_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(EXPANSION_REGION + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(EXPANSION_REGION + NEW_END, MappingTypes.EXPANSION_REGION_NODE_TYPE, activityGraphicalContainer);
			break;
		case MappingTypes.OUTPUT_PIN_NODE_TYPE:
		case MappingTypes.INPUT_PIN_NODE_TYPE:
			borderNodeContainer = this.getElementByNameFromRoot(CALL_BEHAVIOR_ACTION + NEW_END).get();
			graphicalBorderNodeContainer = this.getNodeFromContainer(CALL_BEHAVIOR_ACTION + NEW_END, MappingTypes.CALL_BEHAVIOR_ACTION_NODE_TYPE, activityGraphicalContainer);
			break;
		default:
			break;
		}
		Assert.assertNotNull("We can't find the semantic container of the border node", borderNodeContainer); //$NON-NLS-1$
		Assert.assertNotNull("We can't find the graphical container of the border node", graphicalBorderNodeContainer); //$NON-NLS-1$
		this.setSemanticNewSource(this.getElementByName(borderNodeContainer, this.newSourceName).get());
		this.setEdgeNewSource(this.getNodeFromContainer(this.newSourceName, this.mappingNewSourceTypeName, graphicalBorderNodeContainer));
	}

	/**
	 * Check if the mapping of the new source is a border node.
	 *
	 * @return <code>true</code> if the mapping is a BorderNode, <code>false</code> otherwise.
	 */
	private boolean isBorderNode() {
		return MappingTypes.isBorderNode(this.mappingNewSourceTypeName);
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
