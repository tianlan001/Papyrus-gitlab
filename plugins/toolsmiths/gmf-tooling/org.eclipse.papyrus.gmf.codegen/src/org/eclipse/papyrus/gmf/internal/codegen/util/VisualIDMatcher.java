/******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - match referenced elements
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.internal.common.reconcile.Matcher;

/**
 * @author dstadnik
 */
public class VisualIDMatcher implements Matcher {
	private final EReference myCommonBaseReference; 

	public VisualIDMatcher() {
		myCommonBaseReference = null;
	}

	/**
	 * Matches not the elements passed by reconciler, but values they reference with the supplied feature.
	 * Feature's type should be subclass of GenCommonBase. If feature is multiple, element ordering is ignored.
	 */
	public VisualIDMatcher(EReference commonBaseReference) {
		assert commonBaseReference != null; // use another cons instead
		assert GMFGenPackage.eINSTANCE.getGenCommonBase().isSuperTypeOf(commonBaseReference.getEReferenceType());
		myCommonBaseReference = commonBaseReference;
	}

	public boolean match(EObject current, EObject old) {
		if (myCommonBaseReference == null) {
			if (current instanceof GenCommonBase && old instanceof GenCommonBase) {
				int oldID = ((GenCommonBase) old).getVisualID();
				int newID = ((GenCommonBase) current).getVisualID();
				return oldID == newID;
			}
			return false;
		}
		assert myCommonBaseReference.getEContainingClass().isInstance(current);
		if (myCommonBaseReference.isMany()) {
			@SuppressWarnings("unchecked")
			List<GenCommonBase> lcb_old = (List<GenCommonBase>) old.eGet(myCommonBaseReference);
			@SuppressWarnings("unchecked")
			List<GenCommonBase> lcb_new = (List<GenCommonBase>) current.eGet(myCommonBaseReference);
			if (lcb_new.size() != lcb_old.size()) {
				return false;
			}
			HashSet<Integer> oldVisualIDs = new HashSet<Integer>();
			for (GenCommonBase cb : lcb_old) {
				oldVisualIDs.add(cb.getVisualID());
			}
			for (GenCommonBase cb : lcb_new) {
				if (!oldVisualIDs.remove(cb.getVisualID())) {
					return false;
				}
			}
			assert oldVisualIDs.isEmpty(); // sanity
			return true;
		} else {
			GenCommonBase oldCB = (GenCommonBase) old.eGet(myCommonBaseReference);
			GenCommonBase newCB = (GenCommonBase) current.eGet(myCommonBaseReference);
			return oldCB.getVisualID() == newCB.getVisualID();
		}
	}
}
