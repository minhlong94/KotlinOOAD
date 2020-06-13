package rental

import DURentSystem
import car.Car
import enums.Status
import utils.Randomizer
import java.util.*

abstract class Rental(var customerID: String,
                      var carID: String, private var createDate: Date, private var expectedReturn: Date,
                      private var payment: Payment, var fullCost: Double
                      , private var status: Status, var actualReturn: Date? = null,
                      var returnedBranchID: String? = null, var actualCost: Double? = null) {

    // Randomize rentalNumber
    val rentalNumber = Randomizer.randomString()

    init {
        println("Rental was created with rentalNumber = $rentalNumber")
    }

    private val pickUpBranchID = DURentSystem.getCarFromCarID(carID).currentBranchID

    // Set rental status for both this rental and the car
    fun setRentalStatus(status: Status, car: Car) {
        this.status = status
        car.carStatus = status
    }

    override fun toString(): String {
        return "Rental(customerID='$customerID', carID='$carID', createDate=$createDate, expectedReturn=$expectedReturn, payment=$payment, fullCost=$fullCost, status=$status, actualReturn=$actualReturn, returnedBranchID=$returnedBranchID, actualCost=$actualCost, rentalNumber='$rentalNumber', pickUpBranchID=$pickUpBranchID)"
    }

}

