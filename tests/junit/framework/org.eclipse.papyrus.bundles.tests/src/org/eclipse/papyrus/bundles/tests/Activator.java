package org.eclipse.papyrus.bundles.tests;

import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.bundles.tests"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ITargetPlatformService tpService;

	/**
	 * The constructor
	 */
	public Activator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		ServiceReference<? extends ITargetPlatformService> ref = context.getServiceReference(ITargetPlatformService.class);
		tpService = (ref == null) ? null : context.getService(ref);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tpService = null;
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

	public ITargetPlatformService getTargetPlatformService() {
		return tpService;
	}
}
