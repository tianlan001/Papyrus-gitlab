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

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import static com.google.common.collect.Iterables.filter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Strings;

/**
 * Default implementation of an opaque resource provider suitable for most Papyrus tooling <tt>plugin.xml</tt> files.
 */
class BasicPluginXMLResourceProvider implements OpaqueResourceProvider.XML {

	private final Object resourceClassifier;
	private final String diagnosticSource;
	private final String extensionPoint;
	private final String elementName;
	private final String attributeName;
	private final URIAccessor<Element, String, String> uriAccessor;
	private final Function<IProject, URI> baseURIFunction;

	BasicPluginXMLResourceProvider(Object resourceClassifier, String diagnosticSource, String extensionPoint, String elementName, String attributeName) {
		this(resourceClassifier, diagnosticSource, extensionPoint, elementName, attributeName, BasicPluginXMLResourceProvider::getURI);
	}

	BasicPluginXMLResourceProvider(Object resourceClassifier, String diagnosticSource, String extensionPoint, String elementName, String attributeName, URIAccessor<Element, String, String> uriAccessor) {
		super();

		this.resourceClassifier = resourceClassifier;
		this.diagnosticSource = diagnosticSource;
		this.extensionPoint = extensionPoint;
		this.elementName = elementName;
		this.attributeName = attributeName;
		this.uriAccessor = uriAccessor;

		Function<IProject, String> projectBundle = project -> Optional.ofNullable(PluginRegistry.findModel(project))
				.map(IPluginModelBase::getBundleDescription)
				.map(BundleDescription::getSymbolicName)
				.orElse(project.getName());
		this.baseURIFunction = project -> URI.createPlatformResourceURI(projectBundle.apply(project), true)
				.appendSegment(""); // Ensure a trailing path separator //$NON-NLS-1$
	}

	@Override
	public Iterable<ClassifiedURI> getOpaqueResourceReferences(IProject modelProject, IFile modelFile, Element object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!elementName.equals(object.getNodeName())) {
			return List.of();
		}
		if (!isInExtensionPoint(object)) {
			return List.of();
		}
		String value = object.getAttribute(attributeName);
		if (Strings.isNullOrEmpty(value)) {
			return List.of();
		}
		return filter(List.of(resolveURI(modelProject, object, value, diagnostics, context)), Objects::nonNull);
	}

	/** Is the given {@code element} nested within our extension point? */
	private boolean isInExtensionPoint(Element element) {
		boolean result = false;

		for (Node parent = element.getParentNode(); !result && parent instanceof Element; parent = parent.getParentNode()) {
			result = isExtensionPoint((Element) parent);
		}

		return result;
	}

	/** Is the given {@code element} our extension point element? */
	private boolean isExtensionPoint(Element element) {
		return "extension".equals(element.getNodeName()) && extensionPoint.equals(element.getAttribute("point")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	ClassifiedURI resolveURI(IProject project, Element element, String attributeValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		URI result;

		try {
			result = uriAccessor.getURI(element, attributeName, attributeValue);
		} catch (Exception e) {
			BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, diagnosticSource, 0,
					NLS.bind(Messages.BasicPluginXMLResourceProvider_0,
							new Object[] { element.getNodeName(), resourceClassifier, attributeValue }),
					null);
			diagnostics.add(diagnostic);
			return null;
		}

		if (result.isRelative()) {
			result = result.resolve(baseURIFunction.apply(project));
		}

		if (result.isPlatformResource()) {
			// Can check for existence of the resource
			IPath path = new Path(result.toPlatformString(true));
			IResource resource = project.getParent().findMember(path);
			if (resource == null || !resource.isAccessible() || resource.getType() != IResource.FILE) {
				BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, diagnosticSource, 0,
						NLS.bind(Messages.BasicPluginXMLResourceProvider_1,
								new Object[] { result.lastSegment(), element.getNodeName(), resourceClassifier }),
						null);
				diagnostics.add(diagnostic);
				return null;
			}
		}

		return new ClassifiedURIImpl(result, resourceClassifier);
	}

	static URI getURI(Element owner, String attribute, String value) {
		return Strings.isNullOrEmpty(value) ? URI.createURI("") : URI.createURI(value, true); //$NON-NLS-1$
	}

}
