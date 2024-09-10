/*******************************************************************************
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
 *    Gregoire Dupe (Mia-Software) - Bug 406569 - Image provider factory
 ******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.swt.imageprovider;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.emf.facet.util.swt.internal.imageprovider.ImageProviderFactory;

public interface IImageProviderFactory {

	IImageProviderFactory DEFAULT = new ImageProviderFactory();

	IImageProvider createIImageProvider(Plugin plugin);
}
