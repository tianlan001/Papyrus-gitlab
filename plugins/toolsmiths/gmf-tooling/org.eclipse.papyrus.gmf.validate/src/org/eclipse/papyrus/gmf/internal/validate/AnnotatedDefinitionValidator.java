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
 *    Artem Tikhomirov (Borland) - [230418] non-containment contexts; refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.gmf.internal.validate.Annotations.Meta;
import org.eclipse.papyrus.gmf.internal.validate.DefUtils.ContextTypeAdapter;
import org.eclipse.papyrus.gmf.internal.validate.DefUtils.ExpressionContextProvider;
import org.eclipse.papyrus.gmf.internal.validate.DefUtils.ExpressionTypeProvider;
import org.eclipse.papyrus.gmf.internal.validate.DefUtils.LookupByNameContextProvider;
import org.eclipse.papyrus.gmf.internal.validate.DefUtils.TypedElementProvider;
import org.eclipse.papyrus.gmf.internal.validate.IDefElementProvider.ContextProvider;
import org.eclipse.papyrus.gmf.internal.validate.IDefElementProvider.StringValProvider;
import org.eclipse.papyrus.gmf.internal.validate.IDefElementProvider.TypeProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.EnvironmentProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.ExpressionProviderRegistry;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpression;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpressionProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IParseEnvironment;
import org.eclipse.papyrus.gmf.internal.validate.expressions.NoProviderExpression;
import org.eclipse.papyrus.gmf.internal.validate.ocl.OCLExpressionProvider;


// FIXME: replace all methods with Map<Object, Object> context with dedicated "ValidateSession" class
// that keeps track of all the caching stuff
public class AnnotatedDefinitionValidator extends AbstractValidator implements EValidator {
	
	public AnnotatedDefinitionValidator() {		
	}
	
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}
	
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate(eObject.eClass(), eObject, diagnostics, context);
	}

	/**
	 * @see EObjectValidator#validate(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */	
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if(eObject.eClass().getEPackage() == EcorePackage.eINSTANCE) {
			if(eObject instanceof EModelElement) {
				return validateMetaModel((EModelElement)eObject, diagnostics, context);
			}
		} else {
			return validateModel(eObject, diagnostics, context);
		}
		return true;
	}
	
	
	protected boolean validateModel(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		EReference[] constrainedFeatures = collectConstrainedFeatures(eObject.eClass());
		if (constrainedFeatures == null || constrainedFeatures.length == 0) {
			return true;
		}
		boolean result = true;
		for (EReference sf : constrainedFeatures) {
			if (!eObject.eIsSet(sf)) {
				continue;
			}
			if (sf.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> constraintObject = (List<EObject>) eObject.eGet(sf, true);
				for (EObject o : constraintObject) {
					result &= validateInstance(eObject, sf, o, diagnostics, context);
				}
			} else {
				EObject constraintObject = (EObject) eObject.eGet(sf, true);
				result = constraintObject != null && validateInstance(eObject, sf, constraintObject, diagnostics, context);
			}
		}
		return result;
	}

	private boolean validateInstance(EObject context, EReference contextFeature, EObject constraint, DiagnosticChain diag, Map<Object, Object> validationContext) {
		ValueSpecDef def = getDefinition(constraint.eClass(), diag, null, validationContext);
		if(def == null) {
			SubstitutionLabelProvider lp = getLabelProvider(validationContext);
			String message = String.format("Object '%s', supposed to be a constraint for feature '%s' of '%s', is missing essential constraint metainformation", lp.getObjectLabel(constraint), lp.getFeatureLabel(contextFeature), lp.getObjectLabel(context));
			diag.add(new BasicDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, StatusCodes.INVALID_CONSTRAINT_CONTEXT, message, new Object[] {context} ));
			return false;
		} else if(!def.isOK()) {
			return false;
		}

		String lang = def.createLanguage(constraint);

		if(!(Annotations.Meta.OCL_KEY.equals(lang) || Annotations.REGEXP_KEY.equals(lang) || Annotations.NEG_REGEXP_KEY.equals(lang))) {
			// add support for other languages here
			return true;
		}
		ContextData contextData = getContextBinding(context.eClass(), contextFeature, validationContext);
		if(contextData == null) {
			diag.add(new BasicDiagnostic(
				Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 
				StatusCodes.NO_VALUESPEC_CONTEXT_AVAILABLE, 
				NLS.bind(Messages.def_NoContextAvailable, getLabelProvider(validationContext).getObjectLabel(context)),
				new Object[] { context }));
			return false;			
		}
		
		EClassifier evaluationContextClass = contextData.contextClass.getContextClassifier(context);
		if(evaluationContextClass == null) {
			String noCtxMessage = contextData.contextClass.getStatus().isOK() ?
				NLS.bind(Messages.def_NoContextAvailable, getLabelProvider(validationContext).getObjectLabel(context))
				: contextData.contextClass.getStatus().getMessage();
			
				diag.add(new BasicDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 
				StatusCodes.NO_VALUESPEC_CONTEXT_AVAILABLE,
				noCtxMessage, new Object[] { context } ));
		}

		String body = def.createBody(constraint);
		if(body != null && evaluationContextClass != null) {
			// get real environment
			IParseEnvironment env = null;
			if(contextData.environment != null) {
				env = EnvironmentProvider.createParseEnv();
				ExternModelImport modelImports = ExternModelImport.getImporter(validationContext);
				env.setImportRegistry(modelImports.getPackageRegistry());
				for (String varName : contextData.environment.keySet()) {
					TypeProvider typeProvider = contextData.environment.get(varName);
					EClassifier type = typeProvider.getType(context);
					if(type != null) {
						// TODO - produce error status as no variable type is available
						env.setVariable(varName, type);
					}
				}
			}
			
			IModelExpression expression = getExpression(lang, body, evaluationContextClass, env);
			if(!expression.getStatus().isOK()) {
				String message = MessageFormat.format(
						Messages.invalidExpressionBody, 
						new Object[] { expression.getBody(), 
						expression.getStatus().getMessage() });				
				diag.add(new BasicDiagnostic(
					Diagnostic.ERROR, DIAGNOSTIC_SOURCE, 
					StatusCodes.INVALID_CONSTRAINT_EXPRESSION, 
					message, new Object[] { context }));				
				return false;
			}
			
			EObject typeResolutionContext = context;
			// check type restriction on the given expression
			TypeProvider typeProvider = def.getTypeRestriction();
			if(typeProvider == null) {
				typeProvider = getTypeInfo(contextFeature, context.eClass(), diag, validationContext);
			}
			if(typeProvider != null && typeProvider.getStatus().isOK() && expression.getResultType() != null) {
				if(!typeProvider.isAssignable(typeResolutionContext, expression)) {
					EClassifier type = typeProvider.getType(typeResolutionContext);
					IStatus s = DefUtils.getIncompatibleTypesStatus(type, expression.getResultType());
					diag.add(DefUtils.statusToDiagnostic(s, DIAGNOSTIC_SOURCE, context));
					return false;
				}
			}
		}
		
		return true;
	}

	private static IModelExpression getExpression(String language, String body, EClassifier context, IParseEnvironment env) {
		IModelExpressionProvider provider = ExpressionProviderRegistry.getInstance().getProvider(language);
		if (provider == null) {
			return new NoProviderExpression(language, body, context);
		}
		
		return provider.createExpression(body, context, env);
	}

	public ContextData getContextBinding(EClass contextClass, EStructuralFeature featureToConstraint, Map<Object, Object> validationContext) {
		ContextData contextData = getCachedCtxBinding(featureToConstraint, validationContext);
		if(contextData != null) {
			return contextData;
		}

		ContextProvider contextProvider = getContextClass(featureToConstraint, validationContext);
		if(contextProvider != null) {
			ContextData newContextData = new ContextData(contextProvider, getEnvProvider(featureToConstraint, getExpressionFactory(validationContext)));
			registerCtxBinding(featureToConstraint, newContextData, validationContext);				

			if(Trace.shouldTrace(DebugOptions.META_DEFINITIONS)) {
				String msgPtn = "[context-def] {0} binding: {1}::{2}"; //$NON-NLS-1$
				Trace.trace(MessageFormat.format(msgPtn, 
					new Object[] { 
						newContextData.contextClass.toString(), 
						LabelProvider.INSTANCE.getObjectLabel(contextClass),
						LabelProvider.INSTANCE.getFeatureLabel(featureToConstraint)
					}));
			}
			
			return newContextData;
		}
		return null;
	}
		
	protected boolean validateMetaModel(EModelElement modelElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		EAnnotation annotation = (modelElement instanceof EAnnotation) ? (EAnnotation)modelElement : null;		
		if(annotation != null) {
			if(!Annotations.CONSTRAINTS_META_URI.equals(annotation.getSource())) {
				return true;
			} 
			modelElement = annotation.getEModelElement();
			if(modelElement == null) {
				return true;
			}
		}
		
		if(modelElement instanceof EStructuralFeature && annotation != null && Meta.CONTEXT.equals(annotation.getDetails().get(Meta.DEF_KEY))) {			
			EStructuralFeature sfeature = (EStructuralFeature)modelElement;
			
			ContextProvider contextProvider = getContextClass(sfeature, context);
			if(contextProvider != null) {
				// check extended context environment
				getEnvProvider(sfeature, getExpressionFactory(context));
				if(!contextProvider.getStatus().isOK()) {
					DefUtils.mergeAndFlatten(contextProvider.getStatus(), DIAGNOSTIC_SOURCE, annotation, diagnostics);
					return false;
				}
			}
		} else if(modelElement instanceof EClass) {
			getDefinition((EClass)modelElement, diagnostics, null, context);
		}
		
		return true;
	}	

	private static OCLExpressionProvider getExpressionFactory(Map<Object, Object> validationContext) {
		OCLExpressionProvider p = (OCLExpressionProvider) validationContext.get(OCLExpressionProvider.class);
		if (p == null) {
			validationContext.put(OCLExpressionProvider.class, p = new OCLExpressionProvider());
		}
		return p;
	}

	public static ContextProvider getContextClass(EStructuralFeature bindFeature, Map<Object, Object> validationContext) {
		EClass resolutionContext = bindFeature.getEContainingClass();
		ExternModelImport modelImports = ExternModelImport.getImporter(validationContext);
		return DefUtils.getContextClass(resolutionContext, getExpressionFactory(validationContext), bindFeature, modelImports.getPackageRegistry());
	}

	private static ValueSpecDef getDefinition(EClass metaClass, DiagnosticChain diagnostics, DefData data, Map<Object, Object> context) {
		ValueSpecDef definition = findDefinition(metaClass, context);
		if(definition != null) {
			return definition;
		}

		if(data == null) {		
			for (EAnnotation nextAnnotation : metaClass.getEAnnotations()) {
				if(Annotations.CONSTRAINTS_META_URI.equals(nextAnnotation.getSource())) {
					String val = nextAnnotation.getDetails().get(Meta.DEF_KEY);
					if(val != null && (val.equals(Meta.VALUESPEC) || val.equals(Meta.CONSTRAINT))) {
						data = new DefData();
						data.metaKey = val;	
						data.defClass = metaClass;
						break;						
					}
				} 
			}
		}				
		
		EList<EClass> superTypes = metaClass.getESuperTypes();		
		if(data == null && superTypes.isEmpty()) {
			return null;
		}
		
		if(data != null) {
			OCLExpressionProvider expressionFactory = getExpressionFactory(context);
			for (EStructuralFeature nextAttr : metaClass.getEStructuralFeatures()) {
				for (EAnnotation annotation : nextAttr.getEAnnotations()) {
					if(!Annotations.CONSTRAINTS_META_URI.equals(annotation.getSource())) {
						continue;
					}
					String metaValue = annotation.getDetails().get(Meta.DEF_KEY);				
					if(data.body == null) {				
						if(Meta.BODY.equals(metaValue)) {						
							data.body = new DefUtils.FeatureValProvider(nextAttr);
							checkAndReportProblems(data.body, annotation, diagnostics);
						}
					}
					
					if(data.lang == null) {				
						if(Meta.LANG.equals(metaValue)) {
							data.lang = new DefUtils.FeatureValProvider(nextAttr);
							checkAndReportProblems(data.lang, annotation, diagnostics);			
						}
					}
					
					if(data.context == null) {
						if(Meta.CONTEXT.equals(metaValue)) {
							String ctxExpression = annotation.getDetails().get(Meta.OCL_KEY);
							if(ctxExpression != null) {
								data.context = new ExpressionContextProvider(expressionFactory.createExpression(ctxExpression, metaClass));
								checkAndReportProblems(data.context, annotation, diagnostics);								
							}							
						}
					}
					
					if(data.type == null) {
						if(Meta.TYPE.equals(metaValue)) {
							String typeExpr = annotation.getDetails().get(Meta.OCL_KEY);  						
							if(typeExpr != null) {
								data.type = new ExpressionTypeProvider(expressionFactory.createExpression(typeExpr, metaClass));					
							} else {
								data.type = new TypedElementProvider(nextAttr);
							}
							checkAndReportProblems(data.type, annotation, diagnostics);
						}
					}
				} // end of EAttribute annotations iteration
			}
		
			if(data.type == null) {
				data.type = getTypeInfo(metaClass, metaClass, diagnostics, context);
/*				EAnnotation typeAnnotation = DefUtils.getAnnotationWithKey(metaClass, Annotations.CONSTRAINTS_META_URI, Annotations.Meta.OCL_KEY);				
				if(typeAnnotation != null && Meta.TYPE.equals(typeAnnotation.getDetails().get(Meta.DEF_KEY))) {
					String typeExpr = (String)typeAnnotation.getDetails().get(Meta.OCL_KEY);
					if(typeExpr != null) {
						data.type = new ExpresssionTypeProvider(getExpression(Meta.OCL_KEY, typeExpr, metaClass, context));
						checkAndReportProblems(data.type, typeAnnotation, diagnostics);
					}
				}*/
			}
	
			if(data.body != null) {
				definition = data.createDefinition();
				assert data.defClass != null;						
				registerDefinition(data.defClass, definition, context);
				return definition;
			}
		}
				
		for (EClass superClass : superTypes) {
			ValueSpecDef inheritedDef = getDefinition(superClass, diagnostics, data, context);
			if(inheritedDef != null) {
				if(data == null) {
					data = new DefData();
				}
				data.inheritFrom(metaClass, inheritedDef);				
				registerDefinition(data.defClass, data.createDefinition(), context);				
				return inheritedDef;
			}
		}
		
		if(data != null) {
			if(data.body == null) {
				String message = MessageFormat.format(Messages.def_MissingBodyAnnotation, new Object[] { LabelProvider.INSTANCE.getObjectLabel(metaClass) }); 
				// report missing body
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, StatusCodes.MISSING_VALUESPEC_BODY_ANNOTATION, message, new Object[] { metaClass }));
				return null;
			}
		}
		return null;
	}
	
	private static TypeProvider getTypeInfo(EModelElement typeAnnotationSource, EClass resolutionContext, DiagnosticChain diagnostics, Map<Object, Object> validationContext) {
		TypeProvider typeProvider = null;		
		List<EAnnotation> annotations = DefUtils.getAnnotationsWithKeyAndValue(typeAnnotationSource, Annotations.CONSTRAINTS_META_URI, Annotations.Meta.DEF_KEY, Annotations.Meta.TYPE);
		
		EAnnotation typeAnnotation = annotations.isEmpty() ? null : annotations.get(0);		
		
		final OCLExpressionProvider exprFactory = getExpressionFactory(validationContext);
		final ExternModelImport modelImports = ExternModelImport.getImporter(validationContext);
		if(typeAnnotation != null && Meta.TYPE.equals(typeAnnotation.getDetails().get(Meta.DEF_KEY))) {
			String typeExprBody = typeAnnotation.getDetails().get(Meta.OCL_KEY);
			if(typeExprBody != null) {
				IModelExpression typeExpr = exprFactory.createExpression(typeExprBody, resolutionContext);					
				boolean usesTypeName = typeExpr.getStatus().isOK() && String.class.equals(typeExpr.getResultType().getInstanceClass());
				typeProvider = (usesTypeName) ? 
						new ContextTypeAdapter(new LookupByNameContextProvider(typeExpr, modelImports.getPackageRegistry())) : 
						new ExpressionTypeProvider(typeExpr);
				checkAndReportProblems(typeProvider, typeAnnotation, diagnostics);
			}
		}
		return typeProvider;
	}
	

	private static boolean checkAndReportProblems(IDefElementProvider defElementProvider, EObject destination, DiagnosticChain diagnostics) {
		if(!defElementProvider.getStatus().isOK()) {
			diagnostics.add(DefUtils.statusToDiagnostic(defElementProvider.getStatus(), DIAGNOSTIC_SOURCE, destination));
			return false;
		}
		return true;
	}

	private static Map<String, TypeProvider> getEnvProvider(EStructuralFeature contextBindFeature, OCLExpressionProvider exprFactory) {
		List<EAnnotation> varDefs = DefUtils.getAnnotationsWithKeyAndValue( 
				contextBindFeature, Annotations.CONSTRAINTS_META_URI, 
				Annotations.Meta.DEF_KEY, Annotations.Meta.VARIABLE);
		if(varDefs.isEmpty()) {
			return null;
		}
		
		Map<String, TypeProvider> env = null;
		for (EAnnotation nextVarAnnotation : varDefs) {
			TypeProvider type = null;
			String typePrefix = Annotations.Meta.TYPE + "."; //$NON-NLS-1$
			Map.Entry<String, String> typeExpression = DefUtils.getKeyPrefixAnnotation(nextVarAnnotation, typePrefix);
			if(typeExpression != null) {
				String body = typeExpression.getValue();
				if(body == null) {
					// TODO - report missing var type status
				} else {
					IModelExpression expression = exprFactory.createExpression(body, contextBindFeature.getEContainingClass());
					type = new DefUtils.ExpressionTypeProvider(expression);
				}						
			} else {
				// TODO - report missing var type status
			}
			
			String name = nextVarAnnotation.getDetails().get(Annotations.Meta.NAME);
			if(name == null) {
				//TODO - report missing var name status
				continue;
			}
			if(env == null) {
				env = new HashMap<String, TypeProvider>();
			}
			env.put(name, type);
		}
		return env;
	}
	
	private static void registerDefinition(EClass eClass, ValueSpecDef definition, Map<Object, Object> context) {
		assert definition != null;
		assert eClass != null;
		@SuppressWarnings("unchecked")
		Map<EClass, ValueSpecDef> defMap = (Map<EClass, ValueSpecDef>) context.get(ValueSpecDef.class);
		if(defMap == null) {
			defMap = new HashMap<EClass, ValueSpecDef>();
			context.put(ValueSpecDef.class, defMap);
		}
		defMap.put(eClass, definition);
	}

	private static ValueSpecDef findDefinition(EClass eClass, Map<Object, Object> context) {
		Map<?,?> defMap = (Map<?,?>)context.get(ValueSpecDef.class);
		return (defMap != null) ? (ValueSpecDef) defMap.get(eClass) : null;
	}	
	
	private static class DefData {
		String metaKey;
		EClass defClass;
		StringValProvider body;
		StringValProvider lang;
		TypeProvider type;
		ContextProvider context;
		public DefData() {}		

		ValueSpecDef createDefinition() {
			assert body != null;
			ValueSpecDef valueSpecDef = Meta.CONSTRAINT.equals(metaKey) ? 
				new ConstraintDef(body, lang) :
				new ValueSpecDef(body, lang, type);

			if(Trace.shouldTrace(DebugOptions.META_DEFINITIONS)) {
				String msgPtn = "[{0}] {1} type: {2}"; //$NON-NLS-1$
				Trace.trace( MessageFormat.format(msgPtn, new Object[] { metaKey, defClass.getName(), type }));
			}
			return valueSpecDef;
		}

		void inheritFrom(EClass valueSpecEClass, ValueSpecDef superDef) {
			defClass = valueSpecEClass;
			if(body == null) {
				body = superDef.getBody();
			}
			if(lang == null) {
				lang = superDef.getLang();
			}
			if(type == null) {
				type = superDef.getTypeRestriction();
			}		
		}
	}
	
	private static class ContextData {		
		final ContextProvider contextClass;
		final Map<String, TypeProvider> environment;
		public ContextData(ContextProvider contextProvider, Map<String, TypeProvider> environment) {	
			this.contextClass = contextProvider;
			this.environment = environment;
		}				
	}

	private static ContextData getCachedCtxBinding(EModelElement modelElement, Map<Object, Object> context) {
		if(context != null) {
			Map<?,?> bindMap = (Map<?,?>)context.get(ContextProvider.class);
			if(bindMap != null) {
				return (ContextData) bindMap.get(modelElement);				
			}
		}
		if(Trace.shouldTrace(DebugOptions.DEBUG)) {
			Trace.trace("Performance warning: Validation should run in a context for caching"); //$NON-NLS-1$
		}		
		return null;
	}

	private static void registerCtxBinding(EStructuralFeature contextDefOwner, ContextData contextData, Map<Object, Object> context) {
		if(context != null) {
			@SuppressWarnings("unchecked")
			Map<EStructuralFeature, ContextData> bindMap = (Map<EStructuralFeature, ContextData>)context.get(ContextProvider.class);
			if(bindMap == null) {
				bindMap = new HashMap<EStructuralFeature, ContextData>();
				context.put(ContextProvider.class, bindMap);
			}
			bindMap.put(contextDefOwner, contextData);
		}
	}

	// may cache features per class, but doesn't seem too expensive to calculate 'em
	private static EReference[] collectConstrainedFeatures(EClass eClass) {
		LinkedList<EReference> result = new LinkedList<EReference>();
		for (EReference sf : eClass.getEAllReferences()) {
			for (EAnnotation a: sf.getEAnnotations()) {
				if (Annotations.CONSTRAINTS_META_URI.equals(a.getSource()) && Annotations.Meta.CONTEXT.equals(a.getDetails().get(Annotations.Meta.DEF_KEY))) {
					result.add(sf);
					break; // recognize no more than one def="context"
				}
			}
		}
		if (result.isEmpty()) {
			return null;
		}
		return result.toArray(new EReference[result.size()]);
	}
}
