package space.irsi7.cryptoviewer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.coin.CoinsList

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var  ChipChoise: ChipGroup
    private lateinit var ChipUsd: Chip
    private lateinit var ChipEur: Chip
//    lateinit var download: TextView
//    lateinit var error: TextView
//    lateinit var btn: Button
    // lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ChipChoise = view.findViewById(R.id.ChoiceV)
        ChipUsd = view.findViewById(R.id.chipUSD)
        ChipEur = view.findViewById(R.id.chipEUR)

        ChipUsd.isEnabled = false
        ChipEur.isEnabled = false
        if (savedInstanceState == null) {
            viewModel.getTokensInfo()
        }
        var firstcall = true
        viewModel.isDownloading.observe(viewLifecycleOwner, Observer{
            if(it){
                childFragmentManager.beginTransaction()
                    .replace(R.id.content, DownloadFragment.newInstance())
                    .commitNow()
                firstcall = false
            } else if(!firstcall){
                childFragmentManager.beginTransaction()
                    .replace(R.id.content, CoinsList.newInstance())
                    .commitNow()
                ChipEur.isEnabled = true
                ChipUsd.isEnabled = true
                ChipUsd.isChecked = true
            }
       })
        var firstcall1 = true
        ChipChoise.setOnCheckedStateChangeListener { group, checkedIds ->
            if(!firstcall1 && checkedIds.isNotEmpty()) {
                if (checkedIds[0] == ChipUsd.id) {
                    ChipUsd.isChecked = true
                    viewModel.chosenVal.postValue(0)
                }
                if (checkedIds[0] == ChipEur.id) {
                    viewModel.chosenVal.postValue(1)
                    ChipEur.isChecked = true
                }
            }
            firstcall1 = false
        }
    }
}