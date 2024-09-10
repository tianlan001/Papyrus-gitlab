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
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class FontProvider implements IFontProvider {

	private Map<FontData, Font> cash = new HashMap<FontData, Font>();
	private Device device;

	public FontProvider(final Device device) {
		this.device = device;
	}

	public Font getFont(final FontData fontData) {
		Font result = this.cash.get(fontData);
		if (result == null) {
			result = new Font(this.device, fontData);
			this.cash.put(fontData, result);
		}
		return result;
	}

}
