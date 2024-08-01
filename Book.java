
/**
 * Representa un libro de una biblioteca. 
 * Los detalles del libro son su id, título, autor, cantidad de páginas, cantidad de ejemplares,
 * y cantidad de ejemplares en existencia (no prestados). 
 * 
 * @author N. Aguirre
 * @version 0.1
 */
public class Book
{
    // id del libro
    private int id;
    // título del libro
    private String title;
    // autor del libro
    private String author;
    // cantidad de páginas
    private int pages;
    // cantidad de ejemplares
    private int copies;
    // cantidad de ejemplares en existencia (no prestados)
    private int copiesAvailable;

    /**
     * Crea un nuevo libro con id, título, autor, cantidad de páginas
     * y ejemplares.
     * El título no puede ser null ni vacío. 
     * El autor no puede ser null ni vacío. 
     * El id debe ser positivo.
     * La cantidad de páginas debe ser un número positivo.
     * La cantidad de copias debe ser un número positivo.
     * La cantidad de copias disponibles (en existencia) se setea con
     * el mismo valor que la cantidad de copias.
     */
    public Book(int id, String title, String author, int pages, int copies)
    {
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("el titulo no puede ser nulo o vacio");   
        }
        if(author == null || author.trim().isEmpty()){
            throw new IllegalArgumentException("el author no puede ser nulo o vacio");   
        }
        if(id <= 0){
            throw new IllegalArgumentException("el id debe ser positivo");
        }
        if(pages <= 0){
            throw new IllegalArgumentException("la cantidad de paginas debe ser positivo");
        }
        if(copies <= 0){
            throw new IllegalArgumentException("la cantidad de copias debe ser positivo");
        }
        
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.copies = copies;
        this.copiesAvailable = copies;
        assert repOK() : "error en el constructor Book";
    }

    /**
     * Retorna los detalles del libro, como
     * cadena de caracteres.
     * @return detalles del libro en una cadena de caracteres,
     * usando el formato "<id>: <title> (by <author>). <pages> pages. Copies: <copies> (<copiesAvailable> available)".
     */
    public String toString()
    {
        return id + ": " + title + " (by " + author +"). " + pages + " pages. Copies: " + copies + " (" + copiesAvailable + " available)";
    }
    
    /**
     * Invariante de la clase Book.
     * Un libro es internamente consistente si su id es positivo, 
     * su título y autor no son nulos ni vacíos, la cantidad de páginas es positiva,
     * la cantidad de copias es positiva, y la cantidad de copias disponibles está entre cero
     * y la cantidad de copias.
     * @return true ssi el objeto es internamente consistente (satisface el invariante de clase).
     */
    public boolean repOK() 
    {
        if(title == null || title.trim().isEmpty()){
            return false;   
        }
        if(author == null || author.trim().isEmpty()){
            return false;   
        }
        if(id <= 0){
            return false;   
        }
        if(pages <= 0){
            return false;   
        }
        if(copies <= 0){
            return false;   
        }
        if(copiesAvailable < 0 || copiesAvailable > copies){ 
            return false;
        }
        return true;    
    }

    /**
     * Presta una copia del libro. La cantidad de copias disponible tiene que ser mayor a cero
     * para poder prestar una copia. El método debe disminuir en uno la cantidad de copias disponibles.
     */
    public void lendCopy() 
    {
        if (copiesAvailable > 0){
            copiesAvailable--;
        }
        else {
            System.out.println("no hay copias disponibles para prestar");
        }
    }
    
    /**
     * Devuelve una copia del libro. Tiene que haber libros prestados para poder devolver un ejemplar. 
     * El método debe incrementar en uno la cantidad de copias disponibles.
     */
    public void returnCopy() // no chequea precondicion
    {
        if(copiesAvailable < copies){
        copiesAvailable++;
        }
        else {
            System.out.println("no hay libros prestados");
        }
    }
    
    
    /**
     * este devuelve el titulo del libro
     * 
     * @return title.
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * este devuelve el autor del libro
     * 
     * @return author.
     */
    public String getAuthor(){
        return author;
    }
    
    /**
     * este devuelve el ID del libro
     * 
     * @return id.
     */
    public int getId(){
        return id;
    }
    
    /**
     * este devuelve las paginas del libro
     * 
     * @return pages.
     */
    public int getPages(){
        return pages;
    }
    
    /**
     * este devuelve la cantidad de copias del libro
     * 
     * @return copies.
     */
    public int getCopies(){
        return copies;
    }
    
    /**
     * este devuelve la cantidad de copias disponibles del libro
     * 
     * @return copiesAvailable.
     */
    public int getCopiesAvailable(){
        return copiesAvailable;
    }
}