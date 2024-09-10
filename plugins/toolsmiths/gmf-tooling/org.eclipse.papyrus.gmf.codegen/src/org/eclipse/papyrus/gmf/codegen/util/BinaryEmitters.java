/******************************************************************************
 * Copyright (c) 2014, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.jet.JETCompiler;
import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;
import org.eclipse.papyrus.gmf.internal.common.codegen.BinaryEmitter;
import org.eclipse.papyrus.gmf.internal.common.codegen.GIFEmitter;
import org.eclipse.papyrus.gmf.internal.common.codegen.JETGIFEmitterAdapter;


/**
 * @since 2.11
 */
public class BinaryEmitters {
	
	private final URL myLocation;
	
	public BinaryEmitters() {
		myLocation = Platform.getBundle("org.eclipse.papyrus.gmf.codegen").getEntry("/templates/");
	}
	
	public BinaryEmitter getShortcutImageEmitter() throws UnexpectedBehaviourException {
		return newGIFEmitter("/xpt/editor/shortcut.gif"); //$NON-NLS-1$
	}
	
	public BinaryEmitter getGroupIconEmitter() throws UnexpectedBehaviourException {
		return newGIFEmitter("/xpt/navigator/navigatorGroup.gif"); //$NON-NLS-1$
	}
	
	public BinaryEmitter getDiagramIconEmitter() throws UnexpectedBehaviourException {
		return newGIFEmitterAdapter("/xpt/editor/diagram.gif"); //$NON-NLS-1$
	}
	
	public BinaryEmitter getWizardBannerImageEmitter() throws UnexpectedBehaviourException {
		return newGIFEmitterAdapter("/xpt/editor/wizban.gif"); //$NON-NLS-1$
	}
	
	private BinaryEmitter newGIFEmitter(String relativePath) throws UnexpectedBehaviourException {
		return new GIFEmitter(checkTemplateLocation(relativePath));
	}

	private BinaryEmitter newGIFEmitterAdapter(String relativePath) throws UnexpectedBehaviourException {
		return new JETGIFEmitterAdapter(new org.eclipse.emf.codegen.util.GIFEmitter(checkTemplateLocation(relativePath)));
	}

	private String checkTemplateLocation(String relativePath) throws UnexpectedBehaviourException {
		String[] templatesPath = new String[]{myLocation.toString()};
		String templateLocation = JETCompiler.find(templatesPath, relativePath);
		if (templateLocation == null) {
			throw new UnexpectedBehaviourException("Template " + relativePath + " not found");
		}
		return templateLocation;
	}
}
