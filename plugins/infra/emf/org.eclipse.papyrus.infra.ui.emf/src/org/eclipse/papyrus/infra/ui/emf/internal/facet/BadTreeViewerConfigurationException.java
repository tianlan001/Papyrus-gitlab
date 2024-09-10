/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.internal.facet;

import java.util.List;

import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;

/**
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 */
public class BadTreeViewerConfigurationException extends Exception {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 8074370719995941054L;

	/**
	 * the intermediate list of customization
	 */
	private List<Customization> intermediateResult;

	/**
	 * Constructor.
	 *
	 * @param message
	 *            the error message
	 * @param intermediateResult
	 *            the intermediate result of merged Customizations
	 */
	public BadTreeViewerConfigurationException(final String message, final List<Customization> intermediateResult) {
		super(message);
		this.intermediateResult = intermediateResult;
	}

	/**
	 *
	 * @return
	 *         the intermediate result of the merge Customization
	 */
	public List<Customization> getIntermediateResult() {
		return this.intermediateResult;
	}

}
