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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DurationObservationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InformationItemEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InformationItemEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest;

/**
 * The Class RoundedCompartmentTest use to test if edit part which need it, extends RoundCompartmentEditPart and RoundBorderNameElementEditPart.
 */
public class RoundedCompartmentTest extends AbstractGenericShapeTest {

	/**
	 * Gets the rounded compartment edit parts.
	 *
	 * @return the rounded compartment edit parts
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest#getRoundedCompartmentEditParts()
	 */
	public List<Class<?>> getRoundedCompartmentEditParts() {

		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(AssociationNodeEditPart.class);
		classes.add(AssociationClassEditPart.class);
		classes.add(ClassEditPart.class);
		classes.add(ClassEditPartCN.class);
		classes.add(ComponentEditPart.class);
		classes.add(ComponentEditPartCN.class);
		classes.add(DataTypeEditPart.class);
		classes.add(DataTypeEditPartCN.class);
		classes.add(EnumerationEditPart.class);
		classes.add(EnumerationEditPartCN.class);
		classes.add(InformationItemEditPart.class);
		classes.add(InformationItemEditPartCN.class);
		classes.add(InterfaceEditPart.class);
		classes.add(InterfaceEditPartCN.class);
		classes.add(PrimitiveTypeEditPart.class);
		classes.add(PrimitiveTypeEditPartCN.class);
		classes.add(SignalEditPart.class);
		classes.add(SignalEditPartCN.class);
		classes.add(DurationObservationEditPart.class);
		classes.add(InstanceSpecificationEditPart.class);
		classes.add(InstanceSpecificationEditPartCN.class);
		classes.add(ModelEditPartCN.class);
		classes.add(ModelEditPartTN.class);
		classes.add(PackageEditPart.class);
		classes.add(PackageEditPartCN.class);
		classes.add(TimeObservationEditPart.class);

		return classes;
	}

	/**
	 * Gets the rounded border named element edit parts.
	 *
	 * @return the rounded border named element edit parts
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest#getRoundedBorderNamedElementEditParts()
	 */
	public List<Class<?>> getRoundedBorderNamedElementEditParts() {
		return new ArrayList<Class<?>>();

	}
}
