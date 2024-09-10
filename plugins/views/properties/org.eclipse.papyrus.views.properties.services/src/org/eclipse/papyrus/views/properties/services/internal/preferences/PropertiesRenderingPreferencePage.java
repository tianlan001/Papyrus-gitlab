/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.services.internal.preferences;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.papyrus.views.properties.services.Activator;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService;
import org.eclipse.papyrus.views.properties.services.PropertyRendererPreferencesConstants;
import org.eclipse.papyrus.views.properties.services.internal.messages.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * The preference page used to select the renderer to use
 *
 */
public class PropertiesRenderingPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The radio group used to select the renderer
	 */
	private RadioGroupFieldEditor radioGroupFieldEditor;

	/**
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *
	 * @param workbench
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 *
	 */
	@Override
	protected void createFieldEditors() {
		List<String> renderers = getRenderers();
		String[][] array = new String[renderers.size()][2];

		int i = 0;
		for (String delegate : renderers) {
			array[i][0] = delegate;
			array[i][1] = delegate;
			i++;
		}

		this.radioGroupFieldEditor = new RadioGroupFieldEditor(PropertyRendererPreferencesConstants.PREFERRED_RENDERER, Messages.SelectTheRendererToUse, 1, array, getFieldEditorParent());
		addField(this.radioGroupFieldEditor);
	}

	/**
	 *
	 * @return
	 */
	private List<String> getRenderers() {
		final BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		final ServiceReference<IPropertySheetPageProviderService> servreg = Activator.getDefault().getBundle().getBundleContext().getServiceReference(IPropertySheetPageProviderService.class);

		if (servreg != null) {
			final IPropertySheetPageProviderService provider = bundleContext.getService(servreg);
			if (provider != null) {
				return provider.getAvailableRenderers();
			}
		}
		return Collections.emptyList();
	}

}
