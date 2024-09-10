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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.customization.properties.generation.generators.EcoreGenerator;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsFactory;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.uml2.common.util.UML2Util;

import com.google.common.collect.Lists;

/**
 * Source trace helper that infers source traces from known constraint types that
 * reference the source Ecore model.
 */
class ImplicitEcoreSourceTraceHelper extends SwitchingSourceTraceHelper {

	private static final String EMF_INSTANCE_OF = "org.eclipse.papyrus.infra.constraints.constraints.EMFInstanceOfConstraint"; //$NON-NLS-1$

	private static final String PROPERTY_NS_URI = "nsUri"; //$NON-NLS-1$
	private static final String PROPERTY_CLASS_NAME = "className"; //$NON-NLS-1$

	// As declared in UMLUtil class
	private static final String ANNOTATION__REDEFINES = "redefines"; //$NON-NLS-1$

	ImplicitEcoreSourceTraceHelper() {
		super();
	}

	@Override
	public List<? extends EObject> getNestedPackages(EObject sourcePackage) {
		return sourcePackage instanceof EPackage ? ((EPackage) sourcePackage).getESubpackages() : null;
	}

	@Override
	public List<? extends EObject> getClasses(EObject sourcePackage) {
		return sourcePackage instanceof EPackage ? getEClasses((EPackage) sourcePackage) : null;
	}

	private List<EClass> getEClasses(EPackage ePackage) {
		return ePackage.getEClassifiers().stream()
				.filter(EClass.class::isInstance).map(EClass.class::cast)
				.collect(Collectors.toList());
	}

	@Override
	protected EObject getOwningPackage(EObject sourceElement) {
		return sourceElement instanceof EClassifier
				? ((EClassifier) sourceElement).getEPackage()
				: sourceElement instanceof EPackage
						? ((EPackage) sourceElement).getESuperPackage()
						: null;
	}

	@Override
	protected EObject getClass(EObject sourcePackage, DataContextElement contextElement) {
		EClassifier result = sourcePackage instanceof EPackage ? ((EPackage) sourcePackage).getEClassifier(contextElement.getName()) : null;
		return result instanceof EClass ? result : null;
	}

	@Override
	public List<? extends EObject> getProperties(EObject sourceClass) {
		return sourceClass instanceof EClass ? ((EClass) sourceClass).getEStructuralFeatures() : null;
	}

	@Override
	public boolean isPropertyRedefinition(EObject sourceProperty) {
		EAnnotation redefinesAnnotation = sourceProperty instanceof EStructuralFeature
				? ((EStructuralFeature) sourceProperty).getEAnnotation(ANNOTATION__REDEFINES)
				: null;
		EList<?> redefinitions = redefinesAnnotation == null ? ECollections.emptyEList() : redefinesAnnotation.getReferences();

		return !redefinitions.isEmpty() && redefinitions.get(0) instanceof EStructuralFeature;
	}

	@Override
	public List<? extends EObject> getSuperclasses(EObject sourceClass) {
		return sourceClass instanceof EClass ? ((EClass) sourceClass).getESuperTypes() : null;
	}

	@Override
	public ConstraintDescriptor createInstanceOfConstraint(EObject sourceClass) {
		SimpleConstraint result = null;

		if (sourceClass instanceof EClass) {
			result = getConstraintType(EMF_INSTANCE_OF).map(type -> {
				SimpleConstraint constraint = ConstraintsFactory.eINSTANCE.createSimpleConstraint();
				constraint.setConstraintType(type);
				setEClassifier(constraint, (EClass) sourceClass);
				return constraint;
			}).orElse(null);
		}

		return result;
	}

	@Override
	public boolean isInstanceOfConstraint(ConstraintDescriptor constraint) {
		return getConstraintType(constraint).map(ConstraintType::getConstraintClass).filter(EMF_INSTANCE_OF::equals).isPresent();
	}

	@Override
	public EObject resolveInstanceOfConstraintClass(ConstraintDescriptor constraint) {
		EObject result = null;

		if (isInstanceOfConstraint(constraint)) {
			result = asSimpleConstraint(constraint).map(simple -> {
				String nsURI = getValue(simple, PROPERTY_NS_URI);
				String className = getValue(simple, PROPERTY_CLASS_NAME);

				EPackage ePackage = EMFHelper.getResourceSet(simple).getPackageRegistry().getEPackage(nsURI);
				return (ePackage == null) ? null : ePackage.getEClassifier(className);
			}).orElse(null);
		}

		return result;
	}

	@Override
	public String getClassName(ConstraintDescriptor instanceOfConstraint) {
		return asSimpleConstraint(instanceOfConstraint).map(simple -> getValue(simple, PROPERTY_CLASS_NAME)).orElse(null);
	}

	@Override
	public ValueProperty getClassNameProperty(ConstraintDescriptor instanceOfConstraint) {
		return asSimpleConstraint(instanceOfConstraint).map(simple -> getProperty(simple, PROPERTY_CLASS_NAME)).orElse(null);
	}

	@Override
	public IGenerator createGenerator(EObject sourceClass) {
		IGenerator result = null;

		if (sourceClass instanceof EClassifier) {
			result = new EcoreGenerator() {
				{
					ecorePackage = ((EClassifier) sourceClass).getEPackage();
					listEPackages = Lists.newArrayList(ecorePackage);
				}
			};
		}

		return result;
	}

	@Override
	protected EObject getProperty(EObject class_, Property contextProperty) {
		return class_ instanceof EClass ? ((EClass) class_).getEStructuralFeature(contextProperty.getName()) : null;
	}

	@Override
	public String getName(EObject sourceElement, NameKind kind) {
		String result = null;

		if (sourceElement instanceof ENamedElement) {
			ENamedElement namedElement = (ENamedElement) sourceElement;
			switch (kind) {
			case QUALIFIED:
				result = UML2Util.getQualifiedName(namedElement, "::"); //$NON-NLS-1$
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
		return (sourceProperty instanceof ETypedElement) ? ((ETypedElement) sourceProperty).getUpperBound() : DEFAULT_MULTIPLICITY;
	}

	@Override
	protected Switch<EObject> createConstraintsSwitch() {
		return new ConstraintsDelegate() {
			@Override
			protected EObject constraintSwitch(SimpleConstraint constraint, String constraintClass) {
				EObject result = null;

				switch (constraintClass) {
				case EMF_INSTANCE_OF:
					result = getEClassifier(constraint);
					break;
				}

				return result;
			}
		};
	}

	private EObject getEClassifier(SimpleConstraint constraint) {
		EObject result = null;
		String nsURI = getValue(constraint, PROPERTY_NS_URI);
		String className = getValue(constraint, PROPERTY_CLASS_NAME);

		if (nsURI != null && className != null) {
			EPackage ePackage = EMFHelper.getResourceSet(constraint).getPackageRegistry().getEPackage(nsURI);
			return ePackage != null ? ePackage.getEClassifier(className) : null;
		}

		return result;
	}

	private void setEClassifier(SimpleConstraint constraint, EClass eClass) {
		setValue(constraint, PROPERTY_NS_URI, eClass.getEPackage().getNsURI());
		setValue(constraint, PROPERTY_CLASS_NAME, eClass.getName());
	}

}
