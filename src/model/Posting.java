/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nanang1himawan
 */
public class Posting implements Comparable<Posting>{
    private String term;
    private Document document;
    private int numberOfTerm =1;
    
    
    public Posting(Document document) {
        this.document = document;
    }

    public Posting(String term, Document document) {
        this.term = term;
        this.document = document;
    }
    

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public int compareTo(Posting posting) {
 return term.compareToIgnoreCase(posting.getTerm());
    }

    public int getNumberOfTerm() {
        return numberOfTerm;
    }

    public void setNumberOfTerm(int tempNumber) {
        this.numberOfTerm = numberOfTerm;
    }
    
}
