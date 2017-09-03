package medicallab.web.model.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.model.Patient;

@Component("patientDAOImpl")
public class PatientDAOImpl extends DAOSessionFactory implements PatientDAO {

	@Override
	public Long create(Patient patient) {
		return (Long) getSession().save(patient);
	}

	@Override
	public List<Patient> findAll() {
		return getSession()
				.createQuery("from Patient", Patient.class)
				.getResultList();
	}
	
	@Override
	public Patient findById(Long id) throws NoSuchPatientException {
		Patient patient;
		try {
			patient =  getSession()
					.createQuery("from Patient where id = :id", Patient.class)
					.setParameter("id", id)
					.getSingleResult();
			
		} catch(NoResultException e) {
			throw new NoSuchPatientException(id.toString());
		}
		
		return patient;
	}

	@Override
	public void update(Patient patient) {
		getSession().update(patient);
	}

	@Override
	public void delete(Patient patient) {
		getSession().delete(patient);
	}

	@Override
	public Map<String, Object> findPagedList(Integer startPosition, Integer maxResult, String nativeQueryString, String nativeCountQueryString) {
		List<Patient> patientList = getSession()
					.createNativeQuery(nativeQueryString, Patient.class)
					.setFirstResult(startPosition)
					.setMaxResults(maxResult)
					.getResultList();
		
		BigInteger resultCount = (BigInteger) getSession()
					.createNativeQuery(nativeCountQueryString)
					.getSingleResult();
		
		Map<String, Object> result = new HashMap<>();
		result.put("pagedList", patientList);
		result.put("noOfElements", resultCount.intValue());
		
		return result;
	}
	
	
	
	@Override
	public Patient findByPatientId(String patientId) throws NoSuchPatientException {
		Patient patient;
		try {
			patient =  getSession()
					.createQuery("from Patient where patientId = :patientId", Patient.class)
					.setParameter("patientId", patientId)
					.getSingleResult();
			
		} catch(NoResultException e) {
			throw new NoSuchPatientException(patientId);
		}
		
		return patient;
	}

	@Override
	public byte[] findProfileImageByPatientId(String patientId) {
		return (byte[]) getSession()
		.createQuery("select profileImage from Patient where patientId = :patientId")
		.setParameter("patientId", patientId)
		.getSingleResult();
	}
}
