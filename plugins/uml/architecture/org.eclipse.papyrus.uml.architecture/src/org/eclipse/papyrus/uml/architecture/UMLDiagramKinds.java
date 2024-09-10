/*****************************************************************************
 * Copyright (c) 2018 Maged Elaasar, CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 Maged Elaasar - Initial API and Implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.architecture;

/**
 * The kinds of UML diagrams in the UML Architecture Language
 * 
 */
public interface UMLDiagramKinds {

	public static final String ACTIVITY_DIAGRAM ="org.eclipse.papyrus.uml.diagram.activity";
	
	public static final String CLASS_DIAGRAM ="org.eclipse.papyrus.uml.diagram.class";
	
	public static final String INNER_CLASS_DIAGRAM ="org.eclipse.papyrus.uml.diagram.innerClass";

	public static final String PACKAGE_DIAGRAM ="org.eclipse.papyrus.uml.diagram.package";

	public static final String COMMUNICATION_DIAGRAM ="org.eclipse.papyrus.uml.diagram.communication";
	
	public static final String COMPONENT_DIAGRAM_IN_COMPONENT ="org.eclipse.papyrus.uml.diagram.component.root.component";
	
	public static final String COMPONENT_DIAGRAM_IN_PACKAGE ="org.eclipse.papyrus.uml.diagram.component.root.package";

	public static final String COMPOSITE_STRUCTURE_DIAGRAM_IN_STRUCTURED_CLASSIFIER ="org.eclipse.papyrus.uml.diagram.compositeStructure.root.structuredClassifier";
	
	public static final String COMPOSITE_STRUCTURE_DIAGRAM_IN_PACKAGE ="org.eclipse.papyrus.uml.diagram.compositeStructure.root.package";

	public static final String DEPLOYMENT_DIAGRAM ="org.eclipse.papyrus.uml.diagram.deployment";
	
	public static final String INTERACTION_OVERVIEW_DIAGRAM ="org.eclipse.papyrus.uml.diagram.interactionOverview";
	
	public static final String PROFILE_DIAGRAM ="org.eclipse.papyrus.uml.diagram.profile";
	
	public static final String SEQUENCE_DIAGRAM ="org.eclipse.papyrus.uml.diagram.sequence";
	
	public static final String STATE_MACHINE_DIAGRAM ="org.eclipse.papyrus.uml.diagram.stateMachine";
	
	public static final String TIMING_DIAGRAM ="org.eclipse.papyrus.uml.diagram.timing";
	
	public static final String USE_CASE_DIAGRAM ="org.eclipse.papyrus.uml.diagram.useCase";

}
