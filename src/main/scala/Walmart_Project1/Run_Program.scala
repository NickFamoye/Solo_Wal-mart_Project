package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.Console.println

/*Created An Object To Run The Application*/
object Run_Program {

  /*TURN LOG OFF*/
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val dataManager = new DataManager

  /*Defined A Function To Display Developers At The Beginning Of The Application*/
  def Developer (n: String): Unit = {
    n match {
      case stars =>
        println(stars * 5)
        println("Nicholas Famoye: Big Data Engineer")
        println("Ethan Kemp: Big Data Engineer")
    }
  }
  Developer(n = "⭐")

  /*Defined A Function To Create A Temporary Delay Between Menu Screens*/
  def delay(milliseconds: Int): Unit ={
    Thread.sleep(milliseconds)
  }

  /*Defined A Function To Create A Temporary Pause For Output In The Console*/
  def pause(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
    println("\nPress Any Key To Return To Main Page: ")
    scala.io.StdIn.readLine()
    Menu_Options.login_menu()
  }

  /*Defined A Function To Instantiate SparkSql Table*/
  def queryApp(): Unit= {
    dataManager.SparkSQL_Table()

  }

  /*Defined Main Function To Run The Program*/
  def main(args: Array[String]): Unit = {
    Menu_Options.login_menu()
  }
}
