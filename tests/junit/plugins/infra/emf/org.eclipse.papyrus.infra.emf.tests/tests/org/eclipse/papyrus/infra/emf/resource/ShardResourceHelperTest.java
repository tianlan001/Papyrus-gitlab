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

package org.eclipse.papyrus.infra.emf.resource;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.internal.resource.AbstractCrossReferenceIndex;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for the {@link ShardResourceHelper} class.
 */
@RunWith(Parameterized.class)
public class ShardResourceHelperTest extends AbstractCrossReferenceIndexTest {

	@Rule
	public final AnnotationRule<String> shardElementQualifiedName = AnnotationRule.create(ShardElement.class);

	private final boolean useCommands;
	private EditingDomain domain; // In case we are using commands

	private ShardResourceHelper helper;
	private EObject shardElement;
	private Resource shardResource;

	/**
	 * Initializes me.
	 * 
	 * @param useCommands
	 *            whether to use the shard helper's commands for resource manipulation
	 *            (ensures test coverage)
	 */
	public ShardResourceHelperTest(boolean useCommands) {
		super();

		this.useCommands = useCommands;
	}

	@Test
	@ProjectResource("referencing.uml")
	@ShardElement("referencing::package2::packageB")
	public void makeShard() {
		setShard(true);
		assertAdapter();
		assertAnnotation();

		undo();
		assertAdapter();
		assertNoAnnotation();

		redo();
		assertAdapter();
		assertAnnotation();
	}

	@Test
	@ProjectResource("root.uml")
	@ShardElement("root::package2::packageB")
	public void makeNotShard() {
		setShard(false);
		assertAdapter();
		assertNoAnnotation();

		undo();
		assertAdapter();
		assertAnnotation();

		redo();
		assertAdapter();
		assertNoAnnotation();
	}

	@Test
	@ProjectResource("referencing.uml")
	@ShardElement("referencing::package2::packageB")
	public void makeShardObservedByOtherHelper() {
		ShardResourceHelper other = new ShardResourceHelper(shardElement);
		housekeeper.cleanUpLater(other, ShardResourceHelper::close);

		setShard(true);
		assertThat("Other helper did not observe exec", other.isShard(), is(true));

		undo();
		assertThat("Other helper did not observe undo", other.isShard(), is(false));

		redo();
		assertThat("Other helper did not observe redo", other.isShard(), is(true));
	}

	@Test
	@ProjectResource("root.uml")
	@ShardElement("root::package2::packageB")
	public void makeNotShardObservedByOtherHelper() {
		ShardResourceHelper other = new ShardResourceHelper(shardElement);
		housekeeper.cleanUpLater(other, ShardResourceHelper::close);

		setShard(false);
		assertThat("Other helper did not observe exec", other.isShard(), is(false));

		undo();
		assertThat("Other helper did not observe undo", other.isShard(), is(true));

		redo();
		assertThat("Other helper did not observe redo", other.isShard(), is(false));
	}

	@Test(expected = IllegalStateException.class)
	@ProjectResource("referencing.uml")
	@ShardElement("referencing::package2::packageB")
	public void attemptToUseClosedHelper() {
		helper.close();

		setShard(true);
	}

	//
	// Test framework
	//

	@Parameters(name = "{index}: useCommands={0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ false },
				{ true },
		});
	}

	@BeforeClass
	public static void createProjectContents() {
		createProjectContents("resources/shards");
	}

	@Before
	public void createShardHelper() {
		if (useCommands) {
			// Need an editing domain for command execution
			domain = housekeeper.createSimpleEditingDomain(fixture);
		}

		// Find the shard element
		shardElement = initialResources.stream()
				.flatMap(res -> UMLUtil.findNamedElements(res, shardElementQualifiedName.get()).stream())
				.findAny()
				.orElseThrow(AssertionError::new);

		// Create or find the shard resource
		if (((InternalEObject) shardElement).eDirectResource() != null) {
			shardResource = shardElement.eResource();
		} else {
			shardResource = fixture.createResource(project.getURI("the-shard.uml"));
			if (useCommands) {
				domain.getCommandStack().execute(new AddCommand(domain, shardResource.getContents(), shardElement));
			} else {
				shardResource.getContents().add(shardElement);
			}
		}
		helper = new ShardResourceHelper(shardElement);
	}

	@After
	public void destroyShardHelper() {
		helper.close();
	}

	void setShard(boolean shard) {
		if (useCommands) {
			Command command = helper.getSetShardCommand(shard);
			domain.getCommandStack().execute(command);
		} else {
			helper.setShard(shard);
		}

		assertThat("Changing shard status failed", helper.isShard(), is(shard));
	}

	void undo() {
		if (useCommands) {
			assertThat("Cannot undo", domain.getCommandStack().canUndo(), is(true));
			domain.getCommandStack().undo();
		} else {
			helper.setShard(!helper.isShard());
		}
	}

	void redo() {
		if (useCommands) {
			assertThat("Cannot redo", domain.getCommandStack().canRedo(), is(true));
			domain.getCommandStack().redo();
		} else {
			helper.setShard(!helper.isShard());
		}
	}

	/** Assert that our model element has the shard adapter attached. */
	void assertAdapter() {
		assertThat("Shard adapter not found", findAdapter().isPresent(), is(true));
	}

	/** Assert that our model element does not have the shard adapter attached. */
	void assertNoAdapter() {
		assertThat("Shard adapter found", findAdapter().isPresent(), is(false));
	}

	private Optional<Adapter> findAdapter() {
		return shardElement.eAdapters().stream()
				.filter(a -> a.getClass().getName().contains("ShardResourceHelper$"))
				.findAny();
	}

	/** Assert that our model element has the shard annotation. */
	void assertAnnotation() {
		Optional<EAnnotation> annotation = findAnnotation();
		assertThat("Shard annotation not found", annotation.isPresent(), is(true));
		assertThat("Shard annotation missing back pointer",
				annotation.map(EAnnotation::getReferences).get(),
				hasItem(shardElement.eContainer()));
	}

	/** Assert that our model element does not have the shard annotation. */
	void assertNoAnnotation() {
		assertThat("Shard annotation found", findAnnotation().isPresent(), is(false));
	}

	private Optional<EAnnotation> findAnnotation() {
		return Optional.of(shardElement)
				.filter(EModelElement.class::isInstance).map(EModelElement.class::cast)
				.map(EModelElement::getEAnnotations).map(Collection::stream)
				.map(s -> s.filter(a -> AbstractCrossReferenceIndex.SHARD_ANNOTATION_SOURCE.equals(a.getSource())))
				.flatMap(s -> s.findAny());
	}

	//
	// Nested types
	//

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ShardElement {
		/** The qualified name of the element to make or unmake as a shard. */
		String value();
	}
}
