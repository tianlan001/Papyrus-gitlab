/*****************************************************************************
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.uml2.common.util.UML2Util.getValidJavaIdentifier;
import static org.eclipse.xtext.xbase.lib.StringExtensions.toFirstLower;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * @author damus
 *
 */
class GeneratorParametersBlock {

	private static final String IDENTIFIER_PREFIX = "identifierPrefix"; //$NON-NLS-1$

	private final GeneratorWizardModel model;

	private Text idText;

	public GeneratorParametersBlock(GeneratorWizardModel model) {
		super();

		this.model = model;
	}

	public void createControl(Composite parent) {
		new Label(parent, SWT.NONE).setText("Identifier:");
		idText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		idText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		idText.setText(getDefaultIdentifier());
		model.setIdentifier(getIdentifier());

		idText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				model.setIdentifier(getIdentifier());
				model.validatePage();
			}
		});
	}

	private String getIdentifier() {
		return idText.getText().trim();
	}

	void save() {
		String identifier = getIdentifier();
		if (!getDefaultIdentifier().equals(identifier)) {
			// Only a custom default prefix if the user deviated from the default
			final String suffix = "." + getDefaultQualifier(); //$NON-NLS-1$
			if (identifier.endsWith(suffix)) {
				// This looks like a custom prefix
				model.getDialogSettings().put(IDENTIFIER_PREFIX, identifier.substring(0, identifier.length() - suffix.length()));
			}
		}
	}

	private String getDefaultIdentifier() {
		return String.format("%s.%s", getDefaultIdentifierPrefix(), getDefaultQualifier());
	}

	private String getDefaultQualifier() {
		return Joiner.on('.').join(Iterables.transform(Splitter.on(NamedElement.SEPARATOR).split(model.getProfile().getQualifiedName()), new Function<String, String>() {
			@Override
			public String apply(String input) {
				return toFirstLower(getValidJavaIdentifier(input));
			}
		}));
	}

	private String getDefaultIdentifierPrefix() {
		String result = model.getDialogSettings().get(IDENTIFIER_PREFIX);
		Profile profile = model.getProfile();

		Stereotype ePackage = profile.getApplicableStereotype(String.format("%s::%s", UMLUtil.PROFILE__ECORE, UMLUtil.STEREOTYPE__E_PACKAGE)); //$NON-NLS-1$
		if ((ePackage != null) && profile.isStereotypeApplied(ePackage)) {
			String basePackage = (String) profile.getValue(ePackage, UMLUtil.TAG_DEFINITION__BASE_PACKAGE);
			if (!isNullOrEmpty(basePackage)) {
				result = basePackage;
			}
		}

		if (result == null) {
			result = "com.example"; //$NON-NLS-1$
		}

		return result;
	}
}
