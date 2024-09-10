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

package org.eclipse.papyrus.infra.editor.welcome.nattable.internal.widgets;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.papyrus.infra.editor.welcome.internal.modelelements.LanguageObservable;
import org.eclipse.papyrus.infra.editor.welcome.nattable.widgets.FormTable;
import org.eclipse.swt.widgets.Composite;

/**
 * A table widget presenting languages in two columns for name and version.
 */
public class LanguagesTable extends FormTable<LanguageObservable> {

	public LanguagesTable(Composite parent, int style) {
		// No hyperlinks
		super(parent, style, "Languages:", new LanguagesColumnAccessor(), "Name", "Version");
	}

	//
	// Nested types
	//

	static class LanguagesColumnAccessor implements IColumnPropertyAccessor<LanguageObservable> {
		static final String NAME = "name"; //$NON-NLS-1$
		static final String VERSION = "version"; //$NON-NLS-1$

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getDataValue(LanguageObservable rowObject, int columnIndex) {
			Object result;

			switch (columnIndex) {
			case 0:
				result = rowObject.getName();
				break;
			case 1:
				result = rowObject.getVersion();
				break;
			default:
				throw new IndexOutOfBoundsException(Integer.toString(columnIndex));
			}

			return result;
		}

		@Override
		public void setDataValue(LanguageObservable rowObject, int columnIndex, Object newValue) {
			throw new IllegalStateException("not editable"); //$NON-NLS-1$
		}

		@Override
		public String getColumnProperty(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return NAME;
			case 1:
				return VERSION;
			default:
				throw new IndexOutOfBoundsException(Integer.toString(columnIndex));
			}
		}

		@Override
		public int getColumnIndex(String propertyName) {
			switch (propertyName) {
			case NAME:
				return 0;
			case VERSION:
				return 1;
			default:
				throw new IllegalArgumentException(propertyName);
			}
		}
	}
}
