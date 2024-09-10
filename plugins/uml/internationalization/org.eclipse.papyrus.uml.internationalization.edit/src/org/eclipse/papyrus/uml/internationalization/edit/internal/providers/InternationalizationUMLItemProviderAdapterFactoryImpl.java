/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 528343
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.edit.internal.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.edit.utils.InternationalizationElementItemProviderUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.*;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This allows to override the {@link UMLItemProviderAdapterFactory} to avoid
 * the {@link NamedElement#getLabel()} call when the internationalization
 * preference is set to false.
 */
public class InternationalizationUMLItemProviderAdapterFactoryImpl extends UMLItemProviderAdapterFactory {

	/**
	 * Constructor.
	 */
	public InternationalizationUMLItemProviderAdapterFactoryImpl() {
		super();
	}

	@Override
	public Adapter createDependencyAdapter() {
		if (dependencyItemProvider == null) {
			dependencyItemProvider = new DependencyItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dependencyItemProvider;
	}

	@Override
	public Adapter createPackageAdapter() {
		if (packageItemProvider == null) {
			packageItemProvider = new PackageItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return packageItemProvider;
	}

	@Override
	public Adapter createProfileAdapter() {
		if (profileItemProvider == null) {
			profileItemProvider = new ProfileItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return profileItemProvider;
	}

	@Override
	public Adapter createStereotypeAdapter() {
		if (stereotypeItemProvider == null) {
			stereotypeItemProvider = new StereotypeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stereotypeItemProvider;
	}

	@Override
	public Adapter createClassAdapter() {
		if (classItemProvider == null) {
			classItemProvider = new ClassItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return classItemProvider;
	}

	@Override
	public Adapter createGeneralizationSetAdapter() {
		if (generalizationSetItemProvider == null) {
			generalizationSetItemProvider = new GeneralizationSetItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return generalizationSetItemProvider;
	}

	@Override
	public Adapter createUseCaseAdapter() {
		if (useCaseItemProvider == null) {
			useCaseItemProvider = new UseCaseItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return useCaseItemProvider;
	}

	@Override
	public Adapter createIncludeAdapter() {
		if (includeItemProvider == null) {
			includeItemProvider = new IncludeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return includeItemProvider;
	}

	@Override
	public Adapter createExtendAdapter() {
		if (extendItemProvider == null) {
			extendItemProvider = new ExtendItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extendItemProvider;
	}

	@Override
	public Adapter createConstraintAdapter() {
		if (constraintItemProvider == null) {
			constraintItemProvider = new ConstraintItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return constraintItemProvider;
	}

	@Override
	public Adapter createExtensionPointAdapter() {
		if (extensionPointItemProvider == null) {
			extensionPointItemProvider = new ExtensionPointItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionPointItemProvider;
	}

	@Override
	public Adapter createSubstitutionAdapter() {
		if (substitutionItemProvider == null) {
			substitutionItemProvider = new SubstitutionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return substitutionItemProvider;
	}

	@Override
	public Adapter createRealizationAdapter() {
		if (realizationItemProvider == null) {
			realizationItemProvider = new RealizationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return realizationItemProvider;
	}

	@Override
	public Adapter createAbstractionAdapter() {
		if (abstractionItemProvider == null) {
			abstractionItemProvider = new AbstractionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return abstractionItemProvider;
	}

	@Override
	public Adapter createOpaqueExpressionAdapter() {
		if (opaqueExpressionItemProvider == null) {
			opaqueExpressionItemProvider = new OpaqueExpressionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueExpressionItemProvider;
	}

	@Override
	public Adapter createParameterAdapter() {
		if (parameterItemProvider == null) {
			parameterItemProvider = new ParameterItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return parameterItemProvider;
	}

	@Override
	public Adapter createPropertyAdapter() {
		if (propertyItemProvider == null) {
			propertyItemProvider = new PropertyItemProvider(this) {

				public String getText(Object object) {
					StringBuffer text = appendType(
							appendKeywords(new StringBuffer(), object), "_UI_Property_type"); //$NON-NLS-1$

					final Property property = (Property) object;
					final Type type = property.getType();

					if (property.isDerived()) {
						appendString(text, "/"); //$NON-NLS-1$
					}

					String label = UMLLabelInternationalization.getInstance().getLabel(property, shouldTranslate());

					if (!UML2Util.isEmpty(label)) {
						appendString(text, label);
					} else if (property.getAssociation() != null && type != null) {
						String typeName = type.getName();

						if (!UML2Util.isEmpty(typeName)) {
							appendString(text, Character.toLowerCase(typeName.charAt(0))
									+ typeName.substring(1));
						}
					}

					if (type != null) {
						String typeLabel = UMLLabelInternationalization.getInstance().getLabel(type, shouldTranslate());

						if (!UMLUtil.isEmpty(typeLabel)) {
							appendString(text, ": " + typeLabel); //$NON-NLS-1$
						}
					}

					return ExtendedMultiplicityElementItemProvider.appendMultiplicityString(text, object)
							.toString();
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return propertyItemProvider;
	}

	/**
	 * This allows to redefine the 'appendMultiplicity' method.
	 */
	public static class ExtendedMultiplicityElementItemProvider extends MultiplicityElementItemProvider {

		/**
		 * Constructor.
		 *
		 * @param adapterFactory
		 *            The adapter factory
		 */
		public ExtendedMultiplicityElementItemProvider(final AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		/**
		 * This allows to append the multiplicity.
		 *
		 * @param text
		 *            The existing string buffer.
		 * @param object
		 *            The multiplicity element.
		 * @return The existing string buffer concat with multiplicity.
		 */
		public static StringBuffer appendMultiplicityString(final StringBuffer text, final Object object) {
			return MultiplicityElementItemProvider.appendMultiplicity(text, object);
		}
	}

	@Override
	public Adapter createDeploymentAdapter() {
		if (deploymentItemProvider == null) {
			deploymentItemProvider = new DeploymentItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deploymentItemProvider;
	}

	@Override
	public Adapter createDeploymentSpecificationAdapter() {
		if (deploymentSpecificationItemProvider == null) {
			deploymentSpecificationItemProvider = new DeploymentSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deploymentSpecificationItemProvider;
	}

	@Override
	public Adapter createArtifactAdapter() {
		if (artifactItemProvider == null) {
			artifactItemProvider = new ArtifactItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return artifactItemProvider;
	}

	@Override
	public Adapter createManifestationAdapter() {
		if (manifestationItemProvider == null) {
			manifestationItemProvider = new ManifestationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return manifestationItemProvider;
	}

	@Override
	public Adapter createOperationAdapter() {
		if (operationItemProvider == null) {
			operationItemProvider = new OperationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}

				@Override
				public String getText(Object object) {
					final StringBuffer text = appendLabel(
							appendType(appendKeywords(new StringBuffer(), object), "_UI_Operation_type"), object); //$NON-NLS-1$

					final Operation operation = (Operation) object;

					appendString(text, "(");

					final List<Parameter> ownedParameters = new ArrayList<Parameter>(operation.getOwnedParameters());
					ownedParameters.removeAll(operation.returnResult());

					for (Iterator<Parameter> parameters = ownedParameters.iterator(); parameters.hasNext();) {

						final Parameter parameter = parameters.next();
						final String label = UMLLabelInternationalization.getInstance().getLabel(parameter,
								shouldTranslate());
						if (UMLUtil.isEmpty(label)) {
							text.append('<').append(getTypeText(parameter)).append('>');
						} else {
							text.append(label);
						}

						final Type type = parameter.getType();

						if (null != type) {
							final String typeLabel = UMLLabelInternationalization.getInstance().getLabel(type,
									shouldTranslate());

							if (!UMLUtil.isEmpty(typeLabel)) {
								appendString(text, ": " + typeLabel); //$NON-NLS-1$
							}
						}

						appendMultiplicity(text, parameter);

						if (parameters.hasNext()) {
							text.append(", "); //$NON-NLS-1$
						}
					}

					text.append(')');

					Parameter returnResult = operation.getReturnResult();

					if (returnResult != null) {
						Type type = returnResult.getType();

						if (type != null) {
							String typeLabel = type.getLabel(shouldTranslate());

							if (!UMLUtil.isEmpty(typeLabel)) {
								appendString(text, ": " + typeLabel); //$NON-NLS-1$
							}
						}

						appendMultiplicity(text, returnResult);
					}

					return text.toString();
				}
			};
		}
		return operationItemProvider;
	}

	@Override
	public Adapter createParameterSetAdapter() {
		if (parameterSetItemProvider == null) {
			parameterSetItemProvider = new ParameterSetItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return parameterSetItemProvider;
	}

	@Override
	public Adapter createDataTypeAdapter() {
		if (dataTypeItemProvider == null) {
			dataTypeItemProvider = new DataTypeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dataTypeItemProvider;
	}

	@Override
	public Adapter createInterfaceAdapter() {
		if (interfaceItemProvider == null) {
			interfaceItemProvider = new InterfaceItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interfaceItemProvider;
	}

	@Override
	public Adapter createReceptionAdapter() {
		if (receptionItemProvider == null) {
			receptionItemProvider = new ReceptionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return receptionItemProvider;
	}

	@Override
	public Adapter createSignalAdapter() {
		if (signalItemProvider == null) {
			signalItemProvider = new SignalItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return signalItemProvider;
	}

	@Override
	public Adapter createProtocolStateMachineAdapter() {
		if (protocolStateMachineItemProvider == null) {
			protocolStateMachineItemProvider = new ProtocolStateMachineItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return protocolStateMachineItemProvider;
	}

	@Override
	public Adapter createStateMachineAdapter() {
		if (stateMachineItemProvider == null) {
			stateMachineItemProvider = new StateMachineItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateMachineItemProvider;
	}

	@Override
	public Adapter createRegionAdapter() {
		if (regionItemProvider == null) {
			regionItemProvider = new RegionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return regionItemProvider;
	}

	@Override
	public Adapter createTransitionAdapter() {
		if (transitionItemProvider == null) {
			transitionItemProvider = new TransitionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return transitionItemProvider;
	}

	@Override
	public Adapter createTriggerAdapter() {
		if (triggerItemProvider == null) {
			triggerItemProvider = new TriggerItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return triggerItemProvider;
	}

	@Override
	public Adapter createPortAdapter() {
		if (portItemProvider == null) {
			portItemProvider = new PortItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}

				@Override
				public String getText(Object object) {
					StringBuffer text = appendType(
							appendKeywords(new StringBuffer(), object), "_UI_Port_type"); //$NON-NLS-1$

					Port port = (Port) object;
					Type type = port.getType();

					if (port.isDerived()) {
						appendString(text, "/"); //$NON-NLS-1$
					}

					String label = null;
					if (InternationalizationPreferencesUtils.getInternationalizationPreference(port)) {
						label = UMLLabelInternationalization.getInstance().getLabel(port, shouldTranslate());
					} else {
						label = port.getName();
					}

					if (!UML2Util.isEmpty(label)) {
						appendString(text, label);
					} else if (port.getAssociation() != null && type != null) {
						String typeName = type.getName();

						if (!UML2Util.isEmpty(typeName)) {
							appendString(text, Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1));
						}
					}

					if (type != null) {
						String typeLabel = type.getLabel(shouldTranslate());

						if (!UMLUtil.isEmpty(typeLabel)) {
							appendString(text, (port.isConjugated() ? ": ~" : ": ") + typeLabel); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}

					return appendMultiplicity(text, object).toString();
				}
			};
		}
		return portItemProvider;
	}

	@Override
	public Adapter createStateAdapter() {
		if (stateItemProvider == null) {
			stateItemProvider = new StateItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateItemProvider;
	}

	@Override
	public Adapter createConnectionPointReferenceAdapter() {
		if (connectionPointReferenceItemProvider == null) {
			connectionPointReferenceItemProvider = new ConnectionPointReferenceItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectionPointReferenceItemProvider;
	}

	@Override
	public Adapter createPseudostateAdapter() {
		if (pseudostateItemProvider == null) {
			pseudostateItemProvider = new PseudostateItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return pseudostateItemProvider;
	}

	@Override
	public Adapter createAssociationAdapter() {
		if (associationItemProvider == null) {
			associationItemProvider = new AssociationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}

				@Override
				public String getText(Object object) {
					StringBuffer text = appendType(appendKeywords(new StringBuffer(), object), "_UI_Association_type"); //$NON-NLS-1$

					final Association association = (Association) object;

					if (association.isDerived()) {
						appendString(text, "/"); //$NON-NLS-1$
					}

					String label = null;
					if (InternationalizationPreferencesUtils.getInternationalizationPreference(association)) {
						label = UMLLabelInternationalization.getInstance().getLabel(association, shouldTranslate());
					} else {
						label = association.getName();
					}

					if (!UML2Util.isEmpty(label)) {
						appendString(text, label);
					} else {
						EList<Property> memberEnds = association.getMemberEnds();

						if (!memberEnds.isEmpty()) {
							appendString(text, "A"); //$NON-NLS-1$

							for (Property memberEnd : memberEnds) {
								String memberEndName = memberEnd.getName();

								text.append('_'); // $NON-NLS-1$

								if (!UML2Util.isEmpty(memberEndName)) {
									text.append(memberEndName);
								} else {
									Type type = memberEnd.getType();

									if (type != null) {
										String typeName = type.getName();

										if (!UML2Util.isEmpty(typeName)) {
											memberEndName = Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1);
										}
									}

									if (!UML2Util.isEmpty(memberEndName)) {
										text.append(memberEndName);
									} else {
										text.append('<'); // $NON-NLS-1$
										text.append(getTypeText(memberEnd));
										text.append('>'); // $NON-NLS-1$
									}
								}
							}
						}
					}

					return text.toString();
				}
			};
		}
		return associationItemProvider;
	}

	@Override
	public Adapter createCollaborationUseAdapter() {
		if (collaborationUseItemProvider == null) {
			collaborationUseItemProvider = new CollaborationUseItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return collaborationUseItemProvider;
	}

	@Override
	public Adapter createCollaborationAdapter() {
		if (collaborationItemProvider == null) {
			collaborationItemProvider = new CollaborationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return collaborationItemProvider;
	}

	@Override
	public Adapter createConnectorAdapter() {
		if (connectorItemProvider == null) {
			connectorItemProvider = new ConnectorItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectorItemProvider;
	}

	@Override
	public Adapter createRedefinableTemplateSignatureAdapter() {
		if (redefinableTemplateSignatureItemProvider == null) {
			redefinableTemplateSignatureItemProvider = new RedefinableTemplateSignatureItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return redefinableTemplateSignatureItemProvider;
	}

	@Override
	public Adapter createInterfaceRealizationAdapter() {
		if (interfaceRealizationItemProvider == null) {
			interfaceRealizationItemProvider = new InterfaceRealizationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interfaceRealizationItemProvider;
	}

	@Override
	public Adapter createExtensionAdapter() {
		if (extensionItemProvider == null) {
			extensionItemProvider = new ExtensionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionItemProvider;
	}

	@Override
	public Adapter createExtensionEndAdapter() {
		if (extensionEndItemProvider == null) {
			extensionEndItemProvider = new ExtensionEndItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionEndItemProvider;
	}

	@Override
	public Adapter createStringExpressionAdapter() {
		if (stringExpressionItemProvider == null) {
			stringExpressionItemProvider = new StringExpressionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stringExpressionItemProvider;
	}

	@Override
	public Adapter createExpressionAdapter() {
		if (expressionItemProvider == null) {
			expressionItemProvider = new ExpressionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expressionItemProvider;
	}

	@Override
	public Adapter createLiteralIntegerAdapter() {
		if (literalIntegerItemProvider == null) {
			literalIntegerItemProvider = new LiteralIntegerItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalIntegerItemProvider;
	}

	@Override
	public Adapter createLiteralStringAdapter() {
		if (literalStringItemProvider == null) {
			literalStringItemProvider = new LiteralStringItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalStringItemProvider;
	}

	@Override
	public Adapter createLiteralBooleanAdapter() {
		if (literalBooleanItemProvider == null) {
			literalBooleanItemProvider = new LiteralBooleanItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalBooleanItemProvider;
	}

	@Override
	public Adapter createLiteralNullAdapter() {
		if (literalNullItemProvider == null) {
			literalNullItemProvider = new LiteralNullItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalNullItemProvider;
	}

	@Override
	public Adapter createInstanceSpecificationAdapter() {
		if (instanceSpecificationItemProvider == null) {
			instanceSpecificationItemProvider = new InstanceSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return instanceSpecificationItemProvider;
	}

	@Override
	public Adapter createEnumerationAdapter() {
		if (enumerationItemProvider == null) {
			enumerationItemProvider = new EnumerationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return enumerationItemProvider;
	}

	@Override
	public Adapter createEnumerationLiteralAdapter() {
		if (enumerationLiteralItemProvider == null) {
			enumerationLiteralItemProvider = new EnumerationLiteralItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return enumerationLiteralItemProvider;
	}

	@Override
	public Adapter createPrimitiveTypeAdapter() {
		if (primitiveTypeItemProvider == null) {
			primitiveTypeItemProvider = new PrimitiveTypeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return primitiveTypeItemProvider;
	}

	@Override
	public Adapter createInstanceValueAdapter() {
		if (instanceValueItemProvider == null) {
			instanceValueItemProvider = new InstanceValueItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return instanceValueItemProvider;
	}

	@Override
	public Adapter createLiteralUnlimitedNaturalAdapter() {
		if (literalUnlimitedNaturalItemProvider == null) {
			literalUnlimitedNaturalItemProvider = new LiteralUnlimitedNaturalItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalUnlimitedNaturalItemProvider;
	}

	@Override
	public Adapter createOpaqueBehaviorAdapter() {
		if (opaqueBehaviorItemProvider == null) {
			opaqueBehaviorItemProvider = new OpaqueBehaviorItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueBehaviorItemProvider;
	}

	@Override
	public Adapter createFunctionBehaviorAdapter() {
		if (functionBehaviorItemProvider == null) {
			functionBehaviorItemProvider = new FunctionBehaviorItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return functionBehaviorItemProvider;
	}

	@Override
	public Adapter createActorAdapter() {
		if (actorItemProvider == null) {
			actorItemProvider = new ActorItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actorItemProvider;
	}

	@Override
	public Adapter createUsageAdapter() {
		if (usageItemProvider == null) {
			usageItemProvider = new UsageItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return usageItemProvider;
	}

	@Override
	public Adapter createMessageAdapter() {
		if (messageItemProvider == null) {
			messageItemProvider = new MessageItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return messageItemProvider;
	}

	@Override
	public Adapter createInteractionAdapter() {
		if (interactionItemProvider == null) {
			interactionItemProvider = new InteractionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionItemProvider;
	}

	@Override
	public Adapter createLifelineAdapter() {
		if (lifelineItemProvider == null) {
			lifelineItemProvider = new LifelineItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return lifelineItemProvider;
	}

	@Override
	public Adapter createPartDecompositionAdapter() {
		if (partDecompositionItemProvider == null) {
			partDecompositionItemProvider = new PartDecompositionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return partDecompositionItemProvider;
	}

	@Override
	public Adapter createInteractionUseAdapter() {
		if (interactionUseItemProvider == null) {
			interactionUseItemProvider = new InteractionUseItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionUseItemProvider;
	}

	@Override
	public Adapter createGateAdapter() {
		if (gateItemProvider == null) {
			gateItemProvider = new GateItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return gateItemProvider;
	}

	@Override
	public Adapter createActivityAdapter() {
		if (activityItemProvider == null) {
			activityItemProvider = new ActivityItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityItemProvider;
	}

	@Override
	public Adapter createActivityPartitionAdapter() {
		if (activityPartitionItemProvider == null) {
			activityPartitionItemProvider = new ActivityPartitionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityPartitionItemProvider;
	}

	@Override
	public Adapter createStructuredActivityNodeAdapter() {
		if (structuredActivityNodeItemProvider == null) {
			structuredActivityNodeItemProvider = new StructuredActivityNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return structuredActivityNodeItemProvider;
	}

	@Override
	public Adapter createVariableAdapter() {
		if (variableItemProvider == null) {
			variableItemProvider = new VariableItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return variableItemProvider;
	}

	@Override
	public Adapter createInterruptibleActivityRegionAdapter() {
		if (interruptibleActivityRegionItemProvider == null) {
			interruptibleActivityRegionItemProvider = new InterruptibleActivityRegionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interruptibleActivityRegionItemProvider;
	}

	@Override
	public Adapter createExceptionHandlerAdapter() {
		if (exceptionHandlerItemProvider == null) {
			exceptionHandlerItemProvider = new ExceptionHandlerItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return exceptionHandlerItemProvider;
	}

	@Override
	public Adapter createOutputPinAdapter() {
		if (outputPinItemProvider == null) {
			outputPinItemProvider = new OutputPinItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return outputPinItemProvider;
	}

	@Override
	public Adapter createInputPinAdapter() {
		if (inputPinItemProvider == null) {
			inputPinItemProvider = new InputPinItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return inputPinItemProvider;
	}

	@Override
	public Adapter createGeneralOrderingAdapter() {
		if (generalOrderingItemProvider == null) {
			generalOrderingItemProvider = new GeneralOrderingItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return generalOrderingItemProvider;
	}

	@Override
	public Adapter createOccurrenceSpecificationAdapter() {
		if (occurrenceSpecificationItemProvider == null) {
			occurrenceSpecificationItemProvider = new OccurrenceSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return occurrenceSpecificationItemProvider;
	}

	@Override
	public Adapter createInteractionOperandAdapter() {
		if (interactionOperandItemProvider == null) {
			interactionOperandItemProvider = new InteractionOperandItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionOperandItemProvider;
	}

	@Override
	public Adapter createInteractionConstraintAdapter() {
		if (interactionConstraintItemProvider == null) {
			interactionConstraintItemProvider = new InteractionConstraintItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionConstraintItemProvider;
	}

	@Override
	public Adapter createExecutionOccurrenceSpecificationAdapter() {
		if (executionOccurrenceSpecificationItemProvider == null) {
			executionOccurrenceSpecificationItemProvider = new ExecutionOccurrenceSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return executionOccurrenceSpecificationItemProvider;
	}

	@Override
	public Adapter createStateInvariantAdapter() {
		if (stateInvariantItemProvider == null) {
			stateInvariantItemProvider = new StateInvariantItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateInvariantItemProvider;
	}

	@Override
	public Adapter createActionExecutionSpecificationAdapter() {
		if (actionExecutionSpecificationItemProvider == null) {
			actionExecutionSpecificationItemProvider = new ActionExecutionSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actionExecutionSpecificationItemProvider;
	}

	@Override
	public Adapter createBehaviorExecutionSpecificationAdapter() {
		if (behaviorExecutionSpecificationItemProvider == null) {
			behaviorExecutionSpecificationItemProvider = new BehaviorExecutionSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return behaviorExecutionSpecificationItemProvider;
	}

	@Override
	public Adapter createMessageOccurrenceSpecificationAdapter() {
		if (messageOccurrenceSpecificationItemProvider == null) {
			messageOccurrenceSpecificationItemProvider = new MessageOccurrenceSpecificationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return messageOccurrenceSpecificationItemProvider;
	}

	@Override
	public Adapter createCombinedFragmentAdapter() {
		if (combinedFragmentItemProvider == null) {
			combinedFragmentItemProvider = new CombinedFragmentItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return combinedFragmentItemProvider;
	}

	@Override
	public Adapter createContinuationAdapter() {
		if (continuationItemProvider == null) {
			continuationItemProvider = new ContinuationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return continuationItemProvider;
	}

	@Override
	public Adapter createConsiderIgnoreFragmentAdapter() {
		if (considerIgnoreFragmentItemProvider == null) {
			considerIgnoreFragmentItemProvider = new ConsiderIgnoreFragmentItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return considerIgnoreFragmentItemProvider;
	}

	@Override
	public Adapter createCallEventAdapter() {
		if (callEventItemProvider == null) {
			callEventItemProvider = new CallEventItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callEventItemProvider;
	}

	@Override
	public Adapter createChangeEventAdapter() {
		if (changeEventItemProvider == null) {
			changeEventItemProvider = new ChangeEventItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return changeEventItemProvider;
	}

	@Override
	public Adapter createSignalEventAdapter() {
		if (signalEventItemProvider == null) {
			signalEventItemProvider = new SignalEventItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return signalEventItemProvider;
	}

	@Override
	public Adapter createAnyReceiveEventAdapter() {
		if (anyReceiveEventItemProvider == null) {
			anyReceiveEventItemProvider = new AnyReceiveEventItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return anyReceiveEventItemProvider;
	}

	@Override
	public Adapter createCreateObjectActionAdapter() {
		if (createObjectActionItemProvider == null) {
			createObjectActionItemProvider = new CreateObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createObjectActionItemProvider;
	}

	@Override
	public Adapter createDestroyObjectActionAdapter() {
		if (destroyObjectActionItemProvider == null) {
			destroyObjectActionItemProvider = new DestroyObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return destroyObjectActionItemProvider;
	}

	@Override
	public Adapter createTestIdentityActionAdapter() {
		if (testIdentityActionItemProvider == null) {
			testIdentityActionItemProvider = new TestIdentityActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return testIdentityActionItemProvider;
	}

	@Override
	public Adapter createReadSelfActionAdapter() {
		if (readSelfActionItemProvider == null) {
			readSelfActionItemProvider = new ReadSelfActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readSelfActionItemProvider;
	}

	@Override
	public Adapter createReadStructuralFeatureActionAdapter() {
		if (readStructuralFeatureActionItemProvider == null) {
			readStructuralFeatureActionItemProvider = new ReadStructuralFeatureActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readStructuralFeatureActionItemProvider;
	}

	@Override
	public Adapter createClearStructuralFeatureActionAdapter() {
		if (clearStructuralFeatureActionItemProvider == null) {
			clearStructuralFeatureActionItemProvider = new ClearStructuralFeatureActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearStructuralFeatureActionItemProvider;
	}

	@Override
	public Adapter createRemoveStructuralFeatureValueActionAdapter() {
		if (removeStructuralFeatureValueActionItemProvider == null) {
			removeStructuralFeatureValueActionItemProvider = new RemoveStructuralFeatureValueActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return removeStructuralFeatureValueActionItemProvider;
	}

	@Override
	public Adapter createAddStructuralFeatureValueActionAdapter() {
		if (addStructuralFeatureValueActionItemProvider == null) {
			addStructuralFeatureValueActionItemProvider = new AddStructuralFeatureValueActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return addStructuralFeatureValueActionItemProvider;
	}

	@Override
	public Adapter createLinkEndDataAdapter() {
		if (linkEndDataItemProvider == null) {
			linkEndDataItemProvider = new LinkEndDataItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndDataItemProvider;
	}

	@Override
	public Adapter createQualifierValueAdapter() {
		if (qualifierValueItemProvider == null) {
			qualifierValueItemProvider = new QualifierValueItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return qualifierValueItemProvider;
	}

	@Override
	public Adapter createReadLinkActionAdapter() {
		if (readLinkActionItemProvider == null) {
			readLinkActionItemProvider = new ReadLinkActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkActionItemProvider;
	}

	@Override
	public Adapter createLinkEndCreationDataAdapter() {
		if (linkEndCreationDataItemProvider == null) {
			linkEndCreationDataItemProvider = new LinkEndCreationDataItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndCreationDataItemProvider;
	}

	@Override
	public Adapter createCreateLinkActionAdapter() {
		if (createLinkActionItemProvider == null) {
			createLinkActionItemProvider = new CreateLinkActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createLinkActionItemProvider;
	}

	@Override
	public Adapter createDestroyLinkActionAdapter() {
		if (destroyLinkActionItemProvider == null) {
			destroyLinkActionItemProvider = new DestroyLinkActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return destroyLinkActionItemProvider;
	}

	@Override
	public Adapter createLinkEndDestructionDataAdapter() {
		if (linkEndDestructionDataItemProvider == null) {
			linkEndDestructionDataItemProvider = new LinkEndDestructionDataItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndDestructionDataItemProvider;
	}

	@Override
	public Adapter createClearAssociationActionAdapter() {
		if (clearAssociationActionItemProvider == null) {
			clearAssociationActionItemProvider = new ClearAssociationActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearAssociationActionItemProvider;
	}

	@Override
	public Adapter createBroadcastSignalActionAdapter() {
		if (broadcastSignalActionItemProvider == null) {
			broadcastSignalActionItemProvider = new BroadcastSignalActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return broadcastSignalActionItemProvider;
	}

	@Override
	public Adapter createSendObjectActionAdapter() {
		if (sendObjectActionItemProvider == null) {
			sendObjectActionItemProvider = new SendObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sendObjectActionItemProvider;
	}

	@Override
	public Adapter createValueSpecificationActionAdapter() {
		if (valueSpecificationActionItemProvider == null) {
			valueSpecificationActionItemProvider = new ValueSpecificationActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return valueSpecificationActionItemProvider;
	}

	@Override
	public Adapter createTimeExpressionAdapter() {
		if (timeExpressionItemProvider == null) {
			timeExpressionItemProvider = new TimeExpressionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeExpressionItemProvider;
	}

	@Override
	public Adapter createDurationAdapter() {
		if (durationItemProvider == null) {
			durationItemProvider = new DurationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationItemProvider;
	}

	@Override
	public Adapter createValuePinAdapter() {
		if (valuePinItemProvider == null) {
			valuePinItemProvider = new ValuePinItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return valuePinItemProvider;
	}

	@Override
	public Adapter createDurationIntervalAdapter() {
		if (durationIntervalItemProvider == null) {
			durationIntervalItemProvider = new DurationIntervalItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationIntervalItemProvider;
	}

	@Override
	public Adapter createIntervalAdapter() {
		if (intervalItemProvider == null) {
			intervalItemProvider = new IntervalItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return intervalItemProvider;
	}

	@Override
	public Adapter createTimeConstraintAdapter() {
		if (timeConstraintItemProvider == null) {
			timeConstraintItemProvider = new TimeConstraintItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeConstraintItemProvider;
	}

	@Override
	public Adapter createIntervalConstraintAdapter() {
		if (intervalConstraintItemProvider == null) {
			intervalConstraintItemProvider = new IntervalConstraintItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return intervalConstraintItemProvider;
	}

	@Override
	public Adapter createTimeIntervalAdapter() {
		if (timeIntervalItemProvider == null) {
			timeIntervalItemProvider = new TimeIntervalItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeIntervalItemProvider;
	}

	@Override
	public Adapter createDurationConstraintAdapter() {
		if (durationConstraintItemProvider == null) {
			durationConstraintItemProvider = new DurationConstraintItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationConstraintItemProvider;
	}

	@Override
	public Adapter createTimeObservationAdapter() {
		if (timeObservationItemProvider == null) {
			timeObservationItemProvider = new TimeObservationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeObservationItemProvider;
	}

	@Override
	public Adapter createDurationObservationAdapter() {
		if (durationObservationItemProvider == null) {
			durationObservationItemProvider = new DurationObservationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationObservationItemProvider;
	}

	@Override
	public Adapter createOpaqueActionAdapter() {
		if (opaqueActionItemProvider == null) {
			opaqueActionItemProvider = new OpaqueActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueActionItemProvider;
	}

	@Override
	public Adapter createSendSignalActionAdapter() {
		if (sendSignalActionItemProvider == null) {
			sendSignalActionItemProvider = new SendSignalActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sendSignalActionItemProvider;
	}

	@Override
	public Adapter createCallOperationActionAdapter() {
		if (callOperationActionItemProvider == null) {
			callOperationActionItemProvider = new CallOperationActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callOperationActionItemProvider;
	}

	@Override
	public Adapter createCallBehaviorActionAdapter() {
		if (callBehaviorActionItemProvider == null) {
			callBehaviorActionItemProvider = new CallBehaviorActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callBehaviorActionItemProvider;
	}

	@Override
	public Adapter createInformationItemAdapter() {
		if (informationItemItemProvider == null) {
			informationItemItemProvider = new InformationItemItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return informationItemItemProvider;
	}

	@Override
	public Adapter createInformationFlowAdapter() {
		if (informationFlowItemProvider == null) {
			informationFlowItemProvider = new InformationFlowItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return informationFlowItemProvider;
	}

	@Override
	public Adapter createModelAdapter() {
		if (modelItemProvider == null) {
			modelItemProvider = new ModelItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return modelItemProvider;
	}

	@Override
	public Adapter createReadVariableActionAdapter() {
		if (readVariableActionItemProvider == null) {
			readVariableActionItemProvider = new ReadVariableActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readVariableActionItemProvider;
	}

	@Override
	public Adapter createClearVariableActionAdapter() {
		if (clearVariableActionItemProvider == null) {
			clearVariableActionItemProvider = new ClearVariableActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearVariableActionItemProvider;
	}

	@Override
	public Adapter createAddVariableValueActionAdapter() {
		if (addVariableValueActionItemProvider == null) {
			addVariableValueActionItemProvider = new AddVariableValueActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return addVariableValueActionItemProvider;
	}

	@Override
	public Adapter createRemoveVariableValueActionAdapter() {
		if (removeVariableValueActionItemProvider == null) {
			removeVariableValueActionItemProvider = new RemoveVariableValueActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return removeVariableValueActionItemProvider;
	}

	@Override
	public Adapter createRaiseExceptionActionAdapter() {
		if (raiseExceptionActionItemProvider == null) {
			raiseExceptionActionItemProvider = new RaiseExceptionActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return raiseExceptionActionItemProvider;
	}

	@Override
	public Adapter createActionInputPinAdapter() {
		if (actionInputPinItemProvider == null) {
			actionInputPinItemProvider = new ActionInputPinItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actionInputPinItemProvider;
	}

	@Override
	public Adapter createReadExtentActionAdapter() {
		if (readExtentActionItemProvider == null) {
			readExtentActionItemProvider = new ReadExtentActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readExtentActionItemProvider;
	}

	@Override
	public Adapter createReclassifyObjectActionAdapter() {
		if (reclassifyObjectActionItemProvider == null) {
			reclassifyObjectActionItemProvider = new ReclassifyObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return reclassifyObjectActionItemProvider;
	}

	@Override
	public Adapter createReadIsClassifiedObjectActionAdapter() {
		if (readIsClassifiedObjectActionItemProvider == null) {
			readIsClassifiedObjectActionItemProvider = new ReadIsClassifiedObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readIsClassifiedObjectActionItemProvider;
	}

	@Override
	public Adapter createStartClassifierBehaviorActionAdapter() {
		if (startClassifierBehaviorActionItemProvider == null) {
			startClassifierBehaviorActionItemProvider = new StartClassifierBehaviorActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return startClassifierBehaviorActionItemProvider;
	}

	@Override
	public Adapter createReadLinkObjectEndActionAdapter() {
		if (readLinkObjectEndActionItemProvider == null) {
			readLinkObjectEndActionItemProvider = new ReadLinkObjectEndActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkObjectEndActionItemProvider;
	}

	@Override
	public Adapter createReadLinkObjectEndQualifierActionAdapter() {
		if (readLinkObjectEndQualifierActionItemProvider == null) {
			readLinkObjectEndQualifierActionItemProvider = new ReadLinkObjectEndQualifierActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkObjectEndQualifierActionItemProvider;
	}

	@Override
	public Adapter createCreateLinkObjectActionAdapter() {
		if (createLinkObjectActionItemProvider == null) {
			createLinkObjectActionItemProvider = new CreateLinkObjectActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createLinkObjectActionItemProvider;
	}

	@Override
	public Adapter createAcceptEventActionAdapter() {
		if (acceptEventActionItemProvider == null) {
			acceptEventActionItemProvider = new AcceptEventActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return acceptEventActionItemProvider;
	}

	@Override
	public Adapter createAcceptCallActionAdapter() {
		if (acceptCallActionItemProvider == null) {
			acceptCallActionItemProvider = new AcceptCallActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return acceptCallActionItemProvider;
	}

	@Override
	public Adapter createReplyActionAdapter() {
		if (replyActionItemProvider == null) {
			replyActionItemProvider = new ReplyActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return replyActionItemProvider;
	}

	@Override
	public Adapter createUnmarshallActionAdapter() {
		if (unmarshallActionItemProvider == null) {
			unmarshallActionItemProvider = new UnmarshallActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return unmarshallActionItemProvider;
	}

	@Override
	public Adapter createReduceActionAdapter() {
		if (reduceActionItemProvider == null) {
			reduceActionItemProvider = new ReduceActionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return reduceActionItemProvider;
	}

	@Override
	public Adapter createControlFlowAdapter() {
		if (controlFlowItemProvider == null) {
			controlFlowItemProvider = new ControlFlowItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return controlFlowItemProvider;
	}

	@Override
	public Adapter createInitialNodeAdapter() {
		if (initialNodeItemProvider == null) {
			initialNodeItemProvider = new InitialNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return initialNodeItemProvider;
	}

	@Override
	public Adapter createActivityParameterNodeAdapter() {
		if (activityParameterNodeItemProvider == null) {
			activityParameterNodeItemProvider = new ActivityParameterNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityParameterNodeItemProvider;
	}

	@Override
	public Adapter createForkNodeAdapter() {
		if (forkNodeItemProvider == null) {
			forkNodeItemProvider = new ForkNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return forkNodeItemProvider;
	}

	@Override
	public Adapter createFlowFinalNodeAdapter() {
		if (flowFinalNodeItemProvider == null) {
			flowFinalNodeItemProvider = new FlowFinalNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return flowFinalNodeItemProvider;
	}

	@Override
	public Adapter createCentralBufferNodeAdapter() {
		if (centralBufferNodeItemProvider == null) {
			centralBufferNodeItemProvider = new CentralBufferNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return centralBufferNodeItemProvider;
	}

	@Override
	public Adapter createMergeNodeAdapter() {
		if (mergeNodeItemProvider == null) {
			mergeNodeItemProvider = new MergeNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return mergeNodeItemProvider;
	}

	@Override
	public Adapter createDecisionNodeAdapter() {
		if (decisionNodeItemProvider == null) {
			decisionNodeItemProvider = new DecisionNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return decisionNodeItemProvider;
	}

	@Override
	public Adapter createActivityFinalNodeAdapter() {
		if (activityFinalNodeItemProvider == null) {
			activityFinalNodeItemProvider = new ActivityFinalNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityFinalNodeItemProvider;
	}

	@Override
	public Adapter createJoinNodeAdapter() {
		if (joinNodeItemProvider == null) {
			joinNodeItemProvider = new JoinNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return joinNodeItemProvider;
	}

	@Override
	public Adapter createDataStoreNodeAdapter() {
		if (dataStoreNodeItemProvider == null) {
			dataStoreNodeItemProvider = new DataStoreNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dataStoreNodeItemProvider;
	}

	@Override
	public Adapter createObjectFlowAdapter() {
		if (objectFlowItemProvider == null) {
			objectFlowItemProvider = new ObjectFlowItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return objectFlowItemProvider;
	}

	@Override
	public Adapter createSequenceNodeAdapter() {
		if (sequenceNodeItemProvider == null) {
			sequenceNodeItemProvider = new SequenceNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sequenceNodeItemProvider;
	}

	@Override
	public Adapter createConditionalNodeAdapter() {
		if (conditionalNodeItemProvider == null) {
			conditionalNodeItemProvider = new ConditionalNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return conditionalNodeItemProvider;
	}

	@Override
	public Adapter createClauseAdapter() {
		if (clauseItemProvider == null) {
			clauseItemProvider = new ClauseItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clauseItemProvider;
	}

	@Override
	public Adapter createLoopNodeAdapter() {
		if (loopNodeItemProvider == null) {
			loopNodeItemProvider = new LoopNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return loopNodeItemProvider;
	}

	@Override
	public Adapter createExpansionNodeAdapter() {
		if (expansionNodeItemProvider == null) {
			expansionNodeItemProvider = new ExpansionNodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expansionNodeItemProvider;
	}

	@Override
	public Adapter createExpansionRegionAdapter() {
		if (expansionRegionItemProvider == null) {
			expansionRegionItemProvider = new ExpansionRegionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expansionRegionItemProvider;
	}

	@Override
	public Adapter createComponentRealizationAdapter() {
		if (componentRealizationItemProvider == null) {
			componentRealizationItemProvider = new ComponentRealizationItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return componentRealizationItemProvider;
	}

	@Override
	public Adapter createComponentAdapter() {
		if (componentItemProvider == null) {
			componentItemProvider = new ComponentItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return componentItemProvider;
	}

	@Override
	public Adapter createNodeAdapter() {
		if (nodeItemProvider == null) {
			nodeItemProvider = new NodeItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return nodeItemProvider;
	}

	@Override
	public Adapter createDeviceAdapter() {
		if (deviceItemProvider == null) {
			deviceItemProvider = new DeviceItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deviceItemProvider;
	}

	@Override
	public Adapter createExecutionEnvironmentAdapter() {
		if (executionEnvironmentItemProvider == null) {
			executionEnvironmentItemProvider = new ExecutionEnvironmentItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return executionEnvironmentItemProvider;
	}

	@Override
	public Adapter createCommunicationPathAdapter() {
		if (communicationPathItemProvider == null) {
			communicationPathItemProvider = new CommunicationPathItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return communicationPathItemProvider;
	}

	@Override
	public Adapter createFinalStateAdapter() {
		if (finalStateItemProvider == null) {
			finalStateItemProvider = new FinalStateItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return finalStateItemProvider;
	}

	@Override
	public Adapter createTimeEventAdapter() {
		if (timeEventItemProvider == null) {
			timeEventItemProvider = new TimeEventItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeEventItemProvider;
	}

	@Override
	public Adapter createProtocolTransitionAdapter() {
		if (protocolTransitionItemProvider == null) {
			protocolTransitionItemProvider = new ProtocolTransitionItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return protocolTransitionItemProvider;
	}

	@Override
	public Adapter createAssociationClassAdapter() {
		if (associationClassItemProvider == null) {
			associationClassItemProvider = new AssociationClassItemProvider(this) {

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return associationClassItemProvider;
	}

	/**
	 * This is copied from MiltiplicityElementItemProvider because the initial
	 * function is protected scoped.
	 * 
	 * @param text
	 *            The initial string buffer.
	 * @param object
	 *            The object to manage multiplicity.
	 * @return The modified string buffer.
	 */
	protected static StringBuffer appendMultiplicity(final StringBuffer text, final Object object) {

		if (object instanceof MultiplicityElement) {
			MultiplicityElement multiplicityElement = (MultiplicityElement) object;

			if (multiplicityElement.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER)
					|| multiplicityElement.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER)) {

				if (0 < text.length()) {
					text.append(' ');
				}

				int upper = multiplicityElement.getUpper();

				text.append('[').append(multiplicityElement.getLower()).append("..") //$NON-NLS-1$
						.append(upper == LiteralUnlimitedNatural.UNLIMITED ? "*" : String.valueOf(upper)).append(']'); //$NON-NLS-1$
			}
		}

		return text;
	}
}
