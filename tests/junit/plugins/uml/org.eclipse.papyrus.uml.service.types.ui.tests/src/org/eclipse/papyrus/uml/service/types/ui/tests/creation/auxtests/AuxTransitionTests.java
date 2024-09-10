/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.ui.tests.creation.auxtests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

import static org.junit.Assert.*;


import org.junit.Assert;

public class AuxTransitionTests implements IAuxTest {

	/**
	 * @see org.eclipse.papyrus.uml.service.types.tests.creation.auxtests.AuxTest#test(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.emf.type.core.IHintedType, java.util.Collection)
	 *
	 * @param container
	 * @param source
	 * @param target
	 * @param hintedType
	 * @param commandResult
	 */
	@Override
	public void test(EObject container, EObject source, EObject target, IHintedType hintedType, Collection<?> commandResult) {
		Transition transition = (Transition) ((ArrayList<?>)commandResult).get(0);		
		Assert.assertNotNull("Transition is null",transition);
		Assert.assertNotNull("Transition source is null",transition.getSource());
		Assert.assertNotNull("Transition target is null",transition.getTarget());
		
		Assert.assertEquals(String.format("Transition was set to {0} but should have been {1}",transition.getSource().getName(),((Vertex)source).getName()),transition.getSource(),source);
		Assert.assertEquals(String.format("Transition was set to {0} but should have been {1}",transition.getTarget().getName(),((Vertex)target).getName()),transition.getTarget(),target);
		
		if (source instanceof Pseudostate) {
			Pseudostate initial = (Pseudostate)source;
			if (initial.getKind().equals(PseudostateKind.INITIAL_LITERAL)) {
				int size = initial.getOutgoings().size();
				assertTrue("An initial node cannot have more than one outgoing transition",size < 2);
			}
		}	
		
		if (target instanceof Pseudostate) {
			Pseudostate initial = (Pseudostate)target;
			if (initial.getKind().equals(PseudostateKind.INITIAL_LITERAL)) {
				int size = initial.getIncomings().size();
				Assert.assertEquals("An initial state can not any incoming transition", size,0);

			}
		}				

		
	}

}
