/*****************************************************************************
 * Copyright (c) 2012, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 569357, 570097
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 *
 * This class provides methods for EMF Resource
 *
 */
public class ResourceUtils {

	private static final String PATH_SEPARATOR = "/";


	private ResourceUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param resource
	 *            an EMF resource
	 * @return
	 *         the IFile corresponding to this resource, or <code>null</code> if not found
	 */
	public static IFile getFile(final Resource resource) {
		if (resource != null) {
			URI uri = resource.getURI();
			uri = resource.getResourceSet().getURIConverter().normalize(uri);
			if (uri.isPlatformResource()) {
				String uriPlatformString = uri.toPlatformString(true);
				return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uriPlatformString));
			}
		}
		return null;
	}

	/**
	 *
	 *
	 * @param resource
	 *            a resource
	 * @return
	 *         a collection with all existing id in the resource
	 *
	 */
	public static Collection<String> getAllResourceIds(final XMIResource resource) {
		final Set<String> ids = new HashSet<>();
		final Iterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			final EObject current = iterator.next();
			final String id = resource.getID(current);
			Assert.isNotNull(id);
			Assert.isTrue(!ids.contains(id));
			ids.add(id);
		}
		return ids;
	}


	/**
	 * Default options to save emf files used in Papyrus
	 *
	 * @return
	 */
	public static Map<String, Object> getSaveOptions() {
		Map<String, Object> saveOptions = new HashMap<>();

		// default save options.
		saveOptions.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		saveOptions.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");

		// see bug 397987: [Core][Save] The referenced plugin models are saved using relative path
		saveOptions.put(XMLResource.OPTION_URI_HANDLER, new org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware());

		return saveOptions;
	}

	/**
	 * Return an encoded path string that can be used as a path attribute in a an extension for the given file.
	 * If an Ecore URI mapping exists for the path, it will be used as a path segment.
	 * Otherwise, a project-relative encoded path will be returned.
	 *
	 * @since 4.2
	 */
	public static String mapAndEncodePath(IFile file) {
		// check if we have a mapping that we can use
		URI pluginURI = URI.createPlatformPluginURI(file.getProject().getName() + PATH_SEPARATOR + file.getProjectRelativePath().toString(), true);
		String path = pluginURI.toString();

		// check local mappings first
		for (Entry<String, String> entry : getLocalUriMappings(file.getProject()).entrySet()) {
			String targetURI = entry.getValue();
			if (targetURI != null && targetURI.endsWith(PATH_SEPARATOR) && path.startsWith(targetURI)) {
				return path.replaceFirst(targetURI, entry.getKey());
			}
		}
		// check registered mappings
		for (Entry<URI, URI> entry : URIMappingRegistryImpl.INSTANCE.entrySet()) {
			String targetURI = entry.getValue().toString();
			if (targetURI != null && targetURI.endsWith(PATH_SEPARATOR) && path.startsWith(targetURI)) {
				return path.replaceFirst(targetURI, entry.getKey().toString());
			}
		}
		// no mapping were found - fallback to project relative path
		IPath relativePath = file.getProjectRelativePath();
		return getStringURI(relativePath);
	}

	/**
	 * Create a URI converter that supports not only the registered URI mappings in the target platform, but
	 * also {@linkplain #getLocalUriMappings(IProject) mappings in <tt>plugin.xml</tt> files in workspace projects}.
	 * Mappings from the workspace supersede the same prefixes in mappings from the target platform.
	 * And <tt>platform:/plugin</tt> URIs map into the workspace (and vice-versa) where applicable, per
	 * the {@linkplain #computePlatformResourceMap() platform resource map}.
	 *
	 * @return the workspace-inclusive URI converter
	 *
	 * @see ExtensibleURIConverterImpl#getURIMap()
	 * @see #getLocalUriMappings(IProject)
	 * @see #computePlatformResourceMap()
	 * @since 4.2
	 */
	public static URIConverter createWorkspaceAwareURIConverter() {
		URIConverter result = new ExtensibleURIConverterImpl();
		Map<URI, URI> uriMap = result.getURIMap();

		uriMap.putAll(computePlatformResourceMap());

		for (IProject next : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (next.isAccessible()) {
				Map<String, String> mappings = getLocalUriMappings(next);
				for (Map.Entry<String, String> mapping : mappings.entrySet()) {
					URI prefix = URI.createURI(mapping.getKey());
					URI target = URI.createURI(mapping.getValue());

					uriMap.put(prefix, target);
				}
			}
		}

		return result;
	}

	/**
	 * Return the {@code org.eclipse.emf.ecore.uri_mapping} extension declarations in the given {@code project}.
	 *
	 * @since 4.2
	 */
	public static Map<String, String> getLocalUriMappings(IProject project) {
		return PlatformHelper.INSTANCE.getLocalUriMappings(project);
	}

	/**
	 * Returns an encoded string representation of the path.
	 *
	 * @since 4.2
	 */
	public static String getStringURI(IPath path) {
		return Arrays.stream(path.segments())
				.map(segment -> URI.encodeSegment(segment, false))
				.collect(Collectors.joining(PATH_SEPARATOR));
	}

	/**
	 * Compute a mapping of <tt>platform:</tt> scheme URIs for maximal portability of cross-document
	 * references in resources that are saved (as almost always should be) using the
	 * {@linkplain org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware platform-scheme-aware URI handler}.
	 * That is the case for all resources saved using the {@linkplain #getSaveOptions() common Papyrus save options}.
	 * The resulting mapping forwards <tt>platform:/plugin/</tt> URIs to </tt>platform:/resource/</tt> for
	 * plug-in projects that are imported and open in the workspace and <tt>platform:/resource/</tt> to <tt>platform:/plugin/</tt>
	 * for all other plug-ins in the PDE Target.
	 *
	 * @return the platform URI mappings
	 *
	 * @see getSaveOptions()
	 * @see EcorePlugin#computePlatformPluginToPlatformResourceMap()
	 * @see EcorePlugin#computePlatformResourceToPlatformPluginMap(Collection)
	 * @since 4.2
	 */
	public static Map<URI, URI> computePlatformResourceMap() {
		Map<URI, URI> result = new HashMap<>();
		result.putAll(EcorePlugin.computePlatformPluginToPlatformResourceMap());

		List<URI> platform = PlatformHelper.INSTANCE.getPlatformBundleIDs().stream()
				.map(name -> URI.createPlatformPluginURI(name, true))
				.collect(Collectors.toList());
		result.putAll(EcorePlugin.computePlatformResourceToPlatformPluginMap(platform));

		return result;
	}

	/**
	 * Create a package registry that, if possible, includes also packages registered by plug-ing projects in the workspace.
	 *
	 * @return a new workspace-aware (as much as possible) package registry
	 * @since 4.2
	 */
	public static EPackage.Registry createWorkspaceAwarePackageRegistry() {
		return PlatformHelper.INSTANCE.createWorkspacePackageRegistry();
	}

}
