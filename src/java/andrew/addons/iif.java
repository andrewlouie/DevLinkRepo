/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.addons;

/**
 *
 * @author Andrew
 */
public class iif {
    public static String iif(boolean test,String a,String b) {
        if (test) return a;
        return b;
    }
}
