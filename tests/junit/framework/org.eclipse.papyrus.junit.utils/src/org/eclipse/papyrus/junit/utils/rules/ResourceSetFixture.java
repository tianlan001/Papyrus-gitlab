/**
 * Copyright (c) 2014, 2019 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 549108
 */
package org.eclipse.papyrus.junit.utils.rules;

import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;


/**
 * A simple non-transactional {@link ResourceSet} fixture.
 */
public class ResourceSetFixture extends AbstractModelFixture<EditingDomain> {

	public ResourceSetFixture() {
		super();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param additionalFileExtension
	 *            the extension of additional files to manage during the project creation
	 * @since 2.3
	 */
	public ResourceSetFixture(final List<String> additionalFileExtension) {
		super(additionalFileExtension);
	}

	@Override
	protected EditingDomain createEditingDomain() {
		return new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
	}
}
