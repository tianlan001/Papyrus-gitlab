/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.tests;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.edit.domain.PapyrusTransactionalEditingDomain;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;

/**
 * A model-set fixture that creates an instance of exactly the {@link PapyrusTransactionalEditingDomain} class,
 * not any of the plugged-in-by-extension-point subclasses.
 */
public class StrictModelSetFixture extends ModelSetFixture {

	public StrictModelSetFixture() {
		super();
	}

	@Override
	protected ServicesRegistry createServiceRegistry() throws Exception {
		ServicesRegistry result = new ServicesRegistry();

		ModelSet modelSet = new ModelSet();
		TransactionalEditingDomain domain = PapyrusTransactionalEditingDomain.FACTORY.createEditingDomain(modelSet);

		result.add(TransactionalEditingDomain.class, 10, domain);
		result.add(ModelSet.class, 10, modelSet);

		result.startRegistry();

		return result;
	}
}
