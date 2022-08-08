package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import java.util.concurrent.{ExecutorService, Executors}

import Walmart_Project1.Menu_Options.{detail_menu, login_menu}

import scala.Console.println
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/*Created An Executor Object to Handle Success and Failure Exception*/
object MyExecutionContext {
  val executor: ExecutorService = Executors.newFixedThreadPool(4)
  implicit val ex: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

/*Created An Object To Instantiate Login And Review Features*/
object Main {
  import MyExecutionContext._
  import slick.jdbc.PostgresProfile.api._

  /*Defined A Function To Give Amin Authority to Delete All Information In Postgres Database*/
  def Admin(): Unit = {
    println("---" * 30)
    println(
      """
        |   Administrative Privilege
        |[1] Delete All User Login Information
        |[2] Delete All Review Information
        |[3] Return To Main Page
        |
        |To Delete Choose An Option:""".stripMargin)
    val delete_all_account: String = scala.io.StdIn.readLine()

    /*Case Matching Delete Features To Option Chosen*/
    delete_all_account match {
      case "1" =>
        PostgresConnection.db.run(SlickTables.loginTable.delete)
        println("All User Login Information Are Deleted Successfully From Database!!")
        Run_Program.pause(1000)
        login_menu()

      case "2" =>
        PostgresConnection.db.run(SlickTables.reviewTable.delete)
        println("All Review Information Are Deleted Successfully From Database!!")
        Run_Program.pause(1000)
        login_menu()

      case "3" =>
        Run_Program.delay(1000)
        login_menu()

      case _ =>
        println("Wrong Entry, Please Choose Another Option [1][2][3]:")
        Run_Program.delay(1000)
        Admin()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Login and Confirm Your Information In Postgres Database*/
  def LoginPostgress(): Unit = {
    println("---" * 30)
    println("To Login\nPlease Enter Your Username: ")
    val username_in: String = scala.io.StdIn.readLine()

    println("Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection and Filtering your information In Postgres Database*/
    val Login_PostgresFuture: Future[Seq[LoginDatabase]] = PostgresConnection.db.run(SlickTables.loginTable
      .filter(_.password === password_in).filter(_.username === username_in).result)

    /*Future Concurrent Success and Failure Block*/
    Login_PostgresFuture.onComplete {
      case Success(password) =>
        val username = username_in
        if (password.nonEmpty && username.nonEmpty) {
          println(s"Successful Login $username_in!")

          Run_Program.delay(1000)
          detail_menu()
        }
        else println("---" * 30)
        println(
          s"""
             |Username Or Password Incorrect!!
             |
             |[1] Try Again
             |[2] Signup
             |[3] Forgot Password
             |
             |Choose An Option: """.stripMargin)
        val input = scala.io.StdIn.readLine()

        input match {

          case "1" =>
            Run_Program.delay(1000)
            LoginPostgress()

          case "2" =>
            Run_Program.delay(1000)
            SignupPostgress()

          case "3" =>
            Run_Program.delay(1000)
            ForgotPassword()

          case _ =>
            println("Wrong Entry, Please Choose Another Option [1][2][3]:")
            Run_Program.delay(1000)
            LoginPostgress()
        }

      case Failure(ex) => println(s"Diagnosis: $ex")
        Run_Program.delay(1000)
        LoginPostgress()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Signup and Store Your Information In Postgres Database*/
  def SignupPostgress(): Unit = {
    println("---" * 30)
    println("To Signup\nPlease Enter Your Username: ")
    val username_new: String = scala.io.StdIn.readLine()

    println("Password: ")
    val password_new: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection and Query description to Grab your information*/
    val loginDatabaseOption: LoginDatabase = LoginDatabase(0, s"$username_new", s"$password_new")
    val queryDescription = SlickTables.loginTable += loginDatabaseOption
    val insert_futureId: Future[Int] = PostgresConnection.db.run(queryDescription)

    /*Future Concurrent Success and Failure Block*/
    insert_futureId.onComplete {
      case Success(newUsername) => println(s"$newUsername Signup Completed! ")
        Run_Program.delay(1000)
        detail_menu()

      case Failure(_) => println(s"Sorry Username=($username_new) Already Exists\nTry Again!")
        Run_Program.delay(1000)
        SignupPostgress()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Update Password Information In Postgres Database*/
  def ForgotPassword(): Unit = {
    println("---" * 30)

    println("Forgot Password Please Enter Your\nUsername: ")
    val username_in: String = scala.io.StdIn.readLine()

    println("Enter Your New Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    val id_in: Long = scala.io.StdIn.hashCode()

    /*Established Postgres Connection To Update your Password In Postgres Database*/
    val loginDatabaseOption: LoginDatabase = LoginDatabase(id_in, username_in, password_in)

    val queryDescriptor = SlickTables.loginTable.filter(_.username === s"$username_in")
      .update(loginDatabaseOption.copy(password = s"$password_in"))
    val update_futureId: Future[Int] = PostgresConnection.db.run(queryDescriptor)

    /*Future Concurrent Success and Failure Block*/
    update_futureId.onComplete {
      case Success(username) =>
        println("---" * 30)
        if (username == 0) {
          println(
            """
              |Failed To Update Password!! Username Not Found!!
              |[1] Try Again
              |[2] Return To Main Page
              |
              |Choose An Option: """.stripMargin)
          val input = scala.io.StdIn.readLine()

          input match {

            case "1" =>
              Run_Program.delay(1000)
              ForgotPassword()

            case "2" =>
              Run_Program.delay(1000)
              login_menu()

            case _ =>
              println("Wrong Entry, Please Choose Another Option [1][2]:")
              Run_Program.delay(1000)
              ForgotPassword()
          }
        }
        else
          println(s"$username New Password Updated Successful!")
        Run_Program.delay(1000)
        login_menu()

      case Failure(_) => println("Please Try A More Secured Password!")
        Run_Program.delay(1000)
        ForgotPassword()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Delete Filtered Information In Postgres Database*/
  def DeleteAccount(): Unit = {
    println("---" * 30)
    println("To Permanently Delete Your Account\nEnter Your Username: ")
    val username_in: String = scala.io.StdIn.readLine()

    println("Enter Your Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection To Delete Filtered Information In Postgres Database*/
    val delete_account: Future[Int] = PostgresConnection.db.run(SlickTables.loginTable.
      filter(_.username === username_in).filter(_.password === password_in).delete)

    /*Case Matching Delete Features To Option Chosen*/
    delete_account.onComplete{
      case Success(username) =>
        if (username == 0) {
          println(
            """
              |Failed To Delete!! Username Or Password Incorrect!!
              |[1] Try Again
              |[2] Return To Main Page
              |
              |Choose An Option: """.stripMargin)
          val input = scala.io.StdIn.readLine()

          input match {

            case "1" =>
              Run_Program.delay(1000)
              DeleteAccount()

            case "2" =>
              Run_Program.delay(1000)
              login_menu()

            case _ =>
              println("Wrong Entry, Please Choose Another Option [1][2]:")
              Run_Program.delay(1000)
              DeleteAccount()
          }
        }
        else println(s"$username User Deleted From Database!!")
        Run_Program.delay(1000)
        login_menu()

      case Failure(ex) =>
        println(s"$ex Failed To Delete")
        Run_Program.delay(1000)
        DeleteAccount()
    }
    Thread.sleep(50000)
  }

  /*Review Block*/
  /*Defined A Function To Store Review Information In Postgres Database*/
  def WriteReviewInDatabase(): Unit = {
    println("===" *23 + "WAL-MART REVIEWS PAGE" + "===" *23)

    println("To Write Review Please Enter Your\nUsername: ")
    val username_in = scala.io.StdIn.readLine()

    println("\nRATINGS \nPlease Select [1][2][3][4][5]: ")
    val ratings = scala.io.StdIn.readLine()

    println("Please Share Your Experience: ")
    val review = scala.io.StdIn.readLine()

    /*Established Postgres Connection To Write And Store Review In Postgres Database*/
    val write_review_inDatabase: ReviewDatabase = ReviewDatabase (0,s"$ratings", s"$username_in", s"$review")
    val queryDescription = SlickTables.reviewTable += write_review_inDatabase
    val insert_new_reviewfuture: Future[Int] = PostgresConnection.db.run(queryDescription)

    /*Future Concurrent Success and Failure Block*/
    insert_new_reviewfuture.onComplete {
      case Success(reviewcreated) =>
        ratings match {
          case "0" =>
            if (ratings == "0") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s"$username_in 😒\n $review\n\n Sorry For Your Experience, " +
                s"We Will Put It Into Consideration\n $reviewcreated Review As Been Received Thank You!!")
              Run_Program.delay(1000)
              Walmart_Review()
            }

          case "1" =>
            if (ratings == "1") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s"$username_in 😒⭐\n $review\n\n Sorry For Your Experience, " +
                s"We Will Put It Into Consideration\n $reviewcreated Review As Been Received Thank You!!")

              Run_Program.delay(1000)
              Walmart_Review()
            }

          case "2" =>
            if (ratings == "2") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s" $username_in 🙂⭐⭐\n $review\n\n Sorry For Your Experience, " +
                s"We Will Put It Into Consideration\n $reviewcreated Review As Been Received Thank You!!")

              Run_Program.delay(1000)
              Walmart_Review()
            }

          case "3" =>
            if (ratings == "3") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s"$username_in 😑⭐⭐⭐\n $review\n " +
                s"\n $reviewcreated Review As Been Received Thank You!!")

              Run_Program.delay(1000)
              Walmart_Review()
            }

          case "4" =>
            if (ratings == "4") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s"$username_in ☺⭐⭐⭐⭐\n $review\n\n Glad You Had A Great Experience " +
                s"\n $reviewcreated Review As Been Received Thank You!!")

              Run_Program.delay(1000)
              Walmart_Review()
            }
          case "5" =>
            if (ratings == "5") {

              println("---" * 30)
              Run_Program.delay(1000)

              println(s"$username_in 😊⭐⭐⭐⭐⭐\n $review\n\n Glad You Had A Wonderful Experience " +
                s"\n $reviewcreated Review As Been Received Thank You!!")

              Run_Program.delay(1000)
              Walmart_Review()

            }
          case _ =>
            println(s"\n$username_in 😊⭐⭐⭐⭐⭐😊\n $review\n\n Way To Go!!\n Thanks For Choosing An Higher Rating Option!" +
              s" Glad You Had A Wonderful Experience.\n $reviewcreated Review As Been Received Thank You!!")
            Run_Program.delay(1000)
            Walmart_Review()
        }
      case Failure(_) =>
        println(s"Username=($username_in) Can Not Be Found In The Database! Try Again!")
        Run_Program.delay(1000)
        Walmart_Review()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Store Review Information In Postgres Database*/
  def ReadReviewInDatabase(): Unit = {
    Run_Program.delay(1000)
    println("===" *23 + "WAL-MART REVIEWS PAGE" + "===" *23)
    Run_Program.delay(1000)

    /*Established Postgres Connection To Write And Store Review In Postgres Database*/
    val read_reviewFuture: Future[Seq[ReviewDatabase]] = PostgresConnection.db.run(SlickTables.reviewTable.result)

    /*Future Concurrent Success and Failure Block*/
    read_reviewFuture.onComplete {
      case Success(review) => println(s"\nAll Reviews\n${review.mkString("\n")}")
        println("---" * 30)
        println(
          """
            |   READ-REVIEW COMPLETED 📌
            |[a] Return To Review Page
            |[b] Return To Main Page
            |
            |Choose An Option: """.stripMargin)
        val input = scala.io.StdIn.readLine()

        input match {
          case "a" =>
            Run_Program.delay(1000)
            Walmart_Review()

          case "b" =>
            Run_Program.delay(1000)
            login_menu()

          case _ =>
            println("Wrong Entry, Choose Another Option [a][b]:")
            ReadReviewInDatabase()
        }
      case Failure(ex) =>
        println(s"Diagnosis: $ex")
        Run_Program.delay(1000)
        Walmart_Review()
    }
    Thread.sleep(50000)
  }

  //  def EditReview(): Unit = {
  //
  //    println("To Delete Enter Review id: ")
  //    val id: String = scala.io.StdIn.readLine()
  //
  //    println("Please Share Your Experience: ")
  //    var review = scala.io.StdIn.readLine()
  //
  //    val queryDescriptor = SlickTables.reviewTable.filter(_.username === s"$id").update(review.copy(review = s"$review"))
  //    val edit_review_futureId: Future[Int] = Connection.db.run(queryDescriptor)
  //
  //    edit_review_futureId.onComplete {
  //      case Success(review) => println(s"Review Edited $review")
  //      case Failure(ex) => println(s"User not found!, reason: $ex")
  //    }
  //
  //    Thread.sleep(5000)
  //  }

  /*Defined A Function To Delete Filtered Review Information In Postgres Database*/
  def DeleteReview(): Unit = {

    println("---" * 30)
    println("To Delete Your Reviews\nPlease Enter Your Username: ")
    val username_in: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection To Delete Review In Postgres Database*/
    val review_delete: Future [Int]= PostgresConnection.db.run(SlickTables.reviewTable.filter(_.username.like(s"$username_in")).delete)

    review_delete.onComplete {
      case Success(deleted) =>
        if (deleted == 0) {
          println(
            """
              |Failed To Delete!! Username Not Found!!
              |[1] Try Again
              |[2] Return To Review Page
              |
              |Choose An Option: """.stripMargin)
          val input = scala.io.StdIn.readLine()

          input match {

            case "1" =>
              Run_Program.delay(1000)
              DeleteReview()

            case "2" =>
              Run_Program.delay(1000)
              Walmart_Review()

            case _ =>
              println("Wrong Entry, Choose Another Option [1][2]:")
              Run_Program.delay(1000)
              DeleteReview()
          }
        }
        else println("Deletion Complete!!")
        Run_Program.delay(1000)
        Walmart_Review()

      case Failure(ex) =>
        println(s"$ex Failed To Delete")
        Run_Program.delay(1000)
        Walmart_Review()

        Thread.sleep(50000)
    }
  }

  /*Defined A Function To Instantiate Walmart Review In The Menu-Option*/
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
        println("Wrong Entry, Choose Another Option [1][2][3]:")
        Walmart_Review()
    }
    Thread.sleep(50000)
  }

  def main(args: Array[String]): Unit = {
    //              demoInsertLoginDatabase()
    //      demoReadAllLogins()
    //      demoReadDistinctLogins()
    //        demoUpdate()
    //              demoDelete()

    //      WriteReviewInDatabase()
    //      ReadReviewInDatabase()
    //      DeleteReview()
  }
}
