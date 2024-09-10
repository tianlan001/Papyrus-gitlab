package org.eclipse.papyrus.infra.services.edit.tests.context;

import static org.junit.Assert.fail;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Test;

/**
 * JUnit tests Papyrus ElementTypeRegistry binding context.
 */
public class TestClientContext extends AbstractPapyrusTest {

	@Test
	public void testClientContextExist() {
		try {
			TypeContext.getDefaultContext();
		} catch (ServiceException e) {
			fail("Papyrus IClientContext could not be found.");
		}
	}

}
