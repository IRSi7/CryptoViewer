package space.irsi7.cryptoviewer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentErrorBinding
import space.irsi7.cryptoviewer.model.States
import space.irsi7.cryptoviewer.ui.viewModels.MainViewModel

class ErrorFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentErrorBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTry.setOnClickListener{
            when(viewModel.currentState.value){
                States.SELECTED -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.info_content, DownloadFragment.newInstance())
                        .commitNow()
                    viewModel.downloadTokenInfo(viewModel.selectedToken)
                }
                else -> viewModel.downloadTokenList()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ErrorFragment()
    }
}