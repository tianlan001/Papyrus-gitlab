/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.common.commands.tests;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultDiagramCopyCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultDiagramPasteCommand;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts.CustomClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link DefaultDiagramPasteCommand} class.
 */
@PluginResource("models/ExpansionModelTest.di")
@ActiveDiagram("NewDiagram")
public class DefaultDiagramPasteCommandTest{

	private final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Rule
	public final CustomUMLFactoryFixture fixture = new CustomUMLFactoryFixture(editor);

	@Rule
	public final PapyrusClipboardFixture clipboard = new PapyrusClipboardFixture();

	@Test
	public void pasteElement() {
		org.eclipse.uml2.uml.Class myClass = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("MyClass");
		IGraphicalEditPart editPart = (IGraphicalEditPart) editor.findEditPart(myClass);

		Command copy = new DefaultDiagramCopyCommand(editor.getEditingDomain(), clipboard, singletonList(editPart));

		fixture.execute(copy);

		ICommand paste = new DefaultDiagramPasteCommand(editor.getEditingDomain(), "Paste", clipboard, editor.getActiveDiagram());

		// The copied class has its name set via a structural feature setting
		fixture.assertInvocation("eSetting");

		fixture.execute(paste);

		// Find the copy
		List<String> typeNamesLikeMyClass = editor.getModel().getOwnedTypes().stream()
				.map(NamedElement::getName)
				.filter(containsString(myClass.getName())::matches)
				.collect(Collectors.toList());
		Assert.assertThat(typeNamesLikeMyClass.size(), is(2));
	}
	
	@Test
	public void pasteProperty() {
		
		editor.openDiagram("CompositeDiagram");
		org.eclipse.uml2.uml.Class myClass = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class1");
		IGraphicalEditPart classEditPart = (IGraphicalEditPart) editor.findEditPart(myClass);
		View classNotationView = classEditPart.getNotationView();
		Assert.assertEquals("the Type of class editpart is not correct", CustomClassCompositeEditPart.VISUAL_ID, classNotationView.getType());
		
		
		org.eclipse.uml2.uml.Property myProperty = (org.eclipse.uml2.uml.Property) myClass.getOwnedAttributes().get(0);
		IGraphicalEditPart propertyEditPart = (IGraphicalEditPart) editor.findEditPart(myProperty);
		View compartment = ((IGraphicalEditPart) propertyEditPart.getParent()).getNotationView();
		Assert.assertEquals("The property view is located in the compatment view ", ClassCompositeCompartmentEditPart.VISUAL_ID, compartment.getType());
		
		Command copy = new DefaultDiagramCopyCommand(editor.getEditingDomain(), clipboard, singletonList(propertyEditPart));
		fixture.execute(copy);
		ICommand paste = new DefaultDiagramPasteCommand(editor.getEditingDomain(), "Paste", clipboard, (GraphicalEditPart) classEditPart);
		fixture.execute(paste);
	
		// Find the copied property and verify that it was copied in in the compartment view
		org.eclipse.uml2.uml.Property myCopiedProperty = (org.eclipse.uml2.uml.Property) myClass.getOwnedAttributes().get(1); // the copied property
		IGraphicalEditPart copiedEditPart = (IGraphicalEditPart) editor.findEditPart(myCopiedProperty);
		View propertyContainerView = ((IGraphicalEditPart) copiedEditPart.getParent()).getNotationView();
		Assert.assertEquals("The property view is copied in the compatment view ", ClassCompositeCompartmentEditPart.VISUAL_ID, propertyContainerView.getType());
	}
	
	

	
}
