/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.osgi.util.NLS;

public class ExternModelImport {
	private static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.gmf.validate.imports"; //$NON-NLS-1$ 
	private static final Object ROOT_TARGET_OBJECT_KEY = new Object(); 	
	
	private final ResourceSet importedModels;
	private final EPackage.Registry registry = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);
	private final HashSet<URI> myProcessedMetaModels = new HashSet<URI>();
	private final HashSet<EPackage> processedPackages;
	

	private ExternModelImport(EObject validatedObject) {
		this.importedModels = new ResourceSetImpl();
		Resource targetModel = validatedObject.eResource();		
		if(targetModel != null) {
			this.importedModels.setURIConverter(targetModel.getResourceSet().getURIConverter());
		}
		this.importedModels.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());
		this.processedPackages = new HashSet<EPackage>();
	}
	
	public static EValidator newImportValidator() {
		return new AbstractValidator() {

			public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
				ensureRootTargetInitialized(eObject, context);		
				return true;
			}	

			public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
				ensureRootTargetInitialized(eObject, context);
				ExternModelImport importer = getImporter(context);
				if(eObject instanceof EAnnotation) {
					return importer.processAnnotation((EAnnotation)eObject, diagnostics);
				}
				
				EPackage ePackage = eObject.eClass().getEPackage();
				if(!importer.hasPackageImportsProcessed(ePackage)) {
					return importer.processAnnotations(eObject.eClass().getEPackage(), diagnostics);
				}
				return true;
			}

			public boolean validate(EDataType dataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
				return true;
			}
		};
	}
	
	public static ExternModelImport getImporter(Map<Object, Object> context) {
		Object value = context.get(ExternModelImport.class);
		if(value instanceof ExternModelImport) {
			return (ExternModelImport)value;
		}
		assert value == null;
		EObject validationTarget = getRootTargetObject(context);
		ExternModelImport importer = new ExternModelImport(validationTarget);
		importer.initializeExternPackages(validationTarget);
		context.put(ExternModelImport.class, importer);
		return importer;
	}	
	
	/**
	 * @return The import package registry associated with the context or <code>null</code> if there is no such registry 
	 */
	public EPackage.Registry getPackageRegistry() {
		return registry;
	}
	
	boolean hasPackageImportsProcessed(EPackage importingPackage) {
		return processedPackages.contains(importingPackage);
	}
	
	private boolean processAnnotations(EPackage importingPackage, DiagnosticChain diagnostics) {
		boolean result = true;
		for (EAnnotation next : importingPackage.getEAnnotations()) {
			result &= processAnnotation(next, diagnostics);
		}
		processedPackages.add(importingPackage);
		return result;
	}
	
	private boolean processAnnotation(EAnnotation annotation, DiagnosticChain diagnostics) {
		if(Annotations.CONSTRAINTS_URI.equals(annotation.getSource())) {
			return processImportEAnnotation(annotation, diagnostics);
		}
		return true;
	}
	
	public void initializeExternPackages(EObject root) {
		Resource metaModelResource = root.eClass().getEPackage().eResource();
		if (metaModelResource != null && !myProcessedMetaModels.contains(metaModelResource.getURI())) {
			for (EObject nextResourceElement : metaModelResource.getContents()) {
				if (nextResourceElement instanceof EPackage) {
					registerLocally((EPackage) nextResourceElement);
				}
			}
			registerReferencedMetaModels(EcoreUtil.ExternalCrossReferencer.find(metaModelResource));
			myProcessedMetaModels.add(metaModelResource.getURI());
		}
		registerReferencedMetaModels(EcoreUtil.ExternalCrossReferencer.find(root));
	}

	static void ensureRootTargetInitialized(EObject target, Map<Object, Object> context) {
		if(context != null && !context.containsKey(ROOT_TARGET_OBJECT_KEY)) {
			context.put(ROOT_TARGET_OBJECT_KEY, EcoreUtil.getRootContainer(target, true)); 		
		}
	}

	private static EObject getRootTargetObject(Map<Object, Object> context) {
		Object rootObj = context.get(ROOT_TARGET_OBJECT_KEY);
		assert rootObj == null || rootObj instanceof EObject;
		return (EObject)rootObj;
	}
	
	private void registerReferencedMetaModels(Map<EObject, Collection<Setting>> externalCrossReferences) {
		for (EObject next : externalCrossReferences.keySet()) {
			EPackage nextPackage = null;
			if (next instanceof EClassifier) {
				nextPackage = ((EClassifier) next).getEPackage();
			} 
			else if(next instanceof EPackage) {
				nextPackage = (EPackage)next;
			}
			else if(next instanceof GenPackage) {
				nextPackage = ((GenPackage)next).getEcorePackage();				
			}
			else if(next instanceof GenClassifier) {
				GenClassifier genClassifier = (GenClassifier) next;
				if(genClassifier.getGenPackage() != null) {
					nextPackage = genClassifier.getGenPackage().getEcorePackage();
				}
			}
			if(nextPackage != null) {
				registerLocally(nextPackage);
			}
		}		
	}

	private void registerLocally(EPackage nextPackage) {
		// force the package to be initialized in registry, in case a package descriptor is registered
		// Note: this is required for successfull ocl environment lookup of EClassifiers from external meta-models
		registry.put(nextPackage.getNsURI(), registry.getEPackage(nextPackage.getNsURI()));
	}
	
	private boolean processImportEAnnotation(EAnnotation annotation, DiagnosticChain diagnostics) {
		boolean result = true;
		for (Map.Entry<String, String> nextEntry : annotation.getDetails()) {
			if(!nextEntry.getKey().equals(Annotations.Meta.IMPORT)) {
				continue;
			}			
			String importVal = nextEntry.getValue();
			if(importVal != null) {
				importVal = importVal.trim();
				EPackage p = registry.getEPackage(importVal);
				if (p != null) {
					registerLocally(p);
					return true;
				} 

				if (!loadAsResourceURI(importVal, annotation, diagnostics)) {
					result = false;
				}

			} else {
				result = false;				
				reportInvalidModelURI(importVal, annotation.getEModelElement(), diagnostics);					
			}			
		}
		return result;
	}
	
	/**
	 * @return false if load failed
	 */
	private boolean loadAsResourceURI(String importValue, EAnnotation annotation, DiagnosticChain diagnostics) {
		try {
			URI modelURI = URI.createURI(importValue);
			try {
				importModelFromResource(modelURI);
			} catch (RuntimeException e) {
				reportModelLoadingError(importValue, annotation.getEModelElement(), diagnostics, e);
				return false;
			}						
		} catch (IllegalArgumentException e) {
			reportInvalidModelURI(importValue, annotation.getEModelElement(), diagnostics);
			return false;
		}
		return true;
	}
	
	private static void reportInvalidModelURI(String modelURIValue, EModelElement annotatedElement, DiagnosticChain diagnostics) {
		Object destObj = DefUtils.findAnnotationDetailEntry(annotatedElement, 
				Annotations.CONSTRAINTS_URI, Annotations.Meta.IMPORT, modelURIValue);
		assert destObj != null;
		
		Diagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR,
				DIAGNOSTIC_SOURCE, StatusCodes.INVALID_MODEL_IMPORT_URI,
				NLS.bind(Messages.invalidModelImportUri, modelURIValue), new Object[] { destObj });
		diagnostics.add(diagnostic);
	}
	
	private static void reportModelLoadingError(String modelURIValue, EModelElement annotatedElement, DiagnosticChain diagnostics, RuntimeException error) {
		Object destObj = DefUtils.findAnnotationDetailEntry(annotatedElement, 
				Annotations.CONSTRAINTS_URI, Annotations.Meta.IMPORT, modelURIValue);
		assert destObj != null;
		
		String message = NLS.bind(Messages.modelImportResourceLoadingError, modelURIValue, error.getLocalizedMessage());		
		Diagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR,
				DIAGNOSTIC_SOURCE, StatusCodes.INVALID_MODEL_IMPORT_URI, 
				message, new Object[] { destObj });		
		diagnostics.add(diagnostic);
		GMFValidationPlugin.log(diagnostic.getSeverity(), message, error);
	}
	
	private boolean importModelFromResource(URI modelURI) throws RuntimeException {
		EList<EObject> contents = importedModels.getResource(modelURI, true).getContents();
		for (EObject nextObj : contents) {
			if(nextObj instanceof EPackage) {
				EPackage ePackage = (EPackage)nextObj;
				if(ePackage.getNsURI() != null) {				
					registerLocally(ePackage);
				}
				return true;
			}
		}
		return false;
	}
	
}
