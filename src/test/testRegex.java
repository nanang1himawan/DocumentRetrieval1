/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author nanang1himawan
 */
public class testRegex {
        //https://github.com/nanang1himawan/DocumentRetrieval1.git
    public static void main(String[] args) {
        String test = "ini sebuah, kalimat..?";
        test = test.replaceAll("[,?.]", "");
        System.out.println(test);
    }
}
