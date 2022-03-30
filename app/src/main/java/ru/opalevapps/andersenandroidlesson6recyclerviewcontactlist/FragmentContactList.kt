package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment

private const val TAG = "FragmentContactList"

class FragmentContactList : Fragment() {
    lateinit var lvContactList: ListView
    var contactArrayList: ArrayList<Contact> = ArrayList()
    var contactListAdapter: ContactListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeat(5) {
            contactArrayList.add(Contact())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_contact, container, false)

        lvContactList = root.findViewById(R.id.lvContactList)
        contactListAdapter = ContactListAdapter(root.context, contactArrayList)
        lvContactList.adapter = contactListAdapter

        // listView on item click listener
        lvContactList.setOnItemClickListener { adapterView, view, position, id ->
            val element = adapterView.getItemAtPosition(position) as Contact
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
                                element.firstName,
                                element.lastName,
                                element.phone,
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
                                    element.firstName,
                                    element.lastName,
                                    element.phone,
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

            lvContactList.deferNotifyDataSetChanged()
            contactListAdapter = ContactListAdapter(root.context, contactArrayList)
            lvContactList.adapter = contactListAdapter
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
}