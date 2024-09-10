/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.internal.sashmodel;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.IServiceFactory;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * Factory for the headless {@link IPageManager} service.
 */
public class BasicPageManagerServiceFactory implements IServiceFactory {

	private ModelSet modelSet;

	public BasicPageManagerServiceFactory() {
		super();
	}

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.modelSet = servicesRegistry.getService(ModelSet.class);
	}

	@Override
	public void startService() {
		// Pass
	}

	@Override
	public void disposeService() {
		// Pass
	}

	@Override
	public Object createServiceInstance() {
		EditingDomain domain = TransactionUtil.getEditingDomain(modelSet);
		IPageManager result;

		SashWindowsMngr diSashModel = SashModelUtils.getSashWindowsMngr(modelSet);

		if (domain != null) {
			result = new TransactionalBasicPageManagerImpl(diSashModel, domain);
		} else {
			result = new BasicPageManagerImpl(diSashModel);
		}

		return result;
	}

}
