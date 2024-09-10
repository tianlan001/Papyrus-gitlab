/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
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
 * <p>
 * Annotates a test or test class with a file to overlay on the {@link TestProject project content}
 * after that has initially been populated. The file may already exist in the base project template,
 * in which case it is replaced with new content, or it may be added as a new file in the project.
 * </p>
 * <p>
 * Note that, currently, only the main test project may
 * have files added or replaced by this mechanism
 * </p>
 *
 * @see TestProjectFixture
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OverlayFile.OverlayFiles.class)
public @interface OverlayFile {

	/**
	 * The source path of the file to overlay on the project, relative to the {@code resources/} folder.
	 */
	String value();

	/**
	 * The path of the file to create in the project. If omitted, the file is created at the same path
	 * relative to the project as the source is relative to the first-level nested folder of the
	 * {@code resources/} folder in the bundle in which the source file is contained. If the source
	 * file is a direct member of the {@code resources/} folder, then it is created in the root of
	 * the project.
	 */
	String path() default "";

	//
	// Nested types
	//

	/**
	 * Container of the repeatable {@code OverlayFile} annotation.
	 */
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface OverlayFiles {
		OverlayFile[] value();
	}

}
