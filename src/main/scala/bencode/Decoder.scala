package me.harsi.sctorrent
package bencode

import scala.collection.mutable

object Decoder {
  private def decodeString(string: String): (BString, Int) = {
    val splitStrs = string.split(":", 2)
    val strLen = splitStrs(0).toInt
    val bStr = splitStrs(1).substring(0, strLen)
    (BString(bStr), splitStrs(0).length + 1 + strLen)
  }

  private def decodeInt(string: String): (BInt, Int) = {
    // TODO: Impl decodeInt
    (BInt(0), 0)
  }

  private def decodeList(string: String): (BList, Int) = {
    // TODO: Impl decodeList
    (BList(Vector()), 0)
  }

  private def decodeDict(string: String): (BDict, Int) = {
    // TODO: Imp decodeDict
    (BDict(mutable.Map()), 0)
  }

  def apply(string: String): List[BNode] = {
    var list: List[BNode] = List()
    var pos = 0
    while (pos < string.length)
      val (bNode, processedPos) = string.charAt(pos) match
        case d if d.isDigit => decodeString(string.substring(pos))
        case 'i' => decodeInt(string.substring(pos))
        case 'l' => decodeList(string.substring(pos))
        case 'd' => decodeDict(string.substring(pos))
        case _ => throw Exception("Data is corrupted")
      pos += processedPos
      list = list :+ bNode
    list
  }
}
