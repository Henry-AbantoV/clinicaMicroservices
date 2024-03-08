package edu.unc.clinica.DetailsAuth;

import java.util.Collection;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;



@NoRepositoryBean
public class RefreshableCRUDRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements RefreshableCRUDRepository<T, ID> {

	private final EntityManager entityManager;

	private RefreshableCRUDRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void flush() {
		entityManager.flush();
	}

	@Override
	@Transactional
	public void refresh(T t) {
		entityManager.refresh(t);
	}

	@Override
	public void refresh(Collection<T> s) {
		// TODO Auto-generated method stub

	}
}
