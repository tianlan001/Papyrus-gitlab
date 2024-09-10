/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 573890
 *  
 * 
 */
package org.eclipse.papyrus.infra.nattable.representation;

import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation kind that depicts elements form a UML model as a table
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getCreationCommand <em>Creation Command</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.representation.RepresentationPackage#getPapyrusTable()
 * @model
 * @generated
 */
public interface PapyrusTable extends PapyrusRepresentationKind {
	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The configuration of the table
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Configuration</em>' reference.
	 * @see #setConfiguration(TableConfiguration)
	 * @see org.eclipse.papyrus.infra.nattable.representation.RepresentationPackage#getPapyrusTable_Configuration()
	 * @model required="true"
	 * @generated
	 */
	TableConfiguration getConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getConfiguration <em>Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(TableConfiguration value);

	/**
	 * Returns the value of the '<em><b>Creation Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a command that creates the table. The command must implement the org.eclipse.papyrus.infra.nattable.representation.ITableCreationCommand inteface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Creation Command</em>' attribute.
	 * @see #setCreationCommand(String)
	 * @see org.eclipse.papyrus.infra.nattable.representation.RepresentationPackage#getPapyrusTable_CreationCommand()
	 * @model annotation="http://www.eclipse.org/papyrus/infra/core/architecture classConstraint='bundleclass://org.eclipse.papyrus.infra.nattable.common/org.eclipse.papyrus.infra.nattable.common.internal.command.ITableCreationCommand'"
	 * @generated
	 */
	String getCreationCommand();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getCreationCommand <em>Creation Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Command</em>' attribute.
	 * @see #getCreationCommand()
	 * @generated
	 */
	void setCreationCommand(String value);

} // PapyrusTable
