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

public abstract class Decision {
	private final EStructuralFeature myFeature;

	public abstract void apply(EObject current, EObject old);
	
	public Decision(EStructuralFeature feature){
		myFeature = feature;
	}
	
	protected final EStructuralFeature getFeature(){
		return myFeature;
	}
	
	public static class ALWAYS_OLD extends Decision {
		public ALWAYS_OLD(EStructuralFeature feature){
			super(feature);
		}
		
		public void apply(EObject current, EObject old) {
			preserveOld(current, old);
		}
	}
	
	public static class ALWAYS_NEW extends Decision {
		public ALWAYS_NEW(EStructuralFeature feature){
			super(feature);
		}
		
		public void apply(EObject current, EObject old) {
			acceptNew(current, old);
		}
	}

	protected void acceptNew(EObject current, EObject old) {
		// do nothing
	}

	protected void preserveOld(EObject current, EObject old) {
		Object oldValue = old.eGet(getFeature(), true);
		current.eSet(getFeature(), oldValue);
	}

	protected final boolean isValueSet(EObject obj) {
		return obj.eIsSet(getFeature());
	}
}
