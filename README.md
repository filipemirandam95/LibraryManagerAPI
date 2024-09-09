# Library Manager API 
JAVA RESTful API designed for a Library.

## Class Diagram

```mermaid
classDiagram
    class Book {
        +String title
        +String isbn
        +Author author
        +boolean isBorrowed
        +Book(String title, String isbn, Author author)
        +String getTitle()
        +void setTitle(String title)
        +String getIsbn()
        +void setIsbn(String isbn)
        +Author getAuthor()
        +void setAuthor(Author author)
        +boolean isBorrowed()
        +void setBorrowed(boolean isBorrowed)
    }

    class Author {
        +String name
        +String nationality
        +List<Book> books
        +Author(String name, String nationality)
        +String getName()
        +void setName(String name)
        +String getNationality()
        +void setNationality(String nationality)
        +List<Book> getBooks()
        +void addBook(Book book)
    }

    class User {
        +String name
        +String id
        +User(String name, String id)
        +String getName()
        +void setName(String name)
        +String getId()
        +void setId(String id)
    }

    class Loan {
        +Book book
        +User user
        +LocalDate loanDate
        +LocalDate returnDate
        +Loan(Book book, User user, LocalDate loanDate, LocalDate returnDate)
        +Book getBook()
        +void setBook(Book book)
        +User getUser()
        +void setUser(User user)
        +LocalDate getLoanDate()
        +void setLoanDate(LocalDate loanDate)
        +LocalDate getReturnDate()
        +void setReturnDate(LocalDate returnDate)
        +void returnBook()
    }

    Author "1" -- "0..*" Book : has
    Loan "1" -- "1" Book : involves
    Loan "1" -- "1" User : involves
```
