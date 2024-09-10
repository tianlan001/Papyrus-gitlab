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

package org.eclipse.papyrus.uml.decoratormodel.internal.controlmode.updaters;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ProfileApplication;

/**
 * @author damus
 *
 */
public interface IDecoratorModelUpdaterDelegate {

	Package getDecoratorPackage(Resource resource);

	Package createDecoratorPackage(Resource resource);

	ProfileApplication getProfileApplication(Package package_, URI appliedProfileURI);

	ProfileApplication addProfileApplication(Package package_, URI profileURI, URI appliedDefinitionURI);

}
