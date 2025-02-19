package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{

	public boolean existsBySurname(String surname);
	
	public boolean existsByNameAndSurname(String name, String surname);
	
	public List<Author> findBySurname(String surname);

	@Query(value="SELECT * "
			+ "FROM public.author "
			+ "ORDER BY surname " 
			+ "ASC", nativeQuery = true)
	List<Author> findAllAuthorsOrderedBySurname();

}
