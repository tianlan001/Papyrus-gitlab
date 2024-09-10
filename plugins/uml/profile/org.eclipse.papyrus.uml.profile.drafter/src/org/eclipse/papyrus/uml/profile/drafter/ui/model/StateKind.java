/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.model;


/**
 * States of an object: created, loaded, modified
 * <ul>
 *   <li>created: Model is created by user.</li>
 *   <li>loaded: model is loaded from an existing Object (Property, Value, ...). No change has happen.</li>
 *   <li>modified: loaded model is modified.</li>
 * </ul>
 * @author cedric dumoulin
 *
 */
public enum StateKind {

	created, loaded, modified;
}
