/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;

import com.google.common.base.Functions;

/**
 * A simple marker resolution that adds an extension element that was missing in the <tt>plugin.xml</tt>.
 */
public final class SimpleMissingExtensionMarkerResolution extends AbstractMissingExtensionMarkerResolution {

	private final String label;
	private final String description;
	private final Function<? super IMarker, String> extensionPointFunction;
	private final Function<? super IMarker, String> elementNameFunction;
	private final Function<? super IMarker, ? extends Map<String, String>> elementAttributesFunction;

	/**
	 * Iniitalizes me with everything required to complete the resolution.
	 *
	 * @param problemID
	 *            the problem ID that I fix
	 * @param label
	 *            my label to present to the user
	 * @param description
	 *            my description to present to the user
	 * @param extensionPointFunction
	 *            a function that computes the extension point on which I add a new extension element from the problem marker
	 * @param elementNameFunction
	 *            a function that computes the name of the element to create from the problem marker
	 * @param elementAttributesFunction
	 *            a function that computes the attribute values to set in the element from the problem marker
	 */
	public SimpleMissingExtensionMarkerResolution(int problemID, String label, String description,
			Function<? super IMarker, String> extensionPointFunction,
			Function<? super IMarker, String> elementNameFunction,
			Function<? super IMarker, ? extends Map<String, String>> elementAttributesFunction) {

		super(problemID);

		this.label = label;
		this.description = description;
		this.extensionPointFunction = extensionPointFunction;
		this.elementNameFunction = elementNameFunction;
		this.elementAttributesFunction = elementAttributesFunction;
	}

	/**
	 * Iniitalizes me with static element name and attributes that do not depend on the details of the problem
	 * marker that I fix.
	 *
	 * @param problemID
	 *            the problem ID that I fix
	 * @param label
	 *            my label to present to the user
	 * @param description
	 *            my description to present to the user
	 * @param extensionPoint
	 *            the extension point on which I add a new extension element
	 * @param elementName
	 *            the name of the element to create from the problem marker
	 * @param elementAttributes
	 *            the attribute values to set in the element from the problem marker
	 */
	public SimpleMissingExtensionMarkerResolution(int problemID, String label, String description, String extensionPoint,
			String elementName, Map<String, String> elementAttributes) {

		this(problemID, label, description, Functions.constant(extensionPoint), Functions.constant(elementName),
				Functions.constant(elementAttributes));
	}

	/**
	 * Iniitalizes me with everything required to complete the resolution.
	 *
	 * @param problemID
	 *            the problem ID that I fix
	 * @param label
	 *            my label to present to the user
	 * @param description
	 *            my description to present to the user
	 * @param extensionPoint
	 *            the extension point on which I add a new extension element
	 * @param elementName
	 *            the name of the element to create from the problem marker
	 * @param elementAttributes
	 *            generators of the attribute values to set in the element from the problem marker
	 */
	@SafeVarargs
	public SimpleMissingExtensionMarkerResolution(int problemID, String label, String description,
			String extensionPoint, String elementName, BiConsumer<IMarker, Map<String, String>>... elementAttributes) {

		this(problemID, label, description, Functions.constant(extensionPoint), Functions.constant(elementName),
				attributes(elementAttributes));
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	protected String getExtensionPoint(IMarker marker) {
		return extensionPointFunction.apply(marker);
	}

	@Override
	protected void configureExtension(IPluginExtension extension, IMarker marker) throws CoreException {
		IPluginElement element = createElement(extension, elementNameFunction.apply(marker));
		elementAttributesFunction.apply(marker).forEach((name, value) -> setAttribute(element, name, value));
	}

	@SafeVarargs
	public static Function<IMarker, Map<String, String>> attributes(BiConsumer<IMarker, Map<String, String>>... attribute) {
		return marker -> {
			Map<String, String> result = new HashMap<>();
			for (BiConsumer<IMarker, Map<String, String>> next : attribute) {
				next.accept(marker, result);
			}
			return result;
		};
	}

	public static BiConsumer<IMarker, Map<String, String>> attribute(String name, String value) {
		return (marker, attributes) -> attributes.put(name, value);
	}

	public static BiConsumer<IMarker, Map<String, String>> attribute(Function<? super IMarker, String> name, String value) {
		return (marker, attributes) -> attributes.put(name.apply(marker), value);
	}

	public static BiConsumer<IMarker, Map<String, String>> attribute(String name, Function<? super IMarker, String> value) {
		return (marker, attributes) -> attributes.put(name, value.apply(marker));
	}

	public static BiConsumer<IMarker, Map<String, String>> attribute(Function<? super IMarker, String> name, Function<? super IMarker, String> value) {
		return (marker, attributes) -> attributes.put(name.apply(marker), value.apply(marker));
	}

	public static BiConsumer<IMarker, Map<String, String>> optionalAttribute(String name, Function<? super IMarker, Optional<String>> value) {
		return (marker, attributes) -> value.apply(marker).ifPresent(v -> attributes.put(name, v));
	}

	public static BiConsumer<IMarker, Map<String, String>> optionalAttribute(Function<? super IMarker, String> name, Function<? super IMarker, Optional<String>> value) {
		return (marker, attributes) -> value.apply(marker).ifPresent(v -> attributes.put(name.apply(marker), v));
	}

}
