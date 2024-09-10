/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants.PaletteModelContextEnum;
import org.eclipse.ui.IEditorPart;

/**
 * Utils for palette customization.
 *
 */
public class PaletteUtils {

	/**
	 * Save options for palette resources.
	 */
	public static final Map<Object, Object> saveOptions;
	static {
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);
		options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());
		saveOptions = Collections.unmodifiableMap(options);
	}

	/**
	 * set the status field which describe in which case of palette model we are.
	 * 
	 * @param descriptor
	 */
	public static PaletteModelContextEnum getPaletteModelContext(ProviderDescriptor descriptor) {
		PaletteModelContextEnum paletteContext;
		// Case 1: WS
		if (descriptor instanceof PapyrusPaletteService.WorkspaceExtendedProviderDescriptor) {
			paletteContext = PaletteModelContextEnum.Workspace;
		} else
		// case 2: plugin
		if (descriptor instanceof PapyrusPaletteService.ExtendedProviderDescriptor) {
			paletteContext = PaletteModelContextEnum.Plugin;
		} else
		// case 3: local
		if (descriptor instanceof PapyrusPaletteService.LocalExtendedProviderDescriptor) {
			paletteContext = PaletteModelContextEnum.Local;
		} else {
			// case 4 : new
			paletteContext = PaletteModelContextEnum.New;
		}
		return paletteContext;
	}

	/**
	 * @param the
	 *            file to test
	 * @return true if it exist and is readable.
	 */
	public static boolean notErrorOnFile(File file) {
		boolean error = false;
		if (null != file) {
			if (!file.exists()) {
				error = true;
			} else if (!file.canRead()) {
				error = true;
			}
		}

		return !error;
	}

	/**
	 * Get the Id value of the current editor
	 * 
	 * @return the editor id value as a String
	 */
	public static String getEditorIdValue(IEditorPart editorPart) {
		String editorIDValue = ""; //$NON-NLS-1$
		if (editorPart instanceof DiagramEditorWithFlyOutPalette) {
			editorIDValue = ((DiagramEditorWithFlyOutPalette) editorPart).getContributorId();
		}
		return editorIDValue;
	}

}
