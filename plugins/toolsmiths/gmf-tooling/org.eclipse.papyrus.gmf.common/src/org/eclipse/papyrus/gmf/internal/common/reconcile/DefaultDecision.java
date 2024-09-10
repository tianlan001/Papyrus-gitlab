/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/

package org.eclipse.papyrus.gmf.internal.common.reconcile;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class DefaultDecision extends Decision {
	
	private boolean myInverted;

	public DefaultDecision(EStructuralFeature feature){
		this(feature, false);
	}
	
	public DefaultDecision(EStructuralFeature feature, boolean inverted){
		super(feature);
		myInverted = inverted;
	}
	
	public void apply(EObject current, EObject old) {
		assert current.eClass().equals(old.eClass());
		if (!(myInverted ^ isValueSet(current)) && (myInverted ^ isValueSet(old))) {
			preserveOld(current, old);
		} else {
			acceptNew(current, old);
		}
	}
}
