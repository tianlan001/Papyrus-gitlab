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

package org.eclipse.papyrus.infra.gmfdiag.welcome.internal.widgets;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.papyrus.infra.editor.welcome.nattable.widgets.HyperlinkTable;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements.NotationObservable;
import org.eclipse.swt.widgets.Composite;

/**
 * A two-column table of hyperlinks for diagrams, tables, and other notational views:
 * on the left side, a link that navigates to the diagram/table page, and on the right side,
 * a link that navigates to the view's context element in the Model Explorer.
 */
public class NotationsTable extends HyperlinkTable<NotationObservable> {

	public NotationsTable(Composite parent, int style) {
		super(parent, style, true, new NotationsColumnAccessor(), "View", "Context");
	}

	//
	// Nested types
	//

	static class NotationsColumnAccessor implements IColumnPropertyAccessor<NotationObservable> {
		static final String VIEW = "view"; //$NON-NLS-1$
		static final String CONTEXT = "context"; //$NON-NLS-1$

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getDataValue(NotationObservable rowObject, int columnIndex) {
			Object result;

			switch (columnIndex) {
			case 0:
				result = rowObject.getView();
				break;
			case 1:
				result = rowObject.getContext();
				break;
			default:
				throw new IndexOutOfBoundsException(Integer.toString(columnIndex));
			}

			return result;
		}

		@Override
		public void setDataValue(NotationObservable rowObject, int columnIndex, Object newValue) {
			throw new IllegalStateException("not editable"); //$NON-NLS-1$
		}

		@Override
		public String getColumnProperty(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return VIEW;
			case 1:
				return CONTEXT;
			default:
				throw new IndexOutOfBoundsException(Integer.toString(columnIndex));
			}
		}

		@Override
		public int getColumnIndex(String propertyName) {
			switch (propertyName) {
			case VIEW:
				return 0;
			case CONTEXT:
				return 1;
			default:
				throw new IllegalArgumentException(propertyName);
			}
		}
	}
}
