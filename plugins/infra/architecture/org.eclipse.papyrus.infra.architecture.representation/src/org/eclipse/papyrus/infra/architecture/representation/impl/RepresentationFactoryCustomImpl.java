/**
 * Copyright (c) 2015 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Benoit Maggi benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 463156
 *
 */
package org.eclipse.papyrus.infra.architecture.representation.impl;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationFactory;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;


public class RepresentationFactoryCustomImpl extends RepresentationFactoryImpl implements RepresentationFactory {

	public static RepresentationFactory init() {
		try {
			RepresentationFactory theRepresentationFactory = (RepresentationFactory) EPackage.Registry.INSTANCE.getEFactory(RepresentationPackage.eNS_URI);
			if (theRepresentationFactory != null) {
				return theRepresentationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RepresentationFactoryImpl();
	}

	/**
	 * Creates an instance of the custom factory.
	 */
	public RepresentationFactoryCustomImpl() {
		super();
	}

	@Override
	public ModelAutoCreate createModelAutoCreate() {
		return new ModelAutoCreateCustomImpl();
	}

}
