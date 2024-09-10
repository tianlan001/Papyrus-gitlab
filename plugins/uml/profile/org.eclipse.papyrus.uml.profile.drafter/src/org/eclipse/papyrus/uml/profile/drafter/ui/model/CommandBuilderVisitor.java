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

package org.eclipse.papyrus.uml.profile.drafter.ui.model;

import org.eclipse.papyrus.uml.profile.drafter.commands.CompoundCommand;
import org.eclipse.papyrus.uml.profile.drafter.commands.ValueProxy;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;


/**
 * @author dumoulin
 *
 */
public class CommandBuilderVisitor extends SimpleModelVisitor {

	/**
	 * The resulting command build by this visitor.
	 */
	protected CompoundCommand compoundCommand;
	
	/**
	 * The shared value containing the current stereotype
	 */
	protected ValueProxy<Stereotype> curStereotype;
	
	/**
	 * The shared value associated to the latest encountered {@link PropertyModel}.
	 * 
	 */
	protected ValueProxy<Property> curProperty;
	
	/**
	 * The shared value associated to the latest encountered Property Value.
	 * TODO : set the right type !!
	 * 
	 */
	protected ValueProxy<Property> curPropertyValue;

	/**
	 * Flag indicating if the commands should be executed. 
	 */
	private boolean isExecutionRequested = false;
	
	/**
	 * Constructor.
	 *
	 */
	public CommandBuilderVisitor() {
		compoundCommand = new CompoundCommand();
	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.model.IModelVisitor#visit(org.eclipse.papyrus.uml.profile.drafter.ui.model.StereoptypeModel)
	 *
	 * @param model
	 */
	@Override
	public void doVisit(StereoptypeModel model) {
		

	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.model.IModelVisitor#visit(org.eclipse.papyrus.uml.profile.drafter.ui.model.PropertyModel)
	 *
	 * @param model
	 */
	@Override
	public void doVisit(PropertyModel model) {
		

	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.model.IModelVisitor#visit(org.eclipse.papyrus.uml.profile.drafter.ui.model.MetaclassModel)
	 *
	 * @param model
	 */
	@Override
	public void doVisit(MetaclassModel model) {
		

	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.model.IModelVisitor#visit(org.eclipse.papyrus.uml.profile.drafter.ui.model.ExtendedStereotypeModel)
	 *
	 * @param model
	 */
	@Override
	public void doVisit(ExtendedStereotypeModel model) {
		

	}

	/**
	 * Return true if the command should be executed (ie if there is some modifications requested).
	 * 
	 * @return true if the command should be executed. False, if there is no modification requested.
	 * 
	 */
	public boolean isExecutionRequested() {
		
		return isExecutionRequested;
	}

}
