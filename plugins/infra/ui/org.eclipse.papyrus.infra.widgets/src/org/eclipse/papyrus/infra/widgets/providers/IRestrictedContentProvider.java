/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;


/**
 * Add a boolean to choose the display mode :
 * <ul>
 * <li>display all possible values according to the model (restricted==false)</li>
 * <li>display all possible values according to current edited object (restricted==true)</li>
 * </ul>
 *
 * @author Juan Cadavid
 *
 */
public interface IRestrictedContentProvider extends IHierarchicContentProvider, IStaticContentProvider, IInheritedElementContentProvider {

	/**
	 *
	 * @param isRestricted
	 */
	public void setRestriction(boolean isRestricted);

	/**
	 *
	 * @return
	 *         <code>true</code> if the content provider is restricted
	 */
	public boolean isRestricted();

}
