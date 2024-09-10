/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.nattable.provider;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.papyrus.infra.emf.nattable.registry.EOperationImageRegistry;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;

/**
 * Provides the label for the EstructuralFeature
 *
 * @author Vincent Lorenzo
 *
 */
public class EMFEOperationHeaderLabelProvider extends EMFEObjectHeaderLabelProvider {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 *         <code>true</code> if we are looking for the label of an EStructuralFeature
	 */
	@Override
	public boolean accept(final Object element) {
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			return object instanceof EOperation || object instanceof EOperationAxis;
		}
		return false;
	}

	/**
	 *
	 * @param featureConf
	 *            the configuration to use to know what display in the label
	 * @param configRegistry
	 *            the configRegistry
	 * @param name
	 *            the name of the feature
	 * @param type
	 *            the type of the feature
	 * @param lowerBound
	 *            the lower bound of the feature
	 * @param upperBounds
	 *            the upper bound of the feature
	 * @return
	 * 		the text to display for the feature according to these informations and the preferences of the user
	 */
	protected String getText(final FeatureLabelProviderConfiguration featureConf, final IConfigRegistry configRegistry, final String name, final Object type, final int lowerBound, final int upperBounds) {
		// we collect the required values
		boolean displayName = false;
		try {
			displayName = featureConf.isDisplayName();
		} catch (Exception e) {
			Activator.log.error(e);
		}
		boolean displayMultiplicity = featureConf.isDisplayMultiplicity();
		boolean displayType = featureConf.isDisplayType();

		String displayedText = ""; //$NON-NLS-1$
		if (displayName) {
			displayedText += name;
		}
		if (displayType) {
			if (displayName) {
				displayedText += " : "; //$NON-NLS-1$
			}
			displayedText += getTypeName(configRegistry, type);
		}
		if (displayMultiplicity) {
			displayedText += " ["; //$NON-NLS-1$
			if (upperBounds == -1 && lowerBound <= 1) {
				displayedText += "*"; //$NON-NLS-1$
			} else if (lowerBound == upperBounds) {
				displayedText += Integer.toString(lowerBound);
			} else {
				displayedText += Integer.toString(lowerBound);
				displayedText += ".."; //$NON-NLS-1$
				if (upperBounds == -1) {
					displayedText += "*"; //$NON-NLS-1$
				} else {
					displayedText += Integer.toString(upperBounds);
				}
			}
			displayedText += "]"; //$NON-NLS-1$
		}

		return displayedText;
	}

	/**
	 *
	 * @param featureConf
	 *            the configuration to use to know what display in the label
	 * @param configRegistry
	 *            the configRegistry
	 * @param name
	 *            the name of the feature
	 * @param type
	 *            the type of the feature
	 * @param lowerBound
	 *            the lower bound of the feature
	 * @param upperBounds
	 *            the upper bound of the feature
	 * @return
	 * 		the text to display for the feature according to these informations and the preferences of the user
	 */
	protected String getText(final OperationLabelProviderConfiguration featureConf, final IConfigRegistry configRegistry, final String name, final Object type, final int lowerBound, final int upperBounds) {
		// we collect the required values
		boolean displayName = false;
		try {
			displayName = featureConf.isDisplayName();
		} catch (Exception e) {
			Activator.log.error(e);
		}
		boolean displayMultiplicity = featureConf.isDisplayMultiplicity();
		boolean displayType = featureConf.isDisplayType();

		String displayedText = ""; //$NON-NLS-1$
		if (displayName) {
			displayedText += name;
		}
		if (displayType) {
			if (displayName) {
				displayedText += " : "; //$NON-NLS-1$
			}
			displayedText += getTypeName(configRegistry, type);
		}
		if (displayMultiplicity) {
			displayedText += " ["; //$NON-NLS-1$
			if (upperBounds == -1 && lowerBound <= 1) {
				displayedText += "*"; //$NON-NLS-1$
			} else if (lowerBound == upperBounds) {
				displayedText += Integer.toString(lowerBound);
			} else {
				displayedText += Integer.toString(lowerBound);
				displayedText += ".."; //$NON-NLS-1$
				if (upperBounds == -1) {
					displayedText += "*"; //$NON-NLS-1$
				} else {
					displayedText += Integer.toString(upperBounds);
				}
			}
			displayedText += "]"; //$NON-NLS-1$
		}

		return displayedText;
	}

	/**
	 *
	 * @param configRegistry
	 *            the configRegistry
	 * @param type
	 *            the type of the feature
	 * @return
	 * 		the name to display for the type
	 */
	protected String getTypeName(final IConfigRegistry configRegistry, final Object type) {
		final LabelProviderService serv = getLabelProviderService(configRegistry);
		if (type instanceof EClassifier) {
			return ((EClassifier) type).getName();
		} else {
			return serv.getLabelProvider(type).getText(type);
		}
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(final Object element) {
		final ILabelProviderContextElementWrapper wrapper = (ILabelProviderContextElementWrapper) element;
		final IConfigRegistry configRegistry = wrapper.getConfigRegistry();

		final Object value = getWrappedValue(wrapper);
		EOperation operation = null;
		String alias = "";//$NON-NLS-1$
		if (value instanceof EOperationAxis) {
			operation = ((EOperationAxis) value).getElement();
			alias = ((EOperationAxis) value).getAlias();
		} else if (value instanceof EOperation) {
			operation = (EOperation) value;
		}

		String returnedValue = null;
		if (alias != null && !alias.isEmpty()) {
			returnedValue = alias;
		} else {
			StringBuilder returnedValueBuilder = new StringBuilder(operation.getName());
			if (!operation.getEParameters().isEmpty()) {
				returnedValueBuilder.append("("); //$NON-NLS-1$
				final Iterator<EParameter> parameters = operation.getEParameters().iterator();
				while (parameters.hasNext()) {
					final EParameter parameter = parameters.next();
					returnedValueBuilder.append(parameter.getEType().getName());
					returnedValueBuilder.append(" "); //$NON-NLS-1$
					returnedValueBuilder.append(parameter.getName());
					if (parameters.hasNext()) {
						returnedValueBuilder.append(", "); //$NON-NLS-1$
					}
				}
				returnedValueBuilder.append(")"); //$NON-NLS-1$
			} else {
				returnedValueBuilder.append("()"); //$NON-NLS-1$
			}
			returnedValue = returnedValueBuilder.toString();
		}
		ILabelProviderConfiguration conf = null;
		if (wrapper instanceof LabelProviderCellContextElementWrapper) {
			conf = getLabelConfiguration((LabelProviderCellContextElementWrapper) wrapper);
		}
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayLabel()) {
			returnedValue = ""; //$NON-NLS-1$
		} else {
			if (conf instanceof OperationLabelProviderConfiguration) {
				returnedValue = getText((OperationLabelProviderConfiguration) conf, configRegistry, returnedValue, operation.getEType(), operation.getLowerBound(), operation.getUpperBound());
			} else if (conf instanceof FeatureLabelProviderConfiguration) {
				returnedValue = getText((FeatureLabelProviderConfiguration) conf, configRegistry, returnedValue, operation.getEType(), operation.getLowerBound(), operation.getUpperBound());
			}
		}
		return returnedValue;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(final Object element) {
		Image result = null;
		ILabelProviderConfiguration conf = null;
		if (element instanceof LabelProviderCellContextElementWrapper) {
			conf = getLabelConfiguration((LabelProviderCellContextElementWrapper) element);
		}
		if (conf instanceof ObjectLabelProviderConfiguration && !((ObjectLabelProviderConfiguration) conf).isDisplayIcon()) {
			return null;
		}

		final Object object = getWrappedValue((ILabelProviderContextElementWrapper) element);
		EOperation operation = null;
		if (object instanceof EOperationAxis) {
			operation = ((EOperationAxis) object).getElement();
		} else if (object instanceof EOperation) {
			operation = (EOperation) object;
		}
		if (null != operation) {
			result = EOperationImageRegistry.getOperationIcon();
		}
		return result;
	}

	/**
	 * @param wrapper
	 * @return
	 * 		the wrapped value to use to calculate the label
	 */
	protected Object getWrappedValue(final ILabelProviderContextElementWrapper wrapper) {
		return wrapper.getObject();
	}

}
