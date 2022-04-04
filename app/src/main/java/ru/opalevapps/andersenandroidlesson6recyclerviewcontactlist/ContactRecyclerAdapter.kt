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
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsArrayList[position], itemClickListener, position)
    }

    override fun getItemCount(): Int = contactsArrayList.size

    override fun getItemId(p0: Int): Long = p0.toLong()
}

interface OnItemClickListener{
    fun onItemClicked(contact: Contact, id: Long)
}