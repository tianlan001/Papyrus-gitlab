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
import static com.google.common.collect.Iterables.transform;
import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.CommonURIUtils;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;

import com.google.common.collect.Iterables;

/**
 * Default implementation of an opaque resource provider suitable for most Papyrus tooling models.
 */
class BasicEMFResourceProvider implements OpaqueResourceProvider.EMF {

	private final Object resourceClassifier;
	private final String diagnosticSource;
	private final EAttribute referenceAttribute;
	private final URIAccessor<EObject, EAttribute, Object> uriAccessor;
	private final BiFunction<EObject, IProject, URI> baseURIFunction;

	BasicEMFResourceProvider(Object resourceClassifier, String diagnosticSource, EAttribute attribute) {
		this(resourceClassifier, diagnosticSource, attribute, null, BasicEMFResourceProvider::getURI);
	}

	BasicEMFResourceProvider(Object resourceClassifier, String diagnosticSource, EAttribute attribute, EAttribute bundleQualifier) {
		this(resourceClassifier, diagnosticSource, attribute, bundleQualifier, BasicEMFResourceProvider::getURI);
	}

	BasicEMFResourceProvider(Object resourceClassifier, String diagnosticSource, EAttribute attribute, URIAccessor<EObject, EAttribute, Object> uriAccessor) {
		this(resourceClassifier, diagnosticSource, attribute, null, uriAccessor);
	}

	BasicEMFResourceProvider(Object resourceClassifier, String diagnosticSource, EAttribute attribute, EAttribute bundleQualifier, URIAccessor<EObject, EAttribute, Object> uriAccessor) {
		super();

		// Both of these classes are final
		if (attribute.getEAttributeType().getInstanceClass() != String.class
				&& attribute.getEAttributeType().getInstanceClass() != URI.class) {
			throw new IllegalArgumentException("attribute not of URI type"); //$NON-NLS-1$
		}
		if (bundleQualifier != null && bundleQualifier.getEAttributeType().getInstanceClass() != String.class) {
			throw new IllegalArgumentException("bundleQualifier not of String type"); //$NON-NLS-1$
		}
		if (bundleQualifier != null && !bundleQualifier.getEContainingClass().isSuperTypeOf(attribute.getEContainingClass())) {
			throw new IllegalArgumentException("bundleQualifier not a feature of the referencing EClass"); //$NON-NLS-1$
		}

		this.resourceClassifier = resourceClassifier;
		this.diagnosticSource = diagnosticSource;
		this.referenceAttribute = attribute;
		this.uriAccessor = uriAccessor;

		Function<IProject, String> projectBundle = project -> Optional.ofNullable(PluginRegistry.findModel(project))
				.map(IPluginModelBase::getBundleDescription)
				.map(BundleDescription::getSymbolicName)
				.orElse(project.getName());
		BiFunction<EObject, IProject, URI> projectBase = (object, project) -> URI.createPlatformPluginURI(projectBundle.apply(project), true)
				.appendSegment(""); // Ensure a trailing path separator //$NON-NLS-1$
		if (bundleQualifier == null) {
			this.baseURIFunction = projectBase;
		} else {
			Function<EObject, Optional<String>> bundleFunction = object -> Optional.of(object).filter(bundleQualifier.getEContainingClass()::isInstance)
					.map(owner -> //
					(String) owner.eGet(bundleQualifier)).filter(not(String::isBlank));
			BiFunction<EObject, IProject, URI> bundleBase = (object, project) -> URI.createPlatformPluginURI(bundleFunction.apply(object).orElse(project.getName()), true)
					.appendSegment(""); // Ensure a trailing path separator //$NON-NLS-1$
			this.baseURIFunction = bundleBase;
		}
	}

	@Override
	public Iterable<ClassifiedURI> getOpaqueResourceReferences(IProject modelProject, IFile modelFile, EObject object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!referenceAttribute.getEContainingClass().isInstance(object)) {
			return List.of();
		}
		return filter(transform(get(object, referenceAttribute),
				e -> resolveURI(modelProject, object, e, diagnostics, context)),
				Objects::nonNull);
	}

	Iterable<?> get(EObject owner, EAttribute attribute) {
		Object value = owner.eGet(attribute);
		if (value == null) {
			return List.of();
		} else if (value instanceof Iterable<?>) {
			return Iterables.filter((Iterable<?>) value, Objects::nonNull);
		} else {
			return List.of(value);
		}
	}

	ClassifiedURI resolveURI(IProject project, EObject object, Object referenceValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		URI result;

		try {
			result = uriAccessor.getURI(object, referenceAttribute, referenceValue);
		} catch (Exception e) {
			BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, diagnosticSource, 0,
					NLS.bind(Messages.BasicEMFResourceProvider_0,
							new Object[] { EObjectValidator.getObjectLabel(object, context), resourceClassifier, referenceValue }),
					new Object[] { object, referenceAttribute });
			diagnostics.add(diagnostic);
			return null;
		}

		if (result != null) {
			if (result.isRelative() && result.isHierarchical()) {
				// Sometimes the developer puts a leading '/' in the path that will confuse this resolution process
				if (result.hasAbsolutePath() && !result.hasAuthority() && !result.hasDevice()) {
					// Create a relative-path URI (we already know that it has no scheme because it's a relative URI
					result = URI.createHierarchicalURI(result.segments(), result.query(), result.fragment());
				}
				result = result.resolve(baseURIFunction.apply(object, project));
			}

			// Check for existence of the resource, if applicable
			if (!CommonURIUtils.exists(object, result)) {
				BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, diagnosticSource, 0,
						NLS.bind(Messages.BasicEMFResourceProvider_1,
								new Object[] { result.lastSegment(), EObjectValidator.getObjectLabel(object, context), resourceClassifier }),
						new Object[] { object, referenceAttribute });
				diagnostics.add(diagnostic);
				result = null;
			}
		}

		return result == null ? null : new ClassifiedURIImpl(result, resourceClassifier);
	}

	static URI getURI(EObject owner, EAttribute attribute, Object value) {
		URI result = null;

		if (value != null && attribute.getEContainingClass().isInstance(owner)) {
			URI uri = value instanceof URI ? (URI) value : URI.createURI(String.valueOf(value), true);

			if (!uri.isEmpty()) {
				result = uri;
			}
		}

		return result;
	}

}
