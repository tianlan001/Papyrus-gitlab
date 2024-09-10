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
package org.eclipse.papyrus.infra.nattable.common.handlers;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.nattableconfiguration.NattableConfigurationRegistry;

/**
 * The handler used to create a nattable editor
 *
 * @author Vincent Lorenzo
 *
 */
public class CreateNatTableEditorHandler extends AbstractCreateNattableEditorHandler implements IExecutableExtension {

	/**
	 * the name of the parameter of the handler
	 */
	private static final String TABLE_TYPE_PARAMETER = "tableType"; //$NON-NLS-1$

	/**
	 * the type of the table to create
	 */
	private String type;

	/**
	 *
	 * Constructor.
	 *
	 */
	public CreateNatTableEditorHandler() {
		super();
	}


	/**
	 * Set the type of table to be created by this handler
	 *
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.common.handlers.AbstractCreateNattableEditorHandler2#getTableEditorConfiguration()
	 *
	 * @return
	 */
	@Override
	protected TableConfiguration getTableEditorConfiguration() {
		return NattableConfigurationRegistry.INSTANCE.getConfiguration(this.type);
	}

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		if (this.type != null) {
			List<EObject> selection = getSelection();
			if (selection.size() == 1) {
				IStatus status = NattableConfigurationRegistry.INSTANCE.canCreateTable(this.type, selection.get(0));
				setBaseEnabled(status.isOK());
				return;
			}
		}
		setBaseEnabled(false);
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 *
	 * @param config
	 * @param propertyName
	 * @param data
	 * @throws CoreException
	 */
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		if (data instanceof Hashtable) {
			this.type = (String) ((Hashtable<?, ?>) data).get(TABLE_TYPE_PARAMETER);
		}
	}
}
