package utils

object ActualCostCalculator {
    // Encapsulation principle
    fun costAfterDiscountPercentage(fullCost: Double, discount: Double): Double {
        // formula: actualCost = fullCost * (100-discount)%
        return fullCost * (100 - discount) * 0.01
    }
}