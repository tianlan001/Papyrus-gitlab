/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.ui.properties.modelelement;

import java.util.Collection;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableValue;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.properties.widgets.ProfileExplorerDialog;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.ui.properties.providers.RequiredProfilesLabelProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Profile;

/**
 * {@link EMFModelElement} for {@link StereotypeToApply}. Add required profile needed when set stereotype qualify name.
 */
public class StereotypeToApplyModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 */
	public StereotypeToApplyModelElement(final EObject sourceElement, final EditingDomain domain) {
		super(sourceElement, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#doGetObservable(java.lang.String)
	 */
	@Override
	protected IObservable doGetObservable(final String propertyPath) {
		IObservable observable = null;
		if ("stereotypeQualifiedName".equals(propertyPath)) {//$NON-NLS-1$
			observable = new StereotypeQualifyNameObsevableValue(source, getDomain());
		}
		return null != observable ? observable : super.doGetObservable(propertyPath);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;
		if ("requiredProfiles".equals(propertyPath)) {//$NON-NLS-1$
			labelProvider = new RequiredProfilesLabelProvider();
		}
		return null != labelProvider ? labelProvider : super.getLabelProvider(propertyPath);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getDirectCreation(java.lang.String)
	 */
	@Override
	public boolean getDirectCreation(final String propertyPath) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getValueFactory(java.lang.String)
	 */
	@Override
	public ReferenceValueFactory getValueFactory(final String propertyPath) {

		ReferenceValueFactory valueFactory = null;
		if ("requiredProfiles".equals(propertyPath)) {//$NON-NLS-1$
			// Set the string edition factory for required profiles
			valueFactory = new ReferenceValueFactory() {

				@Override
				public boolean canCreateObject() {
					return true;
				}

				@Override
				public boolean canEdit() {
					return true;
				}

				@Override
				public Object createObject(Control widget, Object context) {
					Object object = null;
					ProfileExplorerDialog dialog = new ProfileExplorerDialog(widget.getShell()); // $NON-NLS-1$

					int result = dialog.open();
					if (result == Window.OK) {
						Object[] resultValue = dialog.getResult();
						if (0 < resultValue.length) {
							Object profile = resultValue[0];
							if (profile instanceof Profile) {
								object = ((Profile) profile).getName();
							} else if (profile instanceof IRegisteredProfile) {
								object = ((IRegisteredProfile) profile).getName();
							}
						}
					}
					return object;
				}

				@Override
				public Object edit(Control widget, Object object) {
					if (object instanceof String) {
						Object newValue = null;
						ProfileExplorerDialog dialog = new ProfileExplorerDialog(widget.getShell(), (String) object); // $NON-NLS-1$

						int result = dialog.open();
						if (result == Window.OK) {
							Object[] resultValue = dialog.getResult();
							if (0 < resultValue.length) {
								Object profile = resultValue[0];
								if (profile instanceof Profile) {
									newValue = ((Profile) profile).getName();
								} else if (profile instanceof IRegisteredProfile) {
									newValue = ((IRegisteredProfile) profile).getName();
								}
							}
						}
						return newValue;
					}
					return null;
				}

				@Override
				public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
					return objectsToValidate;
				}

			};
		}

		return null != valueFactory ? valueFactory : super.getValueFactory(propertyPath);
	}

	/**
	 * Observable value for {@link StereotypeQualifyNameObsevableValue}. Used to set Required Profile.
	 */
	public class StereotypeQualifyNameObsevableValue extends EMFObservableValue {

		/**
		 * Constructor.
		 *
		 * @param eObject
		 *            The object.
		 * @param domain
		 *            The editing domain.
		 */
		public StereotypeQualifyNameObsevableValue(final EObject eObject, final EditingDomain domain) {
			super(eObject, ApplyStereotypeAdvicePackage.eINSTANCE.getStereotypeToApply_StereotypeQualifiedName(), domain);
		}

		/**
		 * {@inheritDoc}
		 * <br>
		 * Add required profile needed when set stereotype qualify name.
		 * 
		 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableValue#doSetValue(java.lang.Object)
		 *
		 * @param value
		 *            The value to set.
		 */
		@Override
		protected void doSetValue(final Object value) {
			Command command = null;

			if (value instanceof String) {
				// Set the qualify name value as usual.
				command = new CompoundCommand("set value"); //$NON-NLS-1$

				((CompoundCommand) command).append(getSetCommand(value));

				// Add profile to required profile
				if (source instanceof StereotypeToApply && !((String) value).isEmpty()) {
					String stereotypeQualifiedName = ((StereotypeToApply) source).getStereotypeQualifiedName();

					String oldProfile = "";//$NON-NLS-1$
					if (null != stereotypeQualifiedName && stereotypeQualifiedName.contains(NamedElementUtil.QUALIFIED_NAME_SEPARATOR)) {// $NON-NLS-1$
						oldProfile = stereotypeQualifiedName.split(NamedElementUtil.QUALIFIED_NAME_SEPARATOR)[0];// $NON-NLS-1$
					}

					EList<String> requiredProfiles = ((StereotypeToApply) source).getRequiredProfiles();
					// Take the first element which is the Profile name
					String profile = ((String) value).split(NamedElementUtil.QUALIFIED_NAME_SEPARATOR)[0];// $NON-NLS-1$
					// The profile name have changed
					if (!oldProfile.equals(profile)) {
						// Delete old value from list
						if (requiredProfiles.contains(oldProfile)) {
							Command removeCommand = new RemoveCommand(getDomain(), requiredProfiles, oldProfile);
							((CompoundCommand) command).append(removeCommand);
						}
						// Add new value to list
						Command addCommand = new AddCommand(getDomain(), requiredProfiles, profile);
						((CompoundCommand) command).append(addCommand);
					}
				}

			}
			domain.getCommandStack().execute(command);
		}
	}

}
