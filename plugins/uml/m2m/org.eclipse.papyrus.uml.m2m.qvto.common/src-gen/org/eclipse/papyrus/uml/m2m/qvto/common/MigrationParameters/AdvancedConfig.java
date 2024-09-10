/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - bug 496176
 * 
 */
package org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Advanced Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#getMappingParameters <em>Mapping Parameters</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedDiagrams <em>Remove Unmapped Diagrams</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isConvertOpaqueExpressionToLiteralString <em>Convert Opaque Expression To Literal String</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedProfilesAndStereotypes <em>Remove Unmapped Profiles And Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedAnnotations <em>Remove Unmapped Annotations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isAlwaysAcceptSuggestedMappings <em>Always Accept Suggested Mappings</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig()
 * @model
 * @generated
 */
public interface AdvancedConfig extends ThreadConfig {
	/**
	 * Returns the value of the '<em><b>Mapping Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Parameters</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Parameters</em>' containment reference.
	 * @see #setMappingParameters(MappingParameters)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_MappingParameters()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	MappingParameters getMappingParameters();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#getMappingParameters <em>Mapping Parameters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Parameters</em>' containment reference.
	 * @see #getMappingParameters()
	 * @generated
	 */
	void setMappingParameters(MappingParameters value);

	/**
	 * Returns the value of the '<em><b>Remove Unmapped Diagrams</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, the diagrams which were not migrated will be removed. Otherwise, only the successfully imported diagrams will be removed
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Remove Unmapped Diagrams</em>' attribute.
	 * @see #setRemoveUnmappedDiagrams(boolean)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_RemoveUnmappedDiagrams()
	 * @model default="false" unique="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isRemoveUnmappedDiagrams();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedDiagrams <em>Remove Unmapped Diagrams</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Unmapped Diagrams</em>' attribute.
	 * @see #isRemoveUnmappedDiagrams()
	 * @generated
	 */
	void setRemoveUnmappedDiagrams(boolean value);

	/**
	 * Returns the value of the '<em><b>Convert Opaque Expression To Literal String</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, the Opaque Expressions with a single body and no language (or a single empty language) will be converted to LiteralString
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Convert Opaque Expression To Literal String</em>' attribute.
	 * @see #setConvertOpaqueExpressionToLiteralString(boolean)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_ConvertOpaqueExpressionToLiteralString()
	 * @model default="true" unique="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isConvertOpaqueExpressionToLiteralString();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isConvertOpaqueExpressionToLiteralString <em>Convert Opaque Expression To Literal String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Convert Opaque Expression To Literal String</em>' attribute.
	 * @see #isConvertOpaqueExpressionToLiteralString()
	 * @generated
	 */
	void setConvertOpaqueExpressionToLiteralString(boolean value);

	/**
	 * Returns the value of the '<em><b>Remove Unmapped Profiles And Stereotypes</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, all unsupported RSA profiles and stereotypes will be deleted at the end of the transformation
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Remove Unmapped Profiles And Stereotypes</em>' attribute.
	 * @see #setRemoveUnmappedProfilesAndStereotypes(boolean)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_RemoveUnmappedProfilesAndStereotypes()
	 * @model default="false" unique="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isRemoveUnmappedProfilesAndStereotypes();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedProfilesAndStereotypes <em>Remove Unmapped Profiles And Stereotypes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Unmapped Profiles And Stereotypes</em>' attribute.
	 * @see #isRemoveUnmappedProfilesAndStereotypes()
	 * @generated
	 */
	void setRemoveUnmappedProfilesAndStereotypes(boolean value);

	/**
	 * Returns the value of the '<em><b>Remove Unmapped Annotations</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, the unmapped RSA EAnnotations will be deleted from the imported model
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Remove Unmapped Annotations</em>' attribute.
	 * @see #setRemoveUnmappedAnnotations(boolean)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_RemoveUnmappedAnnotations()
	 * @model default="false" unique="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isRemoveUnmappedAnnotations();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedAnnotations <em>Remove Unmapped Annotations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Unmapped Annotations</em>' attribute.
	 * @see #isRemoveUnmappedAnnotations()
	 * @generated
	 */
	void setRemoveUnmappedAnnotations(boolean value);

	/**
	 * Returns the value of the '<em><b>Always Accept Suggested Mappings</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, the Transformation will not open a dialog to ask user-confirmation for the dependency mappings. The tool will automatically keep going by "guessing" the proper mapping
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Always Accept Suggested Mappings</em>' attribute.
	 * @see #setAlwaysAcceptSuggestedMappings(boolean)
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getAdvancedConfig_AlwaysAcceptSuggestedMappings()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isAlwaysAcceptSuggestedMappings();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isAlwaysAcceptSuggestedMappings <em>Always Accept Suggested Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Always Accept Suggested Mappings</em>' attribute.
	 * @see #isAlwaysAcceptSuggestedMappings()
	 * @generated
	 */
	void setAlwaysAcceptSuggestedMappings(boolean value);

} // AdvancedConfig
