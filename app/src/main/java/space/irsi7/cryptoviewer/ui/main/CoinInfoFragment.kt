package space.irsi7.cryptoviewer.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import space.irsi7.cryptoviewer.MainActivity
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentCoinInfoBinding


class CoinInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCoinInfoBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.back_vector)

        toolbar.setNavigationOnClickListener {
            viewModel.isSelected.postValue(false)
        }
        binding.Info.text = viewModel.tokenInfo.value!!.description.getValue("en")
        binding.Categories.text = viewModel.tokenInfo.value!!.categories.toString()
    }

    companion object {
        fun newInstance() = CoinInfoFragment()
    }
}