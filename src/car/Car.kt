package car

import enums.Status

class Car(val carID: String,
          var currentBranchID: String, var carModelNumber: String,
          var carGroupCode: String, var carStatus: Status = Status.AVAILABLE) {

    override fun toString(): String {
        return "Car(carID='$carID', carStatus=$carStatus, currentBranchID=$currentBranchID, " +
                "carModelNumber=$carModelNumber, carGroupCode=$carGroupCode)"
    }
}