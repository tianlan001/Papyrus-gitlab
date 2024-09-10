/**
* Copyright (c) 2018 CEA LIST.
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
package org.eclipse.papyrus.infra.core.architecture.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.emf.resources.AbstractEMFResourceWithUUID;

/**
 * This class is used to manage all file with the type architecture.
 */
public class ArchitectureResource extends AbstractEMFResourceWithUUID {
	/**
	 * Creates an instance of the resource.
	 */
	public ArchitectureResource(URI uri) {
		super(uri);
	}

} //ArchitectureResourceImpl
