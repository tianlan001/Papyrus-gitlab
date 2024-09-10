/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.architecture.util;

import static java.util.FormattableFlags.ALTERNATE;
import static java.util.FormattableFlags.LEFT_JUSTIFY;
import static java.util.FormattableFlags.UPPERCASE;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

/**
 * Convenience for printing <em>Architecture Model</em> elements in logging and other
 * textual output contexts.
 */
public final class FormattableADElement implements Formattable {

	private static final String ELLIPSIS = "..."; //$NON-NLS-1$

	private final ADElement element;

	public FormattableADElement(ADElement element) {
		super();

		this.element = element;
	}

	/**
	 * If a format string argument is an {@link ADElement}, wrap it.
	 *
	 * @param formatArgument
	 *            a format string argument
	 * @return the wrapped argument, which may just be the original if there was nothing to wrap
	 */
	public static Object wrap(Object formatArgument) {
		return canWrap(formatArgument) ? doWrap(formatArgument) : formatArgument;
	}

	private static Object doWrap(Object formatArgument) {
		Object result;

		if (formatArgument instanceof ADElement) {
			result = new FormattableADElement((ADElement) formatArgument);
		} else if (formatArgument instanceof Iterable<?>) {
			List<Object> resultList = new ArrayList<>();
			for (Object next : ((Iterable<?>) formatArgument)) {
				resultList.add(wrap(next));
			}
			result = resultList;
		} else if (formatArgument instanceof Object[]) {
			Object[] resultArray = ((Object[]) formatArgument).clone();
			for (int i = 0; i < resultArray.length; i++) {
				resultArray[i] = wrap(resultArray[i]);
			}
			result = resultArray;
		} else {
			result = formatArgument;
		}

		return result;
	}

	/**
	 * For any of the given format arguments that are {@link ADElement}s, wrap them.
	 *
	 * @param formatArguments
	 *            format string arguments
	 * @return the wrapped arguments, which may just be the original array if there was nothing to wrap
	 */
	public static Object[] wrapAll(Object... formatArguments) {
		Object[] result = formatArguments;

		for (int i = 0; i < formatArguments.length; i++) {
			if (canWrap(formatArguments[i])) {
				// Need to wrap
				result = doWrapAll(formatArguments);
				break;
			}
		}

		return result;
	}

	private static boolean canWrap(Object formatArgument) {
		return formatArgument instanceof ADElement
				|| (formatArgument instanceof Iterable<?> && canWrapAny((Iterable<?>) formatArgument))
				|| (formatArgument instanceof Object[] && canWrapAny((Object[]) formatArgument));
	}

	private static boolean canWrapAny(Object[] formatArgument) {
		boolean result = false;

		for (int i = 0; !result && i < formatArgument.length; i++) {
			result = canWrap(formatArgument[i]);
		}

		return result;
	}

	private static boolean canWrapAny(Iterable<?> formatArgument) {
		boolean result = false;

		for (Iterator<?> iter = formatArgument.iterator(); !result && iter.hasNext();) {
			result = canWrap(iter.next());
		}

		return result;
	}

	private static Object[] doWrapAll(Object... formatArguments) {
		Object[] result = new Object[formatArguments.length];
		for (int i = 0; i < formatArguments.length; i++) {
			result[i] = wrap(formatArguments[i]);
		}
		return result;
	}

	@Override
	public void formatTo(Formatter formatter, int flags, int width, int precision) {
		StringBuilder buf = new StringBuilder();

		// What form of name to output?
		// Use the alternate form for the identifier, not the qualified name. Null safe in either case
		String name = String.valueOf((flags & ALTERNATE) == ALTERNATE ? element.getId() : name());

		if ((flags & UPPERCASE) == UPPERCASE) {
			name = name.toUpperCase(formatter.locale());
		}

		String eclass = eclass();
		String resource = String.valueOf(resourceURI());

		int nameLen = name.length();
		int eclassLen = eclass.length() + 3; // Two angle brackets and a space
		int resourceLen = resource.length() + 3; // A space and two parentheses

		if ((precision < 0) || precision >= (nameLen + eclassLen + resourceLen)) {
			// Output everything
			buf.append('<').append(eclass).append("> "); //$NON-NLS-1$
			buf.append(name);
			buf.append(" (").append(resource).append(')'); //$NON-NLS-1$
		} else {
			int ellipsis = ELLIPSIS.length();
			int ellipsis2 = 2 * ellipsis;
			// If the precision cannot accommodate more than the name, then just output the name.
			// If the precision cannot accommodate more than the name and the eclass, then just output those
			// and prefer to truncate the name. But don't leave nothing of the name
			if (precision <= nameLen) {
				if (nameLen > ellipsis2) {
					buf.append(ELLIPSIS).append(name.substring(nameLen - precision + ellipsis));
				} else {
					buf.append(name.substring(nameLen - precision));
				}
			} else if (precision <= eclassLen + nameLen) {
				int totalLen = eclassLen + nameLen;
				int trunc = totalLen - precision;

				int howMuchName = nameLen - trunc;
				if (howMuchName < ellipsis2) {
					// Not good enough. Drop the EClass.
					howMuchName = Math.min(nameLen, precision);
					if (howMuchName < nameLen) {
						// Ellipsis if possible
						if (howMuchName > ellipsis2) {
							buf.append(ELLIPSIS).append(name.substring(nameLen - howMuchName + ellipsis));
						} else {
							buf.append(name.substring(nameLen - howMuchName));
						}
					}
				} else {
					buf.append('<').append(eclass).append("> "); //$NON-NLS-1$
					buf.append(ELLIPSIS);
					buf.append(name.substring(nameLen - howMuchName + ellipsis));
				}
			} else {
				// It's the resource that we sacrifice
				buf.append('<').append(eclass).append("> "); //$NON-NLS-1$
				buf.append(name);

				int totalLen = eclassLen + nameLen + resourceLen;
				int trunc = totalLen - precision;
				int ellipsis3 = ellipsis2 + ellipsis;

				// Don't bother with the resource if we can't show even three characters of it
				int howMuchResource = resourceLen - trunc;
				if (howMuchResource >= ellipsis3) {
					buf.append(" (").append(ELLIPSIS); //$NON-NLS-1$
					buf.append(resource.substring(resourceLen - howMuchResource + ellipsis));
					buf.append(')');
				}
			}
		}

		// Apply width and justification
		int totalLen = buf.length();
		if (totalLen < width) {
			for (int i = 0; i < width - totalLen; i++) {
				if ((flags & LEFT_JUSTIFY) == LEFT_JUSTIFY) {
					buf.append(' ');
				} else {
					buf.insert(0, ' '); // Right justify
				}
			}
		}

		formatter.format(buf.toString());
	}

	@Override
	public String toString() {
		String resource = resourceURI();

		return String.format(resource == null ? "<%s> %s" : "<%s> %s (%s)", //$NON-NLS-1$//$NON-NLS-2$
				eclass(), name(), resource);
	}

	private String eclass() {
		EClass eclass = element.eClass();

		// Simplify some known names
		if (eclass.getEPackage() == ArchitecturePackage.eINSTANCE) {
			switch (eclass.getClassifierID()) {
			case ArchitecturePackage.ARCHITECTURE_DOMAIN:
				return "Domain"; //$NON-NLS-1$
			case ArchitecturePackage.ARCHITECTURE_FRAMEWORK:
				return "Framework"; //$NON-NLS-1$
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE:
				return "ADL"; //$NON-NLS-1$
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT:
				return "Viewpoint"; //$NON-NLS-1$
			}
		}

		return eclass.getName();
	}

	private String name() {
		String result = element.getQualifiedName();
		if (result == null) {
			result = element.getName();
		}
		return result;
	}

	private String resourceURI() {
		Resource resource = element.eResource();
		URI result = (resource == null) ? null : resource.getURI();
		if (result != null) {
			result = result.trimFileExtension(); // Don't need the '.architecture'
		}

		return (result == null) ? null : result.toString();
	}

}
