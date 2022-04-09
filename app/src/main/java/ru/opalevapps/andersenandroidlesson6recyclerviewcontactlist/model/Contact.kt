package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.model

data class Contact(
    var firstName: String = "Smotrov",
    var lastName: String = "Aleksandr",
    var phone: String = "+79007777777",
    val photoURL: String = "https://picsum.photos/100",
) {
    val id: Int get() = hashCode()
}