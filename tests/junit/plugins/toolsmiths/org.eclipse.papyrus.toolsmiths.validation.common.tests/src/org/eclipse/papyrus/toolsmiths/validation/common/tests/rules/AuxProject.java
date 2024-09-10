/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.tests.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a test or test class with an auxiliary project to import after importing
 * the test project (thus it may depend on the test project). This is useful for tests
 * that verify validation rules that apply to project dependencies, class paths, or
 * other scenarios that cannot be tested in just a single project.
 *
 * @see TestProjectFixture
 * @see TestProject
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AuxProject.AuxProjects.class)
public @interface AuxProject {

	/** The path to the project template folder, relative to the {@code resources/} folder. */
	String value();

	/** The name of the project to create, in case it is different to the template folder name. */
	String as() default "";

	//
	// Nested types
	//

	/**
	 * Container of the repeatable {@code AuxProject} annotation.
	 */
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AuxProjects {
		AuxProject[] value();
	}

}
