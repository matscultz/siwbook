package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;

	public List<Author> getAll() {
		return (List<Author>) this.authorRepository.findAll();
	}

	public Optional<Author> getById(Long id) {
		return this.authorRepository.findById(id);
	}

	public boolean existsByNameAndSurname(String name, String surname) {
		return this.authorRepository.existsByNameAndSurname(name, surname);
	}

	public void save(Author author) {
		this.authorRepository.save(author);
	}

	public List<Author> getAllInOrder() {
		return this.authorRepository.findAllAuthorsOrderedBySurname();
	}

}
