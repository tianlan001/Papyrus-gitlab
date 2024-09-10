/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts.util;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.papyrus.infra.properties.contexts.Annotation;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.util.ContextsResourceFactoryImpl
 * @generated
 */
public class ContextsResourceImpl extends XMIResourceImpl {
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param uri
	 *            the URI of the new resource.
	 * @generated
	 */
	public ContextsResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
	 *
	 * @return
	 * @generated NOT
	 */
	@Override
	protected boolean useUUIDs() {
		return true;
	}

	@Override
	protected XMLHelper createXMLHelper() {
		return new ContextsXMIHelper();
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new ContextsXMILoad(createXMLHelper());
	}

	boolean isAnnotationDetailURI(EObject object, EStructuralFeature feature) {
		return feature == EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY__VALUE
				&& "model".equals(((Map.Entry<?, ?>) object).getKey())
				&& object.eContainer() instanceof Annotation;
	}

	void resolveURIs(Annotation annotation) {
		for (Map.Entry<String, String> detail : annotation.getDetails()) {
			if ("model".equals(detail.getKey())) {
				// The value is a URI
				String rawValue = detail.getValue();
				if (rawValue != null && !rawValue.isBlank()) {
					URI uri = URI.createURI(rawValue, true);
					if (uri.isRelative()) {
						uri = uri.resolve(getURI());
						detail.setValue(uri.toString());
					}
				}
			}
		}
	}

	private class ContextsXMIHelper extends XMIHelperImpl {

		ContextsXMIHelper() {
			super(ContextsResourceImpl.this);
		}

		@Override
		public Object getValue(EObject object, EStructuralFeature feature) {
			if (isAnnotationDetailURI(object, feature)) {
				// Deresolve the URI
				String rawValue = (String) ((Map.Entry<?, ?>) object).getValue();
				if (rawValue != null && !rawValue.isBlank()) {
					URI uri = URI.createURI(rawValue);
					URI base = ContextsResourceImpl.this.getURI();
					return uri.deresolve(base).toString();
				}
			}

			return super.getValue(object, feature);
		}
	}

	private class ContextsXMILoad extends XMILoadImpl {

		ContextsXMILoad(XMLHelper helper) {
			super(helper);
		}

		@Override
		protected DefaultHandler makeDefaultHandler() {
			return new ContextsXMIHandler(resource, helper, options);
		}

	}

	private class ContextsXMIHandler extends SAXXMIHandler {

		ContextsXMIHandler(XMLResource xmiResource, XMLHelper helper, Map<?, ?> options) {
			super(xmiResource, helper, options);
		}

		@Override
		public void endElement(String uri, String localName, String name) {
			EObject object = objects.peekEObject();
			if (object instanceof Annotation) {
				resolveURIs((Annotation) object);
			}

			super.endElement(uri, localName, name);
		}

	}

} // ContextsResourceImpl
