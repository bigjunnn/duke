package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import tasks.Task;
import tasks.ToDo;
import tasks.DeadLine;
import tasks.Event;

/**
 * Storage object holds the file, in which writing and reading will happen.
 */
public class Storage {

  private File f;

  // Helper method to add text to end of file
  private static void appendToFile(String filePath, String textToAppend) throws IOException {
    FileWriter fw = new FileWriter(filePath, true);
    fw.write(textToAppend);
    fw.close();
  }

  // Helper method to overwrite the entire file
  private static void writeToFile(String filePath, String textToWrite) throws IOException {
    FileWriter fw = new FileWriter(filePath);
    fw.write(textToWrite);
    fw.close();
  }

  public Storage(String filePath) {
    File f = new File(filePath);
    this.f = f;
  }

  /**
   * A method to create a list of tasks, from an input file.
   * 
   * @return List of tasks
   * @throws FileNotFoundException
   * @throws IOException
   */
  public ArrayList<Task> load() throws FileNotFoundException, IOException {

    ArrayList<Task> tasks = new ArrayList<>();

    // Read from the file location
    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] wordArr = line.split("\\|");
      String taskType = wordArr[0].trim();
      int done = Integer.parseInt(wordArr[1].trim());
      String description = wordArr[2].trim();

      if (taskType.equals("T")) {
        tasks.add(new ToDo(description));

        if (done == 1) {
          tasks.get(tasks.size() - 1).setAsDone();
        }
      } else if (taskType.equals("D")) {
        String time = wordArr[3].trim();
        tasks.add(new DeadLine(description, time));

        if (done == 1) {
          tasks.get(tasks.size() - 1).setAsDone();
        }
      } else {
        String time = wordArr[3].trim();
        tasks.add(new Event(description, time));

        if (done == 1) {
          tasks.get(tasks.size() - 1).setAsDone();
        }
      }
    }

    sc.close();
    return tasks;

  }

  /**
   * Adds the task specified into the text file.
   * 
   * @param textToAdd
   * @throws IOException
   */
  public void saveTask(String textToAdd) throws IOException {
    appendToFile(f.getAbsolutePath(), textToAdd);
  }

  /**
   * Deletes the task specified at index in the text file.
   * 
   * @param index
   * @throws FileNotFoundException
   * @throws IOException
   */
  public void deleteTask(int index) throws FileNotFoundException, IOException {
    int count = 0;
    String finalText = "";

    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      if (count == index) {
        count++;
        continue;
      } else {
        finalText += line + "\n";
        count++;
      }
    }

    sc.close();
    writeToFile(f.getAbsolutePath(), finalText);
  }

  /**
   * Updates the task specified at index as done in the text file.
   * 
   * @param index
   * @throws FileNotFoundException
   * @throws IOException
   */
  public void updateAsDone(int index) throws FileNotFoundException, IOException {
    int count = 0;
    String finalText = "";

    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      if (count == index) {
        String newLine = line.replace("| 0 |", "| 1 |");
        finalText += newLine + "\n";
        count++;
      } else {
        finalText += line + "\n";
        count++;
      }
    }

    sc.close();
    writeToFile(f.getAbsolutePath(), finalText);
  }
}