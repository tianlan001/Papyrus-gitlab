/*****************************************************************************
 * Copyright (c) 2013, 2019 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 545575 (add Cloneable implementation)
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.converter;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.messages.Messages;

/**
 * Abstract class for String value Container
 *
 * @author Vincent Lorenzo
 * @since 1.2
 *
 */
public abstract class AbstractStringValueConverter implements IStringValueConverter, Cloneable {

	protected static final String THE_STRING_X_IS_NOT_VALID_TO_CREATE_Y = Messages.AbstractStringValueConverter_TheStringXIsNotValidToCreateY;

	protected static final String THE_FEATURE_X_CANT_BE_RESOLVED = Messages.AbstractStringValueConverter_TheFeatureXCantBeResolved;

	protected static final String THE_STRING_VALUE_X_CANT_BE_RESOLVED = Messages.AbstractStringValueConverter_TheStringValueXCantBeResolved;

	protected static final String SOME_STRING_ARE_NOT_VALID_TO_CREATE_X = Messages.AbstractStringValueConverter_SomeStringsAreNotValidToCreateY;

	protected static final String SOME_STRING_CANT_BE_RESOLVED_TO_FIND_X = Messages.AbstractStringValueConverter_SomeStringsCantBeResolvedToFindY;

	protected static final String NO_X_REPRESENTED_BY_Y_HAVE_BEEN_FOUND = Messages.AbstractStringValueConverter_NoXReprensentedByYHaveBeenFound;

	private ConvertedValueContainer<?> result;

	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.converter.IStringValueConverter#deduceValueFromString(java.lang.Object, java.lang.String)
	 *
	 * @param type
	 * @param valueAsString
	 * @return
	 */
	@Override
	public final ConvertedValueContainer<?> deduceValueFromString(final Object type, final String valueAsString) {
		result = doDeduceValueFromString(type, valueAsString);
		if (result == null) {
			final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, NLS.bind(THE_STRING_VALUE_X_CANT_BE_RESOLVED, valueAsString));
			result = new ConvertedValueContainer<Object>(null, status);
		}
		return result;
	}

	/**
	 *
	 * @return
	 * 		the converted value, you should call deduceValueFromString before to call this method
	 */
	public final ConvertedValueContainer<?> getConvertedValue() {
		if (this.result == null) {
			throw new IllegalStateException("You should call deduceValueFromString before to call this method"); //$NON-NLS-1$
		}
		return this.result;
	}

	/**
	 *
	 * @param type
	 *            the type of the object
	 * @param valueAsString
	 *            the string to resolve
	 * @return
	 * 		a {@link ConvertedValueContainer} with the resolved values and a status
	 */
	protected abstract ConvertedValueContainer<?> doDeduceValueFromString(final Object type, final String valueAsString);

	/**
	 *
	 * Added to fix easily the Bug 545575: [Table] Paste String resolution doesn't work for stereotype's properties typed with an UML metaclass
	 *
	 * @see java.lang.Object#clone()
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
