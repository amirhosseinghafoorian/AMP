package com.a.amp.home.ui

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.storage.setting
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
        val loginViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)

        CoroutineScope((Dispatchers.IO)).launch {
            fillDataBase()
            loginViewModel.fillSummary()
            loginViewModel.fillRelated()

            withContext(Dispatchers.Main) {
                val myAdapter2 = loginViewModel.summaryList.value?.let { HomeSummaryCvAdapter(it) }
                val myAdapter = loginViewModel.relatedList.value?.let { HomeRelatedCvAdapter(it) }

//        homeViewPagerInit()
                home_page_recycle_2.apply {
                    adapter = myAdapter2
                    setHasFixedSize(true)
                }

                home_page_recycle_1.apply {
                    adapter = myAdapter
                    setHasFixedSize(true)
                }


                loginViewModel.summaryList.observe(viewLifecycleOwner, { list ->
                    myAdapter2?.notifyDataSetChanged()
                })

                loginViewModel.relatedList.observe(viewLifecycleOwner, { list ->
                    myAdapter?.notifyDataSetChanged()
                })
            }
        }

        home_appbar_end_icon.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    setting.getString("username"),
                    setting.getString("id")
                )
            )
        }
        home_appbar_start_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment(""))
        }
        home_appbar_end_icon_power.setOnClickListener {
            setting.remove("username")
            setting.remove("id")
            setting.remove("token")
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthenticate())
        }
    }

    private suspend fun fillDataBase() {

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
        val repoResult2 = repo.syncFeed()
        if (repoResult2.status == Status.SUCCESS && repoResult2.code == 200) {

            val unformattedList2 = repoResult2.data?.articles
            val formattedList2 = mutableListOf<Article>()
            unformattedList2?.let { formattedList2.addAll(it) }
            val resultList2 = ArticleEntity.convertToDataItem6(formattedList2)

            for (i in resultList2.indices) {
                db.myDao().insertArticles(resultList2[i])
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
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}

//    private fun homeViewPagerInit() {
//        val viewPagerAdapter = HomeViewPagerAdapter(childFragmentManager, lifecycle)
//        viewPagerAdapter.addFragment(ProfileTabFragment("selena"), "اقتصاد")
//        viewPagerAdapter.addFragment(ProfileTabFragment2(""), "بورس")
//        viewPager.adapter = viewPagerAdapter
//        TabLayoutMediator((home_page_TL as TabLayout), (viewPager as ViewPager2)) { tab, position ->
//            tab.text = viewPagerAdapter.getName(position)
//        }.attach()
////        viewPager.addFragment()
//    }