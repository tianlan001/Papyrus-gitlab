/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;

/**
 * Interface for semantic parsers that support mask options.
 */
public interface IMaskManagedSemanticParser extends ISemanticParser {

	/** The label to show in case ParserOptions.None is passed as flag (avoid a fully masked label) */
	public static final String MaskedLabel = "<empty label>"; //$NON-NLS-1$

	/**
	 * Get the {@link Map} of masks
	 *
	 * @return the {@link Map} of masks
	 */
	public Map<String, String> getMasks();

	/**
	 * Gets the default mask configuration for this parser
	 *
	 * @return
	 *         The default mask configuration for this parser
	 */
	public Collection<String> getDefaultValue(IAdaptable element);
}
