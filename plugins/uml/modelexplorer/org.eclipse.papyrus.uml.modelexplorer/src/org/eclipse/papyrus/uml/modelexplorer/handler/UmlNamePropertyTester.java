/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.handler;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.menu.NamePropertyTester;
import org.eclipse.uml2.uml.NamedElement;


public class UmlNamePropertyTester extends NamePropertyTester {

	/**
	 * @see org.eclipse.papyrus.infra.ui.menu.NamePropertyTester#isNameChangeable(org.eclipse.jface.viewers.IStructuredSelection)
	 *
	 * @param selection
	 * @return
	 */
	@Override
	protected boolean isNameChangeable(IStructuredSelection selection) {
		boolean atLeastOne=false;
		if (!selection.isEmpty()) {
			Iterator<?> iter = selection.iterator();
			while (iter.hasNext()) {
				EObject object = EMFHelper.getEObject(iter.next());
				if (object instanceof NamedElement) {
					atLeastOne  =true;
					break;
				}
			}
		}
		return atLeastOne;
	}

}
