/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - bug 496176
 * 
 */
package org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Thread Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getMaxThreads <em>Max Threads</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getThreadConfig()
 * @model
 * @generated
 */
public interface ThreadConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getThreadConfig_Name()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Max Threads</b></em>' attribute.
	 * The default value is <code>"2"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The maximum number of threads to use during the migration.
	 * More threads will provide faster results, at the cost of memory consumption.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Threads</em>' attribute.
	 * @see #setMaxThreads(int)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getThreadConfig_MaxThreads()
	 * @model default="2" unique="false" dataType="org.eclipse.uml2.types.Integer" required="true" ordered="false"
	 * @generated
	 */
	int getMaxThreads();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getMaxThreads <em>Max Threads</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Threads</em>' attribute.
	 * @see #getMaxThreads()
	 * @generated
	 */
	void setMaxThreads(int value);

} // ThreadConfig
