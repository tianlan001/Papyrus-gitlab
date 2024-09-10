/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;
import org.eclipse.papyrus.infra.nattable.comparator.ObjectNameAndPathComparator;
import org.eclipse.papyrus.infra.nattable.display.converter.ObjectNameAndPathDisplayConverter;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.PapyrusTextMatchingMode;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.services.decoration.DecorationService;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * This class references the keys registered in the ConfigRegistry of the table to facilitate access to these objects.
 *
 * @author Vincent Lorenzo
 *
 */
public class NattableConfigAttributes {

	private NattableConfigAttributes() {
		// to prevent instaciantionF
	}

	/**
	 * The config attribute used to register the table model manager
	 *
	 * <ul>
	 * <li>To store it : <code> configRegistry.registerConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, YOUR_NATTABLE_MODEL_MANAGER, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);</code></li>
	 * <li>To get it : <code></code></li>
	 * </ul>
	 */
	public static final ConfigAttribute<INattableModelManager> NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE = new ConfigAttribute<INattableModelManager>();

	/**
	 * The config attribute used to register the label provider service
	 *
	 * <ul>
	 * <li>To store it : <code> configRegistry.registerConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, YOUR_LABEL_PROVIDER_SERVICE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);</code></li>
	 * <li>To get it : <code></code></li>
	 * LabelProviderService serv = configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
	 * </ul>
	 */
	public static final ConfigAttribute<LabelProviderService> LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE = new ConfigAttribute<LabelProviderService>();

	/**
	 * The config attribute used to register the decoration service
	 *
	 * <ul>
	 * <li>To store it : <code> configRegistry.registerConfigAttribute(NattableConfigAttributes.DECORATION_SERVICE_CONFIG_ATTRIBUTE, YOUR_LABEL_PROVER_SERVICE, DisplayMode.NORMAL, NattableConfigAttributes.DECORATION_SERVICE_ID);</code></li>
	 * <li>To get it : <code></code></li>
	 * LabelProviderService serv = configRegistry.getConfigAttribute(NattableConfigAttributes.DECORATION_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.DECORATION_SERVICE_ID);
	 * </ul>
	 */
	public static final ConfigAttribute<DecorationService> DECORATION_SERVICE_CONFIG_ATTRIBUTE = new ConfigAttribute<DecorationService>();


	/**
	 * Id used to register the label provider service
	 */
	public static final String LABEL_PROVIDER_SERVICE_ID = "label_provider_service_id"; //$NON-NLS-1$

	/**
	 * Id used to register the label provider service
	 */
	public static final String DECORATION_SERVICE_ID = "decoration_service_id"; //$NON-NLS-1$


	/**
	 * Id used to register the table model manager
	 */
	public static final String NATTABLE_MODEL_MANAGER_ID = "nattable_model_manager_id"; //$NON-NLS-1$

	/**
	 * Attribute used to register a comparator for object, comparing this kind of string : "name - path"
	 */
	public static final ConfigAttribute<ObjectNameAndPathComparator> OBJECT_NAME_AND_PATH_COMPARATOR = new ConfigAttribute<ObjectNameAndPathComparator>();

	/**
	 * the id used for the previous comparator
	 */
	public static final String OBJECT_NAME_AND_PATH_COMPARATOR_ID = "object_name_and_path_comparator_id"; //$NON-NLS-1$

	/**
	 * the attribute used to register a converter (object to string) the string as the format : "name - path" where name is the name of the object and path its path in the model
	 */
	public static final ConfigAttribute<ObjectNameAndPathDisplayConverter> OBJECT_NAME_AND_PATH_DISPLAY_CONVERTER = new ConfigAttribute<ObjectNameAndPathDisplayConverter>();

	/**
	 * the id of the previous converter
	 */
	public static final String OBJECT_NAME_AND_PATH_DISPLAY_CONVERTER_ID = "object_name_and_path_display_converter_id"; //$NON-NLS-1$

	/**
	 * the attribute used to store matched editor used for filtering in the table
	 */
	public static final ConfigAttribute<IPapyrusMatcherEditorFactory<?>> MATCHER_EDITOR_FACTORY = new ConfigAttribute<IPapyrusMatcherEditorFactory<?>>();

	/**
	 * the attribute used to define the matching mode to use for filter. The papyrus implements allows to use this one, or {@link TextMatchingMode}
	 */
	public static final ConfigAttribute<PapyrusTextMatchingMode> STRING_FILTER_MATCHING_MODE = new ConfigAttribute<PapyrusTextMatchingMode>();

	/**
	 * the config attribute used to register the class loading and storing filter state
	 */
	public static final ConfigAttribute<IFilterValueToMatchManager> FILTER_VALUE_TO_MATCH_MANAGER = new ConfigAttribute<IFilterValueToMatchManager>();

	/**
	 * the config attribute used to register the sort model
	 */
	public static final ConfigAttribute<ISortModel> ROW_SORT_MODEl = new ConfigAttribute<ISortModel>();

	/**
	 * The config attribute used to reinitialise the height of rows in the table.
	 * @since 3.0
	 */
	public static final ConfigAttribute<Boolean> REINITIALISE_ROW_HEIGHT = new ConfigAttribute<Boolean>();

}