package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(FragmentContactList.FRAGMENT_CONTACT_LIST) == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, FragmentContactList.newInstance(), FragmentContactList.FRAGMENT_CONTACT_LIST)
                commit()
            }
        }
    }
}