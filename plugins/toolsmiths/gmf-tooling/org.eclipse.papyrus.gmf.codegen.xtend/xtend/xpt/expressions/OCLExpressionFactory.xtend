/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - API extracted to GMF-T runtime, migrated to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.expressions

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import plugin.Activator
import xpt.Common
import xpt.CodeStyle

@com.google.inject.Singleton class OCLExpressionFactory {
	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject Activator xptActivator;
	@Inject ExpressionAbstractExpression xptAbstractExpression;

	def className(GenExpressionInterpreter it) '''«it.className»'''

	def packageName(GenExpressionInterpreter it) '''«it.container.expressionsPackageName»'''

	def qualifiedClassName(GenExpressionInterpreter it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenExpressionInterpreter it) '''«qualifiedClassName(it)»'''

	def OCLExpressionFactory(GenExpressionInterpreter it) '''
		«copyright(it.container.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» {

			«initInterpreterFactory(it)»

			«generatedMemberComment('This is factory method, callers are responsible to keep reference to the return value if they want to reuse parsed expression')»
			public static «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» getExpression(String body, org.eclipse.emf.ecore.EClassifier context, java.util.Map<String, org.eclipse.emf.ecore.EClassifier> environment) {
				return new Expression(body, context, environment);
			}

			«generatedMemberComment('This method will become private in the next release')»«/* FIXME [MG] private or completely remove in the next release. Besides, no real need to pass emptyMap when null would suffice  */»
			public static «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» getExpression(String body, org.eclipse.emf.ecore.EClassifier context) {
				return getExpression(body, context, java.util.Collections.<String, org.eclipse.emf.ecore.EClassifier>emptyMap());
			}

			«generatedMemberComment»
			private static class Expression extends «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» {

				«generatedMemberComment»
				private final org.eclipse.ocl.ecore.OCL oclInstance;

				«generatedMemberComment»
				private org.eclipse.ocl.ecore.OCLExpression oclExpression;

				«generatedMemberComment»
				public Expression(String body, org.eclipse.emf.ecore.EClassifier context, java.util.Map<String, org.eclipse.emf.ecore.EClassifier> environment) {
					super(body, context);
					oclInstance = org.eclipse.ocl.ecore.OCL.newInstance();
					initCustomEnv(oclInstance.getEnvironment(), environment);
					«/*
					 * [artem]: I've moved expression initialization right into constructor because:
					 * - Caching and weak references done at the caller are more effective for this usecase (we have an instance/static field to reference this Expression anyway)
					 * - Expression's status is known right away, not only after protected getQuery get invoked
					 */»org.eclipse.ocl.ecore.OCL.Helper oclHelper = oclInstance.createOCLHelper();
					oclHelper.setContext(context());
					try {
						oclExpression = oclHelper.createQuery(body());
						setStatus(org.eclipse.core.runtime.IStatus.OK, null, null);
					} catch (org.eclipse.ocl.ParserException e) {
						setStatus(org.eclipse.core.runtime.IStatus.ERROR, e.getMessage(), e);
					}
				}

				«generatedMemberComment»
				«it.container.editorGen.diagram.overrideC»
				@SuppressWarnings("rawtypes")
				protected Object doEvaluate(Object context, java.util.Map env) {
					if (oclExpression == null) {
						return null;
					}
					// on the first call, both evalEnvironment and extentMap are clear, for later we have finally, below.
					org.eclipse.ocl.EvaluationEnvironment<?,?,?,?,?>  evalEnv = oclInstance.getEvaluationEnvironment();
					// initialize environment«/* Using Object below because Map env comes as raw type, and Object(unlike String) works fine for both Iterator<Type> = iterable.iterator() and for (Type a : iterable) code styles  */»
					for (Object nextKey : env.keySet()) {
						evalEnv.replace((String) nextKey, env.get(nextKey));
					}
					try {
						Object result = oclInstance.evaluate(context, oclExpression);
						return oclInstance.isInvalid(result) ? null : result;
					} finally {
						evalEnv.clear();
						oclInstance.setExtentMap(null); // clear allInstances cache, and get the oclInstance ready for the next call
					}
				}

				«generatedMemberComment»
				private static void initCustomEnv(org.eclipse.ocl.Environment<?,org.eclipse.emf.ecore.EClassifier,?,?,?,org.eclipse.emf.ecore.EParameter,?,?,?,?,?,?> ecoreEnv, java.util.Map<String, org.eclipse.emf.ecore.EClassifier> environment) {
					«initializeEnvironment(it, 'ecoreEnv')»
					for (String varName : environment.keySet()) {
						org.eclipse.emf.ecore.EClassifier varType = environment.get(varName);
						ecoreEnv.addElement(varName, createVar(ecoreEnv, varName, varType), false);
					}
				}

				«generatedMemberComment»
				private static org.eclipse.ocl.ecore.Variable createVar(org.eclipse.ocl.Environment<?,org.eclipse.emf.ecore.EClassifier,?,?,?,?,?,?,?,?,?,?> ecoreEnv, String name, org.eclipse.emf.ecore.EClassifier type) {
					org.eclipse.ocl.ecore.Variable var = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
					var.setName(name);
					var.setType(ecoreEnv.getUMLReflection().getOCLType(type));
					return var;
				}
			}
		}
	'''

	/**
	 * Clients may override if they don't need
	 */
	def initializeEnvironment(GenExpressionInterpreter it, String environmentVar) '''
		// Use EObject as implicit root class for any object, to allow eContainer() and other EObject operations from OCL expressions
		org.eclipse.ocl.options.ParsingOptions.setOption(«environmentVar», org.eclipse.ocl.options.ParsingOptions.implicitRootClass(«environmentVar»), org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getEObject());
	'''

	/**
	 * just to avoid identical piece of template in the RegexpExpressionFactory
	 */
	def CharSequence initInterpreterFactory(GenExpressionInterpreter it) '''
		«generatedMemberComment»
		private final «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)»[] expressions;

		«generatedMemberComment»
		private final String [] expressionBodies;

		«generatedMemberComment»
		protected «className»() {
			this.expressions = new «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)»[«expressions.size»];
			this.expressionBodies = new String[] {
					«FOR e : expressions»
						«e.bodyString», «nonNLS(1)»
					«ENDFOR»
				};
		}

		«generatedMemberComment»
		private static «className» getInstance() {
			«className» instance = «xptActivator.instanceAccess(it.container.editorGen)».get«className»();
			if (instance == null) {
				«xptActivator.instanceAccess(it.container.editorGen)».set«className»(instance = new «className»());
			}
			return instance;
		}

		«generatedMemberComment»
		public static String getExpressionBody(int index) {
			return getInstance().expressionBodies[index];
		}

		«/*
		 * - I'm not quite sure indexes instead of string bodies are good approach, may revisit this later.
		 * - There's no confidence we shouldn't keep Map/Set instead of fixed length array for expressions.
		 * The reason I didn't use Map/Set right away is uncertainty with the key - i.e. body only, body + context, or
		 * body+context+environment(how). I.e. if it's possible (reasonable) to have same body for different contexts -
		 * of course, 'self.oclIsUndefined()' may be run in different contexts, but it's useless example.
		 	* - DGMT and reuse of gmfgen::ValueExpressions might be related here - if we decide identical body is enough to
		 * reuse an expression (and change DGMT#bindToProvider accordingly), then the answer to previous point would become obvious (i.e. "body is enough")
		 */
		generatedMemberComment»
		public static «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» getExpression(int index, org.eclipse.emf.ecore.EClassifier context, java.util.Map<String, org.eclipse.emf.ecore.EClassifier> environment) {
			«className» cached = getInstance();
			if (index < 0 || index >= cached.expressions.length) {
				throw new IllegalArgumentException();
			}
			if (cached.expressions[index] == null) {
				cached.expressions[index] = getExpression(cached.expressionBodies[index], context, environment == null ? java.util.Collections.<String, org.eclipse.emf.ecore.EClassifier>emptyMap() : environment);
			}
			return cached.expressions[index];
		}
	'''
}
