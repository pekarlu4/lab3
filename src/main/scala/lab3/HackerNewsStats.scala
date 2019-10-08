package lab3

import java.io.File

object HackerNewsStats extends App {

  def readAll(dirname: String): List[HackerNewsItem] = {
    val dir = new File(dirname)
    val files = dir.listFiles()

    files
      .map(HackerNewsItem.fromFile)
      .foldLeft(List[HackerNewsItem]())(_ including _)
  }

  def correlation(points: List[(Double, Double)]): Double = ???

  val items = readAll("data/data/item")

  // 1. how many news posts are there
  println("Number of news: " + items.size)
  // 2. how many stories are there
  println("Number of stories: " + items.filter(_.kind == "story").size)

  println(items.take(5).toString)
}
