/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

/**
 * This interface is used by the action ShowHideLabelsAction
 *
 *
 *
 */
public interface ILabelRoleProvider {

	/**
	 * Returns the label role
	 *
	 * @return the label role
	 */
	public String getLabelRole();

	/**
	 * Returns an icon illustrating the label role
	 *
	 * @return an icon illustrating the label role
	 */
	public String getIconPathRole();
}
