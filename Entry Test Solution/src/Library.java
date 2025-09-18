import java.io.*;
import java.util.*;

public class Library {
    static ArrayList<LibraryItem> catalog = new ArrayList<LibraryItem>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("books.txt"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[“”„\"]", "\"");
                String[] parts = line.split("\"");
                if (parts.length >= 8) {
                    String title = parts[1];
                    String author = parts[3];
                    String isbn = parts[5];
                    String publisher = parts[7];
                    Book book = new Book(title, author, isbn, publisher);
                    catalog.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books.txt: " + e.getMessage());
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter book title to search: ");
        String searchTitle = scan.nextLine().trim().toLowerCase();

        LibraryItem found = null;
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getTitle().toLowerCase().equals(searchTitle)) {
                found = catalog.get(i);
                break;
            }
        }

        if (found != null) {
            found.displayDetails();
            if (found.isBorrowed) {
                System.out.println("This book is currently borrowed.");
            } else {
                System.out.println("This book is available.");
            }
        } else {
            System.out.println("Book not found in catalog.");
        }
    }

    public static void addLibraryItem(LibraryItem item) {
        catalog.add(item);
        System.out.println("Added: " + item.getTitle());
    }

    public static LibraryItem findItem(String itemId) {
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getItemId().equals(itemId)) {
                return catalog.get(i);
            }
        }
        return null;
    }
}

abstract class LibraryItem {
    String title;
    String author;
    String itemId;
    boolean isBorrowed;

    public LibraryItem(String title, String author, String itemId) {
        this.title = title;
        this.author = author;
        this.itemId = itemId;
        this.isBorrowed = false;
    }

    public void borrowItem() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("Item '" + title + "' has been borrowed.");
        } else {
            System.out.println("Item '" + title + "' is already borrowed.");
        }
    }

    public void returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println("Item '" + title + "' has been returned.");
        } else {
            System.out.println("Item '" + title + "' was not borrowed.");
        }
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public abstract void displayDetails();
}

class Book extends LibraryItem {
    private String isbn;
    private String publisher;

    public Book(String title, String author, String isbn, String publisher) {
        super(title, author, isbn);
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public void displayDetails() {
        System.out.println("----- Book Details -----");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Publisher: " + publisher);
        System.out.println("Borrowed: " + (isBorrowed ? "Yes" : "No"));
        System.out.println("------------------------");
    }
}
