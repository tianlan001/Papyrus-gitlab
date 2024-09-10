/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.editor.welcome.nattable.painter;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.papyrus.infra.editor.welcome.nattable.ServiceConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * A text painter that obtains text labels from the {@link LabelProviderService} registered
 * in the NatTable configuration registry.
 */
public class LabelProviderTextPainter extends TextPainter {

	public LabelProviderTextPainter() {
		super();
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg, boolean calculateByTextLength, boolean calculateByTextHeight) {
		super(wrapText, paintBg, calculateByTextLength, calculateByTextHeight);
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg, boolean calculate) {
		super(wrapText, paintBg, calculate);
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg, int spacing, boolean calculateByTextLength, boolean calculateByTextHeight) {
		super(wrapText, paintBg, spacing, calculateByTextLength, calculateByTextHeight);
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg, int spacing, boolean calculate) {
		super(wrapText, paintBg, spacing, calculate);
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg, int spacing) {
		super(wrapText, paintBg, spacing);
	}

	public LabelProviderTextPainter(boolean wrapText, boolean paintBg) {
		super(wrapText, paintBg);
	}

	@Override
	protected String convertDataType(ILayerCell cell, IConfigRegistry configRegistry) {
		String result;
		Object dataValue = cell.getDataValue();
		if (dataValue instanceof String) {
			result = (String) dataValue;
		} else {
			if (dataValue instanceof IObservableValue<?>) {
				dataValue = ((IObservableValue<?>) dataValue).getValue();
			}

			LabelProviderService labelService = ServiceConfigAttributes.getService(LabelProviderService.class, configRegistry, cell);
			ILabelProvider labelProvider = (dataValue == null) ? labelService.getLabelProvider() : labelService.getLabelProvider(dataValue);

			result = labelProvider.getText(dataValue);
		}

		return result;
	}
}
