/*****************************************************************************
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
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;

/**
 * @since 3.0
 */
public class InteractionEditHelper extends ElementEditHelper {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getEditContextCommand(org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest)
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected ICommand getEditContextCommand(GetEditContextRequest req) {
		return super.getEditContextCommand(req);
	}





}
