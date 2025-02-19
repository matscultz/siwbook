package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.BookValidator;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService; 
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private BookValidator bookValidator;
	
	@GetMapping("/book")
	public String getBooks(Model model) {
		model.addAttribute("books", this.bookService.findAllBooks());
		return "books.html";
		}

	@GetMapping("/book/{id}")
	public String getBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.getById(id).get());
		return "book.html"; 
	}
	
	@GetMapping(value="/admin/manageBooks")
	public String manageBooks(Model model) {
		model.addAttribute("books", this.bookService.findAllBooks());
		return "admin/manageBooks.html";
	}
	
	@GetMapping(value="/admin/formUpdateBook/{id}")
	public String formUpdateBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.getById(id).get());
		return "admin/formUpdateBook.html";
	}
	
	@GetMapping(value = "/admin/formNewBook")
	public String addNewBook(Model model) {
		//model.addAttribute("authors", this.authorService.getAll());
		model.addAttribute("genres", Genre.values());
		model.addAttribute("authors", this.authorService.getAllInOrder());
		model.addAttribute("book", new Book());
		return "/admin/formNewBook.html";
	}
	
	@PostMapping(value = "/admin/book")
	public String newBook(@Valid @ModelAttribute("book") Book book, @RequestParam("authorId") Long authorId,
			BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
		Author author = this.authorService.getById(authorId).get();
		if(!this.bookService.existsByTitle(book.getTitle())) {
			String base64Image = Base64.getEncoder().encodeToString(multipartFile.getBytes());;
			book.setPhoto(base64Image);
			book.setAuthor(author);
			this.bookService.save(book);
			model.addAttribute("book", book);
			return "book.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo libro esiste già!");
			return "admin/formNewBook.html";
		}
			/*this.bookValidator.validate(book, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.bookService.save(book);
			model.addAttribute("book", book);
			return "book.html";
		} else {
			return "admin/formNewBook.html";
		} */
	}
	
	@GetMapping(value="/admin/addAuthor/{id}")
	public String addAuthor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("authors", this.authorService.getAll());
		model.addAttribute("book", this.bookService.getById(id).get());
		return "admin/authorToAdd.html";
	}
	
	@GetMapping("/admin/setAuthor/{bookId}/{authorId}") 
	public String setAuthor(Model model, @PathVariable("bookId") Long bookId,
            @PathVariable("authorId") Long authorId) {
		Book book = this.bookService.getById(bookId).get();
		Author author = this.authorService.getById(authorId).get();
       // Movie movie = this.movieRepository.findById(movieId).get();
		this.bookService.setAuthorToBook(book, author);

		model.addAttribute("book", book);
		model.addAttribute("author", book.getAuthor());

        return "admin/formUpdateBook.html";
    }
	
	
	@GetMapping(value="/admin/indexBook")
	public String indexBook() {
		return "admin/indexBook.html";
	}
	
	@GetMapping("/formSearchBooks")
	public String formSearchBooks() {
		return "formSearchBooks.html";
	}

	@PostMapping("/searchBooks")
	public String searchMovies(Model model, @RequestParam String title) {
		model.addAttribute("movies", this.bookService.findByTitle(title));
		return "foundBooks.html";
	}
	
	@GetMapping("/admin/book/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		this.bookService.deleteById(id);
		return "books.html";
	}
	
}
