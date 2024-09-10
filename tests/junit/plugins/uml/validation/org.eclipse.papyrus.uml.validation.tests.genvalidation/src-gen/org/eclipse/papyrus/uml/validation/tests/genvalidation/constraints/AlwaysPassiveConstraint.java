/**
 * Created by the Papyrus DSML plugin generator
 */

package org.eclipse.papyrus.uml.validation.tests.genvalidation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.ecore.EObject;

public class AlwaysPassiveConstraint extends AbstractModelConstraint {

	public IStatus validate(IValidationContext ctx) {
		EObject target = ctx.getTarget();

		if (target instanceof profile.AlwaysPassiveJava) {
			if (evaluateConstraint((profile.AlwaysPassiveJava) target)) {
				return ctx.createSuccessStatus();
			}
			else {
				return ctx.createFailureStatus(""); //$NON-NLS-1$ failure message is in plugin.xml
			}
		}
		return ctx.createSuccessStatus();
	}

	private boolean evaluateConstraint(profile.AlwaysPassiveJava self) {
		return !self.getBase_Class().isActive();
	}

}
