/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 *
 * @author nanang1himawan
 */
public class InvertedIndex {

    private ArrayList<Document> listOfDocument = new ArrayList<Document>();
    private ArrayList<Term> dictionary = new ArrayList<Term>();

    public InvertedIndex() {
    }

    public void addNewDocument(Document document) {
        getListOfDocument().add(document);
    }

    public ArrayList<Posting> getUnsortedPostingList() {
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // buat node Posting utk listofdocument
        for (int i = 0; i < getListOfDocument().size(); i++) {
            // buat listOfTerm dari document ke -i
            String[] termResult = getListOfDocument().get(i).getListofTerm();
            // loop sebanyak term dari document ke i
            for (int j = 0; j < termResult.length; j++) {
                // buat object tempPosting
                Posting tempPosting = new Posting(termResult[j],
                        getListOfDocument().get(i));
                list.add(tempPosting);
            }
        }
        return list;
    }

    public ArrayList<Posting> getSortedPostingList() {
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // panggil list yang belum terurut
        list = this.getUnsortedPostingList();
        // urutkan
        Collections.sort(list);
        return list;
    }

    public ArrayList<Posting> search(String query) {
        //buat index/dictionary
        makeDictionary();
        String tempQuery[]=query.split(" ");
        ArrayList<ArrayList<Posting>> tempPosting = new ArrayList<>();
        for (int i = 0; i < tempQuery.length; i++) {
            tempPosting.add(search(tempQuery[i]));
        }
        return intersection(tempPosting.get(0), tempPosting.get(1));
    }

    public ArrayList<Posting> searchOneWord(String word) {
        Term tempTerm = new Term(word);
        if (getDictionary().isEmpty()) {
            return null;
        } else {
            int positionTerm = Collections.binarySearch(dictionary, tempTerm);
            if (positionTerm < 0) {
                return null;
            } else {
                return dictionary.get(positionTerm).getPostingList();
            }
        }
    }

    public void makeDictionary() {
        // buat posting list term terurut
        ArrayList<Posting> list = getSortedPostingList();
        // looping buat list of term (dictionary)
        for (int i = 0; i < list.size(); i++) {
            // cek dictionary kosong?
            if (getDictionary().isEmpty()) {
                // buat term
                Term term = new Term(list.get(i).getTerm());
                // tambah posting ke posting list utk term ini
                term.getPostingList().add(list.get(i));
                // tambah ke dictionary
                getDictionary().add(term);
            } else {
                // dictionary sudah ada isinya
                Term tempTerm = new Term(list.get(i).getTerm());
                // pembandingan apakah term sudah ada atau belum
                // luaran dari binarysearch adalah posisi
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    // term baru
                    // tambah postinglist ke term
                    tempTerm.getPostingList().add(list.get(i));
                    // tambahkan term ke dictionary
                    getDictionary().add(tempTerm);
                } else {
                    // term ada
                    // tambahkan postinglist saja dari existing term
                    getDictionary().get(position).
                            getPostingList().add(list.get(i));
                    // urutkan posting list
                    Collections.sort(getDictionary().get(position)
                            .getPostingList());
                }
                // urutkan term dictionary
                Collections.sort(getDictionary());
            }

        }

    }

    /**
     * @return the listOfDocument
     */
    public ArrayList<Document> getListOfDocument() {
        return listOfDocument;
    }

    /**
     * @param listOfDocument the listOfDocument to set
     */
    public void setListOfDocument(ArrayList<Document> listOfDocument) {
        this.listOfDocument = listOfDocument;
    }

    /**
     * @return the dictionary
     */
    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }
    public ArrayList<Posting> intersection(ArrayList<Posting> p1,
            ArrayList<Posting> p2){
        // mengecek p1 atau p2 sama dengan null?
        if (p1 == null || p2 == null) {
            // mengembalikan posting p1 atau p2
            return new ArrayList<>();
        }
        // menyiapkan posting tempPosting
        ArrayList<Posting> tempPostings = new ArrayList<>();
        // menyiapkan variable p1Index dan p2Index
        int p1Index = 0;
        int p2Index = 0;
        
        // menyiapkan variable post1 dan post2 bertipe Posting 
        Posting post1 = p1.get(p1Index);
        Posting post2 = p2.get(p2Index);

        while (true) {
            // mengecek id document post1 = id document post2?
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    // menambahkan post1 ke tempPosting
                    tempPostings.add(post1);
                    // p1Index dan p2Index bertambah 1
                    p1Index++;
                    p2Index++;
                    
                    post1 = p1.get(p1Index);
                    post2 = p2.get(p2Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }

            } // mengecek id document post1 < id document post2?
            else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    // p1Index bertambah 1
                    p1Index++;
                    post1 = p1.get(p1Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }

            } 
            else {
                try {
                    // p2Index bertambah 1
                    p2Index++;
                    post2 = p2.get(p2Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }
            }
        }
        // mengembalikan tempPosting
        return tempPostings;
    }
}
