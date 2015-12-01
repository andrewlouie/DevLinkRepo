package andrew.data;

import javax.persistence.EntityManagerFactory;
import andrew.tables.Category;
import andrew.tables.Type;

public class PopulateDatabase {
    private static EntityManagerFactory emf;
        
    public static void main(String[] args) {
       
        
        Category cat1 = new Category();
        Category cat2 = new Category();
        Category cat3 = new Category();
        Category cat4 = new Category();
        Category cat5 = new Category();
        Category cat6 = new Category();
        Category cat7 = new Category();
        Category cat8 = new Category();
        Category cat9 = new Category();
        Category cat10 = new Category();
        Category cat11 = new Category();
        Category cat12 = new Category();
        Category cat13 = new Category();
        Category cat14 = new Category();
        Category cat15 = new Category();
        Category cat16 = new Category();
        cat1.setCatName("JQuery/JavaScript");
        cat2.setCatName("PHP");
        cat3.setCatName("HTML");
        cat4.setCatName("Design");
        cat5.setCatName("Ruby");
        cat6.setCatName("CSS");
        cat7.setCatName("Android");
        cat8.setCatName("iOS");
        cat9.setCatName("Wordpress");
        cat10.setCatName("General");
        cat11.setCatName("SQL");
        cat12.setCatName("C#");
        cat13.setCatName("VB.NET");
        cat14.setCatName("Python");
        cat15.setCatName("Java");
        cat16.setCatName("ASP.NET");
        CategoryDB.insert(cat1);
        CategoryDB.insert(cat2);
        CategoryDB.insert(cat3);
        CategoryDB.insert(cat4);
        CategoryDB.insert(cat5);
        CategoryDB.insert(cat6);
        CategoryDB.insert(cat7);
        CategoryDB.insert(cat8);
        CategoryDB.insert(cat9);
        CategoryDB.insert(cat10);
        CategoryDB.insert(cat11);
        CategoryDB.insert(cat12);
        CategoryDB.insert(cat13);
        CategoryDB.insert(cat14);
        CategoryDB.insert(cat15);
        CategoryDB.insert(cat16);
        Type typ1 = new Type();
        Type typ2 = new Type();
        Type typ3 = new Type();
        Type typ4 = new Type();
        Type typ5 = new Type();
        Type typ6 = new Type();
        typ1.setTypeName("Other");
        typ2.setTypeName("Snippet");
        typ3.setTypeName("Tutorial");
        typ4.setTypeName("Freebie");
        typ5.setTypeName("Online Resource");
        typ6.setTypeName("Plugin");
        TypeDB.insert(typ1);
        TypeDB.insert(typ2);
        TypeDB.insert(typ3);
        TypeDB.insert(typ4);
        TypeDB.insert(typ5);
        TypeDB.insert(typ6);
        
    }
}