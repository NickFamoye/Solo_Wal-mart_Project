package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.language.postfixOps

class DataManager {

  /*Establishing New Instance Of Data_Analysis Class To map
  Each Query Function To A Specific Case*/
  val data_analysis = new Data_Analysis()

  /*ESTABLISHING SPARK SESSION CONNECTION
  FOR SPARK SQL QUERIES*/
  val spark: SparkSession = SparkSession
    .builder()
    .appName(" WAL-MART SAVE PEOPLE MONEY APPLICATION")
    .config("spark.master", "local")
    .getOrCreate()

  /*Defined A Function To Convert Dataset To DataFrame*/
  def SparkSQL_Table(): Unit = {

    val prices: DataFrame = spark.read.format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("src/Input/Project_for_Nicholas_Dataset .csv")
      .toDF("Store", "Brand", "Product", "Department", "Prices")
    prices.createOrReplaceTempView("Grocery_StoreBrand_Comparison")
    prices.show(10, 100, vertical = false)
  }

  /* Defined A Function To End SparkSession*/
  def closeSpark(): Unit = {
    println("Spark Session closed")
    spark.close
  }

  /*Defined A Function To Case Match The Query Options*/
  def query(queryNumber: String): Unit = {
    queryNumber match {
      case "Q1_a" => data_analysis.Store_Brand(spark: SparkSession)
      case "Q1_b" => data_analysis.Name_Brand(spark: SparkSession)
      case "Q2_a" => data_analysis.Prescription_Drug(spark: SparkSession)
      case "Q2_b" => data_analysis.Medication_Total(spark: SparkSession)
    }
  }
}
