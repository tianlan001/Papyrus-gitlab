/*****************************************************************************
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core;

import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.papyrus.infra.types.ContainerConfiguration;


/**
 * Descriptor for the configurable container descriptor
 */
public interface IConfiguredContainerDescriptor<T extends ContainerConfiguration> extends IContainerDescriptor {

	void init(T containerConfiguration);
}
