package org.example.repository;

import org.example.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(UUID.fromString(rs.getString("id")));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getInt("publication_year"));
        return book;
    };

    @Override
    public Optional<Book> findById(UUID id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        List<Book> result = jdbcTemplate.query(sql, rowMapper, id.toString());
        return result.stream().findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            UUID newId = UUID.randomUUID();
            book.setId(newId);
            String sql = "INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    book.getId().toString(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationYear());
        } else {
            String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationYear(),
                    book.getId().toString());
        }
        return book;
    }

    @Override
    public boolean existsById(UUID id) {
        String sql = "SELECT COUNT(*) FROM books WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.toString());
        return count != null && count > 0;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id.toString());
    }
}

