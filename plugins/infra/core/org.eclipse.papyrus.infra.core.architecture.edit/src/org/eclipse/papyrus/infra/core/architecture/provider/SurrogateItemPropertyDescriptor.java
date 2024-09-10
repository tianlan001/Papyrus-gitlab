/*****************************************************************************
 * Copyright (c) 2013, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.architecture.provider;

import java.util.Collection;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor.PropertyValueWrapper;

/**
 * Represents a surrogate property descriptor delegating to an encapsulated descriptor
 *
 * @author Laurent Wouters
 */
public abstract class SurrogateItemPropertyDescriptor implements IItemPropertyDescriptor {
	private IItemPropertyDescriptor inner;

	public SurrogateItemPropertyDescriptor(IItemPropertyDescriptor inner) {
		this.inner = inner;
	}

	@Override
	public Object getPropertyValue(Object object) {
		return inner.getPropertyValue(object);
	}

	@Override
	public boolean isPropertySet(Object object) {
		return inner.isPropertySet(object);
	}

	@Override
	public boolean canSetProperty(Object object) {
		return inner.canSetProperty(object);
	}

	@Override
	public void resetPropertyValue(Object object) {
		inner.resetPropertyValue(object);
	}

	@Override
	public void setPropertyValue(Object object, Object value) {
		inner.setPropertyValue(object, value);
	}

	@Override
	public String getCategory(Object object) {
		return inner.getCategory(object);
	}

	@Override
	public String getDescription(Object object) {
		return inner.getDescription(object);
	}

	@Override
	public String getDisplayName(Object object) {
		return inner.getDisplayName(object);
	}

	@Override
	public String[] getFilterFlags(Object object) {
		return inner.getFilterFlags(object);
	}

	@Override
	public Object getHelpContextIds(Object object) {
		return inner.getHelpContextIds(object);
	}

	@Override
	public String getId(Object object) {
		return inner.getId(object);
	}

	@Override
	public IItemLabelProvider getLabelProvider(Object object) {
		return inner.getLabelProvider(object);
	}

	@Override
	public boolean isCompatibleWith(Object object, Object anotherObject, IItemPropertyDescriptor anotherPropertyDescriptor) {
		return inner.isCompatibleWith(object, anotherObject, anotherPropertyDescriptor);
	}

	@Override
	public Object getFeature(Object object) {
		return inner.getFeature(object);
	}

	@Override
	public boolean isMany(Object object) {
		return inner.isMany(object);
	}

	@Override
	public abstract Collection<?> getChoiceOfValues(Object object);

	@Override
	public boolean isMultiLine(Object object) {
		return inner.isMultiLine(object);
	}

	@Override
	public boolean isSortChoices(Object object) {
		return inner.isSortChoices(object);
	}

	/**
	 * The Merged Architecture Domain API has an EMF.Edit implementation that uses property wrappers.
	 * This method unwraps them to get at the values.
	 *
	 * @param object
	 *            a possibly property value wrapper
	 * @return the property value
	 */
	protected Object unwrap(Object object) {
		Object result = object;
		while (result instanceof PropertyValueWrapper) {
			Object value = ((PropertyValueWrapper) result).getEditableValue(result);
			if (value == result) {
				// Self-wrapped? Give up
				break;
			}
			result = value;
		}
		return result;
	}

}
