import java.util.ArrayList;
import java.util.Iterator;

/**
 * Administra los libros de una biblioteca. 
 * Library representa una biblioteca, con su nombre, capacidad máxima de 
 * la biblioteca (cuántos libros puede alojar), y lista de libros de la biblioteca.
 * 
 * @author N. Aguirre 
 * @version 0.1
 */
public class Library
{
    // nombre de la biblioteca
    private String name;
    // capacidad de la biblioteca (cuántos libros puede alojar como máximo)
    private int bookCapacity;
    // La lista de libros de la biblioteca
    private ArrayList<Book> books;

    /**
     * Constructor de Library.
     */
    public Library(String name, int bookCapacity)
    {
        if (name == null || name.equals("")) throw new IllegalArgumentException();
        if (bookCapacity <= 0) throw new IllegalArgumentException();
        this.books = new ArrayList<Book>();
        this.name = name;
        this.bookCapacity = bookCapacity;
        assert repOK():"error en la inicializacionde los objetos del constructor";
    }

    /**
     * Agrega un libro a la biblioteca
     * @param book es el libro a agregar
     * Precondición: Agregar el libro no debe exceder la capacidad de la biblioteca (ejemplares de los libros existentes 
     * más los nuevos ejemplares no debe superar la capacidad).
     * Precondición: No debe haber en la biblioteca un libro con el mismo id que el que se agrega.
     * Postcondición: se agrega el libro a la lista de libros de la biblioteca. La lista de libros se debe mantener
     * ordenada por id, con lo cual la inserción debe ubicar el libro en la posición ordenada correspondiente.
     */
    public void addBook(Book book)
    {
        // chequea incorrectamente capacidad.
        // no inserta ordenadamente
        for(Book b : books){
            int currId = b.getId();
            if(currId == book.getId()){
                throw new IllegalArgumentException("ya existe un libro con esa ID");
            }
            if(bookCapacity == books.size()){
                throw new IllegalArgumentException("la biblioteca esta llena");
            }
        }
        books.add(book);
        assert repOK():"error en el metodo addBook";
    }
    
    /**
     * Presta un ejemplar de un libro a la biblioteca
     * @param id es el id del libro a prestar.
     * Precondición: Existe un libro con el id indicado, y hay al menos una copia en existencia para prestar.
     * Postcondición: Se actualiza el número de copias en existencia del libro correspondiente, disminuyendo la
     * candidad de copias disponibles en uno.
     * Importante: Este algoritmo debe tener complejidad O(log n) en el peor caso.
     */
    public void lendBook(int id)
    {
        Book book = null;
        for(int i = 0 ; i < books.size() ; i++){
            int currBook = books.get(i).getId();
            if(currBook != id){
                throw new IllegalArgumentException("no existe un libro con esa ID"); // mal!
            }
            if(books.get(i).getCopiesAvailable() < 1){
                throw new IllegalArgumentException("no hay copias disponibles para prestar");
            }
            book = books.get(i);
        }
        book.lendCopy();
    }
    
    
    /**
     * Retorna el libro de la biblioteca con la mayor cantidad de palabras en el título.
     * Por ejemplo, "Rayuela" tiene una palabra, y "El Aleph" tiene dos palabras. Este método
     * retorna el libro con mayor cantidad de palabras, entre todos los pertenecientes a la 
     * biblioteca.
     * Precondición: debe haber al menos un libro almacenado en la biblioteca.
     * @return el libro (objeto) con mayor cantidad de palabras entre los de la biblioteca.
     * Si hay más de uno con la misma cantidad máxima de palabras, debe retornarse el primero de la 
     * lista entre ellos (es decir, el de id más chico).
     */
    public Book mostWordsInTitle()
    {
        // no calcula cantidad de palabras.
        if(books == null  || books.size() <= 0){
            throw new IllegalArgumentException("no hay libros en la biblioteca");
        }
        Book firstBook = null;
        for(int i = 1 ; i < books.size() ; i++){
        firstBook = books.get(0);
        String currBook = books.get(i).getTitle();
            if (currBook.length() > firstBook.getTitle().length()){
                firstBook = books.get(i);
            }
        }
        return firstBook;
    }
    
    /**
     * Elimina de la biblioteca todos los libros cuyo autor coincida con el autor pasado como parámetro.
     * @param author es el autor cuyos libros hay que eliminar.
     * Precondición: el parámetro author debe ser no nulo y no vacío.
     */
    public void deleteBooksWithAuthor(String author)
    {
        if(author == null || author.trim().isEmpty()){
            throw new IllegalArgumentException("no puede ingresar un autor nulo o vacio");
        }
        for(int i = 0 ; i < books.size() ; i++){
            if((books.get(i).getAuthor()).equals(author)){
                books.remove(i);
            }
        } // saltea posiblemente algunos libros.
    }
    
    /**
     * Invariante de clase de Library (biblioteca). Una biblioteca se considera válida, o
     * internamente consistente, si su nombre no es nulo ni vacío, la capacidad es mayor a cero,
     * la lista de libros no contiene null, los libros están ordenados crecientemente por id,
     * cada libro es internamente consistente (satisface su respectivo repOK()), y la suma de 
     * ejemplares de los libros no excede la capacidad de la biblioteca. 
     * @return true si y sólo si el objeto satisface el invariante de clase.
     */
    public boolean repOK()
    {
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        if(bookCapacity <= 0){
            return false;
        }
        if(books == null){
            return false;
        }
        for(Book b : books){
            if(b == null){
                return false;
            }
            if(!b.repOK()){
                return false;
            }
        }
        return true;
        
        // no chequea correctamente la capacidad.
        }
    }