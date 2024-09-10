/*******************************************************************************
 * Copyright (c) 2007 CEA List.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA List - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrus.uml.extensionpoints.standard;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.uml.extensionpoints.IRegisteredItem;

/**
 *
 */
public class ExtensionLabelProvider extends LabelProvider {

	/**
	 *
	 *
	 * @param obj
	 *
	 * @return
	 */
	@Override
	public String getText(Object obj) {

		if (obj instanceof RegisteredElementExtensionPoint) {
			return ((IRegisteredItem)obj).getName();
		} else {
			return "unknown object";
		}
	}
}
