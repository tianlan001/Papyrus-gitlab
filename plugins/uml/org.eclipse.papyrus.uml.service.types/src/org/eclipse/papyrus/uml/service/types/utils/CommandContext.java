/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Context holder for model element creation commands.
 */
public class CommandContext implements ICommandContext {

	private EObject container;

	private EReference reference;

	private Map<Object, Object> parameters;
	/** Constructor */
	public CommandContext(EObject container) {
		this.container = container;
	}

	/** Constructor */
	public CommandContext(EObject container, EReference reference) {
		this.container = container;
		this.reference = reference;
	}
	
	/** Constructor */
	public CommandContext(EObject container, EReference reference, Map<Object, Object> parameters) {
		this.container = container;
		this.reference = reference;
		this.parameters = parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	public EObject getContainer() {
		return this.container;
	}

	/**
	 * {@inheritDoc}
	 */
	public EReference getReference() {
		return this.reference;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<Object, Object> getParameters() {
		return this.parameters;
	}
}
