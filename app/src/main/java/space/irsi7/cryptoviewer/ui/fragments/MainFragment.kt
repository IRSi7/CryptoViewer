package space.irsi7.cryptoviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.databinding.FragmentMainBinding
import space.irsi7.cryptoviewer.model.Currency
import space.irsi7.cryptoviewer.model.States
import space.irsi7.cryptoviewer.ui.viewModels.MainViewModel


class MainFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null

    private lateinit var  chipChoice: ChipGroup

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
        chipChoice.isSingleSelection = true
        chipChoice.forEach { it.isEnabled = false }
        setChipChoice()

        if (viewModel.tokensList.value == null) {

            viewModel.downloadTokenList()
        }

        //Успешное обновление
        viewModel.currentState.observe(viewLifecycleOwner) { state ->
            when(state){
                States.DOWNLOADING -> {
                    setFragment(DownloadFragment.newInstance())
                }
                States.ERROR -> {
                    setFragment(ErrorFragment.newInstance())
                }
                States.TOKENSLIST -> {
                    setFragment(TokenListFragment.newInstance())
                    chipChoice.forEach { it.isEnabled = true }
                }
                States.SELECTED -> TODO()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setChipChoice() {
        var chips = setChips(chipChoice)
        chipChoice.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                for (i in Currency.values()) {
                    if (checkedIds[0] == chips[i]!!.id){
                        chips[viewModel.currentCurrency.value]!!.isClickable = true
                        chips[i]!!.isChecked = false
                        viewModel.currentCurrency.value = i
                        chips[i]!!.isChecked = true
                        chips[i]!!.isClickable = false
                        break
                    }
                }
            }
        }
    }

    private fun setChips(chipGroup: ChipGroup): Map<Currency, Chip> {
        var result = mutableMapOf<Currency, Chip>()
        Currency.values().forEach {
            val chip = Chip(context)
            chip.text = it.toString()
            chip.isCheckable = true
            chipGroup.addView(chip)
            result[it] = chip
        }
        result[Currency.USD]!!.isChecked = true
        return result
    }

    private fun setFragment(fragment: Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commitNow()
    }
}