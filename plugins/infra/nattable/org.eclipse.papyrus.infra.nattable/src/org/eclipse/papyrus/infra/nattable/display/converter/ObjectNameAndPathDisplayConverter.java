/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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

package org.eclipse.papyrus.infra.nattable.display.converter;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.services.labelprovider.service.IQualifierLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * 
 * Only the method {@link DisplayConverter#canonicalToDisplayValue(Object)} is implemented by this converter
 * It returns this kind of string : "name - path"
 */
public class ObjectNameAndPathDisplayConverter extends DisplayConverter {

	/**
	 * the label provider service
	 */
	private LabelProviderService serv;

	private static final String NAME_AND_PATH_SEPARATOR = " - "; //$NON-NLS-1$

	private static final String MULTI_VALUE_SEPARATOR = ","; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 * 
	 * @param service
	 *            the label provider service to use
	 */
	public ObjectNameAndPathDisplayConverter(LabelProviderService service) {
		this.serv = service;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 *
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public Object canonicalToDisplayValue(Object canonicalValue) {
		if (canonicalValue == null) {
			return null;
		}
		return getText(canonicalValue);
	}

	private String getText(Object object) {
		if (object == null) {
			return ""; //$NON-NLS-1$
		}
		StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			Collection<?> coll = (Collection<?>) object;
			Iterator<?> iter = coll.iterator();
			while (iter.hasNext()) {
				Object next = iter.next();
				String txt = getText(next);
				if (txt != null && !txt.isEmpty()) {
					buffer.append(txt);
					if (iter.hasNext()) {
						buffer.append(MULTI_VALUE_SEPARATOR);
						buffer.append(" "); //$NON-NLS-1$
					}
				}
			}
			return buffer.toString();
		}
		final ILabelProvider provider = serv.getLabelProvider(Constants.BODY_LABEL_PROVIDER_CONTEXT, object);
		if (provider instanceof IQualifierLabelProvider) {
			String txt = provider.getText(object);
			if (txt != null && !txt.isEmpty()) {
				buffer.append(txt);
				String path = ((IQualifierLabelProvider) provider).getQualifierText(object);
				if (path != null && !path.isEmpty()) {
					buffer.append(NAME_AND_PATH_SEPARATOR);
					buffer.append(path);
				}
			}

		} else {
			buffer.append(serv.getLabelProvider(object).getText(object));
		}
		return buffer.toString();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#displayToCanonicalValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToCanonicalValue(Object displayValue) {
		throw new UnsupportedOperationException();

	}
}