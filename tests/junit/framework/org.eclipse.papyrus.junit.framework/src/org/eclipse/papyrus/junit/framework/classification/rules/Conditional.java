/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.junit.framework.classification.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Test;


/**
 * An annotation for {@linkplain Test test methods} that are to be executed conditionally. The {@link #key() key} indicates a field or method
 * annotated with {@link Condition @Condition}. If the condition evaluates {@code false}, the test case is skipped.
 *
 * @see Condition
 * @see ConditionRule
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Conditional {

	/**
	 * Identifies a condition field or method annotated with {@link Condition @Condition}, which either specifies an explicit
	 * {@linkplain Condition#key() key} or implicit by field/method name.
	 *
	 * @return the key of the condition to evaluate
	 */
	String key();
}
