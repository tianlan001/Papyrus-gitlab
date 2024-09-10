/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.ui.tests.creation.auxtests;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

public interface IAuxTest {
	
	/**
	 * @param container
	 * @param source
	 * @param target
	 * @param hintedType
	 * @param commandResult
	 */
	public abstract void test(EObject container, EObject source, EObject target, IHintedType hintedType, Collection<?> commandResult);
}
