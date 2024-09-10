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

package org.eclipse.papyrus.uml.profile.drafter.ui.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.uml.profile.drafter.Activator;
import org.eclipse.papyrus.uml.profile.drafter.ProfileApplicator;
import org.eclipse.papyrus.uml.profile.drafter.exceptions.DraftProfileException;
import org.eclipse.papyrus.uml.profile.drafter.ui.dialog.IStereotypeUpdateArgs;
import org.eclipse.papyrus.uml.profile.drafter.ui.dialog.StereotypeUpdateDialog;
import org.eclipse.papyrus.uml.profile.drafter.ui.model.StereoptypeModel;
import org.eclipse.papyrus.uml.profile.drafter.utils.UMLMetamodelUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.ExtensionEnd;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;


/**
 * @author cedric dumoulin
 *
 */
public class UpdateProfileHandler extends AbstractProfileBaseHandler {

	private List<NamedElement> cachedSelectionAsNamedElement;
	
	/**
	 * Object used to hold the update values.
	 */
	private IStereotypeUpdateArgs updateArgs;

	/**
	 * Model used to populate the Dialog, and to retrieve values.
	 */
	private StereoptypeModel stereoptypeModel;
	
	
	/**
	 * Constructor.
	 *
	 */
	public UpdateProfileHandler() {
		
	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.handler.AbstractBaseHandler#getCommandName()
	 *
	 * @return
	 */
	@Override
	public String getCommandName() {
		return "Add Profile";
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.handler.AbstractBaseHandler#preExecute(org.eclipse.core.commands.ExecutionEvent, org.eclipse.core.expressions.IEvaluationContext)
	 *
	 * @param event
	 * @param context
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected boolean preExecute(ExecutionEvent event, IEvaluationContext context) throws ExecutionException {

		// Check if an element is selected.
		List<NamedElement> selected = getSelectionAsNamedElements(context);
		
		if( selected.isEmpty()) {
			// Stop if no NamedElement is selected
			return false;
		}

		// Open the dialog to ask the new name
		// TODO dialog should not be in the transaction !! put it outside !

		// ProfileDialog
		StereotypeUpdateDialog newDialog = new StereotypeUpdateDialog(Display.getCurrent().getActiveShell(), "Update Stereotype", 
				selected.get(0), new ArrayList<Class>(  getSelectedElementMetaclasses(context)) );
		if(newDialog.open() == Window.OK) {
			
			// Lookup commands
			stereoptypeModel = newDialog.getStereotypeModel();
				
			// old fashion
			updateArgs = newDialog.getUpdateArgs();
			if(updateArgs == null ) {
				return false;
			}
		} else {
			// cancelled
			return false;
		}

		return true;
	}
	
	/**
	 * Return the list of metaclasses corresponding to selected element.
	 * @return A list of metaclasses, or an empty list if nothing is selected or if an error occurs.
	 */
	private Set<Class> getSelectedElementMetaclasses(IEvaluationContext context) {


		List<NamedElement> selectedElements = getSelectionAsNamedElements(context);
		// If there is nothing to do, skip immediately
		if( selectedElements.isEmpty()) {
			return Collections.emptySet();
		}
		
		
		try {
			Set<Class> result = new HashSet<Class>();
			UMLMetamodelUtils umlMetamodelUtils = getUmlMetamodelUtils(context);
			
			for( NamedElement sel : selectedElements) {
				result.add(umlMetamodelUtils.getElementMetaclass(sel));
			}
			return result;
		} catch (DraftProfileException e) {
			Activator.log.error( e);
			return Collections.emptySet();
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.handler.AbstractBaseHandler#doExecute(org.eclipse.core.commands.ExecutionEvent, org.eclipse.core.expressions.IEvaluationContext, java.util.List)
	 *
	 * @param event
	 * @param context
	 * @param selections
	 */
	@Override
	protected void doExecute(ExecutionEvent event, IEvaluationContext context) {

		List<NamedElement> selected = getSelectionAsNamedElements(context);
		
		// Try to apply the stereotype
		ProfileApplicator profileApplicator = new ProfileApplicator(selected.get(0));
		try {
			profileApplicator.updateStereotype(stereoptypeModel);
		} catch (DraftProfileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.handler.AbstractBaseHandler#resetCachedValues()
	 *
	 */
	@Override
	protected void resetCachedValues() {
		super.resetCachedValues();
		cachedSelectionAsNamedElement = null;
	}
	
	/**
	 * Get the list of selected element of type 'NamedElement'.
	 * 
	 * @param context
	 * @return
	 */
	protected List<NamedElement> getSelectionAsNamedElements(IEvaluationContext context) {
	
		if( cachedSelectionAsNamedElement == null) {

			cachedSelectionAsNamedElement = getSelectionsByType(context, NamedElement.class);
		}
		
		return cachedSelectionAsNamedElement;
	}

	protected Extension createExtension( Stereotype source,  Type target) {
		//create the extension
		Extension newExtension = UMLFactory.eINSTANCE.createExtension();

		//create the endSource
		ExtensionEnd endSource = UMLFactory.eINSTANCE.createExtensionEnd();

			//initialize the endSource
			endSource.setName("extension_" + source.getName()); //$NON-NLS-1$ //$NON-NLS-2$
			endSource.setType(source);
			endSource.setAggregation(AggregationKind.COMPOSITE_LITERAL);

			//add the endSource to the extension
			newExtension.getOwnedEnds().add(endSource); // add extension end to extension


			//create source_property
			Property property = UMLFactory.eINSTANCE.createProperty();
			property.setName("base_" + target.getName());
			//	property.setIsDerived(true);
			property.setType(target); // set the type
			property.setAssociation(newExtension); // Set the association link
			property.setAggregation(AggregationKind.NONE_LITERAL);

			newExtension.getMemberEnds().add(property);

			source.getOwnedAttributes().add(property);

			return newExtension;
	}
	/**
	 * 
	 * @see org.eclipse.papyrus.uml.profile.drafter.ui.handler.AbstractBaseHandler#isEnabled(org.eclipse.core.expressions.IEvaluationContext, java.util.List)
	 *
	 * @param context
	 * @param selections
	 * @return
	 */
	@Override
	public boolean isEnabled(IEvaluationContext context, List<Object> selections) {
		
		if( ! selections.isEmpty()  ) {
			return true;
		}
		
		 return false;
	}
}
