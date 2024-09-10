/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 441962
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration;

/**
 * Constraint for a Direct Editor.
 * 
 * @author Camille Letavernier
 *
 */
public interface IDirectEditorConstraint {

	/**
	 * Label which define the constrained. This label is displayed in preference page
	 * and it is used to persist the preferences.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Check if the input object (current selection) corresponds to the defined constraint.
	 *
	 * @param selection
	 *            the current selection
	 * @return <code>true</code> if selection matches the constraint, otherwise <code>false</code>
	 */
	public boolean appliesTo(Object selection);

}
