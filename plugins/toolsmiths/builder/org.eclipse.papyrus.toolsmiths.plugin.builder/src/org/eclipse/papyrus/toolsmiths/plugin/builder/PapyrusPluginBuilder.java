/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 569357, 572644
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.plugin.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.papyrus.toolsmiths.plugin.builder.preferences.PluginBuilderPreferencesConstants;

/**
 * The main Papyrus builder in charge to delegate build to sub-builders.
 */
public class PapyrusPluginBuilder extends IncrementalProjectBuilder {

	/**
	 * the id of the builder
	 */
	public static final String BUILDER_ID = "org.eclipse.papyrus.plugin.builder"; //$NON-NLS-1$

	@Deprecated
	private static final List<AbstractPapyrusBuilder> modelBuilders = new ArrayList<>();

	@Deprecated
	private static final List<AbstractPapyrusBuilder> pluginBuilders = new ArrayList<>();

	@Deprecated
	private static final List<AbstractPapyrusBuilder> manifestBuilders = new ArrayList<>();

	/**
	 * @deprecated Since version 1.1 of this bundle, register an {@link IPapyrusBuilderProvider}, instead.
	 */
	@Deprecated(since = "1.1")
	public static final void addModelBuilder(final AbstractPapyrusBuilder modelBuilder) {
		modelBuilders.add(modelBuilder);
	}

	/**
	 * @deprecated Since version 1.1 of this bundle, register an {@link IPapyrusBuilderProvider}, instead.
	 */
	@Deprecated(since = "1.1")
	public static final void addPluginBuilder(final AbstractPapyrusBuilder pluginBuilder) {
		pluginBuilders.add(pluginBuilder);
	}

	/**
	 * @deprecated Since version 1.1 of this bundle, register an {@link IPapyrusBuilderProvider}, instead.
	 */
	@Deprecated(since = "1.1")
	public static final void addManifestBuilder(final AbstractPapyrusBuilder pluginBuilder) {
		manifestBuilders.add(pluginBuilder);
	}

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		if (getProject() == null) {
			return null;
		}

		if (!isPapyrusPluginBuilderActivated()) {
			return null;
		}

		List<AbstractPapyrusBuilder> delegates = getDelegates();

		SubMonitor subMonitor = SubMonitor.convert(monitor,
				/* Clean */ 1 + /* Pluggable builders */ delegates.size());

		// remove all previously created marker
		clean(subMonitor.newChild(1));

		final List<IProject> wantedDeltaProjects = new ArrayList<>();

		for (final AbstractPapyrusBuilder builder : delegates) {
			try {
				IProject[] projects = builder.build(getProject(), this, kind, args, subMonitor.newChild(1));
				if (projects != null && projects.length != 0) {
					wantedDeltaProjects.addAll(Arrays.asList(projects));
				}
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in Papyrus project builder delegate.", e); //$NON-NLS-1$
			}
		}

		SubMonitor.done(monitor);

		return wantedDeltaProjects.toArray(new IProject[wantedDeltaProjects.size()]);
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		List<AbstractPapyrusBuilder> delegates = getDelegates();

		SubMonitor subMonitor = SubMonitor.convert(monitor,
				/* Super implementation */ 1 + /* Pluggable builders */ delegates.size());

		super.clean(subMonitor.newChild(1));

		for (final AbstractPapyrusBuilder builder : delegates) {
			try {
				builder.clean(subMonitor.newChild(1), getProject());
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in Papyrus project builder delegate.", e); //$NON-NLS-1$
			}
		}

		SubMonitor.done(monitor);
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the Papyrus Plugin Builder is activated
	 */
	protected boolean isPapyrusPluginBuilderActivated() {
		Boolean result = Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.ACTIVATE_MAIN_PAPYRUS_BUILDER);
		return result.booleanValue();
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the Papyrus Model Builder is activated
	 */
	protected boolean isPapyrusModelBuilderActivated() {
		boolean result = Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MODEL_BUILDER);
		if (result) {
			// we get sub preference too, to avoid to cross and call builder to do nothing
			result = Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_CHECK_MODEL_DEPENDENCIES)
					|| Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_VALIDATE_MODEL);
		}
		return result;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the Papyrus Manifest Builder is activated
	 */
	protected boolean isPapyrusManifestBuilderActivated() {
		boolean result = Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MANIFEST_BUILDER);

		if (result) {
			// we get sub preference too, to avoid to cross and call builder to do nothing
			result = Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE)
					|| Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT);
		}
		return result;
	}

	private List<AbstractPapyrusBuilder> getDelegates() {
		List<AbstractPapyrusBuilder> result = new ArrayList<>();
		Collection<IPapyrusBuilderProvider> providers = Activator.getDefault().getBuilderProviders();

		if (isPapyrusModelBuilderActivated()) {
			result.addAll(modelBuilders); // Legacy case
			providers.stream().map(p -> provide(p, PapyrusBuilderKind.MODEL_RESOURCE)).filter(Objects::nonNull).forEach(result::add);
		}

		if (isPapyrusManifestBuilderActivated()) {
			result.addAll(manifestBuilders); // Legacy case
			providers.stream().map(p -> provide(p, PapyrusBuilderKind.BUNDLE_MANIFEST)).filter(Objects::nonNull).forEach(result::add);
		}

		result.addAll(pluginBuilders); // Legacy case
		providers.stream().map(p -> provide(p, PapyrusBuilderKind.PLUGIN_MANIFEST)).filter(Objects::nonNull).forEach(result::add);

		return result;
	}

	private AbstractPapyrusBuilder provide(IPapyrusBuilderProvider provider, PapyrusBuilderKind builderKind) {
		return provider.getBuilder(builderKind, getProject());
	}
}
