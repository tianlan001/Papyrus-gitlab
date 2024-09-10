/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.converter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.StringResolutionProblem;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;

/**
 * @author Vincent Lorenzo
 * 
 *         This class wraps a papyrus converter to manage the string resolution problem
 *
 */
public class StringResolutionProblemPapyrusConverter implements IPapyrusConverter {

	private IPapyrusConverter wrappedDisplayConverter;

	/**
	 * Constructor.
	 *
	 */
	public StringResolutionProblemPapyrusConverter(IPapyrusConverter displayConverter) {
		this.wrappedDisplayConverter = displayConverter;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getMatchingElements(java.lang.String)
	 *
	 * @param aString
	 * @return
	 */
	public List<?> getMatchingElements(String aString) {
		return wrappedDisplayConverter.getMatchingElements(aString);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getElementsByName(java.lang.String)
	 *
	 * @param aString
	 * @return
	 */
	public List<?> getElementsByName(String aString) {
		return wrappedDisplayConverter.getElementsByName(aString);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getShortestQualifiedNames(java.lang.Object)
	 * @deprecated since 1.2.0
	 */
	@Override
	public List<String> getShortestQualifiedNames(final Object element) {
		return getShortestQualifiedNames(element, true);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getShortestQualifiedNames(java.lang.Object, boolean)
	 */
	@Override
	public List<String> getShortestQualifiedNames(final Object element, final boolean manageDuplicate) {
		return wrappedDisplayConverter.getShortestQualifiedNames(element, manageDuplicate);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#getSubStringsWithTheirPositions(java.lang.String)
	 *
	 * @param multiValueAsString
	 * @return
	 */
	public Map<List<Integer>, String> getSubStringsWithTheirPositions(String multiValueAsString) {
		return wrappedDisplayConverter.getSubStringsWithTheirPositions(multiValueAsString);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#splitFullStringToSubElementString(java.lang.String)
	 *
	 * @param multiValueAsString
	 * @return
	 */
	public List<String> splitFullStringToSubElementString(String multiValueAsString) {
		return wrappedDisplayConverter.splitFullStringToSubElementString(multiValueAsString);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#isValidEditString(java.lang.String)
	 *
	 * @param aString
	 * @return
	 */
	public IStatus isValidEditString(String aString) {
		return wrappedDisplayConverter.isValidEditString(aString);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param element
	 * @return
	 */
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return wrappedDisplayConverter.getCompletionProcessor(element);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#canonicalToDisplayValue(java.lang.Object, int)
	 *
	 * @param object
	 * @param flag
	 * @return
	 */
	public String canonicalToDisplayValue(Object object, int flag) {
		return wrappedDisplayConverter.canonicalToDisplayValue(object, flag);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#canonicalToEditValue(java.lang.Object, int)
	 *
	 * @param object
	 * @param flag
	 * @return
	 */
	public String canonicalToEditValue(Object object, int flag) {
		if (object == null || EMPTY_STRING.equals(object)) {
			return EMPTY_STRING;
		}
		StringBuilder builder = new StringBuilder();
		if (object instanceof Collection<?>) {
			Collection<?> coll = (Collection<?>) object;
			if (coll.isEmpty()) {
				return EMPTY_STRING;
			}
			Iterator<?> iter = coll.iterator();
			while (iter.hasNext()) {
				Object tmp = iter.next();
				if (tmp instanceof StringResolutionProblem) {
					builder.append(((StringResolutionProblem) tmp).getValueAsString());
				}
				if (iter.hasNext()) {
					builder.append(STRING_SEPARATOR);
				}
			}
		} else if (object instanceof StringResolutionProblem) {
			builder.append(((StringResolutionProblem) object).getValueAsString());
		}
		if (builder.length() > 0) {
			return builder.toString();
		}
		return wrappedDisplayConverter.canonicalToEditValue(object, flag);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#displayToCanonicalValue(java.lang.String, int)
	 *
	 * @param string
	 * @param flag
	 * @return
	 */
	public Object displayToCanonicalValue(String string, int flag) {
		return wrappedDisplayConverter.displayToCanonicalValue(string, flag);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#editToCanonicalValue(java.lang.String, int)
	 *
	 * @param string
	 * @param flag
	 * @return
	 */
	public Object editToCanonicalValue(String string, int flag) {
		return wrappedDisplayConverter.editToCanonicalValue(string, flag);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#editToDisplayValue(java.lang.String)
	 *
	 * @param string
	 * @return
	 */
	public String editToDisplayValue(String string) {
		return wrappedDisplayConverter.editToDisplayValue(string);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter#displayToEditValue(java.lang.String)
	 *
	 * @param string
	 * @return
	 */
	public String displayToEditValue(String string) {
		return wrappedDisplayConverter.displayToEditValue(string);
	}


}
