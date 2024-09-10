/**
* Copyright (c) 2017 CEA LIST.
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
 *  
 * 
 */
package org.eclipse.papyrus.infra.core.architecture;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Framework</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A framework (from ISO 42010) represents in Papyrus a modeling framework (e.g., DoDAF). It has a unique id' that corresponds to that of an ''IClientContext from GMF. It references a set of element type set configurations, a creation command (creates a model of this framework), a conversion command (converts a model to this framework), and an icon. It also has an optional extension prefix for its models. It can also contain a set of viewpoints.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureFramework()
 * @model
 * @generated
 */
public interface ArchitectureFramework extends ArchitectureContext {
} // ArchitectureFramework
