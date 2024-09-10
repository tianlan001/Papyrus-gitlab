/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.views.properties.toolsmiths.providers.AbstractContextualContentProvider;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;

/**
 * A Content provider to retrieve all available properties in the current
 * contexts
 *
 * @author Camille Letavernier
 */
public class PropertyContentProvider extends AbstractContextualContentProvider implements IHierarchicContentProvider {

	/**
	 * Constructor.
	 *
	 * @param source
	 *            The source from which the contexts will be retrieved
	 */
	public PropertyContentProvider(EObject source) {
		super(source);
	}

	@Override
	public Object[] getElements() {
		return contexts.toArray();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Context) {
			Context parent = (Context) parentElement;
			return parent.getDataContexts().toArray();
		} else if (parentElement instanceof DataContextElement) {
			List result = new LinkedList();
			if (parentElement instanceof DataContextPackage) {
				DataContextPackage contextPackage = (DataContextPackage) parentElement;
				result.addAll(contextPackage.getElements());
			}
			DataContextElement element = (DataContextElement) parentElement;
			result.addAll(element.getProperties());
			Iterator<?> it = result.iterator();
			while (it.hasNext()) {
				Object value = it.next();
				if (isEmpty(value)) {
					it.remove();
				}
			}
			return result.toArray();
		} else {
			return new Object[0];
		}
	}

	protected boolean isEmpty(Object element) {
		if (element instanceof DataContextPackage) {
			DataContextPackage dcPackage = (DataContextPackage) element;
			return dcPackage.getElements().isEmpty() && dcPackage.getProperties().isEmpty();
		} else if (element instanceof DataContextElement) {
			return ((DataContextElement) element).getProperties().isEmpty();
		} else if (element instanceof Context) {
			return ((Context) element).getDataContexts().isEmpty();
		}

		return false;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Property) {
			return ((Property) element).getContextElement();
		} else if (element instanceof DataContextElement) {
			return ((DataContextElement) element).getPackage();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public boolean isValidValue(Object element) {
		return element instanceof Property;
	}

}
