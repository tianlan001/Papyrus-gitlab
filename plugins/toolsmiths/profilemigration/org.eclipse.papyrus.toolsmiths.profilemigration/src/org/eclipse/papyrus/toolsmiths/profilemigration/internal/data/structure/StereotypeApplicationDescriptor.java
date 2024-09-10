/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This class is used to store a Stereotype application and keep the link between the
 * object (Stereotype) store in the treeNode and the object store in the profiles model
 */
public class StereotypeApplicationDescriptor {

	/**
	 * The stereotype store in the profiled model
	 */
	private Stereotype stereotype;

	/**
	 * The owner of the stereotype application
	 */
	private Element owner;

	/**
	 * Save the values link to the property before the migration
	 */
	private Map<Property, Object> mapPropertyToApplicationValue;

	/**
	 * Constructor
	 *
	 * @param treeNodeStereotype
	 *            the stereotype store in the treeNode
	 * @param profiledModelStereotype
	 *            the stereotype store in the profiled model
	 * @param owner
	 *            the owner of the stereotype application
	 */
	public StereotypeApplicationDescriptor(Stereotype profiledModelStereotype, Element owner) {
		super();
		this.stereotype = profiledModelStereotype;
		this.owner = owner;
		initMapPropertyToApplicationValue();
	}

	/**
	 * Initialize the map
	 */
	private void initMapPropertyToApplicationValue() {
		mapPropertyToApplicationValue = new HashMap<>();
		for (Property attribute : stereotype.getAllAttributes()) {
			if (attribute.getOwner() instanceof Stereotype) {
				try {
					Object value = owner.getValue(stereotype, attribute.getName());
					mapPropertyToApplicationValue.put(attribute, value);
				} catch (IllegalArgumentException e) {
					// means that the attribute doesn't exist in the stereotype application (of the old version of the profile)
					// ex: a new attribute has been add to a stereotype
				}
			}
		}
	}

	/**
	 * @return the profiledModelStereotype
	 */
	public Stereotype getStereotype() {
		return stereotype;
	}

	/**
	 * @param profiledModelStereotype
	 *            the profiledModelStereotype to set
	 */
	public void setStereotype(Stereotype profiledModelStereotype) {
		this.stereotype = profiledModelStereotype;
	}

	/**
	 * @return the owner
	 */
	public Element getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Element owner) {
		this.owner = owner;
	}

	/**
	 * @return the mapPropertyToApplicationValue
	 */
	public Map<Property, Object> getMapPropertyToApplicationValue() {
		return mapPropertyToApplicationValue;
	}
}
