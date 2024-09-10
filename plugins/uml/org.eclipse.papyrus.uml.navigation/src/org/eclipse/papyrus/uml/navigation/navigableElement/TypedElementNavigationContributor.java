/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.uml.navigation.navigableElement;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationContributor;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.TypedElement;

/**
 * NavigationContributor to navigate from TypedElement to their Type
 *
 * @author Camille Letavernier
 *
 */
public class TypedElementNavigationContributor implements NavigationContributor {

	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> result = new LinkedList<NavigableElement>();

		Element element = UMLUtil.resolveUMLElement(fromElement);
		if (element instanceof TypedElement && ((TypedElement) element).getType() != null) {
			result.add(new TypedNavigableElement(((TypedElement) element).getType()));
		} else if (element instanceof Operation && ((Operation) element).getType() != null) {
			result.add(new OperationTypeNavigableElement((Operation) element));
		} else if (element instanceof Connector && ((Connector) element).getType() != null) {
			result.add(new ConnectorTypeNavigableElement((Connector) element));
		}

		return result;
	}
}
