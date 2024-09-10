/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
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
package org.eclipse.papyrus.uml.nattable.manager.cell;

import java.util.List;
import java.util.Map;

import org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.papyrus.uml.tools.utils.UMLStringValueConverter;
import org.eclipse.uml2.uml.Element;

/**
 *
 * @author Vincent Lorenzo
 *         This cell manager allows to manage UML Feature
 */
public class UMLFeatureCellManager extends EMFFeatureValueCellManager {


	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public boolean handles(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		boolean answer = super.handles(columnElement, rowElement, tableManager);
		if (answer) {
			final List<Object> objects = organizeAndResolvedObjects(columnElement, rowElement, null);
			answer = objects.get(0) instanceof Element;
		}
		return answer;
	}

	/**
	 *
	 * @param existingConverters
	 * @param multiValueSeparator
	 * @param tableManager
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getOrCreateStringValueConverterClass(java.util.Map, java.lang.String, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @return
	 */
	@Override
	public AbstractStringValueConverter getOrCreateStringValueConverterClass(Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters, String multiValueSeparator, INattableModelManager tableManager) {
		AbstractStringValueConverter converter = existingConverters.get(UMLStringValueConverter.class);
		if (converter == null) {
			converter = new UMLStringValueConverter(tableManager.getTable().getContext(), multiValueSeparator);
			existingConverters.put(UMLStringValueConverter.class, converter);
		}
		return converter;
	}
}
