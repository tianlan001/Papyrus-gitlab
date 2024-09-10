/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * This class is a structure that is adapted for the code of provider not to describe compartments as the model
 *  it satisfy #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_060
 *
 */
public class ChildrenListRepresentation {

	/**
	 * 
	 */
	private static final String DEBUG_PREFIX = "[EXPANSION_DIAGRAM]";
	public HashMap<String, AbstractRepresentation> IDMap=new HashMap<String, AbstractRepresentation>();
	public HashMap<String, List<String>> parentChildrenRelation= new HashMap<String, List<String>>();

	protected UseContext useContext;
	/**
	 * Constructor.
	 *
	 */
	public ChildrenListRepresentation(UseContext useContext) {
		this.useContext=useContext;
		initStructure();
	}

	protected void createStructure(AbstractRepresentation representation, ArrayList<String> idListToAdd){

		if( representation instanceof Representation) {
			String hint=null;
			if( representation instanceof GMFT_BasedRepresentation){
				hint=((GMFT_BasedRepresentation)representation).getReusedID();

			}else{
				final IElementType elementType;
				if(((Representation)representation).getGraphicalElementTypeRef()!=null) {
					if(((Representation)representation).getGraphicalElementTypeRef() instanceof ElementTypeConfiguration) {
						ElementTypeConfiguration elementTypeConfiguration=(ElementTypeConfiguration)((Representation)representation).getGraphicalElementTypeRef();
						elementType=ElementTypeRegistry.getInstance().getType(elementTypeConfiguration.getIdentifier());
						if( elementType instanceof IHintedType){
							hint=(((IHintedType)elementType).getSemanticHint());
						}
					}
				}
			}
			idListToAdd.add(hint);
			if( IDMap.get(hint)==null){
				IDMap.put(hint, representation);
				ArrayList<String> subRepresentationIDs= new ArrayList<>();
				//compartments
				for (InducedRepresentation compartments : ((Representation)representation).getInducedRepresentations()) {
					createStructure(compartments, subRepresentationIDs);
				}
				//subRepresentation as port...
				for (Representation subRepresentation : ((Representation)representation).getSubRepresentations()) {
					createStructure(subRepresentation, subRepresentationIDs);
				}
				if( subRepresentationIDs.size()>0){
					parentChildrenRelation.put(hint, subRepresentationIDs);
				}
			}
		}
		else if( representation instanceof InducedRepresentation){
			idListToAdd.add(((InducedRepresentation) representation).getHint());
			if( IDMap.get(((InducedRepresentation) representation).getHint())==null){
				IDMap.put(((InducedRepresentation) representation).getHint(), representation);
				ArrayList<String> compartmentChildreen= new ArrayList<String>();
				for (Representation subRepresentation : ((InducedRepresentation)representation).getChildren()) {
					createStructure(subRepresentation, compartmentChildreen);
				}
				if( compartmentChildreen.size()>0){
					parentChildrenRelation.put(((InducedRepresentation) representation).getHint(), compartmentChildreen);
				}
			} 
		}
	}

	protected void initStructure(){

		//init the structure by go across the the model.
		ArrayList<String> childreen= new ArrayList<>();
		for ( Representation currentR : useContext.getRepresentations()) {
			//Check shapes
			createStructure(currentR, childreen);
		}
		parentChildrenRelation.put(useContext.getDiagramType(), childreen);

	}
	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		String out=      DEBUG_PREFIX+"+ChildrenListRepresentation for "+useContext.getDiagramType();
		out=out+    "\n"+DEBUG_PREFIX+"+-->ID - Representation";
		for (String currentID : IDMap.keySet()) {
			out=out+"\n"+DEBUG_PREFIX+"    \""+currentID+ "\" "+IDMap.get(currentID);
		}
		out=out+    "\n"+DEBUG_PREFIX+"+--> ParentID- ChildrenIDs";
		for (String currentID : parentChildrenRelation.keySet()) {
			out=out+"\n"+DEBUG_PREFIX+"    \""+currentID+ "\" "+parentChildrenRelation.get(currentID);
		}
		return out;
	}
}
