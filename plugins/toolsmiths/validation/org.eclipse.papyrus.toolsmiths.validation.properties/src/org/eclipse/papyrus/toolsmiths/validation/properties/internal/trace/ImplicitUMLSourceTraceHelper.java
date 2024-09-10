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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace;

import static java.util.function.Predicate.not;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.generators.ProfileGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsFactory;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.LocalProfileIndex;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Source trace helper that infers source traces from known constraint types that
 * reference the source UML model or profile.
 */
class ImplicitUMLSourceTraceHelper extends SwitchingSourceTraceHelper {

	private static final String UML_INSTANCE_OF = "org.eclipse.papyrus.uml.properties.constraints.UmlInstanceOfConstraint"; //$NON-NLS-1$
	private static final String HAS_STEREOTYPE = "org.eclipse.papyrus.uml.properties.constraints.HasStereotypeConstraint"; //$NON-NLS-1$

	private static final String PROPERTY_STEREOTYPE_NAME = "stereotypeName"; //$NON-NLS-1$
	private static final String PROPERTY_CLASS_NAME = "umlClassName"; //$NON-NLS-1$

	private static final URI UML_METAMODEL_URI = URI.createURI(UMLResource.UML_METAMODEL_URI);

	private final Set<String> constraintTypes = Set.of(UML_INSTANCE_OF, HAS_STEREOTYPE);

	ImplicitUMLSourceTraceHelper() {
		super();
	}

	@Override
	public List<? extends EObject> getNestedPackages(EObject sourcePackage) {
		return sourcePackage instanceof org.eclipse.uml2.uml.Package ? ((org.eclipse.uml2.uml.Package) sourcePackage).getNestedPackages() : null;
	}

	@Override
	public List<? extends EObject> getClasses(EObject sourcePackage) {
		return sourcePackage instanceof org.eclipse.uml2.uml.Package ? getOwnedClasses((org.eclipse.uml2.uml.Package) sourcePackage) : null;
	}

	private List<Classifier> getOwnedClasses(org.eclipse.uml2.uml.Package package_) {
		return package_.getOwnedTypes().stream()
				.filter(this::isClasslike).map(Classifier.class::cast)
				.collect(Collectors.toList());
	}

	/**
	 * Query whether a {@code type} in an UML model or profile is a class-like concept that presents properties editable
	 * in the properties view. Actual UML {@linkplain org.eclipse.uml2.uml.Class classes} and complex {@linkplain DataType data types}
	 * are class-like.
	 *
	 * @param type
	 *            an UML type
	 * @return whether it is class-like for properties editing purposes
	 */
	private boolean isClasslike(Type type) {
		return type instanceof org.eclipse.uml2.uml.Class || isComplexDataType(type);
	}

	private boolean isComplexDataType(Type type) {
		return type instanceof DataType &&
				!(type instanceof PrimitiveType) &&
				!(type instanceof Enumeration);
	}

	@Override
	protected EObject getOwningPackage(EObject sourceElement) {
		return sourceElement instanceof org.eclipse.uml2.uml.Package
				? ((org.eclipse.uml2.uml.Package) sourceElement).getNestingPackage()
				: sourceElement instanceof Element
						? ((Element) sourceElement).getNearestPackage()
						: null;
	}

	@Override
	protected EObject getClass(EObject sourcePackage, DataContextElement contextElement) {
		return sourcePackage instanceof org.eclipse.uml2.uml.Package
				? ((org.eclipse.uml2.uml.Package) sourcePackage).getOwnedType(contextElement.getName(), false, UMLPackage.Literals.CLASS, false)
				: null;
	}

	@Override
	public List<? extends EObject> getProperties(EObject sourceClass) {
		return sourceClass instanceof AttributeOwner
				? ((AttributeOwner) sourceClass).getOwnedAttributes().stream()
						// But not the base element references! Those do not generate properties
						.filter(not(this::isExtensionEnd))
						.collect(Collectors.toList())
				: null;
	}

	@Override
	public boolean isPropertyRedefinition(EObject sourceProperty) {
		return sourceProperty instanceof Property && !((Property) sourceProperty).getRedefinedProperties().isEmpty();
	}

	private boolean isExtensionEnd(org.eclipse.uml2.uml.Property property) {
		return UMLPackage.Literals.EXTENSION.isInstance(property.getAssociation());
	}

	@Override
	public List<? extends EObject> getSuperclasses(EObject sourceClass) {
		return sourceClass instanceof Classifier
				? ((Classifier) sourceClass).getGenerals().stream().filter(this::isClasslike).collect(Collectors.toList())
				: null;
	}

	@Override
	public ConstraintDescriptor createInstanceOfConstraint(EObject sourceClass) {
		SimpleConstraint result = null;
		String constraintClassName = null;

		if (sourceClass instanceof Stereotype) {
			constraintClassName = HAS_STEREOTYPE;
		} else if (sourceClass instanceof org.eclipse.uml2.uml.Class) {
			constraintClassName = UML_INSTANCE_OF;
		} else if (sourceClass instanceof DataType) {
			// FIXME: This is what the QVTo transformation generates, but it is not correct. This should
			// be fixed when the generator is fixed
			constraintClassName = HAS_STEREOTYPE;
		}

		if (constraintClassName != null) {
			result = getConstraintType(constraintClassName).map(type -> {
				SimpleConstraint constraint = ConstraintsFactory.eINSTANCE.createSimpleConstraint();
				constraint.setConstraintType(type);
				setClassifier(constraint, (Classifier) sourceClass);
				return constraint;
			}).orElse(null);
		}

		return result;
	}

	@Override
	public boolean isInstanceOfConstraint(ConstraintDescriptor constraint) {
		return getConstraintType(constraint).map(ConstraintType::getConstraintClass).filter(constraintTypes::contains).isPresent();
	}

	@Override
	public EObject resolveInstanceOfConstraintClass(ConstraintDescriptor constraint) {
		EObject result = null;

		if (isInstanceOfConstraint(constraint)) {
			result = asSimpleConstraint(constraint).map(simple -> {
				switch (simple.getConstraintType().getConstraintClass()) {
				case UML_INSTANCE_OF:
					return UMLPackage.eINSTANCE.getEClassifier(getValue(simple, PROPERTY_CLASS_NAME));
				case HAS_STEREOTYPE:
					String stereotypeName = getValue(simple, PROPERTY_STEREOTYPE_NAME);
					return LocalProfileIndex.getInstance(constraint)
							.filter(__ -> stereotypeName != null)
							.map(index -> index.getStereotype(stereotypeName, simple))
							.orElse(null);
				default:
					return null;
				}
			}).orElse(null);
		}

		return result;
	}

	@Override
	public String getClassName(ConstraintDescriptor instanceOfConstraint) {
		return asSimpleConstraint(instanceOfConstraint).map(simple -> {
			switch (simple.getConstraintType().getConstraintClass()) {
			case UML_INSTANCE_OF:
				return getValue(simple, PROPERTY_CLASS_NAME);
			case HAS_STEREOTYPE:
				return getValue(simple, PROPERTY_STEREOTYPE_NAME);
			default:
				return null;
			}
		}).orElse(null);
	}

	@Override
	public ValueProperty getClassNameProperty(ConstraintDescriptor instanceOfConstraint) {
		return asSimpleConstraint(instanceOfConstraint).map(simple -> {
			switch (simple.getConstraintType().getConstraintClass()) {
			case UML_INSTANCE_OF:
				return getProperty(simple, PROPERTY_CLASS_NAME);
			case HAS_STEREOTYPE:
				return getProperty(simple, PROPERTY_STEREOTYPE_NAME);
			default:
				return null;
			}
		}).orElse(null);
	}

	@Override
	public Collection<? extends EObject> getValidConstraintSourceClasses(ConstraintDescriptor instanceOfConstraint, EObject sourceClass) {
		return asSimpleConstraint(instanceOfConstraint).map(simple -> {
			switch (simple.getConstraintType().getConstraintClass()) {
			case UML_INSTANCE_OF:
				return sourceClass instanceof Stereotype ? ((Stereotype) sourceClass).getAllExtendedMetaclasses() : List.of(sourceClass);
			default:
				return List.of(sourceClass);
			}
		}).orElse(List.of(sourceClass));
	}

	@Override
	public IGenerator createGenerator(EObject sourceClass) {
		IGenerator result = null;

		Profile profile = null;

		if (sourceClass instanceof Classifier) {
			Classifier classifier = (Classifier) sourceClass;
			if (classifier.getPackage() instanceof Profile) {
				profile = (Profile) classifier.getPackage();
			}
		}

		if (profile != null) {
			Profile _profile = profile;
			result = new ProfileGenerator() {
				{
					setProfile(_profile);
				}
			};
		}

		return result;
	}

	@Override
	protected EObject getProperty(EObject class_, org.eclipse.papyrus.infra.properties.contexts.Property contextProperty) {
		return class_ instanceof AttributeOwner ? ((AttributeOwner) class_).getOwnedAttribute(contextProperty.getName(), null) : null;
	}

	@Override
	public String getName(EObject sourceElement, NameKind kind) {
		String result = null;

		if (sourceElement instanceof NamedElement) {
			NamedElement namedElement = (NamedElement) sourceElement;
			switch (kind) {
			case QUALIFIED:
				result = UML2Util.getQualifiedText(namedElement, UMLUtil.QualifiedTextProvider.DEFAULT);
				break;
			case CONSTRAINT:
				if (namedElement instanceof Stereotype) {
					// need the qualified name
					result = UML2Util.getQualifiedText(namedElement, UMLUtil.QualifiedTextProvider.DEFAULT);
				} else {
					// UML Metaclasses need the simple name
					result = namedElement.getName();
				}
				break;
			default:
				result = namedElement.getName();
				break;
			}
		}

		return result;
	}

	@Override
	public int getMultiplicity(EObject sourceProperty) {
		return (sourceProperty instanceof MultiplicityElement) ? ((MultiplicityElement) sourceProperty).upperBound() : DEFAULT_MULTIPLICITY;
	}

	@Override
	protected Switch<EObject> createConstraintsSwitch() {
		return new ConstraintsDelegate() {
			@Override
			protected EObject constraintSwitch(SimpleConstraint constraint, String constraintClass) {
				EObject result = null;

				switch (constraintClass) {
				case HAS_STEREOTYPE:
					result = getStereotype(constraint);
					break;
				case UML_INSTANCE_OF:
					result = getUMLClass(constraint);
					break;
				}

				return result;
			}
		};
	}

	private EObject getStereotype(SimpleConstraint constraint) {
		EObject result = null;
		String stereotypeName = getValue(constraint, PROPERTY_STEREOTYPE_NAME);

		if (stereotypeName != null) {
			result = LocalProfileIndex.getInstance(constraint)
					.map(index -> index.getStereotype(stereotypeName, constraint))
					.orElse(null);
		}

		return result;
	}

	private EObject getUMLClass(SimpleConstraint constraint) {
		EObject result = null;
		String className = getValue(constraint, PROPERTY_CLASS_NAME);

		if (className != null) {
			org.eclipse.uml2.uml.Package uml = UML2Util.load(EMFHelper.getResourceSet(constraint), UML_METAMODEL_URI, UMLPackage.Literals.PACKAGE);
			result = (uml != null) ? uml.getOwnedType(className) : null;
		}

		return result;
	}

	private void setClassifier(SimpleConstraint constraint, Classifier classifier) {
		switch (constraint.getConstraintType().getConstraintClass()) {
		case HAS_STEREOTYPE:
			setValue(constraint, PROPERTY_STEREOTYPE_NAME, classifier.getQualifiedName());
			break;
		default:
			setValue(constraint, PROPERTY_CLASS_NAME, classifier.getName());
			break;
		}
	}

}
