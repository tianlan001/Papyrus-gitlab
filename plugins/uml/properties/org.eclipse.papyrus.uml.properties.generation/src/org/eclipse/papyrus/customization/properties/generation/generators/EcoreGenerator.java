/*****************************************************************************
 * Copyright (c) 2010, 2021  CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Thibault Le Ouay t.leouay@sherpa-eng.com - Strategy improvement of generated files
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 544869
 *  Christian W. Damus - bug 573987
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.customization.properties.generation.Activator;
import org.eclipse.papyrus.customization.properties.generation.messages.Messages;
import org.eclipse.papyrus.customization.properties.generation.wizard.widget.FileChooser;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.views.properties.root.PropertiesRoot;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * An IGenerator to create Property view contexts from an Ecore metamodel FIXME
 * : The generator doesn't seem to keep the Metaclass inheritance
 *
 * @author Camille Letavernier
 */
public class EcoreGenerator extends AbstractQVTGenerator {

	private FileChooser sourceFileChooser;

	protected EPackage ecorePackage;

	protected List<EPackage> listEPackages;

	@Override
	public void createControls(Composite parent, IFile workbenchSelection) {
		Composite root = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		root.setLayout(layout);

		Label sourceLabel = new Label(root, SWT.NONE);
		sourceLabel.setText(Messages.EcoreGenerator_source);
		GridData data = new GridData();
		data.widthHint = 100;
		sourceLabel.setLayoutData(data);

		sourceFileChooser = new FileChooser(root, false);
		sourceFileChooser.setFilterExtensions(new String[] { "ecore" }); //$NON-NLS-1$
		sourceFileChooser.addListener(this);

		listEPackages = new ArrayList<>();

		if (workbenchSelection != null) {
			IFile suggestion = getSourceFile(workbenchSelection);
			if (suggestion != null) {
				sourceFileChooser.setFile(suggestion);
			}
		}
	}

	@Override
	public String getDescription() {
		return Messages.EcoreGenerator_ecoreGeneratorDescription;
	}

	@Override
	public boolean isReady() {
		return sourceFileChooser.getFilePath() != null;
	}

	@Override
	public String getName() {
		return Messages.EcoreGenerator_ecoreGeneratorName;
	}

	@Override
	public boolean isSelectedSingle(Property property) {
		EStructuralFeature feature = getFeature(property);
		if (feature == null) {
			return false;
		}

		if (feature.isDerived()) {
			return false;
		}

		if (!feature.isChangeable()) {
			return false;
		}

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (reference.isContainer() || reference.isContainment()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retrieve the EStructuralFeature corresponding to the given property
	 *
	 * @param property
	 * @return The EStructuralFeature corresponding to the given property
	 */
	protected EStructuralFeature getFeature(Property property) {
		List<String> path = getPath(property);
		path.remove(0); // Root = EPackage

		EPackage currentPackage = ecorePackage;

		EClassifier classifier = findClassifier(path, currentPackage);
		if (classifier == null) {
			return null;
		}

		if (!(classifier instanceof EClass)) {
			return null;
		}

		EClass eClass = (EClass) classifier;
		return eClass.getEStructuralFeature(property.getName());
	}

	/**
	 * Retrieve the Classifier corresponding to the given path, in the given
	 * EPackage
	 *
	 * @param path
	 *            The list of package and subpackages names, and the classifier
	 *            name, i.e. the list of segments in the classifier's qualified
	 *            name
	 * @param source
	 *            The root EPackage in which the classifier should be retrieved
	 * @return The corresponding EClassifier, or null if it couldn't be
	 *         retrieved
	 */
	protected EClassifier findClassifier(List<String> path, EPackage source) {
		String qualifier = path.get(0);
		EClassifier classifier = source.getEClassifier(qualifier);
		if (classifier == null) {
			source = findSubPackage(source, qualifier);
			if (source == null) {
				return null;
			}
			path.remove(0);
			return findClassifier(path, source);
		} else {
			return classifier;
		}
	}

	/**
	 * Retrieve the subpackage corresponding to the given packageName, in the
	 * given package
	 *
	 * @param currentPackage
	 *            The EPackage in which the subpackage should be found
	 * @param packageName
	 *            The name of the EPackage to find
	 * @return The corresponding EPackage, or null if it couldn't be found
	 */
	protected EPackage findSubPackage(EPackage currentPackage, String packageName) {
		for (EPackage pack : currentPackage.getESubpackages()) {
			if (pack.getName().equals(packageName)) {
				return pack;
			}
		}
		return null;
	}

	private List<String> getPath(Property property) {
		List<String> result = getPath(property.getContextElement());
		return result;
	}

	private List<String> getPath(DataContextElement element) {
		List<String> result;
		if (element.getPackage() == null) {
			result = new LinkedList<>();
		} else {
			result = getPath(element.getPackage());
		}

		result.add(element.getName());
		return result;
	}

	@Override
	public boolean isSelectedMultiple(Property property) {
		if (!isSelectedSingle(property)) {
			return false;
		}

		EStructuralFeature feature = getFeature(property);

		Set<String> validDataTypes = new HashSet<>(Arrays.asList(new String[] { "int", "boolean", "float", "double" })); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		if (feature.getEType() instanceof EDataType) {
			if (validDataTypes.contains(((EDataType) feature.getEType()).getInstanceTypeName())) {
				return true;
			}
		}

		if (feature.getEType() instanceof EEnum) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSelectedSingle(Property property, DataContextElement element) {
		return isSelectedSingle(property);
	}

	@Override
	public boolean isSelectedMultiple(Property property, DataContextElement element) {
		return isSelectedMultiple(property);
	}

	@Override
	protected URI getTransformationURI() {
		return URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/transforms/ecore2datacontext.qvto", true); //$NON-NLS-1$
	}


	@Override
	protected List<ModelExtent> getModelExtents() {
		LinkedList<ModelExtent> result = new LinkedList<>();
		ModelExtent temp = new BasicModelExtent();
		ModelExtent inPackage = new BasicModelExtent(Collections.singletonList(ecorePackage));



		PropertiesRoot root = ConfigurationManager.getInstance().getPropertiesRoot();
		ModelExtent inRoot = new BasicModelExtent(Collections.singletonList(root));
		if (!listEPackages.isEmpty()) {
			temp.setContents(listEPackages);
			if (!listEPackages.contains(ecorePackage)) {
				result.add(temp); // if the root package isnt selected
			} else {
				result.add(inPackage);
			}
			result.add(temp);

		} else {
			// Basic Method
			result.add(inPackage);
			result.add(inPackage);
		}

		result.add(inRoot);
		result.add(getOutContextExtent());
		return result;

	}

	@Override
	public IObservableValue getObservableValue() {
		return sourceFileChooser.getObservableValue();
	}

	@Override
	public List<Object> getExternalReference() {

		URI packageURI = URI.createPlatformResourceURI(sourceFileChooser.getFilePath(), true);

		try {
			ecorePackage = (EPackage) loadEMFModel(packageURI);
		} catch (IOException e) {
			// nothing
		}

		EcoreUtil.resolveAll(ecorePackage);

		List<Object> listePackage = new ArrayList<>();
		if (!listePackage.contains(ecorePackage)) {
			listePackage.add(ecorePackage);
		}

		TreeIterator<Notifier> tree = ecorePackage.eResource().getResourceSet().getAllContents();
		while (tree.hasNext()) {
			Notifier next = tree.next();
			if (!(next instanceof EObject)) {
				continue;
			}

			EObject object = (EObject) next;
			if (object instanceof EStructuralFeature) {
				EStructuralFeature feature = (EStructuralFeature) object;
				EClass eClass = feature.getEContainingClass();
				if (eClass != null) {
					EClassifier classifier = feature.getEType();
					EPackage targetPackage = null;
					if (classifier != null) {
						targetPackage = classifier.getEPackage();
					}
					if (targetPackage != null) {
						if (!ecorePackage.equals(targetPackage)) {
							if (!listePackage.contains(targetPackage)) {
								listePackage.add(targetPackage);
							}

						}
					}
				}
			}
			if (object instanceof EClass) {

				EClass eclass = (EClass) object;
				List<EClass> list = eclass.getESuperTypes();
				for (EClass item : list) {
					if (!listePackage.contains(item.getEPackage())) {
						final EPackage epackage = item.getEPackage();
						if (null != epackage && !listePackage.contains(item.getEPackage())) {
							listePackage.add(item.getEPackage());
						} else if (null == epackage) {
							Activator.log.warn(NLS.bind("The EPackage for {0} can't be resolved.", item)); //$NON-NLS-1$
						}
					}

				}
			}

		}

		return listePackage;
	}

	@Override
	public void addCheckElement(Object obj) {

		if (obj instanceof EPackage) {
			EPackage pack = (EPackage) obj;
			listEPackages.add(pack);
		}

	}


	@Override
	protected List<ModelExtent> getModelExtents(int i) {
		EPackage currentPackage = listEPackages.get(i);
		try {

			ModelExtent inPackage = new BasicModelExtent(Collections.singletonList(currentPackage));
			PropertiesRoot root = ConfigurationManager.getInstance().getPropertiesRoot();
			ModelExtent inRoot = new BasicModelExtent(Collections.singletonList(root));
			LinkedList<ModelExtent> result = new LinkedList<>();
			result.add(inPackage);
			result.add(inPackage);
			result.add(inRoot);
			result.add(getOutContextExtent());

			return result;

		} catch (Exception ex) {
			return null;

		}

	}

	@Override
	public IFile getSourceFile(IFile file, IContentType contentType) {
		if (contentType == null) {
			return null; // Ecore content type is registered, so the file is not an Ecore model
		}

		IContentType ecore = Platform.getContentTypeManager().getContentType(EcorePackage.eCONTENT_TYPE);

		return ecore != null && contentType.isKindOf(ecore) ? file : null;
	}

}
