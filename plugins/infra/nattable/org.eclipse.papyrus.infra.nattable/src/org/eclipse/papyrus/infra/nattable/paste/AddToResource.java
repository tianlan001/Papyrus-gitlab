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
package org.eclipse.papyrus.infra.nattable.paste;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;


/**
 *
 * @author Vincent Lorenzo
 *
 */
public class AddToResource implements IValueSetter {

	/**
	 * the managed resource
	 */
	private final Resource resource;

	/**
	 * the object to add to the resource
	 */
	private final EObject toAdd;

	/**
	 *
	 * Constructor.
	 *
	 * @param resource
	 * @param feature
	 * @param toAdd
	 */
	public AddToResource(final Resource resource, final EObject toAdd) {
		this.resource = resource;
		this.toAdd = toAdd;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.paste.IValueSetter#doSetValue(org.eclipse.emf.edit.domain.EditingDomain)
	 *
	 * @param domain
	 */
	@Override
	public void doSetValue(EditingDomain domain) {
		if (resource != null && toAdd != null) {
			final Command cmd = new RecordingCommand((TransactionalEditingDomain) domain) {

				@Override
				protected void doExecute() {
					resource.getContents().add(toAdd);
				}
			};
			cmd.execute();
		}
	}
}
