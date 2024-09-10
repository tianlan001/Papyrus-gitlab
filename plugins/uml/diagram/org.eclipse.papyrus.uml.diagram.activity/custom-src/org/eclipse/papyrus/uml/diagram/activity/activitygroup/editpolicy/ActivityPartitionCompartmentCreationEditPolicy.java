package org.eclipse.papyrus.uml.diagram.activity.activitygroup.editpolicy;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.activity.edit.policies.ActivityCompartmentCreationEditPolicy;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityPartition;


public class ActivityPartitionCompartmentCreationEditPolicy extends ActivityCompartmentCreationEditPolicy {

	/**
	 * Elements graphically shown inside partitions are semantically owned by the activity.
	 * So default reparenting check (of different semantic containers) should be skipped for partition contents.
	 */
	@Override
	protected boolean shouldReparent(EObject element, EObject newContext) {
		if (newContext instanceof ActivityPartition && element.eContainer() instanceof Activity) {
			return element != null &&
					element != newContext;
		}
		return super.shouldReparent(element, newContext);
	}
}
