package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import models.*;

public class Application extends Controller {

	static String message = "Welcome to Bookmark";
	

	
	static Form<Name> input =  new Form<Name>(Name.class);
	// ne koristimo staticku varijablu -->  public static String name=" no name";
	//zato sto svako ko bi otvorio stanicu bi vido name
	
    public static Result index() {
    	// u indexu cemo porvjeriti da li nesto ima u varijabi name
    	
    	String name = session("name");
    	if ( name == null){
    		name="no name";
    		return ok(index.render(message, name , input));
    	} else {
    		// ako vec psotoji korisnikovo ime prebacuje ga u bookmarks
    		return redirect("/bookmarks");
    	}
       
    }
    
    public static Result recognize(){
    	// eko zelimo da dobijemo nesto iz nekog formulara
    	// bindFromRequest() pokupi iz post requesta i poveze ih sa Form-om
    	// get() vraca objegat tipa klase Name
    	
    	String name =  input.bindFromRequest().get().name;
    	session("name", name);
//    	return ok(index.render(message,  name, input));
    	
    	/*
    	 * redirect(); metoda koja nas salje na drugi URL
    	 */
    	return redirect("/bookmarks");
    }
    
    static Form<Bookmark> newBookmark =  new Form<Bookmark>(Bookmark.class);
    public static Result bookmarks(){
    	String name=  session("name");
    	if (name == null)
    		return redirect("/");
    	return ok(bookmark.render(newBookmark, name, Bookmark.all(name)));
    }
    
    public static Result addbookmark(){
    	String url = newBookmark.bindFromRequest().get().url;
    	String username = session("name");
    	Bookmark.create(username, url);
    	return redirect("/bookmarks");
    }
}
