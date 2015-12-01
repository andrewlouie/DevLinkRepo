package andrew.addons;

import andrew.tables.Link;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class SortList {
    
    public static List<Link> SortByTopUser(List<Link> links) {
        Collections.sort(links,new Comparator<Link>() {
        @Override

            public int compare(Link o1, Link o2) {
                Integer a = o2.getUser().getRating();
                return a.compareTo(o1.getUser().getRating());
            }
        
    });
        return links;
    }
    public static List<Link> SortByTop(List<Link> links) {
        Collections.sort(links,new Comparator<Link>() {
        @Override

            public int compare(Link o1, Link o2) {
                Integer a = o2.getUpVotes() - o2.getDownVotes();
                return a.compareTo(o1.getUpVotes() - o1.getDownVotes());
            }
        
    });
        return links;
    }
    public static List<Link> SortByHot(List<Link> links) {
        SortByTop(links);
        Collections.sort(links,new Comparator<Link>() {
        @Override

            public int compare(Link o1, Link o2) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
        
    });
        return links;
    }
    
}