/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.tests;


/**
 * Constants for test classes
 */
public interface ITestConstants {
	static final String ORG_ECLIPSE_PAPYRUS_UML_CLASS = "org.eclipse.papyrus.uml.Class"; //$NON-NLS-1$

	final static String ABSTRACT_CLASS_TOOL = "org.eclipse.papyrus.infra.types.tests.aspectabstractclass"; //$NON-NLS-1$

	final static String ElementTypeConfigurationSetName_Test1 = "org.eclipse.papyrus.infra.types.tests.tests"; //$NON-NLS-1$

	final static String WORKSPACE_ELEMENT_TYPE_ID = "org.eclipse.papyrus.infra.types.tests.workspacetype"; //$NON-NLS-1$

	final static String WORKSPACE_ELEMENT_TYPE_TOOL = "org.eclipse.papyrus.infra.types.tests.tool"; //$NON-NLS-1$

	static final String PACKAGE_WITH_COMPONENTS = "PackageWithComponents"; //$NON-NLS-1$

	static final String PACKAGE_WITH_ALL_ELEMENTS = "PackageWithAllElements"; //$NON-NLS-1$

	static final String OTHER_PACKAGE_WITH_COMPONENTS = "OtherPackageWithComponents"; //$NON-NLS-1$

	static final String PACKAGE_WITH_COMPONENTS_TYPE_ID = "org.eclipse.papyrus.infra.types.tests.packagewithcomponentsonly"; //$NON-NLS-1$

	static final String SPECIFICCOMPONENT_TYPE_ID = "org.eclipse.papyrus.infra.types.tests.specificcomponent"; //$NON-NLS-1$

	static final String COMPONENT_NAME = "Component1"; //$NON-NLS-1$

	static final String CYCLIC_ADVICES_CONFIGURATIONS = "platform:/plugin/org.eclipse.papyrus.infra.types.tests/model/cyclicAdvices.elementtypesconfigurations";

	static final String VALID_ADVICES_ORDER_CONFIGURATIONS = "platform:/plugin/org.eclipse.papyrus.infra.types.tests/model/validAdvicesOrder.elementtypesconfigurations";

	static final String ADVICES_ID_1 = "org.eclipse.papyrus.infra.types.tests.TestEditHelperAdvice1";
	static final String ADVICES_ID_2 = "org.eclipse.papyrus.infra.types.tests.TestEditHelperAdvice2";
	static final String ADVICES_ID_3 = "org.eclipse.papyrus.infra.types.tests.TestEditHelperAdvice3";


}
