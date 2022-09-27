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
import com.squareup.picasso.Picasso
import space.irsi7.cryptoviewer.MainActivity
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentInfoBinding
import space.irsi7.cryptoviewer.databinding.FragmentInformBinding


class CoinInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentInformBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInformBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.with(context)
            .load(viewModel.tokenInfo.value!!.image.getValue("large"))
            .placeholder(R.drawable.placeholder)
            //.error(R.drawable.user_placeholder_error)
            .into(binding.iwIcon)
        binding.Info.text = viewModel.tokenInfo.value!!.description.getValue("en")
        binding.Categories.text = viewModel.tokenInfo.value!!.categories.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoinInfoFragment()
    }
}