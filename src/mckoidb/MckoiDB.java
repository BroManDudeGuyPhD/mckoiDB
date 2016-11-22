// Andrew Fossier
// aaf8553
// CMPS 360
// Programming Project : 5
// Due Date : 11/21/15
// Program Description: Models a DB

/* Certificate of Authenticity:   (Choose one of the two following forms:)
 I certify that the code in the method functions including method function main of this 
 project are entirely my own work. 
 */
package mckoidb;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MckoiDB {

    public static void main(String[] args) {

        try {

            try {
                Class.forName("com.mckoi.JDBCDriver").newInstance();
            } catch (Exception e) {
                System.out.println("Cannot register driver: " + e);
                return;
            }

            Connection connection;
            try {
                connection
                        = DriverManager.getConnection("url", "admin", "adminPw");
            } catch (SQLException e) {
                System.out.println("cannot connect to database: " + e);
                return;
            }

            Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(MckoiDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet result;

            // create a Student table
            statement.executeQuery(
                    "CREATE TABLE Student "
                    + "(id INTEGER, name VARCHAR(25), majorId INTEGER)"
            );

            // insert records into Student table
            statement.executeQuery(
                    "INSERT INTO Student(id, name, majorId) VALUES "
                    + "(9, 'Fred Flintstone', 1)"
            );

            Scanner in = new Scanner(System.in);

            int choice = 1;
            String studentID;
            while (choice != 0) {
                System.out.println("--------------------------------- MENU ---------------------------------");
                System.out.println("1. List All Courses, Majors, Students in Major, Or Students in Course\n"
                                 + "2. List Student schedule and info\n"
                                 + "3. Add Student, Course, or Major\n"
                                 + "4. Enroll Student in Course");
                System.out.print("Choice: ");
                choice = in.nextInt();

                
                if (choice == 1) {
                    //List all courses
                    System.out.println("============= LIST ALL Submenu ============= ");
                    System.out.println("1. Courses\n"
                                     + "2. Majors\n"
                                     + "3. Students in Major\n"
                                     + "4. Students in Course");
                    
                    System.out.print("Choice: ");
                    int subChoice = in.nextInt();

                    if (subChoice == 1) {
                        //List all courses in the database with course ids
                        result = statement.executeQuery(
                                "SELECT courseId, courseDesc FROM Course"
                        );

                        while (result.next()) {
                            System.out.println(
                                    result.getString(1) + " :: "
                                    + result.getString(2) + "\n");
                        }
                    }

                    if (subChoice == 2) {
                        //List all majors in the database with major ids
                        result = statement.executeQuery(
                                "SELECT majorId, majorDesc FROM Major"
                        );

                        System.out.println("MajorID :: Major Description");
                        while (result.next()) {
                            System.out.println(
                                    result.getString(1) + " :: "
                                    + result.getString(2) + "\n");
                        }
                    }

                    if (subChoice == 3) {
                        //List all students with a given major; the user is shown a list of majors with ids and then enters the major id for the students to be displayed

                        result = statement.executeQuery(
                                "SELECT majorId, majorDesc FROM Major"
                        );

                        System.out.println("MajorID :: Major Description");

                        while (result.next()) {
                            System.out.println(
                                    result.getString(1) + " :: "
                                    + result.getString(2) + "\n");
                        }

                        System.out.println("Selection: ");
                        String selection = in.nextLine();

                        result = statement.executeQuery(
                                "SELECT Student.name FROM Student, Major WHERE Student.majorID=" + selection
                        );
                    }

                    if (subChoice == 4) {
                        //List all students in a course  in the database; the user is shown a list of courses with ids and then enters the course id for the list of students to be displayed

                        result = statement.executeQuery(
                                "SELECT courseId, courseDesc FROM Course"
                        );
                        System.out.println("CourseID :: Course Description");

                        while (result.next()) {
                            System.out.println(
                                    result.getString(1) + " :: "
                                    + result.getString(2) + "\n");
                        }

                        System.out.println("Selection: ");
                        String selection = in.nextLine();
                        result = statement.executeQuery(
                                "SELECT Student.name FROM Student, Enrolled WHERE Student.id=Enrolled.id AND Enrolled.courseId=" + selection
                        );
                    }
                }

                
                if (choice == 2) {
                    //list schedule and student info
                    
                    System.out.print("Enter Student ID: ");
                    studentID = in.nextLine();
                    System.out.println("Schedule for Student " + studentID + ": ");
                    
                    result = statement.executeQuery(
                            "SELECT Course.courseDesc FROM Course, Enrolled WHERE " + studentID + "=Enrolled.id AND Enrolled.courseId=Course.courseId "
                    );

                    //SELECT Student.name, Major.majorDesc, Address.street, Address.city, Address.state, Address.zip 
                    //FROM Student, Major, Address WHERE Student.id= STUDENTIDVARIABLE AND Student.id=Address.id AND Student.majorId=Major.majorId
                }

                
                if (choice == 3) {
                    //add new student, add new course, add new major

                    System.out.println("============= Add Submenu ============= ");
                    System.out.println("1. Student\n"
                                     + "2. Course\n"
                                     + "3. Major");
                    
                    System.out.print("Choice: ");
                    int subChoice = in.nextInt();

                    if (subChoice == 1) {
                        //Add new Student
                    }

                    if (subChoice == 2) {
                        //Add new Course        
                    }

                    if (subChoice == 3) {
                        //Add new major
                    }
                }

                if (choice == 4) {
                    //enroll a student in a course in the database; the user is shown a list of course names with course ids, then can enroll a student by entering the course id and the student id
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(MckoiDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
