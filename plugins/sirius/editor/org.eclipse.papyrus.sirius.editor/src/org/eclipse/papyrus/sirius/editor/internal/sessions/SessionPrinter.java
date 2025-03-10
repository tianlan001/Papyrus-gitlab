/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.sirius.editor.internal.sessions;

import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.business.api.session.Session;

/**
 *
 */
public class SessionPrinter {


	public static final void print(Session session, final String ID) {
		System.out.println(NLS.bind("------------- {0} ----------", ID)); //$NON-NLS-1$
		System.out.println("session =" + session); //$NON-NLS-1$
		if (session != null) {
			System.out.println("\thashcode  =" + session.hashCode()); //$NON-NLS-1$
		} else {
			System.out.println("\tsession is null"); //$NON-NLS-1$
			return;
		}
		if (session.getSessionResource() != null) {
			System.out.println("\tresource hascode = " + session.getSessionResource().hashCode()); //$NON-NLS-1$
		} else {
			System.out.println("\tsession resource is null"); //$NON-NLS-1$
			return;
		}
		if (session.getSessionResource().getResourceSet() != null) {
			System.out.println("\tresourceSet hascode= " + session.getSessionResource().getResourceSet().hashCode()); //$NON-NLS-1$
		} else {
			System.out.println("\tsession resource set is null"); //$NON-NLS-1$
			return;
		}
		if (session.getTransactionalEditingDomain() != null) {
			System.out.println("\teditingDomain hashcode= " + session.getTransactionalEditingDomain().hashCode()); //$NON-NLS-1$
		} else {
			System.out.println("\tsession editing domain is null"); //$NON-NLS-1$
		}

	}
}
