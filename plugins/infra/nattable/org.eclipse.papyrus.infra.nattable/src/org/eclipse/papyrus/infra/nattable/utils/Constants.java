/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.utils;


/**
 * 
 * The constants used in the Papyrus NatTable Framework
 *
 */
public class Constants {

	private Constants() {
		// to prevent instanciation
	}

	public static final String BEGIN_OF_COLLECTION = "["; //$NON-NLS-1$

	public static final String END_OF_COLLECTION = "]"; //$NON-NLS-1$

	/**
	 * The String used to represent big collections
	 * Example: [v1, v2, v3, ...]
	 */
	public static final String BIG_COLLECTION = "..."; //$NON-NLS-1$

	public static final String SEPARATOR = ", "; //$NON-NLS-1$

	public static final String NOT_AVALAIBLE = "N/A"; //$NON-NLS-1$

	public static final String INFINITY_STAR = "*"; //$NON-NLS-1$

	public static final String INFINITE_MINUS_ONE = "-1"; //$NON-NLS-1$

	/**
	 * the context to use to find the label provider used to display values in cells and headers
	 */
	public static final String TABLE_LABEL_PROVIDER_CONTEXT = "org.eclipse.papyrus.infra.nattable.full.labelprovider";//$NON-NLS-1$

	/**
	 * the context to use to find the label provider used to display values in the headers
	 */
	public static final String HEADER_LABEL_PROVIDER_CONTEXT = "org.eclipse.papyrus.infra.nattable.header.labelprovider";//$NON-NLS-1$

	/**
	 * The tree filling feature configuration context.
	 */
	public static final String HEADER_LABEL_PROVIDER_TREE_FILLING_FEATURE_CONFIGURATION_CONTEXT = "org.eclipse.papyrus.infra.nattable.header.treefilling.feature.labelprovider"; //$NON-NLS-1$
	
	/**
	 * The tree filling operation configuration context.
	 */
	public static final String HEADER_LABEL_PROVIDER_TREE_FILLING_OPERATION_CONFIGURATION_CONTEXT=	"org.eclipse.papyrus.infra.nattable.header.treefilling.operation.labelprovider"; //$NON-NLS-1$

	
	/**
	 * the context to use to find the label provider used to display values in the body of the table
	 */

	public static final String BODY_LABEL_PROVIDER_CONTEXT = "org.eclipse.papyrus.infra.nattable.body.labelprovider";//$NON-NLS-1$

	/**
	 * the id of the command specific for rows in the table
	 */
	public static final String ROW_HEADER_COMMANDS_CATEGORY = "org.eclipse.papyrus.infra.nattable.rows.command.category"; //$NON-NLS-1$


	/**
	 * The name of the parameter used for the sort command
	 */
	public static final String SORT_COMMAND_PARAMETER = "org.eclipse.papyrus.infra.nattable.sort.command.alphabetic.order.parameter"; //$NON-NLS-1$

	/**
	 * the key used to store the container of the pasted element in a map
	 */
	public static final String PASTED_ELEMENT_CONTAINER_KEY = "pastedElementContainer"; //$NON-NLS-1$

	/**
	 * the key used to store the references to set after to have attached the pasted element to the model
	 */
	public static final String REFERENCES_TO_SET_KEY = "referencesToSet"; //$NON-NLS-1$

	/**
	 * the key used to register additional post actions to conclude the paste. the returned value must be a Collection<String>
	 * These post actions have been executed by CellManager, but they must be concluded.
	 * These post actions are not defined in the configuration of the table, but added by CellManager
	 *
	 * This key is used to be able to apply stereotype required by columns properties, when there are no post actions defined
	 * in the paste configuration of the table
	 * see bug 431691: [Table 2] Paste from Spreadsheet must be able to apply required stereotypes for column properties in all usecases
	 */
	public static final String ADDITIONAL_POST_ACTIONS_TO_CONCLUDE_PASTE_KEY = "additionnal_post_actions_to_conclude_paste"; //$NON-NLS-1$

	/**
	 * the key used to store the cells to attached to the model
	 */
	public static final String CELLS_TO_ADD_KEY = "cellsToAdd"; //$NON-NLS-1$

}
