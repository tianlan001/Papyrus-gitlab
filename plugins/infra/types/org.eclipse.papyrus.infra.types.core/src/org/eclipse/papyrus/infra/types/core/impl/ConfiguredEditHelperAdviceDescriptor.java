/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 570542
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.impl;

import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingInheritance;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredEditHelperAdvice;
import org.eclipse.papyrus.infra.types.core.IConfiguredEditHelperAdviceDescriptor;

public class ConfiguredEditHelperAdviceDescriptor<T extends AdviceConfiguration> implements IConfiguredEditHelperAdviceDescriptor<T> {

	protected T editHelperAdviceConfiguration;

	protected String id;

	protected String typeId;

	protected IElementMatcher matcher;

	protected IContainerDescriptor containerDescriptor;

	protected IEditHelperAdvice editHelperAdvice;

	protected AdviceBindingInheritance inheritance;

	public ConfiguredEditHelperAdviceDescriptor(String id, String typeId, IElementMatcher matcher, IContainerDescriptor containerDescriptor, IEditHelperAdvice editHelperAdvice, AdviceBindingInheritance inheritance) {
		this.id = id;
		this.typeId = typeId;
		this.matcher = matcher;
		this.containerDescriptor = containerDescriptor;
		this.editHelperAdvice = editHelperAdvice;
		this.inheritance = inheritance;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getTypeId() {
		return this.typeId;
	}

	@Override
	public IElementMatcher getMatcher() {
		return this.matcher;
	}

	@Override
	public IContainerDescriptor getContainerDescriptor() {
		return this.containerDescriptor;
	}

	@Override
	public IEditHelperAdvice getEditHelperAdvice() {
		return this.editHelperAdvice;
	}

	@Override
	public AdviceBindingInheritance getInheritance() {
		return this.inheritance;
	}

	@Override
	public void init(T editHelperAdviceConfiguration) {
		this.editHelperAdviceConfiguration = editHelperAdviceConfiguration;

		if (editHelperAdvice instanceof IConfiguredEditHelperAdvice) {
			@SuppressWarnings("unchecked")
			IConfiguredEditHelperAdvice<? super T> configuredAdvice = (IConfiguredEditHelperAdvice<? super T>) editHelperAdvice;
			configuredAdvice.init(editHelperAdviceConfiguration);
		}
	}
}
