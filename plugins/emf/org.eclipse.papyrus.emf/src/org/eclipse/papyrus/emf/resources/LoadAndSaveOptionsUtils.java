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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.emf.resources;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;

/**
 * @author Vincent LORENZO
 *
 *         This class provides the common load and save options to use in the Papyrus EMF models
 */
public final class LoadAndSaveOptionsUtils {

	/**
	 * the save options to use in Papyrus
	 */
	private static final Map<Object, Object> SAVE_OPTIONS;

	/**
	 * the load options to use in Papyrus
	 */
	private static final Map<Object, Object> LOAD_OPTIONS;

	static {

		// create the save options
		SAVE_OPTIONS = new HashMap<Object, Object>();
		// idem in Papyrus ModelSet
		SAVE_OPTIONS.put(XMLResource.OPTION_URI_HANDLER, new PapyrusURIHandler());


		// idem in MultiDiagramUtil
		SAVE_OPTIONS.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
		SAVE_OPTIONS.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

		// formating option about the size of the line in the file
		// options.put(XMLResource.OPTION_LINE_WIDTH, 10);
		SAVE_OPTIONS.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

		// to force the save of the default content
		SAVE_OPTIONS.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		SAVE_OPTIONS.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		SAVE_OPTIONS.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);

		// create the load options
		// the same as in Papyrus ModelSet
		LOAD_OPTIONS = new HashMap<Object, Object>();
		LOAD_OPTIONS.put(XMLResource.OPTION_DEFER_ATTACHMENT,  Boolean.TRUE);
		LOAD_OPTIONS.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION,  Boolean.TRUE);
		LOAD_OPTIONS.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
		LOAD_OPTIONS.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		LOAD_OPTIONS.put(XMLResource.OPTION_USE_PACKAGE_NS_URI_AS_LOCATION, Boolean.FALSE);
		// idem in Papyrus ModelSet
		LOAD_OPTIONS.put(XMLResource.OPTION_URI_HANDLER, new PapyrusURIHandler());
}

	/**
	 * Constructor.
	 *
	 */
	private LoadAndSaveOptionsUtils() {
		// to avoid instanciation
	}

	/**
	 * 
	 * @return
	 * 		the load options to use in the Papyrus EMF model
	 */
	public static final Map<Object, Object> getLoadOptions() {
		return new HashMap<Object, Object>(LOAD_OPTIONS);// we create a copy, to avoid non wanted changes
	}

	/**
	 * 
	 * @return
	 * 		the save options to use in the Papyrus EMF model
	 */
	public static final Map<Object, Object> getSaveOptions() {
		return new HashMap<Object, Object>(SAVE_OPTIONS);// we create a copy, to avoid non wanted changes
	}
	
	/**
	 * A URI handler that converts all platform:/resource URIs on save to platform:/plugin format
	 * and loads them back as either platform:/resource (if they exist in the workspace) or
	 * platform:/plugin (if they do not).
	 */
	protected static class PapyrusURIHandler extends URIHandlerImpl.PlatformSchemeAware {
	   
		@Override
	    public URI deresolve(URI uri) {
	    		// Convert all platform:/resource URIs to platform:/plugin format on save 
	    		if (uri.isPlatformResource()) {
	    			String platformString = uri.toPlatformString(true);
	    			String fragment = uri.fragment();
	    			URI pluginURI = URI.createPlatformPluginURI(platformString, true);
	    			pluginURI = pluginURI.appendFragment(fragment);
	    			return pluginURI;
	    		}
	    		return super.deresolve(uri);
	    }
		
	    @Override
		public URI resolve(URI uri) {
    			// Convert platform:/plugin URIs to platform:/resource format on load if the resource exists in the workspace 
	    		if (uri.isPlatformPlugin()) {
	    			String platformString = uri.toPlatformString(true);
	    			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
	    			if (resource != null) {
		    			String fragment = uri.fragment();
		    			URI resourceURI = URI.createPlatformResourceURI(platformString, true);
		    			resourceURI = resourceURI.appendFragment(fragment);
		    			return resourceURI;
	    			}
	    		}
	    		return super.resolve(uri);
	    }
	}
}
