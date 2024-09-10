/*******************************************************************************
 * Copyright (c) 2008, 2018 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *     Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *******************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.ui;

/**
 * This action implementation is an abstract implementation of an object contribution action that
 * opens a TEF editor window within a host editor. Inheriting concrete implementations have to
 * provide the EMF object to be edited, the position of the editor window, access to the hosting GUI
 * control and edit part.
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.OpenEmbeddedTextEditorObjectActionDelegate} instead.
 */
@Deprecated
public abstract class OpenEmbeddedTextEditorObjectActionDelegate extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.OpenEmbeddedTextEditorObjectActionDelegate {

}
