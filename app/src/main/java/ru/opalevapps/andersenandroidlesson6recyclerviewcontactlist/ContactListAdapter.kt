package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlin.collections.ArrayList


class ContactListAdapter(
    private val context: Context,
    private val contacts: ArrayList<Contact>
) : BaseAdapter() {
    //    private lateinit var context: Context
//    private lateinit var layoutInflater: LayoutInflater
//    private lateinit var objects: ArrayList<Contact>

    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var phone: TextView

    override fun getCount(): Int = contacts.size

    override fun getItem(p0: Int): Any = contacts[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, p2, false)

        firstName = view!!.findViewById(R.id.tvContactFirstName)
        lastName = view.findViewById(R.id.tvContactLastName)
        phone = view.findViewById(R.id.tvContactPhone)
        firstName.text = contacts[p0].firstName
        lastName.text = contacts[p0].lastName
        phone.text = contacts[p0].phone
        return view
    }
}