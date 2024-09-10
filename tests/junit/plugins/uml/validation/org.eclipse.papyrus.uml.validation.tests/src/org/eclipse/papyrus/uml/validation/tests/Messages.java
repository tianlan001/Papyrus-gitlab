/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.validation.tests;

import org.eclipse.osgi.util.NLS;

/**
 * @author ansgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.validation.tests.messages"; //$NON-NLS-1$
	public static String TestOCLConstraintOnInstanceSpec_PriceRule;
	public static String TestOCLValidationRuleFromFile_CamelCaseRule;
	public static String TestOCLValidationRuleFromFile_CanNotGetEnvFactory;
	public static String TestOCLValidationRuleFromFile_OCLCanNotLoadMM;
	public static String TestStereotypeValidation_UnsetRequiredAttribute;
	public static String TestUMLValidationRules_DetectNameClash;
	public static String TestUMLValidationRules_NonStandardMultiplicityMayNotImplyError;
	public static String TestValidationRulesInPluginXML_ConstraintIsMissing;
	public static String TestValidationRulesInPluginXML_IsActiveShouldNotTriggerIssue;
	public static String TestValidationRulesInPluginXML_IsActiveShouldTriggerIssue;
	public static String TestValidationRulesInPluginXML_IsPassiveShouldNotTriggerIssue;
	public static String TestValidationRulesInPluginXML_IsPassiveShouldTriggerIssue;
	public static String TestValidationRulesInProfile_IsActiveMalformedShouldTriggerIssue;
	public static String TestValidationRulesInProfile_IsActiveShouldNotTriggerIssue;
	public static String TestValidationRulesInProfile_IsActiveShouldTriggerIssue;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
