/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.dev.project.management.handlers.plugins;

import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.dev.project.management.internal.operations.UpdateDependencyRangesOperation;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;

/**
 * Handler for the "update dependency ranges" command, which updates all dependencies
 * in the selected <tt.MANIFEST.MF</tt> file(s) to be version ranges lower-bounded
 * by the current PDE Target version, except for certain 3rd-party Orbit bundles
 * (such as Guava, ICU4J) that are known to increase major versions frequently without
 * breaking compatibility.
 * 
 * @since 1.2
 */
public class UpdateDependencyRangesHandler extends AbstractManifestUpdateHandler {

	public UpdateDependencyRangesHandler() {
		super();
	}

	@Override
	protected IUndoableOperation createUpdateOperation(Map<? extends IFile, ? extends IManifestEditor> manifests) {
		return new UpdateDependencyRangesOperation(manifests);
	}
}
