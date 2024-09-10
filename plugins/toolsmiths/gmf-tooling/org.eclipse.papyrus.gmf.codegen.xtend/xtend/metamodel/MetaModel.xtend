/*****************************************************************************
 * Copyright (c) 2008, 2015, 2021 Anatoliy Tischenko, Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Anatoliy Tischenko - Initial API and implementation
 * Artem Tikhomirov (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package metamodel

import com.google.inject.Inject
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenClassImpl
import xpt.GenModelUtils_qvto

@com.google.inject.Singleton class MetaModel {

	@Inject extension MetaModel_qvto;
	@Inject extension GenModelUtils_qvto;

	/**
	 * Does instanceof check.
	 */
	def IsInstance(GenClass xptSelf, String accessor) '''«accessor» instanceof «getQualifiedInterfaceName(xptSelf)»'''

	/**
	 * Shorthand, negates IsInstance - handy if you consider
	 * different approaches for generated and dynamic models: false == instanceof
	 *  vs. !MetaModelFacility.isInstance
	 */
	def NotInstance(GenClass xptSelf, String accessor) '''false == «IsInstance(xptSelf, accessor)»'''

	/**
	 * Special kind of instanceof check, that compares eContainer() of the object.
	 * Since metaClass may be an external interface, eContainer() might need cast to EObject  
 	*/
	def IsContainerInstance(GenClass it, String _object, GenClass metaClass) // 
		'''«getEObjectFeature(metaClass, _object, 'eContainer()')» instanceof «getQualifiedInterfaceName(it)»'''

	protected def getEObjectFeature(GenClass it, String _object, String feature) //
		'''«IF it.externalInterface»((org.eclipse.emf.ecore.EObject) «_object»).«feature»«ELSE»«_object».«feature»«ENDIF»'''

	// Public/API-sort templates are named with first letter capitalized.
	// FIXME getFeatureValue* and setFeatureValue start with lowercase to indicate pending refactoring

	def getFeatureValue(GenFeature it, String containerVar, GenClass containerClass) // 
	'''
	«IF containerClass.externalInterface»((«featureTargetType») ((org.eclipse.emf.ecore.EObject) «containerVar»).eGet(«MetaFeature(it)»))«ELSE»«containerVar».«it.getAccessor»()«ENDIF»
	'''

	def featureTargetType(GenFeature it) //
	'''«IF it.listType»java.util.List«ELSE»«QualifiedClassName(genClass.genPackage.genModel.findGenClassifier(ecoreFeature.EType))»«ENDIF»'''

	/**
	 * FIXME leave only one version of these two getFeatureValue
	 *
	 * A slightly more sophisticated version of getFeatureValue().
	 * @param containerName the name of the container
	 * @param feature the feature whose value is in interest
	 * @param containerMetaClass the <code>GenClass</code> of the container, or <code>null</code>, if the container is declared as an <code>org.eclipse.emf.ecore.EObject</code>.
	 * @param needsCastToResultType whether the cast to the result type is required (this parameter is only used if the <code>org.eclipse.emf.ecore.EClass</code> this feature belongs to is an external interface). 
	 */
	def getFeatureValue(GenFeature it, String containerVar, GenClass container, boolean needsCastToResultType) // 
	'''
	«IF genClass.externalInterface»«IF needsCastToResultType»((«featureTargetType(it)») «ENDIF»«parenthesizedCast(containerVar, container, null)».eGet(«MetaFeature(it)»)«IF needsCastToResultType»)«ENDIF»«ELSE»«parenthesizedCast(containerVar, container, genClass)».«it.getAccessor»()«ENDIF»
	'''

	def modifyFeature(GenFeature it, String targetVar, GenClass targetType, String value)'''
		«IF it.listType»«getFeatureValue(it, targetVar, targetType)»
			.add(«value»);
		«ELSE»
			«setFeatureValue(it, targetVar, targetType, value)»;
		«ENDIF»
	'''

	def replaceFeatureValue(GenFeature it, String targetVar, GenClass targetType, String oldValue, String newValue) //
	'''
	«IF it.listType»«getFeatureValue(it, targetVar, targetType)».remove(«oldValue»);«ENDIF»
	«modifyFeature(it, targetVar, targetType, newValue)»
	'''

	def moveFeatureValue(GenFeature it, String oldTarget, String newTarget, GenClass targetType, String value) //
	'''
	«IF it.listType»«getFeatureValue(it, oldTarget, targetType)».remove(«value»);«ELSE»«setFeatureValue(it, oldTarget, targetType, 'null')»;«ENDIF»
	«modifyFeature(it, newTarget, targetType, value)»
	'''

	def setFeatureValue(GenFeature it, String targetVar, GenClass targetType, String valueVar)'''«setFeatureValue(it, targetVar, targetType, valueVar, false)»'''

	// FIXME support list features as well, i.e. do .add() instead of eSet
	def setFeatureValue(GenFeature it, String targetVar, GenClass targetType, String valueVar, boolean isPlainObjectValue) {
		if(targetType.externalInterface) {
			'''((org.eclipse.emf.ecore.EObject) «targetVar»).eSet(«MetaFeature(it)», «valueVar»)'''
		} else {
			'''«targetVar».set«it.accessorName»(«setFeatureArgs(it, valueVar, isPlainObjectValue)»)'''
		}
	}
	
	protected def setFeatureArgs(GenFeature it, String valueVar, boolean isPlainObjectValue){
		if(!isPlainObjectValue) {
			'''«valueVar»'''
		} else if (isPrimitiveType(it)) {
			'''«unwrapObjectToPrimitiveValue(it, valueVar)»'''
		} else {
			'''(«featureTargetType(it)»)«valueVar»'''
		}
	}
	

	protected def unwrapObjectToPrimitiveValue(GenFeature it, String valueVar) '''((«featureTargetType(it)») «valueVar»).«ecoreFeature.EType.instanceClassName»Value()'''

	def MetaClass(GenClassifier it)'''«getQualifiedPackageInterfaceName(it.genPackage)».eINSTANCE.get«getClassifierAccessorName(it)»()'''

	def MetaFeature(GenFeature it)'''«getQualifiedPackageInterfaceName(it.genClass.genPackage)».eINSTANCE.get«getFeatureAccessorName(it)»()'''

	/**
	* SomeFactory.eINSTANCE.createBlaBla();
	* NB: for map entries, the resulting type is EObject, not the qualified interface name. If cast is needed, use (un)parenthesizedCast() extension.
	* @see GenClassImpl#hasFactoryInterfaceCreateMethod() for details why map entries should be treated differently
	*/
	def NewInstance(GenClass it)'''
	«IF it.mapEntry»
		«getQualifiedFactoryInterfaceName(it.genPackage)».«getFactoryInstanceName(it.genPackage)».create(«MetaClass(it)»)«ELSE»
		«getQualifiedFactoryInterfaceName(genPackage)».«getFactoryInstanceName(genPackage)».create«ecoreClass.name»()«ENDIF»
	'''

	/**
	* // XXX Need to take into account possible GenClass from generated and always available code
	* // e.g. Notation or Ecore 
	* // FIXME be consistent on final line feed - e.g. NewInstance adds a LF, while modifyFeature not, hence together they look odd.
	*/
	def NewInstance(GenClass it, String varName) '''
		«getQualifiedInterfaceName(it)» «varName» = «IF it.mapEntry»(«getQualifiedInterfaceName(it)») «ENDIF»«NewInstance(it)»;
	'''

	/**
	 * Ensures value is of type EObject, may be no-op if context GenClass is compatible with EObject.
	 * Note, injected value is not surrounded with parenthesis, may need to introduce another
	 * template to accomplish that if needed.
	 */
	def DowncastToEObject(GenClass it, String value) '''«IF it.externalInterface»(org.eclipse.emf.ecore.EObject) «ENDIF»«value»'''
	
	/**
	 * Declares new variable of appropriate type and assigns casted value to it.
	 */
	def DeclareAndAssign(GenClass it, String assignee, String value) '''«getQualifiedInterfaceName(it)» «assignee» = («getQualifiedInterfaceName(it)») «value»;'''
	
	/**
	 * third boolean parameter is to indicate the value is not EObject, so may
	 * need extra cast in case dynamic model instances are in use.
	 */
	def dispatch DeclareAndAssign(GenClass it, String assignee, String value, boolean isPlainObjectValue) '''«DeclareAndAssign(it, assignee, value)»'''
	
	def dispatch DeclareAndAssign(GenClassifier it, String assignee, String value, boolean isPlainObjectValue) '''«getQualifiedClassName(it)» «assignee» = («getQualifiedClassName(it)») «value»;'''

	/**
	 *  @see IsContainerInstance
	 */
	def DeclareAndAssignContainer(GenClass it, String assignee, String _object, GenClass metaClass) //
		'''«getQualifiedInterfaceName(it)» «assignee» = («getQualifiedInterfaceName(it)») «getEObjectFeature(metaClass, _object, 'eContainer()')»;'''

	/**
	 * Declares new variable of context type and assignes a value obtained from 'src',
	 * which is of type 'srcMetaClass', via 'srcFeature'
	 *
	 * XXX in certain scenarions may need extra cast of the feature value
	 */
	def DeclareAndAssign(GenClass it, String assignee, String src, GenClass srcMetaClass, GenFeature srcFeature) //
		'''«getQualifiedInterfaceName(it)» «assignee» = «getFeatureValue(srcFeature, src, srcMetaClass)»;'''
	
	/**
	 * Same as DeclareAndAssign, with extra operation applied to source object
	 */
	def DeclareAndAssign2(GenClass it, String assignee, String src, GenClass srcMetaClass, GenFeature srcFeature, String srcExt, boolean needCast) //
		'''«getQualifiedInterfaceName(it)» «assignee» = «getFeatureValue(srcFeature, src, srcMetaClass)».«srcExt»;'''

	/**
	 * Cast value of type EObject to specific type. Would be no-op with dynamic model instances,
	 * therefore, the fact eObjectValue is actually EObject is essential
	 */
	def CastEObject(GenClass xptSelf, String eObjectValue) '''(«getQualifiedInterfaceName(xptSelf)») «eObjectValue»'''

	/**
	 * Qualified interface name of the generated EClass, or EObject for dynamic models.
	 * Use whenever class name is inevitable (e.g. method arguments)
	 * SHOULD NEVER APPEAR in instanceof or any other similar comparison operation
	 */
	def dispatch QualifiedClassName(GenClass xptSelf) '''«xptSelf.qualifiedInterfaceName»'''

	def dispatch QualifiedClassName(GenEnum xptSelf) '''«xptSelf.qualifiedName»'''

	def dispatch QualifiedClassName(GenClassifier xptSelf) '''«getQualifiedClassName(xptSelf)»'''

}