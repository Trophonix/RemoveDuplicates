package com.trophonix.removeduplicates;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    RemoveDuplicates is a simple program which removes duplicate files in the working directory.
    Copyright (C) 2018 Trophonix

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

public class Main {

  public static void main(String... args) throws IOException {
    System.out.println(" * - * - * - * \n");
    System.out.println("    RemoveDuplicates  Copyright (C) 2018  Trophonix\n" +
            "    This program comes with ABSOLUTELY NO WARRANTY.\n" +
            "    This is free software, and you are welcome to redistribute it\n");
    System.out.println(" * - * - * - * \n");
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
