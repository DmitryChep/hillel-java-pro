package ua.ithillel.javapro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        library = new Library(new ArrayList<>());
        book1 = new Book("Title1", "Author1");
        book2 = new Book("Title2", "Author2");
    }

    @Test
    public void addBook_shouldIncreaseBookCount_whenBookIsAdded() {
        library.addBook(book1);
        assertEquals(1, library.getBookCount());
        assertTrue(library.getBooks().contains(book1));
    }

    @Test
    public void removeBook_shouldDecreaseBookCount_whenBookIsRemoved() {
        library.addBook(book1);
        boolean removed = library.removeBook(book1);
        assertTrue(removed);
        assertEquals(0, library.getBookCount());
        assertFalse(library.getBooks().contains(book1));
    }

    @Test
    public void removeBook_shouldReturnFalse_whenBookDoesNotExist() {
        boolean removed = library.removeBook(book1);
        assertFalse(removed);
    }

    @Test
    public void addBook_shouldThrowException_whenBookIsNull() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
    }

    @Test
    public void removeBook_shouldThrowException_whenBookIsNull() {
        assertThrows(IllegalArgumentException.class, () -> library.removeBook(null));
    }

    @Test
    public void getAllBooks_shouldReturnList_whenBooksExist() {
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.getBooks().size());
        assertTrue(library.getBooks().contains(book1));
        assertTrue(library.getBooks().contains(book2));
    }

    @Test
    public void getBookCount_shouldReturnZero_whenNoBooksExist() {
        assertEquals(0, library.getBookCount());
    }

    @Test
    public void getBookCount_shouldReturnCorrectCount_whenBooksExist() {
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.getBookCount());
    }
}
