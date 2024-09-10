/**
 * Copyright (c) 2015 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Benoit Maggi benoit.maggi@cea.fr - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.architecture.representation.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl;


public class ModelAutoCreateCustomImpl extends ModelAutoCreateImpl implements ModelAutoCreate {
	
	
	/**
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl#basicGetOrigin()
	 *
	 * @return
	 */
	@Override
	public EClass basicGetOrigin() {
		OwningRule rule = (OwningRule) this.eContainer();
		EList<ModelAutoCreate> list = rule.getNewModelPath();
		int index = list.indexOf(this);
		if (index == 0) {
			return rule.getElement();
		}
			
		return null; // FIXME used to be : list.get(index - 1).getCreationType();
	}
	
	
}
