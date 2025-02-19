package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	
	public List<Book> findByGenre(String genre);
	
	public boolean existsByTitle(String title);
	
	public Book findByTitle(String title);

	public List<Book> findAll();
	
	public void deleteById(Long id);
}
