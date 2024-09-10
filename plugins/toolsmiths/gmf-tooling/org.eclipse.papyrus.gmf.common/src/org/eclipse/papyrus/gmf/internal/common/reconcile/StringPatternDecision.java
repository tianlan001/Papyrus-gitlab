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

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * XXX [artem] actually, either StringPatternDecision should be abstract, or renamed to
 * OldValueNotByPatternDecision to better reflect its implementation
 */
public class StringPatternDecision extends Decision {
	protected final Pattern myPattern;
	
	public StringPatternDecision(String valuePattern, EAttribute attribute){
		this(Pattern.compile(valuePattern), attribute);
	}
	
	public StringPatternDecision(Pattern valuePattern, EAttribute attribute){
		super(attribute);
		if (attribute.getEAttributeType() != EcorePackage.eINSTANCE.getEString()){
			throw new IllegalArgumentException("Expected string attribute");
		}
		if (attribute.getUpperBound() != 1){
			throw new IllegalArgumentException("Expected multiplicity [0..1] or [1]");
		}
		myPattern = valuePattern;
	}
	
	public void apply(EObject current, EObject old) {
		String oldValue = (String)old.eGet(getFeature());
		if (oldValue != null && !myPattern.matcher(oldValue).matches()){
			preserveOld(current, old);
		} else {
			acceptNew(current, old);
		}
	}

}
