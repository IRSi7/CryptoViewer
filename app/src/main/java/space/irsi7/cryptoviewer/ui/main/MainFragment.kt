package space.irsi7.cryptoviewer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.ui.main.TokenListFragment.CoinsListFragment
import space.irsi7.cryptoviewer.databinding.FragmentMainBinding
import space.irsi7.cryptoviewer.ui.main.MessageFragment.DownloadFragment
import space.irsi7.cryptoviewer.ui.main.MessageFragment.ErrorFragment


class MainFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null

    private lateinit var  chipChoice: ChipGroup
    private lateinit var chipUSD: Chip
    private lateinit var chipEUR: Chip

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        //requireActivity().setActionBar(toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipChoice = binding.ChoiceV
        chipUSD = binding.chipUSD
        chipEUR = binding.chipEUR

        chipUSD.isEnabled = false
        chipEUR.isEnabled = false
        if (viewModel.tokenData.value == null) {
            viewModel.getCoinList()
        }

        viewModel.isDownloadingList.observe(viewLifecycleOwner) {
            if (it != null) {
                    if (it) {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.content, DownloadFragment.newInstance())
                            .commitNow()
                    } else {
                        if (viewModel.isFailList) {
                            childFragmentManager.beginTransaction()
                                .replace(R.id.content, ErrorFragment.newInstance())
                                .commitNow()
                        } else {
                            childFragmentManager.beginTransaction()
                                .replace(R.id.content, CoinsListFragment.newInstance())
                                .commitNow()
                            chipEUR.isEnabled = true
                            chipUSD.isEnabled = true
                            chipUSD.isChecked = true
                        }
                }
            }
        }

        var firstcall = true
        chipChoice.setOnCheckedStateChangeListener { _, checkedIds ->
            if(!firstcall && checkedIds.isNotEmpty()) {
                if (checkedIds[0] == chipUSD.id) {
                    chipUSD.isChecked = true
                    viewModel.chosenVal.postValue(0)
                }
                if (checkedIds[0] == chipEUR.id) {
                    viewModel.chosenVal.postValue(1)
                    chipEUR.isChecked = true
                }
            }
            firstcall = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}