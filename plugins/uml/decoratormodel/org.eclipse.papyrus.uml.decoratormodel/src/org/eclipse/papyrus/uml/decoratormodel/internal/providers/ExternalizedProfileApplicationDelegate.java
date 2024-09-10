/*****************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation, Embarcadero Technologies, CEA, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - initial API and implementation
 *   Kenn Hussey (Embarcadero Technologies) - 271470
 *   Kenn Hussey - 323181, 348433
 *   Kenn Hussey (CEA) - 327039, 369492, 313951, 163556, 418466, 447901
 *   Christian W. Damus (CEA) - 300957, 431998
 *   Christian W. Damus - 444588, 399859, 451557
 *   Christian W. Damus - bug 458197
 *   Christian W. Damus - bug 481302
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *   Pauline DEVILLE - Bug 529707
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.providers;

import static com.google.common.collect.Iterables.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.uml.decoratormodel.helper.DecoratorModelUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate;
import org.eclipse.papyrus.uml.tools.helper.ProfileApplicationDelegatePreferenceInitializer;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * An implementation of the profile-application delegate protocol for externalized profile applications.
 */
public class ExternalizedProfileApplicationDelegate implements IProfileApplicationDelegate {

	public static final String PREFERENCE_CONSTANT = ProfileApplicationDelegatePreferenceInitializer.PREFERENCE_CONSTANT_FOR_DEFAULT;

	private final PrivateUtil util = new PrivateUtil();

	public ExternalizedProfileApplicationDelegate() {
		super();
	}

	@Override
	public boolean appliesTo(Package package_) {
		return true;
	}

	@Override
	public Iterable<ProfileApplication> getProfileApplications(Package package_) {
		return Iterables.concat(package_.getProfileApplications(), DecoratorModelUtils.getDecoratorModelProfileApplications(package_));
	}

	@Override
	public ProfileApplication getProfileApplication(Package package_, Profile profile) {
		ProfileApplication result = package_.getProfileApplication(profile);

		if (result == null) {
			for (ProfileApplication next : DecoratorModelUtils.getDecoratorModelProfileApplications(package_)) {
				if (next.getAppliedProfile() == profile) {
					result = next;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public EList<EObject> applyProfile(Package package_, Profile profile, Package context, IProgressMonitor monitor) {
		// Create the new profile application and then "reapply" it in place
		ProfileApplication profileApplication;
		if (!DecoratorModelUtils.isDecoratorModel(context) || EcoreUtil.isAncestor(context, package_)) {
			// The context is meaningless to me or we are creating an externalized profile application
			profileApplication = package_.createProfileApplication();
		} else {
			Package decorator = DecoratorModelUtils.getDecoratorPackage(context, package_, true);
			profileApplication = decorator.createProfileApplication();
		}

		profileApplication.setAppliedProfile(profile);

		return util.reapplyProfile(package_, profileApplication, monitor);
	}

	@Override
	public boolean appliesTo(ProfileApplication profileApplication) {
		return true;
	}

	@Override
	public Package getApplyingPackage(ProfileApplication profileApplication) {
		Package result = profileApplication.getApplyingPackage();

		Resource resource = (result == null) ? null : result.eResource();
		if (resource != null) {
			if (DecoratorModelUtils.isDecoratorModel(resource)) {
				result = DecoratorModelUtils.getUserModelApplyingPackage(profileApplication);
			}
		} else {
			// Unloaded? See whether we can still remember the user-model package
			Package userModel = DecoratorPackageCache.getUserModelPackage(result);
			if (userModel != null) {
				result = userModel;
			}
		}

		return result;
	}

	@Override
	public Profile getAppliedProfile(ProfileApplication profileApplication) {
		return profileApplication.getAppliedProfile();
	}

	@Override
	public EList<EObject> reapplyProfile(Package package_, Profile profile, IProgressMonitor monitor) {
		ProfileApplication existing = getProfileApplication(package_, profile);
		return (existing == null) ? ECollections.<EObject> emptyEList() // Nothing to do
				: util.reapplyProfile(package_, existing, monitor); // Our extended UML case
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate#getPreferenceConstant()
	 *
	 * @return
	 */
	@Override
	public String getPreferenceConstant() {
		return PREFERENCE_CONSTANT;
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate#getPreferenceLabel()
	 *
	 * @return
	 */
	@Override
	public String getPreferenceLabel() {
		return "External stereotype application tool";
	}

	//
	// Nested types
	//

	// extending UML-internal PackageOperations class to access protected API
	@SuppressWarnings("restriction")
	private class PrivateUtil extends org.eclipse.uml2.uml.internal.operations.PackageOperations {

		EList<EObject> reapplyProfile(Package package_, ProfileApplication profileApplication, IProgressMonitor monitor) {
			Profile profile = ExternalizedProfileApplicationDelegate.this.getAppliedProfile(profileApplication);
			EPackage definition = profile.getDefinition();

			monitor = SubMonitor.convert(monitor, NLS.bind("Re-applying profile \"{0}\" to package \"{1}\"", UMLLabelInternationalization.getInstance().getLabel(profile), UMLLabelInternationalization.getInstance().getLabel(package_)),
					IProgressMonitor.UNKNOWN);

			Collection<EObject> originals = Lists.newArrayList();
			StereotypeApplicationCopier copier = new PrivateStereotypeCopier(profile);

			// First, update the referenced definition of profile applications and copy all stereotype applications that are still applicable
			for (TreeIterator<EObject> allContents = getAllContents(package_, true, false); allContents.hasNext();) {
				EObject eObject = allContents.next();

				if (eObject instanceof Element) {
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}

					Element element = (Element) eObject;

					if (element instanceof Package) {
						for (ProfileApplication next : ExternalizedProfileApplicationDelegate.this.getProfileApplications((Package) element)) {
							if (ExternalizedProfileApplicationDelegate.this.getAppliedProfile(next) == profile) {
								EList<EObject> references = getEAnnotation(profileApplication, UML2_UML_PACKAGE_2_0_NS_URI, true).getReferences();

								if (references.isEmpty()) {
									references.add(definition);
								} else {
									references.set(0, definition);
								}
							}
						}
					}

					for (EObject stereotypeApplication : element.getStereotypeApplications()) {
						Stereotype stereotype = getStereotype(stereotypeApplication);

						if ((stereotype != null) && (stereotype.getProfile() == profile)) {
							if (element.isStereotypeApplicable(stereotype)) {
								EObject copy = copier.copy(stereotypeApplication);
								Resource eResource = stereotypeApplication.eResource();

								if (eResource != null) {
									EList<EObject> contents = eResource.getContents();

									if (eResource == copy.eResource()) {
										contents.move(contents.indexOf(stereotypeApplication), copy);
									} else {
										contents.set(contents.indexOf(stereotypeApplication), copy);
									}
								}
							}

							originals.add(stereotypeApplication);
						}
					}
				}
			}

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			// Copy cross-references within the stereotype application forest that was copied
			copier.copyReferences();

			// Propagate object IDs of stereotype applications and update all incoming cross-references from objects that were not copied
			for (EObject key : copier.keySet()) {
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}

				EObject copy = copier.get(key);

				Resource eResource = key.eResource();

				if (eResource instanceof XMLResource) {
					XMLResource xmlResource = (XMLResource) eResource;
					xmlResource.setID(copy, xmlResource.getID(key));
				}

				for (Setting setting : new ArrayList<EStructuralFeature.Setting>(
						getNonNavigableInverseReferences(key))) {

					EObject eObject = setting.getEObject();

					if (!copier.containsKey(eObject)) {
						EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();

						if ((eStructuralFeature != null) && eStructuralFeature.isChangeable()) {
							if (eStructuralFeature.isMany()) {
								Object value = eObject.eGet(eStructuralFeature);

								@SuppressWarnings("unchecked")
								EList<EObject> list = ((EList<EObject>) value);
								list.set(list.indexOf(key), copy);
							} else {
								setting.set(copy);
							}
						}
					}
				}
			}

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			// Delete all of the original stereotype applications (leaving only the copies)
			destroyAll(originals);

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			// Easy result in the common case of the profile not having any required metaclass extensions
			return profile.getOwnedExtensions(true).isEmpty() ? ECollections.<EObject> emptyEList() : applyAllRequiredStereotypes(package_);
		}

		//
		// Nested types
		//

		private class PrivateStereotypeCopier extends StereotypeApplicationCopier {
			private static final long serialVersionUID = 1L;

			private Set<Profile> extendedProfiles;

			PrivateStereotypeCopier(Profile profile) {
				super(profile);
			}

			/**
			 * Extends the superclass implementation to additionally look into implicitly
			 * referenced profiles to handle the case of a dynamic profile extending
			 * a static profile (bug 481302).
			 * 
			 * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=481302">bug 481302</a>
			 */
			@Override
			protected ENamedElement getDefinition(NamedElement element) {
				ENamedElement result = super.getDefinition(element);

				if (result == null) {
					for (Profile next : getExtendedProfiles()) {
						if (EcoreUtil.isAncestor(next, element)) {
							result = next.getDefinition(element);
							if (result != null) {
								break;
							}
						}
					}
				}

				return result;
			}

			protected Set<Profile> getExtendedProfiles() {
				if (extendedProfiles == null) {
					extendedProfiles = Sets.newHashSet();
					computeExtendedProfiles(profile, extendedProfiles);
				}
				return extendedProfiles;
			}

			protected void computeExtendedProfiles(Profile profile, Set<Profile> result) {
				for (TreeIterator<EObject> iter = profile.eAllContents(); iter.hasNext();) {
					EObject next = iter.next();
					if (next instanceof Stereotype) {
						for (Stereotype super_ : filter(((Stereotype) next).getSuperClasses(), Stereotype.class)) {
							Profile supersProfile = super_.containingProfile();
							if ((supersProfile != null) && (supersProfile != profile)) {
								result.add(supersProfile);
							}
						}
					} else if (next instanceof Profile) {
						// It has its own applications
						iter.prune();
					} else if (!(next instanceof Package)) {
						// Only descend into non-profile packages to look for more stereotypes
						iter.prune();
					}
				}
			}
		}
	}
}
