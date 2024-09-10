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

package org.eclipse.papyrus.infra.nattable.common.api;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;

/**
 * @author Vincent Lorenzo
 *         This interface provides code which can be used for returned IStatus
 */
public interface ITableEditorStatusCode {

	/** It is OK! */
	public static final int OK_STATUS_CODE = IStatus.OK;

	/** The service registry has not be found */
	public static final int SERVICES_REGISTRY_NOT_FOUND = OK_STATUS_CODE + 1;

	/** the model set has not been found */
	public static final int MODEL_SET_NOT_FOUND = SERVICES_REGISTRY_NOT_FOUND + 1;

	/** the editing domain has not been found */
	public static final int EDITING_DOMAIN_NOT_FOUND = MODEL_SET_NOT_FOUND + 1;

	/** the page manager has not been found */
	public static final int PAGE_MANAGER_NOT_FOUND = EDITING_DOMAIN_NOT_FOUND + 1;

	/** the table context is <code>null</code> */
	public static final int TABLE_CONTEXT_IS_NULL = PAGE_MANAGER_NOT_FOUND + 1;

	/** the table type is <code>null</code> */
	public static final int TABLE_TYPE_IS_NULL = TABLE_CONTEXT_IS_NULL + 1;

	/** the name of the table is <code>null</code> */
	public static final int TABLE_NAME_IS_NULL = TABLE_TYPE_IS_NULL + 1;

	/** the {@link PapyrusNattableModel} has not been found */
	public static final int PAPYRUS_NATTABLE_MODEL_NOT_FOUND = TABLE_NAME_IS_NULL + 1;

	/** the {@link TableViewPrototype} has not been found */
	public static final int TABLE_VIEW_PROTOTYPE_NOT_FOUND = PAPYRUS_NATTABLE_MODEL_NOT_FOUND + 1;

	/** the {@link TableConfiguration} URI has not be found */
	public static final int TABLE_CONFIGURATION_URI_NOT_FOUND = TABLE_VIEW_PROTOTYPE_NOT_FOUND + 1;

	/** the {@link TableConfiguration} has not been found */
	public static final int TABLE_CONFIGURATION_NOT_FOUND = TABLE_CONFIGURATION_URI_NOT_FOUND + 1;

	/** the returned command is <code>null</code> */
	public static final int COMMAND_NULL = TABLE_CONFIGURATION_NOT_FOUND + 1;

	/** the returned command is not executable */
	public static final int COMMAND_NOT_EXECUTABLE = COMMAND_NULL + 1;

	/** the table is read only, so we cannot edit/destroy it */
	public static final int TABLE_IS_READ_ONLY = COMMAND_NOT_EXECUTABLE + 1;

	/** the command provider has not been found */
	public static final int COMMAND_PROVIDER_NOT_FOUND = TABLE_IS_READ_ONLY + 1;
}
