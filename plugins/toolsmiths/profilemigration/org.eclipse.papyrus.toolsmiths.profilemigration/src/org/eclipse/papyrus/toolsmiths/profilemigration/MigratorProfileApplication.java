/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 538928
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 539181
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.toolsmiths.profilemigration.factory.IMigratorFactory;
import org.eclipse.papyrus.toolsmiths.profilemigration.factory.MigratorFactory;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.extensionPoint.AtomicMigratorRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.AtomicMigratorComparator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.DifferenceTreeBuilder;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.ICompositeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.ProfileMigrationToolConfigurationDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferencePage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

/**
 * The class is used to migrated the model to the new version of the profile
 */
public class MigratorProfileApplication {

	/**
	 * The profiled model which is migrated
	 */
	public static Package profiledModel;

	/**
	 * The applied profile which is currently use for the migration
	 */
	public static Profile appliedProfile;

	/**
	 * The comparison of the two profile (output of EMF compare)
	 */
	public static Comparison comparison;

	/**
	 * The list of profile applied during the migration
	 */
	public static Set<Profile> newAppliedProfile = new HashSet<>();

	/**
	 * The list of stereotype applied during the migration
	 */
	public static List<EObject> newStereotypeApplication = new ArrayList<>();

	/**
	 * the list of atomic migration detected between the two files
	 */
	protected List<IAtomicMigrator> atomicList;

	/**
	 * the list of composite migration detected between the two files
	 */
	protected List<ICompositeMigrator> compositeList;

	/**
	 * The list of file already open which is keep to avoid asking the user to select the file each time
	 */
	private static Map<String, String> cacheProfileToFile = new HashMap<>();

	/**
	 * Constructor.
	 *
	 */
	public MigratorProfileApplication() {
		atomicList = new ArrayList<>();
		compositeList = new ArrayList<>();
	}

	/**
	 * Reapply the profile on the package_
	 *
	 * @param package_
	 *            package owning the stereotype application
	 * @param profile
	 *            the profile which must be reapply
	 * @return the list of newly applied stereotype
	 */
	public List<EObject> reapplyProfile(Package package_, Profile profile) {
		Resource profileAfterResource = profile.eResource();

		String path = null;
		Resource profileBeforeResource = null;

		if (!isAlreadyInCached(profile, profileAfterResource)) {
			ProfileMigrationToolConfigurationDialog dialog = new ProfileMigrationToolConfigurationDialog(getActiveShell(), profile.getQualifiedName());
			int dialogResult = dialog.open();
			switch (dialogResult) {
			case Window.OK:
				path = dialog.getFilePath();
				if (path != null && path != "") { //$NON-NLS-1$
					URI uri = URI.createFileURI(path);
					ResourceSet profileBeforeResourceSet = new ResourceSetImpl();
					profileBeforeResource = profileBeforeResourceSet.getResource(uri, true);
					saveNewFileInCache(path, profileBeforeResource);
				}
				break;
			case ProfileMigrationToolConfigurationDialog.SKIP:
				package_.applyProfile(profile);
				return newStereotypeApplication;
			case Window.CANCEL:
			default:
				return newStereotypeApplication;
			}
		} else {
			try {
				path = cacheProfileToFile.get(((XMIResource) profileAfterResource).getID(profile));
				URI uri = URI.createFileURI(path);
				ResourceSet profileBeforeResourceSet = new ResourceSetImpl();
				profileBeforeResource = profileBeforeResourceSet.getResource(uri, true);
			} catch (WrappedException e) {
				// File is not found so restart reapply profile with asking the user
				removeFilePathFromCached(path);
				reapplyProfile(package_, profile);
			}
		}
		if (path != null && profileBeforeResource != null) {
			migrateNewAppliedProfile(package_, profile, profileBeforeResource, profileAfterResource);
		}

		return newStereotypeApplication;
	}

	/**
	 * Migrate package_ to the new version of profile
	 *
	 * @param package_
	 *            the package to migrate
	 * @param profile
	 *            the new profile
	 * @param profileBeforeResource
	 *            the resource corresponding to the profile before modification
	 * @param profileAfterResource
	 *            the resource corresponding to the new profile
	 * @return the migrated package
	 */
	public Package migrateNewAppliedProfile(Package package_, Profile profile, Resource profileBeforeResource, Resource profileAfterResource) {
		profiledModel = package_;
		appliedProfile = profile;
		newAppliedProfile.clear();

		try {
			// 1] initialize treeNode and registry (save all necessary data)
			TreeNode rootTreeNode = getTreeNode(profileBeforeResource, profileAfterResource);
			if (rootTreeNode != null) {
				new StereotypeApplicationRegistry(rootTreeNode, profiledModel);

				// 2] migrate
				migrateNewAppliedProfile(profiledModel, rootTreeNode, profile, true);
			} else {
				// this is the case where there is no modification in the profile
				profiledModel.applyProfile(profile);
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}
		return profiledModel;
	}

	/**
	 * This method is used to get the treeNode from to files (before and after)
	 *
	 * @param before
	 *            the resource for the model before modification
	 * @param after
	 *            the resource for the model after modification
	 * @return the treeNode base on modifications
	 */
	protected TreeNode getTreeNode(Notifier before, Notifier after) {
		// get comparison model
		DefaultComparisonScope scope = new DefaultComparisonScope(after, before, null);
		EMFCompare comparator = EMFCompare.builder().build();
		comparison = comparator.compare(scope);

		// get TreeNode
		DifferenceTreeBuilder builder = new DifferenceTreeBuilder(comparison);
		TreeNode differenceTree = builder.buildMatchTree();

		return differenceTree;
	}

	/**
	 * This method migrate to model to the new version of the profile
	 *
	 * @param model
	 *            the profiled model to migrate
	 * @param treeNode
	 *            the treeNode corresponding to differences between the profile before and after modifications
	 * @param profile
	 *            the concerned profile
	 * @param shouldReapply
	 *            true if we have to reapply the profile, false otherwise
	 */
	protected void migrateNewAppliedProfile(Package model, TreeNode treeNode, Profile profile, boolean shouldReapply) {
		// 1] clear lists
		atomicList.clear();
		compositeList.clear();

		// 2] initialize lists
		initAtomicList(treeNode, MigratorFactory.INSTANCE);
		initCompositeList();
		postProcessing();

		// 3] reapply profile if it is necessary
		if (shouldReapply) {
			model.applyProfile(appliedProfile);
		}

		// 4] migrate the initialize atomicList
		for (IAtomicMigrator atomic : atomicList) {
			atomic.migrationAction();
		}

		// 5] migrate for newly applied profiles (when move into new profile)
		for (Profile newProfile : newAppliedProfile) {
			newAppliedProfile.remove(newProfile);
			migrateNewAppliedProfile(model, treeNode, newProfile, false);
		}
	}

	/**
	 * Initialize the list of atomic migration
	 *
	 * @param treeNode
	 */
	protected void initAtomicList(TreeNode treeNode, IMigratorFactory migratorFactory) {
		atomicList.addAll(migratorFactory.instantiateMigrator(treeNode));
		for (TreeNode childNode : treeNode.getChildren()) {
			initAtomicList(childNode, migratorFactory);
		}
		atomicList.sort(new AtomicMigratorComparator());
	}

	/**
	 * Remove every erased migrator (erased by extension point)
	 */
	private void postProcessing() {
		List<IAtomicMigrator> toRemove = new ArrayList<>();
		for (AtomicMigratorRegistry.Descriptor descriptor : AtomicMigratorRegistry.INSTANCE.getRegistry()) {
			for (String replacement : descriptor.getErasedMigrators()) {
				for (IAtomicMigrator migrator : atomicList) {
					if (!(toRemove.contains(migrator)) && migrator.getClass().getName().equals(replacement)) {
						toRemove.add(migrator);
					}
				}
			}
		}
		atomicList.removeAll(toRemove);
	}


	/**
	 * Initialize the list of atomic migration from the list of atomic migration
	 */
	protected void initCompositeList() {
		// TODO [Composite migration] initialize this list of composite migration, a composite migration is composed of atomic migration, all atomic migration which compose it shall be remove from the atomic list
	}

	private static Shell getActiveShell() {
		Display display = Display.getDefault();
		Shell result = display.getActiveShell();

		if (result == null) {
			Shell[] shells = display.getShells();
			for (Shell shell : shells) {
				if (shell.getShells().length == 0) {
					result = shell;
				}
			}
		}

		return result;
	}

	/**
	 * Check if there is already the registered file for this profile and update cacheProfileToFile property accordingly
	 *
	 * @param profile
	 *            the migrated profile
	 * @param profileAfterResource
	 *            the resource associate to tjis migrated profile
	 * @return true if the profile has already an associated file in the cache
	 */
	private boolean isAlreadyInCached(Profile profile, Resource profileAfterResource) {
		if (cacheProfileToFile.containsKey(((XMIResource) profileAfterResource).getID(profile))) {
			String path = cacheProfileToFile.get(((XMIResource) profileAfterResource).getID(profile));
			if (ProfileMigrationPreferencePage.getCachedFiles().contains(path)) {
				return true;
			} else {
				cacheProfileToFile.remove(((XMIResource) profileAfterResource).getID(profile));
				return false;
			}
		}
		return false;
	}

	/**
	 * Add all profiles contain by the file to the cache
	 *
	 * @param path
	 *            the filePath
	 */
	private void saveNewFileInCache(String path, Resource profileBeforeResource) {
		TreeIterator<EObject> iter = profileBeforeResource.getAllContents();
		while (iter.hasNext()) {
			EObject object = iter.next();
			if (object instanceof Package) {
				if (object instanceof Profile) {
					cacheProfileToFile.put(((XMIResource) profileBeforeResource).getID(object), path);
					if (!ProfileMigrationPreferencePage.getCachedFiles().contains(path)) {
						ProfileMigrationPreferencePage.addFile(path);
					}
				}
			} else {
				iter.prune();
			}
		}
	}

	/**
	 * Remove every reference to the given path in the cacheProfileToFile
	 */
	private void removeFilePathFromCached(String path) {
		Iterator<Entry<String, String>> it = cacheProfileToFile.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			if (entry.getValue().equals(path)) {
				ProfileMigrationPreferencePage.getCachedFiles().remove(path);
				it.remove();
			}
		}
	}
}
