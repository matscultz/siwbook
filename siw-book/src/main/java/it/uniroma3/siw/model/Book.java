package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	@Min(1700)
	@Max(2025)
	private Integer year;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Genre genre;
	
	@Column(nullable = false, length = 100000000)
	private String photo;
	
	@Column(nullable = false, length = 3000)
	private String plot;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@ManyToOne
	private Author author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Book(Long id, String title, Integer year, Genre genre, String photo, String plot,
			List<Review> reviews, Author author) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.photo = photo;
		this.plot = plot;
		this.reviews = reviews;
		this.author = author;
	}
	
	public Book() {
	}
}
