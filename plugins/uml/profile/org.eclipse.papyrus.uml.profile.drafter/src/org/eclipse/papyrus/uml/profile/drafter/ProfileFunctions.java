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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;

import com.google.common.base.Function;


/**
 * Set of utilities {@link Function} for collections of {@link Profile}.
 * 
 * @author cedric dumoulin
 *
 */
public class ProfileFunctions {

	public static ProfileFunctions INSTANCE = new ProfileFunctions();
	
	public static Function<ProfileApplication, Profile> PROFILE_APPLICATION_GETAPPLIEDPROFILE = INSTANCE.new ProfileApplicationProfile();
	public static Function<ProfileApplication, String>  PROFILE_APPLICATION_PROFILE_NAME = INSTANCE.new ProfileApplicationProfileName();
	public static Function<ProfileApplication, String>  PROFILE_APPLICATION_PROFILE_QUALIFIEDNAME = INSTANCE.new ProfileApplicationProfileQualifiedName();
	public static Function<Stereotype, String>  STEROTYPE_NAME = INSTANCE.new StereotypeName();
	public static Function<Stereotype, String>  STEROTYPE_QUALIFIEDNAME = INSTANCE.new StereotypeQualifiedName();
	
	/**
	 * function {@link ProfileApplication#getAppliedProfile()}
	 * 
	 */
	public class ProfileApplicationProfile implements Function<ProfileApplication, Profile> {
		
		public Profile apply(ProfileApplication input) {
			return input.getAppliedProfile();
		};
	}
	
	/**
	 * function {@link ProfileApplication#getAppliedProfile()}
	 * 
	 */
	public class ProfileApplicationProfileName implements Function<ProfileApplication, String> {
		
		public String apply(ProfileApplication input) {
			return UMLLabelInternationalization.getInstance().getLabel(input.getAppliedProfile());
		};
	}
	
	/**
	 * function {@link ProfileApplication#getAppliedProfile()}
	 * 
	 */
	public class ProfileApplicationProfileQualifiedName implements Function<ProfileApplication, String> {
		
		public String apply(ProfileApplication input) {
			return input.getAppliedProfile().getQualifiedName();
		};
	}
	
	/**
	 * function {@link ProfileApplication#getAppliedProfile()}
	 * 
	 */
	public class StereotypeName implements Function<Stereotype, String> {
		
		public String apply(Stereotype input) {
			return UMLLabelInternationalization.getInstance().getKeyword(input);
		};
	}
	/**
	 * function {@link ProfileApplication#getAppliedProfile()}
	 * 
	 */
	public class StereotypeQualifiedName implements Function<Stereotype, String> {
		
		public String apply(Stereotype input) {
			return input.getQualifiedName();
		};
	}
}
