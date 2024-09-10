/*****************************************************************************
 * Copyright (c) 2015, 2020 Christian W. Damus, CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   CEA LIST - Bug 569720
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal.language;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageProvider;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * A default language provider for extensions that simply declare content-type matches for languages.
 */
public class DefaultLanguageProvider implements ILanguageProvider {
	private static Map<EPackage, IContentType> CONTENT_TYPES = new WeakHashMap<>();

	private static IContentType NULL_CONTENT_TYPE = Platform.getContentTypeManager().getContentType("org.eclipse.emf.ecore.xmi"); //$NON-NLS-1$

	private final Set<IContentType> contentTypes = Sets.newHashSet();

	private final List<ILanguage> languages = Lists.newArrayList();

	private List<LanguageProxy> languageProxies;

	public DefaultLanguageProvider() {
		super();
	}

	@Override
	public synchronized Iterable<ILanguage> getLanguages(ILanguageService languageService, URI modelURI, boolean uriHasFileExtension) {
		Set<URI> allURIs;

		try {
			allURIs = resolveURIs(modelURI, uriHasFileExtension);
		} catch (CoreException e) {
			Activator.log.log(e.getStatus());

			// Can't match our content types against anything
			return Collections.emptyList();
		}

		if (!matchContentType(allURIs)) {
			return Collections.emptyList();
		} else {
			// If any of my content-types matches, then all of my languages are present
			resolveProxies();
			return Collections.unmodifiableList(languages);
		}
	}

	@Override
	public synchronized Iterable<ILanguage> getLanguages(ILanguageService languageService, ModelSet modelSet) {
		// Make a defensive copy because CDO resources aren't iteration-safe as standard EMF resources are
		if (!matchContentType(new ArrayList<>(modelSet.getResources()))) {
			return Collections.emptyList();
		} else {
			// If any of my content-types matches, then all of my languages are present
			resolveProxies();
			return Collections.unmodifiableList(languages);
		}
	}

	void addContentType(String id) {
		IContentType contentType = Platform.getContentTypeManager().getContentType(id);
		if (contentType == null) {
			Activator.log.warn("No such content-type in language provider extension: " + id); //$NON-NLS-1$
		} else {
			contentTypes.add(contentType);
		}
	}

	synchronized void addLanguage(ILanguage language) {
		this.languages.add(language);
	}

	synchronized void addLanguage(IConfigurationElement config, String attribute) {
		if (languageProxies == null) {
			languageProxies = Lists.newArrayList();
		}

		languageProxies.add(new LanguageProxy(config, attribute));
	}

	private void resolveProxies() {
		if (languageProxies != null) {
			try {
				for (LanguageProxy next : languageProxies) {
					ILanguage resolved = next.resolve();
					if (resolved != null) {
						addLanguage(resolved);
					}
				}
			} finally {
				languageProxies = null;
			}
		}
	}

	private Set<URI> resolveURIs(URI modelURI, boolean uriHasFileExtension) throws CoreException {
		// If given an extension, just take that
		if (uriHasFileExtension) {
			return Collections.singleton(modelURI);
		}

		Set<URI> result = Sets.newHashSet();

		if (modelURI.isPlatformResource()) {
			String baseName = modelURI.lastSegment();
			IPath path = new Path(modelURI.trimSegments(1).toPlatformString(true));
			IContainer container = (path.segmentCount() > 1) ? ResourcesPlugin.getWorkspace().getRoot().getFolder(path) : ResourcesPlugin.getWorkspace().getRoot().getProject(path.lastSegment());
			for (IResource next : container.members()) {
				if (next.getType() == IResource.FILE) {
					IPath nextPath = next.getFullPath();
					String name = nextPath.removeFileExtension().lastSegment();
					if (name.equals(baseName)) {
						result.add(URI.createPlatformResourceURI(nextPath.toString(), true));
					}
				}
			}
		} else if (modelURI.isFile()) {
			String baseName = modelURI.lastSegment();
			File directory = new File(modelURI.trimSegments(1).toFileString());
			File[] listFiles = directory.listFiles();
			if (listFiles != null) {
				for (File next : listFiles) {
					if (next.isFile()) {
						IPath nextPath = new Path(directory.getPath()).append(next.getName());
						String name = nextPath.removeFileExtension().lastSegment();
						if (name.equals(baseName)) {
							result.add(URI.createPlatformResourceURI(nextPath.toString(), true));
						}
					}
				}
			}
		}

		return result;
	}

	private boolean matchContentType(Set<URI> uris) {
		boolean result = false;

		out: for (URI next : uris) {
			try {
				Map<String, ?> desc = URIConverter.INSTANCE.contentDescription(next, null);
				String contentTypeID = (String) desc.get(ContentHandler.CONTENT_TYPE_PROPERTY);
				if (contentTypeID != null) {
					IContentType contentType = Platform.getContentTypeManager().getContentType(contentTypeID);
					if (contentType != null) {
						for (IContentType type : contentTypes) {
							if (contentType.isKindOf(type)) {
								result = true;
								break out;
							}
						}
					}
				}
			} catch (IOException e) {
				// Not our content type, I guess
			}
		}

		return result;
	}

	private boolean matchContentType(Collection<? extends Resource> resources) {
		boolean result = false;
		Set<EPackage> packagesSeen = new HashSet<>();

		out: for (Resource next : resources) {
			String filename = next.getURI().lastSegment();

			// TODO: Is it really sufficient to check only the roots? Other content could be contained
			for (EObject root : next.getContents()) {
				EPackage ePackage = root.eClass().getEPackage();
				if (packagesSeen.add(ePackage)) {
					IContentType contentType = getContentType(ePackage, filename);
					if (contentType != null) {
						for (IContentType type : contentTypes) {
							if (contentType.isKindOf(type)) {
								result = true;
								break out;
							}
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * We don't actually have a mapping in EMF between packages and content-types,
	 * so we infer it by feeding the platform's content-type system some fake inputs,
	 * relaying on the standard EMF pattern of using XML namespace declarations to
	 * determine content-type.
	 *
	 * @param ePackage
	 *            the package to infer the content-type
	 * @param filename
	 *            used as a hint to the platform's content-type manager
	 *
	 * @return the content-type or a placeholder for unknown content (never {@code null})
	 */
	private IContentType getContentType(EPackage ePackage, String filename) {
		return CONTENT_TYPES.computeIfAbsent(ePackage, package_ -> {
			IContentType contentType = null;

			InputStream xmiContent = createInputStream(ePackage);
			try {
				IContentDescription desc = Platform.getContentTypeManager().getDescriptionFor(xmiContent, filename, null);
				contentType = (desc == null) ? null : desc.getContentType();
			} catch (IOException e) {
				// Not our content type, I guess
			}

			if (contentType == null) {
				contentType = NULL_CONTENT_TYPE;
			}

			return contentType;
		});
	}

	private InputStream createInputStream(EPackage ePackage) {
		String xmi = String.format(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmi:XMI xmlns:xmi=\"%s\" xmlns:content=\"%s\"/>", //$NON-NLS-1$
				XMIResource.XMI_2_1_URI, ePackage.getNsURI());
		return new ByteArrayInputStream(xmi.getBytes());
	}

	//
	// Nested types
	//

	private static class LanguageProxy {
		private final IConfigurationElement config;
		private final String attribute;

		LanguageProxy(IConfigurationElement config, String attribute) {
			super();

			this.config = config;
			this.attribute = attribute;
		}

		ILanguage resolve() {
			try {
				return (ILanguage) config.createExecutableExtension(attribute);
			} catch (Exception e) {
				Activator.log.error("Failed to instantiate language in provider extension from contributor " + config.getContributor().getName(), e); //$NON-NLS-1$
				return null;
			}
		}
	}
}
