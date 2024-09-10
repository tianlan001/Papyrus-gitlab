/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bugs 542945, 573197
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.pde.core.build.IBuildModel;
import org.eclipse.pde.core.plugin.IExtensions;
import org.eclipse.pde.core.plugin.IPluginAttribute;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;

/**
 * This allows to define utils methods and functions corresponding to project.
 */
public class ProjectManagementUtils {

	/**
	 * This allows to get the plugin model base from the project.
	 * This allows to manipulate the 'plugin.xml' and 'MANIFEST.MF' files content.
	 *
	 * @param project
	 *            The current project.
	 * @return The {@link IPluginModelBase} representing the plug-in project content.
	 */
	public static IPluginModelBase getPluginModelBase(final IProject project) {
		return PluginRegistry.findModel(project);
	}

	/**
	 * This allows to get the list of extensions in the 'plugin.xml' file.
	 *
	 * @param project
	 *            The current project.
	 * @return The list of extensions (can be empty).
	 */
	public static List<IPluginExtension> getPluginExtensions(final IProject project) {
		final IPluginModelBase pluginModelBase = ProjectManagementUtils.getPluginModelBase(project);
		return null != pluginModelBase ? Arrays.asList(pluginModelBase.getExtensions().getExtensions()) : Collections.emptyList();
	}

	/**
	 * This allows to get the list of required plug-ins.
	 *
	 * @param project
	 *            The current project.
	 * @return The list of required plug-ins (can be empty).
	 */
	public static List<BundleSpecification> getPluginDependencies(final IProject project) {
		final IPluginModelBase pluginModelBase = ProjectManagementUtils.getPluginModelBase(project);
		return null != pluginModelBase ? Arrays.asList(pluginModelBase.getBundleDescription().getRequiredBundles()) : Collections.emptyList();
	}

	/**
	 * This allows to get the build model of the project.
	 *
	 * @param project
	 *            The current project.
	 * @return The build model representing the 'build.properties' file.
	 */
	public static IBuildModel getPluginBuild(final IProject project) {
		final IPluginModelBase pluginModelBase = ProjectManagementUtils.getPluginModelBase(project);
		if (null != pluginModelBase) {
			try {
				return PluginRegistry.createBuildModel(pluginModelBase);
			} catch (CoreException e) {
				// Do nothing, just return null
			}
		}
		return null;
	}

	/**
	 * This allows to check the file corresponding to the file name in parameter exists.
	 *
	 * @param container
	 *            The container resource.
	 * @param foundFile
	 *            The name of the found file.
	 * @param isExtensionCheck
	 *            <code>true</code> if the found file is an extension to find, <code>false</code> otherwise.
	 * @return The corresponding file or <code>null</code>.
	 */
	public static boolean existFileFromProject(final IContainer container, final String foundFile, final boolean isExtensionCheck) {
		boolean result[] = { false };

		if (container.isAccessible()) {
			try {
				container.accept(resource -> {
					switch (resource.getType()) {
					case IResource.FILE:
						result[0] = result[0] || isCorrespondingFile((IFile) resource, foundFile, isExtensionCheck);
						break;
					default:
						break;
					}
					return !result[0];
				});
			} catch (final CoreException e) {
				Activator.log.error(e);
			}
		}

		return result[0];
	}

	/**
	 * This allows to get the files corresponding to the file name in parameter.
	 *
	 * @param container
	 *            The container resource.
	 * @param foundFile
	 *            The name of the found file.
	 * @param isExtensionCheck
	 *            <code>true</code> if the found file is an extension to find, <code>false</code> otherwise.
	 * @return The corresponding file or <code>null</code>.
	 */
	public static Collection<IFile> getFilesFromProject(final IContainer container, final String foundFile, final boolean isExtensionCheck) {
		final Collection<IFile> result = new HashSet<>();

		if (container.isAccessible()) {
			try {
				container.accept(resource -> {
					switch (resource.getType()) {
					case IResource.FILE:
						if (isCorrespondingFile((IFile) resource, foundFile, isExtensionCheck)) {
							result.add((IFile) resource);
						}
						break;
					default:
						break;
					}
					return true;
				});
			} catch (final CoreException e) {
				Activator.log.error(e);
			}
		}

		return result;
	}

	/**
	 * This allows to get the 'MANIFEST.MF' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'MANIFEST.MF' or <code>null</code>.
	 */
	public static IFile getManifestFile(final IContainer container) {
		final Collection<IFile> manifestFiles = ProjectManagementUtils.getFilesFromProject(container, "MANIFEST.MF", false); //$NON-NLS-1$
		return manifestFiles.isEmpty() ? null : manifestFiles.iterator().next();
	}

	/**
	 * This allows to get the 'plugin.xml' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'plugin.xml' or <code>null</code>.
	 */
	public static IFile getPluginXMLFile(final IContainer container) {
		final Collection<IFile> pluginXMLFiles = ProjectManagementUtils.getFilesFromProject(container, "plugin.xml", false); //$NON-NLS-1$
		return pluginXMLFiles.isEmpty() ? null : pluginXMLFiles.iterator().next();
	}

	/**
	 * This allows to get the 'build.properties' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'build.properties' or <code>null</code>.
	 */
	public static IFile getBuildFile(final IContainer container) {
		final Collection<IFile> buildPropertiesFiles = ProjectManagementUtils.getFilesFromProject(container, "build.properties", false); //$NON-NLS-1$
		return buildPropertiesFiles.isEmpty() ? null : buildPropertiesFiles.iterator().next();
	}

	/**
	 * This allows to check if the current file is corresponding to the find one.
	 *
	 * @param file
	 *            The current file.
	 * @param foundFile
	 *            The name of the found file.
	 * @param isExtensionCheck
	 *            <code>true</code> if the found file is an extension to find, <code>false</code> otherwise.
	 * @return <code>true</code> if the file is corresponding to the found file, <code>false</code> otherwise.
	 */
	private static boolean isCorrespondingFile(final IFile file, final String foundFile, final boolean isExtensionCheck) {
		return isExtensionCheck ? file.getName().endsWith("." + foundFile) : file.getName().equals(foundFile); //$NON-NLS-1$
	}

	/**
	 * Find an extension element from the current PDE target overlaid with workspace plug-in models.
	 *
	 * @param extensionPoint
	 *            the point on which to search extension elements
	 * @param elementName
	 *            the extension element name to search
	 * @param attributes
	 *            optional attributes by which to filter the search, as name/value pairs
	 *
	 * @return the found extension element
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the {@code attributes} array has a name without a following value
	 */
	public static Optional<IPluginElement> findExtensionElement(String extensionPoint, String elementName, String... attributes) {
		return getExtensionElements(extensionPoint, elementName, attributes).findAny();
	}

	/**
	 * Get extension elements from the current PDE target overlaid with workspace plug-in models.
	 *
	 * @param extensionPoint
	 *            the point on which to search extension elements
	 * @param elementName
	 *            the extension element name to search
	 * @param attributes
	 *            optional attributes by which to filter the search, as name/value pairs
	 *
	 * @return the matching extension elements
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the {@code attributes} array has a name without a following value
	 */
	public static Stream<IPluginElement> getExtensionElements(String extensionPoint, String elementName, String... attributes) {
		Predicate<IPluginElement> elementFilter = element -> elementName.equals(element.getName());

		if (attributes.length > 0) {
			elementFilter = elementFilter.and(hasAttributes(attributes));
		}

		return Stream.of(PluginRegistry.getActiveModels())
				.map(IPluginModelBase::getExtensions)
				.map(IExtensions::getExtensions).flatMap(Stream::of)
				.filter(ext -> extensionPoint.equals(ext.getPoint()))
				.map(IPluginExtension::getChildren).flatMap(Stream::of)
				.filter(IPluginElement.class::isInstance).map(IPluginElement.class::cast)
				.filter(elementFilter);
	}

	/**
	 * Create a predicate that matches <tt>plugin.xml</tt> elements having the given {@code attributes}.
	 * In the unhelpful case of an empty array of {@code attributes}, the result is an always-{@code true}
	 * predicate.
	 *
	 * @param attributes
	 *            attributes by which to filter the search, as name/value pairs
	 *
	 * @return a filter matching extension elements that have these {@code attributes}
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the {@code attributes} array has a name without a following value
	 */
	public static Predicate<IPluginElement> hasAttributes(String... attributes) {
		Predicate<IPluginElement> result = __ -> true;

		for (int i = 0; i < attributes.length; i = i + 2) {
			String name = attributes[i];
			String value = attributes[i + 1];

			Predicate<IPluginElement> next = element -> value.equals(element.getAttribute(name).getValue());
			result = i > 0 ? result.and(next) : next;
		}

		return result;
	}

	/**
	 * Create a predicate that matches <tt>plugin.xml</tt> elements having an {@code attribute} matching the given {@code predicate}.
	 *
	 * @param attribute
	 *            the attribute name to test
	 * @param predicate
	 *            a predicate to match the {@code attribute} value
	 *
	 * @return a filter matching extension elements that have an {@code attribute} matching the given {@code predicate}
	 */
	public static Predicate<IPluginElement> hasAttribute(String attribute, Predicate<? super String> predicate) {
		return element -> Optional.ofNullable(element.getAttribute(attribute)).map(IPluginAttribute::getValue).filter(predicate).isPresent();
	}

	/**
	 * Obtain a predicate matching a <tt>plugin.xml</tt> element whose resource path matches a given URI.
	 * The path attribute is assumed to be either a relative path within the bundle that defines the extension
	 * or else a <tt>platform:/plugin</tt> URI.
	 *
	 * @param pathAttribute
	 *            the attribute name that indicates the resource path
	 * @param uri
	 *            the resource location to match against the path specification
	 *
	 * @return the element predicate
	 */
	public static Predicate<IPluginElement> resourcePathIs(String pathAttribute, URI uri) {
		return element -> Optional.ofNullable(element.getAttribute(pathAttribute)).map(attr -> resourcePathMatches(attr, uri)).orElse(false);
	}

	private static boolean resourcePathMatches(IPluginAttribute attribute, URI uri) {
		String path = attribute.getValue();
		if (path == null || path.isBlank()) {
			return false;
		}

		try {
			URI pathURI = URI.createURI(path, true);
			if (pathURI.isRelative()) {
				pathURI = URI.createPlatformPluginURI(String.format("%s/%s", attribute.getPluginBase().getId(), pathURI), false); //$NON-NLS-1$
			}

			return pathURI.equals(uri) || (pathURI.isPlatform() && uri.isPlatform() && pathURI.toPlatformString(false).equals(uri.toPlatformString(false)));
		} catch (Exception e) {
			// Cannot parse it as an URI? Then it cannot be matched against a valid URI
			return false;
		}
	}

}
