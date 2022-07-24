package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

/*Created A Case Class To Pattern Match Attributes And It's Column*/
case class LoginDatabase(id: Long, username: String, password: String)
case class ReviewDatabase(id: Long, Ratings: String, username: String, review: String)

/*Created An Object To Instantiate Database Tables Features*/
object SlickTables{

  class LoginTable(tag: Tag) extends Table[LoginDatabase](tag, Some("logindatabase")/* <- schema name */, "LoginDatabase"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")

    /*Mapping Function to the case class*/
    override def * = (id,username, password) <> (LoginDatabase.tupled, LoginDatabase.unapply)
  }

  class ReviewTable(tag: Tag) extends Table[ReviewDatabase](tag, Some("reviewdatabase")/* <- schema name */, "ReviewDatabase"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def ratings = column[String]("ratings")
    def username = column[String]("username")
    def review = column[String]("review")

    /*Mapping Function to the case class*/
    override def * = (id,ratings,username,review) <> (ReviewDatabase.tupled, ReviewDatabase.unapply)
  }

  /*API entry point*/
  lazy val loginTable = TableQuery[LoginTable]
  lazy val reviewTable = TableQuery[ReviewTable]
}
