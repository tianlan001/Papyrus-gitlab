/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;

/**
 * The resource corresponding to the properties files.
 * 
 * @since 3.0
 */
public class TableResource extends XMIResourceImpl {

	/**
	 * Constructor.
	 *
	 * @param uri
	 *            The uri of the resource.
	 */
	public TableResource(final URI uri) {
		super(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#save(java.util.Map)
	 */
	@Override
	public void save(final Map<?, ?> options) throws IOException {

		final TransactionalEditingDomain domain = ((ModelSet) getResourceSet()).getTransactionalEditingDomain();

		final CompoundCommand compoundCommand = new CompoundCommand("Modify top tables"); //$NON-NLS-1$
		for (final EObject top : getContents()) {
			if (top instanceof Table) {
				compoundCommand.append(getDisposeTableCommand(domain, (Table) top));
			}
		}

		try {
			GMFUnsafe.write(domain, compoundCommand);
		} catch (InterruptedException e) {
			Activator.log.error(e);
		} catch (RollbackException e) {
			Activator.log.error(e);
		} finally {
			super.save(options);
		}
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
	 *
	 * @return
	 */
	@Override
	protected boolean useUUIDs() {
		return true;
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain
	 * @param table
	 *            the table to clean before dispose
	 * @return
	 * 		the command to use to clean the table before disposing it
	 */
	protected void disposeTableCommand(final Table table) {
		table.setContext(null);
		table.setOwner(null);
		table.getCurrentRowAxisProvider().eSet(NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), Collections.emptyList());
	}

	/**
	 * This allows to delete the possible project dependencies.
	 * 
	 * @param domain
	 *            the editing domain.
	 * @param table
	 *            the table to clean before dispose.
	 * @return
	 * 		the command to use to clean the table before disposing it.
	 * @since 2.0
	 */
	protected CompoundCommand getDisposeTableCommand(final TransactionalEditingDomain domain, final Table table) {
		final CompoundCommand disposeCommand = new CompoundCommand("Command used to clean the table before disposing it"); //$NON-NLS-1$
		disposeCommand.append(SetCommand.create(domain, table, NattablePackage.eINSTANCE.getTable_Context(), null));
		disposeCommand.append(SetCommand.create(domain, table, NattablePackage.eINSTANCE.getTable_Owner(), null));
		// assuming the table is synchronized and not inverted :
		disposeCommand.append(SetCommand.create(domain, table.getCurrentRowAxisProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), Collections.emptyList()));

		return disposeCommand;
	}
}
