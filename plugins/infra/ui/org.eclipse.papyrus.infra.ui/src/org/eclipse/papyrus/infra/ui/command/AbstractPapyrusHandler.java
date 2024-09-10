/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForIEvaluationContext;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This provides facilities to get the TransactionEditingDomain and the PageManager from
 * the current Papyrus editor or view context.
 * 
 * @since 1.2
 */
public abstract class AbstractPapyrusHandler extends AbstractHandler {

	/**
	 * Obtains the transactional editing domain associated with the Papyrus Editor or View
	 * that is the context of the specified {@code execution}.
	 * 
	 * @param execution
	 *            an execution event
	 * 
	 * @return the editing domain, or {@code null} if there is none (such as when the Papyrus Editor is closing)
	 */
	protected TransactionalEditingDomain getEditingDomain(ExecutionEvent execution) {
		TransactionalEditingDomain result = null;

		try {
			result = ServiceUtilsForHandlers.getInstance().getTransactionalEditingDomain(execution);
		} catch (ServiceException e) {
			// The wrong kind of editor/view is active or the Papyrus Editor is shutting down.
			// These are both normal conditions
		}

		return result;
	}

	/**
	 * Obtains the transactional editing domain associated with the Papyrus Editor or View
	 * that has the specified evaluation {@code context}.
	 * 
	 * @param context
	 *            an evaluation context for a command's enablement or other computation
	 * 
	 * @return the editing domain, or {@code null} if there is none (such as when the Papyrus Editor is closing)
	 */
	protected TransactionalEditingDomain getEditingDomain(IEvaluationContext context) {
		TransactionalEditingDomain result = null;

		try {
			result = ServiceUtilsForIEvaluationContext.getInstance().getTransactionalEditingDomain(context);
		} catch (ServiceException e) {
			// The wrong kind of editor/view is active or the Papyrus Editor is shutting down.
			// These are both normal conditions
		}

		return result;
	}

	/**
	 * Obtains the page manager associated with the Papyrus Editor or View
	 * that is the context of the specified {@code execution}.
	 * 
	 * @param execution
	 *            an execution event
	 * 
	 * @return the page manager, or {@code null} if there is none (such as when the Papyrus Editor is closing)
	 */
	protected IPageManager getPageManager(ExecutionEvent execution) {
		IPageManager result = null;

		try {
			result = ServiceUtilsForHandlers.getInstance().getIPageManager(execution);
		} catch (ServiceException e) {
			// The wrong kind of editor/view is active or the Papyrus Editor is shutting down.
			// These are both normal conditions
		}

		return result;
	}

	/**
	 * Obtains the page manager associated with the Papyrus Editor or View
	 * that has the specified evaluation {@code context}.
	 * 
	 * @param context
	 *            an evaluation context for a command's enablement or other computation
	 * 
	 * @return the page manager, or {@code null} if there is none (such as when the Papyrus Editor is closing)
	 */
	protected IPageManager getPageManager(IEvaluationContext context) {
		IPageManager result = null;

		try {
			result = ServiceUtilsForIEvaluationContext.getInstance().getIPageManager(context);
		} catch (ServiceException e) {
			// The wrong kind of editor/view is active or the Papyrus Editor is shutting down.
			// These are both normal conditions
		}

		return result;
	}

	/**
	 * Adapt the specified object to the requested type, if possible.
	 * Return null if the object can't be adapted.
	 *
	 * @param object
	 * @param expectedClassType
	 * @return The adapted object, or null.
	 */
	@SuppressWarnings("unchecked")
	private <T> T adapt(Object object, Class<T> expectedClassType) {


		EObject eobject = EMFHelper.getEObject(object);

		if (eobject != null && expectedClassType.isInstance(eobject)) {
			return (T) eobject;
		}



		// Try global mechanism
		{
			T ele = Platform.getAdapterManager().getAdapter(object, expectedClassType);
			if (ele != null) {
				return ele;
			}
			// Try as EObject if the expectedClasType is sub-type of EObject.
			if (EObject.class.isAssignableFrom(expectedClassType)) {
				// to EObject
				eobject = Platform.getAdapterManager().getAdapter(object, EObject.class);

				if (eobject != null && expectedClassType.isInstance(eobject)) {

					return (T) eobject;
				}
			}
		}
		// Can't be adapted
		return null;

	}

	/**
	 * Filter the list, and only retain objects that can be adapted to the specified type
	 *
	 * @param objects
	 * @param class1
	 * @return
	 */
	private <T> List<T> getAllElementAdaptedToType(List<Object> list, Class<T> expectedClassType) {

		List<T> res = new ArrayList<T>();

		for (Object cur : list) {

			T adapted = adapt(cur, expectedClassType);
			if (adapted != null) {
				res.add(adapted);
			}
		}
		return res;
	}

	/**
	 * Get all selected element of the specified type.
	 *
	 * @param expectedType
	 * @return
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> getCurrentSelectionAdaptedToType(ExecutionEvent event, Class<T> expectedType) throws ExecutionException {

		// Get selection from the workbench
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);

		// Get the selected objects according to the type of the selected
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return getAllElementAdaptedToType(structuredSelection.toList(), expectedType);
		} else if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			return getAllElementAdaptedToType(treeSelection.toList(), expectedType);

		}
		return null;
	}
}
