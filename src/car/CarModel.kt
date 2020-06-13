package car

import enums.GearType

class CarModel(var modelNumber: String, private var modelDes: String, var gearType: GearType,
               private val petrolComp: Double, private val numOfDoor: Int) {
    override fun toString(): String {
        return "CarModel(modelNumber='$modelNumber', modelDes='$modelDes', gearType=$gearType, " +
                "petrolComp=$petrolComp, numOfDoor=$numOfDoor)"
    }
}