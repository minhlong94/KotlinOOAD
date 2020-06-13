package customer

import utils.Randomizer

class Customer(private var name: String, private var license: String, private var phone: String, var isBlackList: Boolean) {
    // Randomize customerID
    val customerID: String = Randomizer.randomNumbersAsString()

    init {
        println("Customer was created with customerID = $customerID")
    }

    override fun toString(): String {
        return "Customer(name='$name', license='$license', phone='$phone', isBlackList=$isBlackList, customerID='$customerID')"
    }

}