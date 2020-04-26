package com.office14.coffeedose.extensions

infix fun <T> Collection<T>.deepEqualTo(other: Collection<T>): Boolean {
    // check collections aren't same
    if (this !== other) {
        // fast check of sizes
        if (this.size != other.size) return false
        val areNotEqual = this.asSequence()
            .zip(other.asSequence())
            // check this and other contains same elements at position
            .map { (fromThis, fromOther) -> fromThis == fromOther }
            // searching for first negative answer
            .contains(false)
        if (areNotEqual) return false
    }
    // collections are same or they are contains same elements with same order
    return true
}

infix fun <T> Collection<T>.deepEqualToIgnoreOrder(other: Collection<T>): Boolean {
    // check collections aren't same
    if (this !== other) {
        // fast check of sizes
        if (this.size != other.size) return false
        val areNotEqual = this.asSequence()
            // check other contains next element from this
            .map { it in other }
            // searching for first negative answer
            .contains(false)
        if (areNotEqual) return false
    }
    // collections are same or they are contains same elements
    return true
}