package com.jayto.learnkotlin.page.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jayto.learnkotlin.R
import com.jayto.learnkotlin.page.beranda.AddActivity
import com.jayto.learnkotlin.page.beranda.DetaillActivity
import com.jayto.learnkotlin.page.beranda.FragmentBeranda
import com.jayto.learnkotlin.page.info.FragmentInfo
import com.jayto.learnkotlin.page.run.FragmentRun
import com.jayto.learnkotlin.utils.Constants.Companion.GO_TO_ADD
import com.jayto.learnkotlin.utils.Constants.Companion.GO_TO_DETAIL
import com.jayto.learnkotlin.utils.Constants.Companion.HOME
import com.jayto.learnkotlin.utils.Constants.Companion.INFO
import com.jayto.learnkotlin.utils.Constants.Companion.RUN
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener, FragmentBeranda.Callback {

    private var pageContent : Fragment? = null
    private var s = "fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            pageContent = FragmentBeranda.newInstance()
            loadFragment(pageContent, HOME)
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        pageContent?.let { supportFragmentManager.putFragment(outState, s, it) }
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GO_TO_ADD && resultCode == Activity.RESULT_OK) {
            val brand = data?.getBooleanExtra("BERANDA", true)
            if (brand!!) {
                pageContent = FragmentBeranda.newInstance()
                loadFragment(pageContent, HOME)
            }
        } else if (requestCode == GO_TO_DETAIL && resultCode == Activity.RESULT_OK) {
            pageContent = FragmentBeranda.newInstance()
            loadFragment(pageContent, HOME)
        }
    }

    private fun loadFragment(pageContent: Fragment?, item : Int) : Boolean {
        if (pageContent != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, pageContent)
                    .commit()
            bottom_navigation_view.menu.getItem(item).isChecked = true
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var itemNav = 0
        when (item.itemId){
            R.id.bottom_nav_home -> {
                pageContent = FragmentBeranda.newInstance()
                itemNav = HOME
            }
            R.id.bottom_nav_run -> {
                pageContent = FragmentRun.newInstance()
                itemNav = RUN
            }
            R.id.bottom_nav_info -> {
                pageContent = FragmentInfo.newInstance()
                itemNav = INFO
            }
        }
        return loadFragment(pageContent, itemNav)
    }

    override fun goToDetail(id: String?, name: String?) {
        val intent = Intent(this@MainActivity, DetaillActivity::class.java).apply {
            putExtra("EXTRA", id)
            putExtra("EXTRANAME", name)
        }
        startActivityForResult(intent, GO_TO_DETAIL)
    }

    override fun goToAdd() {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivityForResult(intent, GO_TO_ADD)
    }
}