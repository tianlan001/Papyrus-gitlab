/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 528479
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.filter.configuration;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.papyrus.infra.nattable.filter.AbstractPapyrusMatcherEditor;
import org.eclipse.papyrus.infra.nattable.filter.AbstractSinglePapyrusMatcher;
import org.eclipse.uml2.uml.EnumerationLiteral;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * Matcher Editor for UML Enumeration
 *
 */
public class UMLEnumerationMatcherEditor extends AbstractPapyrusMatcherEditor {

	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 */
	public UMLEnumerationMatcherEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		super(columnAccesor, columnIndex, matchOn, configRegistry);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.AbstractPapyrusMatcherEditor#createMatcher(org.eclipse.nebula.widgets.nattable.data.IColumnAccessor, int, java.lang.Object, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 * @return
	 */
	@Override
	protected Matcher<Object> createMatcher(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		return new UMLEnumerationMatcher(columnAccesor, matchOn, columnIndex, configRegistry);
	}

	/**
	 * This Matcher allows to know is an object is displayed in a cell
	 *
	 */
	public static class UMLEnumerationMatcher extends AbstractSinglePapyrusMatcher<Object> {

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 * @param configRegistry
		 */
		public UMLEnumerationMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex, IConfigRegistry configRegistry) {
			super(accessor, columnIndex, wantedObject, configRegistry);
		}

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 */
		public UMLEnumerationMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex) {
			super(accessor, columnIndex, wantedObject);
		}

		/**
		 * @see ca.odell.glazedlists.matchers.Matcher#matches(java.lang.Object)
		 *
		 * @param item
		 * @return
		 */
		@Override
		public boolean matches(Object item) {
			Object res = getColumnAccessor().getDataValue(item, getColumnIndex());
			if (res != null) {
				Object wantedObject = getObjectToMatch();
				if (res instanceof Collection<?>) {
					String wantedName=null;
					if(wantedObject instanceof Enumerator){
						wantedName = ((Enumerator) wantedObject).getName();
					}else if(wantedObject instanceof String){
						wantedName = (String)wantedObject;
					}
					Assert.isNotNull(wantedName);
					for (Object tmp : (Collection<?>) res) {
						if (tmp instanceof EnumerationLiteral) {
							EnumerationLiteral lit = (EnumerationLiteral) tmp;
							if (lit.getName().equals(wantedName)) {
								return true;
							}
						}else if(tmp instanceof String){
							if(wantedName.equals(tmp)){
								return true;
							}
						}
					}
					return ((Collection<?>) res).contains(wantedObject);
				} else if (res instanceof EnumerationLiteral && wantedObject instanceof Enumerator) {
					return ((EnumerationLiteral) res).getName().equals(((Enumerator) wantedObject).getName());
				}else if (res instanceof Enumerator && wantedObject instanceof Enumerator) {
					return ((Enumerator) res).getName().equals(((Enumerator) wantedObject).getName());
				}else if(res instanceof EnumerationLiteral && wantedObject instanceof String){
					return wantedObject.equals(((EnumerationLiteral) res).getName());
				}else if(res instanceof String && wantedObject instanceof String){
					return wantedObject.equals(res);
				}
			}
			return false;
		}

	}

}
