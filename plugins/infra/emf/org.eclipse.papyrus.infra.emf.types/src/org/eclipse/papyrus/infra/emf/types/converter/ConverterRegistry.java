/*****************************************************************************
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.converter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Registry to store onverters.
 */
public class ConverterRegistry {

	/** singleton instance */
	private static ConverterRegistry instance;

	/** map to store converters, identified by their class */
	private Map<Class<?>, IConverter> converterMaps = new HashMap<Class<?>, IConverter>();

	/**
	 * Constructor (not visible: singleton pattern)
	 */
	private ConverterRegistry() {
	}

	/**
	 * Returns the single instance of this registry
	 *
	 * @return the single instance of this registry
	 */
	public static synchronized ConverterRegistry getSingleton() {
		if(instance == null) {
			instance = new ConverterRegistry();
			instance.initializeRegistry();
		}
		return instance;
	}

	/**
	 * Initialize the registry
	 */
	protected void initializeRegistry() {
		converterMaps.put(String.class, new ValueSpecificationToStringConverter());
		converterMaps.put(int.class, new ValueSpecificationToIntegerConverter());
		converterMaps.put(boolean.class, new ValueSpecificationToBooleanConverter());
		converterMaps.put(Enumeration.class, new ValueSpecificationToEnumerationConverter());
		converterMaps.put(EEnum.class, new ValueSpecificationToEENumConverter());
	}

	/**
	 * Returns the converter for the convenient type, from an {@link Object}
	 *
	 * @param toType
	 *        the class into which the element should be transformed
	 *
	 * @return the converter for the convenient type
	 */
	public IConverter getConverter(Class<?> toType) {
		return converterMaps.get(toType);
	}

	public Object convert(Class<?> parameterType, ValueSpecification valueInstance) throws ConverterNotfoundException {
		// retrieve the converter
		IConverter converter = getConverter(parameterType);

		if(converter != null) {
			return converter.convert(valueInstance);
		} else {
			throw new ConverterNotfoundException(parameterType, valueInstance);
		}
	}

//	/**
//	 * Converts a parameter value into a list of values
//	 *
//	 * @param context
//	 *        the context of the evaluation of the query
//	 * @param parameterType
//	 *        the type of the list
//	 * @param parameterValue
//	 *        the parameter value to transform into a list
//	 * @return the list of values contained by the parameter value
//	 */
//	public List<?> convertToList(final EObject context, final Class<?> parameterType, final ParameterValue parameterValue) {
//		return new ConfigurationSwitch<List<?>>() {
//
//			/**
//			 * {@inheritDoc}
//			 */
//			@Override
//			public java.util.List<?> caseConstantParameterValue(ConstantParameterValue constantParameterValue) {
//				IConverter converter = getConverter(parameterType);
//
//				if(converter != null) {
//					return Arrays.asList(converter.convert(constantParameterValue.getValueInstance()));
//				} else {
//					Activator.log.error("Impossible to find a converter for type: " + parameterType, null);
//					return Collections.emptyList();
//				}
//			};
//
//			/**
//			 * {@inheritDoc}
//			 */
//			@Override
//			public java.util.List<?> caseQueryExecutionValue(QueryExecutionValue queryExecutionValue) {
//				// if query returns a list, returns it, else creates an array of one element
//				QueryConfiguration queryConfiguration = queryExecutionValue.getConfiguration();
//				if(!(queryConfiguration instanceof ModiscoQueryConfiguration)) {
//					throw new RuntimeException("List conversion not implemented for this type of configuration. " + queryConfiguration.eClass().getName());
//				}
//				Object executionResult = null;
//				try {
//					executionResult = QueryUtil.evaluateBooleanQuery(context, queryConfiguration);
//				} catch (Exception e) {
//					Activator.log.error(e);
//				}
//				int upperBound = ((ModiscoQueryConfiguration)queryConfiguration).getQuery().getUpperBound();
//				if(upperBound == 1) {
//					return Arrays.asList(executionResult);
//				} else {
//					return (List<?>)executionResult;
//				}
//			};
//
//			/**
//			 * {@inheritDoc}
//			 */
//			@Override
//			public java.util.List<?> caseListParameterValue(ListParameterValue listParameterValue) {
//				List<Object> values = new ArrayList<Object>();
//				for(ParameterValue parameterValue : listParameterValue.getValues()) {
//					values.addAll(convertToList(context, parameterType, parameterValue));
//				}
//				return values;
//			};
//
//		}.doSwitch(parameterValue);
//	}
}
