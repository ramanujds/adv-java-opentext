package com.cwr.streamapi;

import java.util.ArrayList;
import java.util.List;

public class StreamApiDemo {
    static void main() {
        List<String> list = List.of("Gaurav", "Raj", "Ravi");
        // Find all the names starts with 'R'

//        var output = new ArrayList<String>();
//
//        for (var name:list){
//            if(name.startsWith("R")){
//                output.add(name);
//            }
//        }

        var output = list.stream()
                .filter(name -> name.startsWith("R"))
                .filter(name -> name.length()>=4)
                .map(String::toUpperCase)
                .toList();

        System.out.println("List : " + list);
        System.out.println("Filtered List : " + output);

    }
}
