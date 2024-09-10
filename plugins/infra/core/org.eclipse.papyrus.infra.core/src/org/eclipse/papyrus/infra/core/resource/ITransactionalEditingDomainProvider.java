/*****************************************************************************
 * Copyright (c) 2011-2012 Atos.
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
 *  Mathieu Velten  mathieu.velten@atos.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * Classes implementing this interface can provide a way to create editing domain to Papyrus
 * environment. The provider registered on the transactionalEditingDomainProvider extension point
 * with the higher priority will be used by Papyrus to create Editing Domain.
 *
 * @author mvelten
 *
 */
public interface ITransactionalEditingDomainProvider {
	TransactionalEditingDomain createTransactionalEditingDomain(ResourceSet resourceSet);
}
