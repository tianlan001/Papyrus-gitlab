/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.api;

/**
 * This is the super type of real editors contained by the sashEditor
 * see also PapyrusDeclaration useful to add it by extension point
 * in the runtime a IpapyrusEditorDeclaration becomes a IPapyrus Editor
 *
 * @see org.eclipse.papyrus.infra.core.sasheditor.api.IEditorPartWrapper
 * @see org.eclipse.papyrus.infra.core.sasheditor.api.ISWTCompositeWrapper
 */
public interface IPapyrusEditor {

}
