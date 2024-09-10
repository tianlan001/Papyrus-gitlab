/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.reconcile;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.papyrus.gmf.internal.common.Activator;

public class Reconciler {
	private final ReconcilerConfig myConfig;
	// Maps EObject referenced elsewhere to a setting representing the use
	private final Map<EObject, List<Setting>> myCrossRefsToFix;
	private final Map<EObject, EObject> myMatches;
	private boolean myIsMatching;

	private final boolean traceMatches;
	private final boolean traceDecision;
	private final boolean traceFeatureInDecision;
	private final boolean traceCrossRefUpdate;

	public Reconciler(ReconcilerConfig config){
		myConfig = config;
		myCrossRefsToFix = new LinkedHashMap<EObject, List<Setting>>();
		myMatches = new LinkedHashMap<EObject, EObject>();
		final String recon = "/reconciler/", op1 = "traceMatches", op2 = "traceDecision", op3 = "/features", op4 = "traceCrossRefUpdate";
		traceMatches = Boolean.parseBoolean(Platform.getDebugOption(Activator.getID() + recon + op1));
		traceDecision = Boolean.parseBoolean(Platform.getDebugOption(Activator.getID() + recon + op2));
		traceFeatureInDecision = Boolean.parseBoolean(Platform.getDebugOption(Activator.getID() + recon + op2 + op3));
		traceCrossRefUpdate = Boolean.parseBoolean(Platform.getDebugOption(Activator.getID() + recon + op4));
	}

	protected void handleNotMatchedCurrent(EObject current){
		Cleaner cleaner = myConfig.getCleaner(current.eClass());
		cleaner.clear(current);
	}
	
	protected EObject handleNotMatchedOld(EObject currentParent, EObject notMatchedOld) {
		Copier copier = myConfig.getCopier(notMatchedOld.eClass());
		return copier.copyToCurrent(currentParent, notMatchedOld, this);
	}

	public void reconcileResource(Resource current, Resource old){
		reconcileContents(null, current.getContents(), old.getContents());
		updateCrossReferences();
	}
	
	public void reconcileTree(EObject currentRoot, EObject oldRoot){
		internalReconcileTree(currentRoot, oldRoot);
		updateCrossReferences();
	}
	
	protected void reconcileVertex(EObject current, EObject old){
		assert current.eClass().equals(old.eClass());
		registerMatch(current, old);
		for (Decision decision : myConfig.getDecisions(current.eClass())){
			decision.apply(current, old);
			if (traceDecision) {
				trace(traceFeatureInDecision ? "[decision] %s (%s)" : "[decision] %s", decision.getClass().getName(), decision.getFeature().getName());
			}
		}
	}

	protected void internalReconcileTree(EObject currentRoot, EObject oldRoot){
		reconcileVertex(currentRoot, oldRoot);
		reconcileContents(currentRoot, currentRoot.eContents(), oldRoot.eContents());
	}

	protected void registerMatch(EObject current, EObject old) {
		myMatches.put(old, current);
		if (traceMatches) {
			trace("[matched]%s -> %s", old.eClass().getName(), current.eClass().getName());
		}
	}

	protected void updateCrossReferences() {
		for (Map.Entry<EObject, List<Setting>> e : myCrossRefsToFix.entrySet()) {
			final EObject oldReferenceTarget = e.getKey();
			if (myMatches.containsKey(oldReferenceTarget)) {
				EObject copied = myMatches.get(oldReferenceTarget);
				if (traceCrossRefUpdate) {
					trace("[crossRefUpd] matched %s -> %s", oldReferenceTarget, copied);
				}
				for (Setting s : e.getValue()) {
					if (myMatches.containsKey(s.getEObject())) {
						EObject newOwner = myMatches.get(s.getEObject());
						if (traceCrossRefUpdate) {
							trace("[crossRefUpd] updating '%s' value of %s", s.getEStructuralFeature().getName(), newOwner);
						}
						if (s.getEStructuralFeature().isMany() || FeatureMapUtil.isMany(s.getEObject(), s.getEStructuralFeature())) {
							@SuppressWarnings("unchecked")
							List<EObject> values = (List<EObject>) newOwner.eGet(s.getEStructuralFeature());
							assert !values.contains(copied); // sanity, wonder if that may happen, ever
							if (values.contains(oldReferenceTarget)) {
								// replace old value, keep position
								values.set(values.indexOf(oldReferenceTarget), copied);
							} else {
								values.add(copied);
							}
						} else {
							newOwner.eSet(s.getEStructuralFeature(), copied);
						}
					} else {
						if (traceCrossRefUpdate) {
							trace("[crossRefUpd] no matching owner for %s (old owner: %s)", s.getEStructuralFeature().getName(), s.getEObject());
						}
					}
				}
			} else {
				if (traceCrossRefUpdate) {
					trace("[crossRefUpd] no match for old %s", oldReferenceTarget);
				}
			}
		}
		// TODO Auto-generated method stub
	}

	/* package-local */void registerCrossReferencesToUpdate(Map<EObject, Collection<Setting>> crossReferences) {
		for (Map.Entry<EObject, Collection<Setting>> e : crossReferences.entrySet()) {
			List<Setting> entries = myCrossRefsToFix.get(e.getKey());
			if (entries == null) {
				entries = new LinkedList<Setting>();
				myCrossRefsToFix.put(e.getKey(), entries);
			}
			for (Setting s : e.getValue()) {
				if (s.getEStructuralFeature().isChangeable()) {
					entries.add(s);
				}
			}
			if (entries.isEmpty()) {
				myCrossRefsToFix.remove(e.getKey()); // none changeable, no reason to keep empty list 
			}
		}
	}

	private void reconcileContents(EObject currentParent, Collection<EObject> allCurrents, Collection<EObject> allOlds) {
		if (allCurrents.isEmpty() && allOlds.isEmpty()) {
			return;
		}
		final List<Pair> storage = new LinkedList<Pair>();
		match(allCurrents, allOlds, storage);

		for (Pair next : storage) {
			EObject nextCurrent = next.current;
			EObject nextOld = next.old;
			assert (nextCurrent != null || nextOld != null);

			if (nextCurrent == null) {
				if (currentParent != null) { // never copy top-level resource contents
					nextCurrent = handleNotMatchedOld(currentParent, nextOld);
				}
			}

			if (nextCurrent != null && nextOld != null) {
				internalReconcileTree(nextCurrent, nextOld);
			} else if (nextOld == null) {
				handleNotMatchedCurrent(nextCurrent);
			}
		}
	}
	
	private void match(Collection<EObject> currents, Collection<EObject> olds, Collection<Pair> output) {
		assert !myIsMatching;

		final Collection<EObject> myOlds;
		final Collection<EObject> myCurrents;
		try {
			myIsMatching = true;

			myOlds = new LinkedHashSet<EObject>(olds);
			myCurrents = new LinkedList<EObject>(currents);

			for (Iterator<EObject> currentContents = myCurrents.iterator(); !myOlds.isEmpty() && currentContents.hasNext();) {
				EObject nextCurrent = currentContents.next();
				EObject matchedOld = removeMatched(nextCurrent, myOlds);
				output.add(new Pair(nextCurrent, matchedOld));
				currentContents.remove();
			}

			for (Iterator<EObject> notMatchedOlds = myOlds.iterator(); notMatchedOlds.hasNext();) {
				output.add(new Pair(null, notMatchedOlds.next()));
			}
		} finally {
			myIsMatching = false;
		}
	}
		
	private EObject removeMatched(EObject current, Collection<EObject> allOld) {
		EClass eClass = current.eClass();
		Matcher matcher = myConfig.getMatcher(eClass);
		EObject result = null;
		if (matcher != Matcher.FALSE) {
			for (Iterator<EObject> all = allOld.iterator(); all.hasNext();) {
				EObject next = all.next();
				if (eClass.equals(next.eClass()) && matcher.match(current, next)) {
					result = next;
					all.remove();
					break;
				}
			}
		}
		return result;
	}

	private static void trace(String format, Object... args) {
		Activator.log(new Status(IStatus.INFO, Activator.getID(), String.format(format, args)));
	}

	private static class Pair {
		public final EObject current;
		public final EObject old;

		public Pair(EObject cur, EObject old) {
			this.current = cur;
			this.old = old;
		}
	}
}
