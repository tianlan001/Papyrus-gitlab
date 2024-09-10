/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 521353
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.editor.reload;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.ui.part.EditorPart;


/**
 * An {@linkplain EditorReloadEvent#putContext(Object) editor reload context} that delegates to another reload context.
 * This should be used whenever a {@linkplain IReloadContextProvider reload context provider} is needed to get a reload
 * context to delegate to.
 * 
 * @since 1.2
 */
public class DelegatingReloadContext implements IDisposableReloadContext, IAdaptable {

	private Object delegate;

	public DelegatingReloadContext(Object reloadContextProvider) {
		super();

		IReloadContextProvider provider = AdapterUtils.adapt(reloadContextProvider, IReloadContextProvider.class, null);
		if (provider != null) {
			delegate = provider.createReloadContext();
		}
	}

	@Override
	public void dispose() {
		if (delegate instanceof IDisposableReloadContext) {
			((IDisposableReloadContext) delegate).dispose();
		}

		delegate = null;
	}

	public Object getDelegate() {
		return delegate;
	}

	public void restore(Object reloadContextProvider) {
		if (delegate != null) {
			IReloadContextProvider provider = AdapterUtils.adapt(reloadContextProvider, IReloadContextProvider.class, null);
			if (provider != null) {
				// Call setFocus to initialize the graphical viewer before restoring element of the current editor
				// This assures that the selected edit part of the current editor is properly initialized
				// Please see bug 519107 and 521353 for more details
				if (reloadContextProvider instanceof EditorPart) {
					((EditorPart) reloadContextProvider).setFocus();
				}
				provider.restore(delegate);
			}
		}
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return (adapter == IInternalEMFSelectionContext.class) ? getEMFContext() : null;
	}

	private IInternalEMFSelectionContext getEMFContext() {
		IInternalEMFSelectionContext result = null;

		if ((delegate != null) && (AdapterUtils.adapt(delegate, IInternalEMFSelectionContext.class, null) != null)) {
			// We need the adapter
			result = new IInternalEMFSelectionContext.Delegating(this);
		}

		return result;
	}
}
