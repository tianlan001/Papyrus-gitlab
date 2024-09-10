package org.eclipse.papyrus.infra.emf.types.advices.constraints;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceFactory;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;

public class ConstraintAdviceEditHelperAdviceFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {

	public ConstraintAdviceEditHelperAdviceFactory() {
		super();
	}

	@Override
	protected IEditHelperAdvice getEditHelperAdvice(AbstractAdviceBindingConfiguration adviceConfiguration) {
		IEditHelperAdvice result;

		if (adviceConfiguration instanceof ConstraintAdviceConfiguration) {
			result = new ConstraintAdviceEditHelperAdvice();
		} else {
			result = NullEditHelperAdvice.getInstance();
		}

		return result;
	}

	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return ConstraintAdviceFactory.eINSTANCE.createConstraintAdviceConfiguration();
	}

}
