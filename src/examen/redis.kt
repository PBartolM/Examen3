package examen

import redis.clients.jedis.Jedis
import java.util.*
import kotlin.collections.ArrayList

fun main() {
//    val con = Jedis("localhost")
    val con = Jedis("89.36.214.106")
    con.connect()
    con.auth("ieselcaminas.ad")

    for (i in 1..25){
        con.sadd("BomboNums_10","$i")
    }
    con.sadd("BomboPremis_10","Primer premi")
    con.sadd("BomboPremis_10","Segon premi")
    con.sadd("BomboPremis_10","Tercer premi")
    for (i in 1..5){
        con.sadd("BomboPremis_10","Pedrea $i")
    }

    val bomboN = con.smembers("BomboNums_10")
    val bomboP = con.smembers("BomboPremis_10")
    for (key in bomboP) {
        val num= con.spop("BomboNums_10")
        println("$key : $num")
    }
    val bomboN2 = con.smembers("BomboNums_10")
    print("Numeros no premiats $bomboN2")

    con.close()
}

object StringType {
    fun print(key: String, con: Jedis) = println(con.get(key))
}

object HashType {
    fun print(key: String, con: Jedis) {
        val keys = con.hgetAll(key)
        println(key)
        for (keyValue in keys) {
            println("\t${keyValue.key} --> ${keyValue.value}")
        }

    }
}

object ListType {
    fun print(key: String, con: Jedis) {
        val set = con.lrange(key, 0, -1)
        println(key)
        for (value in set) {
            println("\t${value}")
        }

    }
}

object SetType {
    fun print(key: String, con: Jedis) {
        val set = con.smembers(key)
        println(key)
        for (value in set) {
            println("\t${value}")
        }

    }
}

object ZSetType {
    fun print(key: String, con: Jedis) {
        val set = con.zrangeWithScores(key, 0, -1)
        println(key)
        for (t in set) {
            println("\t${t.element} --> ${t.score}")
        }

    }
}
