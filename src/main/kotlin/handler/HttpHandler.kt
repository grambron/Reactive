package handler

import io.reactivex.netty.protocol.http.server.HttpServerRequest
import rx.Observable
import server.Service

class HttpHandler(private val server: Service) {

    fun processRequest(request: HttpServerRequest<*>): Observable<String> {
        return when (request.decodedPath) {
            "/currency/add" -> server.addCurrency(
                name = request.getParam("name"),
                asDollar = request.getParam("asDollar").toDouble()
            )
            "/user/add" -> server.addUser(
                userName = request.getParam("name"),
                currency = request.getParam("currency")
            )
            "/item/add" -> server.addItem(
                price = request.getParam("price").toDouble(),
                name = request.getParam("name")
            )
            "/items" -> server.getAllItems(request.getParam("userName"))
            else -> Observable.just("Illegal request")
        }.map { it.toString() }
    }

    private fun HttpServerRequest<*>.getParam(name: String): String {
        return queryParameters[name]?.get(0) ?: error("Np $name parameter present")
    }

}