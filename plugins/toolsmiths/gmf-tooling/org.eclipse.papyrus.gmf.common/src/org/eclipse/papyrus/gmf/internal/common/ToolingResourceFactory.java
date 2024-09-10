/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : ignore gmfgen load and save option override 
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.emf.resources.AbstractEMFResource;

/**
 * Resource factory for the tooling models.
 * Specifies very small line length value in xml so each attribute is positioned on
 * it's own line thus making merging easier in standard workbench text merger.
 * 
 * @author dstadnik
 */
public class ToolingResourceFactory extends XMIResourceFactoryImpl {


	public Resource createResource(URI uri) {
		return new ToolResource(uri);
	}


	public static class ToolResource extends AbstractEMFResource {

		public ToolResource(URI uri) {
			super(uri);
		}

		@Override
		public Map<Object, Object> getDefaultSaveOptions() {
			// FIXME eal java.lang.NullPointerException
			// at org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl.getDatatypeValue(XMLSaveImpl.java:3107)
			// Error on Estring resolution on GenDiagram attribute (EDAtatype)
			// Map<Object, Object> saveOptions = super.getDefaultSaveOptions();

			if (defaultSaveOptions == null) {
				defaultSaveOptions = new HashMap<Object, Object>();
			}
			Map<Object, Object> saveOptions = defaultSaveOptions;

			// Format xml so each attribute is positioned on it's own line.
			saveOptions.put(XMLResource.OPTION_LINE_WIDTH, Integer.valueOf(1));

			// Do not mark all resources referenced from resourceset as changed on any change
			// (e.g. saving .gmfmap should not result in changed .gmfgraph and .gmftool)
			saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

			// It doesn't hurt to always allow to save schemaLocation
			// (is't not written unless there's model instance in use, with metamodel not from a registry).
			// This helps when GMF model refer to or extended by dynamic model instances.
			// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=215282
			saveOptions.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

			saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
			saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

			return saveOptions;
		}
	}
}
