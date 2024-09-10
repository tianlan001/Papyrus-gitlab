/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.edit.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescription;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModelUtils;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.internal.context.DefaultTypeContext;
import org.eclipse.papyrus.infra.services.edit.messages.Messages;

/**
 * <pre>
 * An API that allows getting a GMF client context that corresponds to an architecture context
 * that is applied to a Papyrus model set or one of its owned objects.
 * </pre>
 * @since 3.0
 */
public class TypeContext {

	/**
	 * Obtains the default Papyrus GMF client context
	 * 
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getDefaultContext() throws ServiceException {
		return DefaultTypeContext.getContext();
	}

	/**
	 * Obtains the default Papyrus GMF client context id
	 * 
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static String getDefaultContextId() {
		return DefaultTypeContext.ID;
	}

	/**
	 * Obtains the GMF client context that corresponds to the given model set
	 * 
	 * @param modelSet
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(ModelSet modelSet) throws ServiceException {
		ArchitectureDescription desc = DiModelUtils.getArchitectureDescription(modelSet);
		if (desc != null)
			return getContext(desc.getContextId());
		return getDefaultContext();
	}

	/**
	 * Obtains the GMF client context that corresponds to the given resource set
	 * 
	 * @param resourceSet
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(ResourceSet resoureSet) throws ServiceException {
		if (resoureSet instanceof ModelSet)
			return getContext((ModelSet)resoureSet);
		return getDefaultContext();
	}

	/**
	 * Obtains the GMF client context that corresponds to the given editing domain
	 * 
	 * @param editingDomain
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(EditingDomain editingDomain) throws ServiceException {
		if (editingDomain.getResourceSet() != null)
			return getContext(editingDomain.getResourceSet());
		return getDefaultContext();
	}

	/**
	 * Obtains the GMF client context that corresponds to the given resource
	 * 
	 * @param resource
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(Resource resource) throws ServiceException {
		if (resource.getResourceSet() != null)
			return getContext(resource.getResourceSet());
		return getDefaultContext();
	}

	/**
	 * Obtains the GMF client context that corresponds to the given object
	 * 
	 * @param object
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(EObject object) throws ServiceException {
		if (object.eResource() != null)
			return getContext(object.eResource());
		return getDefaultContext();
	}

	/**
	 * Obtains the GMF client context that corresponds to the given object
	 * 
	 * @param object
	 * @return IClientContext
	 * @throws ServiceException
	 */
	public static IClientContext getContext(String contextId) throws ServiceException {
		IClientContext context = ClientContextManager.getInstance().getClientContext(contextId);
		if (context == null) {
			throw new ServiceException(NLS.bind(Messages.TypeContext_ContextNotFound, contextId));
		}
		return context;
	}
}
