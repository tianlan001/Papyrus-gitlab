The ElementTypesConfigurations model is extended by many language- or domain-specific models in Papyrus. If changes are made to the ElementTypesConfiguration model, *all* other models must be regenerated as well.

Here's a list of known models extending this one (grouped by plug-in):

	- org.eclipse.papyrus.infra.types.rulebased
		- RuleBased.ecore
	- org.eclipse.papyrus.infra.emf.types
		- InvariantContainerRule.ecore
		- SetValuesAdvice.ecore
	- org.eclipse.papyrus.infra.emf.types.ui
		- RuntimeValuesAdvice.ecore
	- org.eclipse.papyrus.uml.types.core
		- ApplyStereotypeAdvice.ecore
		- InvariantStereotypeRule.ecore
		- SetTypeAdvice.ecore
		- StereotypeMatcher.uml
		- StereotypePropertyReferenceEdgeAdvice.ecore
