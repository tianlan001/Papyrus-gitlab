/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureDomainItemProvider extends MergedArchitectureItemProvider {

	public MergedArchitectureDomainItemProvider(AdapterFactory adapterFactory, MergedArchitectureDomain owner) {
		super(adapterFactory, owner);
	}

	@Override
	protected ArchitectureDomain getADElement() {
		return (ArchitectureDomain) getValue();
	}

	@Override
	protected MergedArchitectureDomain getMergedElement() {
		return (MergedArchitectureDomain) getOwner();
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof MergedArchitectureDomain) {
			return null;
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof MergedArchitectureDomain) {
			MergedArchitectureDomain domain = (MergedArchitectureDomain) object;
			return !(domain.getStakeholders().isEmpty() && domain.getConcerns().isEmpty() && domain.getContexts().isEmpty());
		}
		return super.hasChildren(object);
	}

	@Override
	public Collection<?> getChildren(Object object) {
		if (object instanceof MergedArchitectureDomain) {
			MergedArchitectureDomain domain = (MergedArchitectureDomain) object;
			List<Object> result = new ArrayList<>();
			result.addAll(domain.getStakeholders());
			result.addAll(domain.getConcerns());
			result.addAll(domain.getContexts());
			return result;
		} else {
			return super.getChildren(object);
		}
	}

}
