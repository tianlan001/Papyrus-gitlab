/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.groups.tabbedproperties.appearance;

import org.eclipse.jface.viewers.IFilter;

/**
 * Filter for the {@link ChooseGraphicalParentSection} section
 *
 * @author vhemery
 */
public class ChooseGraphicalParentSectionFilter implements IFilter {

	/**
	 * Select the object only in case it is contained by several groups
	 *
	 * @param object
	 *            object to compare against the filter
	 * @return <code>true</code> if the object is accepted by the filter.
	 */
	public boolean select(Object object) {
		// FIXME enable when the group property section is developped
		return false;
		// Class<ChooseGraphicalParentSection> cl = ChooseGraphicalParentSection.class;
		// try {
		// cl.newInstance();
		// } catch (InstantiationException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// }
		// if(object instanceof IGraphicalEditPart) {
		// return GroupContainmentRegistry.isContainerConcerned((IGraphicalEditPart)object);
		// } else {
		// return false;
		// }
	}

}
