/******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.ui;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Composite;

/**
 * Extension slot for the model selection page.
 * 
 * @author dstadnik
 */
public interface ModelSelectionPageExtension {

	/**
	 * Create additional control(s) inside model selection page.
	 */
	public void createControl(Composite parent);

	/**
	 * New model was selected for the page.
	 * Called by page so extension could update controls.
	 */
	public void setResource(Resource resource);
}
