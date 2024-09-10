/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.eclipse.project.editors.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;

/**
 * Annotation for the {@link IProjectEditor#getMissingFiles()} test case to indicate
 * that the fixture should not create the files required by the project.
 * The missing-files annotation implies {@link CreatedProject @CreatedProject(false)};
 * the created-project annotation is ignored when the missing-files annotation
 * is present with a {@code true} value.
 * 
 * @see CreatedProject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MissingFiles {
	/**
	 * Whether the project's missing files should not be created (default is {@code true}).
	 */
	boolean value() default true;
}
