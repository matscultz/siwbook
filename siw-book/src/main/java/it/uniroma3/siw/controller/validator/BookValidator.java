package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Component
public class BookValidator implements Validator{

	@Autowired
	private BookRepository bookRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Book book = (Book)o;
		if(book.getTitle()!=null && book.getYear()!=null
				&& bookRepository.existsByTitle(book.getTitle())) {
			errors.reject("book.duplicate");
		}
		
	}
	
	
}
