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
        // Verificar la capacidad de la biblioteca
        int totalCopies = 0;
        for (Book b : books) {
            totalCopies += b.getCopies();
        }
        if (totalCopies + book.getCopies() > bookCapacity) {
            throw new IllegalArgumentException("La biblioteca está llena");
        }
    
        // Verificar si ya existe un libro con el mismo ID
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                throw new IllegalArgumentException("Ya existe un libro con esa ID");
            }
        }
    
        // Insertar el libro en la posición correcta para mantener la lista ordenada
        int i = 0;
        while (i < books.size() && books.get(i).getId() < book.getId()) {
            i++;
            /*
             * siempre que el id de los elementos que va recorriendo el indice sea menor que el id del libro
             * el indice sigue aumentando, cuando llega a un libro con una id que sea igual omayor entonces 
             * agrega el libro en esa posicion
             */
        }
        books.add(i, book);
    
        // Asegurarse de que la representación del objeto es correcta
        assert repOK() : "Error en el método addBook";
    }
    
    /**
     * Presta un ejemplar de un libro a la biblioteca
     * @param id es el id del libro a prestar.
     * Precondición: Existe un libro con el id indicado, y hay al menos una copia en existencia para prestar.
     * Postcondición: Se actualiza el número de copias en existencia del libro correspondiente, disminuyendo la
     * candidad de copias disponibles en uno.
     * Importante: Este algoritmo debe tener complejidad O(log n) en el peor caso.
     */
    public void lendBook(int id) {
        int low = 0;
        int high = books.size() - 1; // Inicializa las variables para la búsqueda binaria
        boolean lend = false;
        while (low <= high && !lend) {
            int mid = (high + low) / 2; // Calcula el índice medio
            Book curr = books.get(mid); // Obtiene el libro en el índice medio
            if (curr.getId() == id) { // Si el libro tiene el ID buscado
                if (curr.getCopiesAvailable() == 0) { // Verifica si hay copias disponibles
                    throw new IllegalArgumentException("no hay copias disponibles para prestar");
                }
                curr.lendCopy(); // Presta una copia del libro
                lend = true; // Marca que el préstamo se ha realizado
            } else if (curr.getId() < id) { // Si el ID del libro medio es menor que el ID buscado
                low = mid + 1; // Ajusta el límite inferior de la búsqueda
            } else { // Si el ID del libro medio es mayor que el ID buscado
                high = mid - 1; // Ajusta el límite superior de la búsqueda
            }
        }
    
        if (!lend) {
            throw new IllegalArgumentException("No se encontró un libro con el ID especificado"); // Lanza excepción si no se realizó el préstamo
        }
    }
    
    /**
     * Cuenta el número de palabras en un título.
     * @param title el título del libro
     * @return el número de palabras en el título
     */
    public int countWords(String title) {
        if (title == null || title.trim().isEmpty()) {
            return 0;
        }
        // Dividir el título en palabras y contar el número de palabras
        return title.trim().split("\\s+").length; //este separa las palabras por los espacios
        /*
         * split crea una matriz con los elementos que separa, ene ste caso toma como separacion los espacios
         * \\s+ manejas multiples espacios y tabulaciones
         */
    }
    
    /*
     * este otro separa en caracteres(incluyendo espacios) y los cuenta
     * String[] palabras = title.split("");
     * return palabras.length();
     */
   
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
        if (books.isEmpty()) {
            throw new IllegalStateException("La biblioteca no tiene libros almacenados");
        }
    
        Book mostWordsBook = books.get(0);
        int maxWords = countWords(mostWordsBook.getTitle());
    
        for (Book book : books) {
            int currentWords = countWords(book.getTitle()); //metodo countWords implementado mas arriba
            if (currentWords > maxWords) {
                mostWordsBook = book;
                maxWords = currentWords;
            }
        }
        return mostWordsBook;
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
        int i = 0;
        while(i < books.size()){
            if((books.get(i).getAuthor()).equals(author)){
                books.remove(i);
            }else{
        i++;
            }
        }
    }
    /*
     * otra forma es con el iterator
     * if(author == null || author.trim().isEmpty()){
     *   throw new IllegalArgumentException("no puede ingresar un autor nulo o vacio");
     *       }
     *       Iterator<Book> iterator = books.iterator();
     *       while(iterator.hasNext()){
     *           Book book = iterator.next();
     *           if(book.getAuthor().equals(author)){
     *               iterator.remove();
     *           }
     *       }
     *   }
     */
    
    /**
     * Invariante de clase de Library (biblioteca). Una biblioteca se considera válida, o
     * internamente consistente, si su nombre no es nulo ni vacío, la capacidad es mayor a cero,
     * la lista de libros no contiene null, los libros están ordenados crecientemente por id,
     * cada libro es internamente consistente (satisface su respectivo repOK()), y la suma de 
     * ejemplares de los libros no excede la capacidad de la biblioteca. 
     * @return true si y sólo si el objeto satisface el invariante de clase.
     */
    public boolean repOK() {
        // Verifica que el nombre no sea nulo ni vacío
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Verifica que la capacidad sea mayor a cero
        if (bookCapacity <= 0) {
            return false;
        }
        // Verifica que la lista de libros no sea nula
        if (books == null) {
            return false;
        }
        // Verifica que la lista de libros no contenga null y que cada libro sea internamente consistente
        int totalCopies = 0;
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b == null || !b.repOK()) {
                return false;
            }
            // Verifica que los libros estén ordenados crecientemente por id
            if (i > 0 && books.get(i - 1).getId() >= b.getId()) {
                return false;
            }
            // Suma la cantidad de copias de cada libro
            totalCopies += b.getCopies();
        }
        // Verifica que la suma de ejemplares de los libros no exceda la capacidad de la biblioteca
        if (totalCopies > bookCapacity) {
            return false;
        }
        return true;
    }
}