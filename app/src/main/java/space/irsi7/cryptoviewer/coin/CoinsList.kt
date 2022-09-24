package space.irsi7.cryptoviewer.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.ui.main.MainViewModel
import java.io.File

//Класс хранящий картинки из одной директории
class CoinsList : Fragment() {
    private lateinit var fileList: RecyclerView
    private lateinit var adapter: CoinAdapter
    private lateinit var glm: GridLayoutManager
    private lateinit var base: File
    private lateinit var path: String
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fileList = inflater.inflate(R.layout.tokens_list, container, false) as RecyclerView
        path = arguments?.get("path").toString()
        glm = GridLayoutManager(context, 1)
        fileList.layoutManager = glm
        var first = false
        viewModel.chosenVal.observe(viewLifecycleOwner,Observer{
            if(first) {
                when (it) {
                    0 -> UpdateAdapter(viewModel.valueUSD.value!!)
                    1 -> UpdateAdapter(viewModel.valueEUR.value!!)
                }
            }
            first = true
        })
        setAdapter()
        return fileList
    }

    private fun setAdapter() {
        val b = viewModel.tokenData.value
        adapter = CoinAdapter(context, viewModel.tokenData.value, viewModel.valueUSD.value)
        fileList.adapter = adapter
    }

    fun UpdateAdapter(values: List<Value>) {
        adapter.updateValuesSet(values)
    }

    companion object {
        fun newInstance() = CoinsList()
    }
}