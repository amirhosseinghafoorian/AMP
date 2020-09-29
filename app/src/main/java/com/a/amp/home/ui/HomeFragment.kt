package com.a.amp.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRepository
import com.a.amp.article.data.TagEntity
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.storage.setting
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var setting: setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = 0
        setting = setting()
        CoroutineScope(Dispatchers.IO).launch {
            Looper.prepare()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CoroutineScope((Dispatchers.IO)).launch {
            fillDataBase()

            withContext(Dispatchers.Main) {
                homeViewPagerInit()

                home_appbar_end_icon.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            setting.getString("username"),
                            setting.getString("id")
                        )
                    )
                }
                home_appbar_start_icon.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToWriteFragment(
                            ""
                        )
                    )
                }
                home_appbar_end_icon_power.setOnClickListener {
                    setting.remove("username")
                    setting.remove("id")
                    setting.remove("token")
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthenticate())
                }
            }
        }
    }

    private suspend fun fillDataBase() {
        fillAllArticles()
        fillFeed()
    }

    private suspend fun fillAllArticles() {
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = ArticleRepository(MyApp.publicApp)

        val repoResult = repo.syncArticles()
        if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {

            val unformattedList = repoResult.data?.articles
            val formattedList = mutableListOf<Article>()
            unformattedList?.let { formattedList.addAll(it) }
            val resultList = ArticleEntity.convertToDataItem4(formattedList)

            for (i in resultList.indices) {
                db.myDao().insertArticles(resultList[i])
            }

            for (j in 0 until repoResult.data?.articles?.size!!) {
                val unformattedList3 = repoResult.data.articles[j].tagList
                val formattedList3 = unformattedList3.let {
                    TagEntity.convertToDataItem(
                        it,
                        repoResult.data.articles[j].slug
                    )
                }
                for (i in 0 until formattedList3.size) {
                    db.myDao().insertTags(formattedList3[i])
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.SUCCESS && repoResult.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fillFeed() {
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = ArticleRepository(MyApp.publicApp)
        val repoResult2 = repo.syncFeed()
        if (repoResult2.status == Status.SUCCESS && repoResult2.code == 200) {

            val unformattedList2 = repoResult2.data?.articles
            val formattedList2 = mutableListOf<Article>()
            unformattedList2?.let { formattedList2.addAll(it) }
            val resultList2 = ArticleEntity.convertToDataItem6(formattedList2)

            for (i in resultList2.indices) {
                db.myDao().insertArticles(resultList2[i])
            }


            for (j in 0 until repoResult2.data?.articles?.size!!) {
                val unformattedList3 = repoResult2.data.articles[j].tagList
                val formattedList3 = unformattedList3.let {
                    TagEntity.convertToDataItem(
                        it,
                        repoResult2.data.articles[j].slug
                    )
                }
                for (i in 0 until formattedList3.size) {
                    db.myDao().insertTags(formattedList3[i])
                }
            }

        } else if (repoResult2.status == Status.SUCCESS && repoResult2.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult2.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun homeViewPagerInit() {
        val viewPagerAdapter = HomeViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(HomeTabFragment(), "برای شما")
        viewPagerAdapter.addFragment(HomeTabFragment2(), "تگ ها")
        HomeViewPager.adapter = viewPagerAdapter
        HomeViewPager.isUserInputEnabled = false
        TabLayoutMediator(
            (home_page_TL as TabLayout),
            (HomeViewPager as ViewPager2)
        ) { tab, position ->
            tab.text = viewPagerAdapter.getName(position)
        }.attach()
//        viewPager.addFragment()
    }
}
