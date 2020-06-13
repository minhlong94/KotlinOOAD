package rental

class CreditCard(private val creditCardID: String) : Payment() {
    override fun toString(): String {
        return "CreditCard, creditCardID=$creditCardID"
    }
}
