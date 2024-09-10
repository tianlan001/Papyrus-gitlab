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

package org.eclipse.papyrus.infra.properties.contexts.util;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.Annotation;
import org.eclipse.papyrus.infra.properties.contexts.ContextsFactory;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;

/**
 * Utilities for working with {@link Annotation}s in context models.
 */
public class ContextAnnotations {

	/** The source URI for annotations known by the properties framework and tooling. */
	public static final String ANNOTATION_SOURCE = "http://www.eclipse.org/papyrus/properties/contexts"; //$NON-NLS-1$

	/**
	 * Detail indicating the URI of the source model element that the context element
	 * corresponds to (often was generated from).
	 */
	public static final String DETAIL_MODEL = "model"; //$NON-NLS-1$

	/**
	 * Detail indicating the URI of the layout generator class that was used to generate
	 * the XWT sections for the data context.
	 */
	public static final String DETAIL_LAYOUT_GENERATOR_CLASS = "layoutGenerator"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private ContextAnnotations() {
		super();
	}

	/**
	 * Query the source model element from which a <em>Properties Context</em> model element was generated.
	 *
	 * @param contextElement
	 *            a properties context model element. The context element must already be attached to
	 *            the resource set, otherwise the source model retrieval cannot happen
	 * @return the corresponding source model element, or {@code null} if none could be determined.
	 *
	 * @see #getSourceModel(Annotatable, EObject) {@code getSourceModel(Annotatable, EObject)}
	 *      for cases where the properties context element is not yet attached
	 */
	public static EObject getSourceModel(Annotatable contextElement) {
		return getSourceModel(contextElement, contextElement);
	}

	/**
	 * Query the source model element from which a <em>Properties Context</em> model element was generated,
	 * in the resource set implied by the given {@code context} object.
	 *
	 * @param contextElement
	 *            a properties context model element
	 * @param context
	 *            some other element from the properties context model that already exists in the resource set
	 *
	 * @return the corresponding source model element, or {@code null} if none
	 */
	public static EObject getSourceModel(Annotatable contextElement, EObject context) {
		Resource contextResource = context.eResource();
		if (contextResource == null) {
			return null;
		}

		EObject result = null;

		String modelURIDetail = getAnnotation(contextElement, ANNOTATION_SOURCE, DETAIL_MODEL);
		URI modelURI = modelURIDetail == null ? null : URI.createURI(modelURIDetail, true);
		if (modelURI != null && modelURI.isRelative()) {
			modelURI = modelURI.resolve(contextResource.getURI());
		}

		if (modelURI != null) {
			URI resolved = modelURI;
			result = Optional.ofNullable(contextResource.getResourceSet()).map(rset -> safeGetEObject(rset, resolved))
					// Create a proxy to indicate that we have a source reference, but it doesn't resolve
					.orElseGet(() -> createProxy(resolved));
		} else {
			Annotation annotation = contextElement.getAnnotation(ANNOTATION_SOURCE);
			if (annotation != null && !annotation.getReferences().isEmpty()) {
				// Legacy case
				result = annotation.getReferences().get(0);
			}
		}

		return result;
	}

	private static EObject safeGetEObject(ResourceSet resourceSet, URI uri) {
		EObject result;

		try {
			result = resourceSet.getEObject(uri, true);
		} catch (Exception e) {
			// Resource does not exist or is otherwise not loadable
			result = createProxy(uri);
		}

		return result;
	}

	private static EObject createProxy(URI proxyURI) {
		EObject result = EcoreFactory.eINSTANCE.createEObject();
		((InternalEObject) result).eSetProxyURI(proxyURI);
		return result;
	}

	/**
	 * Query the URI of the source model element from which a <em>Properties Context</em> model element was generated.
	 *
	 * @param contextElement
	 *            a properties context model element
	 * 
	 * @return the URI of the corresponding source model element, or {@code null} if none
	 */
	public static URI getSourceModelURI(Annotatable contextElement) {
		return getSourceModelURI(contextElement, contextElement);
	}

	/**
	 * Query the URI of the source model element from which a <em>Properties Context</em> model element was generated.
	 *
	 * @param contextElement
	 *            a properties context model element
	 * @param context
	 *            some other element from the properties context model that already exists in the resource set
	 * 
	 * @return the URI of the corresponding source model element, or {@code null} if none
	 */
	public static URI getSourceModelURI(Annotatable contextElement, EObject context) {
		String modelURIDetail = getAnnotation(contextElement, ANNOTATION_SOURCE, DETAIL_MODEL);
		URI result = modelURIDetail == null ? null : URI.createURI(modelURIDetail, true);
		if (result != null && result.isRelative()) {
			Resource contextResource = context.eResource();
			if (contextResource != null) {
				result = result.resolve(contextResource.getURI());
			}
		}

		return result;
	}

	/**
	 * Set the source model element from which a <em>Properties Context</em> model element was generated.
	 *
	 * @param contextElement
	 *            a context model element
	 * @param the
	 *            corresponding source model element
	 */
	public static void setSourceModel(Annotatable contextElement, EObject sourceElement) {
		URI modelURI = (sourceElement == null) ? null : EcoreUtil.getURI(sourceElement);

		if (modelURI != null) {
			setAnnotation(contextElement, ANNOTATION_SOURCE, DETAIL_MODEL, modelURI.toString());
		} else {
			Annotation annotation = contextElement.getAnnotation(ANNOTATION_SOURCE);
			if (annotation != null) {
				annotation.getDetails().removeKey(DETAIL_MODEL);
				// Legacy case
				annotation.getReferences().remove(sourceElement);
			}
		}
	}

	public static Annotation getAnnotation(Annotatable contextElement, String source) {
		return getAnnotation(contextElement, source, false);
	}

	public static Annotation getAnnotation(Annotatable contextElement, String source, boolean create) {
		Annotation result = contextElement.getAnnotation(source);
		if (result == null && create) {
			result = ContextsFactory.eINSTANCE.createAnnotation();
			result.setSource(source);
			contextElement.getAnnotations().add(result);
		}
		return result;
	}

	public static String getAnnotation(Annotatable contextElement, String source, String detail) {
		Annotation annotation = getAnnotation(contextElement, source);
		return annotation != null ? annotation.getDetails().get(detail) : null;
	}

	public static void setAnnotation(Annotatable contextElement, String source, String detail, String value) {
		getAnnotation(contextElement, source, true).getDetails().put(detail, value);
	}

	/**
	 * Query the name of the class that generated the layouts of sections for the given data context.
	 *
	 * @param dataContext
	 *            a data context
	 * @return the name of the layout-generator class that was used to generate its sections, or {@code null} if none
	 */
	public static String getLayoutGeneratorClassName(DataContextRoot dataContext) {
		return getAnnotation(dataContext, ANNOTATION_SOURCE, DETAIL_LAYOUT_GENERATOR_CLASS);
	}

	/**
	 * Set the name of the class that generated the layouts of sections in the given data context.
	 *
	 * @param dataContext
	 *            a data context
	 * @return className the name of the layout-generator class that was used to generate its sections
	 */
	public static void setLayoutGeneratorClassName(DataContextRoot dataContext, String className) {
		setAnnotation(dataContext, ANNOTATION_SOURCE, DETAIL_LAYOUT_GENERATOR_CLASS, className);
	}

}
