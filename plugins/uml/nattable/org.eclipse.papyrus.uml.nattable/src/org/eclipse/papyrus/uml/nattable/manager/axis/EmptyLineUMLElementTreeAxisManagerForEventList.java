/*****************************************************************************
 * Copyright (c) 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 563217
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.manager.axis;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;

/**
 * TODO : not yet tested with no {@link TreeFillingConfiguration} for depth==0
 * Axis Manager with one empty line at each level
 *
 * @since 5.4
 */
public class EmptyLineUMLElementTreeAxisManagerForEventList extends UMLElementTreeAxisManagerForEventList {

	/**
	 * boolean indicating if we need to create empty row or not under each feature row
	 */
	private boolean createEmptyRow = false;// false, by default, because in Papyrus we don't get this behavior

	/**
	 *
	 * @param createEmptyLine
	 *            if <code>true</code> the axis manager will create an empty line at each level
	 */
	public void setCreateEmptyRow(final boolean createEmptyRow) {
		this.createEmptyRow = createEmptyRow;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#addObject(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis, java.lang.Object)
	 *
	 * @param parentAxis
	 * @param objectToAdd
	 * @return
	 */
	@Override
	protected ITreeItemAxis addObject(ITreeItemAxis parentAxis, Object objectToAdd) {
		final ITreeItemAxis createdAxis = super.addObject(parentAxis, objectToAdd);
		if (this.createEmptyRow) {
			if (objectToAdd instanceof TreeFillingConfiguration) {
				super.addObject(createdAxis, null);
			}
		}
		return createdAxis;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillChildrenForSemanticElement(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 */
	@Override
	protected void fillChildrenForSemanticElement(final ITreeItemAxis axis) {
		if (this.createEmptyRow) {
			if (axis == null) {
				int nextDepth = 0;
				Object context = getTableContext();
				final List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getTreeFillingConfigurationForDepth(getTable(), representedAxisManager, nextDepth);
				for (TreeFillingConfiguration current : confs) {
					final Collection<?> values = getFilteredValueAsCollection(current, context, nextDepth);
					// with empty line, we always create a new axis for the TreeFillingConfiguration
					ITreeItemAxis newAxis = addObject(axis, current);
					if (nextDepth == 0) {
						for (Object curr : values) {
							addObject(newAxis, curr);
						}
					}
				}
			}
			if (axis != null && axis.getElement() != null && axis.getChildren().size() == 0) {
				int nextDepth = getSemanticDepth(axis) + 1;
				Object context = axis.getElement();
				Assert.isTrue(!(context instanceof TreeFillingConfiguration));
				final List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getTreeFillingConfigurationForDepth(getTable(), representedAxisManager, nextDepth);
				for (TreeFillingConfiguration current : confs) {
					// with empty line, we always create a new axis for the TreeFillingConfiguration
					ITreeItemAxis newAxis = addObject(axis, current);

					final Collection<?> values = getFilteredValueAsCollection(current, context, nextDepth);
					if (values.size() != 0) {
						if (nextDepth == 0) {
							for (Object curr : values) {
								addObject(newAxis, curr);
							}
						}
					}
				}
			}
		} else {
			super.fillChildrenForSemanticElement(axis);
		}

	}
}
