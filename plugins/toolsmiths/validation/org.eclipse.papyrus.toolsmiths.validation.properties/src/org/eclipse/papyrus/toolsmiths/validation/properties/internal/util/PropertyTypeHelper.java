/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.util;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.environment.Type;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.collect.ImmutableMap;

/**
 * Support for analysis of {@linkplain Property#getType() property types} in <em>Properties Context</em>
 * models sourced in various metamodels.
 */
public class PropertyTypeHelper {

	private static final PropertyTypeHelper INSTANCE = new PropertyTypeHelper();

	private final Switch<EObject> sourceTypeSwitch = new SourceTypeSwitch();
	private final Switch<Type> propertyTypeSwitch = new PropertyTypeSwitch();
	private final Switch<Integer> sourceMultiplicitySwitch = new SourceMultiplicitySwitch();

	/**
	 * Not instantiable by clients.
	 */
	private PropertyTypeHelper() {
		super();
	}

	public static final PropertyTypeHelper getInstance(Property property) {
		return INSTANCE;
	}

	public Type getPropertyType(EObject sourceProperty) {
		Type result = Type.STRING; // As in the generator, unknown types are edited as strings

		EObject sourceType = sourceTypeSwitch.doSwitch(sourceProperty);
		if (sourceType != null) {
			result = propertyTypeSwitch.doSwitch(sourceType);
		}

		return result;
	}

	public int getMultiplicity(EObject sourceProperty) {
		int result = 1; // The default upper bound in both Ecore and UML

		Integer multiplicity = sourceMultiplicitySwitch.doSwitch(sourceProperty);
		if (multiplicity != null) {
			result = multiplicity.intValue();
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * A switch that computes the type (in the source model) of a source property.
	 */
	private static final class SourceTypeSwitch extends ComposedSwitch<EObject> {
		SourceTypeSwitch() {
			super();

			addSwitch(createEcoreSwitch());
			addSwitch(createUMLSwitch());
		}

		private Switch<EObject> createEcoreSwitch() {
			return new EcoreSwitch<>() {
				public EObject caseETypedElement(ETypedElement object) {
					return object.getEType();
				}
			};
		}

		private Switch<EObject> createUMLSwitch() {
			return new UMLSwitch<>() {
				public EObject caseTypedElement(TypedElement object) {
					return object.getType();
				}

				public EObject caseOperation(Operation object) {
					return object.getType();
				}
			};
		}

	}

	private static final class PropertyTypeSwitch extends ComposedSwitch<Type> {
		PropertyTypeSwitch() {
			super();

			addSwitch(createEcoreSwitch());
			addSwitch(createUMLSwitch());
		}

		private Switch<Type> createEcoreSwitch() {
			return new EcoreSwitch<>() {
				private final Map<java.lang.Class<?>, Type> primitiveTypes = ImmutableMap.<java.lang.Class<?>, Type> builder()
						.put(EcorePackage.Literals.ESTRING.getInstanceClass(), Type.STRING)
						.put(EcorePackage.Literals.EINT.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.EINTEGER_OBJECT.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.EBIG_INTEGER.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.EBOOLEAN.getInstanceClass(), Type.BOOLEAN)
						.put(EcorePackage.Literals.EFLOAT.getInstanceClass(), Type.DOUBLE)
						.put(EcorePackage.Literals.EFLOAT_OBJECT.getInstanceClass(), Type.DOUBLE)
						.put(EcorePackage.Literals.EDOUBLE.getInstanceClass(), Type.DOUBLE)
						.put(EcorePackage.Literals.EDOUBLE_OBJECT.getInstanceClass(), Type.DOUBLE)
						.put(EcorePackage.Literals.EBIG_DECIMAL.getInstanceClass(), Type.DOUBLE)
						.put(EcorePackage.Literals.ECHAR.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.ECHARACTER_OBJECT.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.EBYTE.getInstanceClass(), Type.INTEGER)
						.put(EcorePackage.Literals.EBYTE_OBJECT.getInstanceClass(), Type.INTEGER)
						.build();

				@Override
				public Type caseEClass(EClass object) {
					return Type.REFERENCE;
				}

				@Override
				public Type caseEEnum(EEnum object) {
					return Type.ENUMERATION;
				}

				@Override
				public Type caseEDataType(EDataType object) {
					return primitiveTypes.get(object.getInstanceClass());
				}

				@Override
				public Type defaultCase(EObject object) {
					return Type.STRING; // as in the generator
				}

			};
		}

		private Switch<Type> createUMLSwitch() {
			return new UMLSwitch<>() {
				private final Map<String, Type> primitiveTypes = Map.of(
						TypesPackage.Literals.STRING.getName(), Type.STRING,
						TypesPackage.Literals.INTEGER.getName(), Type.INTEGER,
						TypesPackage.Literals.BOOLEAN.getName(), Type.BOOLEAN,
						TypesPackage.Literals.REAL.getName(), Type.DOUBLE,
						TypesPackage.Literals.UNLIMITED_NATURAL.getName(), Type.INTEGER,
						"Double", Type.DOUBLE); //$NON-NLS-1$ // as in the generator

				@Override
				public Type caseClass(Class object) {
					return Type.REFERENCE;
				}

				@Override
				public Type caseEnumeration(Enumeration object) {
					return Type.ENUMERATION;
				}

				@Override
				public Type casePrimitiveType(PrimitiveType object) {
					return primitiveTypes.get(object.getName());
				}

				@Override
				public Type caseDataType(DataType object) {
					return Type.REFERENCE;
				}

				@Override
				public Type defaultCase(EObject object) {
					return Type.STRING; // as in the generator
				}

			};
		}
	}

	/**
	 * A switch that computes the multiplicity (in the source model) of a source property.
	 */
	private static final class SourceMultiplicitySwitch extends ComposedSwitch<Integer> {
		SourceMultiplicitySwitch() {
			super();

			addSwitch(createEcoreSwitch());
			addSwitch(createUMLSwitch());
		}

		private Switch<Integer> createEcoreSwitch() {
			return new EcoreSwitch<>() {
				public Integer caseETypedElement(ETypedElement object) {
					return object.getUpperBound();
				}
			};
		}

		private Switch<Integer> createUMLSwitch() {
			return new UMLSwitch<>() {
				public Integer caseMultiplicityElement(MultiplicityElement object) {
					return object.getUpper();
				}

				public Integer caseOperation(Operation object) {
					return object.getUpper();
				}
			};
		}

	}

}
