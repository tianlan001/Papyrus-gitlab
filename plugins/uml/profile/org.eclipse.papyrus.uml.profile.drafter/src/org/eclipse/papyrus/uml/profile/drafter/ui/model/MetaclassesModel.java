/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Class;


/**
 * Model representing a Collection of Metaclasses.
 * This model is here for Histiric reasons. It may be replaced by List<MetaclassModel>.
 * 
 * @author cedric dumoulin
 *
 */
public class MetaclassesModel {

	/**
	 * Constructor.
	 *
	 */
	public MetaclassesModel() {
		
	}

	/**
	 * Metaclasses required by the user before modifying the Stereotype.
	 */
	private List<Class> requiredMetaclasses = new ArrayList<Class>();
	
	/**
	 * Metaclasses selected by the user.
	 */
	private List<Class> selectedMetaclasses = new ArrayList<Class>();

	
	/**
	 * @return the requiredMetaclasses
	 */
	public List<Class> getRequiredMetaclasses() {
		return requiredMetaclasses;
	}

	
	/**
	 * @return the selectedMetaclasses
	 */
	public List<Class> getSelectedMetaclasses() {
		return selectedMetaclasses;
	}
}
