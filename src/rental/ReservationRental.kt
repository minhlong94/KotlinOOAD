package rental

import enums.Status
import java.util.*

class ReservationRental(customerID: String,
                        carID: String, createDate: Date, expectedReturn: Date,
                        payment: Payment, fullCost: Double
                        , status: Status, actualReturn: Date? = null, returnedBranchID: String? = null, actualCost: Double? = null) :
        Rental(customerID, carID, createDate, expectedReturn
                , payment, fullCost, status, actualReturn, returnedBranchID, actualCost)
