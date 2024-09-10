/*******************************************************************************
* Copyright (c) 2013, 2020 Montages A.G., CEA LIST, Artal
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*  	Guillaume Hillairet (Montages A.G.) : initial implementation
*    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
*****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.xtend.ui;

import org.eclipse.papyrus.gmf.codegen.util.GMFGeneratorModule;
import org.eclipse.papyrus.gmf.codegen.util.GMFGeneratorUIModule;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.papyrus.gmf.codegen.xtend.ui"; //$NON-NLS-1$

	private static Activator plugin;

	private Injector injector;

	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
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

	public Injector getInjector() {		
		if (injector == null) {
			injector = createInjector();
		}
		return injector;
	}

	protected Injector createInjector() {
		try {
			Module runtimeModule = getRuntimeModule();
			Module uiModule = getUiModule();
			Module mergedModule = Modules2.mixin(runtimeModule, uiModule);
			return Guice.createInjector(mergedModule);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create injector", e);
		}
	}

	private Module getUiModule() {
		return new GMFGeneratorUIModule();
	}

	protected Module getRuntimeModule() {
		return new GMFGeneratorModule(null);
	}

}
