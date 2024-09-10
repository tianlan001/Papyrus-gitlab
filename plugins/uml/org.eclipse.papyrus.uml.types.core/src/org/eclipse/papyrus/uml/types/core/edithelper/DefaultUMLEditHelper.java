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

package org.eclipse.papyrus.uml.types.core.edithelper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DefaultEditHelper;
import org.eclipse.papyrus.uml.types.core.commands.ApplyProfileCommand;
import org.eclipse.papyrus.uml.types.core.commands.ApplyStereotypeCommand;
import org.eclipse.papyrus.uml.types.core.commands.SetStereotypeValueCommand;
import org.eclipse.papyrus.uml.types.core.commands.UnapplyProfileCommand;
import org.eclipse.papyrus.uml.types.core.commands.UnapplyStereotypeCommand;
import org.eclipse.papyrus.uml.types.core.requests.ApplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest;

public class DefaultUMLEditHelper extends DefaultEditHelper {

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.helper.DefaultEditHelper#getInsteadCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected ICommand getInsteadCommand(IEditCommandRequest req) {

		if (req instanceof ApplyProfileRequest) {
			return getApplyProfileCommand((ApplyProfileRequest) req);
		} else if (req instanceof UnapplyProfileRequest) {
			return getUnapplyProfileCommand((UnapplyProfileRequest) req);
		} else if (req instanceof ApplyStereotypeRequest) {
			return getApplyStereotypeCommand((ApplyStereotypeRequest) req);
		} else if (req instanceof UnapplyStereotypeRequest) {
			return getUnapplyStereotypeCommand((UnapplyStereotypeRequest) req);
		} else if (req instanceof SetStereotypeValueRequest) {
			return getSetStereotypeValueCommand((SetStereotypeValueRequest) req);
		}

		return super.getInsteadCommand(req);
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getUnapplyProfileCommand(UnapplyProfileRequest req) {
		return new UnapplyProfileCommand(req, req.getEditingDomain(), req.getLabel());
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getApplyProfileCommand(ApplyProfileRequest req) {
		return new ApplyProfileCommand(req, req.getEditingDomain(), req.getLabel());
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getUnapplyStereotypeCommand(UnapplyStereotypeRequest req) {
		return new UnapplyStereotypeCommand(req, req.getEditingDomain(), req.getLabel());
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getApplyStereotypeCommand(ApplyStereotypeRequest req) {
		return new ApplyStereotypeCommand(req, req.getEditingDomain(), req.getLabel());
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getSetStereotypeValueCommand(SetStereotypeValueRequest req) {
		return new SetStereotypeValueCommand(req, req.getEditingDomain(), req.getLabel());
	}

}
