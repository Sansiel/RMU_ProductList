package com.example.mylisttobuy

class ListToBuy {
    var id: Int = 0
    var cost: Int? = null
    var prodName: String? = null
    constructor(prodName: String, cost: Int) {
        this.cost = cost
        this.prodName = prodName
    }
    constructor(){}

}