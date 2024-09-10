/******************************************************************************
 * Copyright (c) 2006, 2007 Borland Software Corp.
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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;

/**
 * @author dstadnik
 */
public class ExtensibleModelSelectionPage extends ModelSelectionPage {

	private Map<String, ModelSelectionPageExtension> extensions; // id -> extension

	public ExtensibleModelSelectionPage(String pageId, ResourceLocationProvider rloc, ResourceSet resourceSet) {
		this(pageId, rloc, resourceSet, null);
	}

	public ExtensibleModelSelectionPage(String pageId, ResourceLocationProvider rloc, ResourceSet resourceSet, String modelFileExtension) {
		super(pageId, rloc, resourceSet, modelFileExtension);
		extensions = new LinkedHashMap<String, ModelSelectionPageExtension>();
		addExtensions();
	}

	protected void addExtensions() {
	}

	public void addExtension(String id, ModelSelectionPageExtension extension) {
		assert id != null;
		assert extension != null;
		assert getControl() == null;
		extensions.put(id, extension);
	}

	public ModelSelectionPageExtension getExtension(String id) {
		return extensions.get(id);
	}

	@Override
	public void createAdditionalControls(Composite parent) {
		for (ModelSelectionPageExtension extension : extensions.values()) {
			extension.createControl(parent);
		}
	}

	@Override
	protected void resourceChanged() {
		for (ModelSelectionPageExtension extension : extensions.values()) {
			extension.setResource(getResource());
		}
	}
}
