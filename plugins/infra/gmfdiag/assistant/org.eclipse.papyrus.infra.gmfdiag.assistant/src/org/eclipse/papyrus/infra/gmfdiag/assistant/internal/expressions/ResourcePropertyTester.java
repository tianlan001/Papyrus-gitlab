/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.expressions;

import java.io.File;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.ModelingAssistantModelRegistry;

/**
 * Properties for things that are resource-like.
 */
public class ResourcePropertyTester extends PropertyTester {
	private static final String PROPERTY_IS_DEPLOYED = "isDeployed"; //$NON-NLS-1$

	public ResourcePropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		switch (property) {
		case PROPERTY_IS_DEPLOYED:
			URI uri = getURI(receiver);
			if (uri != null) {
				result = ModelingAssistantModelRegistry.getInstance().isRegistered(uri) == asBoolean(expectedValue);
			}
			break;
		}

		return result;
	}

	static URI getURI(Object object) {
		URI result = null;

		if (object instanceof URI) {
			result = (URI) object;
		} else if (object instanceof Resource) {
			result = ((Resource) object).getURI();
		} else if (object instanceof EObject) {
			result = EcoreUtil.getURI((EObject) object);
		} else if (object instanceof IFile) {
			result = URI.createPlatformResourceURI(((IFile) object).getFullPath().toString(), true);
		} else if (object instanceof File) {
			result = URI.createFileURI(((File) object).getAbsolutePath());
		}

		return result;
	}

	static boolean asBoolean(Object expectedValue) {
		// The default test is for a true value, so take null as true
		return (expectedValue instanceof Boolean) ? ((Boolean) expectedValue).booleanValue() : ((expectedValue == null) || Boolean.parseBoolean(String.valueOf(expectedValue)));
	}

}
