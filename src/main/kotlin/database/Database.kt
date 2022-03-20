package database

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoClients
import model.Currency
import model.Item
import model.User
import org.bson.Document
import rx.Observable
import rx.Single

class Database {

    private val database = MongoClients
        .create("mongodb://localhost:27017")
        .getDatabase("reactive")

    fun saveItem(item: Item): Observable<Boolean> {
        return save(
            "items",
            mapOf(
                "price" to item.price,
                "name" to item.name
            )
        )
    }

    fun saveCurrency(currency: Currency): Observable<Boolean> {
        return save(
            "currencies",
            mapOf(
                "name" to currency.name,
                "asDollar" to currency.asDollar
            )
        )
    }

    fun saveUser(user: User): Observable<Boolean> {
        return save(
            "users",
            mapOf(
                "name" to user.name,
                "currency" to user.currency
            )
        )
    }

    fun getUser(name: String): Single<User> {
        return database.getCollection("users").find(Filters.eq("name", name))
            .toObservable().toSingle()
            .map { document ->
                User(
                    document.getString("name"), document.getString("currency")
                )
            }
    }

    fun getCurrency(name: String): Single<Currency> {
        return database.getCollection("currencies").find(Filters.eq("name", name))
            .toObservable().toSingle()
            .map { document ->
                Currency(
                    document.getString("name"), document.getDouble("asDollar")
                )
            }
    }

    fun getItems(): Observable<Item> {
        return database.getCollection("items").find()
            .toObservable()
            .map { document ->
                Item(
                    document.getDouble("price"), document.getString("name")
                )
            }
    }

    private fun save(collection: String, content: Map<String, Any>): Observable<Boolean> {
        return database.getCollection(collection).insertOne(
            Document(content)
        ).isEmpty.map { !it }
    }

}