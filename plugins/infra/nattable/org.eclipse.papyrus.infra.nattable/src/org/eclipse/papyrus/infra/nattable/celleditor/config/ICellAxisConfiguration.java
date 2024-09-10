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

package org.eclipse.papyrus.infra.nattable.celleditor.config;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * @author MA244259
 *
 */
public interface ICellAxisConfiguration extends IPapyrusNatTableConfiguration{

	/**
	 * @param table
	 * @param axisElement
	 * @return
	 */
	boolean handles(Table table, Object axisElement);

	/**
	 * @param configRegistry
	 * @param axis
	 * @param configLabel
	 */
	void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel);

}
