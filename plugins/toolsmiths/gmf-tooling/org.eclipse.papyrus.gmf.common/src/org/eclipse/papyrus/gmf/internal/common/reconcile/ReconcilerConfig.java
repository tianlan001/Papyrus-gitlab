/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Artem Tikhomirov (independent) support to handle not-matched new elements (Cleaner) 
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/

package org.eclipse.papyrus.gmf.internal.common.reconcile;

import org.eclipse.emf.ecore.EClass;

public interface ReconcilerConfig {
	Matcher getMatcher(EClass eClass); 
	Decision[] getDecisions(EClass eClass);
	Copier getCopier(EClass eClass);
	Cleaner getCleaner(EClass eClass);
}
