/******************************************************************************
 * Copyright (c) 2021-2022, 2024 CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Initial API and implementation
 *  Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - bug 580744
 *  Dilan EESHVARAN (CEA LIST) - dilan.eeshvaran@cea.fr - bug 583128
 *****************************************************************************/
package org.eclipse.papyrus.sirius.editor;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.spi.resolver.IEObjectResolver;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.sirius.editor.internal.emf.SiriusEditPartEObjectResolver;
import org.eclipse.sirius.diagram.ui.tools.api.preferences.SiriusDiagramUiPreferencesKeys;
import org.eclipse.sirius.diagram.tools.internal.preferences.SiriusDiagramInternalPreferencesKeys;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.papyrus.sirius.editor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/** The log. */
	public static Logger log =  LoggerFactory.getLogger( Activator.class );

	/**
	 * contribution to the OSGi Service used by {@link EMFHelper} to revolve Sirius EditPart into the represented semantic EObject
	 */
	private ServiceRegistration<IEObjectResolver> eobjectResolverReg;


	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		this.eobjectResolverReg = context.registerService(IEObjectResolver.class, SiriusEditPartEObjectResolver::resolve, null);
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.sirius.diagram.ui"); //$NON-NLS-1$
		IPreferenceStore store1 = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.sirius.diagram"); //$NON-NLS-1$
		store.setValue(SiriusDiagramUiPreferencesKeys.PREF_SHOW_SYNCHRONIZE_STATUS_DECORATOR.name(), true);
		store1.setValue(SiriusDiagramInternalPreferencesKeys.PREF_SYNCHRONIZE_DIAGRAM_ON_CREATION.name(), false);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (this.eobjectResolverReg != null) {
			this.eobjectResolverReg.unregister();
			this.eobjectResolverReg = null;
		}
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
