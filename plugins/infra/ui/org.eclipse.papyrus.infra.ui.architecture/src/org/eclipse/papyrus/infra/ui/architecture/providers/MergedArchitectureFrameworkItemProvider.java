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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFramework;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureFramework;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureFrameworkItemProvider extends MergedArchitectureContextItemProvider {

	public MergedArchitectureFrameworkItemProvider(AdapterFactory adapterFactory, MergedArchitectureFramework owner) {
		super(adapterFactory, owner);
	}

	@Override
	protected ArchitectureFramework getADElement() {
		return (ArchitectureFramework) getValue();
	}

	@Override
	protected MergedArchitectureFramework getMergedElement() {
		return (MergedArchitectureFramework) getOwner();
	}

}
