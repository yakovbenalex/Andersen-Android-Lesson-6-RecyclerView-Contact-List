package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget.ContactRecyclerAdapter
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget.OnItemClickListener
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.R
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.model.Contact

private const val TAG = "FragmentContactList"

class FragmentContactList : Fragment(), OnItemClickListener {
    lateinit var rvContactList: RecyclerView
    var contactArrayList: ArrayList<Contact> = ArrayList()
    var contactListAdapter: ContactRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeat(200) {
            contactArrayList.add(Contact(photoURL = "https://picsum.photos/id/${it+10}/"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_contact, container, false)

        rvContactList = root.findViewById(R.id.rvContactList)
        rvContactList.layoutManager = LinearLayoutManager(context)
        contactListAdapter = ContactRecyclerAdapter(root.context, contactArrayList, this)
        rvContactList.adapter = contactListAdapter

        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            this
        ) { key, bundle ->
            val firstName = bundle.getString(FragmentContactDetails.DATA_FIRST_NAME)
            val lastName = bundle.getString(FragmentContactDetails.DATA_LAST_NAME)
            val phone = bundle.getString(FragmentContactDetails.DATA_PHONE)
            val idRecord = bundle.getInt(FragmentContactDetails.DATA_ID_RECORD)

            contactArrayList[idRecord].firstName = firstName!!
            contactArrayList[idRecord].lastName = lastName!!
            contactArrayList[idRecord].phone = phone!!

            // update data in recycleView
            rvContactList.invalidate()
            contactListAdapter = ContactRecyclerAdapter(root.context, contactArrayList, this)
            rvContactList.adapter = contactListAdapter
        }

        // Inflate the layout for this fragment
        return root
    }

    companion object {
        const val FRAGMENT_CONTACT_LIST = "FRAGMENT_CONTACT_LIST"
        const val REQUEST_KEY = "FRAGMENT_CONTACT_LIST_REQUEST_KEY"

        @JvmStatic
        fun newInstance() = FragmentContactList()
    }

    override fun onItemClicked(contact: Contact, id: Long) {
        // recycleView on item click listener
        val isTablet = resources.getBoolean(R.bool.isTablet)
        val fragmentManager = requireActivity().supportFragmentManager

        fragmentManager.apply {
            beginTransaction().run {
                if (isTablet) {
                    // get contact details fragment
                    val fragment = fragmentManager.findFragmentByTag(
                        FragmentContactDetails.FRAGMENT_CONTACT_DETAILS
                    )

                    // if fragment contact details already opened, then close fragment and replace by new
                    if (fragment != null) fragmentManager.popBackStack()

                    replace(
                        R.id.fragment_details_container,
                        FragmentContactDetails.newInstance(
                            contact.firstName,
                            contact.lastName,
                            contact.phone,
                            contact.photoURL,
                            id.toInt()
                        ),
                        FragmentContactDetails.FRAGMENT_CONTACT_DETAILS
                    )
                } else {
                    // if fragment already opened
                    if (findFragmentByTag(FragmentContactDetails.FRAGMENT_CONTACT_DETAILS) == null) {
                        replace(
                            R.id.fragment_container,
                            FragmentContactDetails.newInstance(
                                contact.firstName,
                                contact.lastName,
                                contact.phone,
                                contact.photoURL,
                                id.toInt()
                            ),
                            FragmentContactDetails.FRAGMENT_CONTACT_DETAILS
                        )
                    }
                }
                addToBackStack(FragmentContactDetails.FRAGMENT_CONTACT_DETAILS)
                commit()
            }
        }
    }
}