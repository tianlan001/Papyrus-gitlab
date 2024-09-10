/*****************************************************************************
 * Copyright (c) 2013 CEA LIST, 2014 Flanders' Make.
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
 *  Klaas Gadeyne (Flanders' Make) klaas.gadeyne@flandersmake.be - Extended for CallBehaviorActions,
 *  see bug 453721
 *****************************************************************************/
package org.eclipse.papyrus.uml.navigation.navigableElement;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationContributor;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.CallBehaviorAction;

/**
 * NavigationContributor to navigate from a CallBehaviorAction to its Behavior declaration
 *
 * @author Klaas Gadeyne
 *
 */
public class CBANavigationContributor implements NavigationContributor {

	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> result = new LinkedList<NavigableElement>();

		Element element = UMLUtil.resolveUMLElement(fromElement);
		if (element instanceof CallBehaviorAction) {
			result.add(new CBANavigableElement(((CallBehaviorAction) element).getBehavior()));
		}
		return result;
	}
}