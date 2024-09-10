/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal;


/**
 * Visitor used to visit the hierarchy of Parts.
 * Visit can be stopped by returning false from the visit() method.
 *
 * @author dumoulin
 *
 */
public interface IPartVisitor {

	/**
	 * Accept the node of specified type.
	 *
	 * @param folder
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */

	public boolean accept(RootPart tile);

	/**
	 * Accept the node of specified type.
	 *
	 * @param folder
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */
	public boolean accept(SashPanelPart tile);

	/**
	 * Accept the node of specified type.
	 *
	 * @param folder
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */
	public boolean accept(TabFolderPart folder);

	/**
	 * Accept the node of specified type.
	 *
	 * @param folder
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */
	public boolean accept(TabItemPart tile);

	/**
	 * Accept the node of specified type.
	 *
	 * @param folder
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */
	public boolean accept(EditorPart tile);

	/**
	 * Accept the node of specified type.
	 *
	 * @param componentPart
	 * @return true if the visit should continue, false if the visit should be stopped.
	 */
	public boolean accept(ComponentPart componentPart);


}
