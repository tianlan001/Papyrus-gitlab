/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.views.config.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.tester.ITableTester;
import org.eclipse.papyrus.infra.nattable.views.config.Activator;
import org.eclipse.papyrus.infra.nattable.views.config.messages.Messages;


public class TableViewCreationTester implements ITableTester {


	@Override
	public IStatus isAllowed(Object context) {
		if (context instanceof EObject) {
			final EObject current = (EObject) context;

			try {
				ModelSet modelSet = ServiceUtilsForEObject.getInstance().getModelSet(current);
				IModel model = modelSet.getModelFor(current);
				if (model != null) {
					// This is an element of some model. Is it a semantic model?
					if (ILanguageService.getLanguageModels(modelSet).contains(model)) {
						return new Status(IStatus.OK, Activator.PLUGIN_ID, Messages.TableViewCreationTester_TheTableViewCanBeCreated);
					}
				}
			} catch (ServiceException e) {
				// Okay, no services? Then this isn't an appropriate context
				return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.TableViewCreationTester_ServicesUnavailable);
			}
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.TableViewCreationTester_TheTableViewCantBeCreated);
	}
}
