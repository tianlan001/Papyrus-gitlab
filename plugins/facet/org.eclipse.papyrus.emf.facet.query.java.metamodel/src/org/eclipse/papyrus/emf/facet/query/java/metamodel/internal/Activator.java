package org.eclipse.papyrus.emf.facet.query.java.metamodel.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.papyrus.emf.facet.query.java.metamodel.v0_2_0.javaquery.JavaQueryPackage;
import org.eclipse.papyrus.emf.facet.query.java.metamodel.v0_2_0.javaquery.internal.JavaQueryValidator;
import org.osgi.framework.BundleContext;

/** The activator class controls the plug-in life cycle */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.emf.facet.query.java.metamodel"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		Activator.plugin = this;
		EValidator.Registry.INSTANCE.put
		(JavaQueryPackage.eINSTANCE, 
		 new EValidator.Descriptor() {
			 public EValidator getEValidator() {
				 return JavaQueryValidator.eInstance;
			 }
		 });
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		Activator.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return Activator.plugin;
	}
}
