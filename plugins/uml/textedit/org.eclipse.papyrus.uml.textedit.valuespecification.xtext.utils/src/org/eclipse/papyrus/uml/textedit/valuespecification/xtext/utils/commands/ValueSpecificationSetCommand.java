/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.validation.commands.AbstractValidateCommand;
import org.eclipse.papyrus.infra.services.validation.commands.AsyncValidateSubtreeCommand;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.AbstractRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralBooleanRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralIntegerOrUnlimitedNaturalRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralNullRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralRealRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralStringRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.Activator;
import org.eclipse.papyrus.uml.xtext.integration.XtextFakeResourceContext;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProviderWithInit;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;

import com.google.inject.Injector;

/**
 * This class allow to create the Set command from a xtext string value fill by
 * the user.
 */
public class ValueSpecificationSetCommand {

	/**
	 * The instance of the class.
	 */
	private static ValueSpecificationSetCommand instance = new ValueSpecificationSetCommand();

	/**
	 * Constructor.
	 */
	public ValueSpecificationSetCommand() {
		// Nothing
	}

	/**
	 * Get the single instance of ValueSpecificationSetCommand.
	 * 
	 * @return The single instance of ValueSpecificationSetCommand.
	 */
	public static ValueSpecificationSetCommand getInstance() {
		return instance;
	}

	/**
	 * This allow to create the set command for the value specification from a
	 * xtext string value (need to parse it with xtext parser).
	 * 
	 * @param injector
	 *            The injector used to parse the xtext string value.
	 * @param objectToEdit
	 *            The parent object of value specification.
	 * @param structuralFeature
	 *            The structural feature.
	 * @param xtextStringValue
	 *            The initial xtext string value.
	 * @param defaultLanguages
	 *            The default languages for an opaque expression.
	 * @return The created set command allow to set the value specification on
	 *         the objectToEdit.
	 */
	public CompositeCommand createSetCommand(final Injector injector,
			final EObject objectToEdit,
			final EStructuralFeature structuralFeature,
			final String xtextStringValue,
			final Collection<String> defaultLanguages) {

		// Get the initial value specification
		ValueSpecification initialValueSpecification = null;
		if (null != structuralFeature) {
			initialValueSpecification = (ValueSpecification) objectToEdit
					.eGet(structuralFeature);
		}

		// Prepare the composite command
		final CompositeCommand result = new CompositeCommand("validation"); //$NON-NLS-1$
		final IContextElementProvider provider = getContextProvider(objectToEdit);

		// Get the xtext face resource context (needed to parse the xtext string
		// value
		XtextFakeResourceContext context = new XtextFakeResourceContext(
				injector);
		context.getFakeResource().eAdapters()
				.add(new ContextElementAdapter(provider));
		// Load the xtext string value
		try {
			context.getFakeResource().load(
					new StringInputStream(xtextStringValue),
					Collections.EMPTY_MAP);
		} catch (IOException e) {
			Activator.log.error(e);
		}
		if (provider instanceof IContextElementProviderWithInit) {
			((IContextElementProviderWithInit) provider).initResource(context
					.getFakeResource());
		}
		EcoreUtil2.resolveLazyCrossReferences(context.getFakeResource(),
				CancelIndicator.NullImpl);
		if (!context.getFakeResource().getParseResult().hasSyntaxErrors()
				&& context.getFakeResource().getErrors().size() == 0) {
			// No error during the parser of xtext string value
			EObject xtextObject = context.getFakeResource().getParseResult()
					.getRootASTElement();

			ICommand cmd = getParseCommand(objectToEdit,
					initialValueSpecification, structuralFeature, xtextObject,
					xtextStringValue, defaultLanguages);
			if (null != cmd) {
				result.add(cmd);
			}
		} else {
			// The parser of xtext string value throw errors
			result.add(manageOpaqueExpression(objectToEdit, structuralFeature, initialValueSpecification, xtextStringValue, defaultLanguages));
		}
		AbstractValidateCommand validationCommand = new AsyncValidateSubtreeCommand(objectToEdit);
		validationCommand.disableUIFeedback();
		result.add(validationCommand);
		return result;
	}

	/**
	 * This allow to create the parse command of value specification (manage to
	 * create the value specification or the opaque expression).
	 * 
	 * @param objectToEdit
	 *            The parent object of value specification.
	 * @param structuralFeature
	 *            The structural feature.
	 * @param xtextObject
	 *            The xtext object.
	 * @param xtextStringValue
	 *            The initial xtext string value.
	 * @param defaultLanguages
	 *            The default languages for an opaque expression.
	 * @return The created set command allow to set the value specification on
	 *         the objectToEdit.
	 */
	public ICommand getParseCommand(final EObject objectToEdit,
			final EStructuralFeature structuralFeature,
			final EObject xtextObject, final String xtextStringValue,
			final Collection<String> defaultLanguages) {

		// Get the initial value specification
		ValueSpecification initialValueSpecification = null;
		if (null != structuralFeature) {
			initialValueSpecification = (ValueSpecification) objectToEdit
					.eGet(structuralFeature);
		}

		return getParseCommand(objectToEdit, initialValueSpecification,
				structuralFeature, xtextObject, xtextStringValue,
				defaultLanguages);
	}

	/**
	 * This allow to create the parse command of value specification (manage to
	 * create the value specification or the opaque expression).
	 * 
	 * @param objectToEdit
	 *            The parent object of value specification.
	 * @param initialValueSpecification
	 *            The initial value specfication.
	 * @param structuralFeature
	 *            The structural feature.
	 * @param xtextObject
	 *            The xtext object.
	 * @param xtextStringValue
	 *            The initial xtext string value.
	 * @param defaultLanguages
	 *            The default languages for an opaque expression.
	 * @return The created set command allow to set the value specification on
	 *         the objectToEdit.
	 */
	protected ICommand getParseCommand(final EObject objectToEdit,
			final ValueSpecification initialValueSpecification,
			final EStructuralFeature structuralFeature,
			final EObject xtextObject, final String xtextStringValue,
			final Collection<String> defaultLanguages) {

		// Check if the object to edit is not multi-valued
		if (null != objectToEdit
				&& (!(objectToEdit instanceof MultiplicityElement) || !(((MultiplicityElement) objectToEdit)
						.isMultivalued()))) {
			return manageValueSpecification(objectToEdit, structuralFeature,
					initialValueSpecification, xtextObject, xtextStringValue,
					defaultLanguages);
		} else {
			// The object is multi-valued, create an opaque expression
			return manageOpaqueExpression(
					objectToEdit, structuralFeature, initialValueSpecification, xtextStringValue,
					defaultLanguages);
		}
	}

	/**
	 * This allow to create the command from the request.
	 * 
	 * @param objectToEdit
	 *            The parent object of value specification.
	 * @param structuralFeature
	 *            The structural feature.
	 * @param valueSpecification
	 *            The value specification created.
	 * @return The command corresponding to the request.
	 */
	protected ICommand createCommand(final EObject objectToEdit,
			final EStructuralFeature structuralFeature,
			final ValueSpecification valueSpecification) {

		final CompositeCommand setValueCommand = new CompositeCommand("Set Value Specification Command"); //$NON-NLS-1$

		final SetRequest request = new SetRequest(objectToEdit,
				structuralFeature, valueSpecification);

		final IElementEditService commandProvider = ElementEditServiceUtils
				.getCommandProvider(objectToEdit);
		ICommand setDefaultValueCommand = commandProvider
				.getEditCommand(request);
		if (null != setDefaultValueCommand
				&& setDefaultValueCommand.canExecute()) {
			setValueCommand.add(setDefaultValueCommand);
		} else {
			setValueCommand
					.add(UnexecutableCommand.INSTANCE);
		}
		return setValueCommand.isEmpty() ? null : setValueCommand;
	}

	/**
	 * This allow to update/create the value specification from the xtext parser.
	 * 
	 * @param objectToEdit
	 *            The parent object of value specification.
	 * @param structuralFeature
	 *            The structural feature.
	 * @param initialValueSpecification
	 *            The initial value specification.
	 * @param xtextObject
	 *            The object created by the text parsing.
	 * @param xtextStringValue
	 *            The string parsed.
	 * @param defaultLanguages
	 *            The default languages for an opaque expression.
	 * @return The create {@link ValueSpecification}
	 */
	protected ICommand manageValueSpecification(
			final EObject objectToEdit,
			final EStructuralFeature structuralFeature,
			final ValueSpecification initialValueSpecification,
			final EObject xtextObject, final String xtextStringValue,
			final Collection<String> defaultLanguages) {

		ICommand resultCommand = null;
		ValueSpecification newValueSpecification = null;

		// Check that the xtext object parsed is the correct one
		if (xtextObject instanceof AbstractRule) {
			if (null != ((AbstractRule) xtextObject).getUndefined()) {
				if (null != initialValueSpecification) {
					resultCommand = createCommand(objectToEdit, structuralFeature, null);
				}
			} else {
				final AbstractRule abstractRule = (AbstractRule) xtextObject;
				// Get the object to update if the existing is corresponding to the parsed potential value specification

				newValueSpecification = getObjectToUpdate(objectToEdit, initialValueSpecification, abstractRule);

				// If the object to update is not correct, create the good ValueSpecification
				if (null != newValueSpecification) {
					resultCommand = createUpdateValueSpecificationCommand(objectToEdit, structuralFeature, newValueSpecification, abstractRule);
				} else {
					newValueSpecification = createValueSpecification(objectToEdit, initialValueSpecification, abstractRule);

					if (null != newValueSpecification) {
						// Affect the name and the visibility
						affectAttributes(newValueSpecification, abstractRule);
					} else {
						// Create the opaque expression if no value specification
						// was created
						newValueSpecification = createOpaqueExpression(
								initialValueSpecification, xtextStringValue,
								defaultLanguages);
					}

					resultCommand = createCommand(objectToEdit, structuralFeature, newValueSpecification);
				}
			}
		}
		return resultCommand;
	}

	/**
	 * This allow to create the {@link ValueSpecification} corresponding to the xtext object parsed.
	 * 
	 * @param objectToEdit
	 *            The object to edit (parent of ValueSpecification).
	 * @param initialValueSpecification
	 *            The initial {@link ValueSpecification}
	 * @param abstractRule
	 *            The xtext object parsed.
	 * @return The created {@link ValueSpecification} or <code>null</code>.
	 */
	protected ValueSpecification createValueSpecification(final EObject objectToEdit, final EObject initialValueSpecification, final AbstractRule abstractRule) {
		ValueSpecification createdValueSpecification = null;

		final EObject value = abstractRule.getValue();
		if (null != abstractRule.getInstanceSpecification()) {
			// Create an instance value with specification value
			createdValueSpecification = UMLFactory.eINSTANCE
					.createInstanceValue();
			((InstanceValue) createdValueSpecification)
					.setInstance(abstractRule.getInstanceSpecification());
		} else {
			if (value instanceof LiteralBooleanRule) {
				// Check that the type of the parent is a boolean
				if (isTypeNeeeded(objectToEdit,
						UMLPackage.Literals.LITERAL_BOOLEAN)) {
					// Create a literal boolean
					createdValueSpecification = UMLFactory.eINSTANCE
							.createLiteralBoolean();
					copyFeatureValues(createdValueSpecification,
							initialValueSpecification);
					((LiteralBoolean) createdValueSpecification)
							.setValue(Boolean
									.parseBoolean(((LiteralBooleanRule) value)
											.getValue()));
				}
			} else if (value instanceof LiteralIntegerOrUnlimitedNaturalRule) {
				boolean created = false;
				final LiteralIntegerOrUnlimitedNaturalRule integerValue = (LiteralIntegerOrUnlimitedNaturalRule) value;

				// Check that the value is upper than 0 and the type of the
				// parent is a integer
				if (null != integerValue.getUnlimited()) {
					// Create a literal unlimited natural
					createdValueSpecification = UMLFactory.eINSTANCE
							.createLiteralUnlimitedNatural();
					copyFeatureValues(createdValueSpecification,
							initialValueSpecification);
					((LiteralUnlimitedNatural) createdValueSpecification)
							.setValue(-1);
					created = true;
				} else {
					int intValue = integerValue.getValue();
					if (0 <= intValue && isTypeNeeeded(
							objectToEdit,
							UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL)) {

						// Create a literal unlimited natural
						createdValueSpecification = UMLFactory.eINSTANCE
								.createLiteralUnlimitedNatural();
						copyFeatureValues(createdValueSpecification,
								initialValueSpecification);
						((LiteralUnlimitedNatural) createdValueSpecification).setValue(intValue);
						created = true;
					}


					// Check that the value specification is not already created
					// and the type of the parent is an integer
					if (!created
							&& isTypeNeeeded(objectToEdit,
									UMLPackage.Literals.LITERAL_INTEGER)) {
						// Create a literal unlimited natural
						createdValueSpecification = UMLFactory.eINSTANCE
								.createLiteralInteger();
						copyFeatureValues(createdValueSpecification,
								initialValueSpecification);
						((LiteralInteger) createdValueSpecification)
								.setValue(intValue);
						created = true;
					}

					// Check that the value specification is not already created
					// and the type of the parent is a real
					if (!created
							&& isTypeNeeeded(objectToEdit,
									UMLPackage.Literals.LITERAL_REAL)) {
						// Create a literal unlimited natural
						createdValueSpecification = UMLFactory.eINSTANCE
								.createLiteralReal();
						copyFeatureValues(createdValueSpecification,
								initialValueSpecification);
						((LiteralReal) createdValueSpecification)
								.setValue(intValue);
					}
				}
			} else if (value instanceof LiteralRealRule) {
				// Check that the type of the parent is a real
				if (isTypeNeeeded(objectToEdit,
						UMLPackage.Literals.LITERAL_REAL)) {
					// Create a literal real
					createdValueSpecification = UMLFactory.eINSTANCE
							.createLiteralReal();
					copyFeatureValues(createdValueSpecification,
							initialValueSpecification);
					((LiteralReal) createdValueSpecification)
							.setValue(((LiteralRealRule) value).getValue());
				}
			} else if (value instanceof LiteralNullRule) {
				// Create a literal null
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralNull();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
			} else if (value instanceof LiteralStringRule) {
				// Create a literal real
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralString();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
				((LiteralString) createdValueSpecification)
						.setValue(((LiteralStringRule) value).getValue());
			}
		}

		return createdValueSpecification;
	}

	/**
	 * This allow to affect the attributes of the value specification (name and
	 * visibility).
	 * 
	 * @param createdValueSpecification
	 *            The created {@link ValueSpecification}.
	 * @param abstractRule
	 *            The abstract rule.
	 */
	protected void affectAttributes(
			final ValueSpecification createdValueSpecification,
			final AbstractRule abstractRule) {
		// Check that the visibility was set
		if (null != abstractRule.getVisibility()) {
			VisibilityKind visibility = null;
			if (null != abstractRule.getVisibility().getPublic()) {
				visibility = VisibilityKind.PUBLIC_LITERAL;
			} else if (null != abstractRule.getVisibility().getPackage()) {
				visibility = VisibilityKind.PACKAGE_LITERAL;
			} else if (null != abstractRule.getVisibility().getProtected()) {
				visibility = VisibilityKind.PROTECTED_LITERAL;
			} else if (null != abstractRule.getVisibility().getPrivate()) {
				visibility = VisibilityKind.PRIVATE_LITERAL;
			}
			// Affect the correct visibility
			createdValueSpecification.setVisibility(visibility);
		}

		// Set the name if it was created
		if (null != getName(abstractRule)) {
			createdValueSpecification.setName(getName(abstractRule));
		}
	}

	/**
	 * This allow to create the command to update the value specification (visibility, name and value).
	 * 
	 * @param objectToEdit
	 *            The object to edit (parent of ValueSpecification).
	 * @param structuralFeature
	 *            The structural feature.
	 * @param valueSpecification
	 *            The value specification to update.
	 * @param abstractRule
	 *            The xtext object parsed.
	 * @return The command with the update commands.
	 */
	protected ICommand createUpdateValueSpecificationCommand(final EObject objectToEdit, final EStructuralFeature structuralFeature, final ValueSpecification valueSpecification, final AbstractRule abstractRule) {
		final CompositeCommand setAttributesCommand = new CompositeCommand("Update Value Specification Command"); //$NON-NLS-1$

		// Check that the visibility was set
		if (null != abstractRule.getVisibility()) {
			VisibilityKind visibility = null;
			if (null != abstractRule.getVisibility().getPublic()) {
				visibility = VisibilityKind.PUBLIC_LITERAL;
			} else if (null != abstractRule.getVisibility().getPackage()) {
				visibility = VisibilityKind.PACKAGE_LITERAL;
			} else if (null != abstractRule.getVisibility().getProtected()) {
				visibility = VisibilityKind.PROTECTED_LITERAL;
			} else if (null != abstractRule.getVisibility().getPrivate()) {
				visibility = VisibilityKind.PRIVATE_LITERAL;
			}

			if (null == valueSpecification.getVisibility() || !valueSpecification.getVisibility().equals(visibility)) {
				// Set the visibility by command
				final SetRequest request = new SetRequest(valueSpecification,
						UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, visibility);

				final IElementEditService commandProvider = ElementEditServiceUtils
						.getCommandProvider(valueSpecification);
				ICommand setVisibilityCommand = commandProvider
						.getEditCommand(request);
				if (null != setVisibilityCommand
						&& setVisibilityCommand.canExecute()) {
					setAttributesCommand.add(setVisibilityCommand);
				}
			}
		}

		// Set the name if it was created
		final String newName = getName(abstractRule);
		if (null != newName) {
			if(InternationalizationPreferencesUtils.getInternationalizationPreference(valueSpecification) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(valueSpecification)){
				// Set the label by command
				final ModelSet modelSet = (ModelSet)valueSpecification.eResource().getResourceSet();
				if (null != modelSet){
					final ICommand setLabelCommand = new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(modelSet.getTransactionalEditingDomain(), valueSpecification, newName, null));
					if(null != setLabelCommand && setLabelCommand.canExecute()){
						setAttributesCommand.add(setLabelCommand);
					}
				}
			}else{
				// Set the name by command
				final ICommand setNameCommand = createSetNameCommand(valueSpecification, newName);
				if (null != setNameCommand
						&& setNameCommand.canExecute()) {
					setAttributesCommand.add(setNameCommand);
				}
			}
		}

		// Set the value by command
		final ICommand setValueCommand = createSetValueCommand(valueSpecification, abstractRule);
		if (null != setValueCommand
				&& setValueCommand.canExecute()) {
			setAttributesCommand.add(setValueCommand);
		}

		// For the refresh of properties ValueSpecification, this one will be used and reset to allow
		// the notification and the refresh after this

		// Unset the structural feature
		final SetRequest unsetRequest = new SetRequest(objectToEdit,
				structuralFeature, null);

		final IElementEditService commandProvider = ElementEditServiceUtils
				.getCommandProvider(objectToEdit);
		final ICommand unsetCommand = commandProvider
				.getEditCommand(unsetRequest);
		if (null != unsetCommand
				&& unsetCommand.canExecute()) {
			setAttributesCommand.add(unsetCommand);

			// Reset the structural feature
			final SetRequest resetRequest = new SetRequest(objectToEdit,
					structuralFeature, valueSpecification);

			final ICommand resetCommand = commandProvider
					.getEditCommand(resetRequest);
			if (null != resetCommand
					&& resetCommand.canExecute()) {
				setAttributesCommand.add(resetCommand);
			}
		}

		return setAttributesCommand.isEmpty() ? null : setAttributesCommand;
	}

	/**
	 * This allow to create the set name command for a value specification.
	 * 
	 * @param valueSpecification
	 *            The value specification to update.
	 * @param name
	 *            The name to affect.
	 * @return The command which allow to update the value specification.
	 */
	protected ICommand createSetNameCommand(final ValueSpecification valueSpecification, final String name) {
		ICommand resultCommand = null;
		// Set the name by command
		if (null == valueSpecification.getName() || !valueSpecification.getName().equals(name)) {
			final SetRequest request = new SetRequest(valueSpecification,
					UMLPackage.Literals.NAMED_ELEMENT__NAME, name);

			final IElementEditService commandProvider = ElementEditServiceUtils
					.getCommandProvider(valueSpecification);
			resultCommand = commandProvider.getEditCommand(request);
		}
		return resultCommand;
	}

	/**
	 * This allow to create the set value command for a value specification.
	 * 
	 * @param valueSpecification
	 *            The value specification to update.
	 * @param abstractRule
	 *            The xtext object parsed.
	 * @return The command which allow to update the value specification.
	 */
	protected ICommand createSetValueCommand(final ValueSpecification valueSpecification, final AbstractRule abstractRule) {
		ICommand resultCommand = null;
		SetRequest request = null;
		if (valueSpecification instanceof InstanceValue && null != abstractRule.getInstanceSpecification()) {
			request = new SetRequest(valueSpecification,
					UMLPackage.Literals.INSTANCE_VALUE__INSTANCE, abstractRule.getInstanceSpecification());
		} else {
			final EObject xtextValue = abstractRule.getValue();
			if (valueSpecification instanceof LiteralBoolean && xtextValue instanceof LiteralBooleanRule) {
				request = new SetRequest(valueSpecification,
						UMLPackage.Literals.LITERAL_BOOLEAN__VALUE, Boolean.parseBoolean(((LiteralBooleanRule) xtextValue).getValue()));
			} else if (valueSpecification instanceof LiteralInteger && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule && null == ((LiteralIntegerOrUnlimitedNaturalRule)xtextValue).getUnlimited()) {
				 request = new SetRequest(valueSpecification,
				 UMLPackage.Literals.LITERAL_INTEGER__VALUE, ((LiteralIntegerOrUnlimitedNaturalRule) xtextValue).getValue());
			} else if (valueSpecification instanceof LiteralUnlimitedNatural && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule) {
				 int intValue = 0;
				 if (null != ((LiteralIntegerOrUnlimitedNaturalRule)xtextValue).getUnlimited()) {
					 intValue = -1;
				 } else {
					 intValue = ((LiteralIntegerOrUnlimitedNaturalRule)xtextValue).getValue();
				 }
				 request = new SetRequest(valueSpecification,
				 UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE, intValue);
			} else if (valueSpecification instanceof LiteralReal && xtextValue instanceof LiteralRealRule) {
				request = new SetRequest(valueSpecification,
						UMLPackage.Literals.LITERAL_REAL__VALUE, ((LiteralRealRule) xtextValue).getValue());
			} else if (valueSpecification instanceof LiteralString && xtextValue instanceof LiteralStringRule) {
				request = new SetRequest(valueSpecification,
						UMLPackage.Literals.LITERAL_STRING__VALUE, ((LiteralStringRule) xtextValue).getValue());
			}
		}

		if (null != request) {
			final IElementEditService commandProvider = ElementEditServiceUtils
					.getCommandProvider(valueSpecification);
			resultCommand = commandProvider.getEditCommand(request);
		}

		return resultCommand;
	}

	/**
	 * This allow to copy all the old feature values from the existing object to
	 * the new one.
	 * 
	 * @param newValueSpecification
	 *            The new value specification.
	 * @param existingObject
	 *            The existing {@link EObject}.
	 */
	protected void copyFeatureValues(
			final ValueSpecification newValueSpecification,
			final EObject existingObject) {

		// The copy of feature isn't possible for the Opaque Expression
		if (null != existingObject
				&& existingObject instanceof ValueSpecification) {
			ValueSpecification existingValueSpecification = (ValueSpecification) existingObject;
			// Loop on each structural features
			for (EStructuralFeature structuralFeature : existingValueSpecification
					.eClass().getEAllStructuralFeatures()) {
				// Check that the structural is changeable and that the new
				// value specification contains this structural feature (it is
				// needed because the sub classes of ValueSpecification
				if (structuralFeature.isChangeable()
						&& newValueSpecification.eClass()
								.getEAllStructuralFeatures()
								.contains(structuralFeature)) {
					if (!(existingObject instanceof OpaqueExpression && structuralFeature.equals(UMLPackage.Literals.NAMED_ELEMENT__NAME))) {
						newValueSpecification.eSet(structuralFeature,
								existingObject.eGet(structuralFeature));
					}
				}
			}
			
			// Copy the label is existing
			final String existingLabel = UMLLabelInternationalization.getInstance().getLabelWithoutUML((ValueSpecification)existingObject);
			if(InternationalizationPreferencesUtils.getInternationalizationPreference(existingObject) && null != existingLabel){
				UMLLabelInternationalization.getInstance().setLabel(newValueSpecification, existingLabel, null);
			}
		}
	}

	/**
	 * Check the type of the object with the type needed (represented by
	 * string).
	 * 
	 * @param object
	 *            The object to check.
	 * @param typeNeeded
	 *            The type needed.
	 * @return <code>true</code> if the object allow the typed needed, <code>false</code> otherwise.
	 */
	protected boolean isTypeNeeeded(final Object object, final Object typeNeeded) {

		boolean result = false;
		if (!(object instanceof TypedElement)) {
			// If the object is a typed element
			result = true;
		} else {
			TypedElement typedElement = (TypedElement) object;
			if (null == typedElement.getType()
					|| !(typedElement.getType() instanceof PrimitiveType)) {
				result = true;
			} else if (typedElement.getType() instanceof PrimitiveType) {
				PrimitiveType type = (PrimitiveType) typedElement.getType();
				if (typeNeeded.equals(UMLPackage.Literals.LITERAL_BOOLEAN)) {
					result = UMLUtil.isBoolean(type);
				} else if (typeNeeded
						.equals(UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL)) {
					result = UMLUtil.isUnlimitedNatural(type);
				} else if (typeNeeded
						.equals(UMLPackage.Literals.LITERAL_INTEGER)) {
					result = UMLUtil.isInteger(type);
				} else if (typeNeeded.equals(UMLPackage.Literals.LITERAL_REAL)) {
					result = UMLUtil.isReal(type);
				}
			}
		}
		return result;
	}

	/**
	 * This allow to manage the opaque expression update or create.
	 * 
	 * @param initialEObject
	 *            The initial object to edit.
	 * @param structuralFeature
	 *            The structural feature to update/create.
	 * @param initialValueSpecification
	 *            The initial {@link ValueSpecification}.
	 * @param xtextStringValue
	 *            The xtext string value.
	 * @param defaultLanguages
	 *            The default languages for the {@link paqueExpression}.
	 * @return The created command for the update or creation of the {@link paqueExpression}.
	 */
	protected ICommand manageOpaqueExpression(final EObject initialEObject, final EStructuralFeature structuralFeature, final ValueSpecification initialValueSpecification, final String xtextStringValue, final Collection<String> defaultLanguages) {
		ICommand resultCommand = null;
		if (null != initialValueSpecification && initialValueSpecification instanceof OpaqueExpression && !xtextStringValue.isEmpty()) {
			resultCommand = createUpdateOpaqueExpressionCommand((OpaqueExpression) initialValueSpecification, xtextStringValue);
		} else {
			OpaqueExpression createdOpaqueExpression = null;
			if (!xtextStringValue.isEmpty()) {
				createdOpaqueExpression = createOpaqueExpression(initialValueSpecification, xtextStringValue, defaultLanguages);
			}
			resultCommand = createCommand(initialEObject, structuralFeature, createdOpaqueExpression);
		}
		return resultCommand;
	}

	/**
	 * This allow to create the update command of the {@link OpaqueExpression}.
	 * 
	 * @param opaqueExpression
	 *            The {@link OpaqueExpression} to update.
	 * @param name
	 *            The new name of the {@link OpaqueExpression}.
	 * @return The update command which allow to update the {@link OpaqueExpression}.
	 */
	protected ICommand createUpdateOpaqueExpressionCommand(final OpaqueExpression opaqueExpression, final String name) {
		final CompositeCommand setAttributesCommand = new CompositeCommand("Update Opaque Expression Command"); //$NON-NLS-1$

		if (!name.isEmpty()) {
			if(InternationalizationPreferencesUtils.getInternationalizationPreference(opaqueExpression) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(opaqueExpression)){
				// Set the label by command
				final ModelSet modelSet = (ModelSet)opaqueExpression.eResource().getResourceSet();
				if (null != modelSet){
					final ICommand setLabelCommand = new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(modelSet.getTransactionalEditingDomain(), opaqueExpression, name, null));
					if(null != setLabelCommand && setLabelCommand.canExecute()){
						setAttributesCommand.add(setLabelCommand);
					}
				}
			}else{
				// Set the name by command
				ICommand setNameCommand = createSetNameCommand(opaqueExpression, name);
				if (null != setNameCommand
						&& setNameCommand.canExecute()) {
					setAttributesCommand.add(setNameCommand);
				}
			}
		}

		return setAttributesCommand;
	}

	/**
	 * This allow to create the opaque expression.
	 * 
	 * @param initiaValueSpecification
	 *            The initial value specification.
	 * @param xtextStringValue
	 *            The xtext string value.
	 * @param defaultLanguages
	 *            The default languages for an opaque expression.
	 * @return The created {@link OpaqueExpression}.
	 */
	protected OpaqueExpression createOpaqueExpression(
			final EObject initiaValueSpecification, final String xtextStringValue,
			final Collection<String> defaultLanguages) {
		// Create the opaque expression
		final OpaqueExpression valueSpecification = UMLFactory.eINSTANCE
				.createOpaqueExpression();
		copyFeatureValues(valueSpecification, initiaValueSpecification);
		valueSpecification.getLanguages().addAll(
				defaultLanguages);
		valueSpecification.setName(xtextStringValue);
		return valueSpecification;
	}

	/**
	 * Get the context provider of the object to edit.
	 * 
	 * @param objectToEdit
	 *            The object to edit.
	 * @return The context element provider corresponding to the object to edit.
	 */
	protected IContextElementProvider getContextProvider(
			final EObject objectToEdit) {

		return new IContextElementProvider() {

			public EObject getContextObject() {
				if (objectToEdit instanceof EObject) {
					return (EObject) objectToEdit;
				}
				return null;
			}
		};
	}

	/**
	 * This allow to get the existing object to update if this is possible.
	 * 
	 * @param objectToEdit
	 *            The object to edit (parent of ValueSpecification).
	 * @param initialValueSpecification
	 *            The object to edit.
	 * @param xtextObject
	 *            The xtext object.
	 * @return The {@link ValueSpecification} existing or <code>null</code>.
	 */
	protected ValueSpecification getObjectToUpdate(final EObject objectToEdit, final ValueSpecification initialValueSpecification, final EObject xtextObject) {
		ValueSpecification result = null;
		if (null != initialValueSpecification && null != xtextObject && xtextObject instanceof AbstractRule && null == ((AbstractRule) xtextObject).getUndefined()) {
			final AbstractRule abstractRule = (AbstractRule) xtextObject;
			if (initialValueSpecification instanceof InstanceValue && null != abstractRule.getInstanceSpecification()) {
				result = (InstanceValue) initialValueSpecification;
			} else {
				final EObject xtextValue = abstractRule.getValue();
				if (initialValueSpecification instanceof LiteralBoolean && xtextValue instanceof LiteralBooleanRule && isTypeNeeeded(objectToEdit, UMLPackage.Literals.LITERAL_BOOLEAN)) {
					result = (LiteralBoolean) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralInteger && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule && isTypeNeeeded(objectToEdit, UMLPackage.Literals.LITERAL_INTEGER)) {
					if (null == ((LiteralIntegerOrUnlimitedNaturalRule) xtextValue).getUnlimited()) {
						result = (LiteralInteger) initialValueSpecification;
					}
				} else if (initialValueSpecification instanceof LiteralUnlimitedNatural && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule && isTypeNeeeded(objectToEdit, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL)) {
					// Check that the value if positive for the unlimited natural type
					final LiteralIntegerOrUnlimitedNaturalRule integerValue = ((LiteralIntegerOrUnlimitedNaturalRule) xtextValue);
					if (null != integerValue.getUnlimited() || 0 <= integerValue.getValue()) {
						result = (LiteralUnlimitedNatural) initialValueSpecification;
					}
				} else if (initialValueSpecification instanceof LiteralReal && xtextValue instanceof LiteralRealRule && isTypeNeeeded(objectToEdit, UMLPackage.Literals.LITERAL_REAL)) {
					result = (LiteralReal) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralNull && xtextValue instanceof LiteralNullRule) {
					result = (LiteralNull) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralString && xtextValue instanceof LiteralStringRule) {
					result = (LiteralString) initialValueSpecification;
				}
			}
		}
		return result;
	}

	/**
	 * Get the name from the abstractRule.
	 * 
	 * @param abstractRule
	 *            The abstract rule.
	 * @return The string corresponding to the name.
	 */
	protected String getName(final AbstractRule abstractRule) {
		return null == abstractRule.getName() ? null : abstractRule.getName().substring(0, abstractRule.getName().length() - 1);
	}
}
