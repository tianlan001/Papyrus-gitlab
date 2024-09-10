/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 474467
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototypeLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 *
 * This labelprovider provides icon and text for tables to display them in treeviewer AND in the property view
 *
 */
public class TableLabelProvider extends ViewPrototypeLabelProvider implements IFilteredLabelProvider {

	@Override
	public boolean accept(Object object) {
		if (object instanceof IStructuredSelection) {
			return accept((IStructuredSelection) object);
		}

		boolean result = EMFHelper.getEObject(object) instanceof Table;
		return result;
	}

	/**
	 *
	 * @param selection
	 *            a selection
	 * @return
	 * 		<code>true</code> if all elements in the selection are accepted
	 */
	protected boolean accept(final IStructuredSelection selection) {
		for (final Object current : selection.toList()) {
			if (!accept(current)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototypeLabelProvider#getName(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected String getName(final EObject object) {
		String value = null;
		if(object instanceof Table){
			value = LabelInternationalization.getInstance().getTableLabel((Table)object);
		}
		return null != value ? value : super.getName(object);
	}

	@Override
	protected Image getNonCommonIcon(final Object commonObject) {
		return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrus.infra.nattable.Activator.PLUGIN_ID, "/icons/table.gif");
	}

}
