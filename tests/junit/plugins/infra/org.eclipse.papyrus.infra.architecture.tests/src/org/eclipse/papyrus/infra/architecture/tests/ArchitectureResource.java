/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.tests;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates a test class or method with an architecture resource to load.
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(ArchitectureResource.ArchitectureResources.class)
public @interface ArchitectureResource {

	/** The resource name, without the '.architecture' extension. */
	String value();
	
	//
	// Nested types
	//
	
	/**
	 * Aggregation of repeated {@link ArchitectureResource} annotations.
	 */
	@Retention(RUNTIME)
	@Target({TYPE, METHOD})
	public @interface ArchitectureResources {
		ArchitectureResource[] value();
	}
	
}
