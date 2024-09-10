/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.emf.common.util.URI;

/**
 * Class uses to store value of a location during for the control of a model.
 *
 * @author Gabriel Pascual
 */
public class ResourceLocationParameterValues implements IParameterValues {

	/** The Constant LOCATION_KEY. */
	private static final String LOCATION_KEY = "location"; //$NON-NLS-1$

	/** ID of parameter command. */
	public static final String ID = "org.eclipse.papyrus.infra.services.controlmode.resourceLocation"; //$NON-NLS-1$

	/** The parameter values. */
	private Map<String, URI> parameterValues = new HashMap<String, URI>();

	/**
	 * @see org.eclipse.core.commands.IParameterValues#getParameterValues()
	 *
	 * @return
	 */
	public Map<String, URI> getParameterValues() {
		return parameterValues;
	}

	/**
	 * Consume the values.
	 *
	 * @return the values
	 */
	public URI getResourceLocation() {
		URI location = parameterValues.get(LOCATION_KEY);
		parameterValues.remove(LOCATION_KEY);
		return location;
	}

	/**
	 * Sets the resource loacation.
	 *
	 * @param uri
	 *            the new resource loacation
	 */
	public void setResourceLocation(URI uri) {
		parameterValues.put(LOCATION_KEY, uri);
	}

}
