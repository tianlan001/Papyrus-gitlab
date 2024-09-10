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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.menu;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * this command is used to create a link "satisfyBy" between requirement and namedElement
 *
 */
public abstract class NameNormalizationCommand extends RecordingCommand implements NameNormalization{
	protected EObject source;
	protected String parameter;
	
	private final String BLANK= new String("");//$NON-NLS-1$
	private final String SPACE= new String(" ");//$NON-NLS-1$
	private final String MULTISPACE= new String(" +");//$NON-NLS-1$
	private final String UNDERSCORE= new String("_");//$NON-NLS-1$
	private final String MULTIUNDERSCORE= new String("_+");//$NON-NLS-1$
	
	public static final String NAME_ACTION="name quick formatting action";//$NON-NLS-1$
	
	public static final String DEFAULT_ACTION="default";//$NON-NLS-1$
	public static final String UPPERCASE_ACTION="uppercase";//$NON-NLS-1$
	public static final String LOWERCASE_ACTION="lowercase";//$NON-NLS-1$
	public static final String SWITCHSPACE2UNDERSCORE_ACTION="switchSpace2Underscore";//$NON-NLS-1$
	public static final String CAPITALIZEFIRSTLETTER_ACTION="capitalizeFirstLetter";//$NON-NLS-1$
	public static final String REMOVESPACE_ACTION="removeSpace";//$NON-NLS-1$

	public NameNormalizationCommand(TransactionalEditingDomain domain, EObject source, String normalization){ 
		super(domain,NAME_ACTION+": "+normalization);
		this.source=source;
		this.parameter=normalization;
	}

	public String normalizeName(String name, String parameter){
		String newName = new String(name);
		switch (parameter) {
		case UPPERCASE_ACTION:
			newName = getUpperCaseName(name);
			break;
		case LOWERCASE_ACTION:
			newName = getLowerCaseName(name);
			break;
		case SWITCHSPACE2UNDERSCORE_ACTION:
			newName = switchSpace2UnderscoreName(name);
			break;
		case REMOVESPACE_ACTION:
			newName = removeSpaceName(name);
			break;
		case CAPITALIZEFIRSTLETTER_ACTION:
			newName = capitalizeName(name);
			break;	

		default:
			newName = new String(name);
			break;
		}
		return newName;
	}
	
	private String capitalizeName(String name) {
		String[] ns = name.split(" ");
		StringBuffer finalName=new StringBuffer();
		for(String n : ns){
			finalName.append(n.substring(0, 1).toUpperCase()+n.substring(1,n.length())+" ");
		}
		return finalName.toString().trim();
	}

	private String removeSpaceName(String name) {
		StringBuffer finalName=new StringBuffer();
		int spacePos = name.indexOf(SPACE);
		if(spacePos>0){
			finalName.append(name.replace(SPACE, BLANK));
		}
		else{
			finalName.append(name.replaceAll("(.)([A-Z])", "$1 $2"));//$NON-NLS-1$
		}
		
		return finalName.toString().trim();
	}

	private String getUpperCaseName(String name){
		return name.toUpperCase().trim();
	}
	
	private String getLowerCaseName(String name){
		return name.toLowerCase().trim();
	}
	
	private String switchSpace2UnderscoreName(String name){
		StringBuffer finalName=new StringBuffer();
		int underscorePos = name.indexOf(UNDERSCORE);
		int spacePos = name.indexOf(SPACE);
		if(underscorePos>0 && spacePos>0 && underscorePos<spacePos){
			finalName.append(name.replace(UNDERSCORE, BLANK));
		}
		else if(underscorePos>0 && spacePos>0 && underscorePos>spacePos){
			finalName.append(name.replace(SPACE, UNDERSCORE));
		}
		else if(underscorePos<0 && spacePos>0 ){
			finalName.append(name.replace(SPACE, UNDERSCORE));
		}
		else if(underscorePos>0 && spacePos<0){
			finalName.append(name.replace(UNDERSCORE, SPACE));
		}
		
		return finalName.toString().trim().replaceAll(MULTISPACE, SPACE).replaceAll(MULTIUNDERSCORE, UNDERSCORE);
	}
}