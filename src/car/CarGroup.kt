package car

class CarGroup(val code: String, var rentalPrice: Double? = 0.0) {
    init {
        if (rentalPrice == 0.0) println("Group's rental price is initially set as 0.")
    }

    override fun toString(): String {
        return "CarGroup(code='$code', rentalPrice=$rentalPrice)"
    }
}