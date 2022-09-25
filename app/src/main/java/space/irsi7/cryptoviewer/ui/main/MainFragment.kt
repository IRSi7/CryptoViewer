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
import space.irsi7.cryptoviewer.coin.CoinsList

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var  chipChoice: ChipGroup
    private lateinit var chipUSD: Chip
    private lateinit var chipEUR: Chip


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipChoice = view.findViewById(R.id.ChoiceV)
        chipUSD = view.findViewById(R.id.chipUSD)
        chipEUR = view.findViewById(R.id.chipEUR)

        chipUSD.isEnabled = false
        chipEUR.isEnabled = false
        if (savedInstanceState == null) {
            viewModel.getTokensInfo()
        }
        var firstcall = true
        viewModel.isDownloading.observe(viewLifecycleOwner) {
            if (it) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.content, DownloadFragment.newInstance())
                    .commitNow()
                firstcall = false
            } else if (!firstcall) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.content, CoinsList.newInstance())
                    .commitNow()
                chipEUR.isEnabled = true
                chipUSD.isEnabled = true
                chipUSD.isChecked = true
            }
        }
        viewModel.isFail.observe(viewLifecycleOwner) {
            if (it) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.content, ErrorFragment.newInstance())
                    .commitNow()
            }
        }
        var firstcall1 = true
        chipChoice.setOnCheckedStateChangeListener { _, checkedIds ->
            if(!firstcall1 && checkedIds.isNotEmpty()) {
                if (checkedIds[0] == chipUSD.id) {
                    chipUSD.isChecked = true
                    viewModel.chosenVal.postValue(0)
                }
                if (checkedIds[0] == chipEUR.id) {
                    viewModel.chosenVal.postValue(1)
                    chipEUR.isChecked = true
                }
            }
            firstcall1 = false
        }

    }
}