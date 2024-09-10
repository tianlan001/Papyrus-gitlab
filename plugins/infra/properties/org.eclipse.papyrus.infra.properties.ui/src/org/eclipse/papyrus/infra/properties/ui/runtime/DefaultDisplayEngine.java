/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - Use URIs to support non-URL-compatible storage (CDO)
 *  Christian W. Damus (CEA) - bugs 417409, 444227
 *  Christian W. Damus - bugs 450478, 454536, 515257
 *  Patrick Tessier (CEA LIST)- bug 568329
 *  EclipseSource - Bug 572530
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.runtime;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.catalog.PropertiesURIHandler;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.renderers.SectionRendererService;
import org.eclipse.papyrus.infra.properties.ui.util.EMFURLStreamHandler;
import org.eclipse.papyrus.infra.properties.ui.xwt.PapyrusXWTCore;
import org.eclipse.papyrus.infra.properties.ui.xwt.XWTTabDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.xwt.DefaultLoadingContext;
import org.eclipse.xwt.ILoadingContext;
import org.eclipse.xwt.IXWTLoader;
import org.eclipse.xwt.XWT;
import org.eclipse.xwt.XWTLoaderManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A default implementation for {@link DisplayEngine}
 *
 * @author Camille Letavernier
 */
public class DefaultDisplayEngine implements DisplayEngine {

	private static ILoadingContext loadingContext = new DefaultLoadingContext(DefaultDisplayEngine.class.getClassLoader());
	private static PapyrusXWTCore papyrusCore = new PapyrusXWTCore();

	private Map<String, XWTTabDescriptor> currentTabs = new HashMap<>();

	private TabModel<DataSource> displayedSections = new TabModel<>();

	private TabModel<Control> controls = new TabModel<>();

	private boolean allowDuplicate;

	private Object xmlCache;
	// Cache of data source for which the widget page has been build
	private List<DataSource> dataSourceCache = new ArrayList<>();
	// Cache about evaluation of set of constraint linked to a data source.
	private HashMap<ConstraintEvaluationKey, Boolean> constraintEvaluationCache = new HashMap<>();
	private SectionRendererService sectionRendererService;
	private final List<Runnable> toDispose = new ArrayList<>();

	/**
	 * Constructs a new DisplayEnginet that doesn't allow the duplication of sections
	 */
	public DefaultDisplayEngine() {
		this(false);
	}

	/**
	 * Constructor.
	 *
	 * @param allowDuplicate
	 *            If false, two calls of {@link #createSection(Composite, Section, DataSource)} with the same
	 *            section will display the section only once : only the first call is taken into account
	 *            The main property view doesn't allow duplication, to avoid redundancy when two views link to
	 *            the same section.
	 */
	public DefaultDisplayEngine(boolean allowDuplicate) {
		this.allowDuplicate = allowDuplicate;

		BundleContext bundleContext = FrameworkUtil.getBundle(DefaultDisplayEngine.class).getBundleContext();
		ServiceReference<SectionRendererService> serviceReference = bundleContext.getServiceReference(SectionRendererService.class);
		this.sectionRendererService = bundleContext.getService(serviceReference);
		toDispose.add(() -> bundleContext.ungetService(serviceReference));
	}

	@Override
	public List<ITabDescriptor> getTabDescriptors(Set<View> views) {
		Map<String, XWTTabDescriptor> result = new LinkedHashMap<>();

		Set<String> selectedSections = new HashSet<>();

		for (View view : views) {
			for (Section section : view.getSections()) {
				if (selectedSections.contains(section.getName())) {
					continue;
				}

				Tab tab = section.getTab();

				if (tab == null) {
					Activator.log.warn("Null tab for " + section); //$NON-NLS-1$
					continue;
				}

				XWTTabDescriptor descriptor;

				if (result.containsKey(tab.getId())) {
					descriptor = result.get(tab.getId());
				} else {
					descriptor = new XWTTabDescriptor(tab, sectionRendererService);
					result.put(tab.getId(), descriptor);
				}

				descriptor.addSection(section, view, this);
				selectedSections.add(section.getName());
			}
		}

		for (Map.Entry<String, XWTTabDescriptor> next : result.entrySet()) {
			XWTTabDescriptor existing = currentTabs.get(next.getKey());
			if ((existing != null) && !existing.equals(next.getValue())) {
				// Will have to rebuild this tab
				disposeControls(next.getKey());
			}
		}

		currentTabs = result;

		return new ArrayList<>(result.values());
	}

	/**
	 * Disposes the controls created by this DisplayEngine for the specified section.
	 * This should not dispose the engine itself, which can be reused.
	 */
	protected void disposeControls(Section section) {
		Control control = controls.remove(section);
		if (control != null) {
			control.dispose();
		}

		DataSource dataSource = displayedSections.remove(section);
		if (dataSource != null) {
			dataSource.release();
		}
	}

	/**
	 * Disposes the controls created by this DisplayEngine for the specified tab ID.
	 * This should not dispose the engine itself, which can be reused.
	 */
	protected void disposeControls(String tabID) {
		for (Control control : this.controls.remove(tabID)) {
			control.dispose();
		}

		for (DataSource dataSource : displayedSections.remove(tabID)) {
			dataSource.release();
		}
	}

	protected void disposeControls() {
		for (String next : new ArrayList<>(controls.tabIDs())) {
			disposeControls(next);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		disposeControls();
		toDispose.forEach(Runnable::run);
		toDispose.clear();
	}

	/**
	 * Invalidates any caches that I may have because the displayed property UI contexts, constraints, or views have
	 * changed in some way.
	 */
	public void invalidate() {
		// Forget the cached XML property definitions because they may have changed
		xmlCache = null;
	}

	@Override
	public Control createSection(Composite parent, Section section, DataSource source) {
		if (source == null) {
			return null;
		}

		DataSource existing = getDataSource(section);
		if (!allowDuplicate && (existing != null)) {
			if (isUnloaded(existing) || conflictingArity(existing.getSelection(), source.getSelection())) {
				// If it's a left-over from an unloaded resource, then rebuild the properties UI because
				// element-browser widgets and other things may remember the previous (now invalid)
				// resource-set context. Also, cannot reuse a multiple-selection data source for
				// single-selection and vice-versa
				disposeControls(section);
			} else {
				// Update the data source and fire the bindings
				existing.setSelection(source.getSelection());

				return null;
			}
		}

		Control control = createSection(parent, section, loadXWTFile(section), source);

		addDataSource(section, source);

		if (control != null) {
			addControl(section, control);
		}

		return control;
	}

	protected DataSource getDataSource(Section section) {
		return displayedSections.get(section);
	}

	/**
	 * Queries whether any object selected in a data source is unloaded (now an EMF proxy object).
	 *
	 * @param dataSource
	 *            a data source
	 * @return whether it contains an unloaded model element
	 */
	protected boolean isUnloaded(DataSource dataSource) {
		boolean result = false;

		for (Iterator<?> iter = dataSource.getSelection().iterator(); !result && iter.hasNext();) {
			EObject next = EMFHelper.getEObject(iter.next());
			result = (next != null) && next.eIsProxy();
		}

		return result;
	}

	protected boolean conflictingArity(IStructuredSelection selection1, IStructuredSelection selection2) {
		return (selection1.size() <= 1) != (selection2.size() <= 1);
	}

	/**
	 * Adds a new {@code dataSource} for a property {@code section}.
	 *
	 * @return the previously-recorded data source, if any, for this {@code section} which has now been displaced
	 */
	protected DataSource addDataSource(Section section, DataSource dataSource) {
		DataSource result = displayedSections.put(section, dataSource.retain());

		if (result != null) {
			result.autoRelease();
		}

		return result;
	}

	protected void addControl(final Section section, Control control) {
		controls.put(section, control);

		control.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				// Perhaps the tabbed properties view is disposing a tab that is not shown by the new selection
				DataSource dataSource = displayedSections.remove(section);
				if (dataSource != null) {
					dataSource.release();
				}

				controls.remove(section);
			}
		});
	}

	@Override
	public void refreshSection(Composite parent, Section section, DataSource source) {
		// if the data source is not is the cache, sure we must destroy and construct all controls.
		if (!(dataSourceCache.contains(source))) {
			dataSourceCache.add(source);
			storeConstraintevalutionForSource(section, source);

			disposeAndCreateControl(parent, section, source);
		} else {
			// in this context, all widget have been constructed and need to be refreshed
			// but if constraints have been associated to the data source, maybe new widget may
			// appear depending to the change of the value
			ConstraintEvaluationKey constrainstEvaluationKey = new ConstraintEvaluationKey(source, section);
			if (constraintEvaluationCache.containsKey(constrainstEvaluationKey)) {
				boolean newValue = evaluateConstraintForSection(section, source);
				// the value has changed , destruction and creation is needed.
				if (newValue != constraintEvaluationCache.get(constrainstEvaluationKey).booleanValue()) {
					disposeAndCreateControl(parent, section, source);
					storeConstraintevalutionForSource(section, source);

				}
				// in other case, only refresh is needed.
			} else {
				// the constraint evaluation has not be created
				// the section has to be created
				// for example the selection has changed
				storeConstraintevalutionForSource(section, source);
				disposeAndCreateControl(parent, section, source);
			}
		}
	}

	/**
	 * this method destroy all SWT controler and create all.
	 *
	 * @since 4.0
	 */
	protected void disposeAndCreateControl(Composite parent, Section section, DataSource source) {
		for (Control control : parent.getChildren()) {
			control.dispose();
		}

		Control control = createSection(parent, section, loadXWTFile(section), source);

		addDataSource(section, source);

		if (control != null) {
			addControl(section, control);
		}
	}

	/**
	 * This method is used to store evaluation of constraint to determine if it is needed to add potential new controls.
	 *
	 * @since 5.0
	 */
	@Override
	public void storeConstraintevalutionForSource(Section section, DataSource source) {
		if (section.getConstraints().size() > 0) {
			boolean value = evaluateConstraintForSection(section, source);
			ConstraintEvaluationKey key = new ConstraintEvaluationKey(source, section);
			if (constraintEvaluationCache.containsKey(key)) {
				constraintEvaluationCache.replace(key, new Boolean(value));
			} else {
				constraintEvaluationCache.put(key, new Boolean(value));
			}
		}
	}

	/**
	 * Evaluate constraints linked to a data source
	 *
	 * @since 4.0
	 */
	protected boolean evaluateConstraintForSection(Section section, DataSource source) {
		boolean constraintsvalue = false;
		for (ConstraintDescriptor cd : section.getConstraints()) {
			Constraint c = org.eclipse.papyrus.infra.constraints.runtime.ConstraintFactory.getInstance().createFromModel(cd);
			List<?> selectionList = source.getSelection().toList();
			constraintsvalue = constraintsvalue || c.match(selectionList);
		}
		return constraintsvalue;
	}

	@Override
	public Control createSection(Composite parent, Section section, URI sectionFile, DataSource source) {
		if (sectionFile == null) {
			sectionFile = loadXWTFile(section);
			if (sectionFile == null) {
				return null;
			}
		}

		ILoadingContext xwtContext = XWT.getLoadingContext();
		boolean needsRestoreProfile = XWT.applyProfile(papyrusCore);
		IXWTLoader xwtLoader = XWTLoaderManager.getActive();

		Control control = null;

		try {
			XWT.setLoadingContext(loadingContext);

			ResourceSet rset = section.eResource().getResourceSet();
			URL url = new URL(null, sectionFile.toString(), new EMFURLStreamHandler(rset.getURIConverter()));

			Map<String, Object> options = new HashMap<>();
			options.put(IXWTLoader.CONTAINER_PROPERTY, parent);
			options.put(IXWTLoader.DATACONTEXT_PROPERTY, source);
			options.put(IXWTLoader.XML_CACHE_PROPERTY, (xmlCache != null) ? xmlCache : Boolean.TRUE);
			options.put(IXWTLoader.DISABLE_USER_CONTROLS, Boolean.TRUE);

			XWTLoaderManager.setActive(xwtLoader, true);

			control = (Control) XWT.loadWithOptions(url, options);
			xmlCache = options.get(IXWTLoader.XML_CACHE_PROPERTY);

			if (control != null) {
				control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				addControl(section, control);
			}
		} catch (Exception ex) {
			Activator.log.error("Error while loading " + section.getSectionFile(), ex); //$NON-NLS-1$
			disposeControls(section.getTab().getId());
			Label label = new Label(parent, SWT.NONE);
			label.setText("An error occured in the property view. The file " + section.getSectionFile() + " could not be loaded"); //$NON-NLS-1$ //$NON-NLS-2$
		} finally {
			if (needsRestoreProfile) {
				XWT.restoreProfile();
			}
			XWT.setLoadingContext(xwtContext);
		}
		layout(parent);
		// After creation, store values in the caches
		dataSourceCache.add(source);
		storeConstraintevalutionForSource(section, source);
		return control;
	}

	private URI loadXWTFile(Section section) {
		EObject tab = section.eContainer();
		Context context = (Context) tab.eContainer();
		if (context.eResource() == null) {
			context = PropertiesRuntime.getConfigurationManager().getContext(context.getName());
			Activator.log.warn("No resource for Context : " + context + " ; refreshing the model"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		URI sectionURI = URI.createURI(section.getSectionFile());
		URI baseURI = context.eResource().getURI();
		if (PropertiesURIHandler.PROPERTIES_SCHEME.equals(baseURI.scheme())) {
			PropertiesURIHandler handler = new PropertiesURIHandler();
			baseURI = handler.getConvertedURI(baseURI);
		}
		sectionURI = sectionURI.resolve(baseURI);

		return sectionURI;
	}

	private void layout(Composite parent) {
		parent.getParent().getParent().layout();
		parent.getParent().layout();
		parent.layout();
	}

	@Override
	public void removeSection(Composite parent) {
		for (Control control : parent.getChildren()) {
			control.dispose();
		}
		layout(parent);
	}

	/**
	 * Creates a proxy for a {@code section} that makes it distinct from other occurrences of the same section, according to some
	 * arbitrary {@code disciminator}.
	 *
	 * @param section
	 *            a section to be repeated with unique discriminators
	 * @param discriminator
	 *            this {@code section}'s discriminator value
	 *
	 * @return the proxy instance combining the identity of the {@code section} with its unique {@code discriminator}
	 */
	public static Section discriminate(Section section, Object discriminator) {
		if (section == null) {
			throw new IllegalArgumentException("null section");
		}
		if (discriminator == null) {
			throw new IllegalArgumentException("null discriminator");
		}
		if (SectionDiscriminator.isDiscriminated(section)) {
			throw new IllegalArgumentException("section already has a discriminator");
		}

		return SectionDiscriminator.discriminate(section, discriminator);
	}

	/**
	 * Obtains the discriminator for a {@code section} proxy, if it is a proxy.
	 *
	 * @param section
	 *            a section that is repeated with unique discriminators
	 * @return this {@code section}'s discriminator value, or {@code null} if it is a singleton (non-proxy) section
	 */
	public static Object getDiscriminator(Section section) {
		if (section == null) {
			throw new IllegalArgumentException("null section");
		}

		return SectionDiscriminator.getDiscriminator(section);
	}

	/**
	 * This is an inner class especially used for the cache of value of constraints for a section+ Datasource
	 *
	 * @since 5.0
	 */
	public class ConstraintEvaluationKey {
		private DataSource dataSource = null;
		private Section section = null;

		/**
		 * Constructor.
		 *
		 * @param dataSource
		 * @param section
		 */
		public ConstraintEvaluationKey(DataSource dataSource, Section section) {
			super();
			this.dataSource = dataSource;
			this.section = section;
		}

		/**
		 * @return the dataSource
		 */
		public DataSource getDataSource() {
			return dataSource;
		}

		/**
		 * @return the section
		 */
		public Section getSection() {
			return section;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 *
		 * @return
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
			result = prime * result + ((section == null) ? 0 : section.hashCode());
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 *
		 * @param obj
		 * @return
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ConstraintEvaluationKey other = (ConstraintEvaluationKey) obj;
			if (dataSource == null) {
				if (other.dataSource != null) {
					return false;
				}
			} else if (!dataSource.equals(other.dataSource)) {
				return false;
			}
			if (section == null) {
				if (other.section != null) {
					return false;
				}
			} else if (!section.equals(other.section)) {
				return false;
			}
			return true;
		}



	}
}
