/******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean
 *****************************************************************************/
package metamodel;

import org.eclipse.papyrus.gmf.codegen.gmfgen.DynamicModelAccess
import com.google.inject.Inject
import xpt.Common
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage

@com.google.inject.Singleton class Facility {

	@Inject extension Common;
	@Inject extension Facility_qvto;

	def className(DynamicModelAccess it)'''«it.className»'''
	def packageName (DynamicModelAccess it)'''«it.packageName»'''
	def qualifiedClassName (DynamicModelAccess it)'''«packageName(it)».«className(it)»'''
	def fullPath (DynamicModelAccess it)'''«qualifiedClassName(it)»'''

	def Main (DynamicModelAccess it)'''
	package «packageName(it)»;

	public class «className(it)» {

		«FOR p : it.editorGen.getAllDomainGenPackages(false)»
			«field(p, it)»
		«ENDFOR»
		«generatedMemberComment»
		private final org.eclipse.emf.ecore.EPackage ePackage;

		«generatedMemberComment»
		private «className(it)»(org.eclipse.emf.ecore.EPackage ePackage) {
			this.ePackage = ePackage;
		}

		«FOR p : it.editorGen.getAllDomainGenPackages(false)»
			«getMethod(p, it)»
		«ENDFOR»
	
		«getRegistryMethod»

		«generatedMemberComment»«/*NOTE, when metaClassName is 'EObject', isSuperTypeOf is unlikely (see bug #255469) ever to return true (it lookes into metamodel, and unless obj's EClass has explicit EObject in its superclasses, the method simply returns false). But unless it's requested, I don't want to complicate this piece of code*/»
		public boolean isInstance(Object obj, String metaClassName) {
			org.eclipse.emf.ecore.EClass metaClass = getEClass(metaClassName);
			return obj instanceof org.eclipse.emf.ecore.EObject && metaClass.isSuperTypeOf(((org.eclipse.emf.ecore.EObject) obj).eClass());
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EObject newInstance(String metaClassName) {
			return this.ePackage.getEFactoryInstance().create(getEClass(metaClassName));
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EClass getEClass(String metaClassName) {
			«_assert('metaClassName != null')»
			org.eclipse.emf.ecore.EClass metaClass = findMetaClass(metaClassName);
			assertMetaClass(metaClass, metaClassName);
			return metaClass;
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EStructuralFeature getEFeature(String metaClassName, String metaFeatureName) {
			«_assert('metaFeatureName != null')»
			org.eclipse.emf.ecore.EClass metaClass = getEClass(metaClassName);
			org.eclipse.emf.ecore.EStructuralFeature f = metaClass.getEStructuralFeature(metaFeatureName);
			assertMetaFeature(f, metaClass, metaFeatureName);
			return f;
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EAttribute getEAttribute(String metaClassName, String metaFeatureName) {
			return (org.eclipse.emf.ecore.EAttribute) getEFeature(metaClassName, metaFeatureName);
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EReference getEReference(String metaClassName, String metaFeatureName) {
			return (org.eclipse.emf.ecore.EReference) getEFeature(metaClassName, metaFeatureName);
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EDataType getEDataType(String metaClassName) {
			«_assert('metaClassName != null')»
			org.eclipse.emf.ecore.EClassifier c = this.ePackage.getEClassifier(metaClassName);
			if (false == c instanceof org.eclipse.emf.ecore.EDataType) {
				return null;
			}
			return (org.eclipse.emf.ecore.EDataType) c;
		}

		«generatedMemberComment»
		public org.eclipse.emf.ecore.EEnum getEEnum(String metaClassName) {
			«_assert('metaClassName != null')»
			org.eclipse.emf.ecore.EClassifier c = this.ePackage.getEClassifier(metaClassName);
			if (false == c instanceof org.eclipse.emf.ecore.EEnum) {
				return null;
			}
			return (org.eclipse.emf.ecore.EEnum) c;
		}

		«generatedMemberComment»
		private org.eclipse.emf.ecore.EClass findMetaClass(String name) {
			org.eclipse.emf.ecore.EClassifier c = this.ePackage.getEClassifier(name);
			if (false == c instanceof org.eclipse.emf.ecore.EClass) {
				return null;
			}
			return (org.eclipse.emf.ecore.EClass) c;
		}

		«generatedMemberComment('Default implementation throws IllegalStateException if metaclass is null')»
		private void assertMetaClass(org.eclipse.emf.ecore.EClass metaClass, String metaClassName) {
			if (metaClass == null) {
				throw new IllegalStateException(java.text.MessageFormat.format("Can't find class {0} in the package {1}", new Object[] {metaClassName, this.ePackage.getName()}));
			}
		}

	«generatedMemberComment('Default implementation throws IllegalStateException if meta-feature is null')»
	private void assertMetaFeature(org.eclipse.emf.ecore.EStructuralFeature metaFeature, org.eclipse.emf.ecore.EClass metaClass, String featureName) {
		if (metaFeature == null) {
			throw new IllegalStateException(java.text.MessageFormat.format("Can't find feature {0} of class {1} in the package {2}", new Object[] {featureName, metaClass.getName(), this.ePackage.getName()}));
		}
		}
		«FOR p : it.editorGen.getAllDomainGenPackages(false)»
			«initMethod(p)»
		«ENDFOR»
	}
'''


	def field(GenPackage it, DynamicModelAccess dma)'''
		«generatedMemberComment»
		private static «className(dma)» «fieldName(it)»;
	'''

	def getMethod(GenPackage it, DynamicModelAccess dma)'''
		«generatedMemberComment('@throws IllegalStateException if no EPackage with given URI is registered.')»
		public static «className(dma)» get«getNameToken(it)»() {
			if («fieldName(it)» == null) {
				org.eclipse.emf.ecore.EPackage pkg = getRegistry().getEPackage("«getEcorePackage().nsURI»");
				if (pkg == null) {
					throw new IllegalStateException("Package «getEcorePackage().name»(«getEcorePackage().nsURI») not found");
				}
				«fieldName(it)» = new «className(dma)»(pkg);
				«fieldName(it)».init«getNameToken(it)»();
			}
			return «fieldName(it)»;
		
	'''

	def initMethod(GenPackage it)'''
		«generatedMemberComment»
		private void init«getNameToken(it)»() {
		}
	'''

	def getRegistryMethod(DynamicModelAccess it)'''
		«generatedMemberComment('Default implementation returns global registry, clients that need another may redefine.')»
		private static org.eclipse.emf.ecore.EPackage.Registry getRegistry() {
			return org.eclipse.emf.ecore.EPackage.Registry.INSTANCE;
		}
	'''
}