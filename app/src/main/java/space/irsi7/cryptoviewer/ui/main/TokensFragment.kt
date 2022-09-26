package space.irsi7.cryptoviewer.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.coin.CoinsList

class TokensFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tokens, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewpager)
        setupViewPager(viewPager)
    }

    // Функция для добавления фрагментов в ViewPager
    private fun setupViewPager(viewPager: ViewPager2?) {
        val adapter = ViewPager2Adapter(childFragmentManager)
        val coinsList = CoinsList()
        val args = Bundle()
        //args.putString("path", "Camera")
        //photoList.arguments = args
        adapter.addFragment(coinsList, "USD")
        viewPager?.adapter = adapter
    }

    // Класс адаптера для ViewPager-a
    internal inner class ViewPager2Adapter(manager: FragmentManager?) :
        FragmentStateAdapter(manager!!, lifecycle) {
        // Массив для хранения фрагментов (страниц)
        private val mFragmentList: ArrayList<Fragment?> = ArrayList()

        // Массив для хранения массива заголовков фрагментов дяя показа на вкладках
        private val mFragmentTitleList: ArrayList<String?> = ArrayList()

        fun addFragment(fragment: Fragment?, title: String?) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        fun getPageTitle(position: Int): String? {
            return mFragmentTitleList[position]
        }

        override fun getItemCount(): Int {
            return mFragmentTitleList.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]!!
        }
    }


    companion object {
        fun newInstance() = TokensFragment()
    }
}