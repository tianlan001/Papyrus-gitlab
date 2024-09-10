/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import java.util.AbstractList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ISemanticChildrenStrategy.VisuallyNestedElements;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;

/**
 * Default implementation of the {@link VisuallyNestedElements} protocol.
 */
class VisuallyNestedElementsImpl extends AbstractList<EObject>implements VisuallyNestedElements {

	private final List<EObject> delegate;

	private VisuallyNestedElements nested;

	VisuallyNestedElementsImpl(List<EObject> delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public boolean add(EObject e) {
		throw new UnsupportedOperationException("add"); //$NON-NLS-1$
	}

	@Override
	public EObject get(int index) {
		return delegate.get(index);
	}

	@Override
	public EObject set(int index, EObject element) {
		throw new UnsupportedOperationException("set"); //$NON-NLS-1$
	}

	@Override
	public void add(int index, EObject element) {
		throw new UnsupportedOperationException("add"); //$NON-NLS-1$
	}

	@Override
	public EObject remove(int index) {
		throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public EObject top() {
		return get(0);
	}

	@Override
	public VisuallyNestedElements nested() {
		if ((nested == null) && (size() > 1)) {
			nested = new VisuallyNestedElementsImpl(delegate.subList(1, size()));
		}

		return nested;
	}

	static IGraphicalEditPart resolveEditPart(Object connectionEnd, IGraphicalEditPart context) {
		IGraphicalEditPart result = null;
		EObject contextElement = context.resolveSemanticElement();
		IGraphicalEditPart search = DiagramEditPartsUtil.getDiagramEditPart(context);

		if (connectionEnd instanceof VisuallyNestedElements) {
			VisuallyNestedElements stack = (VisuallyNestedElements) connectionEnd;

			for (EObject next : stack) {
				result = DiagramEditPartsUtil.getChildByEObject(next, search, false);
				if (result != null) {
					search = result;
				} else {
					break;
				}
			}
		} else {
			EObject eEnd = (EObject) connectionEnd;
			if (eEnd == contextElement) {
				result = context.getTopGraphicEditPart();
			} else {
				result = DiagramEditPartsUtil.getChildByEObject(eEnd, search, false);
			}
		}

		return result;
	}


}
