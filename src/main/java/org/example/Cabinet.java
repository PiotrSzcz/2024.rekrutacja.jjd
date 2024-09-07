package org.example;

import java.io.File;
import java.util.List;
import java.util.Optional;

interface Cabinet {
    Optional<File>
    findFolderByName(String name);
    List<File> findFoldersBySize(String size);
    int count();
}
interface Folder {
    String getName();
    String getSize();
}
interface MultiFolder extends Folder {
    List<Folder> getFolders();
}