package medicallab.model.service;

import java.util.List;

import medicallab.model.MedicalTest;

public interface MedicalTestService extends GenericService<MedicalTest>{

	List<MedicalTest> findAll();
	
}
