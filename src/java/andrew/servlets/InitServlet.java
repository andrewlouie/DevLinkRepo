package andrew.servlets;

import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.tables.Category;
import andrew.tables.Type;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {

    public static List<Category> categories;
    public static List<Type> types;
    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException{
            categories = CategoryDB.selectCategories();
            types = TypeDB.selectTypes();
    }
}
