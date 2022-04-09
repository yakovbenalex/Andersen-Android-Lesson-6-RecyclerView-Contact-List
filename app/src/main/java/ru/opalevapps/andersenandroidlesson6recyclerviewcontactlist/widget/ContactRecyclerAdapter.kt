package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.R
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.model.Contact
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.util.ImageLoading
import java.util.*
import kotlin.collections.ArrayList


class ContactRecyclerAdapter(
    private val context: Context,
    var contactsArrayList: ArrayList<Contact>,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<ContactRecyclerAdapter.MyViewHolder>(), Filterable {
    internal var fullContactsArrayList: ArrayList<Contact> = ArrayList(contactsArrayList)

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

    override fun getFilter(): Filter {
        return SearchFilter
    }

    private val SearchFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: ArrayList<Contact> = ArrayList()
            if (charSequence.isEmpty()) {
                filteredList.addAll(fullContactsArrayList)
            } else {
                // split search query by words
                val filterPatternArray = charSequence
                    .toString()
                    .lowercase(Locale.getDefault())
                    .trim()
                    .replace("\\s+".toRegex(), " ")  // remove duplicated whitespaces
                    .split(' ')

                // filter contacts by any fields - firstName, lastName or both
                for (contact in fullContactsArrayList) {
                    var filtered = true
                    for (filter in filterPatternArray) {
                        if (!(contact.firstName.lowercase(Locale.getDefault()).contains(filter)
                                    || contact.lastName.lowercase(Locale.getDefault()).contains(filter))
                        ) {
                            filtered = false
                        }
                    }
                    if (filtered) filteredList.add(contact)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            contactsArrayList.clear()
            contactsArrayList.addAll(results.values as ArrayList<Contact>)
            notifyDataSetChanged()
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(contact: Contact, id: Long)
}