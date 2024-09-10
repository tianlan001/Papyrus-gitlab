/*******************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Bros (Mia-Software) - Bug 366567 - [Releng] Tool to update rmaps
 *     Camille Letavernier (CEA LIST) - camille.letavernier@cea.fr - Generalize to handle POMs
 *     Christian W. Damus (CEA) - Add support for updating Oomph setup models
 *     Christian W. Damus - Support updating of multiple selected files
 *
 *******************************************************************************/
package org.eclipse.papyrus.releng.tools.internal.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.releng.tools.internal.popup.actions.UpdateDependencies;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


public class UpdateDependenciesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Shell activeShell = HandlerUtil.getActiveShell(event);

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			List<IFile> filesToUpdate = ImmutableList.copyOf(Iterables.filter(structuredSelection.toList(), IFile.class));
			new UpdateDependencies().updateDependencies(filesToUpdate, activeShell);
		}

		return null;
	}

}
