/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - Refactoring package/profile import/apply UI for CDO
 *  Christian W. Damus (CEA) - bug 323802
 *  Dr. David H. Akehurst - enable programmatic registration
 *****************************************************************************/
package org.eclipse.papyrus.uml.importt.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.extensionpoints.Registry;
import org.eclipse.papyrus.uml.extensionpoints.profile.FilteredRegisteredProfilesAsLibrarySelectionDialog;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.utils.Util;
import org.eclipse.papyrus.uml.profile.ui.dialogs.ElementImportTreeSelectionDialog.ImportSpec;
import org.eclipse.papyrus.uml.profile.ui.dialogs.ProfileTreeSelectionDialog;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;



public class ImportRegisteredProfileHandler extends AbstractImportHandler {

	@Override
	protected ICommand getGMFCommand(final IEvaluationContext context) {
		return new ImportProfileCommand(context);
	}


	/**
	 *
	 * The command to import profiles in the the model
	 *
	 */
	public class ImportProfileCommand extends AbstractImportCommand {

		/**
		 *
		 * Constructor.
		 *
		 * @param editingDomain
		 *            the editing domain
		 */
		public ImportProfileCommand(final IEvaluationContext context) {
			super(new Runnable() {

				public void run() {
					// Retrieve shell instance
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

					// get the set of registered libraries available
					IRegisteredProfile[] allProfiles = Registry.getRegisteredProfiles().toArray(new IRegisteredProfile[0]);

					// Open Registered ModelLibrary selection dialog
					FilteredRegisteredProfilesAsLibrarySelectionDialog dialog = new FilteredRegisteredProfilesAsLibrarySelectionDialog(shell, true, allProfiles, getImportedProfiles());
					dialog.open();
					if (Window.OK == dialog.getReturnCode()) {
						// get the result, which is the set of libraries to import
						List<Object> profilesToImport = Arrays.asList(dialog.getResult());
						importProfiles(profilesToImport.toArray(new IRegisteredProfile[profilesToImport.size()]));
					}
				}
			}, context, "Import Profile", "Import Profile from Registred Profiles"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Import the profiles in the model.
	 *
	 * @param profilesToImport
	 *            the profiles to import
	 */
	protected void importProfiles(IRegisteredProfile[] profilesToImport) {

		// create a temporary resource set. Be sure to unload it so that we don't leak models in the CacheAdapter!
		ResourceSet resourceSet = Util.createTemporaryResourceSet();
		try {
			for (int i = 0; i < profilesToImport.length; i++) {
				IRegisteredProfile currentLibrary = (profilesToImport[i]);
				URI modelUri = currentLibrary.getUri();

				Resource modelResource = resourceSet.getResource(modelUri, true);
				// PackageImportTreeSelectionDialog dialog = new PackageImportTreeSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ((Package)modelResource.getContents().get(0)));
				ProfileTreeSelectionDialog dialog = new ProfileTreeSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ((Package) modelResource.getContents().get(0)));
				int ret = dialog.open();

				if (ret == Window.OK) {
					Collection<ImportSpec<Profile>> result = dialog.getResult();
					Iterator<ImportSpec<Profile>> resultIter = result.iterator();
					while (resultIter.hasNext()) {
						Package element = resultIter.next().getElement();
						PackageImport ei = UMLFactory.eINSTANCE.createPackageImport();
						ei.setImportedPackage(element);

						// we import only once an element :
						List<Package> importedPackages = ((Package) getSelectedElement()).getImportedPackages();
						if (!importedPackages.contains(element)) {
							((Package) getSelectedElement()).getPackageImports().add(ei);
						}
					}
				}
			}
		} finally {
			EMFHelper.unload(resourceSet);
		}
	}

	/**
	 * Returns the list of the profiles which are already imported
	 *
	 * @return
	 * 		the list of the profiles which are already imported
	 */
	protected Collection<IRegisteredProfile> getImportedProfiles() {
		List<IRegisteredProfile> profiles = new ArrayList<IRegisteredProfile>();
		IRegisteredProfile[] allLibraries = Registry.getRegisteredProfiles().toArray(new IRegisteredProfile[0]);
		for (int i = 0; i < allLibraries.length; i++) {
			IRegisteredProfile registeredProfile = allLibraries[i];
			List<String> importedPackageNames = PackageUtil.getImportedPackagesNames((Package) getSelectedElement());
			// problem: name of library might be different from name of top-level package
			if (importedPackageNames.contains(registeredProfile.getName())) {
				profiles.add(registeredProfile);
			}
		}
		return profiles;
	}
}
