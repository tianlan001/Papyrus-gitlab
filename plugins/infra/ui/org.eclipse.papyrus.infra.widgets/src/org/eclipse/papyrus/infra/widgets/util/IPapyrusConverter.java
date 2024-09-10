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

package org.eclipse.papyrus.infra.widgets.util;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

/**
 * @author Vincent Lorenzo
 * 
 *         This class provides method to convert Object to a string to edit or a string to display and vice-versa from a string to edit or to display to the real object
 *
 */
public interface IPapyrusConverter extends INameResolutionHelper {

	// the previous regex,which did not allow to get 'Class, when the user asked for the completion and
	// PLEASE DO NOT ERASE THIS COMMENT
	//	public static final String FIND_PART_NAME_REGEX = "([^'\\x2C]+)|('[^']+')"; //$NON-NLS-1$

	/**
	 * regex used to parse string on , and ',':
	 */
	public static final String FIND_PART_NAME_REGEX = "([^'\\x2C]+)|('[^']+')|('(([^']*[\\x2C][^']*)))"; //$NON-NLS-1$

	/**
	 * the string delimiter
	 */
	public static final String STRING_DELIMITER = "\'"; //$NON-NLS-1$

	/**
	 * the string separator for multivalued properties
	 */
	public static final String STRING_SEPARATOR = ","; //$NON-NLS-1$

	/**
	 * the string used for <code>null</code> value
	 */
	public static final String UNDEFINED_VALUE = "<Undefined>";//$NON-NLS-1$

	/**
	 * add this string to the suggestions when there are more than {@link #MAX_ELEMENTS_TO_DISPLAY}
	 */
	public static final String MORE_ELEMENTS = "...";//$NON-NLS-1$

	/**
	 * empty string
	 */
	public static final String EMPTY_STRING = "";//$NON-NLS-1$

	/**
	 * 
	 * @param multiValueAsString
	 *            the full String write in the StyledText
	 * @return
	 *         a map with the name of the elements as value and the start and the end index of the name in the typed text as key
	 */
	public Map<List<Integer>, String> getSubStringsWithTheirPositions(String multiValueAsString);

	/**
	 * 
	 * @param multiValueAsString
	 *            a string
	 * @return
	 *         all substring according to the applied regex
	 */
	public List<String> splitFullStringToSubElementString(String multiValueAsString);

	/**
	 * 
	 * @param aString
	 *            a string
	 * @return
	 *         a istatus indicating if the string is valid or not
	 */
	public IStatus isValidEditString(String aString);

	/**
	 * Returns the parser's content assist processor
	 * 
	 * @param element
	 *            the element
	 * @return the content assist processor
	 */
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element);


	/**
	 * 
	 * @param object
	 *            an object
	 * @param flag
	 *            a flag which could be used for for the name qualification for example
	 * @return
	 *         the string to display (label) representing the object
	 */
	public String canonicalToDisplayValue(Object object, int flag);

	/**
	 * 
	 * @param object
	 *            an object
	 * @param flag
	 *            a flag which could be used for for the name qualification for example
	 * @return
	 *         the string to edit (label) representing the object
	 */
	public String canonicalToEditValue(Object object, int flag);

	/**
	 * 
	 * @param string
	 *            a display string
	 * @param flag
	 *            a flag which could be used for for the name qualification for example
	 * @return
	 *         the object represented by the displayed string
	 */
	public Object displayToCanonicalValue(String string, int flag);

	/**
	 * 
	 * @param string
	 *            an edited string
	 * @param flag
	 *            a flag which could be used for for the name qualification for example
	 * @return
	 *         the object represented by the edited string
	 */
	public Object editToCanonicalValue(String string, int flag);

	/**
	 * 
	 * @param string
	 *            an edited string
	 * @return
	 *         the the equivalent string to use for edition
	 */
	public String editToDisplayValue(String string);

	/**
	 * 
	 * @param string
	 *            a displayed string
	 * @return
	 *         the the equivalent string to use for
	 */
	public String displayToEditValue(String string);

}
