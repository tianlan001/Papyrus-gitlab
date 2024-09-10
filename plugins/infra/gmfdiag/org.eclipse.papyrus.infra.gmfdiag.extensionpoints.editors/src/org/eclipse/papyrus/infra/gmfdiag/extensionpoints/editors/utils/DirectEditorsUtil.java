/*****************************************************************************
 * Copyright (c) 2008, 2018 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) Remi.Schnekenburger@cea.fr - Initial API and implementation
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Bug 528199
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.DefaultDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.DirectEditorExtensionPoint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint;

public class DirectEditorsUtil {

	/** The Constant UNKNOWN_PRIORITY. */
	private static final int UNKNOWN_PRIORITY = 100;

	/**
	 * Find an editor configuration for specific language and object to edit
	 *
	 * @param language
	 *            the language to edit
	 * @param semanticObjectToEdit
	 *            the semantic object to edit
	 * @return the editor configuration
	 * @since 3.0
	 */
	public static IDirectEditorConfiguration findEditorConfiguration(String language, EObject objectToEdit) {
		return findEditorConfiguration(language, objectToEdit, objectToEdit);
	}

	/**
	 * Find an editor configuration for specific language and object to edit
	 *
	 * @param language
	 *            the language to edit
	 * @param semanticObjectToEdit
	 *            the semantic object to edit
	 * @param selectedElement
	 *            The real selected element (e.g. widget, edit part, ...)
	 * @return the editor configuration
	 */
	public static IDirectEditorConfiguration findEditorConfiguration(String language, Object semanticObjectToEdit, Object selectedElement) {
		IDirectEditorExtensionPoint extension = findEditorExtension(language, semanticObjectToEdit, selectedElement);
		if (extension != null) {
			return extension.getDirectEditorConfiguration();
		}
		// none found, return default
		return new DefaultDirectEditorConfiguration();
	}

	/**
	 * Find an editor extension for a specific language and object to edit
	 * 
	 * @param language
	 *            the language to edit, if null accept all languages
	 * @param semanticObjectToEdit
	 *            the semantic object to edit
	 * @param selectedElement
	 *            The real selected element (e.g. widget, edit part, ...)
	 * @return the extension point that manages this kind of editor
	 * @since 3.0
	 */
	public static IDirectEditorExtensionPoint findEditorExtension(String language, Object semanticObjectToEdit, Object selectedElement) {
		Collection<IDirectEditorExtensionPoint> configs = getDirectEditorExtensions(semanticObjectToEdit, selectedElement);

		IDirectEditorExtensionPoint currentExtension = null;
		int currentPriority = UNKNOWN_PRIORITY;
		for (IDirectEditorExtensionPoint extensionPoint : configs) {
			if ((language == null || language.equals(extensionPoint.getLanguage())) && extensionPoint.getPriority() < currentPriority) {
				currentExtension = extensionPoint;
				currentPriority = extensionPoint.getPriority();
			}
		}

		if (currentExtension == null && language != null) {
			// no extension found, retry without specific language filter. This is useful, since the default editor
			// might not match, e.g. if you edit an opaque expression with a non-OCL body, the OCL editor should not
			// be used, see bug 528199
			return findEditorExtension(null, semanticObjectToEdit, selectedElement);
		}
		return currentExtension;
	}

	/**
	 * finds if an editor for specific language and object is available to edit type
	 *
	 * @param language
	 *            the language to edit
	 * @param semanticObjectToEdit
	 *            the semantic object to edit
	 * @param selectedElement
	 *            The real selected element (e.g. widget, edit part, ...)
	 * @return <code>true</code> if an editor exists
	 */
	public static boolean hasSpecificEditorConfiguration(String language, Object semanticObjectToEdit, Object selectedElement) {
		return findEditorExtension(language, semanticObjectToEdit, selectedElement) != null;
	}

	/**
	 * finds whether an editor for specific object is available to edit
	 * 
	 * @param objectToEdit
	 * @return <code>true</code> if an editor exists
	 * @since 3.0
	 */
	public static boolean hasSpecificEditorConfiguration(EObject objectToEdit) {
		return hasSpecificEditorConfiguration(objectToEdit, objectToEdit);
	}

	/**
	 * finds whether an editor for a specific object is available to edit
	 *
	 * @param language
	 *            the language to edit
	 * @param semanticObjectToEdit
	 *            the semantic object to edit
	 * @param selectedElement
	 *            The real selected element (e.g. widget, edit part, ...)
	 * @return <code>true</code> if an editor exists
	 */
	public static boolean hasSpecificEditorConfiguration(Object semanticObjectToEdit, Object selectedElement) {
		return getDirectEditorExtensions(semanticObjectToEdit, selectedElement).size() > 0;
	}

	/**
	 * Retrieves the preferred editor extension for the specified type
	 *
	 * @param class_
	 *            the type of element to edit
	 * @return the preferred editor extension for the specified or <code>null</code>
	 * @since 3.0
	 */
	public static IDirectEditorExtensionPoint getDefaultDirectEditorExtension(Object semanticObjectToEdit) {
		return getDefaultDirectEditorExtension(semanticObjectToEdit, semanticObjectToEdit);
	}

	/**
	 * Retrieves the preferred editor extension for the specified type
	 *
	 * @param semanticObjectToEdit
	 *            the object that should be edited
	 * @param selectedObject
	 *            the currently selected object
	 * @return the default editor extension for the passed elements or <code>null</code>
	 * @since 3.0
	 */
	public static IDirectEditorExtensionPoint getDefaultDirectEditorExtension(Object semanticObjectToEdit, Object selectedObject) {
		// retrieves preference for this element
		String language = Activator.getDefault().getPreferenceStore().getString(IDirectEditorsIds.EDITOR_FOR_ELEMENT + semanticObjectToEdit.getClass().asSubclass(EObject.class));
		if (IDirectEditorsIds.SIMPLE_DIRECT_EDITOR.equals(language)) {
			return null;
		}
		return findEditorExtension(language, semanticObjectToEdit, selectedObject);
	}

	/**
	 * Return the set of editor extensions registered in the platform for the specified kind of element
	 *
	 * @param semanticObjectToEdit
	 *            the object that should be edited
	 * @param selectedObject
	 *            the currently selected object
	 * @return the set of editor extension registered in the platform for the specified elements
	 * @since 3.0
	 */
	public static Collection<IDirectEditorExtensionPoint> getDirectEditorExtensions(Object semanticObjectToEdit, Object selectedObject) {
		// list of extension points to be returned.
		final List<IDirectEditorExtensionPoint> editorExtensionPoints = new ArrayList<IDirectEditorExtensionPoint>();

		// check each configuration and select those satisfying the constraints
		for (IDirectEditorExtensionPoint configuration : DirectEditorExtensionPoint.getInstance().getDirectEditorConfigurations()) {
			// both class are compatibles ?
			if (configuration.getObjectClassToEdit() != null) {
				if (configuration.getObjectClassToEdit().isInstance(semanticObjectToEdit)) {
					IDirectEditorConstraint constraint = configuration.getAdditionalConstraint();
					if (constraint == null || constraint.appliesTo(selectedObject))
						editorExtensionPoints.add(configuration);
				}
			}
		}
		return editorExtensionPoints;
	}
}
