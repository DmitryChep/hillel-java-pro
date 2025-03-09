package ua.ithillel.javapro;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Library {
    private List<Book> books;

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        books.add(book);
    }

    public boolean removeBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        return books.remove(book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public int getBookCount() {
        return books.size();
    }
}
