/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.internal.language;

import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.resource.IModel;

/**
 * An adapter type for {@link IModel}s that provide language-specific details
 * about themselves.
 */
public interface ILanguageModel {
	/**
	 * Obtains a model's file extension. This identifies resources that
	 * are expected to contain "semantic model" content for some {@link ILanguage}.
	 * Language models are expected to have file extensions associated with them.
	 * 
	 * @return the model's file extension
	 */
	String getModelFileExtension();
}
