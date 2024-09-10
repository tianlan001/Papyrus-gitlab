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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.dnd.strategy.state;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Concrete implementation to edit a State's exit feature
 *
 * @see {@link AbstractBehaviorToStateDropStrategy}
 *
 * @author Camille Letavernier
 */
public class BehaviorToExitDropStrategy extends AbstractBehaviorToStateDropStrategy {

	@Override
	protected EStructuralFeature getFeatureToEdit() {
		return UMLPackage.eINSTANCE.getState_Exit();
	}

}
