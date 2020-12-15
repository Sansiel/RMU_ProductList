package com.example.mylisttobuy

class Product {
    var id = 0
    var name: String
    var cost: String
    internal constructor(name: String, cost: String) {
        this.name = name
        this.cost = cost
    }
    internal constructor(id: Int, name: String, cost: String) {
        this.id = id
        this.name = name
        this.cost = cost
    }
}