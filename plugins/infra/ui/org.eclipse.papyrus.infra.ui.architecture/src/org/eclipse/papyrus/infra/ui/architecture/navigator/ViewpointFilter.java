/**
 * Copyright (c) 2017, 2019 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  FAUVERGUE Nicolas (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550567
 *
 */
package org.eclipse.papyrus.infra.ui.architecture.navigator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.widgets.providers.AbstractTreeFilter;

/**
 * A tree filter for representations (diagrams, tables) based on active viewpoints in a model set
 *
 * @since 1.0
 * @deprecated since 2.1
 */
@Deprecated
public class ViewpointFilter extends AbstractTreeFilter {

	public ViewpointFilter() {
		super();
		useCache = false;// don't cache
	}

	/**
	 * hide a representation when it's not supported by the active viewpoints
	 */
	@Override
	public boolean isVisible(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof EObjectTreeElement) {
			EObject eObj = ((EObjectTreeElement) element).getEObject();
			if (eObj instanceof Diagram && DiagramVersioningUtils.isOfCurrentPapyrusVersion((Diagram) eObj)) {
				return ViewPrototype.get(eObj) != ViewPrototype.UNAVAILABLE_VIEW;
			} else if (eObj instanceof Table && TableVersioningUtils.isOfCurrentPapyrusVersion((Table) eObj)) {
				return ViewPrototype.get(eObj) != ViewPrototype.UNAVAILABLE_VIEW;
			}
		}
		return true;
	}
}
