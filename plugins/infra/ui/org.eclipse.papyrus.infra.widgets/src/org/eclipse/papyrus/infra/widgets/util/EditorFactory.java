/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.util;

import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * The editor factory to create value editor.
 *
 * @since 3.3
 */
@FunctionalInterface
public interface EditorFactory {
	AbstractValueEditor create(final Composite parent, final int style, final String label, final boolean commitOnFocusLost);
}
