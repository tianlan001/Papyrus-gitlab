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
 *   Vincent LORENZO (CEA LIST) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.emf.resources;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * @author Vincent LORENZO
 *         Abstract XMI Resource to extends for EMF model Resource. The goal is to provides the same options (save and load) for the Papyrus EMF models:
 *         <ul>
 *         <li>use ID, instead of position to reference element</li>
 *         <li>common formating rules (ignoring the options given by the editors)</li>
 *         <li>encoding : UTF-8</li>
 *         <li>default values are saved (allow to prevent troubles when default value changed)</li>
 *         <li>...</li>
 *         </ul>
 */
public abstract class AbstractEMFResource extends XMIResourceImpl {

	/**
	 * 
	 * Constructor.
	 *
	 * @param uri
	 */
	public AbstractEMFResource(final URI uri) {
		super(uri);
	}

	/**
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#save(java.util.Map)
	 *
	 * @param options
	 * @throws IOException
	 */
	@Override
	public final void save(Map<?, ?> options) throws IOException {
		super.save(getDefaultSaveOptions());// we bypass the options argument to avoid changes between editors (ExpressionEditor, Ecore Sample Reflexive Editor and Ecore Editor
	}

	/**
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#load(java.util.Map)
	 *
	 * @param options
	 * @throws IOException
	 */
	@Override
	public final void load(Map<?, ?> options) throws IOException {
		super.load(getDefaultLoadOptions());// we bypass the options argument to avoid changes between editors (ExpressionEditor, Ecore Sample Reflexive Editor and Ecore Editor
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#getDefaultSaveOptions()
	 *
	 * @return
	 */
	@Override
	public Map<Object, Object> getDefaultSaveOptions() {
		if (null == this.defaultSaveOptions) {
			super.getDefaultSaveOptions();// initialize the default save options
			this.defaultSaveOptions.putAll(LoadAndSaveOptionsUtils.getSaveOptions());
		}
		return this.defaultSaveOptions;

	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#getDefaultLoadOptions()
	 *
	 * @return
	 */

	@Override
	public Map<Object, Object> getDefaultLoadOptions() {
		if (null == this.defaultLoadOptions) {
			super.getDefaultLoadOptions(); // initialize the default load options
			this.defaultLoadOptions.putAll(LoadAndSaveOptionsUtils.getLoadOptions());
		}
		return this.defaultLoadOptions;
	}
	
}
