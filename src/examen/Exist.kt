package examen

import net.xqj.exist.ExistXQDataSource
import org.w3c.dom.Element

fun main() {
    val s = ExistXQDataSource();
    s.setProperty("serverName", "89.36.214.106")


    val conn = s.getConnection()
    println("Connexió feta")
    val sent = "for \$ruta in doc(\"//db/Tema9/Rutes.xml\")//ruta order by \$ruta/desnivell return \$ruta"
    val sent2="for \$comarca  in doc(\"//db/Tema9/comarques_poblacions_instituts.xml\")//comarques/comarca" +
                "   " +
                "    let \$nombre := \$comarca/nom/xs:string(text())" +
                "    let \$npobles := count(\$comarca/pobles/poble)" +
                "    let \$nhab := sum(\$comarca/pobles/poble/poblacio)" +
                "    let \$ninst := count(\$comarca/pobles/poble/instituts/institut)" +
                "    order by  \$comarca/nom" +
                "    return <ruta> <nombre> {\$nombre} </nombre><pobles> {\$npobles} </pobles> <habitants> {\$nhab} </habitants> <inst> {\$ninst} </inst> </ruta>"
    val cons = conn.prepareExpression (sent2)
    val rs = cons.executeQuery ()

   while (rs.next()){
        val el = rs.getObject () as Element
        println(el.getElementsByTagName("nombre").item(0).getFirstChild().getNodeValue() + " :")
        println("\tPobles: "+el.getElementsByTagName("pobles").item(0).getFirstChild().getNodeValue() + " ")
        println("\tHabitants: "+el.getElementsByTagName("habitants").item(0).getFirstChild().getNodeValue() + " ")
        println("\tInstituts: "+el.getElementsByTagName("inst").item(0).getFirstChild().getNodeValue() + " ")
    }
    conn.close()
}


