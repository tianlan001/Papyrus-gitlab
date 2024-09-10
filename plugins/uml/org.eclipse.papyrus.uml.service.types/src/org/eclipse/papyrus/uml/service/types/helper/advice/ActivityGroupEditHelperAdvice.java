/*****************************************************************************
 * Copyright (c) 2011 Atos.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Atos - Initial API and implementation
 *   Arthur Daussy - Fit to UML 4.0.0 metamodel
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;

/**
 * Add the behavior for deletion of an activity group:
 * 1 Remove a group when delete from the "group" feature
 * 
 * @author adaussy
 * 
 */
@Deprecated // to be removed (be acreful of references from elementype model) empty since year 2015
public class ActivityGroupEditHelperAdvice extends AbstractEditHelperAdvice {

}
