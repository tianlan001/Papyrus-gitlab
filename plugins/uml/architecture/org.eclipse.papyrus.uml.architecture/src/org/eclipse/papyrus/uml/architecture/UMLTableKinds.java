/*****************************************************************************
 * Copyright (c) 2018 Maged Elaasar, CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 Maged Elaasar - Initial API and Implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.architecture;

/**
 * The kinds of UML tables in the UML Architecture Language
 * 
 */
public interface UMLTableKinds {

	public static final String GENERIC_TREE_TABLE = "org.eclipse.papyrus.uml.table.genericTree";

	public static final String CLASS_TREE_TABLE = "org.eclipse.papyrus.uml.table.classTree";
	
	public static final String GENERIC_TABLE = "org.eclipse.papyrus.uml.table.generic";
	
	public static final String STEREO_DISPLAY_TREE_TABLE = "org.eclipse.papyrus.uml.table.stereotypeDisplayTree";

	public static final String VIEW_TABLE = "org.eclipse.papyrus.uml.table.view";
	
	public static final String RELATIONSHIP_GENERIC_MATRIX = "org.eclipse.papyrus.uml.table.matrix";

}
