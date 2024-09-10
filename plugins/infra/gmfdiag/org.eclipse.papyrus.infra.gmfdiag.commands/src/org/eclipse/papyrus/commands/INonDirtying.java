/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.commands;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;


/**
 * A marker interface for GMF and GEF commands that are non-dirtying. For EMF {@link Command}s, use the {@link AbstractCommand.NonDirtying} interface.
 *
 * @see AbstractCommand.NonDirtying
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying} API, instead.
 */
@Deprecated
public interface INonDirtying extends org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying {
	// Just a marker interface
}
