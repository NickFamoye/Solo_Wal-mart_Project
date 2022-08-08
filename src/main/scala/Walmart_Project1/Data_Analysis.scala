package Walmart_Project1

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.spark.sql.SparkSession

import scala.Console.println

class Data_Analysis {

  /*Defined A Function For SparkSql Query Analysis*/
  def Store_Brand(spark:SparkSession): Unit ={
    println(
      """Walmart Store Brand Products Limited To 10 Rows""".stripMargin)
    val price_diffa = spark.sql("select * from Grocery_StoreBrand_Comparison where Store = 'Walmart' and " +
      "Brand = 'Store' limit 10")
    price_diffa.show()
    println("---" * 30)
    Run_Program.delay(2000)

    println(
      """Competitor Store Brand Products Limited To 10 Rows""".stripMargin)
    val price_diffb = spark.sql("select * from Grocery_StoreBrand_Comparison where Store = 'Competitor' and " +
      "Brand = 'Store' limit 10")
    price_diffb.show()
    println("---" * 30)

    Run_Program.delay(3000)
    println(
      """Competitor Total Store Brand Groceries""".stripMargin)
    val price_diffc = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Competitor' and Brand = 'Store' limit 10").toDF("Total_StoreBrand_Groceries")
    price_diffc.show()
    println("---" * 30)

    println(
      """Walmart Total Store Brand Groceries
        |Ways To Save Money 👍""".stripMargin)
    val price_diffd = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Walmart' and Brand = 'Store' limit 10").toDF("Total_StoreBrand_Groceries")
    price_diffd.show()
  }

  /*Defined A Function For SparkSql Query Analysis*/
  def Name_Brand(spark:SparkSession): Unit = {
    println(
      """Competitor Total Name Brand Groceries""".stripMargin)
    val price_namea = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Competitor' and Brand = 'Name' limit 10").toDF("Total_NameBrand_Groceries")
    price_namea.show()
    println("---" * 30)
    Run_Program.delay(2000)

    println(
      """Walmart Total Name Brand Groceries
        |Ways To Save Money 👍""".stripMargin)
    val price_nameb = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Walmart' and Brand = 'Name' limit 10").toDF("Total_NameBrand_Groceries")
    price_nameb.show()
    Run_Program.delay(2000)
  }

  /*Defined A Function For SparkSql Query Analysis*/
  def Prescription_Drug(spark: SparkSession): Unit = {
    println(
      """Pharmacy Medication Overview Limited To 10 Rows""".stripMargin)
    val price_diffb = spark.sql("select * from Grocery_StoreBrand_Comparison where Store = 'Walmart' and Department = 'Pharmacy' limit 10")
    price_diffb.show()
    println("---" * 30)
    Run_Program.delay(2000)

    println(
      """Pharmacy Medication Overview Limited To 10 Rows""".stripMargin)
    val price_diffc = spark.sql("select * from Grocery_StoreBrand_Comparison where Store = 'Competitor' and Department = 'Pharmacy' limit 10")
    price_diffc.show()
    println("---" * 30)
    Run_Program.delay(2000)
  }

  /*Defined A Function For SparkSql Query Analysis*/
  def Medication_Total(spark: SparkSession): Unit = {
    println(
      """Competitor Total Store Brand Groceries""".stripMargin)
    val price_namea = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Competitor' and Department = 'Pharmacy' limit 10").toDF("Pharmaceutical_Total")
    price_namea.show()
    println("---" * 30)
    Run_Program.delay(2000)

    println(
      """Walmart Total Store Brand Groceries
        |Ways To Save Money 👍""".stripMargin)
    val price_nameb = spark.sql("select Sum(Prices) from Grocery_StoreBrand_Comparison " +
      "where Store = 'Walmart' and Department = 'Pharmacy' limit 10").toDF("Pharmaceutical_Total")
    price_nameb.show()
  }
}
