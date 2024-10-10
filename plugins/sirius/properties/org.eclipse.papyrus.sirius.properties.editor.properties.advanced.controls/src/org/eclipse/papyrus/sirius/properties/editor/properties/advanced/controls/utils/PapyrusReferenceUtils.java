/*****************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.utils;

/**
 * Utility class containing the constants related to the Papyrus-Sirius Reference widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class PapyrusReferenceUtils {

	/**
	 * The name of the variable which will hold the type name of the Element to create.
	 * <p>
	 * This type name can come from two ways :
	 * <ul>
	 * <li>selected by the user in {@link PapyrusEEFExtEObjectCreationWizard} wizard,</li>
	 * <li>the type extracted from the Papyrus reference widget when there is only one type possible.</li>
	 */
	public static final String NEW_ELEMENT_TYPE_NAME = "newElementTypeName"; //$NON-NLS-1$

	/**
	 * The name of the variable which will hold the container of the Element to create.
	 * <p>
	 * This container can come from two ways :
	 * <ul>
	 * <li>selected by the user in {@link PapyrusEEFExtEObjectCreationWizard} wizard after choosing the type,</li>
	 * <li>the container displayed by the Papyrus reference widget for containment reference.</li>
	 * </ul>
	 */
	public static final String NEW_ELEMENT_CONTAINER = "newElementContainer"; //$NON-NLS-1$

	/**
	 * The name of the variable which will hold the container feature to refer the Element to create.
	 * <p>
	 * This feature name can come from two ways :
	 * <ul>
	 * <li>selected by the user in {@link PapyrusEEFExtEObjectCreationWizard} wizard after choosing the container,</li>
	 * <li>the feature displayed by the Papyrus reference widget for containment reference.</li>
	 * </ul>
	 */
	public static final String NEW_ELEMENT_CONTAINER_FEATURE_NAME = "newElementContainerFeatureName"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 */
	private PapyrusReferenceUtils() {
		// prevent instantiation
	}
}
