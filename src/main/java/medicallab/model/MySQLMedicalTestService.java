package medicallab.model;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import medicallab.model.repository.MedicalTestRepo;
import medicallab.model.service.MedicalTestService;

@Transactional @Primary @Service
public class MySQLMedicalTestService implements MedicalTestService {
	
	private MedicalTestRepo medicalTestRepo;
	
	@Autowired
	public MySQLMedicalTestService(MedicalTestRepo medicalTestRepo) {
		this.medicalTestRepo = medicalTestRepo;
	}

	@Override
	public List<MedicalTest> findAll() {
		// TODO Auto-generated method stub
		return this.medicalTestRepo.findAll();
	}
	
	@Override
	public void create(MedicalTest obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MedicalTest entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MedicalTest entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<MedicalTest> findPagedList(Map<String, String> queryParams) throws NumberFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateFormIntoObject(Object obj, MedicalTest entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateFormFromObject(Object obj, MedicalTest entity) {
		// TODO Auto-generated method stub
		
	}

}
