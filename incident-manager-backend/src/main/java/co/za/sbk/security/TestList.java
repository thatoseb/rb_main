package co.za.sbk.security;

import java.util.ArrayList;
import java.util.List;

public class TestList {

    public static void main(String[] args) {
        List<String> myList = new ArrayList<>();
        myList.add("11123");
        Boolean found = myList.contains("11123");
        System.out.println("FOUND: "+found);
        
        
    }
}
