package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public List<Book> findAllBooks() {
		return this.bookRepository.findAll();
	}

	public Optional<Book> getById(Long id) {
		return this.bookRepository.findById(id);
	}

	public void save(@Valid Book book) {
		this.bookRepository.save(book);
	}

	public boolean existsByTitle(String title) {
		return this.bookRepository.existsByTitle(title);
	}

	@Transactional
	public Book setAuthorToBook(Book book, Author author) {
		book.setAuthor(author);
		author.getBooks().add(book);
		this.bookRepository.save(book);
		return book;
		
	}

	public Object findByTitle(String title) {
		return this.bookRepository.findByTitle(title);
	}

	public void deleteById(Long id) {
		this.bookRepository.deleteById(id);
	}

	/* public Book findById(Long id) {
		return this.bookRepository.findById(id);
	} */

}
