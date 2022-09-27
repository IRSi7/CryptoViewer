package space.irsi7.cryptoviewer.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentInfoBinding


class InformFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.back_vector)
        toolbar.setNavigationOnClickListener {
            viewModel.selected.postValue(null)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isDownloadingCoin.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    binding.tbID.text = "Загрузка"
                    childFragmentManager.beginTransaction()
                        .replace(R.id.info_content, DownloadFragment.newInstance())
                        .commitNow()
                } else {
                    if (viewModel.isFailCoin) {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.info_content, ErrorFragment.newInstance())
                            .commitNow()
                    } else {
                        binding.tbID.text = viewModel.tokenInfo.value?.name ?: "Ошибка"
                        childFragmentManager.beginTransaction()
                            .replace(R.id.info_content, CoinInfoFragment.newInstance())
                            .commitNow()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.selected.value = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InformFragment().apply {

            }
    }
}