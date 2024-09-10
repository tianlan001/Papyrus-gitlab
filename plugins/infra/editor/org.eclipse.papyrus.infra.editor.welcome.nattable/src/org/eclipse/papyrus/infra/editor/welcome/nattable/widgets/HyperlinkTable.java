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

package org.eclipse.papyrus.infra.editor.welcome.nattable.widgets;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.TextDecorationEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.papyrus.infra.editor.welcome.nattable.hyperlink.HyperlinkNavigationConfiguration;
import org.eclipse.swt.widgets.Composite;

/**
 * A table of hyperlinks for objects. One or more columns may render as hyperlinks to
 * diagrams, views, etc. Label-based sorting of columns is provided.
 */
public abstract class HyperlinkTable<E> extends FormTable<E> {
	public HyperlinkTable(Composite parent, int style, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		super(parent, style, columnAccessor, columnTitle);
	}

	public HyperlinkTable(Composite parent, int style, String label, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		super(parent, style, label, columnAccessor, columnTitle);
	}

	public HyperlinkTable(Composite parent, int style, boolean filter, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		super(parent, style, filter, columnAccessor, columnTitle);
	}

	public HyperlinkTable(Composite parent, int style, boolean filter, String label, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		super(parent, style, filter, label, columnAccessor, columnTitle);
	}

	@Override
	protected void addConfigurations(SelectionLayer selectionLayer) {
		selectionLayer.addConfiguration(new HyperlinkNavigationConfiguration<>(getParent(), IObservableValue.class, this::isActiveHyperlink));
	}

	@Override
	protected void configureBodyStyle(Style bodyStyle) {
		bodyStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.COLOR_BLUE);
		bodyStyle.setAttributeValue(CellStyleAttributes.TEXT_DECORATION, TextDecorationEnum.UNDERLINE);
	}

	protected boolean isActiveHyperlink(IObservableValue<?> value) {
		return (value != null) && (value.getValue() != null);
	}
}
