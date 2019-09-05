package com.face.advice;

import java.util.Scanner;

public class AfterAdvice {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String line=scanner.nextLine();
        String[] arr=line.split("\\s+");
        int w[]=new int[26];
        String res="";
        for (int i = 0; i < arr.length; i++) {
            if(!res.contains(arr[i].charAt(i)+"")){
                res+=arr[i].charAt(i);
            }else {
                if(res.indexOf(arr[i-1].charAt(0))>res.indexOf(arr[i].charAt(0))){
                    System.out.println("invalid");
                    System.exit(0);
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length(); j++) {
                char ch=arr[i].charAt(j);
                char preCh=arr[i].charAt(j-1);
                if(!res.contains(ch+"")){

                }else {
                    if(res.indexOf(preCh)>res.indexOf(ch)){
                        System.out.println("invalid");
                        System.exit(0);
                    }
                }
            }
        }
    }
}
