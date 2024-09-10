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
 *    Artem Tikhomirov (independent) support to handle not-matched new elements (Cleaner) 
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.reconcile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ReconcilerConfigBase implements ReconcilerConfig {
	private static final EClassRecord EMPTY_RECORD = new EClassRecord();
	private final HashMap<EClass, EClassRecord> myEClass2Record;
	private final HashMap<EClass, EClassRecord> myAbstractEClass2SubclassesRecord;
	
	public ReconcilerConfigBase(){
		myEClass2Record = new HashMap<EClass, EClassRecord>();
		myAbstractEClass2SubclassesRecord = new HashMap<EClass, EClassRecord>();
	}
	
	public final Matcher getMatcher(EClass eClass) {
		Matcher result = getRecord(eClass, false).getMatcher();
		if (result != Matcher.FALSE) {
			return result;
		}
		// XXX Correct strategy whould be to look up first *non-default*
		// matcher in the hierarchy, however, for now, expect no more that
		// two records per hierarchy chain (e.g. a nondefault matcher for superclass
		// plus a record with default matcher for subclass
		return getExistingRecordFromHierarchy(eClass).getMatcher();
	}

	public Copier getCopier(EClass eClass) {
		return getRecord(eClass, false).getCopier();
	}

	public Cleaner getCleaner(EClass eClass) {
		return getRecord(eClass, false).getCleaner();
	}

	public final Decision[] getDecisions(EClass eClass) {
		return getRecord(eClass, false).getDecisions();
	}
	
	protected final void setMatcher(EClass eClass, Matcher matcher){
		getRecord(eClass, true).setMatcher(matcher);
	}

	protected final void setMatcher(EClass eClass, EAttribute attribute){  
		checkStructuralFeature(eClass, attribute);
		setMatcher(eClass, new ReflectiveMatcher(attribute));
	}
	
	protected final void setMatcher(EClass eClass, EReference reference) {
		checkStructuralFeature(eClass, reference);
		// XXX Perhaps, for cases, when reference's target is in some other package,
		// might be reasonable to have an alternative matching, non-resolving, just comparing proxyURI?
		setMatcher(eClass, new ReflectiveMatcher(reference));
	}

	protected final void setMatcherForAllSubclasses(EClass eClass, Matcher matcher){
		checkAbstract(eClass);
		getTemplateRecord(eClass, true).setMatcher(matcher);
	}

	protected final void setCopier(EClass eClass, Copier copier){
		getRecord(eClass, true).setCopier(copier);
	}
	
	protected final void setCopierForAllSubclasses(EClass eClass, Copier copier){
		checkAbstract(eClass);
		getTemplateRecord(eClass, true).setCopier(copier);
	}
	
	protected final void setCleaner(EClass eClass, Cleaner cleaner) {
		getRecord(eClass, true).setCleaner(cleaner);
	}

	protected final void setCleanerForAllSubclasses(EClass eClass, Cleaner cleaner) {
		checkAbstract(eClass);
		getTemplateRecord(eClass, true).setCleaner(cleaner);
	}

	private static void checkAbstract(EClass eClass){
		if (!eClass.isAbstract()){
			throw new IllegalArgumentException(
					"This is not safe method that may lead to strange behaviour in case of multiple inheritance. " +
					"We tried to limit its usage as much as possible");
		}
	}

	protected final void addDecision(EClass eClass, Decision decision){
		getRecord(eClass, true).addDecision(decision);
	}
	
	private EClassRecord getRecord(EClass eClass, boolean force){
		EClassRecord result = myEClass2Record.get(eClass);
		if (result == null){
			if (force){
				result = new EClassRecord();
				myEClass2Record.put(eClass, result);
			} else {
				result = getExistingRecordFromHierarchy(eClass);
				if (result != EMPTY_RECORD){
					//cache it for the next time
					myEClass2Record.put(eClass, result);
				}
			}
		}
		return result;
	}

	/**
	 * Looks through the hierarchy of superclasses, checking for registered 
	 * records for abstract classes. 
	 * @return never null, {@link #EMPTY_RECORD} in case none found 
	 */
	private EClassRecord getExistingRecordFromHierarchy(EClass eClass) {
		EClassRecord result= EMPTY_RECORD;
		for (Iterator<EClass> superClasses = eClass.getEAllSuperTypes().iterator(); result == EMPTY_RECORD && superClasses.hasNext();){
			EClass nextSuper = superClasses.next();
			if (nextSuper.isAbstract()) {
				result = getTemplateRecord(nextSuper, false);
			}
		}
		return result;
	}

	private EClassRecord getTemplateRecord(EClass abstractSuperClass, boolean force){
		assert abstractSuperClass.isAbstract();
		EClassRecord result = myAbstractEClass2SubclassesRecord.get(abstractSuperClass);
		if (result == null && force){
			result = new EClassRecord();
			myAbstractEClass2SubclassesRecord.put(abstractSuperClass, result);
		}
		return result == null ? EMPTY_RECORD : result;
	}
	
	private static void checkStructuralFeature(EClass expectedClass, EStructuralFeature feature) {
		if (expectedClass.getEStructuralFeature(feature.getFeatureID()) != feature){
			throw new IllegalArgumentException(MessageFormat.format("Alien feature {0} for EClass {1}", new Object[] {feature, expectedClass}));
		}
	}
	
	protected static final Matcher ALWAYS_MATCH = new Matcher(){
		public boolean match(EObject current, EObject old) {
			return current.eClass().equals(old.eClass());
		}
	};

	private static class EClassRecord {
		private Matcher myMatcher = Matcher.FALSE; 
		private Copier myCopier = Copier.NEVER_COPY;
		private Cleaner myCleaner = new Cleaner();
		private final List<Decision> myDecisions = new LinkedList<Decision>();
		private Decision[] myMakersArray;
		
		public void addDecision(Decision maker){
			myDecisions.add(maker);
			makersSetChanged();
		}
		
		public void setCopier(Copier copier) {
			myCopier = copier;
		}
		
		public void setCleaner(Cleaner cleaner) {
			myCleaner = cleaner;
		}

		public Decision[] getDecisions(){
			if (myMakersArray == null){
				myMakersArray = myDecisions.toArray(new Decision[myDecisions.size()]);
			}
			return myMakersArray;
		}
		
		public void setMatcher(Matcher matcher) {
			myMatcher = matcher;
		}
		
		public Matcher getMatcher() {
			return myMatcher;
		}
		
		public Copier getCopier() {
			return myCopier;
		}
		
		public Cleaner getCleaner() {
			return myCleaner;
		}

		private void makersSetChanged(){
			myMakersArray = null;
		}
	}

}
