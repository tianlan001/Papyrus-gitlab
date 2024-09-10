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

package org.eclipse.papyrus.infra.gmfdiag.gef.internal.editor;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.papyrus.infra.ui.editor.CoreMultiDiagramEditor;

/**
 * Adapter factory for GEF-specific adapters of the {@code CoreMultiDiagramEditor}.
 */
public class CoreMultiDiagramEditorAdapterFactory implements IAdapterFactory {
	private final Class<?>[] adapterTypes = { ActionRegistry.class };

	public CoreMultiDiagramEditorAdapterFactory() {
		super();
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		T result = null;

		if (adapterType == ActionRegistry.class) {
			if (adaptableObject instanceof CoreMultiDiagramEditor) {
				result = adapterType.cast(MultiDiagramEditorGefDelegate.getInstance(
						(CoreMultiDiagramEditor) adaptableObject).get());
			}
		}

		return result;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return adapterTypes;
	}

}
