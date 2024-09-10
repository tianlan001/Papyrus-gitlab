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
 * State indicating the life status of a model. Possible values are:
 * {@link #alive} The model is alived. Its values are taken into account.
 * {@link #deleted} The user has requested to delete the model. It will be deleted later (when all requested modifications will be applied).
 * 
 * @author cedric dumoulin
 *
 */
public enum LifeStatusKind {

	running, deleted;
}
