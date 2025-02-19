package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.ReviewValidator;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReviewValidator reviewValidator;
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/user/formNewReview/{id}")
	public String formReview(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.getById(id).get());
		model.addAttribute("review", new Review());
		return "user/formNewReview.html";
	}
	
	@PostMapping("/user/review/{bookId}")
	public String newReview(@ModelAttribute Review review, BindingResult bindingResult,
			@PathVariable("bookId") Long bookId, Model model) {
		// this.reviewValidator.validate(review, bindingResult);
		Book book = this.bookService.getById(bookId).get();
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
	        User user = credentials.getUser();
	        review.setReviewer(user);
	        review.setBook(book);
			// review.setReviewer(this.credentialsService.getCredentials(bookId))
			this.reviewService.save(review);
			book.getReviews().add(review);
			user.getReviews().add(review);
			return "index.html";
	}
	
}
