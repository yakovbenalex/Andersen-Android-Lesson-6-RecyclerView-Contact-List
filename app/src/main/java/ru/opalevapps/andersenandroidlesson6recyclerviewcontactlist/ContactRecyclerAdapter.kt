package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class ContactRecyclerAdapter(
    private val context: Context,
    private val contactsArrayList: ArrayList<Contact>,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<ContactRecyclerAdapter.MyViewHolder>() {
//    private lateinit var firstName: TextView
//    private lateinit var lastName: TextView
//    private lateinit var phone: TextView

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.tvContactFirstName)
        val lastName: TextView = itemView.findViewById(R.id.tvContactLastName)
        val phone: TextView = itemView.findViewById(R.id.tvContactPhone)

        fun bind(contact: Contact, clickListener: OnItemClickListener, position: Int){
            firstName.text = contact.firstName
            lastName.text = contact.lastName
            phone.text = contact.phone

            itemView.setOnClickListener {
                clickListener.onItemClicked(contact, position.toLong())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)

//        firstName = itemView!!.findViewById(R.id.tvContactFirstName)
//        lastName = itemView.findViewById(R.id.tvContactLastName)
//        phone = itemView.findViewById(R.id.tvContactPhone)

//        firstName.text = contacts[p0].firstName
//        lastName.text = contacts[p0].lastName
//        phone.text = contacts[p0].phone
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val contact = contactsArrayList[position]
        holder.bind(contactsArrayList[position], itemClickListener, position)
//        holder.firstName.text = contact.firstName
//        holder.lastName.text = contact.lastName
//        holder.phone.text = contact.phone
    }

    override fun getItemCount(): Int = contactsArrayList.size

    override fun getItemId(p0: Int): Long = p0.toLong()

//    override fun getCount(): Int = contacts.size
//
//    override fun getItem(p0: Int): Any = contacts[p0]


    /*override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, p2, false)

        firstName = view!!.findViewById(R.id.tvContactFirstName)
        lastName = view.findViewById(R.id.tvContactLastName)
        phone = view.findViewById(R.id.tvContactPhone)
        firstName.text = contacts[p0].firstName
        lastName.text = contacts[p0].lastName
        phone.text = contacts[p0].phone
        return view
    }*/
}

interface OnItemClickListener{
    fun onItemClicked(contact: Contact, id: Long)
}