/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte,
 * Generalitat de la Comunitat Valenciana .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano MuÃ±oz (Prodevelop) - initial API implementation
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


/**
 * Basic content provider for a ViewInfo.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 */
public class BaseViewInfoContentProvider implements ITreeContentProvider {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ViewInfo) {
			ViewInfo viewInfo = (ViewInfo) parentElement;
			return viewInfo.getChildren().toArray();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof ViewInfo) {
			ViewInfo viewInfo = (ViewInfo) element;
			return viewInfo.getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ViewInfo) {
			ViewInfo viewInfo = (ViewInfo) element;
			return viewInfo.getChildren().size() > 0;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}

}
