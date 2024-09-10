/**
 */
package org.eclipse.papyrus.infra.emf.types.ui.advices.values;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.infra.properties.contexts.View;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View To Display</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay#getView <em>View</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage#getViewToDisplay()
 * @model
 * @generated
 */
public interface ViewToDisplay extends EObject {
	/**
	 * Returns the value of the '<em><b>View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View</em>' reference.
	 * @see #setView(View)
	 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage#getViewToDisplay_View()
	 * @model required="true"
	 * @generated
	 */
	View getView();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay#getView <em>View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View</em>' reference.
	 * @see #getView()
	 * @generated
	 */
	void setView(View value);

} // ViewToDisplay
