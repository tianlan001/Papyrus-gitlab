/******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: 
 *    Borland - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.migrate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;

public class MigrationHelper extends XMIHelperImpl {
	private final MigrationDelegate myDelegate;

	public MigrationHelper(XMLResource resource, MigrationDelegate delegate) {
		super(resource);
		assert delegate != null;
		myDelegate = delegate;
	}

	boolean isMigrationApplied() {
		return myDelegate.isMigrationApplied();
	}
	
	@Override
	public void setValue(EObject object, EStructuralFeature feature, Object value, int position) {
		if (!myDelegate.setValue(object, feature, value, position)) {
			super.setValue(object, feature, value, position);
		}
	}

	@Override
	public EStructuralFeature getFeature(EClass eClass, String namespaceURI, String name, boolean isElement) {
		EStructuralFeature result = myDelegate.getFeature(eClass, namespaceURI, name, isElement);
		if (result == null) {
			result = super.getFeature(eClass, namespaceURI, name, isElement);
		}
		return result;
	}
	
	@Override
	public EClassifier getType(EFactory factory, String typeName) {
		EClassifier result = myDelegate.getType(factory, typeName);
		if (result == null) {
			result = super.getType(factory, typeName);
		}
		return result;
	}

	@Override
	public void popContext() {
		super.popContext();
		myDelegate.preResolve();
	}

	@Override
	public String getURI(String prefix) {
		String uri = super.getURI(prefix);
		String newUri = myDelegate.getURI(prefix, uri);
		return newUri == null ? uri : newUri;
	}

}
