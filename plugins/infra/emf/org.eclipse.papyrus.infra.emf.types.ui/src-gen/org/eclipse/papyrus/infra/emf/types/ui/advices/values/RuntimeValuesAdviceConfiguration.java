/**
 */
package org.eclipse.papyrus.infra.emf.types.ui.advices.values;

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration#getViewsToDisplay <em>Views To Display</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage#getRuntimeValuesAdviceConfiguration()
 * @model
 * @generated
 */
public interface RuntimeValuesAdviceConfiguration extends AbstractAdviceBindingConfiguration {
	/**
	 * Returns the value of the '<em><b>Views To Display</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Views To Display</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Views To Display</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage#getRuntimeValuesAdviceConfiguration_ViewsToDisplay()
	 * @model containment="true"
	 * @generated
	 */
	EList<ViewToDisplay> getViewsToDisplay();

} // RuntimeValuesAdviceConfiguration
