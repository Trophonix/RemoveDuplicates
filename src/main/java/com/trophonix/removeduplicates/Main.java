package com.trophonix.removeduplicates;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

  public static void main(String... args) throws IOException {
    File root = new File(System.getProperty("user.dir"));
    System.out.println("Scanning [" + root.getName() + "] for duplicates.");
    File[] children = root.listFiles();
    if (children != null) {
      boolean prefix = true;
      List<String> deleted = new ArrayList<>();
      outer:
      for (File child : children) {
        if (child.isDirectory() || deleted.contains(child.getAbsolutePath())) continue;
        for (File innerChild : children) {
          if (innerChild.isDirectory() || child.getAbsolutePath().equals(innerChild.getAbsolutePath()) || deleted.contains(innerChild.getAbsolutePath())) continue;
          if (FileUtils.contentEquals(child, innerChild)) {
            FileUtils.forceDeleteOnExit(child);
            System.out.println("\n\n - Found duplicate [" + child.getName() + "]. It will be deleted on exit.");
            deleted.add(child.getAbsolutePath());
            prefix = true;
            continue outer;
          }
        }
        if (prefix) {
          System.out.print("\nFound valid file: ");
          prefix = false;
        } else {
          System.out.print(", ");
        }
        System.out.print(child.getName());
      }
    }
  }

}
