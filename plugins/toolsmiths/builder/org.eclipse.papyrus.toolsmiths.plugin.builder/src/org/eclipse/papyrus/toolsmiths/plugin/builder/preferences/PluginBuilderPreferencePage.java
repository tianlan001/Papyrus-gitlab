/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.plugin.builder.preferences;

import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Activator;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The preference page for Papyrus Plugin Builder
 */
public class PluginBuilderPreferencePage extends AbstractPapyrusPreferencePage {

	/**
	 * @see org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage#createPageContents(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	protected void createPageContents(Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.PluginBuilderPreferencePage_builderPreferenceGroupDescription);
		final PluginBuilderPreferenceGroup builderGroup = new PluginBuilderPreferenceGroup(parent, getTitle(), this);
		addPreferenceGroup(builderGroup);
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage#getBundleId()
	 *
	 * @return
	 */
	@Override
	protected String getBundleId() {
		return Activator.PLUGIN_ID;
	}
}