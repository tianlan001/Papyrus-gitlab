/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy;
import org.eclipse.swt.graphics.Image;

/**
 * The label provider to use for the enumeration used to define the relationship direction
 * @since 6.0
 * 
 */
public class MatrixRelationshipOwnerStrategyLabelProvider implements ILabelProvider {

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {

	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 *
	 * @param element
	 * @param property
	 * @return
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param direction
	 * @return
	 */
	@Override
	public String getText(Object direction) {
		MatrixRelationShipOwnerStrategy instance = null;
		if (direction instanceof MatrixRelationShipOwnerStrategy) {
			instance = (MatrixRelationShipOwnerStrategy) direction;
		}
		String label = ""; //$NON-NLS-1$
		if (null != instance) {
			switch (instance.getValue()) {
			case MatrixRelationShipOwnerStrategy.DEFAULT_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_Default;
				break;
			case MatrixRelationShipOwnerStrategy.ROW_AS_OWNER_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_RowAsOwner;
				break;
			case MatrixRelationShipOwnerStrategy.ROW_OWNER_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_RowsOwnerAsOwner;
				break;
			case MatrixRelationShipOwnerStrategy.COLUMN_AS_OWNER_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_ColumAsOwner;
				break;
			case MatrixRelationShipOwnerStrategy.COLUMN_OWNER_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_ColumnsOwnerAsOwner;
				break;
			case MatrixRelationShipOwnerStrategy.TABLE_CONTEXT_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_TableRootElement;
				break;
			case MatrixRelationShipOwnerStrategy.OTHER_VALUE:
				label = Messages.MatrixRelationshipOwnerStrategyLabelProvider_Other;
				break;
			default:
				label = instance.getName();
			}
		}
		return label;
	}

}
