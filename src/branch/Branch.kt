package branch

import car.Car

class Branch(val branchID: String, private val address: String, private val phone: String) {
    var availableCar = HashMap<String, Car>()
    var unavailableCar = HashMap<String, Car>()
    var neighborBranch = HashMap<String, Branch>()

    override fun toString(): String {
        return "Branch(BranchID='$branchID, address='$address', phone='$phone')"
    }

    // Static method
    companion object {
        /**
         * addNeighborToBothBr: both branches given by the parameter will be added as neighbor
         * to each other
         */
        fun addNeighborToBothBr(branch1: Branch, branch2: Branch) {
            branch1.neighborBranch[branch2.branchID] = branch2
            branch2.neighborBranch[branch1.branchID] = branch1
        }
    }

    /**
     * Print neighbor branch(es) of this branch
     */
    fun printNeighbor() {
        this.neighborBranch.forEach { println("Neighbor Branch of this branch: $it") }
    }

    fun printAvailableCar(carGroupCode: String? = null) {
        println("Available Car at branch $branchID ")
        if (carGroupCode != null) {
            println("with carGroupCode = $carGroupCode:")
            this.availableCar.forEach { if (it.value.carGroupCode == carGroupCode) println("\t${it.value}") }
        } else this.availableCar.forEach { println("\t${it.value}") }

    }

    fun printUnavailableCar(carGroupCode: String? = null) {
        println("Unavailable Car at branch $branchID ")
        if (carGroupCode != null) {
            println("with carGroupCode = $carGroupCode:")
            this.unavailableCar.forEach { if (it.value.carGroupCode == carGroupCode) println("\t${it.value}") }
        } else this.unavailableCar.forEach { println("\t${it.value}") }
    }

    /**
     * Make available car from this branch to unavailable
     */
    fun makeAvailToUnavail(car: Car) {
        this.availableCar.remove(car.carID)
        this.unavailableCar[car.carID] = car
    }


}