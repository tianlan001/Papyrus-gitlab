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

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;


/**
 * Basic label provider for a ViewInfo.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 */
public class BaseViewInfoLabelProvider implements IBaseLabelProvider, ILabelProvider {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof ViewInfo) {
			ViewInfo viewInfo = (ViewInfo) element;
			return getLabelType(viewInfo) + " : " + viewInfo.getLabel() + " - " + viewInfo.getVisualID();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers. ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers. ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		// nothing to do
	}

	/**
	 * Gets the label type.
	 *
	 * @param info
	 *            the info
	 *
	 * @return the label type
	 */
	protected String getLabelType(ViewInfo info) {
		switch (info.getType()) {
		case ViewInfo.None:
			return "Unknown";
		case ViewInfo.Head:
			return "Diagram";
		case ViewInfo.Node:
			return "Node";
		case ViewInfo.Edge:
			return "Edge";
		case ViewInfo.Label:
			return "Label";
		default:
			return "Unknown";
		}
	}

}
