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
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.navigation.service;

import java.util.List;

/**
 * Classes that implement this interface returns a list of related objects to the given object in the parameter
 *
 */
public interface NavigationMenuContributor {
	public List<NavigationMenuButton> getButtons(Object fromElement);
}
