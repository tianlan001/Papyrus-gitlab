/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.services;

import org.eclipse.papyrus.infra.core.services.IService;

/**
 * The LifecycleManager for IMultiDiagramEditor
 *
 * It notifies its listeners when the state of the editor changes
 *
 * @author Camille Letavernier
 * @since 1.2
 *
 */
public interface EditorLifecycleManager extends IService {

	public void addEditorLifecycleEventsListener(EditorLifecycleEventListener listener);

	public void removeEditorLifecycleEventsListener(EditorLifecycleEventListener listener);

}
