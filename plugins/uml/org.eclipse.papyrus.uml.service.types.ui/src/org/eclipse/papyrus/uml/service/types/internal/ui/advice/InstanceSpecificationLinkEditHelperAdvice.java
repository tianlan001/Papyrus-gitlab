package org.eclipse.papyrus.uml.service.types.internal.ui.advice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.service.types.internal.ui.commands.InstanceSpecificationLinkCreateCommand;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Type;

public class InstanceSpecificationLinkEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This parameter is for tests only.
	 * When present in request parameters, the value for this key will be silently
	 * returned from the code that normally opens the dialog.
	 * It allows to emulate different user actions in tests
	 */
	private static final String PARAM_SUPPRESS_DIALOG_WITH_VALUE = InstanceSpecificationLinkEditHelperAdvice.class.getName() + ":" + "AssociationSelectionDialogResult";

	public static boolean canCreate(EObject source, EObject target) {
		/*
		 * Case 0: Only the target is null
		 */
		if (source != null && target == null) {
			return source instanceof InstanceSpecification;
		}
		/*
		 * Case 1 : source and target != null
		 * look for if it exist at least a common association between classifiers referenced between these instances
		 */
		if (source == null || target == null) {
			return false;
		}
		if (false == source instanceof InstanceSpecification) {
			return false;
		}
		if (false == target instanceof InstanceSpecification) {
			return false;
		}
		return true;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		InstanceSpecification linkToEdit = null;
		InstanceSpecification source = null;
		InstanceSpecification target = null;
		if (request.getElementToConfigure() instanceof InstanceSpecification) {
			linkToEdit = (InstanceSpecification) (request.getElementToConfigure());
		}
		if (request.getParameter(CreateRelationshipRequest.SOURCE) instanceof InstanceSpecification) {
			source = (InstanceSpecification) request.getParameter(CreateRelationshipRequest.SOURCE);
		}
		if (request.getParameter(CreateRelationshipRequest.TARGET) instanceof InstanceSpecification) {
			target = (InstanceSpecification) request.getParameter(CreateRelationshipRequest.TARGET);
		}
		if (source != null && target != null && linkToEdit != null) {
			return new InstanceSpecificationLinkCreateCommand(request);
		}
		return super.getAfterConfigureCommand(request);
	}

	public static boolean shouldSuppressDialog(IEditCommandRequest request) {
		return request.getParameters().containsKey(PARAM_SUPPRESS_DIALOG_WITH_VALUE);
	}

	public static Association getSuppressedDialogResult(IEditCommandRequest request) {
		Object result = request.getParameter(PARAM_SUPPRESS_DIALOG_WITH_VALUE);
		return result == null ? null : (Association) result;
	}

	@SuppressWarnings("unchecked")
	public static void setupSuppressDialogRequest(CreateConnectionViewRequest request, Association association) {
		request.getExtendedData().put(PARAM_SUPPRESS_DIALOG_WITH_VALUE, association);
	}

	/**
	 * Gets the instance associations.
	 *
	 * @param instance
	 *            the instance
	 * @return the instance associations
	 */
	private static HashSet<Association> getInstanceAssociations(InstanceSpecification instance) {
		// Initialise set of associations
		HashSet<Association> instanceAssociationsSet = new HashSet<Association>();
		// Extract all associations of Instance Specification's classifiers
		Iterator<Classifier> iterator = getSpecificationClassifier(instance).iterator();
		while (iterator.hasNext()) {
			Classifier classifier = iterator.next();
			instanceAssociationsSet.addAll(classifier.getAssociations());
		}
		return instanceAssociationsSet;
	}

	/**
	 * Gets the specification classifiers.
	 *
	 * @param instance
	 *            the instance
	 * @return the specification classifiers
	 */
	public static Set<Classifier> getSpecificationClassifier(InstanceSpecification instance) {
		// Initialise Set of Classifiers
		Set<Classifier> specificationClassicfiersSet = new HashSet<Classifier>();
		// Explore first rank classifiers
		for (Classifier classifier : instance.getClassifiers()) {

			// Explore only Classifier which are not already in Set
			if (!specificationClassicfiersSet.contains(classifier)) {
				specificationClassicfiersSet.add(classifier);
				specificationClassicfiersSet.addAll(getInheritedClassifier(classifier, null));
			}
		}
		return specificationClassicfiersSet;
	}

	/**
	 * Gets the inherited classifier.
	 *
	 * @param classifier
	 *            the classifier
	 * @return the inherited classifier
	 */
	private static Set<Classifier> getInheritedClassifier(Classifier classifier, Set<Classifier> alreadyParsedClassifier) {


		// Initialise set of Classifier from Generalisation
		Set<Classifier> generalizationClassifiers = new HashSet<Classifier>();


		// Keep track of parsed Classifier to avoid loop
		Set<Classifier> parsedClassifiersSet = new HashSet<Classifier>();
		if (alreadyParsedClassifier != null) {
			parsedClassifiersSet.addAll(alreadyParsedClassifier);
		}

		// Explore only Classifier which are not already parsed
		if (!parsedClassifiersSet.contains(classifier)) {
			parsedClassifiersSet.add(classifier);

			// Explore all generalisation of Classifier
			EList<Classifier> classifierGeneralizations = classifier.parents();
			generalizationClassifiers.addAll(classifierGeneralizations);

			for (Classifier generalClassifier : classifierGeneralizations) {
				generalizationClassifiers.addAll(getInheritedClassifier(generalClassifier, parsedClassifiersSet));
			}
		}

		return generalizationClassifiers;
	}

	/**
	 * Gets the instance associations.
	 *
	 * @param sourceInstance
	 *            the source instance
	 * @param targetInstance
	 *            the target instance
	 * @return the instance associations
	 */
	private static Set<Association> getInstanceAssociations(InstanceSpecification sourceInstance, InstanceSpecification targetInstance) {
		Set<Association> instanceAssociationsSet = new HashSet<Association>();
		// Extract all associations of Instance Specification's classifiers
		Iterator<Association> sourceAssociationsIterator = getInstanceAssociations(sourceInstance).iterator();
		Set<Classifier> sourceClassifiers = getSpecificationClassifier(sourceInstance);
		Set<Classifier> targetClassifiers = getSpecificationClassifier(targetInstance);
		while (sourceAssociationsIterator.hasNext()) {
			Association nextAssociation = sourceAssociationsIterator.next();
			if (checkAssociationEndType(nextAssociation, sourceClassifiers, targetClassifiers)) {
				instanceAssociationsSet.add(nextAssociation);
			}
		}
		return instanceAssociationsSet;
	}

	private static boolean checkAssociationEndType(Association association, Set<Classifier> sourceClassifiers, Set<Classifier> targetClassifiers) {
		if (association.getMemberEnds().size() != 2) {
			return false;
		}
		Type sourceAssociationEnd = association.getMemberEnds().get(0).getType();
		Type targetAssociationEnd = association.getMemberEnds().get(1).getType();
		for (Classifier nextSourceClassifier : sourceClassifiers) {
			for (Classifier nextTargetClassifier : targetClassifiers) {
				if ((nextSourceClassifier == sourceAssociationEnd && nextTargetClassifier == targetAssociationEnd) || //
						(nextSourceClassifier == targetAssociationEnd && nextTargetClassifier == sourceAssociationEnd)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Set<Association> getModelAssociations(InstanceSpecification source, InstanceSpecification target) {
		if (source == null || target == null) {
			return Collections.emptySet();
		}
		return getInstanceAssociations(source, target);
	}

}
