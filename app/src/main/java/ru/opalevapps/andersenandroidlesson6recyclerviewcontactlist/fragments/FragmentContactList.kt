package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.R
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.model.Contact
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget.ContactRecyclerAdapter
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.widget.OnItemClickListener

private const val TAG = "FragmentContactList"
private const val contactListSize = 200

class FragmentContactList : Fragment(), OnItemClickListener {
    private lateinit var rvContactList: RecyclerView
    private var contactArrayList: ArrayList<Contact> = ArrayList()
    var contactListAdapter: ContactRecyclerAdapter? = null
    var searchQuery: String = ""
    var searchView: SearchView? = null
    var searchActionMenuItem: MenuItem? = null
    private var isTablet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isTablet = resources.getBoolean(R.bool.isTablet)

        // for adding search option in app bar
        setHasOptionsMenu(true)

        // fill example data for recyclerView
        repeat(contactListSize) {
            contactArrayList.add(Contact(photoURL = "https://picsum.photos/id/${it + 10}/"))
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

            val contact = contactListAdapter!!.fullContactsArrayList
            // find changing contact index
            val index: Int = contact.indexOfFirst { it.id == idRecord }
            // change contact data
            contact[index].firstName = firstName!!
            contact[index].lastName = lastName!!
            contact[index].phone = phone!!

            // update data in recycleView
            if (isTablet) contactListAdapter?.filter?.filter(searchQuery)
            else contactListAdapter?.filter?.filter("")
        }

        // Inflate the layout for this fragment
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        // add search item to app bar
        searchActionMenuItem = menu.findItem(R.id.action_search)
        searchView = searchActionMenuItem?.actionView as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!searchView!!.isIconified) {
                    searchView!!.isIconified = true
                }
                searchActionMenuItem?.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (s.isNotEmpty()) searchQuery = s

                contactListAdapter?.filter?.filter(s)
                Log.d(TAG, "onQueryTextChange: $s")
                return false
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val FRAGMENT_CONTACT_LIST = "FRAGMENT_CONTACT_LIST"
        const val REQUEST_KEY = "FRAGMENT_CONTACT_LIST_REQUEST_KEY"

        @JvmStatic
        fun newInstance() = FragmentContactList()
    }

    override fun onItemClicked(contact: Contact, id: Long) {
        // recycleView on item click listener
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
                            contact.id
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
                                contact.id
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