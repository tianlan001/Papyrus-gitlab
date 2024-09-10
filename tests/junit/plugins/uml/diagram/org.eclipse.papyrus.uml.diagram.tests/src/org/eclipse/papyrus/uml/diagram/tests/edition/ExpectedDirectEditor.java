/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.diagram.tests.edition;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.papyrus.uml.diagram.common.editpolicies.IDirectEdition;

/**
 * Annotates a direct-edit test case or test suite to indicate the expected direct
 * editor kind(s) for the element under test.
 */
@Retention(RUNTIME)
@Target({ METHOD, TYPE })
public @interface ExpectedDirectEditor {
	/**
	 * The default kind expected by tests that are not annotated:
	 * {@link IDirectEdition#DEFAULT_DIRECT_EDITOR}.
	 */
	int DEFAULT_MASK = IDirectEdition.DEFAULT_DIRECT_EDITOR;

	/**
	 * A bit-mask of the kind(s) of direct editor accepted by the test case as
	 * valid for the element under test.
	 * 
	 * @return a mask of the kinds enumerated in the {@link IDirectEdition} interface
	 */
	int value() default DEFAULT_MASK;
}
