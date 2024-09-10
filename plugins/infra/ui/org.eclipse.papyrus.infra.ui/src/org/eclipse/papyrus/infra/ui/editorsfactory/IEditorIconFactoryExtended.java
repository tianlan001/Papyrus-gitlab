/**
 *  Copyright (c) 2011 Atos.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Atos - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.ui.editorsfactory;

/**
 *
 * @author "Arthur Daussy <a href="mailto:arthur.daussy@atos.net">arthur.daussy@atos.net</a>"
 * @since 1.2
 *
 */
public interface IEditorIconFactoryExtended extends IEditorIconFactory {

	/**
	 * Return the icon URL associated to the editor used to render the model. Model represent the top level
	 * object of a model editor.
	 *
	 * @param pageIdentifier
	 * @return
	 */
	public String getURLMainIcon(Object pageIdentifier);
}
