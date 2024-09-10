/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.services.edit.Activator;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.internal.ElementEditServiceProvider;
import org.eclipse.papyrus.infra.services.edit.messages.Messages;
import org.eclipse.papyrus.infra.services.edit.utils.IRequestCacheEntries;

/**
 * <pre>
 *
 * Utility class for a convenient access to edit services.
 *
 * </pre>
 */
public class ElementEditServiceUtils {

	/**
	 * <pre>
	 * Try to retrieve an edit service for the object in parameter
	 * (EObject or EClass, or IElementType expected) in a given context
	 *
	 * Current implementation directly use {@link IElementEditServiceProvider} instance
	 * rather than using Papyrus {@link ServiceUtils} which requires Papyrus to be
	 * the active editor.
	 *
	 * @param objectToEdit
	 * @param context
	 * @return the edit service or null
	 * </pre>
	 */
	public static IElementEditService getCommandProvider(Object objectToEdit, IClientContext context) {
		try {
			IElementEditServiceProvider provider = getEditServiceProvider(context);
			if (provider != null)
				return provider.getEditService(objectToEdit);
		} catch (ServiceException e) {
			Activator.log.error(NLS.bind(Messages.ElementEditServiceUtils_UnableToFindElementType, objectToEdit), e);
		}
		return null;

		// ServicesRegistry serviceRegistry = EditorUtils.getServiceRegistry();
		// try {
		// IElementEditServiceProvider serviceProvider = serviceRegistry.getService(IElementEditServiceProvider.class);
		// IElementEditService service = serviceProvider.getEditService(objectToEdit);
		// return serviceRegistry == null ? null : service;
		// } catch (ServiceException e) {
		// return null;
		// }
	}

	/**
	 * <pre>
	 * Try to retrieve an edit service for the EObject in parameter assuming the context
	 * of the model set that the object belongs to
	 *
	 * @param objectToEdit
	 * @return the edit service or null
	 * </pre>
	 */
	public static IElementEditService getCommandProvider(EObject objectToEdit) {
		IClientContext context;
		try {
			context = TypeContext.getContext(objectToEdit);
			return getCommandProvider(objectToEdit, context);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}
	
	/**
	 * <pre>
	 * Get the edit service provider (using {@link ElementTypeRegistry} instead of
	 * {@link ServiceUtils}). Note that {@link ServiceUtils} would return the same instance
	 * anyway.
	 *
	 * @return the service provider
	 * </pre>
	 */
	public static IElementEditServiceProvider getEditServiceProvider(IClientContext context) {

		return new ElementEditServiceProvider(context);

		// ServicesRegistry serviceRegistry = EditorUtils.getServiceRegistry();
		// try {
		// IElementEditServiceProvider serviceProvider = serviceRegistry.getService(IElementEditServiceProvider.class);
		// return serviceRegistry == null ? null : serviceProvider;
		// } catch (ServiceException e) {
		// return null;
		// }
	}

	/**
	 * Return a target after executing a passed context request
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param owner
	 *            the parent object in which we want to create an element
	 * @param hintedType
	 *            The information which element to create.
	 * @return the resulting target object or null in case of a failure
	 */
	public static EObject getTargetFromContext(TransactionalEditingDomain editingDomain, EObject owner, IEditCommandRequest editRequest) {
		GetEditContextRequest editContextRequest = new GetEditContextRequest(editingDomain, editRequest, owner);

		editContextRequest.setParameter(IRequestCacheEntries.Cache_Maps, new HashMap<Object, Object>());
		editContextRequest.setEditContext(owner);
		try {
			editContextRequest.setClientContext(TypeContext.getContext(editingDomain));
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(owner);
		if (provider == null) {
			Activator.log.debug(Messages.ElementEditServiceUtils_CantGetEditingDomainProvider);
			return null;
		}

		EObject target = owner;
		Object result = null;
		final ICommand getEditContextCommand = provider.getEditCommand(editContextRequest);
		if (getEditContextCommand != null) {
			IStatus status = null;
			try {
				// this command could run in an unprotected transaction, it is not supposed to modify the model
				InternalTransactionalEditingDomain domain = (InternalTransactionalEditingDomain) editingDomain;
				Map<String, Object> options = new HashMap<String, Object>();
				options.put(Transaction.OPTION_NO_NOTIFICATIONS, true);
				options.put(Transaction.OPTION_NO_VALIDATION, true);
				options.put(Transaction.OPTION_NO_TRIGGERS, true);
				Transaction transaction = domain.startTransaction(false, options);
				try {
					status = getEditContextCommand.execute(null, null);
				} finally {
					transaction.commit();
				}
			} catch (InterruptedException e) {
				Activator.log.error(e);
			} catch (ExecutionException e) {
				Activator.log.error(e);
			} catch (RollbackException e) {
				Activator.log.error(e);
			}
			if (status != null && status.isOK()) {
				result = getEditContextCommand.getCommandResult().getReturnValue();
			}
			if (result instanceof EObject) {
				target = (EObject) result;
			}
		}
		return target;
	}

	/**
	 * Convenience function that determines editing domain automatically.
	 * 
	 * @see {@link #getTargetFromContext(TransactionalEditingDomain, EObject, IEditCommandRequest)}
	 */
	public static EObject getTargetFromContext(EObject owner, IEditCommandRequest editRequest) {
		return getTargetFromContext(TransactionUtil.getEditingDomain(owner), owner, editRequest);

	}

	/**
	 * Support creation of a child within another object with a (default) context
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param owner
	 *            the parent object in which we want to create an element
	 * @param hintedType
	 *            The information which element to create.
	 * @return a GMF command that will create child
	 */
	public static ICommand getCreateChildCommandWithContext(TransactionalEditingDomain editingDomain, EObject owner, IHintedType hintedType) {
		TransactionalEditingDomain transactionalEditingDomain = TransactionUtil.getEditingDomain(owner);
		final EObject target = getTargetFromContext(transactionalEditingDomain, owner,
				new CreateElementRequest(transactionalEditingDomain, owner, hintedType));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
		}

		ICommand createGMFCommand = provider.getEditCommand(new CreateElementRequest(transactionalEditingDomain, target, hintedType));
		return createGMFCommand;
	}

	/**
	 * Convenience function that determines editing domain automatically
	 * 
	 * @see {@link #getCreateChildCommandWithContext(TransactionalEditingDomain, EObject, IHintedType)}
	 */
	public static ICommand getCreateChildCommandWithContext(EObject owner, IHintedType hintedType) {
		return getCreateChildCommandWithContext(TransactionUtil.getEditingDomain(owner), owner, hintedType);
	}
}
