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
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.deployment.edit.parts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.CellEditorLocatorAccess;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * @generated
 */
public class UMLEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {
			case DeploymentDiagramEditPart.VISUAL_ID:
				return new DeploymentDiagramEditPart(view);
			case DependencyNodeEditPart.VISUAL_ID:
				return new DependencyNodeEditPart(view);
			case MultiDependencyLabelEditPart.VISUAL_ID:
				return new MultiDependencyLabelEditPart(view);
			case ModelEditPart.VISUAL_ID:
				return new ModelEditPart(view);
			case ModelNameEditPart.VISUAL_ID:
				return new ModelNameEditPart(view);
			case PackageEditPart.VISUAL_ID:
				return new PackageEditPart(view);
			case PackageNameEditPart.VISUAL_ID:
				return new PackageNameEditPart(view);
			case ConstraintEditPart.VISUAL_ID:
				return new ConstraintEditPart(view);
			case ConstraintNameEditPart.VISUAL_ID:
				return new ConstraintNameEditPart(view);
			case ConstraintSpecificationEditPart.VISUAL_ID:
				return new ConstraintSpecificationEditPart(view);
			case CommentEditPart.VISUAL_ID:
				return new CommentEditPart(view);
			case CommentBodyEditPart.VISUAL_ID:
				return new CommentBodyEditPart(view);
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				return new ExecutionEnvironmentEditPart(view);
			case ExecutionEnvironmentNameEditPart.VISUAL_ID:
				return new ExecutionEnvironmentNameEditPart(view);
			case DeviceEditPart.VISUAL_ID:
				return new DeviceEditPart(view);
			case DeviceNameEditPart.VISUAL_ID:
				return new DeviceNameEditPart(view);
			case ArtifactEditPart.VISUAL_ID:
				return new ArtifactEditPart(view);
			case ArtifactNameEditPart.VISUAL_ID:
				return new ArtifactNameEditPart(view);
			case ArtifactFloatingLabelEditPart.VISUAL_ID:
				return new ArtifactFloatingLabelEditPart(view);
			case NodeEditPart.VISUAL_ID:
				return new NodeEditPart(view);
			case NodeNameEditPart.VISUAL_ID:
				return new NodeNameEditPart(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return new DefaultNamedElementEditPart(view);
			case DefaultNamedElementNameEditPart.VISUAL_ID:
				return new DefaultNamedElementNameEditPart(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return new DeploymentSpecificationEditPart(view);
			case DeploymentSpecificationNameEditPart.VISUAL_ID:
				return new DeploymentSpecificationNameEditPart(view);
			case DeploymentSpecificationFloatingLabelEditPart.VISUAL_ID:
				return new DeploymentSpecificationFloatingLabelEditPart(view);
			case ModelEditPartCN.VISUAL_ID:
				return new ModelEditPartCN(view);
			case ModelNameEditPartCN.VISUAL_ID:
				return new ModelNameEditPartCN(view);
			case PackageEditPartCN.VISUAL_ID:
				return new PackageEditPartCN(view);
			case PackageNameEditPartCN.VISUAL_ID:
				return new PackageNameEditPartCN(view);
			case DeviceEditPartCN.VISUAL_ID:
				return new DeviceEditPartCN(view);
			case DeviceNameEditPartCN.VISUAL_ID:
				return new DeviceNameEditPartCN(view);
			case NestedDeviceEditPartCN.VISUAL_ID:
				return new NestedDeviceEditPartCN(view);
			case NestedDeviceNameEditPartCN.VISUAL_ID:
				return new NestedDeviceNameEditPartCN(view);
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				return new ExecutionEnvironmentEditPartCN(view);
			case ExecutionEnvironmentNameEditPartCN.VISUAL_ID:
				return new ExecutionEnvironmentNameEditPartCN(view);
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				return new NestedExecutionEnvironmentEditPartCN(view);
			case NestedExecutionEnvironmentNameEditPartCN.VISUAL_ID:
				return new NestedExecutionEnvironmentNameEditPartCN(view);
			case NodeEditPartCN.VISUAL_ID:
				return new NodeEditPartCN(view);
			case NodeNameEditPartCN.VISUAL_ID:
				return new NodeNameEditPartCN(view);
			case NestedNodeEditPartCN.VISUAL_ID:
				return new NestedNodeEditPartCN(view);
			case NestedNodeNameEditPart.VISUAL_ID:
				return new NestedNodeNameEditPart(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return new ArtifactEditPartCN(view);
			case ArtifactNameEditPartCN.VISUAL_ID:
				return new ArtifactNameEditPartCN(view);
			case ArtifactFloatingLabelEditPartCN.VISUAL_ID:
				return new ArtifactFloatingLabelEditPartCN(view);
			case ArtifactEditPartACN.VISUAL_ID:
				return new ArtifactEditPartACN(view);
			case ArtifactNameEditPartACN.VISUAL_ID:
				return new ArtifactNameEditPartACN(view);
			case ArtifactFloatingLabelEditPartACN.VISUAL_ID:
				return new ArtifactFloatingLabelEditPartACN(view);
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				return new NestedArtifactNodeEditPartCN(view);
			case NestedArtifactNameEditPart.VISUAL_ID:
				return new NestedArtifactNameEditPart(view);
			case CommentEditPartCN.VISUAL_ID:
				return new CommentEditPartCN(view);
			case CommentBodyEditPartCN.VISUAL_ID:
				return new CommentBodyEditPartCN(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return new ConstraintEditPartCN(view);
			case ConstraintNameEditPartCN.VISUAL_ID:
				return new ConstraintNameEditPartCN(view);
			case ConstraintSpecificationEditPartCN.VISUAL_ID:
				return new ConstraintSpecificationEditPartCN(view);
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				return new DeploymentSpecAsClassifierEditPart(view);
			case DeploymentSpecAsClassifierNameEditPart.VISUAL_ID:
				return new DeploymentSpecAsClassifierNameEditPart(view);
			case DeploymentSpecAsClassifierFloatingLabelEditPart.VISUAL_ID:
				return new DeploymentSpecAsClassifierFloatingLabelEditPart(view);
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				return new DeploymentSpecAsPackageableElEditPart(view);
			case DeploymentSpecAsPackageableElNameEditPart.VISUAL_ID:
				return new DeploymentSpecAsPackageableElNameEditPart(view);
			case DeploymentSpecAsPackageableElFloatingLabelEditPart.VISUAL_ID:
				return new DeploymentSpecAsPackageableElFloatingLabelEditPart(view);
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				return new DeploymentSpecAsNestedArtifactEditPart(view);
			case DeploymentSpecAsNestedArtifactNameEditPart.VISUAL_ID:
				return new DeploymentSpecAsNestedArtifactNameEditPart(view);
			case DeploymentSpecAsNestedArtifactFloatingLabelEditPart.VISUAL_ID:
				return new DeploymentSpecAsNestedArtifactFloatingLabelEditPart(view);
			case ModelPackageableElementCompartmentEditPart.VISUAL_ID:
				return new ModelPackageableElementCompartmentEditPart(view);
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				return new PackagePackageableElementCompartmentEditPart(view);
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
				return new DeviceCompositeCompartmentEditPart(view);
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
				return new ExecutionEnvironmentCompositeCompartmentEditPart(view);
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
				return new NodeCompositeCompartmentEditPart(view);
			case ArtifactCompositeCompartmentEditPart.VISUAL_ID:
				return new ArtifactCompositeCompartmentEditPart(view);
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				return new ModelPackageableElementCompartmentEditPartCN(view);
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				return new PackagePackageableElementCompartmentEditPartCN(view);
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
				return new DeviceCompositeCompartmentEditPartCN(view);
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
				return new ExecutionEnvironmentCompositeCompartmentEditPartCN(view);
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
				return new NodeCompositeCompartmentEditPartCN(view);
			case ArtifactCompositeCompartmentEditPartCN.VISUAL_ID:
				return new ArtifactCompositeCompartmentEditPartCN(view);
			case ArtifactCompositeCompartmentEditPartACN.VISUAL_ID:
				return new ArtifactCompositeCompartmentEditPartACN(view);
			case LinkDescriptorEditPart.VISUAL_ID:
				return new LinkDescriptorEditPart(view);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return new CommentAnnotatedElementEditPart(view);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return new ConstraintConstrainedElementEditPart(view);
			case DeploymentEditPart.VISUAL_ID:
				return new DeploymentEditPart(view);
			case DeploymentNameEditPart.VISUAL_ID:
				return new DeploymentNameEditPart(view);
			case DeploymentAppliedStereotypeEditPart.VISUAL_ID:
				return new DeploymentAppliedStereotypeEditPart(view);
			case ManifestationEditPart.VISUAL_ID:
				return new ManifestationEditPart(view);
			case ManifestationNameEditPart.VISUAL_ID:
				return new ManifestationNameEditPart(view);
			case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
				return new ManifestationAppliedStereotypeEditPart(view);
			case GeneralizationEditPart.VISUAL_ID:
				return new GeneralizationEditPart(view);
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return new GeneralizationAppliedStereotypeEditPart(view);
			case DependencyEditPart.VISUAL_ID:
				return new DependencyEditPart(view);
			case DependencyNameEditPart.VISUAL_ID:
				return new DependencyNameEditPart(view);
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return new DependencyAppliedStereotypeEditPart(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return new DependencyBranchEditPart(view);
			case CommunicationPathEditPart.VISUAL_ID:
				return new CommunicationPathEditPart(view);
			case CommunicationPathNameEditPart.VISUAL_ID:
				return new CommunicationPathNameEditPart(view);
			case CommunicationPathAppliedStereotypeEditPart.VISUAL_ID:
				return new CommunicationPathAppliedStereotypeEditPart(view);
			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		if (source.getFigure() instanceof IMultilineEditableFigure) {
			return new MultilineCellEditorLocator((IMultilineEditableFigure) source.getFigure());
		} else {
			return CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
		}
	}

	/**
	 * @generated
	 */
	static private class MultilineCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private IMultilineEditableFigure multilineEditableFigure;

		/**
		 * @generated
		 */
		public MultilineCellEditorLocator(IMultilineEditableFigure figure) {
			this.multilineEditableFigure = figure;
		}

		/**
		 * @generated
		 */
		public IMultilineEditableFigure getMultilineEditableFigure() {
			return multilineEditableFigure;
		}

		/**
		 * @generated
		 */
		@Override
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getMultilineEditableFigure().getBounds().getCopy();
			rect.x = getMultilineEditableFigure().getEditionLocation().x;
			rect.y = getMultilineEditableFigure().getEditionLocation().y;
			getMultilineEditableFigure().translateToAbsolute(rect);
			if (getMultilineEditableFigure().getText().length() > 0) {
				rect.setSize(new Dimension(text.computeSize(rect.width, SWT.DEFAULT)));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
