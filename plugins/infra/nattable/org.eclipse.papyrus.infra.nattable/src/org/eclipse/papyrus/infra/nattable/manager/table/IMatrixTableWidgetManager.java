/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *  Vincent Lorenzo (CEA LIST) - Bug 517742
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;

/**
 * This interface is here to manage the code specific for Matrix
 * 
 * @since 3.0
 * 
 * 
 */
public interface IMatrixTableWidgetManager extends INattableModelManager {

	/**
	 * 
	 * @param objectsToAdd
	 *            the collection of objects to add as Row Sources
	 * @since 5.0
	 */
	public void addRowSources(final Collection<Object> objectsToAdd);

	/**
	 * 
	 * @param objectsToRemove
	 *            the collection of objects to remove for Row Sources
	 * @since 5.0
	 */
	public void removeRowSources(final Collection<Object> objectsToRemove);

	/**
	 * 
	 * @param objectsToAdd
	 *            the collection of objects to add as Column Sources.
	 * @since 5.0
	 */
	public void addColumnSources(final Collection<Object> objectsToAdd);

	/**
	 * 
	 * @param objectsToRemove
	 *            the collection of objects to remove for the Column Sources
	 * @since 5.0
	 */
	public void removeColumnSources(final Collection<Object> objectsToRemove);

	/**
	 * 
	 * @param objectsToAdd
	 *            the collection of objects to add as Row Sources
	 * @return
	 * 		the command to add Row Sources
	 * @since 5.0
	 * 
	 */
	public Command getAddRowSourcesCommand(final Collection<Object> objectsToAdd);

	/**
	 * 
	 * @param objectsToRemove
	 *            the collection of objects to remove for Row Sources
	 * 
	 * @return
	 * 		the command to remove Row Sources
	 * @since 5.0
	 */
	public Command getRemoveRowSourcesCommand(final Collection<Object> objectsToRemove);

	/**
	 * 
	 * @param objectsToAdd
	 *            the collection of objects to add as Column Sources
	 * @return
	 * 		the command to add Column Sources
	 * @since 5.0
	 */
	public Command getAddColumnSourcesCommand(final Collection<Object> objectsToAdd);

	/**
	 * 
	 * @param objectsToRemove
	 *            the collection of objects to remove for Row Sources
	 * @return
	 * 		the command to remove Column Sources
	 * @since 5.0
	 */
	public Command getRemoveColumnSourcesCommand(final Collection<Object> objectsToRemove);
}
