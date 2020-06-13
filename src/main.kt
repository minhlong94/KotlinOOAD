import branch.Branch
import car.Car
import car.CarGroup
import car.CarModel
import customer.Customer
import enums.GearType
import enums.Status
import rental.CreditCard
import rental.WalkInRental
import utils.CustomCalendar
import kotlin.test.assertFails

fun main() {
    /**
     * Inserting data part
     * This part can be done with init{...} in class DURentSystem
     */
    println("Inserting data part.")
    println("=====================================")
    DURentSystem.addCarGroup(CarGroup("A", 100.0))
    DURentSystem.addCarGroup(CarGroup("B", 200.0))
    DURentSystem.addCarGroup(CarGroup("C", 150.0))

    println("=====================================")
    DURentSystem.addCarModel(CarModel("M001", "Big",
            GearType.AUTOMATIC, 5.0, 4))
    DURentSystem.addCarModel(CarModel("M002", "Big and Furious",
            GearType.AUTOMATIC, 8.0, 4))
    DURentSystem.addCarModel(CarModel("M003", "Scary",
            GearType.AUTOMATIC, 2.0, 8))
    DURentSystem.addCarModel((CarModel("M004", "Rare",
            GearType.AUTOMATIC, 6.0, 2)))

    println("=====================================")
    DURentSystem.addBranch(Branch("B001", "Lothric", "01234"))
    DURentSystem.addBranch(Branch("B002", "Lordran", "04653"))
    DURentSystem.addBranch(Branch("B003", "Farron", "04624"))
    DURentSystem.addBranch((Branch("B004", "Anor Londor", "06341")))
    DURentSystem.addBranch(Branch("B005", "Irithyll", "02456"))

    println("=====================================")
    DURentSystem.make2BrNeighbor("B001", "B002")
    DURentSystem.make2BrNeighbor("B001", "B003")

    println("=====================================")
    DURentSystem.addCustomer(Customer("Wick", "0143658", "09435821675", false))
    DURentSystem.addCustomer(Customer("Lordran", "2349751", "04931652374", false))
    DURentSystem.addCustomer(Customer("Sulyvahn", "2341863", "0123687485", false))
    DURentSystem.addCustomer(Customer("Gwyn", "8432094", "05796345163", false))

    /*
    Customer was created with customerID = 8655-1675-4347
    Customer was successfully added.
    Customer was created with customerID = 4254-6411-6665
    Customer was successfully added.
    Customer was created with customerID = 5181-4165-8786
    Customer was successfully added.
    Customer was created with customerID = 0424-6202-5716
    Customer was successfully added.
     */

    DURentSystem.addCustomerToDiscount("8655-1675-4347", 30.0)
    println("=====================================")

    DURentSystem.addCar(Car("C001", "B001",
            "M001", "A"))
    DURentSystem.addCar(Car("C002", "B001",
            "M004", "C"))
    DURentSystem.addCar(Car("C003", "B002",
            "M001", "B"))
    DURentSystem.addCar(Car("C004", "B003",
            "M002", "B"))
    DURentSystem.addCar(Car("C005", "B004",
            "M003", "B"))

    println("=====================================")
    DURentSystem.printCarOfBranch("B001")
    DURentSystem.printCarOfBranch("B002")
    DURentSystem.printUnCarOfBranch("B001")
    DURentSystem.printUnCarOfBranch("B002")

    println("=====================================")
    DURentSystem.printCarOfBranchGroup("B001", "A")
    DURentSystem.printCarNbBranchOfBranchGroup("B001", "B")
    println("=====================================")

    val walkInRental1 = WalkInRental("0424-6202-5716", "C001", CustomCalendar.getInstanceCalendar(),
            CustomCalendar.getSpecificCalendar(2020, 5, 31), CreditCard("2010485618"),
            DURentSystem.getFullCost("C001"), Status.PROCESSING)
    val walkInRental2 = WalkInRental("8655-1675-4347", "C003", CustomCalendar.getInstanceCalendar(),
            CustomCalendar.getSpecificCalendar(2020, 7, 31), CreditCard("9125718247"),
            DURentSystem.getFullCost("C003"), Status.PROCESSING)



    DURentSystem.enterWalkInRental(walkInRental1)
    DURentSystem.enterWalkInRental(walkInRental2)
    /*
    Rental was created with rentalNumber = clCaXYsZdnzcljNw
    Rental was created with rentalNumber = mfpENzfdvIJnVBCg
    */

    println("=============================================================")
    println("Prototype Testing part.")
    println("=====================================")
    println("Add branch with the same branchID: ")
    println(assertFails { DURentSystem.addBranch(Branch("B001", "CA", "981475827345")) })

    println("=====================================")
    println("Make two branch neighbor, but they already are: ")

    println(assertFails { DURentSystem.make2BrNeighbor("B001", "B002") })


    println("=====================================")
    println("Make two branch neighbor, but they already are: ")

    println(assertFails { DURentSystem.make2BrNeighbor("B001", "B002") })

    println("=====================================")
    println("Add CarGroup with the same code: ")

    println(assertFails { DURentSystem.addCarGroup(CarGroup("A", 13.3)) })

    println("=====================================")
    println("Add CarModel with the same modelNumber: ")

    println(assertFails {
        DURentSystem.addCarModel(CarModel("M001", "Tanky",
                GearType.MANUAL, 4.4, 2))
    })

    println("=====================================")
    println("Add Car with the same carID: ")
    println(assertFails {
        DURentSystem.addCar(Car("C001", "B004",
                "M003", "B"))
    })
    println("=====================================")
    println("Print available Car belongs to a Branch in a specific CarGroup, but: ")
    println("BranchID is not available: ")
    println(assertFails {
        DURentSystem.printCarOfBranchGroup("B946845", "A")
    })
    println("=====================================")
    println("CarGroupCode does not exist: ")
    println(assertFails {
        DURentSystem.printCarOfBranchGroup("B001", "GG")
    })
    println("=====================================")
    println("Print available Car belongs to a neighbor branch of a branch " +
            "in a specific CarGroup, but: ")
    println("BranchID is not available: ")
    println(assertFails {
        DURentSystem.printCarNbBranchOfBranchGroup("B4384573824", "B")
    })
    println("=====================================")
    println("CarGroupCode does not exist: ")
    println(assertFails {
        DURentSystem.printCarNbBranchOfBranchGroup("B4384573824", "GG")
    })
    println("=====================================")
    println("Enter walk-in rental, but: ")
    println("CustomerID does not exist: ")
    println(assertFails {
        DURentSystem.enterWalkInRental(WalkInRental("9999-9999-9999", "C001",
                CustomCalendar.getInstanceCalendar(), CustomCalendar.getSpecificCalendar(2022, 5, 31),
                CreditCard("2010485618"), DURentSystem.getFullCost("C001"), Status.PROCESSING))
    })

    println("=====================================")
    println("CarID does not exist: ")
    println(assertFails {
        DURentSystem.enterWalkInRental(WalkInRental("0424-6202-5716", "C666",
                CustomCalendar.getInstanceCalendar(), CustomCalendar.getSpecificCalendar(2020, 5, 31),
                CreditCard("2010485618"), DURentSystem.getFullCost("C001"), Status.PROCESSING))
    })
    println("=====================================")
    println("Record car return, but: ")
    println("rentalNumber does not exist: ")

    println(assertFails {
        DURentSystem.recordCarReturn("OwO",
                CustomCalendar.getSpecificCalendar(2020, 7, 1), "B002")
    })
    println("BranchID does not exist: ")
    println(assertFails {
        DURentSystem.recordCarReturn("clCaXYsZdnzcljNw",
                CustomCalendar.getSpecificCalendar(2020, 7, 1), "B666")
    })

    println("=============================================================")

    DURentSystem.printRental("clCaXYsZdnzcljNw")

    println("=============================================================")
    println("Rental testing part.")
    DURentSystem.printCarOfBranch("B001")
    DURentSystem.printCarOfBranch("B002")
    DURentSystem.printUnCarOfBranch("B001")
    DURentSystem.printUnCarOfBranch("B002")
    println("=====================================")
    println("Rent a car that is unavailable: ")
    println(assertFails {
        DURentSystem.enterWalkInRental(WalkInRental("5181-4165-8786", "C001",
                CustomCalendar.getInstanceCalendar(), CustomCalendar.getSpecificCalendar(2020, 7, 31),
                CreditCard("9125718247"), DURentSystem.getFullCost("C003"), Status.PROCESSING))
    })

    println("=============================================================")
    println("Successfully record the car return: ")
    DURentSystem.recordCarReturn("clCaXYsZdnzcljNw",
            CustomCalendar.getSpecificCalendar(2020, 7, 1), "B002")

    println("=============================================================")
    DURentSystem.printCarOfBranch("B001")
    DURentSystem.printCarOfBranch("B002")
    DURentSystem.printUnCarOfBranch("B001")
    DURentSystem.printUnCarOfBranch("B002")

}

