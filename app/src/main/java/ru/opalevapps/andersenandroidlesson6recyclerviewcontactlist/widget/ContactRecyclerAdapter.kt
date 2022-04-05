package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.R
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.model.Contact
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.util.ImageLoading


class ContactRecyclerAdapter(
    private val context: Context,
    private val contactsArrayList: ArrayList<Contact>,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<ContactRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val tvFirstName: TextView = itemView.findViewById(R.id.tvContactFirstName)
        private val tvLastName: TextView = itemView.findViewById(R.id.tvContactLastName)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvContactPhone)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.ivContactPhoto)

        fun bind(
            contact: Contact,
            clickListener: OnItemClickListener,
            position: Int,
            context: Context
        ) {
            tvFirstName.text = contact.firstName
            tvLastName.text = contact.lastName
            tvPhone.text = contact.phone

            ImageLoading.loadPicturePicasso(context, ivPhoto, contact.photoURL, 80)

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
        holder.bind(contactsArrayList[position], itemClickListener, position, context)
    }

    override fun getItemCount(): Int = contactsArrayList.size

    override fun getItemId(p0: Int): Long = p0.toLong()
}

interface OnItemClickListener {
    fun onItemClicked(contact: Contact, id: Long)
}