/*****************************************************************************
 * Copyright (c) 2008, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.utils;

import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;

/**
 * ResourceSet Manager for UML and DI files, and also other loaded models.
 *
 * @author Cedric dumoulin
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 * @author <a href="mailto:thomas.szadel@atosorigin.com">Thomas Szadel</a>
 *
 * @deprecated Use ModelSet instead.
 */
@Deprecated
public class DiResourceSet extends ModelSet {

	/**
	 *
	 * Constructor.
	 *
	 */
	public DiResourceSet() {
		super();
		// Register declared models
		ModelsReader reader = new ModelsReader();
		reader.readModel(this);
	}

}
