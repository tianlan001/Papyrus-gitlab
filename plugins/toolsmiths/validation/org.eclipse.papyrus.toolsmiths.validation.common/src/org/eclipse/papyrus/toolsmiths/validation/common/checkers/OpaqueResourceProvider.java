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

import static java.util.function.Predicate.not;
import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker.createSubstitutionLabelProvider;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.infra.tools.util.XMLTreeIterator;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Iterables;

/**
 * A provider of opaque resource references from a model or other file being validated.
 * These references are "opaque" inasmuch as they are references by URI or path,
 * not by {@link EReference}, to binary or other resources that the model
 * requires to be deployed with it.
 */
public interface OpaqueResourceProvider<E, P extends OpaqueResourceProvider<E, P>> {

	/**
	 * Get the collection of opaque resources indicated by an {@code object} in a file
	 * being validated in a project being built.
	 *
	 * @param modelProject
	 *            the project being built
	 * @param modelFile
	 *            the model file being validated
	 * @param object
	 *            an object in the file for which to get opaque resource references
	 * @param diagnostics
	 *            the diagnostic chain on which to report the non-existence of referenced resources
	 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
	 *            <tt>build.properties</tt> checking, etc.
	 * @param context
	 *            the validation context for construction of problem diagnostics
	 * @return the references, or an empty collection if none
	 */
	Iterable<ClassifiedURI> getOpaqueResourceReferences(IProject modelProject, IFile modelFile, E object,
			DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * Create an opaque resource provider that provides both my resources and an{@code other}'s resources.
	 *
	 * @param other
	 *            another resource provider compatible with me
	 * @return the combined resource provider
	 */
	default OpaqueResourceProvider<E, P> and(P other) {
		return other == null
				? this
				: (modelProject, modelFile, object, diagnostics, context) -> Iterables.concat(
						getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context),
						other.getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context));
	}

	/**
	 * Process a <tt>plugin.xml</tt> file, extracting all of the opaque resource URIs from it.
	 *
	 * @param modelProject
	 *            the project being built
	 * @param modelFile
	 *            the model file being validated
	 * @param content
	 *            the contents of the file being validated
	 * @param diagnostics
	 *            the diagnostic chain on which to report the non-existence of referenced resources
	 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
	 *            <tt>build.properties</tt> checking, etc.
	 * @param existingURITransform
	 *            a function to transform each distinct URI found in the model that resolves to an existing opaque resource
	 * @param acceptor
	 *            processes each transformed existing opaque resource URI
	 */
	default <V> void process(IProject modelProject, IFile modelFile, Stream<E> content, DiagnosticChain diagnostics,
			Function<? super ClassifiedURI, V> existingURITransform, Consumer<? super V> acceptor) {

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final EValidator.SubstitutionLabelProvider labels = createSubstitutionLabelProvider(adapterFactory);

		try {
			Map<Object, Object> context = new HashMap<>();
			context.put(EValidator.SubstitutionLabelProvider.class, labels);

			content.map(object -> getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context))
					.filter(not(Iterables::isEmpty))
					.map(Iterable::spliterator).flatMap(uris -> StreamSupport.stream(uris, false))
					.distinct()
					.map(existingURITransform)
					.forEach(acceptor);
		} finally {
			adapterFactory.dispose();
		}
	}

	/**
	 * Create an opaque resource provider combines the resources provided by two providers, either
	 * of which may be {@code null}.
	 *
	 * @param provider
	 *            a resource provider
	 * @param other
	 *            another resource provider compatible with it
	 * @return the combined resource provider, or {@code null} if both are {@code null}
	 */
	@SuppressWarnings("unchecked")
	static <E, P extends OpaqueResourceProvider<E, P>> P and(P provider, P other) {
		return provider == null ? other : other == null ? provider : (P) provider.and(other);
	}

	//
	// Nested types
	//

	/**
	 * Opaque resource provider for EMF {@link EObject}s.
	 */
	@FunctionalInterface
	interface EMF extends OpaqueResourceProvider<EObject, EMF> {

		/**
		 * Process an EMF model resource, extracting all of the opaque resource URIs from it.
		 *
		 * @param modelProject
		 *            the project being built
		 * @param modelFile
		 *            the model file being validated
		 * @param modelResource
		 *            the model resource for which to get opaque resource references from every object in it
		 * @param diagnostics
		 *            the diagnostic chain on which to report the non-existence of referenced resources
		 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
		 *            <tt>build.properties</tt> checking, etc.
		 * @param existingURIAcceptor
		 *            processes each distinct URI found in the model that resolves to an existing opaque resource
		 */
		default void processModel(IProject modelProject, IFile modelFile, Resource modelResource,
				DiagnosticChain diagnostics, Consumer<? super ClassifiedURI> existingURIAcceptor) {

			processModel(modelProject, modelFile, modelResource, diagnostics, Function.identity(), existingURIAcceptor);
		}

		/**
		 * Process an EMF model resource, extracting all of the opaque resource URIs from it.
		 *
		 * @param modelProject
		 *            the project being built
		 * @param modelFile
		 *            the model file being validated
		 * @param modelResource
		 *            the model resource for which to get opaque resource references from every object in it
		 * @param diagnostics
		 *            the diagnostic chain on which to report the non-existence of referenced resources
		 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
		 *            <tt>build.properties</tt> checking, etc.
		 * @param existingURITransform
		 *            a function to transform each distinct URI found in the model that resolves to an existing opaque resource
		 * @param acceptor
		 *            processes each transformed existing opaque resource URI
		 */
		default <V> void processModel(IProject modelProject, IFile modelFile, Resource modelResource, DiagnosticChain diagnostics,
				Function<? super ClassifiedURI, V> existingURITransform, Consumer<? super V> acceptor) {

			process(modelProject, modelFile, Iterators2.stream(modelResource.getAllContents()), diagnostics, existingURITransform, acceptor);
		}

		@Override
		default OpaqueResourceProvider.EMF and(OpaqueResourceProvider.EMF other) {
			return (modelProject, modelFile, object, diagnostics, context) -> Iterables.concat(
					getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context),
					other.getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context));
		}

		/**
		 * Create an opaque resource provider on an {@code attribute}. Relative URIs are assumed to be
		 * relative to the project being built.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param attribute
		 *            an attribute that encodes the resource reference in the model. The attribute
		 *            must be of either {@link URI} or {@link String} type
		 *
		 * @return the opaque resource provider
		 *
		 * @throws IllegalArgumentException
		 *             if the {@link attribute} is of a type that is not convertible to an URI
		 */
		static OpaqueResourceProvider.EMF create(Object classifier, String diagnosticSource, EAttribute attribute) {
			return new BasicEMFResourceProvider(classifier, diagnosticSource, attribute);
		}

		/**
		 * Create an opaque resource provider on an {@code attribute} with a bundle qualifier for relative URIs.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param attribute
		 *            an attribute that encodes the resource reference in the model. The attribute
		 *            must be of either {@link URI} or {@link String} type
		 * @param bundleQualifier
		 *            an attribute that, if it has a value, indicates the bundle project that
		 *            is the basis of a relative URI. When the qualifier does not have a value, a relative URI is
		 *            assumed to be relative to the project being built. The bundle qualifier must be of {@link String} type
		 *
		 * @return the opaque resource provider
		 *
		 * @throws IllegalArgumentException
		 *             if the {@link attribute} is of a type that is not convertible to an URI
		 *             or the bundle qualifier is not of string type. Or, if the bundle qualifier
		 *             is specified, it is not an attribute of the same class that features the
		 *             referencing {@code attribute}
		 */
		static OpaqueResourceProvider.EMF create(Object classifier, String diagnosticSource, EAttribute attribute, EAttribute bundleQualifier) {
			return new BasicEMFResourceProvider(classifier, diagnosticSource, attribute, bundleQualifier);
		}

		/**
		 * Create an opaque resource provider on an {@code attribute}. Relative URIs are assumed to be
		 * relative to the project being built.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param attribute
		 *            an attribute that encodes the resource reference in the model
		 * @param uriAccessor
		 *            computes the URI from the {@code attribute} value in a particular object
		 *
		 * @return the opaque resource provider
		 *
		 * @throws IllegalArgumentException
		 *             if the {@link attribute} is of a type that is not convertible to an URI
		 */
		@SuppressWarnings("unchecked")
		static <V> OpaqueResourceProvider.EMF create(Object classifier, String diagnosticSource, EAttribute attribute,
				URIAccessor<EObject, EAttribute, V> uriAccessor) {
			return new BasicEMFResourceProvider(classifier, diagnosticSource, attribute, (URIAccessor<EObject, EAttribute, Object>) uriAccessor);
		}

		/**
		 * Create an opaque resource provider on an {@code attribute} with a bundle qualifier for relative URIs.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param attribute
		 *            an attribute that encodes the resource reference in the model. The attribute
		 *            must be of either {@link URI} or {@link String} type
		 * @param bundleQualifier
		 *            an attribute that, if it has a value, indicates the bundle project that
		 *            is the basis of a relative URI. When the qualifier does not have a value, a relative URI is
		 *            assumed to be relative to the project being built. The bundle qualifier must be of {@link String} type
		 * @param uriAccessor
		 *            computes the URI from the {@code attribute} value in a particular object
		 *
		 * @return the opaque resource provider
		 *
		 * @throws IllegalArgumentException
		 *             if the {@link attribute} is of a type that is not convertible to an URI
		 *             or the bundle qualifier is not of string type. Or, if the bundle qualifier
		 *             is specified, it is not an attribute of the same class that features the
		 *             referencing {@code attribute}
		 */
		@SuppressWarnings("unchecked")
		static <V> OpaqueResourceProvider.EMF create(Object classifier, String diagnosticSource, EAttribute attribute, EAttribute bundleQualifier,
				URIAccessor<EObject, EAttribute, V> uriAccessor) {
			return new BasicEMFResourceProvider(classifier, diagnosticSource, attribute, bundleQualifier, (URIAccessor<EObject, EAttribute, Object>) uriAccessor);
		}

	}

	/**
	 * Opaque resource provider for <tt>plugin.xml</tt> {@link Element}s.
	 */
	@FunctionalInterface
	interface XML extends OpaqueResourceProvider<Element, XML> {

		/**
		 * Process a <tt>plugin.xml</tt> file, extracting all of the opaque resource URIs from it.
		 *
		 * @param modelProject
		 *            the project being built
		 * @param pluginXML
		 *            the <tt>plugin.xml</tt> file being validated
		 * @param diagnostics
		 *            the diagnostic chain on which to report the non-existence of referenced resources
		 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
		 *            <tt>build.properties</tt> checking, etc.
		 * @param existingURIAcceptor
		 *            processes each distinct URI found in the model that resolves to an existing opaque resource
		 */
		default void processPluginXML(IProject modelProject, IFile pluginXML, DiagnosticChain diagnostics,
				Consumer<? super ClassifiedURI> existingURIAcceptor) {

			processModel(modelProject, pluginXML, diagnostics, Function.identity(), existingURIAcceptor);
		}

		/**
		 * Process a <tt>plugin.xml</tt> file, extracting all of the opaque resource URIs from it.
		 *
		 * @param modelProject
		 *            the project being built
		 * @param pluginXML
		 *            the <tt>plugin.xml</tt> file being validated
		 * @param diagnostics
		 *            the diagnostic chain on which to report the non-existence of referenced resources
		 *            (which concern is apart from the primary use of this interface, for manifest dependency checking,
		 *            <tt>build.properties</tt> checking, etc.
		 * @param existingURITransform
		 *            a function to transform each distinct URI found in the model that resolves to an existing opaque resource
		 * @param acceptor
		 *            processes each transformed existing opaque resource URI
		 */
		default <V> void processModel(IProject modelProject, IFile pluginXML, DiagnosticChain diagnostics,
				Function<? super ClassifiedURI, V> existingURITransform, Consumer<? super V> acceptor) {

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc;

				try (InputStream content = pluginXML.getContents()) {
					doc = builder.parse(content, String.valueOf(pluginXML.getLocation()));
				}

				process(modelProject, pluginXML, Iterators2.stream(new XMLTreeIterator(doc)), diagnostics, existingURITransform, acceptor);
			} catch (Exception e) {
				Activator.log.error("Failed to parse the plugin.xml file.", e); //$NON-NLS-1$
			}
		}

		@Override
		default OpaqueResourceProvider.XML and(OpaqueResourceProvider.XML other) {
			return (modelProject, modelFile, object, diagnostics, context) -> Iterables.concat(
					getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context),
					other.getOpaqueResourceReferences(modelProject, modelFile, object, diagnostics, context));
		}

		/**
		 * Create an opaque resource provider on an {@code attribute} of some {@code element} in extensions on a given point.
		 * Relative paths are assumed to be relative to the project being built.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param extensionPoint
		 *            the extension point in which to extract resource URIs from its {@code element}s
		 * @param element
		 *            the element in the extension from which to extract resource URIs
		 * @param attribute
		 *            an attribute that encodes the resource reference in the <tt>plugin.xml</tt>
		 *
		 * @return the opaque resource provider
		 */
		static OpaqueResourceProvider.XML create(Object classifier, String diagnosticSource, String extensionPoint, String element, String attribute) {
			return new BasicPluginXMLResourceProvider(classifier, diagnosticSource, extensionPoint, element, attribute);
		}

		/**
		 * Create an opaque resource provider on an {@code attribute} of some {@code element} in extensions on a given point.
		 * Relative paths are assumed to be relative to the project being built.
		 *
		 * @param classifier
		 *            a localized classifier to indicate to the user what kind of resources I provide
		 * @param diagnosticSource
		 *            the source for reporting any diagnostics for non-existent resources
		 * @param extensionPoint
		 *            the extension point in which to extract resource URIs from its {@code element}s
		 * @param element
		 *            the element in the extension from which to extract resource URIs
		 * @param attribute
		 *            an attribute that encodes the resource reference in the <tt>plugin.xml</tt>
		 * @param uriAccessor
		 *            computes the URI from the {@code attribute} value in a particular object
		 *
		 * @return the opaque resource provider
		 */
		static OpaqueResourceProvider.XML create(Object classifier, String diagnosticSource, String extensionPoint, String element, String attribute,
				URIAccessor<Element, String, String> uriAccessor) {
			return new BasicPluginXMLResourceProvider(classifier, diagnosticSource, extensionPoint, element, attribute, uriAccessor);
		}

	}

	/** A resource URI classified to indicate what kind of resource it is. */
	interface ClassifiedURI {

		/** The default classifier if otherwise unknown is just a generic "resource". */
		Object DEFAULT_CLASSIFIER = ResourceKind.GENERIC;

		/** The URI. */
		URI uri();

		/** The localized classifier of the URI, indicating to the user what kind of resource it is. */
		Object classifier();

	}

	/** A function that converts the value of some attribute of an object to an {@link URI}. */
	@FunctionalInterface
	interface URIAccessor<E, A, T> {

		/** Get the URI from a {@code value} in an {@code attribute} of an {@code object}. */
		URI getURI(E object, A attribute, T value);

	}

	/** Enumeration of commonly referenced kinds of resources. */
	enum ResourceKind {
		/** Generic resource, of otherwise unknown or unspecified type. */
		GENERIC(Messages.ResourceKind_0),
		/** An icon image resource. */
		ICON(Messages.ResourceKind_1),
		/** A Java class resource. */
		CLASS(Messages.ResourceKind_2),
		/** A metamodel (Ecore or UML package) resource. */
		METAMODEL(Messages.ResourceKind_3);

		private final String localName;

		ResourceKind(String localName) {
			this.localName = localName;
		}

		@Override
		public String toString() {
			return localName;
		}
	}

}
