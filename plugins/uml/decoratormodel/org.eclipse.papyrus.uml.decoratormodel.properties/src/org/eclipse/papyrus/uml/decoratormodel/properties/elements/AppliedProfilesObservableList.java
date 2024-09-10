/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Sebastien Poissonnet (CEA LIST) sebastien.poissonnet@cea.fr
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - bug 435174
 *  Gabriel Pascual (ALL4TEC)  -  Bug 441511
 *  Christian W. Damus (CEA) - Bug 441227
 *  Christian W. Damus - bug 399859
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.decoratormodel.properties.elements;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.ResourceAdapter;
import org.eclipse.papyrus.uml.properties.databinding.ProfileApplicationObservableList;
import org.eclipse.uml2.uml.Package;

/**
 * Observable list for applied profiles.
 */
public class AppliedProfilesObservableList extends ProfileApplicationObservableList {

	private ResourceAdapter adapter;

	public AppliedProfilesObservableList(Package source, EditingDomain domain) {
		super(source, domain);

		adapter = createResourceAdapter();
		domain.getResourceSet().eAdapters().add(adapter);
	}

	@Override
	public synchronized void dispose() {
		if (adapter.getTarget() != null) {
			adapter.getTarget().eAdapters().remove(adapter);
		}

		super.dispose();
	}

	protected final Package getPackage() {
		return (Package) getObserved();
	}

	private ResourceAdapter createResourceAdapter() {
		return new ResourceAdapter() {
			@Override
			protected void handleResourceLoaded(Resource resource) {
				refreshCacheList();
			}

			@Override
			protected void handleResourceUnloaded(Resource resource) {
				refreshCacheList();
			}
		};
	}
}
