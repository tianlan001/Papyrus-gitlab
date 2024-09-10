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
package org.eclipse.papyrus.infra.ui.editor.reload;

/**
 * Protocol implemented by {@link EditorReloadEvent} context objects that must be disposed when they are no longer needed.
 *
 * @see EditorReloadEvent#dispose()
 * @since 1.2
 */
public interface IDisposableReloadContext {

	void dispose();
}
