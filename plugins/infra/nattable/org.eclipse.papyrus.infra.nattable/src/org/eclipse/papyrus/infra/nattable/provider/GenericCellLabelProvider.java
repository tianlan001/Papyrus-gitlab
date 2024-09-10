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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bugs 458260, 481366
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * The generic label provider for the cells
 *
 * @author Vincent Lorenzo
 *
 */
public class GenericCellLabelProvider extends AbstractNattableCellLabelProvider {

	/**
	 * Limit the number of displayed elements for collections
	 */
	public static final int MAX_DISPLAYED_ELEMENTS = 10;

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 *         <code>true</code> if the element is an instance of {@link ILabelProviderContextElementWrapper} and if the cell represents an {@link EStructuralFeature} of an {@link EObject}
	 */
	@Override
	public boolean accept(Object element) {
		return element instanceof ILabelProviderCellContextElementWrapper;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		final ILabelProviderCellContextElementWrapper contextElement = (ILabelProviderCellContextElementWrapper) element;
		final IConfigRegistry registry = ((ILabelProviderContextElementWrapper) element).getConfigRegistry();
		final Object value = contextElement.getObject();
		final LabelProviderService service = getLabelProviderService(registry);
		return getText(service, value);
	}
	
	/**
	 * Get the text for the value.
	 * 
	 * @param service The label provider service.
	 * @param value The value to parse.
	 * @return The string representing the object.
	 */
	protected String getText(LabelProviderService service, Object value){
		final StringBuffer label = new StringBuffer();
		if (value instanceof Collection<?>) {
			label.append(getCollectionText(service, (Collection<?>)value));
		} else {
			label.append(service.getLabelProvider(value).getText(value));
		}
		return label.toString();
	}
	
	/**
	 * This allow to manage if the hooks of collection will be displayed or not.
	 * 
	 * @return <code>false</code>
	 */
	private boolean isDisplayCollectionHooks(){
		// This allow to manage if the hooks of collection will be displayed or not
		// This will be managed by preference later
		// Ignore the preference when this is an export to XLS action
		return false;
	}
	
	/**
	 * This allow to manage if the big collection display will be added.
	 * 
	 * @return <code>false</code>
	 */
	private boolean isDisplayBigCollection(){
		// This allow to manage if the big collection display will be added or not
		// This will be managed by preference later
		// Ignore the preference when this is an export to XLS action
		return false;
	}
	
	/**
	 * Get the text for the collection object.
	 * 
	 * @param service The label provider service.
	 * @param value The value to parse.
	 * @return The string representing the object.
	 */
	protected String getCollectionText(LabelProviderService service, Collection<?> value){
		final StringBuilder label = new StringBuilder();
		Iterator<?> iter = ((Collection<?>) value).iterator();
		if(isDisplayCollectionHooks())
			label.append(Constants.BEGIN_OF_COLLECTION);
		int i = 1;
		while (iter.hasNext()) {
			if (i > MAX_DISPLAYED_ELEMENTS) {
				if(isDisplayBigCollection()){
					label.append(Constants.BIG_COLLECTION);
					break;
				}
			}

			Object current = iter.next();
			label.append(getElementText(service, current));
			if (iter.hasNext()) {
				label.append(Constants.SEPARATOR);
			}
			i++;
		}

		if(isDisplayCollectionHooks())
			label.append(Constants.END_OF_COLLECTION);
		return label.toString();
	}
	
	/**
	 * Get the text for the element.
	 * 
	 * @param service The label provider service.
	 * @param value The value to parse.
	 * @return The string representing the object.
	 */
	protected String getElementText(LabelProviderService service, Object value){
		return service.getLabelProvider(value).getText(value);
	}
}
