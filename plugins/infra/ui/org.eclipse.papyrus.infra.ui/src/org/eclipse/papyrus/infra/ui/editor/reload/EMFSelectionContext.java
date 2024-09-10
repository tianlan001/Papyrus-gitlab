/*
 * Copyright (c) 2014 CEA and others.
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
 *
 */
package org.eclipse.papyrus.infra.ui.editor.reload;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelectionProvider;


/**
 * A convenient selection re-load context for UIs that present EMF-based content.
 * 
 * @since 1.2
 */
public class EMFSelectionContext extends SelectionContext<ISelectionProvider, URI> implements IAdaptable {

	private IInternalEMFSelectionContext emfContext;

	public EMFSelectionContext(ISelectionProvider structuredSelectionProvider) {
		super(structuredSelectionProvider);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return (adapter == IInternalEMFSelectionContext.class) ? getEMFContext() : null;
	}

	final IInternalEMFSelectionContext getEMFContext() {
		if (emfContext == null) {
			emfContext = new IInternalEMFSelectionContext.Default();
		}
		return emfContext;
	}

	@Override
	protected URI getToken(Object object) {
		return getEMFContext().getToken(object);
	}

	@Override
	protected Object resolveToken(URI token) {
		return getEMFContext().resolveToken(token);
	}
}
