/**
 * Copyright (c) 2013 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Gregoire Dupe (Mia-Software) - Bug 406578 - Generic Label Provider for EMF objects
 */
package org.eclipse.papyrus.emf.facet.util.emf.ui.internal.exported;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.emf.facet.util.emf.ui.internal.EmfLabelProviderFactory;

/**
 * @since 0.4
 */
public interface IEmfLabelProviderFactory {

	IEmfLabelProviderFactory DEFAULT = new EmfLabelProviderFactory();

	ILabelProvider createLabelProvider();
}
