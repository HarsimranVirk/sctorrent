package me.harsi.sctorrent
package bencode

import scala.collection.mutable

sealed trait BNode {
  def getChildNode(accessor: Int | String): Option[BNode]
}

case class BString(value: String) extends BNode:
  override def getChildNode(accessor: Int | String): Option[BNode] = None

case class BInt(value: Long) extends BNode:
  override def getChildNode(accessor: Int | String): Option[BNode] = None

case class BList(list: Vector[BNode]) extends BNode:
  override def getChildNode(accessor: Int | String): Option[BNode] = accessor match
    case acc: Int if acc < list.length => Some(list(acc))
    case _ => None

case class BDict(dict: mutable.Map[String, BNode]) extends BNode:
  override def getChildNode(accessor: Int | String): Option[BNode] = accessor match
    case acc: String => dict.get(acc)
    case _ => None
