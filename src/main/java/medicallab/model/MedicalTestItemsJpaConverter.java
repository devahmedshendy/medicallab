package medicallab.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MedicalTestItemsJpaConverter implements AttributeConverter<List<String>, String> {

	@Override
	public String convertToDatabaseColumn(List<String> arrayAsList) {
		if (arrayAsList.size() == 0) return "[]";
		
		StringBuilder arrayAsString = new StringBuilder();
		
		arrayAsString.append("[");
		
		String lastItem = arrayAsList.get(arrayAsList.size() - 1);
		for (String item : arrayAsList) {
			arrayAsString.append("\"item\"");
			
			if (! item.equals(lastItem)) {
				arrayAsString.append(", ");
			}
		};
		
		arrayAsString.append("]");
		
		return arrayAsString.toString();
	}

	@Override
	public List<String> convertToEntityAttribute(String arrayAsString) {
		if (arrayAsString.isEmpty()) return new ArrayList<String>();
		
		arrayAsString = arrayAsString.replace("[\'|\\[|\"\\]]", ""); 
		
		return Arrays.asList(arrayAsString.split(", "));
	}

}
