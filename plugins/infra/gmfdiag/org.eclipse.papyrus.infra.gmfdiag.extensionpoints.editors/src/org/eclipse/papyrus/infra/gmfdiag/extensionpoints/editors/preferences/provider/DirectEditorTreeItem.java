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
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint;

/**
 * 
 * Simple class to store informations to display in preferences tree.
 * 
 * @author Gabriel Pascual
 *
 */
public class DirectEditorTreeItem {

	/** The meta class to edit. */
	private String metaClassToEdit;

	/** The configurations list. */
	private List<IDirectEditorExtensionPoint> configurationsList;

	/**
	 * Constructor.
	 *
	 * @param configurations
	 *            the configurations
	 */
	public DirectEditorTreeItem(List<IDirectEditorExtensionPoint> configurations) {
		metaClassToEdit = configurations.get(0).getObjectToEdit();
		configurationsList = configurations;

	}

	/**
	 * Gets all registered constraints on Meta Class.
	 *
	 * @return List of registered constraints
	 */
	public List<IDirectEditorConstraint> getMetaClassConstraints() {
		List<IDirectEditorConstraint> constraintsList = new ArrayList<IDirectEditorConstraint>();

		for (IDirectEditorExtensionPoint configuration : configurationsList) {
			IDirectEditorConstraint constraint = configuration.getAdditionalConstraint();
			if (constraint != null) {
				constraintsList.add(constraint);
			}
		}
		return constraintsList;
	}

	/**
	 * Gets the qualified name of the Meta Class to edit.
	 *
	 * @return The qualified name of the Meta Class
	 */
	public String getMetaClassToEdit() {
		return metaClassToEdit;
	}

	/**
	 * Gets all Direct Editor configuration associated with the Meta Class
	 *
	 * @return the configurations
	 */
	public List<IDirectEditorExtensionPoint> getConfigurations() {
		List<IDirectEditorExtensionPoint> nonConstraintCopnfiguration = new ArrayList<IDirectEditorExtensionPoint>();

		for (IDirectEditorExtensionPoint configuration : configurationsList) {
			if (configuration.getAdditionalConstraint() == null) {
				nonConstraintCopnfiguration.add(configuration);
			}
		}
		return nonConstraintCopnfiguration;
	}

	/**
	 * @param selectedElement
	 * @return
	 */
	public List<IDirectEditorExtensionPoint> getConstrainedEditor(IDirectEditorConstraint selectedElement) {
		List<IDirectEditorExtensionPoint> constrainedDirectEditorsList = new ArrayList<IDirectEditorExtensionPoint>();

		// Avoid useless exploration
		if (selectedElement != null) {
			for (IDirectEditorExtensionPoint configuration : configurationsList) {
				IDirectEditorConstraint additionalConstraint = configuration.getAdditionalConstraint();
				if (additionalConstraint != null && selectedElement.equals(additionalConstraint)) {
					constrainedDirectEditorsList.add(configuration);
				}
			}
		}
		return constrainedDirectEditorsList;
	}
}
