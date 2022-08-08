package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import Walmart_Project1.Main.{Admin, DeleteAccount, DeleteReview, ForgotPassword, LoginPostgress, ReadReviewInDatabase, SignupPostgress, WriteReviewInDatabase}
import Walmart_Project1.Run_Program.queryApp

import scala.Console.println
import scala.annotation.tailrec

/*Created An Object To Instantiate Menu Features*/
object Menu_Options {
  val dataManager = new DataManager()
//  val login = new Login()

  /*Defined A Function For Login Features*/
  @tailrec
  def login_menu(): Unit = {
    println("===" *18 + "WELCOME TO WAL-MART!! CONSUMERS BEST CHOICE" + "===" *18)
    println(
      """
        |   MAIN PAGE 📌
        |[1] Admin
        |[2] Login
        |[3] Signup
        |[4] Forgot Password
        |[5] Reviews
        |[6] Delete Account
        |[0] Logout
        |
        |Please Select An Option: """.stripMargin)
    val input = scala.io.StdIn.readLine()

    /*Case Matching Login Features To Option Chosen*/
    input match {
      case "1" =>
        Admin()
      case "2" =>
        LoginPostgress()
      case "3" =>
        SignupPostgress()
      case "4" =>
        ForgotPassword()
      case "5" =>
        Walmart_Review()
      case "6" =>
        DeleteAccount()
      case "0" =>
        println("Logout Successful 📴\nGoodbye!!")
        sys.exit()
      case _ =>
        println("Wrong Entry, Choose Another Option [1][2][3][4][5][6][0]:")
        login_menu()
    }
  }

  /*Defined A Function For Menu Features*/
  @tailrec
  def detail_menu(): Unit = {
    println("===" *20 + "SAVE PEOPLE MONEY APPLICATION" + "===" *20)
    println(
      """
        |        MENU OPTIONS 📌
        |[1] Groceries Budget Analysis
        |[2] Prescription Drug Budget Analysis
        |[3] Return To Main Page
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val input = scala.io.StdIn.readLine()

    /*Case Matching Menu Features To Option Chosen*/
    input match {
      case "1" =>
        println("---" * 30)
        println("\nDataFrame Overview Limited To 10 Rows!!!")
        queryApp()
        println("---" * 30)
        Run_Program.delay(1000)
        Q1()
      case "2" =>
        println("---" * 30)
        println("\nDataFrame Overview Limited To 10 Rows!!!")
        queryApp()
        println("---" * 30)
        Q2()
      case "3" =>
        println("Returning To Main Page\n" +
          "Goodbye!!")

        Run_Program.delay(1000)
        login_menu()

      case _ =>
        println("Wrong Entry, Choose Another Option [1][2][0]:")
        detail_menu()
    }
  }

  /*Defined A Function For Sub-Menu Query Features*/
  @tailrec
  def Q1(): Unit ={

    println(
      """
        |             GROCERIES ANALYSIS 📌
        |[a] Store Brand Groceries Price Comparison With Total
        |[b] Name Brand Groceries Price Comparison With Total
        |[c] Return To Menu Options
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val input = scala.io.StdIn.readLine()

    /*Case Matching Sub-Menu Query Features To Option Chosen*/
    input match {
      case "a" =>
        println("===" *30)
        dataManager.query(queryNumber = "Q1_a")
        Q1()
      case "b" =>
        println("===" *30)
        dataManager.query(queryNumber = "Q1_b")
        Q1()
      case "c" =>
        Run_Program.delay(1000)
        detail_menu()
      case _ =>
        println("Wrong Entry, Choose Another Option [a][b][c]: ")
        Q1()
    }
  }

  /*Defined A Function For Sub-Menu Query Features*/
  @tailrec
  def Q2(): Unit ={
    println(
      """
        |        PRESCRIPTION ANALYSIS 📌
        |[a] Pharmacy Medication Overview
        |[b] Medication Total Prices Comparison
        |[c] Return To Menu Options
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val input = scala.io.StdIn.readLine()

    /*Case Matching Sub-Menu Query Features To Option Chosen*/
    input match {
      case "a" =>
        println("===" *30)
        dataManager.query(queryNumber = "Q2_a")
        Q2()
      case "b" =>
        println("===" *30)
        dataManager.query(queryNumber = "Q2_b")
        Q2()
      case "c" =>
        Run_Program.delay(1000)
        detail_menu()
      case _ =>
        println("Wrong Entry, Choose Another Option [a][b][c][d]: ")
        Q2()
    }
  }

  /*Defined A Function For Review Features*/
  def Walmart_Review(): Unit = {
    println("---" * 30)
    println(
      """
        |   REVIEW OPTIONS 📌
        |[1] Write Review
        |[2] Read All Review
        |[3] Delete Your Review
        |[4] Return To Main Page
        |
        |Choose An Option: """.stripMargin)
    val input: String = scala.io.StdIn.readLine()

    /*Case Matching Sub-Review Features To Option Chosen*/
    input match {
      case "1" =>
        WriteReviewInDatabase()

      case "2" =>
        ReadReviewInDatabase()

      case "3" =>
        Run_Program.delay(1000)
        DeleteReview()

      case "4" =>
        Run_Program.delay(1000)
        login_menu()

      case _ =>
        println("Wrong Entry, Choose Another Option [1][2][0]:")
        Walmart_Review()
    }
    Thread.sleep(5000)
  }
}
