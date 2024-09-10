/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Stereotype
import org.eclipse.xtend.lib.annotations.Data

/**
 * An analogue of the UML {@code Extension} metaclass that represents an implied metaclass extension.
 * This may be modeled in the profile either directly, via an extension, or indirectly, via an
 * inherited extension.
 */
@Data class ImpliedExtension {
    Stereotype stereotype
    Class metaclass
}
