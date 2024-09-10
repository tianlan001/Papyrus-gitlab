/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 451230
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.Activator;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;

/**
 * Utils class for elements types
 *
 * @author Vincent Lorenzo
 *
 */
public class ElementTypeUtils {


	protected ElementTypeUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 * @return
	 * 		all existing elements types
	 */
	public static final Collection<IElementType> getAllExistingElementTypes() {
		IClientContext clientContext = getDefaultClientContext();
		final IElementType[] types = ElementTypeRegistry.getInstance().getElementTypes(clientContext);
		return Arrays.asList(types);
	}

	/**
	 * @return
	 * 		all existing element type id, sorted by alphabetical order
	 */
	public static final Collection<String> getAllExistingElementTypesIds() {
		final Collection<String> ids = new TreeSet<String>();
		for (final IElementType iElementType : getAllExistingElementTypes()) {
			ids.add(iElementType.getId());
		}
		return ids;
	}

	/**
	 * Obtains the default client context in which the edit service binds element types.
	 *
	 * @return the edit service's default client context, or {@code null} if it is not available in the current installation
	 */
	public static IClientContext getDefaultClientContext() {
		IClientContext result = null;

		try {
			result = TypeContext.getDefaultContext();
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		return result;

	}

	/**
	 * return a boolean about the usage of a GUI for edition of an Element
	 *
	 * @param request
	 *            an edition request
	 * @return true if the request do not contain information about usage of GUI
	 */
	public static boolean useGUI(IEditCommandRequest request) {
		Object value = request.getParameter(RequestParameterConstants.USE_GUI);

		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof String) {
			Boolean booleanObject = Boolean.valueOf((String) value);
			return booleanObject.booleanValue();
		}

		return true; // Default
	}
	
	/**
	 * return a boolean about the cancellability of a diagram for edition of an Element
	 *
	 * @param request
	 *            an edition request
	 * @return true if the request do not contain information about cancellability
	 */
	public static boolean dialogCancellable(IEditCommandRequest request) {
		Object value = request.getParameter(RequestParameterConstants.DIALOG_CANCELLABLE);

		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof String) {
			Boolean booleanObject = Boolean.valueOf((String) value);
			return booleanObject.booleanValue();
		}

		return false; // Default
	}

	/**
	 * Configure a request to specify whether the GUI should be used or not.
	 *
	 * If set to false, dialogs shouldn't be opened during the execution of the associated command(s)
	 *
	 * @param request
	 * @param useGUI
	 */
	public static void setUseGUI(IEditCommandRequest request, boolean useGUI) {
		request.setParameter(RequestParameterConstants.USE_GUI, useGUI);
	}
	
	/**
	 * Configure a request to specify whether the editdialog should be cancellable or not.
	 *
	 * If set to false, dialogs shouldn't be cancellable during the execution of the associated command(s)
	 *
	 * @param request
	 * @param dialogCancellable
	 */
	public static void setDialogCancellable(IEditCommandRequest request, boolean dialogCancellable) {
		request.setParameter(RequestParameterConstants.DIALOG_CANCELLABLE, dialogCancellable);
	}

}