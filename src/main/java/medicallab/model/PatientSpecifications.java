package medicallab.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PatientSpecifications {

	public static Specification<Patient> fullnameStartingWith(String searchText) {
		return (root, criteria, builder) -> {
			return builder.like(builder.upper(root.get(Patient_.fullname)), searchText.trim().toUpperCase() + "%");
		};
	}
	
	public static Specification<Patient> patientIdStartingWith(String searchText) {
		return (root, criteria, builder) -> {
			return builder.like(root.get(Patient_.patientId), searchText.trim() + "%");
		};
	}
	
	public static Specification<Patient> genderTypeIs(String genderType) {
		return (root, criteria, builder) -> {
			return builder.like(builder.upper(root.get(Patient_.gender)), genderType + "%");
		};
	}
	
	public static Specification<Patient> fullnameStartingWithAndGenderTypeIs(String searchText, String genderType) {
		return (root, criteria, builder) -> {
			
			if (genderType == null || "any".equals(genderType.trim().toLowerCase()) || genderType.isEmpty()) {
				return fullnameStartingWith(searchText).toPredicate(root, criteria, builder);
			}
			
			
			return builder.and(
					fullnameStartingWith(searchText).toPredicate(root, criteria, builder), 
					genderTypeIs(genderType).toPredicate(root, criteria, builder)); 
		};
	}
	
	public static Specification<Patient> patientIdStartingWithAndGenderTypeIs(String searchText, String genderType) {
		return (root, criteria, builder) -> {

			if (genderType == null || "any".equals(genderType.trim().toLowerCase()) || genderType.isEmpty()) {
				return patientIdStartingWith(searchText).toPredicate(root, criteria, builder);
			}

			return builder.and(
					patientIdStartingWith(searchText).toPredicate(root, criteria, builder),
					genderTypeIs(genderType).toPredicate(root, criteria, builder));
		};
	}
}
