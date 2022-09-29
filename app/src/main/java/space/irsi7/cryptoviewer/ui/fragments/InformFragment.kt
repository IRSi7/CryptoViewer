package space.irsi7.cryptoviewer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentInfoBinding
import space.irsi7.cryptoviewer.model.States
import space.irsi7.cryptoviewer.ui.viewModels.MainViewModel


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
            viewModel.currentState.value = States.TOKENSLIST
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbID.text = "Загрузка"
        childFragmentManager.beginTransaction()
            .replace(R.id.info_content, DownloadFragment.newInstance())
            .commitNow()

        viewModel.tokenInfo.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tbID.text = viewModel.tokenInfo.value?.name ?: "Ошибка"
                childFragmentManager.beginTransaction()
                    .replace(R.id.info_content, CoinInfoFragment.newInstance())
                    .commitNow()
            }else{
                childFragmentManager.beginTransaction()
                    .replace(R.id.info_content, ErrorFragment.newInstance())
                    .commitNow()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.selectedToken = ""
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InformFragment().apply {
            }
    }
}