/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.statemachine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.extension.commands.PerspectiveContextDependence;

/**
 * The StateMachineDiagramCreationCondition class allows to check if a State Machine
 * diagram can be added to the selected element.
 * CAVEAT: since the state machine diagram uses viewpoints, the returned value is ignored. Instead, the check
 * whether a diagram can be added is done in
 * org.eclipse.papyrus.infra.viewpoints.policy, file builtin/default.configuration
 */
public class StateMachineDiagramCreationCondition extends PerspectiveContextDependence {

	/**
	 * @return whether the diagram can be created.
	 */
	@Override
	public boolean create(EObject selectedElement) {
		return false;
	}
}
