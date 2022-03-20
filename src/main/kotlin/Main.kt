import database.Database
import handler.HttpHandler
import io.reactivex.netty.protocol.http.server.HttpServer
import server.Service

fun main() {
    val database = Database()
    val service = Service(database)
    val handler = HttpHandler(service)

    HttpServer
        .newServer(8080)
        .start { req, resp ->
            val result = handler.processRequest(req)

            resp.writeString(result)
        }
        .awaitShutdown()

}