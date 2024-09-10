/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  IJI - Adapted for new Alf integration
 *  MDS - Revised for Luna
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.libraries.helper;

import java.util.Iterator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Singleton class providing a set of methods to manipulate the ALF profile
 */
public class AlfUtil {

	private AlfUtil() {
	}

	private static AlfUtil singleton;

	/**
	 * Provide a getter to obtain a reference over the helper;
	 * 
	 * @return singleton
	 */
	public static AlfUtil getInstance() {
		if (singleton == null) {
			singleton = new AlfUtil();
		}
		return singleton;
	}

	/**
	 * This retrieve the textual representation associated to a particular UML element.
	 * if there is no associated textual representation the result obtain is TextualRepresentation
	 * object which is not initialized
	 * 
	 * @param element
	 *            - the UML element from which we extract the textual representation
	 * @return representation - the textual representation
	 */
	public String getTextualRepresentation(Element element) {
		String representation = "";
		Comment textualRepresentationComment = this.getTextualRepresentationComment(element);
		if (textualRepresentationComment != null) {
			representation = textualRepresentationComment.getBody();
		}
		return representation;
	}

	/**
	 * Retrieve the first comment owned by this element being a textual representation comment
	 * 
	 * @param element
	 *            - the UML element that is expected to own the comment
	 * @return textualRepresentationComment - the textual representation comment found
	 */
	public Comment getTextualRepresentationComment(Element element) {
		Comment textualRepresentationComment = null;
		Iterator<Comment> commentsIterator = element.getOwnedComments().iterator();
		while (commentsIterator.hasNext() && textualRepresentationComment == null) {
			Comment comment = commentsIterator.next();
			if (comment.getBody() != null && isATextualRepresentationComment(comment)) {
				textualRepresentationComment = comment;
			}
		}
		return textualRepresentationComment;
	}

	/**
	 * Retrieve a reference on the standard profile that is used in the specified context
	 * 
	 * @param context
	 *            - the context
	 * @return a reference to the standard profile
	 */
	public Profile getStandardProfile(Model context) {
		return this.getAppliedProfile(context, AlfUtilConstants.STANDARD_PROFILE_LOADING_NAME);
	}

	/**
	 * Retrieve a reference on the action language profile that is used in the specified context
	 * 
	 * @param context
	 *            - the context
	 * @return a reference to the action language profile
	 */
	public Profile getActionLanguageProfile(Model context) {
		return this.getAppliedProfile(context, AlfUtilConstants.ACTION_LANGUAGE_PROFILE_NAME);
	}

	/**
	 * Load a profile in the context of the resource containing the model
	 * 
	 * @param root
	 *            - the context
	 * @param name
	 *            - the name of the profile that needs to be loaded
	 * @return profile - a reference to the loaded profile
	 */
	public Profile loadProfile(Model context, String name) {
		/* 1. Retrieve the profile from a resource already loaded in the context of the model */
		Profile searchedProfile = null;
		ResourceSet resourceSet = context.eResource().getResourceSet();
		if (resourceSet != null) {
			IRegisteredProfile registeredProfile = RegisteredProfile.getRegisteredProfile(name);
			if (registeredProfile != null) {
				Iterator<Resource> resourceIterator = resourceSet.getResources().iterator();
				Resource searchedResource = null;
				while (searchedResource == null && resourceIterator.hasNext()) {
					searchedResource = resourceIterator.next();
					if (!searchedResource.getURI().equals(registeredProfile.getUri())) {
						searchedResource = null;
					}
				}
				if (searchedResource != null) {
					searchedProfile = (Profile) searchedResource.getContents().get(0);
				}
			}
		}
		/* 2. The profile is not loaded in the resourceset of this model. Load it and return a reference to the profile */
		if (searchedProfile == null) {
			IRegisteredProfile registeredProfile = RegisteredProfile.getRegisteredProfile(name);
			if (registeredProfile != null) {
				Resource modelResource = context.eResource().getResourceSet().getResource(registeredProfile.getUri(), true);
				if (modelResource.getContents().get(0) instanceof Profile) {
					searchedProfile = (Profile) modelResource.getContents().get(0);
				}
			}
		}
		return searchedProfile;
	}

	/**
	 * Return true if the profile which name is given as parameter is applied on the model;
	 * false otherwise
	 * 
	 * @param context
	 *            - the model for which the profile application is checked
	 * @param profileName
	 *            - the name of the profile for which we try to detect an application
	 * @return
	 * 		- true if applied false otherwise
	 */
	protected boolean isProfileApplied(Model context, final String profileName) {
		return this.getAppliedProfile(context, profileName) != null;
	}

	/**
	 * Get a reference on the profile that is used in the specified context
	 * 
	 * @param context
	 *            - the context
	 * @param profileName
	 *            - the name of the profile for which we look for an application
	 * @return a reference on the profile or null
	 */
	protected Profile getAppliedProfile(Model context, final String profileName) {
		Iterator<Profile> appliedProfilesIterator = context.getAppliedProfiles().iterator();
		Profile searchedProfile = null;
		while (searchedProfile == null && appliedProfilesIterator.hasNext()) {
			searchedProfile = appliedProfilesIterator.next();
			if (!searchedProfile.getName().equals(profileName)) {
				searchedProfile = null;
			}
		}
		return searchedProfile;
	}

	/**
	 * Retrieve a reference on the definition of the textual representation stereotype
	 * 
	 * @param comment
	 *            - the comment from which the definition can retrived
	 * @return textualRepresentation - the definition of the stereotype textual representation
	 */
	private Stereotype getTextualRepresentationStereotype(Element context) {
		if (context != null) {
			Profile actionLanguageProfile = this.getAppliedProfile(context.getModel(),
					AlfUtilConstants.ACTION_LANGUAGE_PROFILE_NAME);
			if (actionLanguageProfile != null) {
				return actionLanguageProfile.getOwnedStereotype(
						AlfUtilConstants.TEXTUAL_REPRESENTATION_STEREOTYPE_NAME);
			}
		}
		return null;
	}


	/**
	 * Returns whether the given comment has the stereotype textual representation applied
	 * 
	 * @param comment
	 *            - the assessed comment
	 * @return true if the textual representation stereotype is applied
	 */
	public boolean isATextualRepresentationComment(Comment comment) {
		Stereotype textualRepresentationStereotype = getTextualRepresentationStereotype(comment);
		return textualRepresentationStereotype != null &&
				comment.getAppliedStereotypes().contains(textualRepresentationStereotype) &&
				"Alf".equals(comment.getValue(textualRepresentationStereotype,
						AlfUtilConstants.TEXTUALREPRESENTATION_LANGUAGE_ATTR_NAME));
	}

	/**
	 * Returns whether the action language profile is applied
	 * 
	 * @param element
	 *            - the context
	 * @return true - if it is applied
	 */
	public boolean isActionLanguageProfileApplied(Element context) {
		if (context.getModel() != null) {
			return this.isProfileApplied(context.getModel(), AlfUtilConstants.ACTION_LANGUAGE_PROFILE_NAME);
		}
		return false;
	}

	/**
	 * Returns whether the standard profile is applied
	 * 
	 * @param element
	 *            - the context
	 * @return true - if it is applied
	 */
	public boolean isStandardProfileApplied(Element context) {
		if (context.getModel() != null) {
			return this.isProfileApplied(context.getModel(), AlfUtilConstants.STANDARD_PROFILE_LOADING_NAME);
		}
		return false;
	}

	/**
	 * Build and execute the command that effectively makes the profile applied on the
	 * specified model.
	 * 
	 * @param model
	 *            - the model on which the profile will be applied
	 * @param profile
	 *            - the profile that will be applied
	 * @return true if appplied false otherwise
	 */
	protected boolean applyProfile(Model model, Profile profile) {
		if (model == null || profile == null) {
			return false;
		}
		AbstractTransactionalCommand command = new ApplyProfileCommand(profile, model);
		TransactionalEditingDomain domain = command.getEditingDomain();
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
		return command.getCommandResult().getStatus().isOK();
	}

	/**
	 * Applies the Action Language Profile over the model owning the context element given as parameter
	 * 
	 * @param context
	 *            - the context element
	 * @return true if the profile was applied
	 */
	public boolean applyActionLanguageProfile(Element context) {
		boolean applied = this.isActionLanguageProfileApplied(context);
		if (!applied) {
			applied = this.applyProfile(context.getModel(),
					this.loadProfile(context.getModel(), AlfUtilConstants.ACTION_LANGUAGE_PROFILE_NAME));
		}
		return applied;
	}

	/**
	 * Applies the Standard Profile over the model owning the context element given as parameter
	 * 
	 * @param context
	 *            - the context element
	 * @return true if the profile was applied
	 */
	public boolean applyStandardProfile(Element context) {
		boolean applied = this.isStandardProfileApplied(context);
		if (!applied) {
			applied = this.applyProfile(context.getModel(),
					this.loadProfile(context.getModel(), AlfUtilConstants.STANDARD_PROFILE_NAME));
		}
		return applied;
	}

	/**
	 * Apply the textual representation stereotype (with property value set) on the given comment.
	 * The application only occurs if the action language profile is applied and the stereotype is
	 * not already applied.
	 * 
	 * @param comment
	 *            - the comment on which the stereotype is applied
	 * @return true if applied false otherwise
	 */
	public boolean applyTextualRepresentation(Comment comment) {
		boolean applied = this.isActionLanguageProfileApplied(comment);
		if (!applied) {
			applied = this.applyActionLanguageProfile(comment);
		}
		if (applied) {
			if (!this.isATextualRepresentationComment(comment)) {
				Stereotype textualRepresentation = this.getTextualRepresentationStereotype(comment);
				comment.applyStereotype(textualRepresentation);
				comment.setValue(textualRepresentation,
						AlfUtilConstants.TEXTUALREPRESENTATION_LANGUAGE_ATTR_NAME, "Alf");
			}
		}
		return applied;
	}

	/**
	 * Constants used by AlfUtil
	 */
	class AlfUtilConstants {
		protected static final String STANDARD_PROFILE_NAME = "Standard";
		protected static final String STANDARD_PROFILE_LOADING_NAME = "StandardProfile";
		protected static final String ACTION_LANGUAGE_PROFILE_NAME = "ActionLanguage";
		protected static final String TEXTUAL_REPRESENTATION_STEREOTYPE_NAME = "TextualRepresentation";
		protected static final String TEXTUALREPRESENTATION_LANGUAGE_ATTR_NAME = "language";
	}
}
