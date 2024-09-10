/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies;

import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomPropertyPartEditPartCN;


/**
 * This edit policy replaces the GMF generated edit policy for Property used as Part in composite structure diagram.
 * In particular it provides a getCreateCommands that add a ChangeBoundRequest on a created Port
 * in order to locate it at the cursor position.
 * This class inherits from StructuredClassifierLayoutEditPolicy which provides support for a correct placement of
 * port during creation.
 * An example of code generated by GMF can be found in {@link CustomPropertyPartEditPartCN#createLayoutEditPolicy()}.
 *
 * @since 3.0
 */
public class PropertyLayoutEditPolicy extends StructuredClassifierLayoutEditPolicy {

}
