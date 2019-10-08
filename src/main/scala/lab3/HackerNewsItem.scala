package lab3

import java.io.{File, FileInputStream, ObjectInputStream}

/**
 * A generic news item fetched from Hacker News using the [firebaseio API](https://github.com/HackerNews/API).
 *
 * @param id news ID
 * @param kind one of "job", "story", "comment", "poll", or "pollopt"
 * @param by username of the news's author
 * @param time creation date of the item, in Unix Time.
 * @param title of the story, poll or job.
 * @param text of the comment, story or poll, could contain HTML.
 * @param kids ids of the item's comments in some order
 * @param url of the story
 * @param score of the story or votes for a pollopt
 * @param descendants In the case of stories or polls, the total comment count.
 */
@SerialVersionUID(1)
class HackerNewsItem(val id: Int,
                     val kind: String,
                     val by: String,
                     val time: Long,
                     val title: Option[String],
                     val text: Option[String],
                     val kids: List[Int],
                     val url: Option[String],
                     val score: Option[Int],
                     val descendants: Option[Int])
  extends Serializable {

  override def equals(o: Any): Boolean =
    if (o == null || (getClass ne o.getClass)) {
      false
    } else {
      id == o.asInstanceOf[HackerNewsItem].id
    }

  override def hashCode: Int = java.util.Objects.hash(id)

  override def toString: String =
    s"News(id=$id, kind=$kind, by=$by, time=$time, title=${title.getOrElse("")}, text=${text
      .getOrElse()}, kids=$kids, url=$url, score=$score, descendants=$descendants)"
}

object HackerNewsItem {
  def fromFile(file: File): HackerNewsItem = {
    val fis = new ObjectInputStream(new FileInputStream(file))
    val obj = fis.readObject()
    fis.close()
    obj.asInstanceOf[HackerNewsItem]
  }
}
