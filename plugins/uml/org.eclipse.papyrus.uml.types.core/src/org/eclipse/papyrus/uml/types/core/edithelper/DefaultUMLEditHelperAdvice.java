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
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.types.core.requests.ApplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest;

public class DefaultUMLEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeEditCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest req) {
		if (req instanceof ApplyProfileRequest) {
			return getBeforeApplyProfileCommand((ApplyProfileRequest) req);
		} else if (req instanceof UnapplyStereotypeRequest) {
			return getBeforeUnapplyProfileCommand((UnapplyStereotypeRequest) req);
		} else if (req instanceof ApplyStereotypeRequest) {
			return getBeforeApplyStereotypeCommand((ApplyStereotypeRequest) req);
		} else if (req instanceof UnapplyProfileRequest) {
			return getBeforeUnapplyStereotypeCommand((UnapplyProfileRequest) req);
		} else if (req instanceof SetStereotypeValueRequest) {
			return getBeforeSetValueCommand((SetStereotypeValueRequest) req);
		}

		return super.getBeforeEditCommand(req);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterEditCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ICommand getAfterEditCommand(IEditCommandRequest req) {
		if (req instanceof ApplyProfileRequest) {
			return getAfterApplyProfileCommand((ApplyProfileRequest) req);
		} else if (req instanceof UnapplyStereotypeRequest) {
			return getAfterUnapplyProfileCommand((UnapplyStereotypeRequest) req);
		} else if (req instanceof ApplyStereotypeRequest) {
			return getAfterApplyStereotypeCommand((ApplyStereotypeRequest) req);
		} else if (req instanceof UnapplyProfileRequest) {
			return getAfterUnapplyStereotypeCommand((UnapplyProfileRequest) req);
		} else if (req instanceof SetStereotypeValueRequest) {
			return getAfterSetValueCommand((SetStereotypeValueRequest) req);
		}

		return super.getAfterEditCommand(req);
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getBeforeUnapplyProfileCommand(UnapplyStereotypeRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getBeforeApplyProfileCommand(ApplyProfileRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getBeforeUnapplyStereotypeCommand(UnapplyProfileRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getBeforeApplyStereotypeCommand(ApplyStereotypeRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getAfterUnapplyProfileCommand(UnapplyStereotypeRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getAfterApplyProfileCommand(ApplyProfileRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getAfterUnapplyStereotypeCommand(UnapplyProfileRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getAfterApplyStereotypeCommand(ApplyStereotypeRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getBeforeSetValueCommand(SetStereotypeValueRequest req) {
		return null;
	}

	/**
	 * @param req
	 * @return
	 */
	protected ICommand getAfterSetValueCommand(SetStereotypeValueRequest req) {
		return null;
	}
}
