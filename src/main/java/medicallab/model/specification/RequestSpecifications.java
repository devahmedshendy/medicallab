package medicallab.model.specification;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import medicallab.model.Request;
import medicallab.model.Request_;

public class RequestSpecifications {

	public static Specification<Request> searchAndFilterForPatientFullnameLike(String searchText, String testTypeListAsString, String requestStatusListAsString) {
		return (root, query, builder) -> {
			return builder.and(
					patientFullnameLike(searchText).toPredicate(root, query, builder),
					testTypeIn(testTypeListAsString).toPredicate(root, query, builder),
					requestStatusIn(requestStatusListAsString).toPredicate(root, query, builder)
					);
			
		};
	}
	
	public static Specification<Request> searchAndFilterForPatientIdLike(String searchText, String testTypeListAsString, String requestStatusListAsString) {
		return (root, query, builder) -> {
			return builder.and(
					patientIdLike(searchText).toPredicate(root, query, builder),
					testTypeIn(testTypeListAsString).toPredicate(root, query, builder),
					requestStatusIn(requestStatusListAsString).toPredicate(root, query, builder)
					);
		};
	}
	
	public static Specification<Request> searchAndFilterForDoctorFullnameLike(String searchText, String testTypeListAsString, String requestStatusListAsString) {
		return (root, query, builder) -> {
			return builder.and(
					doctorFullnameLike(searchText).toPredicate(root, query, builder),
					testTypeIn(testTypeListAsString).toPredicate(root, query, builder),
					requestStatusIn(requestStatusListAsString).toPredicate(root, query, builder)
					);
		};
	}
	

	public static Specification<Request> searchAndFilterForRequestCodeLike(String searchText, String testTypeListAsString, String requestStatusListAsString) {
		return (root, query, builder) -> {
			return builder.and(
					requestCodeLike(searchText).toPredicate(root, query, builder),
					testTypeIn(testTypeListAsString).toPredicate(root, query, builder),
					requestStatusIn(requestStatusListAsString).toPredicate(root, query, builder)
					);
		};
	}
	
	
	
	public static Specification<Request> patientFullnameLike(String searchText) {
		return (root, query, builder) -> {
			Expression<String> patientFullnameExpression = builder.upper(root.get(Request_.patientFullname));
			
			return builder.like(patientFullnameExpression, searchText.toUpperCase() + "%");
		};
	}
	
	public static Specification<Request> patientIdLike(String searchText) {
		return (root, query, builder) -> {
			Expression<String> patientIdExpression = root.get(Request_.patientId);
			
			return builder.like(patientIdExpression, searchText + "%");
		};
	}
	
	public static Specification<Request> doctorFullnameLike(String searchText) {
		return (root, query, builder) -> {
			Expression<String> doctorFullnameExpression = root.get(Request_.doctorFullname);
			
			return builder.like(doctorFullnameExpression, searchText + "%");
		};
	}
	
	public static Specification<Request> requestCodeLike(String searchText) {
		return (root, query, builder) -> {
			Expression<String> requestCodeExpression = root.get(Request_.requestCode);
			
			return builder.like(requestCodeExpression, searchText + "%");
		};
	}
	

	public static Specification<Request> testTypeIn(String testTypeListAsString) {
		return (root, query, builder) -> {
			Expression<String> testTypeExpression = root.get(Request_.testType); 
			
			if (testTypeListAsString == null || testTypeListAsString.length() == 0) {
				return builder.like(testTypeExpression, "%");
			}
			
			List<String> testTypeList = Arrays.asList(testTypeListAsString.split(","));
			return testTypeExpression.in(testTypeList);
		};
	}
	
	public static Specification<Request> requestStatusIn(String requestStatusListAsString) {
		return (root, query, builder) -> {
			Expression<String> requestStatusExpression = builder.upper(root.get(Request_.requestStatus));
			
			if (requestStatusListAsString == null || requestStatusListAsString.length() == 0) {
				return builder.like(requestStatusExpression, "%");
			}
			
			List<String> requestStatusList = Arrays.asList(requestStatusListAsString.split(","));
			return requestStatusExpression.in(requestStatusList);
		};
	}
}
