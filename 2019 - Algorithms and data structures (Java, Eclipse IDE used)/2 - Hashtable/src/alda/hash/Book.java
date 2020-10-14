package alda.hash;


public class Book {
	private MyString title;
	private MyString author;
	private ISBN10 isbn;
	private MyString content;
	private int price;

	public Book(String title, String author, String isbn, String content, int price) {
		this.title = new MyString(title);
		this.author = new MyString(author);
		this.isbn = new ISBN10(isbn);
		this.content = new MyString(content);
		this.price = price;
	}

	public MyString getTitle() {
		return title;
	}

	public MyString getAuthor() {
		return author;
	}

	public ISBN10 getIsbn() {
		return isbn;
	}

	public MyString getContent() {
		return content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	// Använde ISBNet som hashcode eftersom den är (iallafall vad jag vet) unik. 
	// Jag multiplicerar den med ett primtal för att få den så unik som möjligt
    public int hashCode() {

        final int prime = 31;
		String charie = isbn.toString();
        int sum = 0;
		for (int i = 0; i < isbn.getChar().length; i++) {
			sum += Character.getNumericValue( charie.charAt(i));
		}
	
		
		int result = 1;
		result += prime * result + sum;
		System.out.println("this is a resultie:");

		System.out.println(result);
		
        return result;
    }
    // Jag checkar här ifall det är samma object. Jag checkar också om isbnet är samma eftersom jag använder den som hashcode.
    @Override
    public boolean equals(Object obj) {
        if (this == obj){

        	System.out.print("SAME OBJ TRUE");
        	return true;
        }
        if (obj == null){

        	System.out.print("OBJ NULL");
        	return false;
        }
        if (getClass() != obj.getClass()){

        	System.out.print("CLASS FALSE");
        	return false;
        }
        if (getIsbn().checkDigit(((Book) obj).getIsbn().toString()) == false){

        	System.out.print("ISBN FALSE");
        	return false;
        }
    	System.out.print("SAME OBJ TRUE2");
        return true;
    }
 


	@Override
	public String toString() {
		return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn,
				content.length());
	}

}