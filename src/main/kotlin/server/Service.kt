package server

import database.Database
import model.Currency
import model.Item
import model.User
import rx.Observable
import view.ItemView

class Service(private val database: Database) {

    fun addUser(userName: String, currency: String): Observable<Boolean> {
        return database.saveUser(User(userName, currency))
    }

    fun addItem(price: Double, name: String): Observable<Boolean> {
        return database.saveItem(Item(price, name))
    }

    fun addCurrency(name: String, asDollar: Double): Observable<Boolean> {
        return database.saveCurrency(Currency(name, asDollar))
    }

    fun getAllItems(userName: String): Observable<ItemView> {
        return database.getUser(userName)
            .flatMap { database.getCurrency(it.currency) }
            .flatMapObservable { currency ->
                database.getItems().map { item -> itemToView(currency, item) }
            }
    }

    private fun itemToView(currency: Currency, item: Item): ItemView {
        return ItemView(
            name = item.name,
            price = item.price / currency.asDollar
        )
    }
}