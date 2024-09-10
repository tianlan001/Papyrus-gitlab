/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.tools.util.ClasspathHelper;
import org.eclipse.papyrus.infra.tools.util.Try;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.URIConverterService;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Common utilities for working with URIs in validation and quick-fixes.
 */
public class CommonURIUtils {

	/** The URI scheme for Equinox's internal <tt>bundleresource://</tt> URIs. */
	private static final String BUNDLE_RESOURCE_SCHEME = "bundleresource"; //$NON-NLS-1$

	/** The URI scheme for e4's <tt>bundleclass://</tt> URIs. */
	private static final String BUNDLE_CLASS_SCHEME = "bundleclass"; //$NON-NLS-1$

	/** Regex to parse the bundle ID out of a URI of <tt>bundleresource:</tt> scheme. */
	private static final Pattern BUNDLE_RESOURCE_AUTHORITY_PATTERN = Pattern.compile("^\\d+"); //$NON-NLS-1$

	public static URI normalize(ResourceSet context, URI resourceURI) {
		// Can we normalize the URI to something recognizable? Note that we do not expect
		// to have URI mappings for 'bundleclass' and 'bundleresource' schemes
		return (context == null) ? resourceURI : URIConverterService.INSTANCE.normalize(resourceURI, context);
	}

	/**
	 * Get the name of a bundle that hosts a resource.
	 *
	 * @param context
	 *            an object in which context the URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return the bundle that packages the resource. The result will not be {@code null}
	 *         but it may have a {@code null} {@linkplain Try#get() value} if the URI scheme should
	 *         infer a bundle name but the URI does not
	 */
	public static Try<String> getBundleName(EObject context, URI resourceURI) {
		return getBundleName(context.eResource(), resourceURI);
	}

	/**
	 * Get the name of a bundle that hosts a resource.
	 *
	 * @param context
	 *            a resource in which context the URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return the bundle that packages the resource. The result will not be {@code null}
	 *         but it may have a {@code null} {@linkplain Try#get() value} if the URI scheme should
	 *         infer a bundle name but the URI does not
	 */
	public static Try<String> getBundleName(Resource context, URI resourceURI) {
		return getBundleName(context.getResourceSet(), resourceURI);
	}

	/**
	 * Get the name of a bundle that hosts a resource.
	 *
	 * @param context
	 *            a resource set in which context the URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return the bundle that packages the resource. The result will not be {@code null}
	 *         but it may have a {@code null} {@linkplain Try#get() value} if the URI scheme should
	 *         infer a bundle name but the URI does not
	 */
	public static Try<String> getBundleName(ResourceSet context, URI resourceURI) {
		String result = null;

		URI normalized = normalize(context, resourceURI);

		if ((normalized.isPlatformPlugin() || normalized.isPlatformResource()) && normalized.segmentCount() > 1) {
			result = normalized.segment(1);
		} else if (BUNDLE_CLASS_SCHEME.equals(normalized.scheme()) && normalized.hasAuthority()) {
			result = normalized.authority();
		} else if (BUNDLE_RESOURCE_SCHEME.equals(normalized.scheme()) && normalized.hasAuthority()) {
			Bundle bundle = null;
			Matcher m = BUNDLE_RESOURCE_AUTHORITY_PATTERN.matcher(normalized.authority());
			if (m.find()) {
				long bundleID = Long.parseLong(m.group());
				bundle = Activator.getDefault().getBundle().getBundleContext().getBundle(bundleID);
			}
			if (bundle != null) {
				result = bundle.getSymbolicName();
			}
		} else {
			// Is it a registered package?
			EPackage ePackage = context.getPackageRegistry().getEPackage(normalized.toString());
			if (ePackage != null) {
				Bundle bundle = FrameworkUtil.getBundle(ePackage.getClass());
				if (bundle != null) {
					result = bundle.getSymbolicName();
				}
			} else {
				// This doesn't look like any URI that resolves into a bundle. Don't report the normalized
				// URI because that's not what the user sees in the resource
				return Try.failure(NLS.bind(Messages.CommonURIUtils_0, resourceURI));
			}
		}

		return Try.success(result);
	}

	/**
	 * Query whether a resource exists.
	 *
	 * @param context
	 *            an object in which context the resource URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return {@code false} if the resource does not exist; {@code true}, otherwise, including in
	 *         the case that the URI is of a kind that cannot be queried for existence and is
	 *         therefore just assumed to exist
	 */
	public static boolean exists(EObject context, URI resourceURI) {
		return exists(context.eResource(), resourceURI);
	}

	/**
	 * Query whether a resource exists.
	 *
	 * @param context
	 *            a resource in which context the resource URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return {@code false} if the resource does not exist; {@code true}, otherwise, including in
	 *         the case that the URI is of a kind that cannot be queried for existence and is
	 *         therefore just assumed to exist
	 */
	public static boolean exists(Resource context, URI resourceURI) {
		return exists(context.getResourceSet(), resourceURI);
	}

	/**
	 * Query whether a resource exists.
	 *
	 * @param context
	 *            a resource set in which context the resource URI is being analyzed
	 * @param resourceURI
	 *            a resource URI
	 * @return {@code false} if the resource does not exist; {@code true}, otherwise, including in
	 *         the case that the URI is of a kind that cannot be queried for existence and is
	 *         therefore just assumed to exist
	 */
	public static boolean exists(ResourceSet context, URI resourceURI) {
		boolean result = true;

		URI normalized = normalize(context, resourceURI);

		if ((normalized.isPlatformPlugin() || normalized.isPlatformResource())) {
			// EMF knows how to deal with platform: URIs. And we need more than the 'resource'/'plugin' and project/plug-in
			// segments in order to identify a valid resource. And a trailing slash indicates a project/plugin or
			// folder, itself, which is not a file resource
			result = !normalized.hasTrailingPathSeparator() && normalized.segmentCount() > 2 && context.getURIConverter().exists(normalized, null);
		} else if (BUNDLE_CLASS_SCHEME.equals(normalized.scheme()) && normalized.hasAuthority() && normalized.segmentCount() > 0) {
			String bundle = normalized.authority();
			String className = normalized.segment(0);
			result = (bundle != null) && (className != null)
					&& ClasspathHelper.INSTANCE.findClass(className, URI.createPlatformPluginURI(bundle, false), null) != null;
		} else if (BUNDLE_RESOURCE_SCHEME.equals(normalized.scheme()) && normalized.hasAuthority() && normalized.segmentCount() > 0) {
			Bundle bundle = null;
			String className = normalized.segment(0);

			Matcher m = BUNDLE_RESOURCE_AUTHORITY_PATTERN.matcher(normalized.authority());
			if (m.find()) {
				long bundleID = Long.parseLong(m.group());
				bundle = Activator.getDefault().getBundle().getBundleContext().getBundle(bundleID);
			}
			result = (bundle != null) && (className != null)
					&& ClasspathHelper.INSTANCE.findClass(className, URI.createPlatformPluginURI(bundle.getSymbolicName(), false), null) != null;
		} else if (!normalized.isRelative()) {
			// Is it a registered package?
			result = context.getPackageRegistry().getEPackage(normalized.toString()) != null;
		}

		return result;
	}

}
