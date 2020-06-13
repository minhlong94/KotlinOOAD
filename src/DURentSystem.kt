import DURentSystem.nonExistException
import branch.Branch
import car.Car
import car.CarGroup
import car.CarModel
import customer.Customer
import enums.GearType
import enums.Status
import exceptions.BlackListException
import exceptions.DuplicateException
import exceptions.NonExistException
import exceptions.NotAvailableException
import rental.Rental
import rental.WalkInRental
import utils.ActualCostCalculator
import java.util.*
import kotlin.collections.HashMap

object DURentSystem {
    /**
     * Singleton object DURentSystem
     * All properties are HashMaps. For example:
     * branchHashMap: key = branchID => value = Branch
     * customerHashMap: key  = customerID => value = Customer
     */

    var branchHashMap = HashMap<String, Branch>()
    var customerHashMap = HashMap<String, Customer>()
    var rentalHashMap = HashMap<String, Rental>()
    var discountHashMap = HashMap<String, Double>()
    var blacklistHashMap = HashMap<String, Customer>()
    var carHashMap = HashMap<String, Car>()
    var carGroupHashMap = HashMap<String, CarGroup>()
    var carModelHashMap = HashMap<String, CarModel>()

    /**
     * Exceptions and Utils
     * @see duplicateException
     * @see nonExistException
     * @see blackListException
     */

    private val duplicateException = DuplicateException()
    private val nonExistException = NonExistException()
    private val blackListException = BlackListException()
    private val notAvailableException = NotAvailableException()

    fun addBranch(newBranch: Branch) {
        verifyIfBranchIsNew(newBranch.branchID)
        this.branchHashMap[newBranch.branchID] = newBranch
        println("Branch was successfully added.")
    }


    fun make2BrNeighbor(branch1ID: String, branch2ID: String) {

        val leftBranch = getBranchByBranchID(branch1ID)
        val rightBranch = getBranchByBranchID(branch2ID)

        val findNbBranch = leftBranch.neighborBranch[rightBranch.branchID]
        if (findNbBranch != null) throw duplicateException

        Branch.addNeighborToBothBr(leftBranch, rightBranch)
        println("Two branches were successfully assigned as neighbors.")
    }

    fun addCarGroup(carGroup: CarGroup) {
        getCarGroupByCode(carGroup.code)
        this.carGroupHashMap[carGroup.code] = carGroup
        println("Car Group was successfully added.")
    }

    fun addCarModel(carModel: CarModel) {
        verifyIfCarModelIsNew(carModel.modelNumber)
        if (carModel.gearType !in GearType.values()) throw nonExistException
        this.carModelHashMap[carModel.modelNumber] = carModel
        println("Car Model was successfully added.")
    }


    fun addCar(car: Car) {
        verifyIfCarIsNew(car.carID)
        verifyIfCarGroupIsNotNew(car.carGroupCode)
        verifyIfCarModelIsNotNew(car.carModelNumber)
        val findBranch = getBranchByBranchID(car.currentBranchID)
        this.carHashMap[car.carID] = car
        findBranch.availableCar[car.carID] = car
        println("Car was successfully added.")
    }

    fun addCustomer(customer: Customer) {
        this.customerHashMap[customer.customerID] = customer
        if (customer.isBlackList) blacklistHashMap[customer.customerID] = customer
        println("Customer was successfully added.")
    }

    /**
     * printCarOfBranchGroup: print available car of a specific carGroup,
     * from a specific branch, given by branchID and carGroupCode
     */

    fun printCarOfBranchGroup(branchID: String, carGroupCode: String) {
        val findBranch = getBranchByBranchID(branchID)
        verifyIfCarGroupIsNotNew(carGroupCode)
        findBranch.printAvailableCar(carGroupCode)
    }

    /**
     * printCarNbBranchOfBranchGroup: print available car of a specific carGroup,
     * from neighbor branches of a specific branch, given by branchID and carGroupCode
     */

    fun printCarNbBranchOfBranchGroup(branchID: String, carGroupCode: String) {
        println("Available car of neighbor branch of branch $branchID: ")
        val findBranch = getBranchByBranchID(branchID)
        verifyIfCarGroupIsNotNew(carGroupCode)
        for ((_, eachBranch) in findBranch.neighborBranch) {
            eachBranch.printAvailableCar(carGroupCode)
        }
    }


    fun enterWalkInRental(walkInRental: WalkInRental) {

        val findBlackList = this.blacklistHashMap[walkInRental.customerID]
        if (findBlackList != null) throw blackListException

        verifyIfCustomerIsNotNew(walkInRental.customerID)
        val findCar = getCarFromCarID(walkInRental.carID)
        if (findCar.carStatus != Status.AVAILABLE) throw notAvailableException

        val findBranch = getBranchByBranchID(findCar.currentBranchID)
        findBranch.makeAvailToUnavail(findCar)

        val discounted = this.discountHashMap[walkInRental.customerID] ?: 0.0
        walkInRental.actualCost = ActualCostCalculator.costAfterDiscountPercentage(walkInRental.fullCost, discounted)

        this.rentalHashMap[walkInRental.rentalNumber] = walkInRental
        walkInRental.setRentalStatus(Status.PROCESSING, findCar)
        println("Walk-in rental was successfully added.")
    }


    fun recordCarReturn(rentalNumber: String, actualReturnDate: Date, returnedBranchID: String) {
        val findRental = getRentalByRentalNumber(rentalNumber)
        val findBranch = getBranchByBranchID(returnedBranchID)
        val findCar = getCarFromCarID(findRental.carID)
        val findExpectedReturnBranch = getBranchByBranchID(findCar.currentBranchID)

        findRental.actualReturn = actualReturnDate
        findRental.returnedBranchID = returnedBranchID
        if (findExpectedReturnBranch.branchID != returnedBranchID) {
            findExpectedReturnBranch.unavailableCar.remove(findCar.carID)
            findCar.currentBranchID = returnedBranchID
        }

        findRental.setRentalStatus(Status.SERVICED, findCar)
        findBranch.availableCar[findCar.carID] = findCar
        println("Car return was successfully recorded.")
    }


    /**
     * Extra methods
     */

    fun getCarFromCarID(carID: String): Car {
        return this.carHashMap[carID] ?: throw nonExistException
    }

    fun getFullCost(carID: String): Double {
        val groupCode = this.carHashMap[carID]?.carGroupCode
        return this.carGroupHashMap[groupCode]?.rentalPrice ?: throw nonExistException
    }

    fun printUnCarOfBranch(branchID: String) {
        val findBranch = getBranchByBranchID(branchID)
        findBranch.printUnavailableCar()
    }

    fun printCarOfBranch(branchID: String) {
        val findBranch = getBranchByBranchID(branchID)
        findBranch.printAvailableCar()
    }

    fun printRental(rentalNumber: String) {
        val findRental = this.rentalHashMap[rentalNumber] ?: throw nonExistException
        println(findRental.toString())
    }

    fun addCustomerToDiscount(customerID: String, value: Double) {
        this.discountHashMap[customerID] = value
        println("Customer $customerID was successfully added to discount list.")
    }


    /**
     * API of DURentSystem. All the following methods are private
     */


    private fun getBranchByBranchID(branchID: String): Branch {
        return this.branchHashMap[branchID] ?: throw nonExistException
    }


    private fun verifyIfBranchIsNew(branchID: String) {
        val findBranch = this.branchHashMap[branchID]
        if (findBranch != null) throw duplicateException
    }


    private fun verifyIfCarGroupIsNotNew(carGroupCode: String) {
        this.carGroupHashMap[carGroupCode] ?: throw nonExistException
    }


    private fun getCarGroupByCode(carGroupCode: String): CarGroup? {
        val carGroup = this.carGroupHashMap[carGroupCode]
        if (carGroup != null) throw duplicateException
        return carGroup
    }


    private fun verifyIfCustomerIsNotNew(customerID: String) {
        this.customerHashMap[customerID] ?: throw nonExistException
    }

    private fun getRentalByRentalNumber(rentalNumber: String): Rental {
        return rentalHashMap[rentalNumber] ?: throw nonExistException
    }


    private fun verifyIfCustomerIsNew(customerID: String) {
        val findCustomer = this.customerHashMap[customerID]
        if (findCustomer != null) throw duplicateException
    }

    private fun verifyIfCarIsNew(carID: String) {
        val findCar = this.carHashMap[carID]
        if (findCar != null) throw duplicateException
    }

    private fun verifyIfCarModelIsNotNew(modelNumber: String) {
        this.carModelHashMap[modelNumber] ?: throw nonExistException
    }

    private fun verifyIfCarModelIsNew(modelNumber: String) {
        val findCarMode = this.carModelHashMap[modelNumber]
        if (findCarMode != null) throw duplicateException
    }

}