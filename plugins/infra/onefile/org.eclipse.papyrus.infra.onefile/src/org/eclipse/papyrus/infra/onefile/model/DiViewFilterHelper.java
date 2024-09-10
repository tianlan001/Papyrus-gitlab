/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.onefile.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.onefile.Activator;
import org.osgi.framework.Bundle;

/**
 * This manage the di view filters extension point depending to the instanciated ones.
 *
 * @since 2.2
 */
public class DiViewFilterHelper {

	/**
	 * The singleton instance
	 */
	private static DiViewFilterHelper instance;

	/**
	 * The name of the extension point.
	 */
	private static final String DI_VIEW_FILTER = "diViewFilter";

	/**
	 * The list of extensions.
	 */
	public static List<Segment> segments;

	/**
	 * Default constructor.
	 */
	private DiViewFilterHelper() {
		final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, DI_VIEW_FILTER);
		segments = new ArrayList<DiViewFilterHelper.Segment>(elements.length);
		for (final IConfigurationElement e : elements) {
			Segment s = new DiViewFilterHelper.Segment();
			try {
				s.priority = Integer.parseInt(e.getAttribute("priority")); //$NON-NLS-1$
				s.bundle = Platform.getBundle(e.getContributor().getName());
				s.className = e.getAttribute("instance"); //$NON-NLS-1$
				segments.add(s);
			} catch (NumberFormatException ex) {
				// in case of exception the process continue but the stack is
				// traced
				Activator.log.error(ex);
			}
		}
		// sort elements according to their priorities
		Collections.sort(segments, new Comparator<Segment>() {
			public int compare(final Segment o1, final Segment o2) {
				final Integer val1 = o1 != null ? o1.priority : 0;
				final Integer val2 = o2 != null ? o2.priority : 0;
				return val1.compareTo(val2);
			}
		});
	}

	/**
	 * Get the singleton instance.
	 *
	 * @return The singleton instance.
	 */
	public static DiViewFilterHelper getInstance() {
		if (null == instance) {
			instance = new DiViewFilterHelper();
		}
		return instance;
	}

	/**
	 * Get the {@link IDiViewFilter} in the current platform.
	 *
	 * @return a {@link IDiViewFilter}.
	 */
	public IDiViewFilter getPapyrusDiViewFilter() {
		if (segments == null || segments.size() == 0) {
			throw new RuntimeException(
					"Initialisation error, please register extension to "
							+ Activator.PLUGIN_ID + "." + DI_VIEW_FILTER);
		}
		// segments are sorted by priorities, the highest is at the end of the list
		return segments.get(segments.size() - 1).getDiViewFilter();
	}

	/**
	 * Utility class to save extension point information.
	 */
	private static class Segment {

		/**
		 * The class name.
		 */
		public String className;

		/**
		 * The bundle.
		 */
		public Bundle bundle;

		/**
		 * The priority.
		 */
		public int priority;

		/**
		 * The instance.
		 */
		public IDiViewFilter instance = null;

		/**
		 * Get the di view filter.
		 *
		 * @return The di view filter.
		 */
		public IDiViewFilter getDiViewFilter() {
			if (instance == null) {
				try {
					instance = (IDiViewFilter) bundle.loadClass(className).newInstance();
				} catch (InstantiationException e) {
					Activator.log.error(e);
				} catch (IllegalAccessException e) {
					Activator.log.error(e);
				} catch (ClassNotFoundException e) {
					Activator.log.error(e);
				}
			}
			return instance;
		}
	}

}
