package medicallab.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MedicalTestResultJpaConverter implements AttributeConverter<Map<String, Double>, String> {

	@Override
	public String convertToDatabaseColumn(Map<String, Double> testResultMap) {
		if (testResultMap.size() == 0)
			return "{}";

		StringBuilder testResultString = new StringBuilder();

		testResultString.append("{");

		for (Map.Entry<String, Double> entry : testResultMap.entrySet()) {
			String itemWithValue = String.format("\"%s\": %s", entry.getKey(), entry.getValue().toString());

			testResultString.append(itemWithValue);
			testResultString.append(", ");
		}

		int stringLength = testResultString.length();
		testResultString.replace(stringLength - 2, stringLength - 1, "");

		testResultString.append("}");

		return testResultString.toString();
	}

	@Override
	public Map<String, Double> convertToEntityAttribute(String testResultString) {
		if (testResultString.isEmpty())
			return new LinkedHashMap<>();

		testResultString = testResultString.replaceAll("[\'|\"|{|}]", "");

		Map<String, Double> testResultMap = new LinkedHashMap<>();
		for (String itemWithValue : testResultString.split(", ")) {
			String item = itemWithValue.split(": ")[0];
			double value = Double.parseDouble(itemWithValue.split(": ")[1]);
			testResultMap.put(item, value);
		}

		return testResultMap;
	}

}
