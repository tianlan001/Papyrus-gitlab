/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder;

/**
 * Enumeration of the kinds of builders that can be contributed to the {@link PapyrusPluginBuilder}.
 * These can, to some degree, be separately enabled/controlled by user preferences.
 */
public enum PapyrusBuilderKind {
	/** A (validation) builder of tooling model resources. */
	MODEL_RESOURCE,
	/** A (validation) builder of bundle manifests, including the <tt>MANIFEST.MF</tt> file and <tt>build.properties</tt>. */
	BUNDLE_MANIFEST,
	/** A (validation) builder of the Eclipse plugin manifest, the <tt>plugin.xml</tt> file. */
	PLUGIN_MANIFEST;
}
