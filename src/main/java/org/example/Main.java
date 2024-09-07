package org.example;

public class Main {
    public static void main(String[] args) {



        String rootPath = ""; //ścieżka do przeszukania


        FolderCabinet cabinet = new FolderCabinet(rootPath);
        System.out.println("Znajdź folder o nazwie 'ExampleFolder': " +
                cabinet.findFolderByName("3d").orElse(null));

        System.out.println("Znajdź foldery o rozmiarze 'LARGE': " +
                cabinet.findFoldersBySize("LARGE"));

        System.out.println("Łączna liczba folderów: " + cabinet.count());
    }
}