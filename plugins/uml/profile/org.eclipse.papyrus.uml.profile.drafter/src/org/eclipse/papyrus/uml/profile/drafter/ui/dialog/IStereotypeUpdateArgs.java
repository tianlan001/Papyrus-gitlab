/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
 *
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.dialog;

import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This interface is used to collect the data needed to create or update a {@link Stereotype}.
 * 
 * @author cedric dumoulin
 *
 */
public interface IStereotypeUpdateArgs {


	public abstract void setStereotypeToUpdate(Stereotype stereotypeToUpdate);

	public abstract Stereotype getStereotypeToUpdate();

	public abstract void setProfileName(String profileName);

	public abstract String getProfileName();

	public abstract void setStereotypeName(String stereotypeName);

	public abstract String getStereotypeName();

	public abstract List<Stereotype> getExtendedStereotypes();

	public abstract List<Class> getExtendedMetaclasses();

}
