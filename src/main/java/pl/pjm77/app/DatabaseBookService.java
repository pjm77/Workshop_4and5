package pl.pjm77.app;

import javax.sql.DataSource;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseBookService implements BookService {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseBookService(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS books");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id BIGINT AUTO_INCREMENT NOT " +
          "NULL, isbn VARCHAR(255), title VARCHAR(255), author VARCHAR(255), publisher VARCHAR" +
          "(255), type VARCHAR(255), PRIMARY KEY(id));");

        this.addBook(new Book("9788324631766", "Thinking in Java.", "Bruce Eckel",
          "Helion", "programming"));
        this.addBook(new Book("9788324627738", "Rusz glowa, Java.",
          "Sierra Kathy, Bates Bert", "Helion", "programming"));
        this.addBook(new Book("9780130819338", "Java 2. Podstawy.",
          "Cay Horstmann, Gary Cornell", "Helion", "programming"));
        this.addBook(new Book("9781932394856", "Test Driven.", "Lance Koskela",
          "Manning", "programming"));
        this.addBook(new Book("9780134685991", "Effective Java.", "Joshua Bloch",
          "Addison - Wesley Professional", "programming"));
        this.addBook(new Book("9780132350884", "Clean Code.", "Robert C. Martin",
          "Prentice Hall", "programming"));
        this.addBook(new Book("9780596007126", "Head First. Design Patterns",
          "Eric Freeman, Bert Bates. Kathy Sierra, Elisabeth Robson", "O'Reilly",
          "programming"));
    }

    @Override
    public long addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO books (isbn, title, author, publisher, type) " +
          "VALUES (?, ?, ?, ?, ?)", book.getIsbn(), book.getTitle(), book.getAuthor(),
          book.getPublisher(), book.getType());
    }

    @Override
    public boolean updateBook(Book book) {
        return jdbcTemplate.update("UPDATE book SET isbn=?, title=?, author=?, publisher=?, " +
          "type=?", book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(),
          book.getType()) == 1;
    }

    @Override
    public boolean deleteBook(long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, new Object[] {id}) == 1;
    }

    @Override
    public Book getBookById(long id) {
        return null;
    }

    @Override
    public Map<Long, Book> getBooks() {
        return null;
    }
}