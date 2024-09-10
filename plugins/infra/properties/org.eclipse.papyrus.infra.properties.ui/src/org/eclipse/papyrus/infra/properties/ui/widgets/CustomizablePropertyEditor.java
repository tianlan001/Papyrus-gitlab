/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;

/**
 * The interface to be implemented by all PropertyEditors
 *
 * @author Camille Letavernier
 *
 */
public interface CustomizablePropertyEditor {

	public DataSource getInput();

	public void setInput(DataSource input);

	public String getProperty();

	public void setProperty(String property);

	public boolean getShowLabel();

	public void setShowLabel(boolean showLabel);

	public String getCustomLabel();

	public void setCustomLabel(String customLabel);

	public void setReadOnly(boolean readOnly);

	public boolean getReadOnly();
}
