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
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureDescriptionLanguageItemProvider extends MergedArchitectureContextItemProvider {

	public MergedArchitectureDescriptionLanguageItemProvider(AdapterFactory adapterFactory, MergedArchitectureDescriptionLanguage owner) {
		super(adapterFactory, owner);
	}

	@Override
	protected ArchitectureDescriptionLanguage getADElement() {
		return (ArchitectureDescriptionLanguage) getValue();
	}

	@Override
	protected MergedArchitectureDescriptionLanguage getMergedElement() {
		return (MergedArchitectureDescriptionLanguage) getOwner();
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof ArchitectureDescriptionLanguage) {
			ArchitectureDescriptionLanguage adl = (ArchitectureDescriptionLanguage) object;
			return super.hasChildren(object) || !adl.getRepresentationKinds().isEmpty();
		}
		return super.hasChildren(object);
	}

	@Override
	public Collection<?> getChildren(Object object) {
		if (object instanceof ArchitectureDescriptionLanguage) {
			ArchitectureDescriptionLanguage adl = (ArchitectureDescriptionLanguage) object;
			List<Object> result = new ArrayList<>(super.getChildren(adl));
			result.addAll(adl.getRepresentationKinds());
			return result;
		} else {
			return super.getChildren(object);
		}
	}

}
