/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.handler;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

/**
 * @author Vincent Lorenzo
 * 
 *
 */
public abstract class AbstractParametricTreeTableHandler extends AbstractTreeTableHandler implements IExecutableExtension {

	/**
	 * the id of the parameter declared for the handler in the plugin.xml
	 */
	private String parameterId;

	/**
	 * the value of the par
	 */
	private String parameterValue;


	/**
	 * Constructor.
	 *
	 * @param expandedDepthArgName
	 */
	public AbstractParametricTreeTableHandler(String parameterId) {
		this.parameterId = parameterId;
	}

	/**
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 *
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws CoreException
	 */
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		if (data instanceof Hashtable<?, ?> && this.parameterId != null) {
			this.parameterValue = (String) ((Hashtable<?, ?>) data).get(this.parameterId);
		}
	}


	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}
}
