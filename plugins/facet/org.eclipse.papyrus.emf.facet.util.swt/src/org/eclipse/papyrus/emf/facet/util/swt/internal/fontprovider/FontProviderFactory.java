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
 *      Grégoire Dupé (Mia-Software) - Bug 424122 - [Table] Images, fonts and colors are not shared between the instances of table
 ******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.swt.internal.fontprovider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.papyrus.emf.facet.util.swt.fontprovider.IFontProvider;
import org.eclipse.papyrus.emf.facet.util.swt.fontprovider.IFontProviderFactory;
import org.eclipse.swt.graphics.Device;

public class FontProviderFactory implements IFontProviderFactory {

	private Map<Device, IFontProvider> cash = new HashMap<Device, IFontProvider>();

	public IFontProvider getOrCreateIFontProvider(final Device device) {
		IFontProvider result = this.cash.get(device);
		if (result == null) {
			result = new FontProvider(device);
			this.cash.put(device, result);
		}
		return result;
	}

}
