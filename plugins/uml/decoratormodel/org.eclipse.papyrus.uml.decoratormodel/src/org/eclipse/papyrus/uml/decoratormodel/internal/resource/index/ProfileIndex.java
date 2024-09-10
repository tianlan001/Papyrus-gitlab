/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.resource.index;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.uml.decoratormodel.internal.resource.DecoratorModelIndex;
import org.eclipse.papyrus.uml.tools.profile.index.IProfileIndex;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Implementation of the profile index provider service API that uses our workspace model index.
 */
public class ProfileIndex implements IProfileIndex, IService {

	public ProfileIndex() {
		super();
	}

	@Override
	public boolean indexes(URI umlResource) {
		return umlResource.isPlatformResource();
	}

	@Override
	public ListenableFuture<Set<URI>> getAppliedProfiles(URI umlResource) {
		return DecoratorModelIndex.getInstance().getAllProfilesAppliedToPackagesAsync(umlResource);
	}

	@Override
	public Set<URI> getIntrinsicAppliedProfiles(URI umlResource) {
		return DecoratorModelIndex.getInstance().getIntrinsicAppliedProfiles(umlResource);
	}

	//
	// Service lifecycle API
	//

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// Pass
	}

	@Override
	public void startService() throws ServiceException {
		// Ensure that the index is alive and kicking
		DecoratorModelIndex.getInstance();
	}

	@Override
	public void disposeService() throws ServiceException {
		// Pass
	}

}
