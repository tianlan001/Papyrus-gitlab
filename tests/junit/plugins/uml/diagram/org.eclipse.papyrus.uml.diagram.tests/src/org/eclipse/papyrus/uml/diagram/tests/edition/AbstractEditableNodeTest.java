/*******************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 464647
 *   Christian W. Damus - bug 476436
 *     
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.edition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.TextCellEditorEx;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.uml.diagram.common.directedit.MultiLineCellEditor;
import org.eclipse.papyrus.uml.diagram.common.directedit.MultilineLabelDirectEditManager;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.IDirectEdition;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure;
import org.junit.Assert;
import org.junit.Rule;

/**
 * Abstract test case definitions for testing direct-edit functionality of editable node edit-parts.
 */
public abstract class AbstractEditableNodeTest extends EditableElementTest {
	@Rule
	public final AnnotationRule<Integer> expectedKind = AnnotationRule.create(ExpectedDirectEditor.class, ExpectedDirectEditor.DEFAULT_MASK);

	protected Command command = null;

	@Override
	public void testEdition(IElementType type) {
		GraphicalEditPart createdEditpart = createNodeOptionally(type, getContainerEditPart());
		if (!(createdEditpart instanceof ITextAwareEditPart)) {
			EditPart nameEditpart = createdEditpart.getPrimaryChildEditPart();
			if (nameEditpart instanceof ITextAwareEditPart) {
				GraphicalEditPart editableEditPart = (GraphicalEditPart) nameEditpart;
				final IFigure nameFigure = editableEditPart.getFigure();
				final Rectangle bounds = nameFigure.getBounds();
				final Point center = bounds.getCenter();
				final DirectEditRequest directEditRequest = new DirectEditRequest();
				directEditRequest.setLocation(center);
				editableEditPart.performRequest(directEditRequest);

				// test if there is not primary editpart
				Assert.assertNull("The editpart must not references primarychildEditpart", editableEditPart.getPrimaryChildEditPart());

				// to increase the covering
				// getLabelTextHelper to refactor
				// setLabelTextHelper to refactor
				// getLabelIconHelper to refactor
				// setLabelIconHelper to refactor
				// setParser to refactor
				// performDirectEdit(Point eventLocation) {
				// performDirectEdit(char initialCharacter) {
				// performDirectEditRequest(Request request) {
				// initializeDirectEditManager(final Request request) {
				// initExtendedEditorConfiguration() {
				// updateExtendedEditorConfiguration() {
				// performDefaultDirectEditorEdit(final Request theRequest) {
				// handleNotificationEvent(Notification event) {

				// getDirectEditionType
				// Mode reflexive to Test the editor associated to the element
				Class<?> editableEditPartClass = editableEditPart.getClass();
				try {
					// test is editable
					Method methodisEditable = getMethodRecusrively(editableEditPartClass, "isEditable");
					if (methodisEditable != null) {
						methodisEditable.setAccessible(true);
						Object resultdirectEditorType = methodisEditable.invoke(editableEditPart);
						Assert.assertEquals("the editpart must be editable", true, resultdirectEditorType);
					}
					//
					Method methodgetEditTextValidator = getMethodRecusrively(editableEditPartClass, "getEditTextValidator");
					if (methodgetEditTextValidator != null) {
						methodgetEditTextValidator.setAccessible(true);
						Object result = methodgetEditTextValidator.invoke(editableEditPart);
						Assert.assertNotNull("the editpart must be editable", result);
						ICellEditorValidator cellvalidato = (ICellEditorValidator) result;
						Assert.assertEquals("the text is valid", null, cellvalidato.isValid("MyElement"));

					}




					// performDirectEdit(Point eventLocation) {
					// performDirectEdit(char initialCharacter) {

					// call and test kind of editor
					int directEditorType = IDirectEdition.UNDEFINED_DIRECT_EDITOR;
					Method methodgetDirectEditionType = getMethodRecusrively(editableEditPartClass, "getDirectEditionType");
					if (methodgetDirectEditionType != null) {
						directEditorType = (Integer) methodgetDirectEditionType.invoke(editableEditPart);
						Assert.assertEquals("wrong kind of direct editor", expectedKind.get() & directEditorType, directEditorType);
					}
					// call and test current editor if supported and it is not contributed by some external plug-in
					if (directEditorType == IDirectEdition.DEFAULT_DIRECT_EDITOR) {
						Method methodGetManager = getMethodRecusrively(editableEditPartClass, "getManager");
						if (methodGetManager != null) {
							methodGetManager.setAccessible(true);
							Object result = methodGetManager.invoke(editableEditPart);
							Assert.assertTrue("the manager to edit name must be a MultilineLabelDirectEditManager", result instanceof MultilineLabelDirectEditManager);
							MultilineLabelDirectEditManager manager = (MultilineLabelDirectEditManager) result;
							IFigure fig = getPrimaryFigure(editableEditPart);

							if (fig instanceof IMultilineEditableFigure) {
								Assert.assertEquals("the editor of this editpart must be multiline editor", MultiLineCellEditor.class, manager.getTextCellEditorClass(editableEditPart));

							} else {
								Assert.assertEquals("the editor of this editpart must be simple editor", TextCellEditorEx.class, manager.getTextCellEditorClass(editableEditPart));
							}
						}
					}
				} catch (NoSuchMethodException e) {
					Assert.fail(e.getMessage());
				} catch (SecurityException e) {
					Assert.fail(e.getMessage());
				} catch (IllegalAccessException e) {
					Assert.fail(e.getMessage());
				} catch (IllegalArgumentException e) {
					Assert.fail(e.getMessage());
				} catch (InvocationTargetException e) {
					Assert.fail(e.getMessage());
				}

			}
		}
	}

	public Method getMethodRecusrively(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		try {
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (clazz.getSuperclass() != null) {
				return getMethodRecusrively(superClazz, methodName, parameterTypes);
			} else {
				throw e;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

}
