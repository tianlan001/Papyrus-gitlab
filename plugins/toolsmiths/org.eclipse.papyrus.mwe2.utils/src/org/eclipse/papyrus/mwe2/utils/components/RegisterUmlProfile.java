/*******************************************************************************
 * Copyright (c) 2014 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid <juan.cadavid@cea.fr> implementation
 ******************************************************************************/
package org.eclipse.papyrus.mwe2.utils.components;


import java.lang.reflect.Field;

import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.eclipse.papyrus.mwe2.utils.messages.Messages;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPlugin;

import com.google.common.base.Strings;

/**
 * This MWE component registers the EPackage definition of a UML profile in the global EPackage registry.
 * An example of its usage is:
 * 
 * <pre>
 * component = org.eclipse.papyrus.mwe.utils.components.RegisterUmlProfile {
 *     profileSlot = 'ecoreprofile'
 * }
 * </pre>
 * 
 * where profileSlot is a slot that contains the uml Profile object to register.
 * Or, for a statically generated profile:
 * 
 * <pre>
 * component = org.eclipse.papyrus.mwe.utils.components.RegisterUmlProfile {
 *     profileSlot = 'utp'
 *     generatedPackageInterfaceName = 'org.eclipse.upr.utp.UTPPackage'
 * }
 * </pre>
 * 
 * where profileSlot is a slot that contains the uml Profile object to register.
 */
public class RegisterUmlProfile extends AbstractWorkflowComponent {
	private org.apache.commons.logging.Log log = LogFactory.getLog(getClass());


	private String profileSlot;

	private String generatedPackageInterfaceName;

	public String getProfileSlot() {
		return profileSlot;
	}

	public void setProfileSlot(String profileSlot) {
		this.profileSlot = profileSlot;
	}

	public String getGeneratedPackageInterfaceName() {
		return generatedPackageInterfaceName;
	}

	public void setGeneratedPackageInterfaceName(String generatedPackageInterfaceName) {
		this.generatedPackageInterfaceName = generatedPackageInterfaceName;
	}

	@Override
	public void checkConfiguration(Issues issues) {
		if (profileSlot == null || profileSlot.equals("")) { //$NON-NLS-1$
			issues.addError(Messages.RegisterUmlProfile_1);
		}
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		EObject eObject = (EObject) ctx.get(profileSlot);
		if (!(eObject instanceof Profile)) {
			log.error(Messages.RegisterUmlProfile_2);
			return;
		}

		Profile profile = (Profile) eObject;

		if (!Strings.isNullOrEmpty(getGeneratedPackageInterfaceName())) {
			// It's a statically generated Profile
			try {
				Class<?> clazz = ResourceLoaderFactory.createResourceLoader().loadClass(getGeneratedPackageInterfaceName());
				if (clazz == null) {
					throw new ClassNotFoundException(getGeneratedPackageInterfaceName());
				}

				Class<? extends EPackage> packageInterface = clazz.asSubclass(EPackage.class);
				Field eNS_URI = packageInterface.getDeclaredField("eNS_URI"); //$NON-NLS-1$
				String nsURI = (String) eNS_URI.get(null);
				UMLPlugin.getEPackageNsURIToProfileLocationMap().put(nsURI, EcoreUtil.getURI(profile));
			} catch (Exception e) {
				log.error(String.format("Failed to register generated profile %s", getGeneratedPackageInterfaceName()), e);
			}
		} else {
			// It's a dynamically defined profile
			EPackage definition = profile.getDefinition();
			if (definition == null) {
				log.error(Messages.RegisterUmlProfile_3);
				return;
			}
			EPackage.Registry.INSTANCE.put(definition.getNsURI(), definition);
		}
	}



}
