/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 * 	Jérémie Tatibouet (CEA LIST)
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.libraries.helper;

import java.sql.Timestamp;
import java.util.Iterator;

import org.eclipse.papyrus.uml.alf.libraries.helper.BackupState.EditionStatus;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * Singleton class providing a set of methods to manipulate the backup profile
 */
public class BackupUtil {

	/**
	 * A reference to the unique instance of that class
	 */
	private static BackupUtil singleton;

	private BackupUtil() {
	}

	/**
	 * Returns an instance of the singleton
	 * 
	 * @return singleton - the instance of the class
	 */
	public static BackupUtil getInstance() {
		if (singleton == null) {
			singleton = new BackupUtil();
		}
		return singleton;
	}

	/**
	 * Get the reference to the backup profile that is applied on the given model
	 * 
	 * @param context
	 *            - the model on which the profile is applied
	 * @return a reference to the applied profile
	 */
	public Profile getBackupProfile(Model context) {
		return AlfUtil.getInstance().getAppliedProfile(context, BackupUtilConstants.BACKUP_PROFILE_NAME);
	}

	/**
	 * Retrieve the Backup stereotype definition
	 * 
	 * @param element
	 *            - the context element
	 * @return backupStereotype - the definition of the stereotype
	 */
	public Stereotype getBackupStereotype(Element context) {
		if (context != null) {
			Profile backupProfile = this.getBackupProfile(context.getModel());
			return backupProfile.getOwnedStereotype(BackupUtilConstants.BACKUP_STEREOTYPE_NAME);
		}
		return null;
	}

	/**
	 * Retrieve the definition of the Enumerated type BackupState
	 * 
	 * @param context
	 *            - a model that has the backup profile applied
	 * @return a reference to the ennumeration specifying the backup state
	 */
	public Enumeration getBackupStateDefinition(Element context) {
		if (context != null) {
			Profile backupProfile = this.getBackupProfile(context.getModel());
			if (backupProfile != null) {
				Type backupStateDefinition = backupProfile.getOwnedType(BackupUtilConstants.BACKUP_BACKUP_STATE_ENUMERATION_NAME);
				if (backupStateDefinition != null &&
						backupStateDefinition instanceof Enumeration) {
					return (Enumeration) backupStateDefinition;
				}
			}
		}
		return null;
	}

	/**
	 * if the given element has a comment that has the backup
	 * stereotype applied then return that comment
	 * 
	 * @param element
	 *            - the element from which the search is initiated
	 * @return a comment or null
	 */
	public Comment getBackupComment(Element element) {
		Comment backupComment = null;
		if (element != null) {
			Iterator<Comment> iteratorComments = element.getOwnedComments().iterator();
			while (iteratorComments.hasNext() && backupComment == null) {
				Comment current = iteratorComments.next();
				if (current.getAppliedStereotypes().contains(this.getBackupStereotype(element))) {
					backupComment = current;
				}
			}
		}
		return backupComment;
	}

	/**
	 * Returns whether the element has a comment with the Backup stereotype
	 * 
	 * @param element
	 *            - the assessed element
	 * @return true if the stereotype is applied
	 */
	public boolean hasBackupComment(Element element) {
		return this.getBackupComment(element) != null;
	}

	/**
	 * Determines if the given comment has the backup stereotype applied
	 * 
	 * @param comment
	 * 			- the comment which is assessed
	 * @return true if the stereotype is applie false otherwise
	 */
	public boolean isBackup(Comment comment){
		if(comment!=null){
			Stereotype backupStereotype = this.getBackupStereotype(comment);
			if(backupStereotype!=null){
				return comment.getAppliedStereotypes().contains(backupStereotype);
			}
		}
		return false;
	}
	
	/**
	 * Returns the BackupState applied to this comment
	 * 
	 * @param comment
	 *            - the comment containing the backup information
	 * @return state - the backup information
	 */
	public BackupState getBackupState(Comment comment) {
		BackupState state = null;
		if (comment != null) {
			/* 1. Extract time information */
			String timestamp = (String) comment.getValue(this.getBackupStereotype(comment),
					BackupUtilConstants.BACKUP_TIMESTAMP_ATTR_NAME);
			/* 2. Extract textual representation state information */
			EnumerationLiteral literal = (EnumerationLiteral) comment.getValue(this.getBackupStereotype(comment),
					BackupUtilConstants.BACKUP_STATE_ATTR_NAME);
			/* 3. Build state */
			state = new BackupState();
			try {
				state.timestamp = Timestamp.valueOf(timestamp);
			} catch (Exception e) {
			}
			state.status = this.map(literal);
		}
		return state;
	}
	
	/**
	 * 
	 * @param literal
	 * @return
	 */
	private EditionStatus map(EnumerationLiteral literal){
		EditionStatus status = null;
		if(literal.getLabel().equals("NONE")){
			status = EditionStatus.NONE;
		}else if(literal.getLabel().equals("SAVED")){
			status = EditionStatus.SAVED;
		}else if(literal.getLabel().equals("MERGED")){
			status = EditionStatus.MERGED;
		}
		return status;
	}
	
	/**
	 * 
	 * @param editionStatus
	 * @param context
	 * @return
	 */
	private EnumerationLiteral map(EditionStatus editionStatus, Element context){
		EnumerationLiteral status = null;
		if(context!=null){
			Enumeration backupState = this.getBackupStateDefinition(context);
			if(backupState!=null){
				if(editionStatus.equals(EditionStatus.NONE)){
					status = backupState.getOwnedLiteral("NONE");
				}else if(editionStatus.equals(EditionStatus.SAVED)){
					status = backupState.getOwnedLiteral("SAVED");
				}else if(editionStatus.equals(EditionStatus.MERGED)){
					status = backupState.getOwnedLiteral("MERGED");
				}
			}
		}
		return status;
	}

	/**
	 * Applies the Backup stereotype over a comment and sets the stereotype attributes. It implies
	 * the application of the backup stereotype over the context model if is not already done.
	 * 
	 * @param comment
	 *            - the comment on which the stereotype is applied
	 * @param timestamp
	 *            - the value for the time stamp attribute
	 * @param state
	 *            - the value for the state attribute
	 * @return true if the applications was realized
	 */
	public boolean applyBackup(Comment comment, final BackupState state) {
		boolean applied = this.isBackupProfileApplied(comment);
		if (!applied) {
			applied = this.applyBackupProfile(comment);
		}
		if (applied) { 
			Stereotype backupStereotype = this.getBackupStereotype(comment);
			/* 1. Apply the stereotype */
			if(!this.isBackup(comment)){
				comment.applyStereotype(backupStereotype);
			}
			/* 2. Set time stamp attribute */
			comment.setValue(backupStereotype, BackupUtilConstants.BACKUP_TIMESTAMP_ATTR_NAME, state.timestamp.toString());
			/* 3. Set state attribute */
			comment.setValue(backupStereotype, BackupUtilConstants.BACKUP_STATE_ATTR_NAME, this.map(state.status, comment));
		}
		return applied;
	}



	/**
	 * Applies the backup profile over the model owning the context element provided as a parameter
	 * 
	 * @param context
	 *            - the context element
	 * @return true if the profile was applied
	 */
	public boolean applyBackupProfile(Element context) {
		boolean applied = this.isBackupProfileApplied(context);
		if(!applied){
			 applied = AlfUtil.getInstance().applyProfile(context.getModel(),
					AlfUtil.getInstance().loadProfile(context.getModel(), 
							BackupUtilConstants.BACKUP_PROFILE_LOADING_NAME));
		}
		return applied;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean isBackupProfileApplied(Element context){
		return this.getBackupProfile(context.getModel()) != null;
	}

	public class BackupUtilConstants {
		public static final String BACKUP_PROFILE_NAME = "BackupProfile";
		public static final String BACKUP_PROFILE_LOADING_NAME = "TextualRepresentationBackup";
		public static final String BACKUP_STEREOTYPE_NAME = "Backup";
		public static final String BACKUP_BACKUP_STATE_ENUMERATION_NAME = "BackupState";
		public static final String BACKUP_TIMESTAMP_ATTR_NAME = "timestamp";
		public static final String BACKUP_STATE_ATTR_NAME = "state";
	}
}
