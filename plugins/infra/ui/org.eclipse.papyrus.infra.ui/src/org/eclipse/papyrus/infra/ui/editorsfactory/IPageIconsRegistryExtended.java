/**
 *  Copyright (c) 2011 Atos Origin.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Atos Origin - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.ui.editorsfactory;

/**
 * Extends IPageIconsRegistry in order to offer a second methods which will give back the URL of the requested Icon
 *
 * @author "Arthur Daussy <a href="mailto:arthur.daussy@atos.net">arthur.daussy@atos.net</a>"
 * @since 1.2
 *
 */
public interface IPageIconsRegistryExtended extends IPageIconsRegistry {

	/**
	 * Get the URL icon associated to the editor used to render the model. Model represent the top level
	 * object of a model editor.
	 *
	 * @param model
	 * @return {@link String} which represent the URL of the resource
	 */
	public String getEditorURLIcon(Object model);

}
